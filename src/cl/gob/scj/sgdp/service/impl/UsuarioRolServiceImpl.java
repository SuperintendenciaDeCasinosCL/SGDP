package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.UsuarioRolDao;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.dto.UsuarioRolDTO;
import cl.gob.scj.sgdp.model.LogFueraDeOficina;
import cl.gob.scj.sgdp.model.UsuarioRol;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.UsuarioRolService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UsuarioRolServiceImpl implements UsuarioRolService {
	
	private static final Logger log = Logger.getLogger(UsuarioRolServiceImpl.class);

	@Autowired 
	private UsuarioRolDao usuarioRolDao;
	
	@Autowired
	private ParametroService parametroService;
		
	@Override	
	public List<UsuarioRol> getUsuarioRolesPorIdUsuario(String idUsuario) {		
		return usuarioRolDao.getUsuarioRolesPorIdUsuario(idUsuario, true);
	}

	@Override
	public List<RolDTO> getRolesUsuarioPorIdUsuario(String idUsuario, List<RolDTO> roles) {	
		List<UsuarioRol> usuarioRoles = usuarioRolDao.getUsuarioRolesPorIdUsuario(idUsuario, true);	
		for (UsuarioRol usuarioRol : usuarioRoles) {
			RolDTO rolDTO = new RolDTO(usuarioRol.getRol().getIdRol(), usuarioRol.getRol().getNombreRol());
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
	
}
