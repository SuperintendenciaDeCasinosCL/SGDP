package cl.gob.scj.sgdp.ws.alfresco.rest.client;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ExpedienteDTO;

@Service
public interface CrearExpedienteCMSService {
	
	public String crearExpediente(Usuario usuario, ExpedienteDTO expedienteDTO) throws Exception ;

}
