package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CargoResponsabilidadPK implements Serializable {

	//bi-directional many-to-one association to Cargo
	@ManyToOne
	@JoinColumn(name="\"ID_CARGO\"")
	private Cargo cargo;
		
	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="\"ID_RESPONSABILIDAD\"")
	private Responsabilidad responsabilidad;

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Responsabilidad getResponsabilidad() {
		return responsabilidad;
	}

	public void setResponsabilidad(Responsabilidad responsabilidad) {
		this.responsabilidad = responsabilidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + ((responsabilidad == null) ? 0 : responsabilidad.hashCode());
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
		CargoResponsabilidadPK other = (CargoResponsabilidadPK) obj;
		if (cargo == null) {
			if (other.cargo != null)
				return false;
		} else if (!cargo.equals(other.cargo))
			return false;
		if (responsabilidad == null) {
			if (other.responsabilidad != null)
				return false;
		} else if (!responsabilidad.equals(other.responsabilidad))
			return false;
		return true;
	}
	
}
