package cl.gob.scj.sgdp.ws.alfresco.rest.client;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.SubirDocumentoDTO;

@Service
public interface SubirDocumentoCMSService {
	
	public String subirDocumento(Usuario usuario, SubirDocumentoDTO subirDocumentoDTO) throws Exception;

}
