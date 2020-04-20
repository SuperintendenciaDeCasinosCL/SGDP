package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the "SGDP_USUARIOS_ASIGNADOS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_USUARIOS_ASIGNADOS_A_TAREAS\"")
@NamedQuery(name="UsuarioAsignado.findAll", query="SELECT u FROM UsuarioAsignado u")
public class UsuarioAsignado implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuarioAsignadoPK id;	
	
	public UsuarioAsignado() {
	}

	public UsuarioAsignadoPK getId() {
		return this.id;
	}

	public void setId(UsuarioAsignadoPK id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UsuarioAsignado [idUsuario=" + id.getIdUsuario() + ", idInstanciaDeTarea: " + id.getInstanciaDeTarea().getIdInstanciaDeTarea() + "]";
	}
	
}