package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cl.gob.scj.sgdp.config.Constantes;

/**
 * The persistent class for the "SGDP_ARCHIVOS_INST_DE_TAREA_METADATA" database table.
 * 
 */
@Entity
@Table(name = "\"SGDP_ARCHIVOS_INST_DE_TAREA_METADATA\"")
@NamedQuery(name = "ArchivosInstDeTareaMetadata.findAll", query = "SELECT p FROM ArchivosInstDeTareaMetadata p order by p.idArchivosInstDeTareaMetadata asc")
public class ArchivosInstDeTareaMetadata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_ID_ARCHIVOS_INST_DE_TAREA_METADATA", sequenceName = "\"SEQ_ID_ARCHIVOS_INST_DE_TAREA_METADATA\"", allocationSize = Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID_ARCHIVOS_INST_DE_TAREA_METADATA")
	@Column(name = "\"ID_ARCHIVOS_INST_DE_TAREA_METADATA\"")
	private long idArchivosInstDeTareaMetadata;

	@Column(name = "\"A_TITULO\"")
	private String titulo;

	@Column(name = "\"A_AUTOR\"")
	private String autor;

	@Column(name = "\"A_DESTINATARIOS\"")
	private String destinatarios;
	
	@Column(name="\"B_DIGITALIZADO\"")
	private Boolean digitalizado;
	
	@Column(name = "\"D_FECHA_DOCUMENTO\"")
	private Date fechaDocumento;
	
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
	
//	@Column(name = "\"D_FECHA_CAPTURA\"")
//	private Date fechaCaptura;
	
	//bi-directional many-to-one association to Unidad
	@ManyToOne
	@JoinColumn(name="\"ID_TIPO\"")
	private Tipo tipo;
	
	@Column(name = "\"N_FLAG_ENVIO\"")
	private long flagEnvio;
	
	@Column(name = "\"A_METADATA_CUSTOM\"")
	private String metadataCustom;
	
	public ArchivosInstDeTareaMetadata() {
	}

	public long getIdArchivosInstDeTareaMetadata() {
		return idArchivosInstDeTareaMetadata;
	}

	public void setIdArchivosInstDeTareaMetadata(long idArchivosInstDeTareaMetadata) {
		this.idArchivosInstDeTareaMetadata = idArchivosInstDeTareaMetadata;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}

	public Boolean getDigitalizado() {
		return digitalizado;
	}

	public void setDigitalizado(Boolean digitalizado) {
		this.digitalizado = digitalizado;
	}

	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
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

//	public Date getFechaCaptura() {
//		return fechaCaptura;
//	}
//
//	public void setFechaCaptura(Date fechaCaptura) {
//		this.fechaCaptura = fechaCaptura;
//	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public long getFlagEnvio() {
		return flagEnvio;
	}

	public void setFlagEnvio(long flagEnvio) {
		this.flagEnvio = flagEnvio;
	}

	public String getMetadataCustom() {
		return metadataCustom;
	}

	public void setMetadataCustom(String metadataCustom) {
		this.metadataCustom = metadataCustom;
	}

	@Override
	public String toString() {
		return "ArchivosInstDeTareaMetadata [idArchivosInstDeTareaMetadata=" + idArchivosInstDeTareaMetadata
				+ ", titulo=" + titulo + ", autor=" + autor + ", destinatarios=" + destinatarios + ", digitalizado="
				+ digitalizado + ", fechaDocumento=" + fechaDocumento + ", nombreInteresado=" + nombreInteresado
				+ ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", rut=" + rut
				+ ", etiquetas=" + etiquetas + ", region=" + region + ", comuna=" + comuna + ", tipo=" + tipo
				+ ", flagEnvio=" + flagEnvio + ", metadataCustom=" + metadataCustom + "]";
	}

	





}