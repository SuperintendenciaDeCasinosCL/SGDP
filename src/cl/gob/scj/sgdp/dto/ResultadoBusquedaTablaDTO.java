package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

public class ResultadoBusquedaTablaDTO implements Serializable {

	private List<TablaRetencionDTO> tablasDTO;

	public ResultadoBusquedaTablaDTO() {
		super();

	}

	public List<TablaRetencionDTO> getTablasDTO() {
		return tablasDTO;
	}

	public void setTablasDTO(List<TablaRetencionDTO> tablasDTO) {
		this.tablasDTO = tablasDTO;
	}

	@Override
	public String toString() {
		return "ResultadoBusquedaTablaDTO [tablasDTO=" + tablasDTO + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8658817574384632000L;
}
