package cl.gob.scj.sgdp.ws.alfresco.rest.client;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ArchivoInfoDTO;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.ObtenerArchivosExpedienteResponse;

@Service
public interface ObtenerArchivosExpedienteCMSService {

	public ObtenerArchivosExpedienteResponse obtenerArchivosExpediente(Usuario usuario, String idExpediente) throws Exception;
	
}
