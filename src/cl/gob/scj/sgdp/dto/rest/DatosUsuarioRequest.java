package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class DatosUsuarioRequest implements Serializable  {
	
	private String idUsuario;
	private String password;
	
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
