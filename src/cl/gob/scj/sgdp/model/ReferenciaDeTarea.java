package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the "SGDP_REFERENCIAS_DE_TAREAS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_REFERENCIAS_DE_TAREAS\"")
@NamedQuery(name="ReferenciaDeTarea.findAll", query="SELECT r FROM ReferenciaDeTarea r")
public class ReferenciaDeTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_REFERENCIA_DE_TAREA", sequenceName="\"SEQ_ID_REFERENCIA_DE_TAREA\"")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_REFERENCIA_DE_TAREA")
	@Column(name="\"ID_REFERENCIA_DE_TAREA\"")
	private long idReferenciaDeTarea;

	/*@Column(name="\"ID_TAREA\"")
	private Long idTarea;*/

	/*@Column(name="\"ID_TAREA_ANTERIOR\"")
	private Long idTareaAnterior;

	@Column(name="\"ID_TAREA_SIGUIENTE\"")
	private Long idTareaSiguiente;*/

	//bi-directional many-to-one association to Tarea
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"ID_TAREA\"")
	private Tarea tarea;

	//bi-directional many-to-one association to Tarea
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"ID_TAREA_SIGUIENTE\"")
	private Tarea tareaSiguiente;

	//bi-directional many-to-one association to Tarea
	/*@JsonIgnore
	@ManyToOne
	@JoinColumn(name="\"ID_TAREA_ANTERIOR\"")
	private Tarea tareaAnterior;*/

	public ReferenciaDeTarea() {
	}

	public long getIdReferenciaDeTarea() {
		return this.idReferenciaDeTarea;
	}

	public void setIdReferenciaDeTarea(long idReferenciaDeTarea) {
		this.idReferenciaDeTarea = idReferenciaDeTarea;
	}

	/*public Long getIdTarea() {
		return this.idTarea;
	}

	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}*/

	/*public Long getIdTareaAnterior() {
		return this.idTareaAnterior;
	}

	public void setIdTareaAnterior(Long idTareaAnterior) {
		this.idTareaAnterior = idTareaAnterior;
	}

	public Long getIdTareaSiguiente() {
		return this.idTareaSiguiente;
	}

	public void setIdTareaSiguiente(Long idTareaSiguiente) {
		this.idTareaSiguiente = idTareaSiguiente;
	}*/

	public Tarea getTarea() {
		return this.tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public Tarea getTareaSiguiente() {
		return this.tareaSiguiente;
	}

	public void setTareaSiguiente(Tarea tareaSiguiente) {
		this.tareaSiguiente = tareaSiguiente;
	}

	/*public Tarea getTareaAnterior() {
		return this.tareaAnterior;
	}

	public void setTareaAnterior(Tarea tareaAnterior) {
		this.tareaAnterior = tareaAnterior;
	}*/

}