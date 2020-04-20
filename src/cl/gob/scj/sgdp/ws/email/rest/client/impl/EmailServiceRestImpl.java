package cl.gob.scj.sgdp.ws.email.rest.client.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.EnviarMailDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.RespuestaMailDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.email.rest.client.EmailServiceRest;

@Service
public class EmailServiceRestImpl implements EmailServiceRest{

	private static final Logger log = Logger.getLogger(EmailServiceRestImpl.class);

	@Autowired
	private ParametroService parametroService;	
	
	@Override
	public RespuestaMailDTO enviarMail(EnviarMailDTO enviarMailDTO) throws JsonProcessingException {
		
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		ObjectMapper mapper = SingleObjectFactory.getMapper();
		
		ParametroDTO parametroDTO = parametroService.getParametroPorID(Constantes.ID_PARAM_EMAIL_REST_URL_ENVIAR_CORREO);
		String emailRestUrlEnviarCorreo = parametroDTO.getValorParametroChar();
		
		log.debug("emailRestUrlEnviarCorreo: " + emailRestUrlEnviarCorreo);
		
		String requestJson;
		
		try {
			requestJson = mapper.writeValueAsString(enviarMailDTO);				
			//log.debug(requestJson);
			HttpHeaders headers = new HttpHeaders();
			MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
			headers.setContentType(mediaType);			
			HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);			
			RespuestaMailDTO respuestaMailDTO = restTemplate.postForObject(emailRestUrlEnviarCorreo, entity, RespuestaMailDTO.class);	
			return respuestaMailDTO;
		} catch (JsonProcessingException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);		
			throw e;
		} catch (HttpClientErrorException e) {
			HttpStatus status = e.getStatusCode();
			log.error(status.getReasonPhrase() + " / " + status.value());
			log.error(e);			
			throw e;
		}
	}

}
