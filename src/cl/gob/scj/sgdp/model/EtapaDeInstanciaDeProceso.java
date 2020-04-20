package cl.gob.scj.sgdp.model;

import java.io.Serializable;

public class EtapaDeInstanciaDeProceso implements Serializable {

	private long idEtapa;
	private String nombreEtapa;
	private long codigoEstadoDeTarea;
	
	public long getIdEtapa() {
		return idEtapa;
	}
	public void setIdEtapa(long idEtapa) {
		this.idEtapa = idEtapa;
	}
	public String getNombreEtapa() {
		return nombreEtapa;
	}
	public void setNombreEtapa(String nombreEtapa) {
		this.nombreEtapa = nombreEtapa;
	}
	public long getCodigoEstadoDeTarea() {
		return codigoEstadoDeTarea;
	}
	public void setCodigoEstadoDeTarea(long codigoEstadoDeTarea) {
		this.codigoEstadoDeTarea = codigoEstadoDeTarea;
	}	
}
