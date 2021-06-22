package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class RespuestaAccesoArchivoNacionalDTO implements Serializable {

	private String mensajeError;
	private String mensajeRespuesta;

	public RespuestaAccesoArchivoNacionalDTO() {
		super();
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}

	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
