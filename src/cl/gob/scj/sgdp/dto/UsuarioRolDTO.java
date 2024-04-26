package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class UsuarioRolDTO extends RespuestaDTO implements Serializable {
	
	private long idUsuarioRol;
	private String idUsuario;
	private boolean activo;
	private boolean fueraDeOficina;
	private long idRol;
	private String nombreRol;
	private long idUnidad;
	private String nombreUnidad;
	private String nombreCompleto;
	private String rut;
	private String dv;
	
	
	
	public UsuarioRolDTO() {
		super();
	}
	public UsuarioRolDTO(long idUsuarioRol, String idUsuario, boolean activo, boolean fueraDeOficina, long idRol,
			String nombreRol, long idUnidad, String nombreUnidad) {
		super();
		this.idUsuarioRol = idUsuarioRol;
		this.idUsuario = idUsuario;
		this.activo = activo;
		this.fueraDeOficina = fueraDeOficina;
		this.idRol = idRol;
		this.nombreRol = nombreRol;
		this.idUnidad = idUnidad;
		this.nombreUnidad = nombreUnidad;
	}
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
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}	
	public String getDv() {
		return dv;
	}
	public void setDv(String dv) {
		this.dv = dv;
	}
	@Override
	public String toString() {
		return "UsuarioRolDTO [idUsuarioRol=" + idUsuarioRol + ", idUsuario=" + idUsuario + ", activo=" + activo
				+ ", fueraDeOficina=" + fueraDeOficina + ", idRol=" + idRol + ", nombreRol=" + nombreRol 
				+ ", idUnidad="	+ idUnidad 				
				+ ", nombreUnidad=" + nombreUnidad
				+ ", nombreCompleto=" + nombreCompleto 
				+ ", rut=" + rut 
				+ ", dv=" + dv 
				+ "]";
	}
}
