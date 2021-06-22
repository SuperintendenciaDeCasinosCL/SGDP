package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class ConfiguracionArchivoNacionalDTO implements Serializable {

	private static final long serialVersionUID = 472060425462063347L;

	private String usuarioArchivo;
	private String passArchivo;
	private String userSFTP;
	private String passSFTP;
	private String codigoInstitucion;
	private String nombreInstitucion;

	public String getUsuarioArchivo() {
		return usuarioArchivo;
	}

	public void setUsuarioArchivo(String usuarioArchivo) {
		this.usuarioArchivo = usuarioArchivo;
	}

	public String getPassArchivo() {
		return passArchivo;
	}

	public void setPassArchivo(String passArchivo) {
		this.passArchivo = passArchivo;
	}

	public String getUserSFTP() {
		return userSFTP;
	}

	public void setUserSFTP(String userSFTP) {
		this.userSFTP = userSFTP;
	}

	public String getPassSFTP() {
		return passSFTP;
	}

	public void setPassSFTP(String passSFTP) {
		this.passSFTP = passSFTP;
	}

	public String getCodigoInstitucion() {
		return codigoInstitucion;
	}

	public void setCodigoInstitucion(String codigoInstitucion) {
		this.codigoInstitucion = codigoInstitucion;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

}
