package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;
import java.util.List;

public class TiposDocumetosArchivoNacionalResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String codigoRespuesta;
	private String mensajeRespuesta;
	private List<TiposDocumetosArchivoNacionalDTO> listaTiposDocumetosArchivoNacionalDTO;
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
	public List<TiposDocumetosArchivoNacionalDTO> getListaTiposDocumetosArchivoNacionalDTO() {
		return listaTiposDocumetosArchivoNacionalDTO;
	}
	public void setListaTiposDocumetosArchivoNacionalDTO(
			List<TiposDocumetosArchivoNacionalDTO> listaTiposDocumetosArchivoNacionalDTO) {
		this.listaTiposDocumetosArchivoNacionalDTO = listaTiposDocumetosArchivoNacionalDTO;
	}
	@Override
	public String toString() {
		return "TiposDocumetosArchivoNacionalResponse [codigoRespuesta=" + codigoRespuesta + ", mensajeRespuesta="
				+ mensajeRespuesta + ", listaTiposDocumetosArchivoNacionalDTO=" + listaTiposDocumetosArchivoNacionalDTO.toString()
				+ "]";
	}
	
	
		
	
}
