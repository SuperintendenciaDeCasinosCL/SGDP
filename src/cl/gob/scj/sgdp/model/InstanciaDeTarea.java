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

import com.fasterxml.jackson.annotation.JsonIgnore;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_INSTANCIAS_DE_TAREAS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_INSTANCIAS_DE_TAREAS\"")
//@NamedQuery(name="InstanciaDeTarea.findAll", query="SELECT i FROM InstanciaDeTarea i")
@NamedQueries({
	
	@NamedQuery(name="InstanciaDeTarea.findAll", query="SELECT i FROM InstanciaDeTarea i"),
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciaDeTareasSiLasAnterioresAsignadasEstanFinalizadas", 
	query="SELECT i from InstanciaDeTarea i, ReferenciaDeTarea r"
	+ " WHERE i.idInstanciaDeTarea = :idInstanciaDeTarea"
	+ " AND i.tarea.idTarea = r.tareaSiguiente.idTarea"
	+ "		AND "
	+ " ("
	+ "		SELECT COUNT(*) from InstanciaDeTarea ia "
	+ "		WHERE ia.tarea.idTarea = r.tarea.idTarea "
	+ "		AND ia.instanciaDeProceso.idInstanciaDeProceso = i.instanciaDeProceso.idInstanciaDeProceso "
	+ "		AND 1 >= (SELECT COUNT(*) FROM HistoricoDeInstDeTarea hi WHERE ia.idInstanciaDeTarea = hi.instanciaDeTareaDeOrigen.idInstanciaDeTarea ) "
	+ " ) = "
	+ " ( "
	+ "		SELECT COUNT(*) from InstanciaDeTarea iaf "
	+ "		WHERE iaf.tarea.idTarea = r.tarea.idTarea "
	+ "		AND iaf.instanciaDeProceso.idInstanciaDeProceso = i.instanciaDeProceso.idInstanciaDeProceso "
	+ "		AND 1 >= (SELECT COUNT(*) FROM HistoricoDeInstDeTarea hif WHERE iaf.idInstanciaDeTarea = hif.instanciaDeTareaDeOrigen.idInstanciaDeTarea ) "
	+ "		AND iaf.estadoDeTarea.idEstadoDeTarea = 3 "
	+ ")"),	
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareaPorIdUsrNombreEstadoTarea", 
				query="SELECT i from InstanciaDeTarea i, UsuarioAsignado u where i.idInstanciaDeTarea = u.id.instanciaDeTarea.idInstanciaDeTarea "
			+ "and i.estadoDeTarea.nombreEstadoDeTarea =:nombreEstadoDeTarea and u.id.idUsuario =:idUsuario "),
		
	@NamedQuery(name="InstanciaDeTarea.getEstadosDeInstProcPorIdUsrNombreEstadoTarea", 
				query="SELECT "					
					+ "i.idInstanciaDeTarea as idInstanciaDeTarea, "						
					+ " CASE "
					+ " 	WHEN "
					+ "			(i.instanciaDeProceso.estadoDeProceso.idEstadoDeProceso = 2) "
					+ "			AND "
					+ "			( 	SELECT count(g.idInstanciaDeTarea) "
					+ "				FROM InstanciaDeTarea g "
					+ "				INNER JOIN g.tarea t "
					+ "				WHERE g.instanciaDeProceso.idInstanciaDeProceso = i.instanciaDeProceso.idInstanciaDeProceso "
					+ "				AND g.estadoDeTarea.idEstadoDeTarea = 2 "
					+ "				AND t.esperarResp = false"
					+ "			) > 0 THEN 'En ejecuci\u00f3n' "
					+ "		WHEN "
					+ "			(i.instanciaDeProceso.estadoDeProceso.idEstadoDeProceso = 2) "
					+ "			AND ( "
					+ "					( SELECT count(g.idInstanciaDeTarea) "
					+ "						FROM InstanciaDeTarea g "
					+ "						INNER JOIN g.tarea t "
					+ "						WHERE g.instanciaDeProceso.idInstanciaDeProceso = i.instanciaDeProceso.idInstanciaDeProceso "
					+ "						AND g.estadoDeTarea.idEstadoDeTarea = 2 "
					+ "						AND t.esperarResp = true"
					+ "					) "
					+ "				= "
					+ "					( SELECT count(g.idInstanciaDeTarea)"
					+ "						FROM InstanciaDeTarea g "			
					+ "						WHERE g.instanciaDeProceso.idInstanciaDeProceso = i.instanciaDeProceso.idInstanciaDeProceso "
					+ "						AND g.estadoDeTarea.idEstadoDeTarea = 2 "
					+ "					) "
					+ "				) THEN 'En espera' "
					+ "		ELSE i.instanciaDeProceso.estadoDeProceso.nombreEstadoDeProceso "
					+ " END as nombreEstadoHomologadoDeInstProceso "
					+ "FROM InstanciaDeTarea i, UsuarioAsignado u where i.idInstanciaDeTarea = u.id.instanciaDeTarea.idInstanciaDeTarea "
					+ "AND i.estadoDeTarea.nombreEstadoDeTarea =:nombreEstadoDeTarea and u.id.idUsuario =:idUsuario "),	
			
	// Query para buscar las instancias de tareas que se encuentren en espera de respuesta
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareaPorIdUsrNombreEstadoTareaPorEsperaRespuesta", 
	query=" SELECT i from InstanciaDeTarea i, UsuarioAsignado u, Tarea t "
		+ " where i.idInstanciaDeTarea = u.id.instanciaDeTarea.idInstanciaDeTarea "
		+ " and i.estadoDeTarea.nombreEstadoDeTarea =:nombreEstadoDeTarea "
		+ " and u.id.idUsuario =:idUsuario "
		+ " and t.idTarea = i.tarea.id "
	    + " and t.esperarResp = :esperaRespuesta "
		),
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareaPorIdUsrIdRolNombreEstadoTarea", 
				query="SELECT i from InstanciaDeTarea i, UsuarioAsignado u, Tarea t, TareaRol tr, UsuarioRol ur "
						+ "where i.idInstanciaDeTarea = u.id.instanciaDeTarea.idInstanciaDeTarea "
						+ "and i.tarea.idTarea = t.idTarea and t.idTarea = tr.id.tarea.idTarea and tr.id.rol.idRol = ur.rol.idRol "
						+ "and ur.idUsuario = u.id.idUsuario "		
						+ "and tr.id.rol.idRol =:idRol and i.estadoDeTarea.nombreEstadoDeTarea =:nombreEstadoDeTarea and u.id.idUsuario =:idUsuario "),						
						
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasQueContinuanDeInstanciaDeTarea", 
				query="SELECT its from InstanciaDeTarea i, Tarea t, ReferenciaDeTarea r, Tarea ts, InstanciaDeTarea its"
						+ " where i.tarea.idTarea = t.idTarea and t.idTarea = r.tarea.idTarea "
						+ " and r.tareaSiguiente.idTarea = ts.idTarea and its.tarea.idTarea = ts.idTarea "
			            + " and i.idInstanciaDeTarea =:idInstanciaDeTarea and i.instanciaDeProceso.idInstanciaDeProceso = its.instanciaDeProceso.idInstanciaDeProceso "),
			            
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasEnEjecucion", 
				query="SELECT i from InstanciaDeTarea i, UsuarioAsignado u "
			+ " where i.idInstanciaDeTarea = u.id.instanciaDeTarea.idInstanciaDeTarea "
			+ " and i.estadoDeTarea.idEstadoDeTarea <>:idEstadoFinalizada "
			+ " and u.id.idUsuario <>:idUsuario "
			+ " and i in ("
			+ " select h.instanciaDeTareaDeDestino from HistoricoDeInstDeTarea h  "
			+ " where h.idUsuarioOrigen =:idUsuario "
			+ " ) "),
			
	@NamedQuery(name="InstanciaDeTarea.getTodasInstanciasDeTareasEnEjecucion", 
				query="select i from InstanciaDeTarea i where i.estadoDeTarea.idEstadoDeTarea = 2"),
				
	@NamedQuery(name="InstanciaDeTarea.getNotificacionesSeguimientosPorUsuario", 
				query= " select i from InstanciaDeTarea i "
						+ " inner join i.instanciaDeProceso ip "
						+ " inner join i.tarea t "
						+ " inner join ip.seguimientoIntanciaProceso sp "
						+ " left join i.usuariosAsignados ua "
						+ "	where sp.id.idUsuario = :idUsuario "
						+ " and i.estadoDeTarea.idEstadoDeTarea in (3,4) "
						+ "	and t.esUltimaTarea = true "
						+ "  "),			
	
			@NamedQuery(name="InstanciaDeTarea.getNotificacionesSeguimientosPorUsuarioUnion", 
			query=" select i from InstanciaDeTarea i "
					+ " inner join i.instanciaDeProceso ip "
					+ " inner join ip.seguimientoIntanciaProceso sp "
					+ " left join i.usuariosAsignados ua "
				//	+ " where ip.estadoDeProceso.idEstadoDeProceso = 2 "
					+ "	where sp.id.idUsuario = :idUsuario "
					+ " and i.estadoDeTarea.idEstadoDeTarea = 2 "
					+ "  "),							
						
						
	@NamedQuery(name="InstanciaDeTarea.getTodasInstanciasDeTareasEnEjecucionPorIdUnidad", 
				query="SELECT i from InstanciaDeTarea i "
						+ "inner join i.instanciaDeProceso.proceso.unidad u "
						+ "where i.estadoDeTarea.idEstadoDeTarea = 2 "
						+ "and u.idUnidad = :idUnidad "),						
						/*+ "and i.idInstanciaDeTarea in ( select u.id.instanciaDeTarea.idInstanciaDeTarea "				
						+ "from UsuarioAsignado u, UsuarioRol ur where u.id.idUsuario = ur.idUsuario "
						+ "and ur.unidad.idUnidad =:idUnidad )"),*/				
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasPorIdExpediente", 
				query="select it from InstanciaDeTarea it, InstanciaDeProceso ip "
						+ "where it.instanciaDeProceso.idInstanciaDeProceso = ip.idInstanciaDeProceso "
						+ "and ip.idExpediente =:idExpediente "),
						
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasPorIdExpedienteAsignadas", 	
			query="select it from InstanciaDeTarea it, InstanciaDeProceso ip "
					+ "where it.instanciaDeProceso.idInstanciaDeProceso = ip.idInstanciaDeProceso "					
					+ "and it.estadoDeTarea.idEstadoDeTarea = 2 "					
					+ "and ip.idExpediente =:idExpediente "),	
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasPorIdExpedienteAsignadasEnEspera", 	
	query="select it from InstanciaDeTarea it, InstanciaDeProceso ip, Tarea t "
			+ "where it.instanciaDeProceso.idInstanciaDeProceso = ip.idInstanciaDeProceso "
			+ "and it.estadoDeTarea.idEstadoDeTarea = 2 "
			+ "and it.tarea.id = t.idTarea "
			+ "and t.esperarResp = true "
			+ "and ip.idExpediente =:idExpediente "),
					
	@NamedQuery(name="InstanciaDeTarea.buscarHistorialDeDocumento", 
	query=" SELECT (case when i2.fechaVencimiento is not null then i2.fechaVencimiento else i2.fechaVencimientoUsuario end ) as fecha ,"
			+ " t2.nombreTarea as nombreTarea ,"
			+ " i2.idUsuarioQueAsigna  as idUsuarioQueAsigna, "
			+ " ( 	select h.comentario from HistoricoDeInstDeTarea h "
			+ "  	 where h.idHistoricoDeInstDeTarea = i2.idInstanciaDeTarea "
			+ "      and h.fechaMovimiento = ( "
			+ "							    select max(hh.fechaMovimiento) from HistoricoDeInstDeTarea hh"
			+ "								where hh.idHistoricoDeInstDeTarea = i2.idInstanciaDeTarea"
			+ "								)  "
			+ ") as comentario ,"
			+ " td.nombreDeTipoDeDocumento as  nombreDeTipoDeDocumento, "
			+ " i2.idInstanciaDeTarea  as idInstanciaDeTarea "
		+ " FROM InstanciaDeTarea i2" 
		+ " inner join i2.tarea as t2 "
		+ " LEFT JOIN  t2.documentosDeSalidasDeTarea  as d" 
		+ "	LEFT JOIN  d.id.tipoDeDocumento as td "
		+ " where  t2.idTarea <= "
		+ " ( "
		+ " select t.idTarea "
		+ " from  InstanciaDeTarea i "
		+ " inner join i.tarea as t"
		+ "	where  i.idInstanciaDeTarea = :idInstanciaDeTarea "
		+ " and  t.proceso.idProceso = t2.idTarea.proceso.idProceso "
		+ " and i.instanciaDeProceso.idInstanciaDeProceso = i2.instanciaDeProceso.idInstanciaDeProceso "
		+ " ) " ),
	
	@NamedQuery(name="InstanciaDeTarea.getPrimeraInstanciaDeTareaPorId", 	
	query="select i from InstanciaDeTarea i, InstanciaDeTarea i2, Tarea t "
			+ "where i.instanciaDeProceso.idInstanciaDeProceso = i2.instanciaDeProceso.idInstanciaDeProceso "
			+ "and i2.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "and i.tarea.idTarea = t.idTarea "
			+ "and t.orden = "
			+ "( select min (t.orden) from InstanciaDeTarea i, InstanciaDeTarea i2, Tarea t "
			+ "where i.instanciaDeProceso.idInstanciaDeProceso = i2.instanciaDeProceso.idInstanciaDeProceso "
			+ "and i2.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "and i.tarea.idTarea = t.idTarea "
			+ "and t.orden <= "
			+ "( select t.orden from InstanciaDeTarea y, Tarea t2 "
			+ "where y.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "and y.tarea.idTarea = t2.idTarea "
			+ ")"
			+ ")"),
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasPorIdNoFinalizadasConAntAnd", 	
	query="select i from InstanciaDeTarea i "
			+ "inner join i.tarea t "
			+ "where t.orden < "
			+ "( "
			+ "select t2.orden from InstanciaDeTarea i2 "
			+ "inner join i2.tarea t2 "
			+ "where i2.instanciaDeProceso.idInstanciaDeProceso = i.instanciaDeProceso.idInstanciaDeProceso "
			+ "and i2.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ ") "
			+ "and i.estadoDeTarea.idEstadoDeTarea = 2 "
			+ "and t.orden in "
			+ "( "
			+ "select t3.orden + 1 from InstanciaDeTarea i3 "
			+ "inner join i3.tarea t3 "
			+ "where i3.instanciaDeProceso.idInstanciaDeProceso = i.instanciaDeProceso.idInstanciaDeProceso "
			+ "and t3.tipoDeBifurcacion = 'AND' "
			+ ")"),
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasPorIdExpedienteBusqueda", 	
	query="select it.idInstanciaDeTarea as idInstanciaDeTarea, "
			+ "t.nombreTarea as nombreDeTarea, "
			+ "case "
			+ "when it.fechaVencimientoUsuario is not null then it.fechaVencimientoUsuario "
			+ "else it.fechaVencimiento "
			+ "end as fechaVencimientoInstanciaDeTarea, "
			+ "( "
			+ "select h.comentario from HistoricoDeInstDeTarea h "
			+ "where h.instanciaDeTareaDeOrigen.idInstanciaDeTarea = it.idInstanciaDeTarea "
			+ "and h.fechaMovimiento = ("
			+ 							"select max(h2.fechaMovimiento) from HistoricoDeInstDeTarea h2 "
			+ 							"where h2.instanciaDeTareaDeOrigen.idInstanciaDeTarea = it.idInstanciaDeTarea "
			+ 							") "
			+ ") as ultimoComentario, "
			+ "t.diasHabilesMaxDuracion as diasHabilesMaxDuracion " 
			+ "from InstanciaDeTarea it "
			+ "inner join it.instanciaDeProceso ip "
			+ "inner join it.tarea t "
			+ "where ip.idExpediente = :idExpediente "),
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasPorIdExpedienteAsignadasUsuario", 	
			query="select it from InstanciaDeTarea it, InstanciaDeProceso ip , UsuarioAsignado u "
						+ " where it.instanciaDeProceso.idInstanciaDeProceso = ip.idInstanciaDeProceso "
						+ " and it.estadoDeTarea.idEstadoDeTarea = 2 "				
						+ " and ip.idExpediente = :idExpediente "
						+ " and u.id.instanciaDeTarea.idInstanciaDeTarea = it.idInstanciaDeTarea "				
						+ " and u.id.idUsuario = :idUsuario " ),
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasPorIdExpedienteUsuarioFinalizada", 	
	query="select it from HistoricoUsuariosAsignadosATarea hu "
			+ "inner join hu.id.historicoDeInstDeTarea hi "
			+ "inner join hi.instanciaDeTareaDeOrigen it "
			+ "inner join it.instanciaDeProceso ip "
				+ "where it.estadoDeTarea.idEstadoDeTarea = 3 "				
				+ "and ip.idExpediente = :idExpediente "							
				+ "and hu.id.idUsuario = :idUsuario " ),
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasAnterioresAsignadasPorIdInstanciaDeProcesoIdInstanciaDeTarea", 	
	query="select it from InstanciaDeTarea it "
			+ "inner join it.tarea t "
			+ "inner join it.instanciaDeProceso ip "
			+ "where ip.idInstanciaDeProceso = :idInstanciaDeProceso "
			+ "and it.estadoDeTarea.idEstadoDeTarea = 2 "				
			+ "and t.idTarea in ( "
			+ "select s2.idTarea from InstanciaDeTarea i, ReferenciaDeTarea r, UsuarioAsignado u, Tarea s, Tarea s2 "
			+ "where i.idInstanciaDeTarea = :idInstanciaDeTarea "
			+ "and i.tarea.idTarea = s.idTarea "
			+ "and r.tareaSiguiente.idTarea = s.idTarea "
			+ "and s2.idTarea = r.tarea.idTarea "
			+ "and u.id.instanciaDeTarea.idInstanciaDeTarea = i.idInstanciaDeTarea "	
			+ ") " ) ,
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasAsignadasPorIdInstanciaDeProcesoDistintasDeIdInstanciaDeTarea", 	
	query="select it from InstanciaDeTarea it "			
			+ "inner join it.instanciaDeProceso ip "
			+ "where ip.idInstanciaDeProceso = :idInstanciaDeProceso "
			+ "and it.estadoDeTarea.idEstadoDeTarea = 2 "				
			+ "and it.idInstanciaDeTarea != :idInstanciaDeTarea " ) ,
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasNuevasQueContinuanDeInstanciaDeTarea", 
	query="SELECT its from InstanciaDeTarea i, Tarea t, ReferenciaDeTarea r, Tarea ts, InstanciaDeTarea its "
			+ "where i.tarea.idTarea = t.idTarea "
			+ "and t.idTarea = r.tarea.idTarea "
			+ "and r.tareaSiguiente.idTarea = ts.idTarea "
			+ "and its.tarea.idTarea = ts.idTarea "
            + "and i.idInstanciaDeTarea =:idInstanciaDeTarea "
            + "and its.estadoDeTarea.idEstadoDeTarea = 1 "
            + "and i.instanciaDeProceso.idInstanciaDeProceso = its.instanciaDeProceso.idInstanciaDeProceso ") ,
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasOrdenUnoPorIdExpedienteAsignadas", 	
	query="select it from InstanciaDeTarea it, InstanciaDeProceso ip "
			+ "where it.instanciaDeProceso.idInstanciaDeProceso = ip.idInstanciaDeProceso "
			+ "and it.estadoDeTarea.idEstadoDeTarea = 2 "					
			+ "and ip.idExpediente =:idExpediente "
			+ "and it.tarea.orden = 1"),
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciasDeTareasAnterioresPorIdInstanciaDeTareaDeDestino",	
	query="SELECT DISTINCT (h.instanciaDeTareaDeOrigen) FROM HistoricoDeInstDeTarea h WHERE h.instanciaDeTareaDeDestino.idInstanciaDeTarea =:idInstanciaDeTarea "),
	
	@NamedQuery(name="InstanciaDeTarea.getInstanciaDeTareaPorIdDiagramaTareaNombreExpediente",	
	query="SELECT it FROM InstanciaDeTarea it "
			+ "WHERE it.tarea.idDiagrama = :idDiagramaTarea "
			+ "AND it.instanciaDeProceso.nombreExpediente = :nombreExpediente ")
	
})
public class InstanciaDeTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_INSTANCIA_DE_TAREA", sequenceName="\"SEQ_ID_INSTANCIA_DE_TAREA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_INSTANCIA_DE_TAREA")
	@Column(name="\"ID_INSTANCIA_DE_TAREA\"")
	private long idInstanciaDeTarea;

	@Column(name="\"A_RAZON_ANULACION\"")
	private String razonAnulacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_ANULACION\"")
	private Date fechaAnulacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_ASIGNACION\"")
	private Date fechaAsignacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_FINALIZACION\"")
	private Date fechaFinalizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_INICIO\"")
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_VENCIMIENTO\"")
	private Date fechaVencimiento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_VENCIMIENTO_USUARIO\"")
	private Date fechaVencimientoUsuario;
	
	//bi-directional many-to-one association to EstadoDeTarea
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="\"ID_ESTADO_DE_TAREA\"")
	private EstadoDeTarea estadoDeTarea;

	//bi-directional many-to-one association to InstanciaDeProceso
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_PROCESO\"")
	private InstanciaDeProceso instanciaDeProceso;
	
	//bi-directional many-to-one association to InstanciaDeTareaLibre
	@JsonIgnore
	@OneToMany(mappedBy="instanciaDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<InstanciaDeTareaLibre> instanciasDeTareasLibres;
	
	//bi-directional many-to-one association to Tarea
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="\"ID_TAREA\"")
	private Tarea tarea;	
	
	//bi-directional many-to-one association to UsuarioAsignado
	@JsonIgnore
	@OneToMany(mappedBy="id.instanciaDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<UsuarioAsignado> usuariosAsignados;
	
	//bi-directional many-to-one association to HistoricoDeInstDeTarea
	@OneToMany(mappedBy="instanciaDeTareaDeOrigen", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoDeInstDeTarea> historicosDeInstDeTareasDeOrigen;
	
	//bi-directional many-to-one association to HistoricoDeInstDeTarea
	@OneToMany(mappedBy="instanciaDeTareaDeDestino", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoDeInstDeTarea> historicosDeInstDeTareasDeDestino;

	@Column(name="\"ID_USUARIO_QUE_ASIGNA\"")
	private String idUsuarioQueAsigna;
	
	//bi-directional many-to-one association to HistoricoFirma
	@JsonIgnore
	@OneToMany(mappedBy="instanciaDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoFirma> historicoFirmas;
	
	//bi-directional many-to-one association to InstanciaDeTareaLibre
	@JsonIgnore
	@OneToMany(mappedBy="instanciaDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<ArchivosInstDeTarea> archivosInstDeTarea;
	
	//bi-directional many-to-one association to InstanciaDeTareaLibre
	@JsonIgnore
	@OneToMany(mappedBy="instanciaDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<HistoricoFechaVencimientoInstanciaProceso> historicosFechaVencimientoInstanciaProceso;
	
	//bi-directional many-to-one association to ValorParametroDeTarea
	@OneToMany(mappedBy="instanciaDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<ValorParametroDeTarea> valorParametrosDeTarea;
		
	public InstanciaDeTarea() {
	}

	public long getIdInstanciaDeTarea() {
		return this.idInstanciaDeTarea;
	}

	public void setIdInstanciaDeTarea(long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
	}

	public String getRazonAnulacion() {
		return this.razonAnulacion;
	}

	public void setRazonAnulacion(String razonAnulacion) {
		this.razonAnulacion = razonAnulacion;
	}

	public Date getFechaAnulacion() {
		return this.fechaAnulacion;
	}

	public void setFechaAnulacion(Date fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}

	public Date getFechaAsignacion() {
		return this.fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Date getFechaFinalizacion() {
		return this.fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaVencimiento() {
		return this.fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}	
	
	public Date getFechaVencimientoUsuario() {
		return fechaVencimientoUsuario;
	}

	public void setFechaVencimientoUsuario(Date fechaVencimientoUsuario) {
		this.fechaVencimientoUsuario = fechaVencimientoUsuario;
	}

	public List<InstanciaDeTareaLibre> getInstanciasDeTareasLibres() {
		return instanciasDeTareasLibres;
	}

	public void setInstanciasDeTareasLibres(
			List<InstanciaDeTareaLibre> instanciasDeTareasLibres) {
		this.instanciasDeTareasLibres = instanciasDeTareasLibres;
	}

	public InstanciaDeTareaLibre addInstanciaDeTareaLibre(InstanciaDeTareaLibre instanciaDeTareaLibre) {
		getInstanciasDeTareasLibres().add(instanciaDeTareaLibre);
		instanciaDeTareaLibre.setInstanciaDeTarea(this);
		return instanciaDeTareaLibre;
	}
	
	public InstanciaDeTareaLibre removeInstanciaDeTareaLibre(InstanciaDeTareaLibre instanciaDeTareaLibre) {
		getInstanciasDeTareasLibres().remove(instanciaDeTareaLibre);
		instanciaDeTareaLibre.setInstanciaDeTarea(null);
		return instanciaDeTareaLibre;
	}
	
	public EstadoDeTarea getEstadoDeTarea() {
		return this.estadoDeTarea;
	}

	public void setEstadoDeTarea(EstadoDeTarea estadoDeTarea) {
		this.estadoDeTarea = estadoDeTarea;
	}

	public InstanciaDeProceso getInstanciaDeProceso() {
		return this.instanciaDeProceso;
	}

	public void setInstanciaDeProceso(InstanciaDeProceso instanciaDeProceso) {
		this.instanciaDeProceso = instanciaDeProceso;
	}

	public Tarea getTarea() {
		return this.tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}
	
	public List<UsuarioAsignado> getUsuariosAsignados() {
		return this.usuariosAsignados;
	}

	public void setUsuariosAsignados(List<UsuarioAsignado> usuariosAsignados) {
		this.usuariosAsignados = usuariosAsignados;
	}

	public UsuarioAsignado addUsuariosAsignado(UsuarioAsignado usuariosAsignado) {
		getUsuariosAsignados().add(usuariosAsignado);		
		usuariosAsignado.getId().setInstanciaDeTarea(this);

		return usuariosAsignado;
	}

	public UsuarioAsignado removeUsuariosAsignado(UsuarioAsignado usuariosAsignado) {
		getUsuariosAsignados().remove(usuariosAsignado);		
		usuariosAsignado.getId().setInstanciaDeTarea(null);
		
		return usuariosAsignado;
	}

	public List<HistoricoDeInstDeTarea> getHistoricosDeInstDeTareasDeOrigen() {
		return historicosDeInstDeTareasDeOrigen;
	}

	public void setHistoricosDeInstDeTareasDeOrigen(
			List<HistoricoDeInstDeTarea> historicosDeInstDeTareasDeOrigen) {
		this.historicosDeInstDeTareasDeOrigen = historicosDeInstDeTareasDeOrigen;
	}

	public List<HistoricoDeInstDeTarea> getHistoricosDeInstDeTareasDeDestino() {
		return historicosDeInstDeTareasDeDestino;
	}

	public void setHistoricosDeInstDeTareasDeDestino(
			List<HistoricoDeInstDeTarea> historicosDeInstDeTareasDeDestino) {
		this.historicosDeInstDeTareasDeDestino = historicosDeInstDeTareasDeDestino;
	}
	
	public HistoricoDeInstDeTarea addHistoricosDeInstDeTareaDeOrigen(HistoricoDeInstDeTarea historicosDeInstDeTarea) {
		getHistoricosDeInstDeTareasDeOrigen().add(historicosDeInstDeTarea);
		historicosDeInstDeTarea.setInstanciaDeTareaDeOrigen(this);

		return historicosDeInstDeTarea;
	}

	public HistoricoDeInstDeTarea removeHistoricosDeInstDeTareaDeOrigen(HistoricoDeInstDeTarea historicosDeInstDeTarea) {
		getHistoricosDeInstDeTareasDeOrigen().remove(historicosDeInstDeTarea);
		historicosDeInstDeTarea.setInstanciaDeTareaDeOrigen(null);

		return historicosDeInstDeTarea;
	}
	
	public HistoricoDeInstDeTarea addHistoricosDeInstDeTareaDeDestino(HistoricoDeInstDeTarea historicosDeInstDeTarea) {
		getHistoricosDeInstDeTareasDeDestino().add(historicosDeInstDeTarea);
		historicosDeInstDeTarea.setInstanciaDeTareaDeDestino(this);

		return historicosDeInstDeTarea;
	}

	public HistoricoDeInstDeTarea removeHistoricosDeInstDeTareaDeDestino(HistoricoDeInstDeTarea historicosDeInstDeTarea) {
		getHistoricosDeInstDeTareasDeDestino().remove(historicosDeInstDeTarea);
		historicosDeInstDeTarea.setInstanciaDeTareaDeDestino(null);

		return historicosDeInstDeTarea;
	}	
	
	public String getIdUsuarioQueAsigna() {
		return idUsuarioQueAsigna;
	}

	public void setIdUsuarioQueAsigna(String idUsuarioQueAsigna) {
		this.idUsuarioQueAsigna = idUsuarioQueAsigna;
	}	

	public List<ArchivosInstDeTarea> getArchivosInstDeTarea() {
		return archivosInstDeTarea;
	}

	public void setArchivosInstDeTarea(List<ArchivosInstDeTarea> archivosInstDeTarea) {
		this.archivosInstDeTarea = archivosInstDeTarea;
	}

	public ArchivosInstDeTarea addArchivosInstDeTarea(ArchivosInstDeTarea archivosInstDeTarea) {
		getArchivosInstDeTarea().add(archivosInstDeTarea);
		archivosInstDeTarea.setInstanciaDeTarea(this);
		return archivosInstDeTarea;
	}

	public ArchivosInstDeTarea removeArchivosInstDeTarea(ArchivosInstDeTarea archivosInstDeTarea) {
		getArchivosInstDeTarea().remove(archivosInstDeTarea);
		archivosInstDeTarea.setInstanciaDeTarea(null);
		return archivosInstDeTarea;
	}	
	
	public List<HistoricoFirma> getHistoricoFirmas() {
		return historicoFirmas;
	}

	public void setHistoricoFirmas(List<HistoricoFirma> historicoFirmas) {
		this.historicoFirmas = historicoFirmas;
	}

	public HistoricoFirma addHistoricoFirmas(HistoricoFirma historicoFirma) {
		getHistoricoFirmas().add(historicoFirma);
		historicoFirma.setInstanciaDeTarea(this);
		return historicoFirma;
	}

	public HistoricoFirma removeHistoricoFirmas(HistoricoFirma historicoFirma) {
		getHistoricoFirmas().remove(historicoFirma);
		historicoFirma.setInstanciaDeTarea(null);
		return historicoFirma;
	}
	
	public List<HistoricoFechaVencimientoInstanciaProceso> getHistoricosFechaVencimientoInstanciaProceso() {
		return historicosFechaVencimientoInstanciaProceso;
	}

	public void setHistoricosFechaVencimientoInstanciaProceso(
			List<HistoricoFechaVencimientoInstanciaProceso> historicosFechaVencimientoInstanciaProceso) {
		this.historicosFechaVencimientoInstanciaProceso = historicosFechaVencimientoInstanciaProceso;
	}

	public List<ValorParametroDeTarea> getValorParametrosDeTarea() {
		return valorParametrosDeTarea;
	}

	public void setValorParametrosDeTarea(List<ValorParametroDeTarea> valorParametrosDeTarea) {
		this.valorParametrosDeTarea = valorParametrosDeTarea;
	}
	
	public HistoricoFechaVencimientoInstanciaProceso addHistoricoFechaVencimientoInstanciaProceso(HistoricoFechaVencimientoInstanciaProceso historicoFechaVencimientoInstanciaProceso) {
		getHistoricosFechaVencimientoInstanciaProceso().add(historicoFechaVencimientoInstanciaProceso);
		historicoFechaVencimientoInstanciaProceso.setInstanciaDeTarea(this);

		return historicoFechaVencimientoInstanciaProceso;
	}

	public HistoricoFechaVencimientoInstanciaProceso removeValorParametrosDeTarea(HistoricoFechaVencimientoInstanciaProceso historicoFechaVencimientoInstanciaProceso) {
		getHistoricosFechaVencimientoInstanciaProceso().remove(historicoFechaVencimientoInstanciaProceso);
		historicoFechaVencimientoInstanciaProceso.setInstanciaDeTarea(null);

		return historicoFechaVencimientoInstanciaProceso;
	}
	
	public ValorParametroDeTarea addValorParametrosDeTarea(ValorParametroDeTarea valorParametrosDeTarea) {
		getValorParametrosDeTarea().add(valorParametrosDeTarea);
		valorParametrosDeTarea.setInstanciaDeTarea(this);

		return valorParametrosDeTarea;
	}

	public ValorParametroDeTarea removeValorParametrosDeTarea(ValorParametroDeTarea valorParametrosDeTarea) {
		getValorParametrosDeTarea().remove(valorParametrosDeTarea);
		valorParametrosDeTarea.setInstanciaDeTarea(null);

		return valorParametrosDeTarea;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idInstanciaDeTarea ^ (idInstanciaDeTarea >>> 32));
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
		InstanciaDeTarea other = (InstanciaDeTarea) obj;
		if (idInstanciaDeTarea != other.idInstanciaDeTarea)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InstanciaDeTarea [idInstanciaDeTarea=" + idInstanciaDeTarea
				+ ", razonAnulacion=" + razonAnulacion + ", fechaAnulacion="
				+ fechaAnulacion + ", fechaAsignacion=" + fechaAsignacion
				+ ", fechaFinalizacion=" + fechaFinalizacion + ", fechaInicio="
				+ fechaInicio + ", fechaVencimiento=" + fechaVencimiento
				+ ", fechaVencimientoUsuario=" + fechaVencimientoUsuario 
				+ ", idUsuarioQueAsigna=" + idUsuarioQueAsigna 
				+ ", estadoDeTarea.IdEstadoDeTarea=" + estadoDeTarea.getIdEstadoDeTarea()
				+ "]";
	}	
		
}
