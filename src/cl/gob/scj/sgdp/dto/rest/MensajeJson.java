package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class MensajeJson  implements Serializable {

	private String mensaje;
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return "MensajeJson [mensaje=" + mensaje + "]";
	}	
	
}
