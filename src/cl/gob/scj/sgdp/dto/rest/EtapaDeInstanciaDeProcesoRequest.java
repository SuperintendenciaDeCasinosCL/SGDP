package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class EtapaDeInstanciaDeProcesoRequest implements Serializable  {

	private String idExpediente;
	private String idUsuario;
	private String clave;
	
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
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	@Override
	public String toString() {
		return "EtapaDeInstanciaDeProcesoRequest [idExpediente=" + idExpediente + ", idUsuario=" + idUsuario
				+ ", clave=" + clave + "]";
	}
	
}
