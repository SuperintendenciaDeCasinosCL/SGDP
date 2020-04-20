package cl.gob.scj.sgdp.ws.alfresco.rest.client.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.LoginResponse;

@Service
public class AutenticacionServiceImpl implements AutenticacionService {
	
	private static final Logger log = Logger.getLogger(AutenticacionServiceImpl.class);

	@Autowired
	private ParametroService parametroService;	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Override
	public String login(String idUsuario) throws Exception {		
		
		log.debug("Inicio login...");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_LOGIN);
		String alfrescoRestURLLogin = parametroDTO.getValorParametroChar();		
		
		log.debug("alfrescoRestURLLogin: " + alfrescoRestURLLogin);
		
		Map<String, String> parametrosURL = new HashMap<String, String>();
	
		parametrosURL.put("username", idUsuario);
		parametrosURL.put("password", configProps.getProperty("customauthenticator.password"));			
		
		try {				
			
			LoginResponse loginResponse = restTemplate.getForObject(alfrescoRestURLLogin, LoginResponse.class, parametrosURL);				
			
			log.debug("loginResponse.getData().getTicket(): " + loginResponse.getData().getTicket());
			
			return loginResponse.getData().getTicket();
			
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();			
			log.error(status.getReasonPhrase() + "/" + status.value());
			log.error(e);
			throw e;
		}		
	}
	
	@Override
	public void logout(String alfTicket) {
		
		log.info("Inicio logout...");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_LOGOUT);
		String alfrescoRestURLLogout = parametroDTO.getValorParametroChar();
		alfrescoRestURLLogout = alfrescoRestURLLogout.replace("{ticket_logout}", alfTicket);
		
		log.info("alfrescoRestURLLogout: " + alfrescoRestURLLogout);
		
		Map<String, String> parametrosURL = new HashMap<String, String>();		
		
		//parametrosURL.put("ticket_logout", alfTicket);
		
		parametrosURL.put("alf_ticket", alfTicket);	
		
		try {
			restTemplate.delete(alfrescoRestURLLogout, parametrosURL);
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			//log.error(status.getReasonPhrase() + "/" + status.value());	
			//log.error(e);
		}
		log.info("Fin logout...");
		
	}	
	
	public boolean esSesionValida(Usuario usuario) {
		log.debug("Inicio esSesionValida...");
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_REST_URL_VALIDA_SESSION);
		String alfrescoRestURLValidaSesion = parametroDTO.getValorParametroChar();
		log.debug("alfrescoRestURLValidaSesion: " + alfrescoRestURLValidaSesion);
		
		Map<String, String> parametrosURL = new HashMap<String, String>();
		
		parametrosURL.put("ticket_valida", usuario.getAlfTicket());		
		parametrosURL.put("alf_ticket", usuario.getAlfTicket());
		
		log.debug("usuario.getAlfTicket(): " + usuario.getAlfTicket());
		
		try {
			restTemplate.getForObject(alfrescoRestURLValidaSesion, String.class, parametrosURL);			
			return true;
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.warn(status.getReasonPhrase() + " / " + status.value());	
			//log.warn(e);
			return false;
		}
	}
	
	@Override
	public void validaSesion(Usuario usuario) throws Exception {
		
		log.debug("Inicio validaSesion...");
			
		if (!esSesionValida(usuario)) {
			log.debug("La sesion no es valida...");
			log.debug("Obteniendo una nueva sesion...");
			String nuevoAlfTicket = login(usuario.getIdUsuario());
			log.debug("nuevoAlfTicket: " + nuevoAlfTicket);
			usuario.setAlfTicket(nuevoAlfTicket);
		}
		
	}

}
