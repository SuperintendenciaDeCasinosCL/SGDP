package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ResponsabilidadTareaPK implements Serializable {

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="\"ID_RESPONSABILIDAD\"")
	private Responsabilidad responsabilidad;

	//bi-directional many-to-one association to Tarea
	@ManyToOne
	@JoinColumn(name="\"ID_TAREA\"")
	private Tarea tarea;

	public Responsabilidad getResponsabilidad() {
		return responsabilidad;
	}

	public void setResponsabilidad(Responsabilidad responsabilidad) {
		this.responsabilidad = responsabilidad;
	}

	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((responsabilidad == null) ? 0 : responsabilidad.hashCode());
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
		ResponsabilidadTareaPK other = (ResponsabilidadTareaPK) obj;
		if (responsabilidad == null) {
			if (other.responsabilidad != null)
				return false;
		} else if (!responsabilidad.equals(other.responsabilidad))
			return false;
		if (tarea == null) {
			if (other.tarea != null)
				return false;
		} else if (!tarea.equals(other.tarea))
			return false;
		return true;
	}	
		
}
