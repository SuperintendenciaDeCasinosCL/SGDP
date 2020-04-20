package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

import java.util.Date;


/**
 * The persistent class for the "SGDP_HISTORICO_SOLICITUD_CREACION_EXP" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_HISTORICO_SOLICITUD_CREACION_EXP\"")
@NamedQuery(name="HistoricoSolicitudCreacionExp.findAll", query="SELECT h FROM HistoricoSolicitudCreacionExp h")
public class HistoricoSolicitudCreacionExp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_HISTORICO_SOLICITUD_CREACION_EXP", sequenceName="\"SEQ_ID_HISTORICO_SOLICITUD_CREACION_EXP\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_HISTORICO_SOLICITUD_CREACION_EXP")
	@Column(name="\"ID_HISTORICO_SOLICITUD_CREACION_EXP\"")
	private Long idHistoricoSolicitudCreacionExp;

	@Column(name="\"A_ASUNTO_MATERIA\"")
	private String asuntoMateria;

	@Column(name="\"A_COMENTARIO\"")
	private String comentario;

	@Column(name="\"A_TIPO_ACCION\"")
	private String tipoAccion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA\"")
	private Date fecha;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_ATENCION\"")
	private Date fechaAtencion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_SOLICITUD\"")
	private Date fechaSolicitud;
	
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

	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;

	@Column(name="\"ID_USUARIO_CREADOR_EXPEDIENTE\"")
	private String idUsuarioCreadorExpediente;

	@Column(name="\"ID_USUARIO_DESTINATARIO\"")
	private String idUsuarioDestinatario;

	@Column(name="\"ID_USUARIO_SOLICITANTE\"")
	private String idUsuarioSolicitante;

	//bi-directional many-to-one association to EstadoSolicitudCreacionExp
	@ManyToOne
	@JoinColumn(name="\"ID_SOLICITUD_CREACION_EXP\"")
	private SolicitudCreacionExp solicitudCreacionExp;

	public HistoricoSolicitudCreacionExp() {
	}

	public Long getIdHistoricoSolicitudCreacionExp() {
		return this.idHistoricoSolicitudCreacionExp;
	}

	public void setIdHistoricoSolicitudCreacionExp(Long idHistoricoSolicitudCreacionExp) {
		this.idHistoricoSolicitudCreacionExp = idHistoricoSolicitudCreacionExp;
	}

	public String getAsuntoMateria() {
		return this.asuntoMateria;
	}

	public void setAsuntoMateria(String asuntoMateria) {
		this.asuntoMateria = asuntoMateria;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getTipoAccion() {
		return this.tipoAccion;
	}

	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaAtencion() {
		return this.fechaAtencion;
	}

	public void setFechaAtencion(Date fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}

	public Date getFechaSolicitud() {
		return this.fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	public String getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
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

	public SolicitudCreacionExp getSolicitudCreacionExp() {
		return this.solicitudCreacionExp;
	}

	public void setSolicitudCreacionExp(SolicitudCreacionExp solicitudCreacionExp) {
		this.solicitudCreacionExp = solicitudCreacionExp;
	}

	public EstadoSolicitudCreacionExp getEstadoSolicitudCreacionExp() {
		return estadoSolicitudCreacionExp;
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
		return proceso;
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
	
}