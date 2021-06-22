package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class BuscarAcuerdoDTO implements Serializable {
	

	private String codSerie;

	public String getCodSerie() {
		return codSerie;
	}

	public void setCodSerie(String codSerie) {
		this.codSerie = codSerie;
	}

	@Override
	public String toString() {
		return "BuscarAcuerdoDTO [codSerie=" + codSerie + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8943585914150340900L;
}
