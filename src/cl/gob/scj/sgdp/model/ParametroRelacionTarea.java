package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "SGDP_PARAMETRO_RELACION_TAREA" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_PARAMETRO_RELACION_TAREA\"")
@NamedQuery(name="ParametroRelacionTarea.findAll", query="SELECT p FROM ParametroRelacionTarea p")
public class ParametroRelacionTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ParametroRelacionTareaPK id;

	public ParametroRelacionTarea() {
	}

	public ParametroRelacionTareaPK getId() {
		return this.id;
	}

	public void setId(ParametroRelacionTareaPK id) {
		this.id = id;
	}

}