package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class CantidadArchivosDTO implements Serializable {

	private int cantidadArchivos;
	private int cantidadArchivosAux;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7883864840323228777L;

	public int getCantidadArchivos() {
		return cantidadArchivos;
	}

	public void setCantidadArchivos(int cantidadArchivos) {
		this.cantidadArchivos = cantidadArchivos;
	}

	public int getCantidadArchivosAux() {
		return cantidadArchivosAux;
	}

	public void setCantidadArchivosAux(int cantidadArchivosAux) {
		this.cantidadArchivosAux = cantidadArchivosAux;
	}

}
