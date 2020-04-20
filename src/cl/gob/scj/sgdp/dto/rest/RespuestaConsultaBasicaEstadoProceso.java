package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class RespuestaConsultaBasicaEstadoProceso implements Serializable  {

	private String nombreProceso;
	private String fechaInicioProceso;
	private String plazoTerminoProceso;
	private String etapaActual;
	private String estadoProceso;
	
	public RespuestaConsultaBasicaEstadoProceso() {
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

	public String getEstadoProceso() {
		return estadoProceso;
	}

	public void setEstadoProceso(String estadoProceso) {
		this.estadoProceso = estadoProceso;
	}

	


}
