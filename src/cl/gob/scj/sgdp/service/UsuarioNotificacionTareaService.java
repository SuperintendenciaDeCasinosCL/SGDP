package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.UsuarioNotificacionTareaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.UsuarioNotificacionTarea;

@Service
public interface UsuarioNotificacionTareaService {
	
	void insertUsuarioNotificacionTarea(List<String> idUsuariosNotificacionTarea, Usuario usuario, long idTarea) throws SgdpException;
	
	void insertUsuarioNotificacionTarea(UsuarioNotificacionTareaDTO usuarioNotificacionTareaDTO, Usuario usuario) throws SgdpException;
	
	List<UsuarioNotificacionTareaDTO> getUsuariosNotificacionTareaPorIdTarea(long idTarea, Usuario usuario) throws SgdpException;
	
	List<UsuarioNotificacionTareaDTO> getUsuariosNotificacionTareaPorIdTareaOnOut(long idTarea, Usuario usuario) throws SgdpException;

}
