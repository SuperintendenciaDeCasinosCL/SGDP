package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the "SGDP_ESTADOS_DE_TAREAS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_ESTADOS_DE_TAREAS\"")
@NamedQuery(name="EstadoDeTarea.findAll", query="SELECT e FROM EstadoDeTarea e")
public class EstadoDeTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_ESTADO_DE_TAREA", sequenceName="\"SEQ_ID_ESTADO_DE_TAREA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_ESTADO_DE_TAREA")
	@Column(name="\"ID_ESTADO_DE_TAREA\"")
	private long idEstadoDeTarea;

	@Column(name="\"A_NOMBRE_ESTADO_DE_TAREA\"")
	private String nombreEstadoDeTarea;

	@Column(name="\"N_CODIGO_ESTADO_DE_TAREA\"")
	private int codigoEstadoDeTarea;

	//bi-directional many-to-one association to InstanciaDeTarea
	@JsonIgnore
	@OneToMany(mappedBy="estadoDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<InstanciaDeTarea> instanciasDeTareas;
	
	//bi-directional many-to-one association to InstanciaDeTareaLibre
	@JsonIgnore
	@OneToMany(mappedBy="estadoDeTarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<InstanciaDeTareaLibre> instanciasDeTareasLibres;

	public EstadoDeTarea() {
	}

	public long getIdEstadoDeTarea() {
		return this.idEstadoDeTarea;
	}

	public void setIdEstadoDeTarea(long idEstadoDeTarea) {
		this.idEstadoDeTarea = idEstadoDeTarea;
	}

	public String getNombreEstadoDeTarea() {
		return this.nombreEstadoDeTarea;
	}

	public void setNombreEstadoDeTarea(String nombreEstadoDeTarea) {
		this.nombreEstadoDeTarea = nombreEstadoDeTarea;
	}

	public int getCodigoEstadoDeTarea() {
		return this.codigoEstadoDeTarea;
	}

	public void setCodigoEstadoDeTarea(int codigoEstadoDeTarea) {
		this.codigoEstadoDeTarea = codigoEstadoDeTarea;
	}

	public List<InstanciaDeTarea> getInstanciasDeTareas() {
		return this.instanciasDeTareas;
	}

	public void setInstanciasDeTareas(List<InstanciaDeTarea> instanciasDeTareas) {
		this.instanciasDeTareas = instanciasDeTareas;
	}

	public InstanciaDeTarea addInstanciasDeTarea(InstanciaDeTarea instanciasDeTarea) {
		getInstanciasDeTareas().add(instanciasDeTarea);
		instanciasDeTarea.setEstadoDeTarea(this);

		return instanciasDeTarea;
	}

	public InstanciaDeTarea removeInstanciasDeTarea(InstanciaDeTarea instanciasDeTarea) {
		getInstanciasDeTareas().remove(instanciasDeTarea);
		instanciasDeTarea.setEstadoDeTarea(null);

		return instanciasDeTarea;
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
		instanciaDeTareaLibre.setEstadoDeTarea(this);
		return instanciaDeTareaLibre;
	}
	
	public InstanciaDeTareaLibre removeInstanciaDeTareaLibre(InstanciaDeTareaLibre instanciaDeTareaLibre) {
		getInstanciasDeTareasLibres().remove(instanciaDeTareaLibre);
		instanciaDeTareaLibre.setEstadoDeTarea(null);
		return instanciaDeTareaLibre;
	}
	
}