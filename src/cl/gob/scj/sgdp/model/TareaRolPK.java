package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the "SGDP_TAREAS_ROLES" database table.
 * 
 */
@Embeddable
public class TareaRolPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="\"ID_ROL\"")
	private Rol rol;

	//bi-directional many-to-one association to Tarea
	@ManyToOne
	@JoinColumn(name="\"ID_TAREA\"")
	private Tarea tarea;	
	
	public TareaRolPK() {
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
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
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
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
		TareaRolPK other = (TareaRolPK) obj;
		if (rol == null) {
			if (other.rol != null)
				return false;
		} else if (!rol.equals(other.rol))
			return false;
		if (tarea == null) {
			if (other.tarea != null)
				return false;
		} else if (!tarea.equals(other.tarea))
			return false;
		return true;
	}
	
	
}