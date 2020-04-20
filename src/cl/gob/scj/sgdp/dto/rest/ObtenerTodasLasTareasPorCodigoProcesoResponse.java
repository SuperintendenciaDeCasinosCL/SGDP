package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;
import java.util.List;

public class ObtenerTodasLasTareasPorCodigoProcesoResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3159729269577993126L;

	private List<TareaRestDTO> listaTareasRestDTO;	
	private String mensaje;
	private String detalleRespuesta;
	
	public List<TareaRestDTO> getListaTareasRestDTO() {
		return listaTareasRestDTO;
	}
	public void setListaTareasRestDTO(List<TareaRestDTO> listaTareasRestDTO) {
		this.listaTareasRestDTO = listaTareasRestDTO;
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
		return "ObtenerTodasLasTareasPorCodigoProcesoResponse [mensaje=" + mensaje + ", detalleRespuesta="
				+ detalleRespuesta + "]";
	}
}
