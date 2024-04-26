package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

public class UsuariosPorRolDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UsuarioRolDTO> asignados;
	private List<UsuarioRolDTO> noAsignados;
	private Long rol;
	public UsuariosPorRolDTO(List<UsuarioRolDTO> asignados, List<UsuarioRolDTO> noAsignados, Long rol) {
		super();
		this.asignados = asignados;
		this.noAsignados = noAsignados;
		this.rol = rol;
	}
	public List<UsuarioRolDTO> getAsignados() {
		return asignados;
	}
	public void setAsignados(List<UsuarioRolDTO> asignados) {
		this.asignados = asignados;
	}
	public List<UsuarioRolDTO> getNoAsignados() {
		return noAsignados;
	}
	public void setNoAsignados(List<UsuarioRolDTO> noAsignados) {
		this.noAsignados = noAsignados;
	}
	public Long getRol() {
		return rol;
	}
	public void setRol(Long rol) {
		this.rol = rol;
	}
	
	
	
}
