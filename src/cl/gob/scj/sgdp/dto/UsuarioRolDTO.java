package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class UsuarioRolDTO implements Serializable {
	
	private long idUsuarioRol;
	private String idUsuario;
	private boolean activo;
	private boolean fueraDeOficina;
	private long idRol;
	private String nombreRol;
	private long idUnidad;
	private String nombreUnidad;
	
	public long getIdUsuarioRol() {
		return idUsuarioRol;
	}
	public void setIdUsuarioRol(long idUsuarioRol) {
		this.idUsuarioRol = idUsuarioRol;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public boolean getFueraDeOficina() {
		return fueraDeOficina;
	}
	public void setFueraDeOficina(boolean fueraDeOficina) {
		this.fueraDeOficina = fueraDeOficina;
	}
	public long getIdRol() {
		return idRol;
	}
	public void setIdRol(long idRol) {
		this.idRol = idRol;
	}
	public String getNombreRol() {
		return nombreRol;
	}
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	public long getIdUnidad() {
		return idUnidad;
	}
	public void setIdUnidad(long idUnidad) {
		this.idUnidad = idUnidad;
	}
	public String getNombreUnidad() {
		return nombreUnidad;
	}
	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}
	@Override
	public String toString() {
		return "UsuarioRolDTO [idUsuarioRol=" + idUsuarioRol + ", idUsuario=" + idUsuario + ", activo=" + activo
				+ ", fueraDeOficina=" + fueraDeOficina + ", idRol=" + idRol + ", nombreRol=" + nombreRol + ", idUnidad="
				+ idUnidad + ", nombreUnidad=" + nombreUnidad + "]";
	}
}
