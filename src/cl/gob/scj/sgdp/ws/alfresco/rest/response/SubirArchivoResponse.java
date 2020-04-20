package cl.gob.scj.sgdp.ws.alfresco.rest.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubirArchivoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idArchivo;

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	@Override
	public String toString() {
		return "SubirArchivoResponse [idArchivo=" + idArchivo + "]";
	}
	
	
}
