package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class FechaEstadoInstanciaProcesoDTO implements Serializable {

	private String fechaInicio;

	private String fechaFin;

	private String nombreEstadoDeProceso;

	public FechaEstadoInstanciaProcesoDTO() {
		super();
	}

	public FechaEstadoInstanciaProcesoDTO(String fechaInicio, String fechaFin, String nombreEstadoDeProceso) {
		super();
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.nombreEstadoDeProceso = nombreEstadoDeProceso;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getNombreEstadoDeProceso() {
		return nombreEstadoDeProceso;
	}

	public void setNombreEstadoDeProceso(String nombreEstadoDeProceso) {
		this.nombreEstadoDeProceso = nombreEstadoDeProceso;
	}

}
