package cl.gob.scj.sgdp.dto.rest;

import java.util.List;

public class ObtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse {
	
	private List<TipoDeDocumentoDTO> listaTipoDeDocumento;	
	private String mensaje;
	private String detalleRespuesta;
	
	public List<TipoDeDocumentoDTO> getListaTipoDeDocumento() {
		return listaTipoDeDocumento;
	}
	public void setListaTipoDeDocumento(List<TipoDeDocumentoDTO> listaTipoDeDocumento) {
		this.listaTipoDeDocumento = listaTipoDeDocumento;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getDetalleRespuesta() {
		return detalleRespuesta;
	}
	public void setDetalleRespuesta(String detalleRespuesta) {
		this.detalleRespuesta = detalleRespuesta;
	}
	@Override
	public String toString() {
		return "ObtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse [mensaje=" + mensaje + ", detalleRespuesta="
				+ detalleRespuesta + "]";
	}
}
