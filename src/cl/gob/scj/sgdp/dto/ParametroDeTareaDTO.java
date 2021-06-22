package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class ParametroDeTareaDTO implements Serializable {

	private Long idParamTarea;
	private String nombreParamTarea;
	private TipoParametroDeTareaDTO tipoParametroDeTareaDTO;
	private String titulo;
	private String textoHtml;
	
	public Long getIdParamTarea() {
		return idParamTarea;
	}
	public void setIdParamTarea(Long idParamTarea) {
		this.idParamTarea = idParamTarea;
	}
	public String getNombreParamTarea() {
		return nombreParamTarea.replace("_", " ");
	}
	public void setNombreParamTarea(String nombreParamTarea) {
		this.nombreParamTarea = nombreParamTarea;
	}	
	public TipoParametroDeTareaDTO getTipoParametroDeTareaDTO() {
		return tipoParametroDeTareaDTO;
	}
	public void setTipoParametroDeTareaDTO(TipoParametroDeTareaDTO tipoParametroDeTareaDTO) {
		this.tipoParametroDeTareaDTO = tipoParametroDeTareaDTO;
	}	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}	
	public String getTextoHtml() {
		return textoHtml;
	}
	public void setTextoHtml(String textoHtml) {
		this.textoHtml = textoHtml;
	}
	@Override
	public String toString() {
		return "ParametroDeTareaDTO [idParamTarea=" + idParamTarea + ", nombreParamTarea=" + nombreParamTarea + ", titulo=" + titulo
				+ ", tipoParametroDeTareaDTO=" + tipoParametroDeTareaDTO
				+ ", textoHtml=" + textoHtml
				+ "]";
	}	
}
