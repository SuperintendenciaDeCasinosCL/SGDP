package cl.gob.scj.sgdp.dto.rest;

import java.util.List;

public class ObtenerTodosLosProcesosResponse {
	
	private List<ProcesoRestDTO> listaProcesoRestDTO;	
	private String mensaje;
	private String detalleRespuesta;
	
	public List<ProcesoRestDTO> getListaProcesoRestDTO() {
		return listaProcesoRestDTO;
	}
	public void setListaProcesoRestDTO(List<ProcesoRestDTO> listaProcesoRestDTO) {
		this.listaProcesoRestDTO = listaProcesoRestDTO;
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
		return "ObtenerTodosLosProcesosResponse [mensaje=" + mensaje + ", detalleRespuesta="
				+ detalleRespuesta + "]";
	}	
}
