package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class ConfidencialidadDocumentoUsuarioDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String usuario;
		
	public ConfidencialidadDocumentoUsuarioDTO() {

	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	
}
