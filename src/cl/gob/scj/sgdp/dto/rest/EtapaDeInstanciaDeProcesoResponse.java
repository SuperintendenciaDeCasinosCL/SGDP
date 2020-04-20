package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;
import java.util.List;

import cl.gob.scj.sgdp.dto.EtapaDeInstanciaDeProcesoDTO;

public class EtapaDeInstanciaDeProcesoResponse implements Serializable  {

	private List<EtapaDeInstanciaDeProcesoDTO> etapasDeInstanciaDeProcesoDTO;
	
	private String codigoRespuesta;
	private String mensajeRespuesta;
	
	public List<EtapaDeInstanciaDeProcesoDTO> getEtapasDeInstanciaDeProcesoDTO() {
		return etapasDeInstanciaDeProcesoDTO;
	}
	public void setEtapasDeInstanciaDeProcesoDTO(List<EtapaDeInstanciaDeProcesoDTO> etapasDeInstanciaDeProcesoDTO) {
		this.etapasDeInstanciaDeProcesoDTO = etapasDeInstanciaDeProcesoDTO;
	}
	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}
	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}
	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}
	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}
	@Override
	public String toString() {
		return "EtapaDeInstanciaDeProcesoResponse [etapasDeInstanciaDeProcesoDTO=" + etapasDeInstanciaDeProcesoDTO
				+ ", codigoRespuesta=" + codigoRespuesta + ", mensajeRespuesta=" + mensajeRespuesta + "]";
	}
}
