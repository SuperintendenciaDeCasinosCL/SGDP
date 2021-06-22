package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespuestaLoginArchivoNacionalDTO implements Serializable {

	@JsonProperty(value = "data")
	private String data;
	@JsonProperty(value = "status")
	private String status;
	@JsonProperty(value = "mensaje")
	private String mensaje;


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
