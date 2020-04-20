package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.UsuariosAsignadosDTO;

@Service
public interface NotificacionTareaService {

	
	public String guardarUsuariosAsignadosNotificacion(Usuario usuario, List<UsuariosAsignadosDTO> listaUsuariosAsignadosDTO);
}
