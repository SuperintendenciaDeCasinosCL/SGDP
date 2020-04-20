package cl.gob.scj.sgdp.ws.email.rest.client;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import cl.gob.scj.sgdp.dto.EnviarMailDTO;
import cl.gob.scj.sgdp.dto.RespuestaMailDTO;

@Service
public interface EmailServiceRest {
	
	RespuestaMailDTO enviarMail(EnviarMailDTO enviarMailDTO) throws JsonProcessingException;

}
