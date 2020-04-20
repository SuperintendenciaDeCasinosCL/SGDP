package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.Date;

import cl.gob.scj.sgdp.model.Tarea;

public class UsuarioNotificacionTareaDTO implements Serializable {	
	
	private Date dFechaCreacion;
	private String idUsuario;
	private Long idTarea;
	
	public UsuarioNotificacionTareaDTO() {
		super();
	}
	
	public UsuarioNotificacionTareaDTO(Date dFechaCreacion, String idUsuario, long idTarea) {
		super();
		this.dFechaCreacion = dFechaCreacion;
		this.idUsuario = idUsuario;
		this.idTarea = idTarea;
	}
	public Date getdFechaCreacion() {
		return dFechaCreacion;
	}
	public void setdFechaCreacion(Date dFechaCreacion) {
		this.dFechaCreacion = dFechaCreacion;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}	
	public Long getIdTarea() {
		return idTarea;
	}
	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}
	@Override
	public String toString() {
		return "UsuarioNotificacionTareaDTO [dFechaCreacion=" + dFechaCreacion + ", idUsuario=" + idUsuario
				+ ", idTarea=" + idTarea + "]";
	}			
}
