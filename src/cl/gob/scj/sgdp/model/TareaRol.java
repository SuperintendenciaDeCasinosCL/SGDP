package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "SGDP_TAREAS_ROLES" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_TAREAS_ROLES\"")
@NamedQuery(name="TareaRol.findAll", query="SELECT t FROM TareaRol t")
public class TareaRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TareaRolPK id;

	@Column(name="\"N_ORDEN\"")
	private int orden;

	public TareaRol() {
	}

	public TareaRolPK getId() {
		return this.id;
	}

	public void setId(TareaRolPK id) {
		this.id = id;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

}