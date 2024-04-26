package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.ConfidencialidadDocumentoRolDao;
import cl.gob.scj.sgdp.dao.ConfidencialidadDocumentoUsuarioDao;
import cl.gob.scj.sgdp.dao.HistoricoArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.PlantillaDeDocumentoDao;
import cl.gob.scj.sgdp.dto.ConfidencialidadDocumentoDTO;
import cl.gob.scj.sgdp.dto.ConfidencialidadDocumentoRolDTO;
import cl.gob.scj.sgdp.dto.ConfidencialidadDocumentoUsuarioDTO;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.PlantillaDeDocumentoDTO;
import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.ConfidencialidadDocumentoRol;
import cl.gob.scj.sgdp.model.ConfidencialidadDocumentoUsuario;
import cl.gob.scj.sgdp.model.HistoricoArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.PlantillaDeDocumento;
import cl.gob.scj.sgdp.model.Rol;
import cl.gob.scj.sgdp.model.UsuarioRol;
import cl.gob.scj.sgdp.service.ConfidencialidadDocumentoService;
import cl.gob.scj.sgdp.service.PlantillaDeDocumentoService;
import cl.gob.scj.sgdp.service.RolService;
import cl.gob.scj.sgdp.service.UsuarioRolService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ConfidencialidadDocumentoServiceImpl implements ConfidencialidadDocumentoService {
	
	@Autowired
	private UsuarioRolService usuarioService;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private ConfidencialidadDocumentoRolDao rolDao;
	
	@Autowired
	private ConfidencialidadDocumentoUsuarioDao usuarioDao;
	
	@Override
	public ConfidencialidadDocumentoDTO getByIdTipoDocumento(String id) {
		return make(id, usuarioService.getTodosLosIdUsuariosPorVigencia(true), rolService.getRoles());
	}
	
	private ConfidencialidadDocumentoDTO make(String id, List<String> usuarios, List<RolDTO> roles) {
	
		List<ConfidencialidadDocumentoUsuario> usuariosDao = usuarioDao.getUsuariosAsignados(id);
		List<ConfidencialidadDocumentoRol> rolesDao = rolDao.getRolesAsignados(id);
		List<ConfidencialidadDocumentoUsuarioDTO> usuariosAsignados = new ArrayList<>();
		List<ConfidencialidadDocumentoRolDTO> rolesAsignados = new ArrayList<>();
		
		
		for(ConfidencialidadDocumentoUsuario usuarioDao : usuariosDao) {
			ConfidencialidadDocumentoUsuarioDTO cdu = new ConfidencialidadDocumentoUsuarioDTO();
			cdu.setUsuario(usuarioDao.getIdUsuario());
			usuariosAsignados.add(cdu);
		}
		
		for(ConfidencialidadDocumentoRol rolDao : rolesDao) {
			ConfidencialidadDocumentoRolDTO cdr = new ConfidencialidadDocumentoRolDTO();
			cdr.setId(rolDao.getRol().getIdRol());
			cdr.setRol(rolDao.getRol().getNombreRol());
			rolesAsignados.add(cdr);
		}
		
		ConfidencialidadDocumentoDTO cf = new ConfidencialidadDocumentoDTO();
		cf.setId(id);
		cf.setRoles(roles);
		cf.setUsuarios(usuarios);
		cf.setRolesAsignados(rolesAsignados);
		cf.setUsuariosAsignados(usuariosAsignados);
		
		return cf;
	}

	@Override
	public ConfidencialidadDocumentoDTO guardar(ConfidencialidadDocumentoDTO cd) {
		eliminar(cd.getId());
		
		for(RolDTO rol : cd.getRoles()) {
			if(rol.getIdRol() != 0l) {
				ConfidencialidadDocumentoRol cdr = new ConfidencialidadDocumentoRol();
				cdr.setIdArchivoCms(cd.getId());
				Rol ur = new Rol();
				ur.setIdRol(rol.getIdRol());
				cdr.setRol(ur);
			
				rolDao.save(cdr);
			}
		}
		
		for(String u : cd.getUsuarios()) {
			if(!u.equals("0")) {
				ConfidencialidadDocumentoUsuario cdu = new ConfidencialidadDocumentoUsuario();
				cdu.setIdArchivoCms(cd.getId());
				cdu.setIdUsuario(u);
			
				usuarioDao.save(cdu);
			}
		}
		return cd;
	}

	@Override
	public ConfidencialidadDocumentoDTO eliminar(String id) {
		rolDao.eliminar(id);
		usuarioDao.eliminar(id);
		return null;
	}

	@Override
	public List<ConfidencialidadDocumentoDTO> getByIdTipoDocumento(List<String> ids) {
		List<String> usuarios = usuarioService.getTodosLosIdUsuariosPorVigencia(true);
		List<RolDTO> roles = rolService.getRoles();
		List<ConfidencialidadDocumentoDTO> confs = new ArrayList<>();
		for(String id : ids ) {
			confs.add(make(id, usuarios, roles));
		}
		return confs;
	}

	@Override
	public boolean puedeVerPorUsuario(String idArchivoCMS, Usuario usuario) {
		ConfidencialidadDocumentoDTO conf = getByIdDocumento(idArchivoCMS);
		List<String> usuarios = new ArrayList<>();
		for(ConfidencialidadDocumentoUsuarioDTO cdu : conf.getUsuariosAsignados()) {
			usuarios.add(cdu.getUsuario());
		}
		return conf.getUsuariosAsignados().size() == 0 || usuarios.contains(usuario.getIdUsuario());
	}
	
	@Override
	public boolean puedeVerPorRol(String idArchivoCMS, Usuario usuario) {
		ConfidencialidadDocumentoDTO conf = getByIdDocumento(idArchivoCMS);
		List<String> roles = new ArrayList<>();
		for(ConfidencialidadDocumentoRolDTO cdr : conf.getRolesAsignados()) {
			roles.add(cdr.getId() + "");
		}
		boolean enRol = false;
		for(RolDTO r: usuario.getTodosLosRoles()) {
			enRol = roles.contains(r.getIdRol() + "");
			if(enRol) {
				break;
			}
		}
		return conf.getRolesAsignados().size() == 0 || enRol;
	}

	@Override
	public ConfidencialidadDocumentoDTO getByIdDocumento(String id) {
		return make(id, usuarioService.getTodosLosIdUsuariosPorVigencia(true), rolService.getRoles());
	}

	@Override
	public void eliminaConfidenciales(List<DetalleDeArchivoDTO> detallesDeArchivosDTO, Usuario usuario) {
		Iterator<DetalleDeArchivoDTO> detallesDeArchivosDTOIt = detallesDeArchivosDTO.iterator();
		while(detallesDeArchivosDTOIt.hasNext()) {
			DetalleDeArchivoDTO da = detallesDeArchivosDTOIt.next();
			if (!puedeVerPorRol(da.getIdArchivo(), usuario) || !puedeVerPorUsuario(da.getIdArchivo(), usuario)) {
				detallesDeArchivosDTOIt.remove();
			}
		}
	}
	
}
