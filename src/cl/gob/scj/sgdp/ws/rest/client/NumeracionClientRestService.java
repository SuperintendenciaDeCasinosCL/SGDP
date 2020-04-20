package cl.gob.scj.sgdp.ws.rest.client;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.rest.TipoDocumentoDTO;

@Service
public interface NumeracionClientRestService {

	TipoDocumentoDTO getTipoDocumentoPorCodTipoDoc( String codTipoDoc);
	
}
