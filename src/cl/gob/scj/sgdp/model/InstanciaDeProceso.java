package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_INSTANCIAS_DE_PROCESOS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_INSTANCIAS_DE_PROCESOS\"")
@NamedQueries({
	@NamedQuery(name="InstanciaDeProceso.findAll", query="SELECT i FROM InstanciaDeProceso i"),	
	
	@NamedQuery(name="InstanciaDeProceso.getEtapasDeInstanciaDeProcesoPorIdExpediente", 
				query="SELECT DISTINCT e.idEtapa as idEtapa, e.nombreEtapa as nombreEtapa, "
						+ "CASE "
						+ "WHEN it.estadoDeTarea.codigoEstadoDeTarea != 2 THEN 0 "
						+ "ELSE it.estadoDeTarea.codigoEstadoDeTarea "
						+ "END as codigoEstadoDeTarea " +
				"FROM InstanciaDeProceso ip, InstanciaDeTarea it, Tarea t, Etapa e " +
				"WHERE ip.idExpediente =:idExpediente " +
				"AND ip.idInstanciaDeProceso = it.instanciaDeProceso.idInstanciaDeProceso " +
				"AND ip.proceso.idProceso = t.proceso.idProceso " +
				"AND it.tarea.idTarea = t.idTarea " +
				"AND t.etapa.idEtapa = e.idEtapa "
				+ "ORDER BY e.idEtapa"),
	
	@NamedQuery(name="InstanciaDeProceso.getInstanciaDeProcesoPorIdExpediente", 
				query="SELECT i FROM InstanciaDeProceso i "
						+ "WHERE i.idExpediente =:idExpediente"),
	
	@NamedQuery(name="InstanciaDeProceso.getFechaEstadoInstanciaDeProcesoPorIdExpediente", 
	query="SELECT DISTINCT "
			+ "	to_char(i.fechaInicio,'DD/MM/YYYY') as fechaInicio , "
			+ " to_char(i.fechaFin, 'DD/MM/YYYY')  as fechaFin , "
			//+ " i.estadoDeProceso.nombreEstadoDeProceso  as nombreEstadoDeProceso "
			+ " CASE "
			+ " 	WHEN "
			+ "			(i.estadoDeProceso.idEstadoDeProceso = 2) "
			+ "			AND "
			+ "			( 	SELECT count(g.idInstanciaDeTarea) "
			+ "				FROM InstanciaDeTarea g "
			+ "				INNER JOIN g.tarea t "
			+ "				WHERE g.instanciaDeProceso.idInstanciaDeProceso = i.idInstanciaDeProceso "
			+ "				AND g.estadoDeTarea.idEstadoDeTarea = 2 "
			+ "				AND t.esperarResp = false"
			+ "			) > 0 THEN 'En ejecuci\u00f3n' "
			+ "		WHEN "
			+ "			(i.estadoDeProceso.idEstadoDeProceso = 2) "
			+ "			AND ( "
			+ "					( SELECT count(g.idInstanciaDeTarea) "
			+ "						FROM InstanciaDeTarea g "
			+ "						INNER JOIN g.tarea t "
			+ "						WHERE g.instanciaDeProceso.idInstanciaDeProceso = i.idInstanciaDeProceso "
			+ "						AND g.estadoDeTarea.idEstadoDeTarea = 2 "
			+ "						AND t.esperarResp = true"
			+ "					) "
			+ "				= "
			+ "					( SELECT count(g.idInstanciaDeTarea)"
			+ "						FROM InstanciaDeTarea g "			
			+ "						WHERE g.instanciaDeProceso.idInstanciaDeProceso = i.idInstanciaDeProceso "
			+ "						AND g.estadoDeTarea.idEstadoDeTarea = 2 "
			+ "					) "
			+ "				) THEN 'En espera' "
			+ "		ELSE i.estadoDeProceso.nombreEstadoDeProceso "
			+ " END as nombreEstadoDeProceso "
			+ " FROM InstanciaDeProceso i "
			+ " WHERE i.idExpediente =:idExpediente"),
	
	@NamedQuery(name="InstanciaDeProceso.ConsultaAvanzadaEstadoProceso", 
						query=" SELECT DISTINCT "
								+ " p.nombreProceso  as nombreProceso ,"
								+ "	to_char(ip.fechaInicio, 'DD/MM/YYYY hh24:mi:ss') as fechaInicioProceso ,"
								+ " to_char(CASE WHEN ip.fechaVencimientoUsuario is not null THEN  ip.fechaVencimientoUsuario"
								+ " ELSE ip.fechaVencimiento END ,'DD/MM/YYYY hh24:mi:ss') as plazoTerminoProceso,"
								+ " e.nombreEtapa as etapaActual ,"
								+ " t.nombreTarea as tareaActual , "
								+ " ua.id.idUsuario as usuarioActual , "
								+ " to_char( it.fechaInicio , 'DD/MM/YYYY hh24:mi:ss') as fechaTareaActual , "
								+ " u.codigoUnidad as areaResponsable , "
								+ " ep.nombreEstadoDeProceso  as estadoProceso "
								+ "	FROM  InstanciaDeProceso  ip "
								+ " inner join ip.estadoDeProceso ep "
								+ "	inner join ip.instanciasDeTareas it "
								+ " inner join it.tarea t"
								+ " inner join it.usuariosAsignados ua "
								+ " inner join ip.proceso p "
								+ " inner join p.unidad  u "
								+ " inner join t.etapa e "
								+ " where ip.idExpediente = :idExpediente "
								+ " and it.estadoDeTarea.idEstadoDeTarea = 2 "),
								
	@NamedQuery(name="InstanciaDeProceso.ConsultaBasicaEstadoProceso", 
								query=" SELECT DISTINCT "
										+ " p.nombreProceso  as nombreProceso ,"
										+ "	to_char(ip.fechaInicio, 'DD/MM/YYYY hh24:mi:ss') as fechaInicioProceso ,"
										+ " to_char(CASE WHEN ip.fechaVencimientoUsuario is not null THEN  ip.fechaVencimientoUsuario"
										+ " ELSE ip.fechaVencimiento END ,'DD/MM/YYYY hh24:mi:ss') as plazoTerminoProceso,"
										+ " e.nombreEtapa as etapaActual ,"
										+ " ep.nombreEstadoDeProceso  as estadoProceso "
										+ "	FROM  InstanciaDeProceso  ip "
										+ " inner join ip.estadoDeProceso ep "
										+ "	inner join ip.instanciasDeTareas it "
										+ " inner join it.tarea t"
										+ " inner join it.usuariosAsignados ua "
										+ " inner join ip.proceso p "
										+ " inner join p.unidad  u "
										+ " inner join t.etapa e "
										+ " where ip.idExpediente = :idExpediente "
										+ " and it.estadoDeTarea.idEstadoDeTarea = 2 "),
	
	@NamedQuery(name="InstanciaDeProceso.getInstanciaDeProcesoPorIdInstanciaDeProceso", 
	query="SELECT i FROM InstanciaDeProceso i "
			+ "WHERE i.idInstanciaDeProceso =:idInstanciaDeProceso"),
	
	@NamedQuery(name="InstanciaDeProceso.getInstanciaDeProcesoPorNombreExpediente", 
	query="SELECT i FROM InstanciaDeProceso i "
			+ "WHERE i.nombreExpediente =:nombreExpediente"),
	
	@NamedQuery(name="InstanciaDeProceso.getInstanciaDeProcesoPorNombreExpedienteAPI", 
	query="SELECT i.nombreExpediente, i.fechaInicio, i.fechaVencimiento, i.estadoDeProceso.nombreEstadoDeProceso, "
			+ " it.tarea.etapa.nombreEtapa, i.proceso.nombreProceso "
			+ " FROM InstanciaDeProceso i, InstanciaDeTarea it, HistoricoDeInstDeTarea hit "
			+ " WHERE i.nombreExpediente =:nombreExpediente"
			+ " and it.instanciaDeProceso.idInstanciaDeProceso = i.idInstanciaDeProceso "
			+ " and hit.instanciaDeTareaDeDestino.idInstanciaDeTarea = it.idInstanciaDeTarea"
			+ " order by hit.fechaMovimiento desc")
})

public class InstanciaDeProceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_INSTANCIA_DE_PROCESO", sequenceName="\"SEQ_ID_INSTANCIA_DE_PROCESO\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_INSTANCIA_DE_PROCESO")
	@Column(name="\"ID_INSTANCIA_DE_PROCESO\"")
	private long idInstanciaDeProceso;

	@Column(name="\"A_NOMBRE_EXPEDIENTE\"")
	private String nombreExpediente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_FIN\"")
	private Date fechaFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_INICIO\"")
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_VENCIMIENTO_USUARIO\"")
	private Date fechaVencimientoUsuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_VENCIMIENTO\"")
	private Date fechaVencimiento;

	/*@Column(name="\"ID_ESTADO_DE_PROCESO\"")
	private long idEstadoDeProceso;*/

	@Column(name="\"ID_EXPEDIENTE\"")
	private String idExpediente;

	/*@Column(name="\"ID_PROCESO\"")
	private long idProceso;*/

	/*@Column(name="\"ID_UNIDAD\"")
	private long idUnidad;*/

	@Column(name="\"ID_USUARIO_INICIA\"")
	private String idUsuarioInicia;

	@Column(name="\"ID_USUARIO_TERMINA\"")
	private String idUsuarioTermina;
	
	@Column(name="\"B_TIENE_DOCUMENTOS_EN_CMS\"")
	private Boolean tieneDocumentosEnCMS;
	
	@Column(name="\"A_EMISOR\"")
	private String emisor;
	
	//bi-directional many-to-one association to MacroProceso
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="\"ID_PROCESO\"")
	private Proceso proceso;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="\"ID_ESTADO_DE_PROCESO\"")
	private EstadoDeProceso estadoDeProceso;	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_PROCESO_PADRE\"")
	private InstanciaDeProceso instanciaDeProcesoPadre;
	
	//bi-directional many-to-one association to InstanciaDeTareaLibre
	@JsonIgnore
	@OneToMany(mappedBy="instanciaDeProceso", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<InstanciaDeTarea> instanciasDeTareas;
	
	@Column(name="\"A_ASUNTO\"")
	private String asunto;
	
	//bi-directional many-to-one association to UsuarioAsignado
	@JsonIgnore
	@OneToMany(mappedBy="id.instanciaDeProceso", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<SeguimientoIntanciaProceso> seguimientoIntanciaProceso;
	
	//bi-directional many-to-one association to VinculacionExp
	@OneToMany(mappedBy="instanciaDeProceso", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<VinculacionExp> vinculacionesDeExp;

	//bi-directional many-to-one association to VinculacionExp
	@OneToMany(mappedBy="instanciaDeProcesoAntecesor", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<VinculacionExp> vinculacionesDeExpAntecesores;
	
	//bi-directional many-to-one association to VinculacionExp
	@OneToMany(mappedBy="instanciaDeProceso", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoVinculacionExp> historicoVinculacionesExp;

	//bi-directional many-to-one association to VinculacionExp
	@OneToMany(mappedBy="instanciaDeProcesoAntecesor", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoVinculacionExp> historicoVinculacionesExpAntecesores;
	
	//bi-directional one-to-one association to InstanciasDeProceso
	@OneToOne(mappedBy="instanciaDeProceso")	
	private SolicitudCreacionExp solicitudCreacionExp;
	
	//bi-directional many-to-one association to HistoricoSolicitudCreacionExp
	@OneToMany(mappedBy="instanciaDeProceso", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoSolicitudCreacionExp> historicoSolicitudesCreacionExps;
	
	//bi-directional many-to-one association to Unidad
	@ManyToOne
	@JoinColumn(name="\"ID_UNIDAD\"")
	private Unidad unidad;
	
	public InstanciaDeProceso() {
	}

	public long getIdInstanciaDeProceso() {
		return this.idInstanciaDeProceso;
	}

	public void setIdInstanciaDeProceso(long idInstanciaDeProceso) {
		this.idInstanciaDeProceso = idInstanciaDeProceso;		
	}

	public String getNombreExpediente() {
		return this.nombreExpediente;
	}

	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaVencimientoUsuario() {
		return this.fechaVencimientoUsuario;
	}

	public void setFechaVencimientoUsuario(Date fechaVencimientoUsuario) {
		this.fechaVencimientoUsuario = fechaVencimientoUsuario;
	}

	public String getIdExpediente() {
		return this.idExpediente;
	}

	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}

	public String getIdUsuarioInicia() {
		return idUsuarioInicia;
	}

	public void setIdUsuarioInicia(String idUsuarioInicia) {
		this.idUsuarioInicia = idUsuarioInicia;
	}

	public String getIdUsuarioTermina() {
		return idUsuarioTermina;
	}

	public void setIdUsuarioTermina(String idUsuarioTermina) {
		this.idUsuarioTermina = idUsuarioTermina;
	}

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public EstadoDeProceso getEstadoDeProceso() {
		return estadoDeProceso;
	}

	public void setEstadoDeProceso(EstadoDeProceso estadoDeProceso) {
		this.estadoDeProceso = estadoDeProceso;
	}

	public InstanciaDeProceso getInstanciaDeProcesoPadre() {
		return instanciaDeProcesoPadre;
	}

	public void setInstanciaDeProcesoPadre(
			InstanciaDeProceso instanciaDeProcesoPadre) {
		this.instanciaDeProcesoPadre = instanciaDeProcesoPadre;
	}
	
	public InstanciaDeTarea addInstanciasDeTarea(InstanciaDeTarea instanciasDeTarea) {
		getInstanciasDeTareas().add(instanciasDeTarea);
		instanciasDeTarea.setInstanciaDeProceso(this);

		return instanciasDeTarea;
	}

	public InstanciaDeTarea removeInstanciasDeTarea(InstanciaDeTarea instanciasDeTarea) {
		getInstanciasDeTareas().remove(instanciasDeTarea);
		instanciasDeTarea.setInstanciaDeProceso(null);

		return instanciasDeTarea;
	}

	public List<InstanciaDeTarea> getInstanciasDeTareas() {
		return instanciasDeTareas;
	}

	public void setInstanciasDeTareas(List<InstanciaDeTarea> instanciasDeTareas) {
		this.instanciasDeTareas = instanciasDeTareas;
	}	

	public Boolean getTieneDocumentosEnCMS() {
		return tieneDocumentosEnCMS;
	}

	public void setTieneDocumentosEnCMS(Boolean tieneDocumentosEnCMS) {
		this.tieneDocumentosEnCMS = tieneDocumentosEnCMS;
	}
	
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}	
	
	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}	
	
	public List<SeguimientoIntanciaProceso> getSeguimientoIntanciaProceso() {
		return seguimientoIntanciaProceso;
	}

	public void setSeguimientoIntanciaProceso(
			List<SeguimientoIntanciaProceso> seguimientoIntanciaProceso) {
		this.seguimientoIntanciaProceso = seguimientoIntanciaProceso;
	}
	
	public List<VinculacionExp> getVinculacionesDeExp() {
		return vinculacionesDeExp;
	}

	public void setVinculacionesDeExp(List<VinculacionExp> vinculacionesDeExp) {
		this.vinculacionesDeExp = vinculacionesDeExp;
	}
	
	public VinculacionExp addVinculacionExp(VinculacionExp vinculacionExp) {
		getVinculacionesDeExp().add(vinculacionExp);
		vinculacionExp.setInstanciaDeProceso(this);

		return vinculacionExp;
	}

	public VinculacionExp removeVinculacionExp(VinculacionExp vinculacionExp) {
		getVinculacionesDeExp().remove(vinculacionExp);
		vinculacionExp.setInstanciaDeProceso(null);

		return vinculacionExp;
	}

	public List<VinculacionExp> getVinculacionesDeExpAntecesores() {
		return vinculacionesDeExpAntecesores;
	}

	public void setVinculacionesDeExpAntecesores(List<VinculacionExp> vinculacionesDeExpAntecesores) {
		this.vinculacionesDeExpAntecesores = vinculacionesDeExpAntecesores;
	}
	
	public VinculacionExp addVinculacionExpAntecesores(VinculacionExp vinculacionExp) {
		getVinculacionesDeExpAntecesores().add(vinculacionExp);
		vinculacionExp.setInstanciaDeProcesoAntecesor(this);

		return vinculacionExp;
	}

	public VinculacionExp removeVinculacionExpAntecesores(VinculacionExp vinculacionExp) {
		getVinculacionesDeExpAntecesores().remove(vinculacionExp);
		vinculacionExp.setInstanciaDeProcesoAntecesor(null);

		return vinculacionExp;
	}	

	public List<HistoricoVinculacionExp> getHistoricoVinculacionesExp() {
		return historicoVinculacionesExp;
	}

	public void setHistoricoVinculacionesExp(List<HistoricoVinculacionExp> historicoVinculacionesExp) {
		this.historicoVinculacionesExp = historicoVinculacionesExp;
	}
	
	public HistoricoVinculacionExp addVinculacionExp(HistoricoVinculacionExp historicoVinculacionExp) {
		getHistoricoVinculacionesExp().add(historicoVinculacionExp);
		historicoVinculacionExp.setInstanciaDeProceso(this);

		return historicoVinculacionExp;
	}

	public HistoricoVinculacionExp removeVinculacionExp(HistoricoVinculacionExp historicoVinculacionExp) {
		getHistoricoVinculacionesExp().remove(historicoVinculacionExp);
		historicoVinculacionExp.setInstanciaDeProceso(null);

		return historicoVinculacionExp;
	}

	public List<HistoricoVinculacionExp> getHistoricoVinculacionesExpAntecesores() {
		return historicoVinculacionesExpAntecesores;
	}

	public void setHistoricoVinculacionesExpAntecesores(
			List<HistoricoVinculacionExp> historicoVinculacionesExpAntecesores) {
		this.historicoVinculacionesExpAntecesores = historicoVinculacionesExpAntecesores;
	}
	
	public HistoricoVinculacionExp addHistoricoVinculacionesExpAntecesores(HistoricoVinculacionExp historicoVinculacionExp) {
		getHistoricoVinculacionesExpAntecesores().add(historicoVinculacionExp);
		historicoVinculacionExp.setInstanciaDeProceso(this);

		return historicoVinculacionExp;
	}

	public HistoricoVinculacionExp removeHistoricoVinculacionesExpAntecesores(HistoricoVinculacionExp historicoVinculacionExp) {
		getHistoricoVinculacionesExpAntecesores().remove(historicoVinculacionExp);
		historicoVinculacionExp.setInstanciaDeProceso(null);

		return historicoVinculacionExp;
	}
	
	public List<HistoricoSolicitudCreacionExp> getHistoricoSolicitudesCreacionExps() {
		return historicoSolicitudesCreacionExps;
	}

	public void setHistoricoSolicitudesCreacionExps(List<HistoricoSolicitudCreacionExp> historicoSolicitudesCreacionExps) {
		this.historicoSolicitudesCreacionExps = historicoSolicitudesCreacionExps;
	}
	
	public HistoricoSolicitudCreacionExp addHistoricoSolicitudesCreacionExp(HistoricoSolicitudCreacionExp historicoSolicitudCreacionExp) {
		getHistoricoSolicitudesCreacionExps().add(historicoSolicitudCreacionExp);
		historicoSolicitudCreacionExp.setInstanciaDeProceso(this);

		return historicoSolicitudCreacionExp;
	}

	public HistoricoSolicitudCreacionExp removeHistoricoSolicitudesCreacionExp(HistoricoSolicitudCreacionExp historicoSolicitudCreacionExp) {
		getHistoricoSolicitudesCreacionExps().remove(historicoSolicitudCreacionExp);
		historicoSolicitudCreacionExp.setInstanciaDeProceso(null);

		return historicoSolicitudCreacionExp;
	}	
	
	public SolicitudCreacionExp getSolicitudCreacionExp() {
		return solicitudCreacionExp;
	}

	public void setSolicitudCreacionExp(SolicitudCreacionExp solicitudCreacionExp) {
		this.solicitudCreacionExp = solicitudCreacionExp;
	}
	
	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idInstanciaDeProceso ^ (idInstanciaDeProceso >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstanciaDeProceso other = (InstanciaDeProceso) obj;
		if (idInstanciaDeProceso != other.idInstanciaDeProceso)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InstanciaDeProceso [idInstanciaDeProceso="
				+ idInstanciaDeProceso + ", nombreExpediente="
				+ nombreExpediente + ", fechaFin=" + fechaFin
				+ ", fechaInicio=" + fechaInicio + ", fechaVencimientoUsuario="
				+ fechaVencimientoUsuario + ", fechaVencimiento="
				+ fechaVencimiento + ", idExpediente=" + idExpediente
				+ ", idUsuarioInicia=" + idUsuarioInicia
				+ ", idUsuarioTermina=" + idUsuarioTermina
				+ ", tieneDocumentosEnCMS=" + tieneDocumentosEnCMS
				+ ", estadoDeProceso=" + estadoDeProceso
				+ ", emisor=" + emisor 
				+ ", asunto=" + asunto 
				+"]";
	}
	
}