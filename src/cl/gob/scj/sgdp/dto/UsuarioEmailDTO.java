package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class UsuarioEmailDTO implements Serializable  {

	private String nombreUsuario;
	private String contrasena;

	public UsuarioEmailDTO() {
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
