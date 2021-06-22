package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

public class ResultadoBusquedaEstadoTransferenciaDTO implements Serializable {

	private String mensajeError;
	private String mensajeRespuesta;
	private List<EstadoTransferenciaDTO> estadoTransferenciaList;

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

	public List<EstadoTransferenciaDTO> getEstadoTransferenciaList() {
		return estadoTransferenciaList;
	}

	public void setEstadoTransferenciaList(List<EstadoTransferenciaDTO> estadoTransferenciaList) {
		this.estadoTransferenciaList = estadoTransferenciaList;
	}

	public ResultadoBusquedaEstadoTransferenciaDTO() {
		super();

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8658817574384632000L;
}
