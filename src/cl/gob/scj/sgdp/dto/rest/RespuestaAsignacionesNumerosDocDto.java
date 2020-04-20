package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class RespuestaAsignacionesNumerosDocDto implements Serializable {

	private String respuesta;
	private long numeroDocumento;
	private long idAsignacionNumeroDoc;
	
	public RespuestaAsignacionesNumerosDocDto() {
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public long getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(long numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public long getIdAsignacionNumeroDoc() {
		return idAsignacionNumeroDoc;
	}

	public void setIdAsignacionNumeroDoc(long idAsignacionNumeroDoc) {
		this.idAsignacionNumeroDoc = idAsignacionNumeroDoc;
	}

	@Override
	public String toString() {
		return "RespuestaAsignacionesNumerosDocDto [respuesta=" + respuesta + ", numeroDocumento=" + numeroDocumento
				+ ", idAsignacionNumeroDoc=" + idAsignacionNumeroDoc + "]";
	}


}
