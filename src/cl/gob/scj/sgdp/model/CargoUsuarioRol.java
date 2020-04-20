package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="\"SGDP_CARGO_USUARIO_ROL\"")
@NamedQueries({
	@NamedQuery(name="CargoUsuarioRol.findAll", query="SELECT c FROM CargoUsuarioRol c")
})
public class CargoUsuarioRol implements Serializable {

	@EmbeddedId
	private CargoUsuarioRolPK id;

	public CargoUsuarioRol() {
		super();
	}

	public CargoUsuarioRolPK getId() {
		return id;
	}

	public void setId(CargoUsuarioRolPK id) {
		this.id = id;
	}
	
}
