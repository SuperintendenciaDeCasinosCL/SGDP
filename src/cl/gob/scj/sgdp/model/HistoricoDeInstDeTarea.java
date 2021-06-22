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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_HISTORICO_DE_INST_DE_TAREAS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_HISTORICO_DE_INST_DE_TAREAS\"")
@NamedQueries({	
	
	@NamedQuery(name="HistoricoDeInstDeTarea.findAll", query="SELECT h FROM HistoricoDeInstDeTarea h"),
	
	@NamedQuery(name="HistoricoDeInstDeTarea.getUltimoHistoricoDeInstDeTareaPorInstanciaDeTareaDeDestino",	
	query="SELECT h FROM HistoricoDeInstDeTarea h WHERE h.instanciaDeTareaDeDestino.idInstanciaDeTarea =:idInstanciaDeTarea "
			+ "and h.idHistoricoDeInstDeTarea = (SELECT MAX(h2.idHistoricoDeInstDeTarea) FROM HistoricoDeInstDeTarea h2 "
			+ "WHERE h2.instanciaDeTareaDeDestino.idInstanciaDeTarea =:idInstanciaDeTarea AND accionHistInstDeTarea.idAccionHistoricoInstDeTarea = 3) "),
	
	@NamedQuery(name="HistoricoDeInstDeTarea.getHistorialDeTareasPorIdIntanciaDeTarea",	
	query="SELECT DISTINCT hi "
			+ "FROM HistoricoDeInstDeTarea hi, AccionesHistInstDeTarea ah "
			+ "WHERE hi.instanciaDeTareaDeOrigen.idInstanciaDeTarea =:idInstanciaDeTarea "
			+ "OR hi.instanciaDeTareaDeDestino.idInstanciaDeTarea =:idInstanciaDeTarea "
			+ "AND hi.accionHistInstDeTarea.idAccionHistoricoInstDeTarea = ah.idAccionHistoricoInstDeTarea "
			+ "AND ah.idAccionHistoricoInstDeTarea != 1 "
			+ "ORDER BY hi.fechaMovimiento ASC "),
			
	@NamedQuery(name="HistoricoDeInstDeTarea.getHistoricoDeInstDeTareaPorIdExpediente",	
	query="SELECT h FROM HistoricoDeInstDeTarea h "
			+ "WHERE (h.instanciaDeTareaDeOrigen.instanciaDeProceso.idExpediente =:idExpediente "
			+ "OR h.instanciaDeTareaDeDestino.instanciaDeProceso.idExpediente =:idExpediente) "
			+ "AND h.accionHistInstDeTarea.idAccionHistoricoInstDeTarea != 1"),
	
	@NamedQuery(name="HistoricoDeInstDeTarea.getHistoricoDeInstDeTareaPorIdExpedienteBusqueda",	
	query="SELECT t.nombreTarea as nombreTareaDeOrigen, h.fechaMovimiento as fechaMovimiento, "
			+ "h.comentario as comentario, a.descAccion as nombreAccion, h.idUsuarioOrigen as idUsuarioOrigen "
			+ ", i.idInstanciaDeTarea as idInstanciaDeTareaDeOrigen, id.idInstanciaDeTarea as idInstanciaDeTareaDeDestino "
			+ ", td.nombreTarea as nombreTareaDeDestino, hu.id.idUsuario as usuariosDestinoString,"
			+ "CASE "
			+ "WHEN (SELECT COUNT(*) FROM h.historicosValorParametroDeTarea hv) > 0 THEN true "
			+ "ELSE false "
			+ "END as tieneHistoricoValorParametroDeTarea, "
			+ "p.nombreExpediente as nombreExpediente, "			
			+ "rto.id.responsabilidad.nombreResponsabilidad as nombreResponsabilidadOrigen, "
			+ "rtd.id.responsabilidad.nombreResponsabilidad as nombreResponsabilidadDestino "			
			+ "FROM HistoricoDeInstDeTarea h, ResponsabilidadTarea rto, ResponsabilidadTarea rtd "
			+ "INNER JOIN h.instanciaDeTareaDeOrigen i "
			+ "INNER JOIN h.instanciaDeTareaDeOrigen.instanciaDeProceso p "
			+ "INNER JOIN h.instanciaDeTareaDeOrigen.tarea t "			
			+ "INNER JOIN h.instanciaDeTareaDeDestino id "
			+ "INNER JOIN h.instanciaDeTareaDeDestino.tarea td "			
			+ "INNER JOIN h.accionHistInstDeTarea a "
			+ "LEFT OUTER JOIN h.historicoUsuariosAsignadosATareas hu "
			+ "WHERE p.idExpediente = :idExpediente "
			+ "AND a.idAccionHistoricoInstDeTarea NOT IN (1, 11) "
			+ "AND rto.id.tarea.idTarea = t.idTarea "
			+ "AND rtd.id.tarea.idTarea = td.idTarea"),
	
	@NamedQuery(name="HistoricoDeInstDeTarea.getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovimiento",	
	query="SELECT h "			
			+ "FROM HistoricoDeInstDeTarea h "			
			+ "WHERE h.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND h.fechaMovimiento = ( "
			+ "SELECT MAX(h2.fechaMovimiento) from HistoricoDeInstDeTarea h2 "
			+ "WHERE h2.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ ")"),
	
	@NamedQuery(name="HistoricoDeInstDeTarea.getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovEnviaODevuelve",	
	query="SELECT h "			
			+ "FROM HistoricoDeInstDeTarea h "			
			+ "WHERE h.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND h.fechaMovimiento = ( "
			+ "SELECT MAX(h2.fechaMovimiento) from HistoricoDeInstDeTarea h2 "
			+ "WHERE h2.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND h2.accionHistInstDeTarea.idAccionHistoricoInstDeTarea in (2, 3, 4) "
			+ ") AND h.accionHistInstDeTarea.idAccionHistoricoInstDeTarea in (2, 3, 4) ) "),
	
	@NamedQuery(name="HistoricoDeInstDeTarea.getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovEnviaODevuelveMenFech",	
	query="SELECT h "			
			+ "FROM HistoricoDeInstDeTarea h "			
			+ "WHERE h.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND h.fechaMovimiento = ( "
			+ "SELECT MAX(h2.fechaMovimiento) from HistoricoDeInstDeTarea h2 "
			+ "WHERE h2.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "AND h2.fechaMovimiento < :fechaMovimiento "
			+ "AND h2.accionHistInstDeTarea.idAccionHistoricoInstDeTarea in (2, 3) "
			+ ") AND h.accionHistInstDeTarea.idAccionHistoricoInstDeTarea in (2, 3) ) "),
	
	@NamedQuery(name="HistoricoDeInstDeTarea.getTodosEnviaDevuelveReasignaHistoricoDeInstDeTareaPorIdInstanciaDeProceso",	
	query="SELECT h "			
			+ "FROM HistoricoDeInstDeTarea h "			
			+ "WHERE ( h.instanciaDeTareaDeOrigen.idInstanciaDeTarea in ( SELECT i.idInstanciaDeTarea FROM InstanciaDeTarea i WHERE i.instanciaDeProceso.idInstanciaDeProceso = :idInstanciaDeProceso ) "
						+ "OR h.instanciaDeTareaDeDestino.idInstanciaDeTarea in ( SELECT i.idInstanciaDeTarea FROM InstanciaDeTarea i WHERE i.instanciaDeProceso.idInstanciaDeProceso = :idInstanciaDeProceso ) "
			+ ") AND h.accionHistInstDeTarea.idAccionHistoricoInstDeTarea in (2, 3, 4) "),
	
	@NamedQuery(name="HistoricoDeInstDeTarea.getHisDeInstDeTareaPorIdInstDeTareaDeDestIdUsrOrigenMaxFechaMov",	
	query="SELECT h "			
			+ "FROM HistoricoDeInstDeTarea h "			
            + "WHERE h.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTarea "
            + "AND h.idUsuarioOrigen = :idUsuarioOrigen "
			+ "AND h.fechaMovimiento = ( "
			+ "SELECT MAX(h2.fechaMovimiento) from HistoricoDeInstDeTarea h2 "
            + "WHERE h2.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTarea "
            + "AND h2.idUsuarioOrigen = :idUsuarioOrigen "
			+ ")"),
	
	@NamedQuery(name="HistoricoDeInstDeTarea.getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeOrigenYDestinoMaxFechaMovimiento",	
	query="SELECT h "			
			+ "FROM HistoricoDeInstDeTarea h "			
			+ "WHERE h.instanciaDeTareaDeOrigen.idInstanciaDeTarea = :idInstanciaDeTareaOrigen "
			+ "AND h.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTareaDestino "			
			+ "AND h.fechaMovimiento = ( "
			+ "SELECT MAX(h2.fechaMovimiento) from HistoricoDeInstDeTarea h2 "
			+ "WHERE h2.instanciaDeTareaDeOrigen.idInstanciaDeTarea = :idInstanciaDeTareaOrigen "
			+ "AND h2.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTareaDestino "
			+ ")"),
	
	@NamedQuery(name="HistoricoDeInstDeTarea.getHistDeInstDeTareaPorIdInstaDeTareaDeOrigenMaxFechaMov",	
	query="SELECT h "			
			+ "FROM HistoricoDeInstDeTarea h "			
			+ "WHERE h.instanciaDeTareaDeOrigen.idInstanciaDeTarea = :idInstanciaDeTareaOrigen "						
			+ "AND h.fechaMovimiento = ( "
			+ "SELECT MAX(h2.fechaMovimiento) from HistoricoDeInstDeTarea h2 "
			+ "WHERE h2.instanciaDeTareaDeOrigen.idInstanciaDeTarea = :idInstanciaDeTareaOrigen "		
			+ ")"),
	
	@NamedQuery(name="HistoricoDeInstDeTarea.getHistoricosDeInstDeTareaPorIdInstanciaDeTareaDeOrigenYDestino",	
	query="SELECT h "			
			+ "FROM HistoricoDeInstDeTarea h "			
			+ "WHERE h.instanciaDeTareaDeOrigen.idInstanciaDeTarea = :idInstanciaDeTareaOrigen "
			+ "AND h.instanciaDeTareaDeDestino.idInstanciaDeTarea = :idInstanciaDeTareaDestino "
			+ ")")
})
public class HistoricoDeInstDeTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_HISTORICO_DE_INST_DE_TAREA", sequenceName="\"SEQ_ID_HISTORICO_DE_INST_DE_TAREA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_HISTORICO_DE_INST_DE_TAREA")
	@Column(name="\"ID_HISTORICO_DE_INST_DE_TAREA\"")
	private Long idHistoricoDeInstDeTarea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_MOVIMIENTO\"")
	private Date fechaMovimiento;

	@Column(name="\"ID_USUARIO_ORIGEN\"")
	private String idUsuarioOrigen;
		
	@Column(name="\"A_COMENTARIO\"")
	private String comentario;

	//bi-directional many-to-one association to AccionesHistInstDeTarea
	@ManyToOne
	@JoinColumn(name = "\"ID_ACCION_HISTORICO_INST_DE_TAREA\"")
	private AccionesHistInstDeTarea accionHistInstDeTarea;

	//bi-directional many-to-one association to InstanciaDeTarea
	@ManyToOne
	@JoinColumn(name = "\"ID_INSTANCIA_DE_TAREA_DE_ORIGEN\"")
	private InstanciaDeTarea instanciaDeTareaDeOrigen;
	
	//bi-directional many-to-one association to InstanciaDeTarea
	@ManyToOne
	@JoinColumn(name = "\"ID_INSTANCIA_DE_TAREA_DESTINO\"")
	private InstanciaDeTarea instanciaDeTareaDeDestino;	
	
	//bi-directional many-to-one association to ArchivosHistInstDeTarea
	@OneToMany(mappedBy="historicoDeInstDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoArchivosInstDeTarea> archivosHistInstDeTareas;
	
	@OneToMany(mappedBy="id.historicoDeInstDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoUsuariosAsignadosATarea> historicoUsuariosAsignadosATareas;
	
	//bi-directional many-to-one association to HistoricoValorParametroDeTarea
	@OneToMany(mappedBy="historicoDeInstDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoValorParametroDeTarea> historicosValorParametroDeTarea;
	
	@Column(name="\"A_MENSAJE_EXCEPCION\"")
	private String mensajeException;
	
	public HistoricoDeInstDeTarea() {
	}

	public Long getIdHistoricoDeInstDeTarea() {
		return this.idHistoricoDeInstDeTarea;
	}

	public void setIdHistoricoDeInstDeTarea(Long idHistoricoDeInstDeTarea) {
		this.idHistoricoDeInstDeTarea = idHistoricoDeInstDeTarea;
	}

	public Date getFechaMovimiento() {
		return this.fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public String getIdUsuarioOrigen() {
		return idUsuarioOrigen;
	}

	public void setIdUsuarioOrigen(String idUsuarioOrigen) {
		this.idUsuarioOrigen = idUsuarioOrigen;
	}

	public AccionesHistInstDeTarea getAccionHistInstDeTarea() {
		return this.accionHistInstDeTarea;
	}

	public void setAccionHistInstDeTarea(AccionesHistInstDeTarea accionHistInstDeTarea) {
		this.accionHistInstDeTarea = accionHistInstDeTarea;
	}

	public InstanciaDeTarea getInstanciaDeTareaDeOrigen() {
		return instanciaDeTareaDeOrigen;
	}

	public void setInstanciaDeTareaDeOrigen(
			InstanciaDeTarea instanciaDeTareaDeOrigen) {
		this.instanciaDeTareaDeOrigen = instanciaDeTareaDeOrigen;
	}
	

	public InstanciaDeTarea getInstanciaDeTareaDeDestino() {
		return instanciaDeTareaDeDestino;
	}

	public void setInstanciaDeTareaDeDestino(
			InstanciaDeTarea instanciaDeTareaDeDestino) {
		this.instanciaDeTareaDeDestino = instanciaDeTareaDeDestino;
	}
	
	public List<HistoricoArchivosInstDeTarea> getArchivosHistInstDeTareas() {
		return this.archivosHistInstDeTareas;
	}

	public void setArchivosHistInstDeTareas(List<HistoricoArchivosInstDeTarea> archivosHistInstDeTareas) {
		this.archivosHistInstDeTareas = archivosHistInstDeTareas;
	}

	public HistoricoArchivosInstDeTarea addArchivosHistInstDeTarea(HistoricoArchivosInstDeTarea archivosHistInstDeTarea) {
		getArchivosHistInstDeTareas().add(archivosHistInstDeTarea);
		archivosHistInstDeTarea.setHistoricoDeInstDeTarea(this);

		return archivosHistInstDeTarea;
	}

	public HistoricoArchivosInstDeTarea removeArchivosHistInstDeTarea(HistoricoArchivosInstDeTarea archivosHistInstDeTarea) {
		getArchivosHistInstDeTareas().remove(archivosHistInstDeTarea);
		archivosHistInstDeTarea.setHistoricoDeInstDeTarea(null);

		return archivosHistInstDeTarea;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public List<HistoricoUsuariosAsignadosATarea> getHistoricoUsuariosAsignadosATareas() {
		return this.historicoUsuariosAsignadosATareas;
	}

	public void setHistoricoUsuariosAsignadosATareas(List<HistoricoUsuariosAsignadosATarea> historicoUsuariosAsignadosATareas) {
		this.historicoUsuariosAsignadosATareas = historicoUsuariosAsignadosATareas;
	}

	public HistoricoUsuariosAsignadosATarea addHistoricoUsuariosAsignadosATarea(HistoricoUsuariosAsignadosATarea historicoUsuariosAsignadosATarea) {
		getHistoricoUsuariosAsignadosATareas().add(historicoUsuariosAsignadosATarea);
		historicoUsuariosAsignadosATarea.getId().setHistoricoDeInstDeTarea(this);

		return historicoUsuariosAsignadosATarea;
	}

	public HistoricoUsuariosAsignadosATarea removeHistoricoUsuariosAsignadosATarea(HistoricoUsuariosAsignadosATarea historicoUsuariosAsignadosATarea) {
		getHistoricoUsuariosAsignadosATareas().remove(historicoUsuariosAsignadosATarea);
		historicoUsuariosAsignadosATarea.getId().setHistoricoDeInstDeTarea(null);

		return historicoUsuariosAsignadosATarea;
	}
	
	public List<HistoricoValorParametroDeTarea> getHistoricosValorParametroDeTarea() {
			return historicosValorParametroDeTarea;
	}

	public void setHistoricosValorParametroDeTarea(List<HistoricoValorParametroDeTarea> historicosValorParametroDeTarea) {
		this.historicosValorParametroDeTarea = historicosValorParametroDeTarea;
	}

	public HistoricoValorParametroDeTarea addHistoricoValorParametroDeTarea(HistoricoValorParametroDeTarea historicoValorParametroDeTarea) {
		this.getHistoricosValorParametroDeTarea().add(historicoValorParametroDeTarea);
		historicoValorParametroDeTarea.setHistoricoDeInstDeTarea(this);
		
		return historicoValorParametroDeTarea;
	}
	
	public HistoricoValorParametroDeTarea removeHistoricoValorParametroDeTarea(HistoricoValorParametroDeTarea historicoValorParametroDeTarea) {
		this.getHistoricosValorParametroDeTarea().remove(historicoValorParametroDeTarea);
		historicoValorParametroDeTarea.setHistoricoDeInstDeTarea(null);
		
		return historicoValorParametroDeTarea;
	}
	
	public String getMensajeException() {
		return mensajeException;
	}

	public void setMensajeException(String mensajeException) {
		this.mensajeException = mensajeException;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idHistoricoDeInstDeTarea == null) ? 0
						: idHistoricoDeInstDeTarea.hashCode());
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
		HistoricoDeInstDeTarea other = (HistoricoDeInstDeTarea) obj;
		if (idHistoricoDeInstDeTarea == null) {
			if (other.idHistoricoDeInstDeTarea != null)
				return false;
		} else if (!idHistoricoDeInstDeTarea
				.equals(other.idHistoricoDeInstDeTarea))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HistoricoDeInstDeTarea ["
				+ "idHistoricoDeInstDeTarea=" + idHistoricoDeInstDeTarea 
				+ ", fechaMovimiento=" + fechaMovimiento 
				+ ", idUsuarioOrigen=" + idUsuarioOrigen
				+ ", comentario=" + comentario 
				+ ", mensajeException=" + mensajeException
				+ ", idInstanciaDeTareaDeOrigen=" + this.instanciaDeTareaDeOrigen.getIdInstanciaDeTarea()
				+ ", idInstanciaDeTareaDeDestino=" + this.instanciaDeTareaDeDestino.getIdInstanciaDeTarea()
				+ ", accion=" + this.accionHistInstDeTarea.getNombreAccion()
				+ "]";
	}

}