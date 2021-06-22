package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class BuscarEnvioArchivoNacionalDTO implements Serializable {

	private String codSerie;
	private String codAcuerdo;

	public String getCodSerie() {
		return codSerie;
	}

	public void setCodSerie(String codSerie) {
		this.codSerie = codSerie;
	}

	public String getCodAcuerdo() {
		return codAcuerdo;
	}

	public void setCodAcuerdo(String codAcuerdo) {
		this.codAcuerdo = codAcuerdo;
	}

	@Override
	public String toString() {
		return "BuscarEnvioArchivoNacionalDTO [codSerie=" + codSerie + ", codAcuerdo=" + codAcuerdo + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8943585914150340900L;
}
