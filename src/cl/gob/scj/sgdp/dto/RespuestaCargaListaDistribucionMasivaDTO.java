package cl.gob.scj.sgdp.dto;

import java.util.List;

public class RespuestaCargaListaDistribucionMasivaDTO {

	private List<ListaDeDistribucionDTO> listaDeDistribucionDTO;
	private List<ListaDeDistribucionDTO> listaDeDistribucionDTOResultadoCargaMasiva;
	private String cssStatus;
	private String mensaje;
	
	public List<ListaDeDistribucionDTO> getListaDeDistribucionDTO() {
		return listaDeDistribucionDTO;
	}
	public void setListaDeDistribucionDTO(List<ListaDeDistribucionDTO> listaDeDistribucionDTO) {
		this.listaDeDistribucionDTO = listaDeDistribucionDTO;
	}
	public String getCssStatus() {
		return cssStatus;
	}
	public void setCssStatus(String cssStatus) {
		this.cssStatus = cssStatus;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public List<ListaDeDistribucionDTO> getListaDeDistribucionDTOResultadoCargaMasiva() {
		return listaDeDistribucionDTOResultadoCargaMasiva;
	}
	public void setListaDeDistribucionDTOResultadoCargaMasiva(
			List<ListaDeDistribucionDTO> listaDeDistribucionDTOResultadoCargaMasiva) {
		this.listaDeDistribucionDTOResultadoCargaMasiva = listaDeDistribucionDTOResultadoCargaMasiva;
	}
	
}
