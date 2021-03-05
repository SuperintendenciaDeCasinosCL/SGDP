package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

import cl.gob.scj.sgdp.tipos.RequisitoType;

public class ParametroDeTareaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2051171311383931878L;
	private Long idParamTarea;
	private String nombreParamTarea;
	private TipoParametroDeTareaDTO tipoParametroDeTareaDTO;
	private String titulo;
	private String textoHtml;
	private Boolean vigente;
	private Boolean esSNC;
	private String nombreDeTipoDeRequisito;
	
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
	public Boolean getVigente() {
		return vigente;
	}
	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}
	public Boolean getEsSNC() {
		return esSNC;
	}	
	public String getNombreDeTipoDeRequisito() {
		return nombreDeTipoDeRequisito;
	}
	public void setNombreDeTipoDeRequisito(String nombreDeTipoDeRequisito) {
		this.nombreDeTipoDeRequisito = nombreDeTipoDeRequisito;
	}
	public void setEsSNC(Boolean esSNC) {
		if (esSNC != null && esSNC.booleanValue() == true) {
			this.nombreDeTipoDeRequisito = RequisitoType.SNC.getNombreDeTipoDeRequisito();
		} else {
			this.nombreDeTipoDeRequisito = RequisitoType.RDS.getNombreDeTipoDeRequisito();
		}
		this.esSNC = esSNC;
	}
	@Override
	public String toString() {
		return "ParametroDeTareaDTO [idParamTarea=" + idParamTarea + ", nombreParamTarea=" + nombreParamTarea
				+ ", tipoParametroDeTareaDTO=" + tipoParametroDeTareaDTO + ", titulo=" + titulo + ", textoHtml="
				+ textoHtml 
				+ ", vigente=" + vigente 
				+ ", esSNC=" + esSNC 
				+ ", nombreDeTipoDeRequisito=" + nombreDeTipoDeRequisito 
				+ "]";
	}	
}
