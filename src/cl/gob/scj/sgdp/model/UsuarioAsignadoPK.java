package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the "SGDP_USUARIOS_ASIGNADOS" database table.
 * 
 */
@Embeddable
public class UsuarioAsignadoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to InstanciaDeTarea
	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_TAREA\"", insertable = true)
	private InstanciaDeTarea instanciaDeTarea;
	
	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;

	public UsuarioAsignadoPK() {
	}		
	
	public UsuarioAsignadoPK(InstanciaDeTarea instanciaDeTarea, String idUsuario) {
		super();
		this.instanciaDeTarea = instanciaDeTarea;
		this.idUsuario = idUsuario;
	}

	public InstanciaDeTarea getInstanciaDeTarea() {
		return instanciaDeTarea;
	}

	public void setInstanciaDeTarea(InstanciaDeTarea instanciaDeTarea) {
		this.instanciaDeTarea = instanciaDeTarea;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idUsuario == null) ? 0 : idUsuario.hashCode());
		result = prime
				* result
				+ ((instanciaDeTarea == null) ? 0 : instanciaDeTarea.hashCode());
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
		UsuarioAsignadoPK other = (UsuarioAsignadoPK) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		if (instanciaDeTarea == null) {
			if (other.instanciaDeTarea != null)
				return false;
		} else if (!instanciaDeTarea.equals(other.instanciaDeTarea))
			return false;
		return true;
	}
	
	
}