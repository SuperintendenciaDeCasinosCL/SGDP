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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cl.gob.scj.sgdp.config.Constantes;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="\"SGDP_ETAPAS\"")
@NamedQuery(name="Etapa.findAll", query="SELECT s FROM Etapa s")
public class Etapa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7450608306809118308L;
	
	@Id
	@SequenceGenerator(name="SEQ_ID_ETAPA", sequenceName="\"SEQ_ID_ETAPA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_ETAPA")
	@Column(name="\"ID_ETAPA\"")
	private long idEtapa;

	@Column(name="\"A_NOMBRE_ETAPA\"")
	private String nombreEtapa;
	
	@JsonIgnore
	@OneToMany(mappedBy="etapa", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<Tarea> tareas;

	public Etapa() {
	}

	public long getIdEtapa() {
		return this.idEtapa;
	}

	public void setIdEtapa(long idEtapa) {
		this.idEtapa = idEtapa;
	}

	public String getNombreEtapa() {
		return nombreEtapa;
	}

	public void setNombreEtapa(String nombreEtapa) {
		this.nombreEtapa = nombreEtapa;
	}
	
	public List<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}
	
	public Tarea addTarea(Tarea tarea) {
		getTareas().add(tarea);
		tarea.setEtapa(this);

		return tarea;
	}

	public Tarea removeTarea(Tarea tarea) {
		getTareas().remove(tarea);
		tarea.setEtapa(null);

		return tarea;
	}

}
