package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;

public class HistorialProceso implements Serializable {

	private long idInstanciaDeTarea;
	private String nombreDeTarea;
	private String idDeUsuario;
	private String ultimoComentario;
	private Date fechaMovimiento;
	private long idInstanciaDeTareaOrigen;
	private Date fechaInicio;
	private Date fechaFinalizacion;
	
	public long getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}
	public void setIdInstanciaDeTarea(long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
	}
	public String getNombreDeTarea() {
		return nombreDeTarea;
	}
	public void setNombreDeTarea(String nombreDeTarea) {
		this.nombreDeTarea = nombreDeTarea;
	}
	public String getIdDeUsuario() {
		return idDeUsuario;
	}
	public void setIdDeUsuario(String idDeUsuario) {
		this.idDeUsuario = idDeUsuario;
	}
	public String getUltimoComentario() {
		return ultimoComentario;
	}
	public void setUltimoComentario(String ultimoComentario) {
		this.ultimoComentario = ultimoComentario;
	}
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	public long getIdInstanciaDeTareaOrigen() {
		return idInstanciaDeTareaOrigen;
	}
	public void setIdInstanciaDeTareaOrigen(long idInstanciaDeTareaOrigen) {
		this.idInstanciaDeTareaOrigen = idInstanciaDeTareaOrigen;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}
	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}
	@Override
	public String toString() {
		return "HistorialProceso [idInstanciaDeTarea=" + idInstanciaDeTarea
				+ ", nombreDeTarea=" + nombreDeTarea + ", idDeUsuario="
				+ idDeUsuario + ", ultimoComentario=" + ultimoComentario
				+ ", fechaMovimiento=" + fechaMovimiento
				+ ", idInstanciaDeTareaOrigen=" + idInstanciaDeTareaOrigen
				+ ", fechaInicio=" + fechaInicio + ", fechaFinalizacion="
				+ fechaFinalizacion + "]";
	}
}
