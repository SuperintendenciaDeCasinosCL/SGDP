package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import cl.gob.scj.sgdp.auth.user.Usuario;

@Service
public interface LogAccionesUsuarioService {
	
	void guardaLogAccionesUsuario(Usuario usuario, Object data, String nombreAccion, String modulo) throws JsonProcessingException;

}
