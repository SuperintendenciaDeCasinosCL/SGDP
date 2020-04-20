package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the "SGDP_INSTANCIAS_DE_TAREAS_LIBRES" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_INSTANCIAS_DE_TAREAS_LIBRES\"")
@NamedQuery(name="InstanciaDeTareaLibre.findAll", query="SELECT i FROM InstanciaDeTareaLibre i")
public class InstanciaDeTareaLibre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_INSTANCIA_DE_TAREA_LIBRE", sequenceName="\"SEQ_ID_INSTANCIA_DE_TAREA_LIBRE\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_INSTANCIA_DE_TAREA_LIBRE")
	@Column(name="\"ID_INSTANCIA_DE_TAREA_LIBRE\"")
	private long idInstanciaDeTareaLibre;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_ASIGNACION\"")
	private Date fechaAsignacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_FINALIZACION\"")
	private Date fechaFinalizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_VENCIMIENTO\"")
	private Date fechaVencimiento;

	@Column(name="\"ID_USUARIO_ASIGANDO\"")
	private String idUsuarioAsigando;

	@Column(name="\"ID_USUARIO_QUE_HACE_CONSULTA\"")
	private String idUsuarioQueHaceConsulta;

	//bi-directional many-to-one association to TipoDeTareaLibre
	@ManyToOne
	@JoinColumn(name="\"ID_TIPO_DE_TAREA_LIBRE\"")
	private TipoDeTareaLibre tiposDeTareasLibre;
	
	//bi-directional many-to-one association to InstanciaDeTarea
	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_TAREA\"")
	private InstanciaDeTarea instanciaDeTarea;
	
	//bi-directional many-to-one association to EstadoDeTarea
	@ManyToOne
	@JoinColumn(name="\"ID_ESTADO_DE_TAREA\"")
	private EstadoDeTarea estadoDeTarea;
	
	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_TAREA_LIBRE_PADRE\"")
	private InstanciaDeTareaLibre instanciaDeTareaLibre;
	
    /*@OneToMany(mappedBy="instanciaDeTareaLibre", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<InstanciaDeTareaLibre> instanciasDeTareasLibres;*/

	public InstanciaDeTareaLibre() {
	}

	public long getIdInstanciaDeTareaLibre() {
		return this.idInstanciaDeTareaLibre;
	}

	public void setIdInstanciaDeTareaLibre(long idInstanciaDeTareaLibre) {
		this.idInstanciaDeTareaLibre = idInstanciaDeTareaLibre;
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

	public Date getFechaVencimiento() {
		return this.fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getIdUsuarioAsigando() {
		return this.idUsuarioAsigando;
	}

	public void setIdUsuarioAsigando(String idUsuarioAsigando) {
		this.idUsuarioAsigando = idUsuarioAsigando;
	}

	public String getIdUsuarioQueHaceConsulta() {
		return this.idUsuarioQueHaceConsulta;
	}

	public void setIdUsuarioQueHaceConsulta(String idUsuarioQueHaceConsulta) {
		this.idUsuarioQueHaceConsulta = idUsuarioQueHaceConsulta;
	}

	public TipoDeTareaLibre getTiposDeTareasLibre() {
		return this.tiposDeTareasLibre;
	}

	public void setTiposDeTareasLibre(TipoDeTareaLibre tiposDeTareasLibre) {
		this.tiposDeTareasLibre = tiposDeTareasLibre;
	}

	public InstanciaDeTarea getInstanciaDeTarea() {
		return instanciaDeTarea;
	}

	public void setInstanciaDeTarea(InstanciaDeTarea instanciaDeTarea) {
		this.instanciaDeTarea = instanciaDeTarea;
	}

	public EstadoDeTarea getEstadoDeTarea() {
		return estadoDeTarea;
	}

	public void setEstadoDeTarea(EstadoDeTarea estadoDeTarea) {
		this.estadoDeTarea = estadoDeTarea;
	}

	public InstanciaDeTareaLibre getInstanciaDeTareaLibre() {
		return instanciaDeTareaLibre;
	}

	public void setInstanciaDeTareaLibre(InstanciaDeTareaLibre instanciaDeTareaLibre) {
		this.instanciaDeTareaLibre = instanciaDeTareaLibre;
	}

	/*public List<InstanciaDeTareaLibre> getInstanciasDeTareasLibres() {
		return instanciasDeTareasLibres;
	}

	public void setInstanciasDeTareasLibres(
			List<InstanciaDeTareaLibre> instanciasDeTareasLibres) {
		this.instanciasDeTareasLibres = instanciasDeTareasLibres;
	}
	
	public InstanciaDeTareaLibre addInstanciaDeTareaLibre(InstanciaDeTareaLibre instanciaDeTareaLibre) {
		getInstanciasDeTareasLibres().add(instanciaDeTareaLibre);
		instanciaDeTareaLibre.addInstanciaDeTareaLibre(this);

		return instanciaDeTareaLibre;
	}

	public InstanciaDeTareaLibre removeInstanciaDeTareaLibre(InstanciaDeTareaLibre instanciaDeTareaLibre) {
		getInstanciasDeTareasLibres().remove(instanciaDeTareaLibre);
		instanciaDeTareaLibre.addInstanciaDeTareaLibre(null);

		return instanciaDeTareaLibre;
	}*/
	

}