package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class ConsultaEstadoProceso implements Serializable  {

	private String idExpediente;

	public ConsultaEstadoProceso() {
	}

	public String getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}

	
}
