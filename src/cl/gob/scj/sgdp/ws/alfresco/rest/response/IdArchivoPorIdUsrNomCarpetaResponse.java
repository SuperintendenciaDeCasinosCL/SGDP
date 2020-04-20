package cl.gob.scj.sgdp.ws.alfresco.rest.response;

import java.io.Serializable;

public class IdArchivoPorIdUsrNomCarpetaResponse implements Serializable  {

	private String idArchivo;

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	@Override
	public String toString() {
		return "IdArchivoPorIdUsrNomCarpetaResponse [idArchivo=" + idArchivo + "]";
	}
	
}
