package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

@SuppressWarnings("serial")
public class HistorialProcesoDTO implements Serializable, Comparator<HistorialProcesoDTO>, Comparable<HistorialProcesoDTO> {
	
	private long idInstanciaDeTarea;
	private String nombreDeTarea;
	private String idDeUsuario;
	private String ultimoComentario;
	private Date fechaMovimiento;
	private long idInstanciaDeTareaOrigen;
	private Date fechaInicio;
	private Date fechaFinalizacion;
	private String comentario;
	private String accion;
	private String nombreDelProceso;
	
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
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}	
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}	
	public String getNombreDelProceso() {
		return nombreDelProceso;
	}
	public void setNombreDelProceso(String nombreDelProceso) {
		this.nombreDelProceso = nombreDelProceso;
	}	
	@Override
	public String toString() {
		return "HistorialProcesoDTO [idInstanciaDeTarea=" + idInstanciaDeTarea + ", nombreDeTarea=" + nombreDeTarea
				+ ", idDeUsuario=" + idDeUsuario + ", ultimoComentario=" + ultimoComentario + ", fechaMovimiento="
				+ fechaMovimiento + ", idInstanciaDeTareaOrigen=" + idInstanciaDeTareaOrigen + ", fechaInicio="
				+ fechaInicio + ", fechaFinalizacion=" + fechaFinalizacion + ", comentario=" + comentario + ", accion="
				+ accion + ", nombreDelProceso=" + nombreDelProceso + "]";
	}
	@Override
	public int compare(HistorialProcesoDTO o1, HistorialProcesoDTO o2) {
		return o1.getFechaMovimiento().compareTo(o2.getFechaMovimiento());
	}
	@Override
	public int compareTo(HistorialProcesoDTO o) {
		return this.getFechaMovimiento().compareTo(o.getFechaMovimiento());
	}
}
