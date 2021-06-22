package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

public class RespuestaEnviarArchivoNacionalDTO implements Serializable {

	private String mensajeError;
	private String mensajeRespuesta;
	private List<UuidCarpeta> listaUuid;
	//private String idTransferencia;

	public RespuestaEnviarArchivoNacionalDTO() {
		super();
	}

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

	/*public String getIdTransferencia() {
		return idTransferencia;
	}

	public void setIdTransferencia(String idTransferencia) {
		this.idTransferencia = idTransferencia;
	}*/

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "RespuestaEnviarArchivoNacionalDTO [mensajeError=" + mensajeError + ", mensajeRespuesta="
				+ mensajeRespuesta + ", listaUuid=" + listaUuid + "]";
	}

	public List<UuidCarpeta> getListaUuid() {
		return listaUuid;
	}

	public void setListaUuid(List<UuidCarpeta> listaUuid) {
		this.listaUuid = listaUuid;
	}

	

	
}
