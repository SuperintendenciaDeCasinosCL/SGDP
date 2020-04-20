package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class ListaDeDistribucionDTO implements Serializable {
	
	private long idListaDeDistribucion;
	private String nombreCompleto;
	private String email;
	private String organizacion;
	private String cargo;
	private String respuesta;
	
	public long getIdListaDeDistribucion() {
		return idListaDeDistribucion;
	}
	public void setIdListaDeDistribucion(long idListaDeDistribucion) {
		this.idListaDeDistribucion = idListaDeDistribucion;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getOrganizacion() {
		return organizacion;
	}
	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}	
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	@Override
	public String toString() {
		return "ListaDeDistribucionDTO [idListaDeDistribucion=" + idListaDeDistribucion + ", nombreCompleto="
				+ nombreCompleto + ", email=" + email 
				+ ", organizacion=" + organizacion 
				+ ", cargo=" + cargo
				+ ", respuesta=" + respuesta
				+ "]";
	}
	
}
