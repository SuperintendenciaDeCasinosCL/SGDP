package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentoDTO implements Serializable {

	@JsonProperty(value = "apellidoMaternoInteresado")
	private String apellidoMaternoInteresado;
	@JsonProperty(value = "apellidoPaternoInteresado")
	private String apellidoPaternoInteresado;
	@JsonProperty(value = "autor")
	private String autor;
	@JsonProperty(value = "checksum")
	private String checksum;
	@JsonProperty(value = "comuna")
	private String comuna;
	@JsonProperty(value = "destinatario")
	private String destinatario;
	@JsonProperty(value = "documentoDigitalizado")
	private String documentoDigitalizado;
	@JsonProperty(value = "etiquetas")
	private String etiquetas;
	@JsonProperty(value = "fechaCapturaDocumento")
	private String fechaCapturaDocumento;
	@JsonProperty(value = "fechaDocumento")
	private String fechaDocumento;
	@JsonProperty(value = "formato")
	private String formato;
	@JsonProperty(value = "identificador")
	private String identificador;

	@JsonProperty(value = "nivelAcceso")
	private String nivelAcceso;
	@JsonProperty(value = "nombreArchivo")
	private String nombreArchivo;
	@JsonProperty(value = "nombreInteresado")
	private String nombreInteresado;
	@JsonProperty(value = "region")
	private String region;
	@JsonProperty(value = "rutInteresado")
	private String rutInteresado;
	@JsonProperty(value = "titulo")
	private String titulo;
	@JsonProperty(value = "volumen")
	private int volumen;
	@JsonIgnore
	private String idArchivoCms;

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

	public String getComuna() {
		return comuna;
	}

	public void setComuna(String comuna) {
		this.comuna = comuna;
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

	public String getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}

	public String getFechaCapturaDocumento() {
		return fechaCapturaDocumento;
	}

	public void setFechaCapturaDocumento(String fechaCapturaDocumento) {
		this.fechaCapturaDocumento = fechaCapturaDocumento;
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

	public String getNivelAcceso() {
		return nivelAcceso;
	}

	public void setNivelAcceso(String nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getVolumen() {
		return volumen;
	}

	public void setVolumen(int volumen) {
		this.volumen = volumen;
	}

	public String getIdArchivoCms() {
		return idArchivoCms;
	}

	public void setIdArchivoCms(String idArchivoCms) {
		this.idArchivoCms = idArchivoCms;
	}

	@Override
	public String toString() {
		return "DocumentoDTO [apellidoMaternoInteresado=" + apellidoMaternoInteresado + ", apellidoPaternoInteresado="
				+ apellidoPaternoInteresado + ", autor=" + autor + ", checksum=" + checksum + ", comuna=" + comuna
				+ ", destinatario=" + destinatario + ", documentoDigitalizado=" + documentoDigitalizado + ", etiquetas="
				+ etiquetas + ", fechaCapturaDocumento=" + fechaCapturaDocumento + ", fechaDocumento=" + fechaDocumento
				+ ", formato=" + formato + ", identificador=" + identificador + ", nivelAcceso=" + nivelAcceso
				+ ", nombreArchivo=" + nombreArchivo + ", nombreInteresado=" + nombreInteresado + ", region=" + region
				+ ", rutInteresado=" + rutInteresado + ", titulo=" + titulo + ", volumen=" + volumen + ", idArchivoCms="
				+ idArchivoCms + "]";
	}

}
