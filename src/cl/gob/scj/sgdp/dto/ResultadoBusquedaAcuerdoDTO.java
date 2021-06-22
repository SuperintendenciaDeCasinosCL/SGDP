package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

public class ResultadoBusquedaAcuerdoDTO implements Serializable {

	private String mensajeError;
	private String mensajeRespuesta;
	private List<AcuerdoTransferenciaDTO> acuerdosDTO;

	
	public String getMensajeError() {
		return mensajeError;
	}


	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}


	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}


	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}


	public ResultadoBusquedaAcuerdoDTO() {
		super();
	
	}


	public List<AcuerdoTransferenciaDTO> getAcuerdosDTO() {
		return acuerdosDTO;
	}


	public void setAcuerdosDTO(List<AcuerdoTransferenciaDTO> acuerdosDTO) {
		this.acuerdosDTO = acuerdosDTO;
	}



	@Override
	public String toString() {
		return "ResultadoBusquedaAcuerdoDTO [mensajeError=" + mensajeError + ", mensajeRespuesta=" + mensajeRespuesta
				+ ", acuerdosDTO=" + acuerdosDTO + "]";
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 8658817574384632000L;
}
