package cl.gob.scj.sgdp.ws.alfresco.rest.client.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.ObtenerArchivosExpedienteCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.ObtenerArchivosExpedienteResponse;

@Service
public class ObtenerArchivosExpedienteCMSServiceImpl implements
		ObtenerArchivosExpedienteCMSService {

	private static final Logger log = Logger.getLogger(ObtenerArchivosExpedienteCMSServiceImpl.class);

	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@Override
	public ObtenerArchivosExpedienteResponse obtenerArchivosExpediente(Usuario usuario,
			String idExpediente) throws Exception {
		
		log.debug("obtenerArchivosExpediente... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_OBTENER_ARCHIVOS_EXPEDIENTE);
		String serviceRestURLObtenerArchivosExpediente = parametroDTO.getValorParametroChar();	
		
		//Se carga con el prefijo
		StringBuilder idExpedienteSB = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_PREFIJO_WP_ST).getValorParametroChar());
		idExpedienteSB.append(idExpediente);
		
		Map<String, String> parametrosURL = new HashMap<String, String>();
		
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ID_EXPEDIENTE, idExpedienteSB.toString());		
		
		log.debug("serviceRestURLObtenerArchivosExpediente: " + serviceRestURLObtenerArchivosExpediente);		
		log.debug("idExpediente: " + idExpedienteSB.toString());
		
		try {
			autenticacionService.validaSesion(usuario);
			parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
			log.debug("alf_ticket: " + usuario.getAlfTicket());
			ObtenerArchivosExpedienteResponse obtenerArchivosExpedienteResponse = restTemplate.getForObject(serviceRestURLObtenerArchivosExpediente, ObtenerArchivosExpedienteResponse.class, parametrosURL);
			log.debug("obtenerArchivosExpediente... fin");	
			return obtenerArchivosExpedienteResponse;
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + status.value());
			log.error(e);
			log.error("obtenerArchivosExpediente... fin");	
			throw e;
		}
	}

}
