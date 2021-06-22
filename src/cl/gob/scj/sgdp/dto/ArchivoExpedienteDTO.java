package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArchivoExpedienteDTO implements Serializable {


	@JsonProperty(value = "autor")
	private String autor;
	@JsonProperty(value = "checksum")
	private String checksum;
	@JsonProperty(value = "destinatario")
	private String destinatario;
	@JsonProperty(value = "documentoDigitalizado")
	private String documentoDigitalizado;
	@JsonProperty(value = "fechaDocumento")
	private String fechaDocumento;
	@JsonProperty(value = "formato")
	private String formato;
	@JsonProperty(value = "identificador")
	private String identificador;
	@JsonProperty(value = "nombreArchivo")
	private String nombreArchivo;
	@JsonProperty(value = "titulo")
	private String titulo;
	@JsonProperty(value = "volumen")
	private long volumen;
	@JsonIgnore
	private String idArchivoCms;

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getDocumentoDigitalizado() {
		return documentoDigitalizado;
	}

	public void setDocumentoDigitalizado(String documentoDigitalizado) {
		this.documentoDigitalizado = documentoDigitalizado;
	}

	public String getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(String fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public long getVolumen() {
		return volumen;
	}

	public void setVolumen(long volumen) {
		this.volumen = volumen;
	}

	public String getIdArchivoCms() {
		return idArchivoCms;
	}

	public void setIdArchivoCms(String idArchivoCms) {
		this.idArchivoCms = idArchivoCms;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1664223562765000060L;

	@Override
	public String toString() {
		return " \n ArchivoExpedienteDTO [autor=" + autor + ", destinatario=" + destinatario + ", documentoDigitalizado="
				+ documentoDigitalizado + ", fechaDocumento=" + fechaDocumento + ", formato=" + formato
				+ ", identificador=" + identificador + ", nombreArchivo=" + nombreArchivo + ", titulo=" + titulo
				+ ", volumen=" + volumen + ", idArchivoCms=" + idArchivoCms + "]";
	}
}
