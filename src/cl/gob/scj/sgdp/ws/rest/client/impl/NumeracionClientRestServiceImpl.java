package cl.gob.scj.sgdp.ws.rest.client.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.rest.TipoDocumentoDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.rest.client.NumeracionClientRestService;

@Service
public class NumeracionClientRestServiceImpl implements NumeracionClientRestService {
	
	private static final Logger log = Logger.getLogger(NumeracionClientRestServiceImpl.class);
	
	@Autowired
	private ParametroService parametroService;

	@Override
	public TipoDocumentoDTO getTipoDocumentoPorCodTipoDoc(String codTipoDoc) {		
		log.info("codTipoDoc: " + codTipoDoc);
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		String serviceRestURLGetTipoDocumentoPorCodTipoDoc = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_NUM_DOC_TIPO_WS).getValorParametroChar();
		TipoDocumentoDTO tipoDocumentoDTO = restTemplate.getForObject(serviceRestURLGetTipoDocumentoPorCodTipoDoc.replace("{codTipoDoc}", codTipoDoc), TipoDocumentoDTO.class);
		log.info(tipoDocumentoDTO.toString());
		return tipoDocumentoDTO;
		
	}

}
