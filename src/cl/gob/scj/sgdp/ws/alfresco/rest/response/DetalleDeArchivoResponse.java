package cl.gob.scj.sgdp.ws.alfresco.rest.response;

import java.io.Serializable;

import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;

public class DetalleDeArchivoResponse implements Serializable {

	private DetalleDeArchivoDTO detalleDeArchivoDTO;

	public DetalleDeArchivoDTO getDetalleDeArchivoDTO() {
		return detalleDeArchivoDTO;
	}

	public void setDetalleDeArchivoDTO(DetalleDeArchivoDTO detalleDeArchivoDTO) {
		this.detalleDeArchivoDTO = detalleDeArchivoDTO;
	}		
	
}
