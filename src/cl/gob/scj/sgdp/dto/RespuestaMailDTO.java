package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class RespuestaMailDTO implements Serializable  {

	private String respuesta;
	private String codigoError;
    private String nombreArchivo;
    
	public RespuestaMailDTO() {
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	
}
