package cl.gob.scj.sgdp.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.UsuarioNotificacionTareaDao;
import cl.gob.scj.sgdp.dto.UsuarioNotificacionTareaDTO;
import cl.gob.scj.sgdp.dto.UsuariosAsignadosDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.UsuarioNotificacionTarea;
import cl.gob.scj.sgdp.service.NotificacionTareaService;
import cl.gob.scj.sgdp.service.UsuarioNotificacionTareaService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class NotificacionTareaServiceImpl implements NotificacionTareaService {

	private static final Logger log = Logger.getLogger(MueveProcesoServiceImpl.class);
	
	@Autowired
	private UsuarioNotificacionTareaService usuarioNotificacionTareaService;

	@Autowired
	private UsuarioNotificacionTareaDao usuarioNotificacionTareaDao;
	
	@Override
	public String guardarUsuariosAsignadosNotificacion(Usuario usuario,
			List<UsuariosAsignadosDTO> listaUsuariosAsignadosDTO) {
		
		try {
			for (UsuariosAsignadosDTO usuariosAsignadosDTO : listaUsuariosAsignadosDTO) {
				if(usuariosAsignadosDTO.getAsignado().equals("si")){
							UsuarioNotificacionTarea usuarioNotificacionTarea =  
							usuarioNotificacionTareaDao.getUsuariosNotificacionTareaPorIdTareaYUsuario(usuariosAsignadosDTO.getIdTarea(), usuariosAsignadosDTO.getIdUsuario());			
							
							if (usuarioNotificacionTarea == null){
								UsuarioNotificacionTareaDTO usuarioNotificacionTareaDTO = new UsuarioNotificacionTareaDTO();
								usuarioNotificacionTareaDTO.setIdTarea(Long.valueOf(usuariosAsignadosDTO.getIdTarea()));
								usuarioNotificacionTareaDTO.setIdUsuario(usuariosAsignadosDTO.getIdUsuario());						
								usuarioNotificacionTareaService.insertUsuarioNotificacionTarea(usuarioNotificacionTareaDTO, usuario);
							}
					
					
				}else if(usuariosAsignadosDTO.getAsignado().equals("no")){
						UsuarioNotificacionTarea usuarioNotificacionTarea =  
								usuarioNotificacionTareaDao.getUsuariosNotificacionTareaPorIdTareaYUsuario(usuariosAsignadosDTO.getIdTarea(), usuariosAsignadosDTO.getIdUsuario());	
						
						if (usuarioNotificacionTarea != null){
							usuarioNotificacionTareaDao.eliminarUsuarioNotificacionTarea(usuarioNotificacionTarea);
						}
					
				}
			
			}
			
			return "OK";
		} catch (SgdpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR";
		}
		
		 
		
		
	}
		


}
