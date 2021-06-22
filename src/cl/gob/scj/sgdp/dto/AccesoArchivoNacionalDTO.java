package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class AccesoArchivoNacionalDTO implements Serializable {

	private static final long serialVersionUID = 472060425462063347L;
	
	private String usuarioArchivo;
	private String passArchivo;
	private String usuarioSftpArchivo;
	private String passSftpArchivo;
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
	@Override
	public String toString() {
		return "AccesoArchivoNacionalDTO [usuarioArchivo=" + usuarioArchivo + ", passArchivo=" + passArchivo
				+ ", codigoInstitucion=" + codigoInstitucion + ", nombreInstitucion=" + nombreInstitucion + "]";
	}
	public String getUsuarioSftpArchivo() {
		return usuarioSftpArchivo;
	}
	public void setUsuarioSftpArchivo(String usuarioSftpArchivo) {
		this.usuarioSftpArchivo = usuarioSftpArchivo;
	}
	public String getPassSftpArchivo() {
		return passSftpArchivo;
	}
	public void setPassSftpArchivo(String passSftpArchivo) {
		this.passSftpArchivo = passSftpArchivo;
	}

}
