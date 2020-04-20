package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class RolDTO implements Serializable {

	private Long idRol;
	private String nombreRol;
		
	public RolDTO(Long idRol, String nombreRol) {
		super();
		this.idRol = idRol;
		this.nombreRol = nombreRol;
	}
	public Long getIdRol() {
		return idRol;
	}
	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}
	public String getNombreRol() {
		return nombreRol;
	}
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	
	
}
