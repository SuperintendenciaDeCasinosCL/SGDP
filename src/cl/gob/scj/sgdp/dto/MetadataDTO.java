package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class MetadataDTO implements Serializable {



	private String composicionDocumental;
	private String titulo;
	private String fechaDocumento;
	private String nombreInteresado;
	private String rut;
	private String etiquetas;
	private String region;
	private String comuna;
	private String listaMetadata;
	
	public String getComposicionDocumental() {
		return composicionDocumental;
	}

	public void setComposicionDocumental(String composicionDocumental) {
		this.composicionDocumental = composicionDocumental;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(String fechaCreacion) {
		this.fechaDocumento = fechaCreacion;
	}

	public String getNombreInteresado() {
		return nombreInteresado;
	}

	public void setNombreInteresado(String nombreInteresado) {
		this.nombreInteresado = nombreInteresado;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getComuna() {
		return comuna;
	}

	public void setComuna(String comuna) {
		this.comuna = comuna;
	}

	public String getListaMetadata() {
		return listaMetadata;
	}

	public void setListaMetadata(String listaMetadata) {
		this.listaMetadata = listaMetadata;
	}

	public MetadataDTO() {
		super();
	}

	@Override
	public String toString() {
		return "MetadataDTO [composicionDocumental=" + composicionDocumental + ", titulo=" + titulo
				+ ", fechaDocumento=" + fechaDocumento + ", nombreInteresado=" + nombreInteresado + ", rut=" + rut
				+ ", etiquetas=" + etiquetas + ", region=" + region + ", comuna=" + comuna + ", listaMetadata="
				+ listaMetadata + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7697793555811566289L;

}
