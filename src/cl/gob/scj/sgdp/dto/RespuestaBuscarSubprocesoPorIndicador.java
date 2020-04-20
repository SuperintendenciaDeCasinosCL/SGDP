package cl.gob.scj.sgdp.dto;

import java.util.List;

public class RespuestaBuscarSubprocesoPorIndicador {

	private String Respuesta;
	private String descripcionRespuesta;

	private List<IndicadorSubprocesoDTO> listaIndicadorSubprocesoDTO;

	public RespuestaBuscarSubprocesoPorIndicador() {
		super();
	}

	public String getRespuesta() {
		return Respuesta;
	}

	public void setRespuesta(String respuesta) {
		Respuesta = respuesta;
	}

	public String getDescripcionRespuesta() {
		return descripcionRespuesta;
	}

	public void setDescripcionRespuesta(String descripcionRespuesta) {
		this.descripcionRespuesta = descripcionRespuesta;
	}

	public List<IndicadorSubprocesoDTO> getListaIndicadorSubprocesoDTO() {
		return listaIndicadorSubprocesoDTO;
	}

	public void setListaIndicadorSubprocesoDTO(List<IndicadorSubprocesoDTO> listaIndicadorSubprocesoDTO) {
		this.listaIndicadorSubprocesoDTO = listaIndicadorSubprocesoDTO;
	}

	
	
	
}
