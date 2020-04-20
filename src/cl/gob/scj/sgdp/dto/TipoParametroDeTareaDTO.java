package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class TipoParametroDeTareaDTO implements Serializable {

	private Long idTipoParametroDeTarea;
	private String nombreTipoParametroDeTarea;
	private String textoHtml;
	private Boolean comenta;
	
	public Long getIdTipoParametroDeTarea() {
		return idTipoParametroDeTarea;
	}
	public void setIdTipoParametroDeTarea(Long idTipoParametroDeTarea) {
		this.idTipoParametroDeTarea = idTipoParametroDeTarea;
	}
	public String getNombreTipoParametroDeTarea() {
		return nombreTipoParametroDeTarea;
	}
	public void setNombreTipoParametroDeTarea(String nombreTipoParametroDeTarea) {
		this.nombreTipoParametroDeTarea = nombreTipoParametroDeTarea;
	}
	public String getTextoHtml() {
		return textoHtml;
	}
	public void setTextoHtml(String textoHtml) {
		this.textoHtml = textoHtml;
	}	
	public Boolean getComenta() {
		return comenta;
	}
	public void setComenta(Boolean comenta) {
		this.comenta = comenta;
	}
	@Override
	public String toString() {
		return "TipoParametroDeTareaDTO [idTipoParametroDeTarea=" + idTipoParametroDeTarea
				+ ", nombreTipoParametroDeTarea=" + nombreTipoParametroDeTarea 
				+ ", comenta=" + comenta 
				+ ", textoHtml=" + textoHtml + "]";
	}	
}
