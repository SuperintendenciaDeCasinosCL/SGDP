package cl.gob.scj.sgdp.ws.alfresco.rest.response;

import java.io.Serializable;
import java.util.List;

import cl.gob.scj.sgdp.dto.ArchivoInfoDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ObtenerArchivosExpedienteResponse implements Serializable {

	private static final long serialVersionUID = 5224138443236204083L;
	
	List<ArchivoInfoDTO> listaDeArchivos;

	public List<ArchivoInfoDTO> getListaDeArchivos() {
		return listaDeArchivos;
	}

	public void setListaDeArchivos(List<ArchivoInfoDTO> listaDeArchivos) {
		this.listaDeArchivos = listaDeArchivos;
	}	

}
