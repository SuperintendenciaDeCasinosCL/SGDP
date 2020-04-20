package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;
import java.util.List;

public class RespuestaTipoDocumentoPrimeraTareaDTO implements Serializable  {

	private String mensaje;
	private List<TipoDeDocumentoDTO> listaTipoDeDocumento;

	public RespuestaTipoDocumentoPrimeraTareaDTO() {
		super();
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<TipoDeDocumentoDTO> getListaTipoDeDocumento() {
		return listaTipoDeDocumento;
	}

	public void setListaTipoDeDocumento(List<TipoDeDocumentoDTO> listaTipoDeDocumento) {
		this.listaTipoDeDocumento = listaTipoDeDocumento;
	}

}
