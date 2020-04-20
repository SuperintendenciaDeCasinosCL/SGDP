package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class RespuestaCrearExpedienteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mensajeError;
	private String mensajeRespuesta;
	private String nombreExpediente;
	private String idExpediente;
	private long idInstanciaDeTareaSalida;
	
	public RespuestaCrearExpedienteDTO() {
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


	public String getNombreExpediente() {
		return nombreExpediente;
	}


	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}


	public String getIdExpediente() {
		return idExpediente;
	}


	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}


	public long getIdInstanciaDeTareaSalida() {
		return idInstanciaDeTareaSalida;
	}


	public void setIdInstanciaDeTareaSalida(long idInstanciaDeTareaSalida) {
		this.idInstanciaDeTareaSalida = idInstanciaDeTareaSalida;
	}
	
	
}
