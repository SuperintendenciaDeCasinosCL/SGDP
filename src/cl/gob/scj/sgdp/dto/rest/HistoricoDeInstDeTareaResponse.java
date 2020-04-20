package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;
import java.util.List;

import cl.gob.scj.sgdp.dto.rest.HistoricoDeInstDeTareaDTO;

public class HistoricoDeInstDeTareaResponse implements Serializable  {

	private List<HistoricoDeInstDeTareaDTO> historicoDeInstDeTareasDTO;
	private String codigoRespuesta;
	private String mensajeRespuesta;

	public List<HistoricoDeInstDeTareaDTO> getHistoricoDeInstDeTareasDTO() {
		return historicoDeInstDeTareasDTO;
	}

	public void setHistoricoDeInstDeTareasDTO(List<HistoricoDeInstDeTareaDTO> historicoDeInstDeTareasDTO) {
		this.historicoDeInstDeTareasDTO = historicoDeInstDeTareasDTO;
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
		return "HistoricoDeInstDeTareaResponse [historicoDeInstDeTareasDTO=" + historicoDeInstDeTareasDTO
				+ ", codigoRespuesta=" + codigoRespuesta + ", mensajeRespuesta=" + mensajeRespuesta + "]";
	}
	
}
