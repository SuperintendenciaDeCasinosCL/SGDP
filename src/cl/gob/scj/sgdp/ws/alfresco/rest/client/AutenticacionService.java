package cl.gob.scj.sgdp.ws.alfresco.rest.client;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;

@Service
public interface AutenticacionService {
	
	public String login(String idUsuario) throws Exception;
	
	public void logout(String alfTicket);
		
	public void validaSesion(Usuario usuario) throws Exception;

}
