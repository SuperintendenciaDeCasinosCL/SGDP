package cl.gob.scj.sgdp.ws.alfresco.rest.client;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.SubirArchivoResponse;

@Service
public interface SubirArchivoCMSService {

	public SubirArchivoResponse subirArchivo(Usuario usuario,
			SubirArhivoDTO subirArhivoDTO) throws Exception;
	
}
