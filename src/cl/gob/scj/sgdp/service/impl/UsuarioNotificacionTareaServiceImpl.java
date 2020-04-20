package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.TareaDao;
import cl.gob.scj.sgdp.dao.UsuarioNotificacionTareaDao;
import cl.gob.scj.sgdp.dto.UsuarioNotificacionTareaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.UsuarioNotificacionTarea;
import cl.gob.scj.sgdp.service.UsuarioNotificacionTareaService;
import cl.gob.scj.sgdp.service.UsuarioRolService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UsuarioNotificacionTareaServiceImpl implements UsuarioNotificacionTareaService {
	
	private static final Logger log = Logger.getLogger(UsuarioNotificacionTareaServiceImpl.class);

	@Autowired
	private UsuarioNotificacionTareaDao usuarioNotificacionTareaDao;
	
	@Autowired
	private UsuarioRolService usuarioRolService;
	
	@Autowired
	private TareaDao tareaDao;
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Override
	public void insertUsuarioNotificacionTarea(List<String> idUsuariosNotificacionTarea, Usuario usuario, long idTarea) throws SgdpException {
		try {
			Tarea tarea = tareaDao.getTareaPorIdTarea(idTarea);
			for (String idUsuarioNotificacionTarea : idUsuariosNotificacionTarea) {
				UsuarioNotificacionTarea usuarioNotificacionTarea = new UsuarioNotificacionTarea();
				usuarioNotificacionTarea.setDFechaCreacion(new Date());
				usuarioNotificacionTarea.setIdUsuario(idUsuarioNotificacionTarea);	
				usuarioNotificacionTarea.setTarea(tarea);
				usuarioNotificacionTareaDao.insertUsuarioNotificacionTarea(usuarioNotificacionTarea);
			}			
		} catch (Exception e) {
			log.error(e);
			throw new SgdpException(configProps.getProperty("errorAlGuardarNotificaciones"));
		}				
	}
	
	@Override
	public void insertUsuarioNotificacionTarea(UsuarioNotificacionTareaDTO usuarioNotificacionTareaDTO, Usuario usuario) throws SgdpException {
		try {
			Tarea tarea = tareaDao.getTareaPorIdTarea(usuarioNotificacionTareaDTO.getIdTarea());
			UsuarioNotificacionTarea usuarioNotificacionTarea = new UsuarioNotificacionTarea();
			usuarioNotificacionTarea.setDFechaCreacion(new Date());
			usuarioNotificacionTarea.setIdUsuario(usuarioNotificacionTareaDTO.getIdUsuario());	
			usuarioNotificacionTarea.setTarea(tarea);
			usuarioNotificacionTareaDao.insertUsuarioNotificacionTarea(usuarioNotificacionTarea);
		} catch (Exception e) {
			log.error(e);
			throw new SgdpException(configProps.getProperty("errorAlGuardarNotificacion"));
		}
	}

	@Override
	public List<UsuarioNotificacionTareaDTO> getUsuariosNotificacionTareaPorIdTarea(long idTarea, Usuario usuario) throws SgdpException {
		log.debug("Inicio getUsuariosNotificacionTareaPorIdTarea");
		try {
			List<UsuarioNotificacionTarea> usuariosNotificacionTarea =  usuarioNotificacionTareaDao.getUsuariosNotificacionTareaPorIdTarea(idTarea);
			List<UsuarioNotificacionTareaDTO> usuariosNotificacionTareaDTO = new ArrayList<UsuarioNotificacionTareaDTO>();
			for (UsuarioNotificacionTarea usuarioNotificacionTarea : usuariosNotificacionTarea) {			
				log.debug(usuarioNotificacionTarea.toString());
				UsuarioNotificacionTareaDTO usuarioNotificacionTareaDTO = new UsuarioNotificacionTareaDTO(usuarioNotificacionTarea.getDFechaCreacion(), 
						usuarioNotificacionTarea.getIdUsuario(), usuarioNotificacionTarea.getTarea().getIdTarea());
				log.debug(usuarioNotificacionTarea.toString());
				usuariosNotificacionTareaDTO.add(usuarioNotificacionTareaDTO);
			}
			return usuariosNotificacionTareaDTO;
		} catch (Exception e) {
			log.error(e);
			throw new SgdpException("Error al buscar usuarios notificados por tarea");
		}
		
		
	}

	@Override
	public List<UsuarioNotificacionTareaDTO> getUsuariosNotificacionTareaPorIdTareaOnOut(long idTarea, Usuario usuario)
			throws SgdpException {
		UsuarioNotificacionTareaDTO usuarioEstaEnListaUsuariosNotificacionTareaDTO;
		List<UsuarioNotificacionTareaDTO> usuariosNotificacionTareaDTO = getUsuariosNotificacionTareaPorIdTarea(idTarea, usuario);
		List<String> todosLosUsuariosVigentes = usuarioRolService.getTodosLosIdUsuariosPorVigencia(true);
		List<UsuarioNotificacionTareaDTO> usuariosNotificacionTareaDTORespuesta = new ArrayList<UsuarioNotificacionTareaDTO>();
		for (String usr : todosLosUsuariosVigentes) {
			UsuarioNotificacionTareaDTO usuarioNotificacionTareaDTO = new UsuarioNotificacionTareaDTO();
			usuarioEstaEnListaUsuariosNotificacionTareaDTO = usuarioEstaEnListaUsuariosNotificacionTareaDTO(usuariosNotificacionTareaDTO, usr);
			if (usuarioEstaEnListaUsuariosNotificacionTareaDTO!=null) {			
				BeanUtils.copyProperties(usuarioEstaEnListaUsuariosNotificacionTareaDTO, usuarioNotificacionTareaDTO);
			} else {
				usuarioNotificacionTareaDTO.setIdUsuario(usr);
			}
			usuariosNotificacionTareaDTORespuesta.add(usuarioNotificacionTareaDTO);
		}
		return usuariosNotificacionTareaDTORespuesta;
	}
	
	private UsuarioNotificacionTareaDTO usuarioEstaEnListaUsuariosNotificacionTareaDTO(List<UsuarioNotificacionTareaDTO> usuariosNotificacionTareaDTO, String usuario) {
		for (UsuarioNotificacionTareaDTO usuarioNotificacionTareaDTO: usuariosNotificacionTareaDTO) {
			if (usuarioNotificacionTareaDTO.getIdUsuario().equals(usuario)) {
				return usuarioNotificacionTareaDTO;
			}
		}
		return null;
	}

}
