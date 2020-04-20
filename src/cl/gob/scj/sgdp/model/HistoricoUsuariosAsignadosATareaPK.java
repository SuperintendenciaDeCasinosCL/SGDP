package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the "SGDP_HISTORICO_USUARIOS_ASIGNADOS_A_TAREAS" database table.
 * 
 */
@Embeddable
public class HistoricoUsuariosAsignadosATareaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="\"ID_HISTORICO_DE_INST_DE_TAREA\"", insertable = true)
	private HistoricoDeInstDeTarea historicoDeInstDeTarea;
	
	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;

	public HistoricoUsuariosAsignadosATareaPK() {
	}
	
	public HistoricoUsuariosAsignadosATareaPK(
			HistoricoDeInstDeTarea historicoDeInstDeTarea, String idUsuario) {
		super();
		this.historicoDeInstDeTarea = historicoDeInstDeTarea;
		this.idUsuario = idUsuario;
	}


	public HistoricoDeInstDeTarea getHistoricoDeInstDeTarea() {
		return historicoDeInstDeTarea;
	}


	public void setHistoricoDeInstDeTarea(
			HistoricoDeInstDeTarea historicoDeInstDeTarea) {
		this.historicoDeInstDeTarea = historicoDeInstDeTarea;
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
		result = prime
				* result
				+ ((historicoDeInstDeTarea == null) ? 0
						: historicoDeInstDeTarea.hashCode());
		result = prime * result
				+ ((idUsuario == null) ? 0 : idUsuario.hashCode());
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
		HistoricoUsuariosAsignadosATareaPK other = (HistoricoUsuariosAsignadosATareaPK) obj;
		if (historicoDeInstDeTarea == null) {
			if (other.historicoDeInstDeTarea != null)
				return false;
		} else if (!historicoDeInstDeTarea.equals(other.historicoDeInstDeTarea))
			return false;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}
	
}