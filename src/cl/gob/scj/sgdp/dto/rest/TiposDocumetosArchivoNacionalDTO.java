package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class TiposDocumetosArchivoNacionalDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombreTipoDocumento;
	private String idArchivoCms;
	private Boolean conformaExpediente;
	private String nombreArchivo;
	private String fechaSubido;
	
	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}
	public void setNombreTipoDocumento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
	}
	public String getIdArchivoCms() {
		return idArchivoCms;
	}
	public void setIdArchivoCms(String idArchivoCms) {
		this.idArchivoCms = idArchivoCms;
	}
	public Boolean getConformaExpediente() {
		return conformaExpediente;
	}
	public void setConformaExpediente(Boolean conformaExpediente) {
		this.conformaExpediente = conformaExpediente;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	public String getFechaSubido() {
		return fechaSubido;
	}
	public void setFechaSubido(String fechaSubido) {
		this.fechaSubido = fechaSubido;
	}
	@Override
	public String toString() {
		return "TiposDocumetosArchivoNacionalDTO [nombreTipoDocumento=" + nombreTipoDocumento + ", idArchivoCms="
				+ idArchivoCms + ", conformaExpediente=" + conformaExpediente + ", nombreArchivo=" + nombreArchivo
				+ ", fechaSubido=" + fechaSubido + "]";
	}
	
	
	
	
	

}
