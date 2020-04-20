package cl.gob.scj.sgdp.ws.alfresco.rest.client.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.RespuestaActualizaMetaDataExpedienteDTO;
import cl.gob.scj.sgdp.dto.rest.ExpedienteRestActMetaDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.GestorMetadataCMSService;

@Service
public class GestorMetadataCMSServiceImpl implements GestorMetadataCMSService {

	private static final Logger log = Logger.getLogger(GestorMetadataCMSServiceImpl.class);
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@Override
	public void agregarComentarioANodo(Usuario usuario, String idNodo, String comentario)
			throws Exception {
		
		log.debug("agregarComentarioANodo... inicio");		
			
		comentario = comentario.replaceAll("\\p{Cntrl}", " ").replaceAll("( )+", " ");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_AGREGAR_COMENTARIO_A_NODO);
		String serviceRestURLAgregarComentario = parametroDTO.getValorParametroChar();
		
		log.debug("serviceRestURLAgregarComentario: " + serviceRestURLAgregarComentario);
		
		String requestJson = "{ \"title\" : \"\", \"content\" : \"" + comentario + "\"}";
	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		
		Map<String, String> parametrosURL = new HashMap<String, String>();	
	
		parametrosURL.put("id", idNodo);
		
		try {
			autenticacionService.validaSesion(usuario);
			parametrosURL.put(Constantes.NOMBRE_PARAMETRO_ALF_TICKET, usuario.getAlfTicket());
			restTemplate.postForObject(serviceRestURLAgregarComentario, entity, String.class, parametrosURL);
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + status.value());
			log.error(e);
			log.error("agregarComentarioANodo... fin");	
			throw e;
		}

	}

	@Override
	public RespuestaActualizaMetaDataExpedienteDTO actualizaUsuariosQueHanParticipadoExpediente(Usuario usuario, String idExpediente, String idUsuarioQueParticipa) throws Exception {
		log.debug("actualizaUsuariosQueHanModificadoExpediente... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
			
		StringBuilder serviceRestURLActualizarMetadataExpediente = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_ACT_META_DATA_EXP).getValorParametroChar());
		serviceRestURLActualizarMetadataExpediente.append("?alf_ticket=");
		
		//Se carga con el prefijo 
		StringBuilder idExpedienteB = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_PREFIJO_WP_ST).getValorParametroChar());
		
		idExpedienteB.append(idExpediente);
		
		log.debug(idExpedienteB.toString());
		
		LinkedMultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();		
		mvm.add("idExpediente", idExpedienteB.toString());
		mvm.add("idUsuarioQueParticipa", idUsuarioQueParticipa);
		
		log.debug("idExpediente: " + idExpediente);
		log.debug("idUsuarioQueParticipa: " + idUsuarioQueParticipa);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<LinkedMultiValueMap<String, Object>>(mvm, headers);
		
		try {
			autenticacionService.validaSesion(usuario);	
			serviceRestURLActualizarMetadataExpediente.append(usuario.getAlfTicket());
			log.debug("serviceRestURLActualizarMetadataExpediente: " + serviceRestURLActualizarMetadataExpediente);
			ResponseEntity<RespuestaActualizaMetaDataExpedienteDTO> actualizaMetaDataExpedienteResponse = restTemplate.postForEntity(serviceRestURLActualizarMetadataExpediente.toString(), request , RespuestaActualizaMetaDataExpedienteDTO.class);
			log.debug(actualizaMetaDataExpedienteResponse.toString());
			return actualizaMetaDataExpedienteResponse.getBody();
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + "/" + status.value());
			log.error(e);
			log.error("actualizaUsuariosQueHanParticipadoExpediente... fin");	
			throw e;
		}
		
	}

	@Override
	public RespuestaActualizaMetaDataExpedienteDTO actualizaExpedientesAntecesores(Usuario usuario, String idExpediente, String nombreExpedienteAntecesor) throws Exception {
		
		log.debug("actualizaExpedientesAntecesores... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		StringBuilder serviceRestURLActualizarMetadataExpediente = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_ACT_META_DATA_EXP).getValorParametroChar());
		serviceRestURLActualizarMetadataExpediente.append("?alf_ticket=");
		
		//Se carga con el prefijo 
		StringBuilder idExpedienteB = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_PREFIJO_WP_ST).getValorParametroChar());
		
		idExpedienteB.append(idExpediente);
		
		log.debug(idExpedienteB.toString());
		
		LinkedMultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();		
		mvm.add("idExpediente", idExpedienteB.toString());
		mvm.add("expedienteAntecesor", nombreExpedienteAntecesor);
		
		log.debug("idExpediente: " + idExpediente);
		log.debug("nombreExpedienteAntecesor: " + nombreExpedienteAntecesor);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<LinkedMultiValueMap<String, Object>>(mvm, headers);
		
		try {
			autenticacionService.validaSesion(usuario);	
			serviceRestURLActualizarMetadataExpediente.append(usuario.getAlfTicket());
			log.debug("serviceRestURLActualizarMetadataExpediente: " + serviceRestURLActualizarMetadataExpediente);
			ResponseEntity<RespuestaActualizaMetaDataExpedienteDTO> actualizaMetaDataExpedienteResponse = restTemplate.postForEntity(serviceRestURLActualizarMetadataExpediente.toString(), request , RespuestaActualizaMetaDataExpedienteDTO.class);
			log.debug(actualizaMetaDataExpedienteResponse.toString());
			return actualizaMetaDataExpedienteResponse.getBody();
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + "/" + status.value());
			log.error(e);
			log.error("actualizaExpedientesAntecesores... fin");	
			throw e;
		}
		
	}
	
	@Override
	public RespuestaActualizaMetaDataExpedienteDTO remueveExpedientesAntecesor(Usuario usuario, String idExpediente, String nombreExpedienteAntecesor) throws Exception {
		
		log.debug("actualizaExpedientesAntecesores... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		StringBuilder serviceRestURLActualizarMetadataExpediente = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_ACT_META_DATA_EXP).getValorParametroChar());
		serviceRestURLActualizarMetadataExpediente.append("?alf_ticket=");
		
		//Se carga con el prefijo 
		StringBuilder idExpedienteB = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_PREFIJO_WP_ST).getValorParametroChar());
		
		idExpedienteB.append(idExpediente);
		
		log.debug(idExpedienteB.toString());
		
		LinkedMultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();		
		mvm.add("idExpediente", idExpedienteB.toString());
		mvm.add("expedienteAntecesorARemover", nombreExpedienteAntecesor);
		
		log.debug("idExpediente: " + idExpediente);
		log.debug("nombreExpedienteAntecesor: " + nombreExpedienteAntecesor);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<LinkedMultiValueMap<String, Object>>(mvm, headers);
		
		try {
			autenticacionService.validaSesion(usuario);	
			serviceRestURLActualizarMetadataExpediente.append(usuario.getAlfTicket());
			log.debug("serviceRestURLActualizarMetadataExpediente: " + serviceRestURLActualizarMetadataExpediente);
			ResponseEntity<RespuestaActualizaMetaDataExpedienteDTO> actualizaMetaDataExpedienteResponse = restTemplate.postForEntity(serviceRestURLActualizarMetadataExpediente.toString(), request , RespuestaActualizaMetaDataExpedienteDTO.class);
			log.debug(actualizaMetaDataExpedienteResponse.toString());
			return actualizaMetaDataExpedienteResponse.getBody();
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + "/" + status.value());
			log.error(e);
			log.error("actualizaExpedientesAntecesores... fin");	
			throw e;
		}		
		
	}
	
	
	@Override
	public RespuestaActualizaMetaDataExpedienteDTO actualizaMetaDataExpediente(Usuario usuario, ExpedienteRestActMetaDTO expedienteRestActMetaDTO) throws Exception {
		log.debug("actualizaMetaDataExpediente... inicio");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
			
		//StringBuilder serviceRestURLActualizarMetadataExpediente = new StringBuilder("http://172.16.10.243:8080/alfresco/s/scj/actualizarMetadataExpediente2");//new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_ACT_META_DATA_EXP).getValorParametroChar());
		StringBuilder serviceRestURLActualizarMetadataExpediente = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_ACT_META_DATA_EXP).getValorParametroChar());
		
		serviceRestURLActualizarMetadataExpediente.append("?alf_ticket=");
		
		//Se carga con el prefijo 
		StringBuilder idExpedienteB = new StringBuilder(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_PREFIJO_WP_ST).getValorParametroChar());
		
		idExpedienteB.append(expedienteRestActMetaDTO.getIdExpediente());
		
		log.debug("idExpedienteB.toString(): " + idExpedienteB.toString());
		
		LinkedMultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();	
		
		mvm.add("idExpediente", idExpedienteB.toString());
		
		if (expedienteRestActMetaDTO.getIdUsuarioQueParticipa()!=null && !expedienteRestActMetaDTO.getIdUsuarioQueParticipa().isEmpty()) {
			mvm.add("idUsuarioQueParticipa", expedienteRestActMetaDTO.getIdUsuarioQueParticipa());
		}
	
		if (expedienteRestActMetaDTO.getAutor()!=null && !expedienteRestActMetaDTO.getAutor().isEmpty()) {
			mvm.add("autor", URLEncoder.encode(expedienteRestActMetaDTO.getAutor(), "UTF-8") );
		}
		
		if (expedienteRestActMetaDTO.getMateria()!=null && !expedienteRestActMetaDTO.getMateria().isEmpty()) {
			mvm.add("materia", URLEncoder.encode(expedienteRestActMetaDTO.getMateria(), "UTF-8"));
		}
			
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<LinkedMultiValueMap<String, Object>> request = new HttpEntity<LinkedMultiValueMap<String, Object>>(mvm, headers);
		
		try {
			autenticacionService.validaSesion(usuario);	
			serviceRestURLActualizarMetadataExpediente.append(usuario.getAlfTicket());			
			//serviceRestURLActualizarMetadataExpediente.append("TICKET_fd467cc3a12277a5d4cfb8bc6dbd1144d5a213d0");
			log.debug("serviceRestURLActualizarMetadataExpediente: " + serviceRestURLActualizarMetadataExpediente);
			ResponseEntity<RespuestaActualizaMetaDataExpedienteDTO> actualizaMetaDataExpedienteResponse = restTemplate.postForEntity(serviceRestURLActualizarMetadataExpediente.toString(), request , RespuestaActualizaMetaDataExpedienteDTO.class);
			log.debug(actualizaMetaDataExpedienteResponse.toString());
			return actualizaMetaDataExpedienteResponse.getBody();
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + "/" + status.value());
			log.error(e);
			log.error("actualizaMetaDataExpediente... fin");	
			throw e;
		}
		
	}
	

}
