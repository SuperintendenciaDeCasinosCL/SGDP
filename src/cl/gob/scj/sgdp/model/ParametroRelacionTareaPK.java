package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import javax.persistence.*;

import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.TipoDeDocumento;

/**
 * The primary key class for the "SGDP_PARAMETRO_RELACION_TAREA" database table.
 * 
 */
@Embeddable
public class ParametroRelacionTareaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to Tarea
	@ManyToOne
	@JoinColumn(name="\"ID_TAREA\"", insertable = true)
	private Tarea tarea;
	
	//bi-directional many-to-one association to TipoDeDocumento
	@ManyToOne
	@JoinColumn(name="\"ID_PARAM_TAREA\"", insertable = true)
	private ParametroDeTarea parametroDeTarea;

	public ParametroRelacionTareaPK() {
	}
	
	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public ParametroDeTarea getParametroDeTarea() {
		return parametroDeTarea;
	}

	public void setParametroDeTarea(ParametroDeTarea parametroDeTarea) {
		this.parametroDeTarea = parametroDeTarea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parametroDeTarea == null) ? 0 : parametroDeTarea.hashCode());
		result = prime * result + ((tarea == null) ? 0 : tarea.hashCode());
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
		ParametroRelacionTareaPK other = (ParametroRelacionTareaPK) obj;
		if (parametroDeTarea == null) {
			if (other.parametroDeTarea != null)
				return false;
		} else if (!parametroDeTarea.equals(other.parametroDeTarea))
			return false;
		if (tarea == null) {
			if (other.tarea != null)
				return false;
		} else if (!tarea.equals(other.tarea))
			return false;
		return true;
	}
	
}