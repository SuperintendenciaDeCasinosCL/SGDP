package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class ExpedienteContieneDTO implements Serializable {

	private String nombreDeExpedienteContiene;
	private String idExpedienteContiene;
	
	public String getNombreDeExpedienteContiene() {
		return nombreDeExpedienteContiene;
	}
	public void setNombreDeExpedienteContiene(String nombreDeExpedienteContiene) {
		this.nombreDeExpedienteContiene = nombreDeExpedienteContiene;
	}
	public String getIdExpedienteContiene() {
		return idExpedienteContiene;
	}
	public void setIdExpedienteContiene(String idExpedienteContiene) {
		this.idExpedienteContiene = idExpedienteContiene;
	}
	@Override
	public String toString() {
		return "ExpedienteContiene [nombreDeExpedienteContiene="
				+ nombreDeExpedienteContiene + ", idExpedienteContiene="
				+ idExpedienteContiene + "]";
	}	
	
}
