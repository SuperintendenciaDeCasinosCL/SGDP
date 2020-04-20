package cl.gob.scj.sgdp.ws.alfresco.rest.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrearExpedienteResponse implements Serializable {

	private static final long serialVersionUID = -963968699473354708L;
	
	private String idExpediente;

	public String getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}

}