package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class AnulacionDTO implements Serializable  {

	private long idInstanciaDeProceso;
	private String motivoAnulacion;
	private String respuestaAnulacion;
	private String idExpediente;	
	private String idUsuario;
	private String idUsuarioAnula;	
	
	public long getIdInstanciaDeProceso() {
		return idInstanciaDeProceso;
	}
	public void setIdInstanciaDeProceso(long idInstanciaDeProceso) {
		this.idInstanciaDeProceso = idInstanciaDeProceso;
	}
	public String getMotivoAnulacion() {
		return motivoAnulacion;
	}
	public void setMotivoAnulacion(String motivoAnulacion) {
		this.motivoAnulacion = motivoAnulacion;
	}
	public String getRespuestaAnulacion() {
		return respuestaAnulacion;
	}
	public void setRespuestaAnulacion(String respuestaAnulacion) {
		this.respuestaAnulacion = respuestaAnulacion;
	}	
	public String getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}	
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}	
	public String getIdUsuarioAnula() {
		return idUsuarioAnula;
	}
	public void setIdUsuarioAnula(String idUsuarioAnula) {
		this.idUsuarioAnula = idUsuarioAnula;
	}
	@Override
	public String toString() {
		return "AnulacionProcesoRequest [idInstanciaDeProceso=" + idInstanciaDeProceso + ", motivoAnulacion=" + motivoAnulacion
				+ ", respuestaAnulacion=" + respuestaAnulacion
				+ ", idExpediente=" + idExpediente
				+ ", idUsuarioAnula=" + idUsuarioAnula
				+ "]";
	}
	
}
