package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CargoUsuarioRolPK implements Serializable {

	//bi-directional many-to-one association to Cargo
	@ManyToOne
	@JoinColumn(name="\"ID_CARGO\"")
	private Cargo cargo;
	
	//bi-directional many-to-one association to Cargo
	@ManyToOne
	@JoinColumn(name="\"ID_USUARIO_ROL\"")
	private UsuarioRol usuarioRol;

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public UsuarioRol getUsuarioRol() {
		return usuarioRol;
	}

	public void setUsuarioRol(UsuarioRol usuarioRol) {
		this.usuarioRol = usuarioRol;
	}
	
	
}
