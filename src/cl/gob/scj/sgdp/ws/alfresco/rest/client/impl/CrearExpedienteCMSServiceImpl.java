package cl.gob.scj.sgdp.ws.alfresco.rest.client.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.ExpedienteDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.CrearExpedienteCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.CrearExpedienteResponse;

@Service
public class CrearExpedienteCMSServiceImpl implements CrearExpedienteCMSService {

	private static final Logger log = Logger.getLogger(CrearExpedienteCMSServiceImpl.class);

	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@Override
	public String crearExpediente(Usuario usuario, ExpedienteDTO expedienteDTO) throws Exception {
		
		log.debug("crearExpediente... inicio");
		log.debug(expedienteDTO.toString());
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_CREAR_EXP);
		String serviceRestURLCrearExpediente = parametroDTO.getValorParametroChar();
		
		Map<String, String> parametrosURL = new HashMap<String, String>();	
	
		parametrosURL.put("creador", expedienteDTO.getCreador());
		parametrosURL.put("materia", expedienteDTO.getMateria());
		parametrosURL.put("autor", expedienteDTO.getNombreAutor());
		parametrosURL.put("perspectiva", expedienteDTO.getNombrePerpectiva());
		parametrosURL.put("proceso", expedienteDTO.getNombreMacroProceso());
		parametrosURL.put("subproceso", expedienteDTO.getNombreProceso());
		parametrosURL.put("nombExp", expedienteDTO.getNombreExpediente());
		parametrosURL.put("esConfidencial", expedienteDTO.getEsConfidencial());
		
		log.debug("alfrescoRestURLCrearExpediente: " + serviceRestURLCrearExpediente);
		
		String requestJson = "";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);		
		
		try {
			autenticacionService.validaSesion(usuario);
			log.debug("usuario.getAlfTicket(): " + usuario.getAlfTicket());
			parametrosURL.put("alf_ticket", usuario.getAlfTicket());
			CrearExpedienteResponse crearExpedienteResponse = restTemplate.postForObject(serviceRestURLCrearExpediente, entity, CrearExpedienteResponse.class, parametrosURL);
			log.debug("crearExpedienteResponse.getIdExpediente(): " + crearExpedienteResponse.getIdExpediente());  
			log.debug("crearExpediente... fin");	
			return crearExpedienteResponse.getIdExpediente();
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + status.value());
			log.error(e);
			log.error("crearExpediente... fin");			
			throw e;
		}		
			
	}

}
