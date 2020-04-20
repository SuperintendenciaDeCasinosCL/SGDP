package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class TareaRestDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8396599069574524160L;
	
	private Long idTarea;
	private String descripcionTarea;
	private String nombreTarea;
	private Integer diasHabilesMaxDuracion;
	private Integer orden;
	private Boolean vigente;
	private String idDiagrama;
	
	public TareaRestDTO() {
		super();
	}	
	public TareaRestDTO(Long idTarea, String descripcionTarea, String nombreTarea, Integer diasHabilesMaxDuracion,
			Integer orden, Boolean vigente, String idDiagrama) {
		super();
		this.idTarea = idTarea;
		this.descripcionTarea = descripcionTarea;
		this.nombreTarea = nombreTarea;
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
		this.orden = orden;
		this.vigente = vigente;
		this.idDiagrama = idDiagrama;
	}
	
	public Long getIdTarea() {
		return idTarea;
	}
	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}
	public String getDescripcionTarea() {
		return descripcionTarea;
	}
	public void setDescripcionTarea(String descripcionTarea) {
		this.descripcionTarea = descripcionTarea;
	}
	public String getNombreTarea() {
		return nombreTarea;
	}
	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}
	public Integer getDiasHabilesMaxDuracion() {
		return diasHabilesMaxDuracion;
	}
	public void setDiasHabilesMaxDuracion(Integer diasHabilesMaxDuracion) {
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
	}
	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	public Boolean getVigente() {
		return vigente;
	}
	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}	
	public String getIdDiagrama() {
		return idDiagrama;
	}
	public void setIdDiagrama(String idDiagrama) {
		this.idDiagrama = idDiagrama;
	}
	@Override
	public String toString() {
		return "TareaRestDTO [idTarea=" + idTarea + ", descripcionTarea=" + descripcionTarea + ", nombreTarea="
				+ nombreTarea + ", diasHabilesMaxDuracion=" + diasHabilesMaxDuracion + ", orden=" + orden 
				+ ", vigente=" + vigente
				+ ", idDiagrama=" + idDiagrama 
				+ "]";
	}
}
