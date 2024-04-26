package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_SOLICITUD_CREACION_EXP" database table.
 *
 */
@Entity
@Table(name="\"SGDP_SOLICITUD_CREACION_EXP\"")
@NamedQueries({
		@NamedQuery(name="SolicitudCreacionExp.findAll", query="SELECT s FROM SolicitudCreacionExp s"),

		@NamedQuery(name="SolicitudCreacionExp.getSolicitudesCreaExpSolicitadasPorOAsignadasA",
				query="SELECT s FROM SolicitudCreacionExp s "
						+ "WHERE ( (s.idUsuarioSolicitante = :idUsuarioSolicitante OR s.idUsuarioCreadorExpediente = :idUsuarioCreadorExpediente) "
						+ "AND s.estadoSolicitudCreacionExp.idEstadoSolicitudCreacionExp = :idEstadoSolicitudCreacionExp ) "
						+ "OR ( s.estadoSolicitudCreacionExp.idEstadoSolicitudCreacionExp = 5 ) "),

		@NamedQuery(name="SolicitudCreacionExp.getSolicitudesCreaExpFinalizadas",
				query="SELECT s FROM SolicitudCreacionExp s "
						+ "WHERE ( (s.idUsuarioSolicitante = :idUsuarioSolicitante OR s.idUsuarioCreadorExpediente = :idUsuarioCreadorExpediente) "
						+ "AND s.estadoSolicitudCreacionExp.idEstadoSolicitudCreacionExp in (3, 4) ) ORDER BY s.idSolicitudCreacionExp desc "),

		@NamedQuery(name="SolicitudCreacionExp.getTotalSolicitudesCreaExpFinalizadas",
				query="SELECT count(*) FROM SolicitudCreacionExp s "
						+ "WHERE ( (s.idUsuarioSolicitante = :idUsuarioSolicitante OR s.idUsuarioCreadorExpediente = :idUsuarioCreadorExpediente) "
						+ "AND s.estadoSolicitudCreacionExp.idEstadoSolicitudCreacionExp in (3, 4) )  "),

		@NamedQuery(name="SolicitudCreacionExp.getSolicitudesCreaExpSolicitadasPorOAsignadasAMultiOficina",
				query="SELECT s FROM SolicitudCreacionExp s "
						+ "WHERE ( (s.idUsuarioSolicitante = :idUsuarioSolicitante OR s.idUsuarioCreadorExpediente = :idUsuarioCreadorExpediente) "
						+ "AND s.estadoSolicitudCreacionExp.idEstadoSolicitudCreacionExp = :idEstadoSolicitudCreacionExp ) "
						+ "OR ( s.estadoSolicitudCreacionExp.idEstadoSolicitudCreacionExp = 5 AND s.unidadOperativa.idUnidadOperativa is null ) "
						+ "OR ( s.estadoSolicitudCreacionExp.idEstadoSolicitudCreacionExp = 5 AND s.unidadOperativa.idUnidadOperativa <> null "
						+ "		AND s.unidadOperativa.idUnidadOperativa in ( SELECT uo.idUnidadOperativa "
						+ "														FROM UsuarioRol ur, Unidad u, UnidadOperativa uo  "
						+ "														WHERE ur.unidad.idUnidad = u.idUnidad "
						+ "														AND u.unidadOperativa.idUnidadOperativa = uo.idUnidadOperativa "
						+ "														AND ur.idUsuario = :idUsuario ) )")
})
public class SolicitudCreacionExp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_SOLICITUD_CREACION_EXP", sequenceName="\"SEQ_ID_SOLICITUD_CREACION_EXP\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_SOLICITUD_CREACION_EXP")
	@Column(name="\"ID_SOLICITUD_CREACION_EXP\"")
	private Long idSolicitudCreacionExp;

	@Column(name="\"A_COMENTARIO\"")
	private String comentario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_ATENCION\"")
	private Date fechaAtencion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_SOLICITUD\"")
	private Date fechaSolicitud;

	@Column(name="\"ID_USUARIO_CREADOR_EXPEDIENTE\"")
	private String idUsuarioCreadorExpediente;

	@Column(name="\"ID_USUARIO_DESTINATARIO\"")
	private String idUsuarioDestinatario;

	@Column(name="\"ID_USUARIO_SOLICITANTE\"")
	private String idUsuarioSolicitante;

	//bi-directional many-to-one association to EstadoSolicitudCreacionExp
	@ManyToOne
	@JoinColumn(name="\"ID_ESTADO_SOLICITUD_CREACION_EXP\"")
	private EstadoSolicitudCreacionExp estadoSolicitudCreacionExp;

	//bi-directional one-to-one association to InstanciasDeProceso
	@OneToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_PROCESO\"")
	private InstanciaDeProceso instanciaDeProceso;

	//bi-directional many-to-one association to Proceso
	@ManyToOne
	@JoinColumn(name="\"ID_PROCESO\"")
	private Proceso proceso;

	//bi-directional many-to-one association to Autor
	@ManyToOne
	@JoinColumn(name="\"ID_AUTOR\"")
	private Autor autor;

	@Column(name="\"A_ASUNTO_MATERIA\"")
	private String asuntoMateria;

	@Column(name="\"ID_CMS_CARPETA\"")
	private String idCMSCarpeta;

	@Column(name="\"ID_CARPETA\"")
	private Long idCarpeta;

	//bi-directional many-to-one association to ArchivosSolCreaExp
	@OneToMany(mappedBy="solicitudCreacionExp")
	private List<ArchivosSolCreaExp> archivosSolCreaExps;

	//bi-directional one-to-one association to UnidadOperativa
	@ManyToOne
	@JoinColumn(name="\"ID_UNIDAD_OPERATIVA\"")
	private UnidadOperativa unidadOperativa;

	//bi-directional many-to-one association to LogDocumento
	@OneToMany(mappedBy="solicitudCreacionExp")
	private List<LogDocumento> logDocumentos;

	public SolicitudCreacionExp() {
	}


	public List<ArchivosSolCreaExp> getArchivosSolCreaExps() {
		return this.archivosSolCreaExps;
	}

	public void setArchivosSolCreaExps(List<ArchivosSolCreaExp> archivosSolCreaExps) {
		this.archivosSolCreaExps = archivosSolCreaExps;
	}

	public ArchivosSolCreaExp addArchivosSolCreaExp(ArchivosSolCreaExp archivosSolCreaExp) {

		getArchivosSolCreaExps().add(archivosSolCreaExp);
		archivosSolCreaExp.setSolicitudCreacionExp(this);
		return archivosSolCreaExp;
	}


	public Long getIdSolicitudCreacionExp() {
		return this.idSolicitudCreacionExp;
	}

	public void setIdSolicitudCreacionExp(Long idSolicitudCreacionExp) {
		this.idSolicitudCreacionExp = idSolicitudCreacionExp;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFechaAtencion() {
		return fechaAtencion;
	}

	public void setFechaAtencion(Date fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getIdUsuarioCreadorExpediente() {
		return this.idUsuarioCreadorExpediente;
	}

	public void setIdUsuarioCreadorExpediente(String idUsuarioCreadorExpediente) {
		this.idUsuarioCreadorExpediente = idUsuarioCreadorExpediente;
	}

	public String getIdUsuarioDestinatario() {
		return this.idUsuarioDestinatario;
	}

	public void setIdUsuarioDestinatario(String idUsuarioDestinatario) {
		this.idUsuarioDestinatario = idUsuarioDestinatario;
	}

	public String getIdUsuarioSolicitante() {
		return this.idUsuarioSolicitante;
	}

	public void setIdUsuarioSolicitante(String idUsuarioSolicitante) {
		this.idUsuarioSolicitante = idUsuarioSolicitante;
	}

	public EstadoSolicitudCreacionExp getEstadoSolicitudCreacionExp() {
		return this.estadoSolicitudCreacionExp;
	}

	public void setEstadoSolicitudCreacionExp(EstadoSolicitudCreacionExp estadoSolicitudCreacionExp) {
		this.estadoSolicitudCreacionExp = estadoSolicitudCreacionExp;
	}

	public InstanciaDeProceso getInstanciaDeProceso() {
		return instanciaDeProceso;
	}

	public void setInstanciaDeProceso(InstanciaDeProceso instanciaDeProceso) {
		this.instanciaDeProceso = instanciaDeProceso;
	}

	public Proceso getProceso() {
		return this.proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public String getAsuntoMateria() {
		return asuntoMateria;
	}

	public void setAsuntoMateria(String asuntoMateria) {
		this.asuntoMateria = asuntoMateria;
	}

	public UnidadOperativa getUnidadOperativa() {
		return unidadOperativa;
	}

	public void setUnidadOperativa(UnidadOperativa unidadOperativa) {
		this.unidadOperativa = unidadOperativa;
	}


	public String getIdCMSCarpeta() {
		return idCMSCarpeta;
	}


	public void setIdCMSCarpeta(String idCMSCarpeta) {
		this.idCMSCarpeta = idCMSCarpeta;
	}


	public Long getIdCarpeta() {
		return idCarpeta;
	}


	public void setIdCarpeta(Long idCarpeta) {
		this.idCarpeta = idCarpeta;
	}


	public List<LogDocumento> getLogDocumentos() {
		return logDocumentos;
	}


	public void setLogDocumentos(List<LogDocumento> logDocumentos) {
		this.logDocumentos = logDocumentos;
	}

}
