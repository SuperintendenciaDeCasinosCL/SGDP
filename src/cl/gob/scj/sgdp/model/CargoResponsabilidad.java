package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the "SGDP_CARGO_RESPONSABILIDAD" database table.
 * 
 */

@Entity
@Table(name="\"SGDP_CARGO_RESPONSABILIDAD\"")
@NamedQueries({
	@NamedQuery(name="CargoResponsabilidad.findAll", query="SELECT cr FROM CargoResponsabilidad cr")	
})
public class CargoResponsabilidad implements Serializable {

	@EmbeddedId
	private CargoResponsabilidadPK id;

	public CargoResponsabilidadPK getId() {
		return id;
	}

	public void setId(CargoResponsabilidadPK id) {
		this.id = id;
	}
	
}
