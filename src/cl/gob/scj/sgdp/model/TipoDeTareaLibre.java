package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the "SGDP_TIPOS_DE_TAREAS_LIBRES" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_TIPOS_DE_TAREAS_LIBRES\"")
@NamedQuery(name="TipoDeTareaLibre.findAll", query="SELECT t FROM TipoDeTareaLibre t")
public class TipoDeTareaLibre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_TIPO_DE_TAREA_LIBRE", sequenceName="\"SEQ_ID_TIPO_DE_TAREA_LIBRE\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_TIPO_DE_TAREA_LIBRE")
	@Column(name="\"ID_TIPO_DE_TAREA_LIBRE\"")
	private long idTipoDeTareaLibre;

	@Column(name="\"A_NOMBRE_DE_TAREA_LIBRE\"")
	private String nombreDeTareaLibre;

	//bi-directional many-to-one association to InstanciaDeTareaLibre
	@JsonIgnore
	@OneToMany(mappedBy="tiposDeTareasLibre", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<InstanciaDeTareaLibre> instanciasDeTareasLibres;

	public TipoDeTareaLibre() {
	}

	public long getIdTipoDeTareaLibre() {
		return this.idTipoDeTareaLibre;
	}

	public void setIdTipoDeTareaLibre(long idTipoDeTareaLibre) {
		this.idTipoDeTareaLibre = idTipoDeTareaLibre;
	}

	public String getNombreDeTareaLibre() {
		return this.nombreDeTareaLibre;
	}

	public void setNombreDeTareaLibre(String nombreDeTareaLibre) {
		this.nombreDeTareaLibre = nombreDeTareaLibre;
	}

	public List<InstanciaDeTareaLibre> getInstanciasDeTareasLibres() {
		return this.instanciasDeTareasLibres;
	}

	public void setInstanciasDeTareasLibres(List<InstanciaDeTareaLibre> instanciasDeTareasLibres) {
		this.instanciasDeTareasLibres = instanciasDeTareasLibres;
	}

	public InstanciaDeTareaLibre addInstanciasDeTareasLibre(InstanciaDeTareaLibre instanciaDeTareaLibre) {
		getInstanciasDeTareasLibres().add(instanciaDeTareaLibre);
		instanciaDeTareaLibre.setTiposDeTareasLibre(this);

		return instanciaDeTareaLibre;
	}

	public InstanciaDeTareaLibre removeInstanciasDeTareasLibre(InstanciaDeTareaLibre instanciaDeTareaLibre) {
		getInstanciasDeTareasLibres().remove(instanciaDeTareaLibre);
		instanciaDeTareaLibre.setTiposDeTareasLibre(null);

		return instanciaDeTareaLibre;
	}

}