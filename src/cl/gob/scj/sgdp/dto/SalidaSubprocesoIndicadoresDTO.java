package cl.gob.scj.sgdp.dto;

import java.util.List;

public class SalidaSubprocesoIndicadoresDTO {

	private String respuesta;
	private String descripcionRespuesta;
	private List<SubprocesoIndicadoresSalidaDTO> subprocesoIndicadoresSalidaDTO;
	
	// 
	
	private Integer numeroElementosDentroDelPlazo;
	private Integer numeroElementosfueraDelPlazo;
	private Integer total;
	
	public SalidaSubprocesoIndicadoresDTO() {
		super();
	}


	public String getRespuesta() {
		return respuesta;
	}


	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}


	public String getDescripcionRespuesta() {
		return descripcionRespuesta;
	}


	public void setDescripcionRespuesta(String descripcionRespuesta) {
		this.descripcionRespuesta = descripcionRespuesta;
	}


	public List<SubprocesoIndicadoresSalidaDTO> getSubprocesoIndicadoresSalidaDTO() {
		return subprocesoIndicadoresSalidaDTO;
	}


	public void setSubprocesoIndicadoresSalidaDTO(List<SubprocesoIndicadoresSalidaDTO> subprocesoIndicadoresSalidaDTO) {
		this.subprocesoIndicadoresSalidaDTO = subprocesoIndicadoresSalidaDTO;
	}


	public Integer getNumeroElementosDentroDelPlazo() {
		return numeroElementosDentroDelPlazo;
	}


	public void setNumeroElementosDentroDelPlazo(Integer numeroElementosDentroDelPlazo) {
		this.numeroElementosDentroDelPlazo = numeroElementosDentroDelPlazo;
	}


	public Integer getNumeroElementosfueraDelPlazo() {
		return numeroElementosfueraDelPlazo;
	}


	public void setNumeroElementosfueraDelPlazo(Integer numeroElementosfueraDelPlazo) {
		this.numeroElementosfueraDelPlazo = numeroElementosfueraDelPlazo;
	}


	public Integer getTotal() {
		return total;
	}


	public void setTotal(Integer total) {
		this.total = total;
	}



	
	
	
}
