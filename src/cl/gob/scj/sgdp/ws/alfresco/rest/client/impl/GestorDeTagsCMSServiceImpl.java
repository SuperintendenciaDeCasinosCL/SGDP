package cl.gob.scj.sgdp.ws.alfresco.rest.client.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.interceptor.RequestLoggingInterceptor;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.GestorDeTagsCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.AgregaRemueveTagDeObjetoResponse;

@Service
public class GestorDeTagsCMSServiceImpl implements
		GestorDeTagsCMSService {

	private static final Logger log = Logger.getLogger(GestorDeTagsCMSServiceImpl.class);
	
	@Autowired
	private ParametroService parametroService;	
	
	@Autowired
	private AutenticacionService autenticacionService;

	@Override
	public List<String> obtenerTodosLosTags(Usuario usuario) throws Exception {
		log.debug("obtenerTodosLosTags... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_OBTENER_TODOS_LOS_TAGS);
		String serviceRestURLObtenerTodosLosTags = parametroDTO.getValorParametroChar();
		
		Map<String, String> parametrosURL = new HashMap<String, String>();
		
		try {			
			autenticacionService.validaSesion(usuario);
			log.error(usuario.getAlfTicket());
			log.error(Constantes.NOMBRE_PARAMETRO_ALF_TICKET);
			log.error(parametrosURL);
			parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
			List<ClientHttpRequestInterceptor> listClientRequestInterceptor = restTemplate.getInterceptors();
			for (ClientHttpRequestInterceptor clientHttpRequestInterceptor : listClientRequestInterceptor) {
				if (clientHttpRequestInterceptor instanceof RequestLoggingInterceptor) {
					RequestLoggingInterceptor requestLoggingInterceptor = (RequestLoggingInterceptor) clientHttpRequestInterceptor;
					log.error("requestLoggingInterceptor.getAlfTicketCookie(): " + requestLoggingInterceptor.getAlfTicketCookie());
				}				
			}
			@SuppressWarnings("unchecked")
			List<String> listaDeTags = restTemplate.getForObject(serviceRestURLObtenerTodosLosTags, List.class, parametrosURL);
			log.debug("obtenerTodosLosTags... fin");
			return listaDeTags;
		} catch(HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + status.value());
			log.error(e);
			log.error("obtenerTodosLosTags... fin");	
			throw e;
		}
		
	}
	
	@Override
	public AgregaRemueveTagDeObjetoResponse agregaRemueveTagDeObjeto(Usuario usuario, String idObjeto, String tag, String accion) throws Exception {
		log.debug("agregaRemueveTagDeObjeto... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_AGREGAR_REMOVER_TAG_DE_OBJETO);
		String serviceRestURLAgregaRemueveTagDeObjeto = parametroDTO.getValorParametroChar();
		
		Map<String, String> parametrosURL = new HashMap<String, String>();
		
		log.debug("Constantes.NOMBRE_PARAMETRO_ALF_TICKET: " + Constantes.NOMBRE_PARAMETRO_ALF_TICKET);
		log.debug("Constantes.NOMBRE_PARAMETRO_ACCION_AGREGAR_TAG_OBJETO: " + Constantes.NOMBRE_PARAMETRO_ACCION_AGREGAR_TAG_OBJETO);
		log.debug("Constantes.NOMBRE_PARAMETRO_ID_OBJETO_AGREGAR_TAG_OBJETO: " + Constantes.NOMBRE_PARAMETRO_ID_OBJETO_AGREGAR_TAG_OBJETO);
		log.debug("Constantes.NOMBRE_PARAMETRO_TAG_AGREGAR_TAG_OBJETO: " + Constantes.NOMBRE_PARAMETRO_TAG_AGREGAR_TAG_OBJETO);
		
		log.debug("usuario.getAlfTicket(): " + usuario.getAlfTicket());
		log.debug("accion: " + accion);
		log.debug("idObjeto: " + idObjeto);
		log.debug("tag: " + tag);
		
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ACCION_AGREGAR_TAG_OBJETO, accion);
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ID_OBJETO_AGREGAR_TAG_OBJETO, idObjeto);
		parametrosURL.put(Constantes.NOMBRE_PARAMETRO_TAG_AGREGAR_TAG_OBJETO, tag);
		
		String requestJson = "";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		
		try {
			autenticacionService.validaSesion(usuario);
			parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
			AgregaRemueveTagDeObjetoResponse agregaRemueveTagDeObjetoResponse = restTemplate.postForObject(serviceRestURLAgregaRemueveTagDeObjeto, entity, AgregaRemueveTagDeObjetoResponse.class, parametrosURL);
			log.debug("agregaRemueveTagDeObjeto... fin");
			return agregaRemueveTagDeObjetoResponse;			
		} catch(HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + status.value());
			log.error(e);
			log.error("agregaRemueveTagDeObjeto... fin");	
			throw e;
		}		
	}

}
