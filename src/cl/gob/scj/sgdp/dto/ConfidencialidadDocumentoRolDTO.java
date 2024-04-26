package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class ConfidencialidadDocumentoRolDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String rol;
		
	public ConfidencialidadDocumentoRolDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}


	
}
