package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.Date;

public class ComplejidadDTO implements Serializable {

	private Long idComplejidad;
	private String complejidad;
	private Date fechaCreacion;
	private String usuarioCreacion;
	
	public Long getIdComplejidad() {
		return idComplejidad;
	}
	public void setIdComplejidad(Long idComplejidad) {
		this.idComplejidad = idComplejidad;
	}
	public String getComplejidad() {
		return complejidad;
	}
	public void setComplejidad(String complejidad) {
		this.complejidad = complejidad;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	
	@Override
	public String toString() {
		return "ComplejidadDTO [idComplejidad=" + idComplejidad + ", complejidad=" + complejidad + ", fechaCreacion="
				+ fechaCreacion + ", usuarioCreacion=" + usuarioCreacion + "]";
	}
	
}
