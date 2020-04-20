package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class AgregaORemueveTagDeObjetoResponse implements Serializable  {

	private String codigoRespuesta;
	private String mensajeRespuesta;
	
	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}
	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}
	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}
	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}
	@Override
	public String toString() {
		return "AgregaORemueveTagDeObjetoResponse [codigoRespuesta=" + codigoRespuesta + ", mensajeRespuesta="
				+ mensajeRespuesta + "]";
	}
	
	
}
