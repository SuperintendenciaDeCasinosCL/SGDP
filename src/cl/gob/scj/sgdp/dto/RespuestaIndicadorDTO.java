package cl.gob.scj.sgdp.dto;


import java.io.Serializable;
import java.util.List;

public class RespuestaIndicadorDTO implements Serializable{

	private String Respuesta;
	private String descripcionRespuesta;
	
	private List<IndicadorDTO> listaIndicadoresDTO;
	
	
	public String getRespuesta() {
		return Respuesta;
	}
	public void setRespuesta(String respuesta) {
		Respuesta = respuesta;
	}
	public List<IndicadorDTO> getListaIndicadoresDTO() {
		return listaIndicadoresDTO;
	}
	public void setListaIndicadoresDTO(List<IndicadorDTO> listaIndicadoresDTO) {
		this.listaIndicadoresDTO = listaIndicadoresDTO;
	}
	public String getDescripcionRespuesta() {
		return descripcionRespuesta;
	}
	public void setDescripcionRespuesta(String descripcionRespuesta) {
		this.descripcionRespuesta = descripcionRespuesta;
	}
	@Override
	public String toString() {
		return "RespuestaIndicadorDTO [Respuesta=" + Respuesta + ", descripcionRespuesta=" + descripcionRespuesta
				+ ", listaIndicadoresDTO=" + listaIndicadoresDTO + "]";
	}
	
	
	
	
}
