package cl.gob.scj.sgdp.model;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_PROCESOS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_PROCESOS\"")
@NamedQueries({
	@NamedQuery(name="Proceso.findAll", 
		query="SELECT p FROM Proceso p"),
	
	@NamedQuery(name="Proceso.getProcesosPorIdMacroProceso", 
		query="SELECT p FROM Proceso p where p.macroProceso.idMacroProceso =:idMacroProceso and p.vigente =:vigente "
				+ "and 0 < (SELECT count(t.idTarea) FROM Tarea t where t.proceso.idProceso = p.idProceso)"),
	
	@NamedQuery(name="Proceso.buscarTodosProcesoVigente", 
		query=" SELECT p FROM Proceso p where p.vigente =:vigente "
				+ " and 0 < (SELECT count(t.idTarea) FROM Tarea t where t.proceso.idProceso = p.idProceso) "
				+ "	order by p.nombreProceso asc "),
	
	@NamedQuery(name="Proceso.getProcesosPorIdUnidad", 
		query=" SELECT p FROM Proceso p where p.unidad.idUnidad =:idUnidad and p.vigente = :vigente "
				+ " and 0 < (SELECT count(t.idTarea) FROM Tarea t where t.proceso.idProceso = p.idProceso) "
				+ "	order by p.nombreProceso asc "),
	
	@NamedQuery(name="Proceso.getBuscarTodosLosNombresdeLosProcesoVigente", 
	query="SELECT p.nombreProceso FROM Proceso p where p.vigente =:vigente "
			+ " and 0 < (SELECT count(t.idTarea) FROM Tarea t where t.proceso.idProceso = p.idProceso) "
			+ " order by p.nombreProceso ASC "),
	
	@NamedQuery(name="Proceso.getProcesoVigentePorCodigoProceso", 
	query="SELECT p FROM Proceso p WHERE p.vigente = true "
			+ "AND p.codigoProceso = :codigoProceso"),
	
	@NamedQuery(name="Proceso.buscarTodosProceso", 
	query=" SELECT p FROM Proceso p "
			+ " where 0 < (SELECT count(t.idTarea) FROM Tarea t where t.proceso.idProceso = p.idProceso) "
			+ "	order by p.nombreProceso asc,  p.idProceso asc, p.vigente asc "),
	
	@NamedQuery(name="Proceso.buscarTodosLosNombresDeProcesos", 
	query=" SELECT DISTINCT p.nombreProceso FROM Proceso p "
			+ " where 0 < (SELECT count(t.idTarea) FROM Tarea t where t.proceso.idProceso = p.idProceso) "
			+ "	order by p.nombreProceso asc "),
	
	@NamedQuery(name="Proceso.buscarTodosProcesoVigenteOrderPorCod", 
	query=" SELECT p FROM Proceso p where p.vigente =:vigente "
			+ " and 0 < (SELECT count(t.idTarea) FROM Tarea t where t.proceso.idProceso = p.idProceso) "
			+ "	order by p.codigoProceso asc ")
	
})
public class Proceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_PROCESO", sequenceName="\"SEQ_ID_PROCESO\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_PROCESO")
	@Column(name="\"ID_PROCESO\"")
	private long idProceso;

	@Column(name="\"A_DESCRIPCION_PROCESO\"")
	private String descripcionProceso;

	@Column(name="\"A_NOMBRE_PROCESO\"")
	private String nombreProceso;

	@Column(name="\"B_VIGENTE\"")
	private Boolean vigente;

	/*@Column(name="\"ID_MACRO_PROCESO\"")
	private long idMacroProceso;

	@Column(name="\"ID_UNIDAD\"")
	private long idUnidad;*/

	@Column(name="\"N_DIAS_HABILES_MAX_DURACION\"")
	private int diasHabilesMaxDuracion;
	
	@Column(name="\"B_CONFIDENCIAL\"")
	private Boolean esConfidencial;

	//bi-directional many-to-one association to InstanciaDeProceso
	@JsonIgnore
	@OneToMany(mappedBy="proceso", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<InstanciaDeProceso> instanciasDeProcesos;

	//bi-directional many-to-one association to MacroProceso
	@ManyToOne
	@JoinColumn(name="\"ID_MACRO_PROCESO\"")
	private MacroProceso macroProceso;

	//bi-directional many-to-one association to Unidad
	@ManyToOne
	@JoinColumn(name="\"ID_UNIDAD\"")
	private Unidad unidad;

	//bi-directional many-to-one association to Tarea
	@JsonIgnore
	@OneToMany(mappedBy="proceso", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<Tarea> tareas;
	
	@Column(name="\"A_CODIGO_PROCESO\"")
	private String codigoProceso;
	
	//bi-directional many-to-one association to SolicitudCreacionExp
	@OneToMany(mappedBy="proceso")
	private List<SolicitudCreacionExp> solicitudesCreacionExps;
	
	//bi-directional many-to-one association to HistoricoSolicitudCreacionExp
	@OneToMany(mappedBy="proceso")
	private List<HistoricoSolicitudCreacionExp> historicoSolicitudesCreacionExps;

	public Proceso() {
	}

	public long getIdProceso() {
		return this.idProceso;
	}

	public void setIdProceso(long idProceso) {
		this.idProceso = idProceso;
	}

	public String getDescripcionProceso() {
		return this.descripcionProceso;
	}

	public void setDescripcionProceso(String descripcionProceso) {
		this.descripcionProceso = descripcionProceso;
	}

	public String getNombreProceso() {
		return this.nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}
	
	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public int getDiasHabilesMaxDuracion() {
		return this.diasHabilesMaxDuracion;
	}

	public void setDiasHabilesMaxDuracion(int diasHabilesMaxDuracion) {
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
	}

	public List<InstanciaDeProceso> getInstanciasDeProcesos() {
		return this.instanciasDeProcesos;
	}

	public void setInstanciasDeProcesos(List<InstanciaDeProceso> instanciasDeProcesos) {
		this.instanciasDeProcesos = instanciasDeProcesos;
	}

	public InstanciaDeProceso addInstanciasDeProceso(InstanciaDeProceso instanciasDeProceso) {
		getInstanciasDeProcesos().add(instanciasDeProceso);
		instanciasDeProceso.setProceso(this);

		return instanciasDeProceso;
	}

	public InstanciaDeProceso removeInstanciasDeProceso(InstanciaDeProceso instanciasDeProceso) {
		getInstanciasDeProcesos().remove(instanciasDeProceso);
		instanciasDeProceso.setProceso(null);

		return instanciasDeProceso;
	}

	public MacroProceso getMacroProceso() {
		return this.macroProceso;
	}

	public void setMacroProceso(MacroProceso macroProceso) {
		this.macroProceso = macroProceso;
	}

	public Unidad getUnidad() {
		return this.unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public List<Tarea> getTareas() {
		return this.tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	public Tarea addTarea(Tarea tarea) {
		getTareas().add(tarea);
		tarea.setProceso(this);

		return tarea;
	}

	public Tarea removeTarea(Tarea tarea) {
		getTareas().remove(tarea);
		tarea.setProceso(null);

		return tarea;
	}

	public Boolean getEsConfidencial() {
		return esConfidencial;
	}

	public void setEsConfidencial(Boolean esConfidencial) {
		this.esConfidencial = esConfidencial;
	}

	public String getCodigoProceso() {
		return codigoProceso;
	}

	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}	
	
	public List<SolicitudCreacionExp> getSolicitudesCreacionExps() {
		return this.solicitudesCreacionExps;
	}

	public void setSolicitudesCreacionExps(List<SolicitudCreacionExp> solicitudesCreacionExps) {
		this.solicitudesCreacionExps = solicitudesCreacionExps;
	}

	public SolicitudCreacionExp addSolicitudesCreacionExp(SolicitudCreacionExp solicitudesCreacionExp) {
		getSolicitudesCreacionExps().add(solicitudesCreacionExp);
		solicitudesCreacionExp.setProceso(this);

		return solicitudesCreacionExp;
	}

	public SolicitudCreacionExp removeSolicitudesCreacionExp(SolicitudCreacionExp solicitudesCreacionExp) {
		getSolicitudesCreacionExps().remove(solicitudesCreacionExp);
		solicitudesCreacionExp.setProceso(null);

		return solicitudesCreacionExp;
	}
	
	public List<HistoricoSolicitudCreacionExp> getHistoricoSolicitudesCreacionExps() {
		return historicoSolicitudesCreacionExps;
	}

	public void setHistoricoSolicitudesCreacionExps(List<HistoricoSolicitudCreacionExp> historicoSolicitudesCreacionExps) {
		this.historicoSolicitudesCreacionExps = historicoSolicitudesCreacionExps;
	}
	
	public HistoricoSolicitudCreacionExp addHistoricoSolicitudesCreacionExp(HistoricoSolicitudCreacionExp historicoSolicitudCreacionExp) {
		getHistoricoSolicitudesCreacionExps().add(historicoSolicitudCreacionExp);
		historicoSolicitudCreacionExp.setProceso(this);

		return historicoSolicitudCreacionExp;
	}

	public HistoricoSolicitudCreacionExp removeHistoricoSolicitudesCreacionExp(HistoricoSolicitudCreacionExp historicoSolicitudCreacionExp) {
		getHistoricoSolicitudesCreacionExps().remove(historicoSolicitudCreacionExp);
		historicoSolicitudCreacionExp.setProceso(null);

		return historicoSolicitudCreacionExp;
	}	
	
}