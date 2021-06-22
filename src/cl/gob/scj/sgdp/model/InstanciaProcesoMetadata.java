package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cl.gob.scj.sgdp.config.Constantes;

/**
 * The persistent class for the "SGDP_INSTANCIA_PROCESO_METADATA" database table.
 * 
 */
@Entity
@Table(name = "\"SGDP_INSTANCIA_PROCESO_METADATA\"")
@NamedQuery(name = "InstanciaProcesoMetadata.findAll", query = "SELECT p FROM InstanciaProcesoMetadata p order by p.idInstanciaProcesoMetadata asc")
public class InstanciaProcesoMetadata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_ID_INSTANCIA_PROCESO_METADATA", sequenceName = "\"SEQ_ID_INSTANCIA_PROCESO_METADATA\"", allocationSize = Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID_INSTANCIA_PROCESO_METADATA")
	@Column(name = "\"ID_INSTANCIA_PROCESO_METADATA\"")
	private long idInstanciaProcesoMetadata;

	@Column(name = "\"A_TITULO\"")
	private String titulo;

	@Column(name = "\"A_NOMBRE_INTERESADO\"")
	private String nombreInteresado;

	@Column(name = "\"A_APELLIDO_PATERNO\"")
	private String apellidoPaterno;
	
	@Column(name = "\"A_APELLIDO_MATERNO\"")
	private String apellidoMaterno;
	
	@Column(name = "\"A_RUT\"")
	private String rut;
	
	@Column(name = "\"A_ETIQUETAS\"")
	private String etiquetas;
	
	@Column(name = "\"A_REGION\"")
	private String region;
	
	@Column(name = "\"A_COMUNA\"")
	private String comuna;
	
	@Column(name = "\"D_FECHA_CREACION\"")
	private Date fechaCreacion;
	
	@Column(name = "\"A_METADATA_CUSTOM\"")
	private String metadataCustom;
	
	public InstanciaProcesoMetadata() {
	}

	public long getIdInstanciaProcesoMetadata() {
		return idInstanciaProcesoMetadata;
	}

	public void setIdInstanciaProcesoMetadata(long idInstanciaProcesoMetadata) {
		this.idInstanciaProcesoMetadata = idInstanciaProcesoMetadata;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNombreInteresado() {
		return nombreInteresado;
	}

	public void setNombreInteresado(String nombreInteresado) {
		this.nombreInteresado = nombreInteresado;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
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

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getMetadataCustom() {
		return metadataCustom;
	}

	public void setMetadataCustom(String metadataCustom) {
		this.metadataCustom = metadataCustom;
	}



}