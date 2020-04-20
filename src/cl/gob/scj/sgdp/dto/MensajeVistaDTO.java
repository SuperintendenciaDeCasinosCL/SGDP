package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
public class MensajeVistaDTO implements Serializable  {
	
	private Map<String, String> mensajes;	
	
	public MensajeVistaDTO() {
		super();
		mensajes = new HashMap<String, String>();
	}

	public Map<String, String> getMensajes() {
		return mensajes;
	}


	public void setMensajes(Map<String, String> mensajes) {
		this.mensajes = mensajes;
	}

	@Override
	public String toString() {
		return "MensajeVista [mensajes=" + mensajes + "]";		
	}
	
}
