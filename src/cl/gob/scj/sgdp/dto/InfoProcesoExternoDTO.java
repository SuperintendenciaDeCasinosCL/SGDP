package cl.gob.scj.sgdp.dto;

import java.io.Serializable;


public class InfoProcesoExternoDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombreProceso;
	private String expediente;
	private String etapaActual;
	private String estadoActual;
	private Long diasDesdeInicio;
	private Long diasRestantesDePlazo;
	
	
	public InfoProcesoExternoDTO() {
		
	}


	public String getNombreProceso() {
		return nombreProceso;
	}


	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}


	public String getEtapaActual() {
		return etapaActual;
	}


	public void setEtapaActual(String etapaActual) {
		this.etapaActual = etapaActual;
	}


	public String getEstadoActual() {
		return estadoActual;
	}


	public void setEstadoActual(String estadoActual) {
		this.estadoActual = estadoActual;
	}


	public Long getDiasDesdeInicio() {
		return diasDesdeInicio;
	}


	public void setDiasDesdeInicio(Long diasDesdeInicio) {
		this.diasDesdeInicio = diasDesdeInicio;
	}


	public Long getDiasRestantesDePlazo() {
		return diasRestantesDePlazo;
	}


	public void setDiasRestantesDePlazo(Long diasRestantesDePlazo) {
		this.diasRestantesDePlazo = diasRestantesDePlazo;
	}


	public String getExpediente() {
		return expediente;
	}


	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

		
}
