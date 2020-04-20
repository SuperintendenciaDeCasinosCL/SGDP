package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class UsuarioResponsabilidadDTO implements Serializable {
	
	private long idUsuarioResponsabilidad;
	private String idUsuario;
	private int orden;
	
	public UsuarioResponsabilidadDTO() {
		super();
	}
	public UsuarioResponsabilidadDTO(long idUsuarioResponsabilidad, String idUsuario, int orden) {
		super();
		this.idUsuarioResponsabilidad = idUsuarioResponsabilidad;
		this.idUsuario = idUsuario;
		this.orden = orden;
	}
	public long getIdUsuarioResponsabilidad() {
		return idUsuarioResponsabilidad;
	}
	public void setIdUsuarioResponsabilidad(long idUsuarioResponsabilidad) {
		this.idUsuarioResponsabilidad = idUsuarioResponsabilidad;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
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
		UsuarioResponsabilidadDTO other = (UsuarioResponsabilidadDTO) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}
}
