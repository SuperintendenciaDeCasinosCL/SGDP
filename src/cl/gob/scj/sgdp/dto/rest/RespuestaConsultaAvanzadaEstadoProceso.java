package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class RespuestaConsultaAvanzadaEstadoProceso implements Serializable  {

	private String nombreProceso;
	private String fechaInicioProceso;
	private String plazoTerminoProceso;
	private String etapaActual;
	private String tareaActual;
	private String usuarioActual;
	private String fechaTareaActual;
	private String areaResponsable;
	private String estadoProceso;
	
	public RespuestaConsultaAvanzadaEstadoProceso() {
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public String getFechaInicioProceso() {
		return fechaInicioProceso;
	}

	public void setFechaInicioProceso(String fechaInicioProceso) {
		this.fechaInicioProceso = fechaInicioProceso;
	}

	public String getPlazoTerminoProceso() {
		return plazoTerminoProceso;
	}

	public void setPlazoTerminoProceso(String plazoTerminoProceso) {
		this.plazoTerminoProceso = plazoTerminoProceso;
	}

	public String getEtapaActual() {
		return etapaActual;
	}

	public void setEtapaActual(String etapaActual) {
		this.etapaActual = etapaActual;
	}

	public String getTareaActual() {
		return tareaActual;
	}

	public void setTareaActual(String tareaActual) {
		this.tareaActual = tareaActual;
	}

	public String getUsuarioActual() {
		return usuarioActual;
	}

	public void setUsuarioActual(String usuarioActual) {
		this.usuarioActual = usuarioActual;
	}

	public String getFechaTareaActual() {
		return fechaTareaActual;
	}

	public void setFechaTareaActual(String fechaTareaActual) {
		this.fechaTareaActual = fechaTareaActual;
	}

	public String getAreaResponsable() {
		return areaResponsable;
	}

	public void setAreaResponsable(String areaResponsable) {
		this.areaResponsable = areaResponsable;
	}

	public String getEstadoProceso() {
		return estadoProceso;
	}

	public void setEstadoProceso(String estadoProceso) {
		this.estadoProceso = estadoProceso;
	}


}
