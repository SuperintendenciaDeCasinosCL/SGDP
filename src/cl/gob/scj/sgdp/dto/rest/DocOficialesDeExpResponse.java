package cl.gob.scj.sgdp.dto.rest;

import java.util.List;

public class DocOficialesDeExpResponse extends RespuestaDTO {
	
	private List<DetalleDeArchivoDTORest> detalleDeDocOficiales;

	public List<DetalleDeArchivoDTORest> getDetalleDeDocOficiales() {
		return detalleDeDocOficiales;
	}

	public void setDetalleDeDocOficiales(List<DetalleDeArchivoDTORest> detalleDeDocOficiales) {
		this.detalleDeDocOficiales = detalleDeDocOficiales;
	}

	@Override
	public String toString() {
		return "DocOficialesDeExpResponse [detalleDeDocOficiales=" + detalleDeDocOficiales + "]";
	}

}
