package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import javax.persistence.*;


import java.sql.Timestamp;


/**
 * The persistent class for the "SGDP_ARCHIVOS_SOL_CREA_EXP" database table.
 *
 */
@Entity
@Table(name="\"SGDP_ARCHIVOS_SOL_CREA_EXP\"")
@NamedQueries({
		@NamedQuery(name="ArchivosSolCreaExp.findAll",
				query="SELECT a FROM ArchivosSolCreaExp a"),
		@NamedQuery(name="ArchivosSolCreaExp.findByIdSol",
				query="SELECT a FROM ArchivosSolCreaExp a WHERE a.solicitudCreacionExp.idSolicitudCreacionExp = :idSolicitudCreacionExp"),
		@NamedQuery(name="ArchivosSolCreaExp.findByIdArchivoCms",
				query="SELECT a FROM ArchivosSolCreaExp a WHERE a.idArchivoCms = :idArchivoCms")
})
public class ArchivosSolCreaExp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"ID_ARCHIVOS_SOL_CREA_EXP\"")
	private Long idArchivosSolCreaExp;

	@Column(name="\"A_DESCRIPCION_ORIGEN\"")
	private String descripcionOrigen;

	@Column(name="\"A_FECHA_CREACION_ORIGEN\"")
	private String fechaCreacionOrigen;

	@Column(name="\"A_FECHA_FOLIO\"")
	private String fechaFolio;

	@Column(name="\"A_FOLIO_ORIGEN\"")
	private String folioOrigen;

	@Column(name="\"A_TIPO_ORIGEN\"")
	private String tipoOrigen;

	@Column(name="\"A_URL_DOCUMENTO_ORIGEN\"")
	private String urlDocumentoOrigen;

	@Column(name="\"B_FOLIADO\"")
	private Boolean foliado;

	@Column(name="\"D_FECHA_SUBIDO\"")
	private Timestamp fechaSubido;

	@Column(name="\"ID_ARCHIVO_CMS\"")
	private String idArchivoCms;

	@Column(name="\"ID_DOCUMENTO_ORIGEN\"")
	private Long idDocumentoOrigen;

	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;

	//bi-directional many-to-one association to SolicitudCreacionExp
	@ManyToOne
	@JoinColumn(name="\"ID_SOLICITUD_CREACION_EXP\"")
	private SolicitudCreacionExp solicitudCreacionExp;

	@Column(name="\"B_CONFIDENCIAL_FINAL\"")
	private Boolean confidencialidadFinal;

	@Column(name="\"A_USUARIOS_CONFIDENCIALES\"")
	private String usuariosConfidenciales;

	@Column(name="\"ID_DOCUMENTO_ORIGEN_STRING\"")
	private String idDocumentoOrigenString;

	public Long getIdArchivosSolCreaExp() {
		return this.idArchivosSolCreaExp;
	}

	public void setIdArchivosSolCreaExp(Long idArchivosSolCreaExp) {
		this.idArchivosSolCreaExp = idArchivosSolCreaExp;
	}

	public String getDescripcionOrigen() {
		return this.descripcionOrigen;
	}

	public void setDescripcionOrigen(String descripcionOrigen) {
		this.descripcionOrigen = descripcionOrigen;
	}

	public String getFechaCreacionOrigen() {
		return this.fechaCreacionOrigen;
	}

	public void setFechaCreacionOrigen(String fechaCreacionOrigen) {
		this.fechaCreacionOrigen = fechaCreacionOrigen;
	}

	public String getFechaFolio() {
		return this.fechaFolio;
	}

	public void setFechaFolio(String fechaFolio) {
		this.fechaFolio = fechaFolio;
	}

	public String getFolioOrigen() {
		return this.folioOrigen;
	}

	public void setFolioOrigen(String folioOrigen) {
		this.folioOrigen = folioOrigen;
	}

	public String getTipoOrigen() {
		return this.tipoOrigen;
	}

	public void setTipoOrigen(String tipoOrigen) {
		this.tipoOrigen = tipoOrigen;
	}

	public String getUrlDocumentoOrigen() {
		return this.urlDocumentoOrigen;
	}

	public void setUrlDocumentoOrigen(String urlDocumentoOrigen) {
		this.urlDocumentoOrigen = urlDocumentoOrigen;
	}

	public Boolean getFoliado() {
		return this.foliado;
	}

	public void setFoliado(Boolean foliado) {
		this.foliado = foliado;
	}

	public Timestamp getFechaSubido() {
		return this.fechaSubido;
	}

	public void setFechaSubido(Timestamp fechaSubido) {
		this.fechaSubido = fechaSubido;
	}

	public String getIdArchivoCms() {
		return this.idArchivoCms;
	}

	public void setIdArchivoCms(String idArchivoCms) {
		this.idArchivoCms = idArchivoCms;
	}

	public Long getIdDocumentoOrigen() {
		return this.idDocumentoOrigen;
	}

	public void setIdDocumentoOrigen(Long idDocumentoOrigen) {
		this.idDocumentoOrigen = idDocumentoOrigen;
	}

	public String getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public SolicitudCreacionExp getSolicitudCreacionExp() {
		return this.solicitudCreacionExp;
	}

	public void setSolicitudCreacionExp(SolicitudCreacionExp solicitudCreacionExp) {
		this.solicitudCreacionExp = solicitudCreacionExp;
	}

	public Boolean getConfidencialidadFinal() {
		return confidencialidadFinal;
	}

	public void setConfidencialidadFinal(Boolean confidencialidadFinal) {
		this.confidencialidadFinal = confidencialidadFinal;
	}

	public String getUsuariosConfidenciales() {
		return usuariosConfidenciales;
	}

	public void setUsuariosConfidenciales(String usuariosConfidenciales) {
		this.usuariosConfidenciales = usuariosConfidenciales;
	}

	public String getIdDocumentoOrigenString() {
		return idDocumentoOrigenString;
	}

	public void setIdDocumentoOrigenString(String idDocumentoOrigenString) {
		this.idDocumentoOrigenString = idDocumentoOrigenString;
	}

	@Override
	public String toString() {
		return "ArchivosSolCreaExp{" +
				"idArchivosSolCreaExp=" + idArchivosSolCreaExp +
				", descripcionOrigen='" + descripcionOrigen + '\'' +
				", fechaCreacionOrigen='" + fechaCreacionOrigen + '\'' +
				", fechaFolio='" + fechaFolio + '\'' +
				", folioOrigen='" + folioOrigen + '\'' +
				", tipoOrigen='" + tipoOrigen + '\'' +
				", urlDocumentoOrigen='" + urlDocumentoOrigen + '\'' +
				", foliado=" + foliado +
				", fechaSubido=" + fechaSubido +
				", idArchivoCms='" + idArchivoCms + '\'' +
				", idDocumentoOrigen=" + idDocumentoOrigen +
				", idUsuario='" + idUsuario + '\'' +
				", confidencialidadFinal=" + confidencialidadFinal +
				", usuariosConfidenciales='" + usuariosConfidenciales + '\'' +
				", idDocumentoOrigenString='" + idDocumentoOrigenString + '\'' +
				'}';
	}

}