package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.RolDao;
import cl.gob.scj.sgdp.dao.UnidadDao;
import cl.gob.scj.sgdp.dao.UsuarioRolDao;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.dto.UsuarioRolDTO;
import cl.gob.scj.sgdp.dto.UsuariosPorRolDTO;
import cl.gob.scj.sgdp.model.LogFueraDeOficina;
import cl.gob.scj.sgdp.model.Rol;
import cl.gob.scj.sgdp.model.Unidad;
import cl.gob.scj.sgdp.model.UsuarioRol;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.RolService;
import cl.gob.scj.sgdp.service.UnidadService;
import cl.gob.scj.sgdp.service.UsuarioRolService;
import cl.gob.scj.sgdp.util.SGDPUtil;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UsuarioRolServiceImpl implements UsuarioRolService {
	
	private static final Logger log = Logger.getLogger(UsuarioRolServiceImpl.class);

	@Autowired 
	private UsuarioRolDao usuarioRolDao;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private UnidadDao unidadDao;
	
	@Autowired
	private RolDao rolDao;
		
	@Override	
	public List<UsuarioRol> getUsuarioRolesPorIdUsuario(String idUsuario) {		
		return usuarioRolDao.getUsuarioRolesPorIdUsuario(idUsuario, true);
	}

	@Override
	public List<RolDTO> getRolesUsuarioPorIdUsuario(String idUsuario, List<RolDTO> roles) {	
		List<UsuarioRol> usuarioRoles = usuarioRolDao.getUsuarioRolesPorIdUsuario(idUsuario, true);	
		for (UsuarioRol usuarioRol : usuarioRoles) {
			//MIG
			RolDTO rolDTO = new RolDTO(usuarioRol.getRol().getIdRol(), usuarioRol.getRol().getNombreRol(), usuarioRol.getRol().getUnidad().getIdUnidad(), usuarioRol.getRol().getUnidad().getNombreCompletoUnidad());
			roles.add(rolDTO);
		}
		return roles;
	}
	
	@Override
	public void cargaUsuariosRolesPosiblesPorIdInstanciaDeTarea(long idInstanciaDeTarea, List<String> posiblesUsuarios) {
		List<UsuarioRol> usuarioRoles = usuarioRolDao.getUsuariosRolesPosiblesPorIdInstanciaDeTarea(idInstanciaDeTarea);
		for (UsuarioRol usuarioRol : usuarioRoles) {
			posiblesUsuarios.add(usuarioRol.getIdUsuario());
		}		
	}

	@Override
	public void cargaUsuarioRolPorIdUsuarioIdRol(UsuarioRolDTO usuarioRolDTO) {
		UsuarioRol usuarioRol = usuarioRolDao.getUsuarioRolPorIdUsuarioIdRol(usuarioRolDTO.getIdUsuario(), usuarioRolDTO.getIdRol());
		usuarioRolDTO.setActivo(usuarioRol.getActivo());
		usuarioRolDTO.setFueraDeOficina(usuarioRol.getFueraDeOficina());
		usuarioRolDTO.setIdUnidad(usuarioRol.getUnidad().getIdUnidad());
		usuarioRolDTO.setIdUsuarioRol(usuarioRol.getIdUsuarioRol());
		usuarioRolDTO.setNombreRol(usuarioRol.getRol().getNombreRol());
		usuarioRolDTO.setNombreUnidad(usuarioRol.getUnidad().getCodigoUnidad());
	}
	
	@Override
	public void actualizaFueraDeOficina(UsuarioRolDTO usuarioRolDTO) {
		List<UsuarioRol> usuarioRolList = getUsuarioRolesPorIdUsuario(usuarioRolDTO.getIdUsuario());
		for (UsuarioRol usuarioRol : usuarioRolList) {
			usuarioRol.setFueraDeOficina(usuarioRolDTO.getFueraDeOficina());
		}
		LogFueraDeOficina logFueraDeOficina = new LogFueraDeOficina();
		logFueraDeOficina.setFechaActualizacion(new Date());
		logFueraDeOficina.setFueraDeOficina(usuarioRolDTO.getFueraDeOficina());
		logFueraDeOficina.setIdUsuario(usuarioRolDTO.getIdUsuario());
		usuarioRolDao.insertLogFueraDeOficina(logFueraDeOficina);
	}

	@Override
	public List<String> getTodosLosIdUsuariosPorVigencia(boolean vigente) {	
		log.debug("Inicio getTodosLosIdUsuariosPorVigencia");
		ParametroDTO parametroDTOUsuarioExcluidos = parametroService.getParametroPorID(Constantes.ID_PARAM_USUSARIOS_EXCLUIDOS);
		String usuariosExcluidos = parametroDTOUsuarioExcluidos.getValorParametroChar();
		//List<String> todosLosUsuarios = usuarioRolDao.getTodosLosIdUsuariosPorVigencia(vigente);
		List<String> todosLosUsuarios = usuarioRolDao.getTodosLosIdUsuariosPorVigenciaOrdenadoPorUnidadYNombre(vigente);
		log.debug("todosLosUsuarios.size(): " + todosLosUsuarios.size());
		List<String> respuestaTodosLosUsuarios = new ArrayList<String>();
		HashSet<String> lookup = new HashSet<String>();
		for (String usuario :todosLosUsuarios ) {
			if (!usuariosExcluidos.contains(usuario) && lookup.add(usuario)) {
				respuestaTodosLosUsuarios.add(usuario);
			}
		}
		return respuestaTodosLosUsuarios;
	}

	@Override
	public List<UsuarioRolDTO> getTodosLosUsuariosAsignadosPorIdRol(Long rolId) {
		
		List<UsuarioRolDTO> us = new ArrayList<UsuarioRolDTO>();	
		for (UsuarioRol u : usuarioRolDao.getTodosLosUsuariosAsignadosPorIdRol(rolId)) {
			UsuarioRolDTO rolDTO = new UsuarioRolDTO(u.getIdUsuarioRol(), u.getIdUsuario(), u.getActivo(), u.getFueraDeOficina(), u.getRol().getIdRol(), u.getRol().getNombreRol(), 0l,"");
			us.add(rolDTO);
		}
		return us;
	}

	@Override
	public List<UsuarioRolDTO> getTodosLosUsuariosActivos() {
		List<UsuarioRolDTO> us = new ArrayList<UsuarioRolDTO>();	
		for (UsuarioRol u : usuarioRolDao.getTodosLosUsuariosActivos()) {
			UsuarioRolDTO rolDTO = new UsuarioRolDTO(u.getIdUsuarioRol(), u.getIdUsuario(), u.getActivo(), u.getFueraDeOficina(), u.getRol().getIdRol(), u.getRol().getNombreRol(), 0l,"");
			us.add(rolDTO);
		}
		return us;
	}

	@Override
	public UsuariosPorRolDTO getUsuariosPorRol(Long idRol) {
		List<UsuarioRolDTO> todos = getTodosLosUsuariosActivos();
		List<UsuarioRolDTO> asignados = getTodosLosUsuariosAsignadosPorIdRol(idRol);
		List<UsuarioRolDTO> noAsignados = new ArrayList<UsuarioRolDTO>();
		
		for(UsuarioRolDTO usTodo : todos ) {
			boolean asignado = false;
			for(UsuarioRolDTO usAsignado : asignados) {
				if (usAsignado.getIdUsuario().equals(usTodo.getIdUsuario())) {
					asignado = true;
					break;
				}
			}
			if(!asignado) {
				noAsignados.add(usTodo);
			}
		}
				
		return new UsuariosPorRolDTO(asignados, noAsignados, idRol);
	}
	
	@Override	
	public List<UsuarioRolDTO> getAll() {	
		log.debug("Inicio getAll");
		List<UsuarioRol> todosLosUsuarioRoles = usuarioRolDao.getTodosLosUsuarios();
		log.debug("Fin getTodosLosIdUsuariosPorVigencia");
		List<UsuarioRolDTO> todosLosUsuarioRolesDTO = new ArrayList<UsuarioRolDTO>();
		for (UsuarioRol usuarioRol : todosLosUsuarioRoles) {
			UsuarioRolDTO usuarioRolDTO = new UsuarioRolDTO();
			BeanUtils.copyProperties(usuarioRol, usuarioRolDTO);
			usuarioRolDTO.setNombreRol(usuarioRol.getRol().getNombreRol());
			usuarioRolDTO.setNombreUnidad(usuarioRol.getUnidad().getNombreCompletoUnidad());
			todosLosUsuarioRolesDTO.add(usuarioRolDTO);
		}
		return todosLosUsuarioRolesDTO;
	}
	
	public UsuarioRolDTO creaUsuarioRol(UsuarioRolDTO usuarioRolDTO) {		
		UsuarioRol usuarioRolValidaRol = usuarioRolDao.getUsuarioRolPorIdUsuarioIdRol(usuarioRolDTO.getIdUsuario(), usuarioRolDTO.getIdRol());		
		if (usuarioRolValidaRol!=null) {
			usuarioRolDTO.setEstado("ERROR");
			usuarioRolDTO.setGlosa("El usuario ya existe en el cargo seleccionado");
			return usuarioRolDTO;			
		}		
		Unidad unidad = unidadDao.find(usuarioRolDTO.getIdUnidad());
		if (unidad == null) {
			usuarioRolDTO.setEstado("ERROR");
			usuarioRolDTO.setGlosa("No existe la unidad con id: " + usuarioRolDTO.getIdUnidad());
			return usuarioRolDTO;
		}
		Rol rol = rolDao.getRolPorIdRol(usuarioRolDTO.getIdRol());
		if (rol == null) {
			usuarioRolDTO.setEstado("ERROR");
			usuarioRolDTO.setGlosa("No existe el rol con id: " + usuarioRolDTO.getIdRol());
			return usuarioRolDTO;
		}		
		if (!usuarioRolDTO.getRut().isEmpty() && usuarioRolDTO.getRut() != null && !usuarioRolDTO.getRut().equals("null") ) {
			if (!SGDPUtil.validaRut(usuarioRolDTO.getRut())) {
				usuarioRolDTO.setEstado("ERROR");
				usuarioRolDTO.setGlosa("Rut invalido: " + usuarioRolDTO.getRut());
				return usuarioRolDTO;
			}			
		}	
		UsuarioRol usuarioRol = new UsuarioRol();
		usuarioRol.setActivo(true);
		usuarioRol.setIdUsuario(usuarioRolDTO.getIdUsuario());
		usuarioRol.setNombreCompleto(usuarioRolDTO.getNombreCompleto() != null ? usuarioRolDTO.getNombreCompleto() :null);
		if (usuarioRolDTO.getRut() != null && usuarioRolDTO.getRut().contains("-")) {
			usuarioRol.setRut(usuarioRolDTO.getRut().split("-")[0]);
			usuarioRol.setDv(usuarioRolDTO.getRut().split("-")[1]);
		} else {
			usuarioRol.setRut(usuarioRolDTO.getRut() != null ? usuarioRolDTO.getRut() :null);
		}
		usuarioRol.setUnidad(unidad);
		usuarioRol.setRol(rol);
		usuarioRolDao.insert(usuarioRol);
		usuarioRolDTO.setEstado("OK");
		return usuarioRolDTO;
	}
	
	@Override	
	public UsuarioRolDTO getUsuarioRolDTOPorIdUsuarioRol(long idUsuarioRol) {		
		UsuarioRol usuarioRol = usuarioRolDao.getUsuarioRolPorIdUsuarioRol(idUsuarioRol);
		UsuarioRolDTO usuarioRolDTO = new UsuarioRolDTO();
		BeanUtils.copyProperties(usuarioRol, usuarioRolDTO);
		usuarioRolDTO.setIdUnidad(usuarioRol.getUnidad().getIdUnidad());
		usuarioRolDTO.setIdRol(usuarioRol.getRol().getIdRol());
		return usuarioRolDTO;
	}
	
	@Override
	public UsuarioRolDTO actualizaUsuario(UsuarioRolDTO usuarioRolDTO) {
		UsuarioRol usuarioRol = usuarioRolDao.getUsuarioRolPorIdUsuarioRol(usuarioRolDTO.getIdUsuarioRol());
		if (!usuarioRolDTO.getIdUsuario().equals(usuarioRol.getIdUsuario())) {	
			usuarioRolDTO.setEstado("ERROR");
			usuarioRolDTO.setGlosa("No es posible actualizar el id del usuario");
			return usuarioRolDTO;
		}		
		UsuarioRol usuarioRolValidaRol = usuarioRolDao.getUsuarioRolPorIdUsuarioIdRol(usuarioRolDTO.getIdUsuario(), usuarioRolDTO.getIdRol());		
		if (usuarioRolValidaRol!=null && usuarioRolDTO.getIdUsuarioRol()!=usuarioRolValidaRol.getIdUsuarioRol()) {
			usuarioRolDTO.setEstado("ERROR");
			usuarioRolDTO.setGlosa("El usuario ya existe en el cargo seleccionado");
			return usuarioRolDTO;			
		}				
		Unidad unidad = unidadDao.find(usuarioRolDTO.getIdUnidad());
		if (unidad == null) {
			usuarioRolDTO.setEstado("ERROR");
			usuarioRolDTO.setGlosa("No existe la unidad con id: " + usuarioRolDTO.getIdUnidad());
			return usuarioRolDTO;
		}
		Rol rol = rolDao.getRolPorIdRol(usuarioRolDTO.getIdRol());
		if (rol == null) {
			usuarioRolDTO.setEstado("ERROR");
			usuarioRolDTO.setGlosa("No existe el rol con id: " + usuarioRolDTO.getIdRol());
			return usuarioRolDTO;
		}
		if (!usuarioRolDTO.getRut().isEmpty() && usuarioRolDTO.getRut() != null && !usuarioRolDTO.getRut().equals("null") ) {
			if (!SGDPUtil.validaRut(usuarioRolDTO.getRut())) {
				usuarioRolDTO.setEstado("ERROR");
				usuarioRolDTO.setGlosa("Rut invalido: " + usuarioRolDTO.getRut());
				return usuarioRolDTO;
			}	
		}		
		if (usuarioRolDTO.getFueraDeOficina()!=usuarioRol.getFueraDeOficina()) {
			actualizaFueraDeOficina(usuarioRolDTO);	
			actualizaFueraDeOficinaNoActivo(usuarioRolDTO);
		}	
		usuarioRol.setNombreCompleto(usuarioRolDTO.getNombreCompleto() != null ? usuarioRolDTO.getNombreCompleto() :null);		
		if (usuarioRolDTO.getRut() != null && usuarioRolDTO.getRut().contains("-")) {
			usuarioRol.setRut(usuarioRolDTO.getRut().split("-")[0]);
			usuarioRol.setDv(usuarioRolDTO.getRut().split("-")[1]);
		} else {
			usuarioRol.setRut(usuarioRolDTO.getRut() != null ? usuarioRolDTO.getRut() :null);
		}
		usuarioRol.setActivo(usuarioRolDTO.isActivo());
		usuarioRol.setUnidad(unidad);
		usuarioRol.setRol(rol);
		usuarioRol.setActivo(usuarioRolDTO.isActivo());
		usuarioRolDTO.setEstado("OK");
		return usuarioRolDTO;		
	}	
	
	public void actualizaFueraDeOficinaNoActivo(UsuarioRolDTO usuarioRolDTO) {
		List<UsuarioRol> usuarioRolList = usuarioRolDao.getUsuarioRolesPorIdUsuario(usuarioRolDTO.getIdUsuario(), false);
		for (UsuarioRol usuarioRol : usuarioRolList) {
			usuarioRol.setFueraDeOficina(usuarioRolDTO.getFueraDeOficina());
		}
		LogFueraDeOficina logFueraDeOficina = new LogFueraDeOficina();
		logFueraDeOficina.setFechaActualizacion(new Date());
		logFueraDeOficina.setFueraDeOficina(usuarioRolDTO.getFueraDeOficina());
		logFueraDeOficina.setIdUsuario(usuarioRolDTO.getIdUsuario());
		usuarioRolDao.insertLogFueraDeOficina(logFueraDeOficina);
	}
	
}