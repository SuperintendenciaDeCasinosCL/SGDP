package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

public class ExpedienteArchNacDTO implements Serializable {
	
	private String apellidoMaternoInteresado;
	private String apellidoPaternoInteresado;
	private String comuna;
	private String etiquetas;
	private String fechaCreacionExpediente;
	private String fechaFinalizacionExpediente;
	
	private List<ArchivoExpedienteDTO> listaArchivos;
	
	private String nivelAcceso;
	private String nombreInteresado;
	private String region;
	private String rutInteresado;
	private String tituloExpediente;
	private long volumenExpediente;
	public String getApellidoMaternoInteresado() {
		return apellidoMaternoInteresado;
	}
	public void setApellidoMaternoInteresado(String apellidoMaternoInteresado) {
		this.apellidoMaternoInteresado = apellidoMaternoInteresado;
	}
	public String getApellidoPaternoInteresado() {
		return apellidoPaternoInteresado;
	}
	public void setApellidoPaternoInteresado(String apellidoPaternoInteresado) {
		this.apellidoPaternoInteresado = apellidoPaternoInteresado;
	}
	public String getComuna() {
		return comuna;
	}
	public void setComuna(String comuna) {
		this.comuna = comuna;
	}
	public String getEtiquetas() {
		return etiquetas;
	}
	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}
	public String getFechaCreacionExpediente() {
		return fechaCreacionExpediente;
	}
	public void setFechaCreacionExpediente(String fechaCreacionExpediente) {
		this.fechaCreacionExpediente = fechaCreacionExpediente;
	}
	public String getFechaFinalizacionExpediente() {
		return fechaFinalizacionExpediente;
	}
	public void setFechaFinalizacionExpediente(String fechaFinalizacionExpediente) {
		this.fechaFinalizacionExpediente = fechaFinalizacionExpediente;
	}
	public List<ArchivoExpedienteDTO> getListaArchivos() {
		return listaArchivos;
	}
	public void setListaArchivos(List<ArchivoExpedienteDTO> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}
	public String getNivelAcceso() {
		return nivelAcceso;
	}
	public void setNivelAcceso(String nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}
	public String getNombreInteresado() {
		return nombreInteresado;
	}
	public void setNombreInteresado(String nombreInteresado) {
		this.nombreInteresado = nombreInteresado;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRutInteresado() {
		return rutInteresado;
	}
	public void setRutInteresado(String rutInteresado) {
		this.rutInteresado = rutInteresado;
	}
	public String getTituloExpediente() {
		return tituloExpediente;
	}
	public void setTituloExpediente(String tituloExpediente) {
		this.tituloExpediente = tituloExpediente;
	}
	public long getVolumenExpediente() {
		return volumenExpediente;
	}
	public void setVolumenExpediente(long volumenExpediente) {
		this.volumenExpediente = volumenExpediente;
	}
	@Override
	public String toString() {
		return "ExpedienteArchNacDTO [apellidoMaternoInteresado=" + apellidoMaternoInteresado
				+ ", apellidoPaternoInteresado=" + apellidoPaternoInteresado + ", comuna=" + comuna + ", etiquetas="
				+ etiquetas + ", fechaCreacionExpediente=" + fechaCreacionExpediente + ", fechaFinalizacionExpediente="
				+ fechaFinalizacionExpediente + ", listaArchivos=" + listaArchivos + ", nivelAcceso=" + nivelAcceso
				+ ", nombreInteresado=" + nombreInteresado + ", region=" + region + ", rutInteresado=" + rutInteresado
				+ ", tituloExpediente=" + tituloExpediente + ", volumenExpediente=" + volumenExpediente + "]";
	}
	

}
