package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class ProcesoDTO implements Serializable {

	private Long idProceso;
	private String descripcionProceso;
	private String nombreProceso;
	private Boolean vigente;
	private Integer diasHabilesMaxDuracion;
	private String nombreMacroproceso;
	private String codigoProceso;

	public ProcesoDTO(Long idProceso, String descripcionProceso,
			String nombreProceso, Boolean vigente,
			Integer diasHabilesMaxDuracion) {
		super();
		this.idProceso = idProceso;
		this.descripcionProceso = descripcionProceso;
		this.nombreProceso = nombreProceso;
		this.vigente = vigente;
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
	}
	
	
	public ProcesoDTO(Long idProceso, String descripcionProceso,
			String nombreProceso, Boolean vigente,
			Integer diasHabilesMaxDuracion, String nombreMacroproceso, String codigoProceso) {
		super();
		this.idProceso = idProceso;
		this.descripcionProceso = descripcionProceso;
		this.nombreProceso = nombreProceso;
		this.vigente = vigente;
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
		this.nombreMacroproceso = nombreMacroproceso;
		this.codigoProceso = codigoProceso;
	}

	public Long getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}
	public String getDescripcionProceso() {
		return descripcionProceso;
	}
	public void setDescripcionProceso(String descripcionProceso) {
		this.descripcionProceso = descripcionProceso;
	}
	public String getNombreProceso() {
		return nombreProceso;
	}
	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}
	public Boolean getVigente() {
		return vigente;
	}
	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}
	public Integer getDiasHabilesMaxDuracion() {
		return diasHabilesMaxDuracion;
	}
	public void setDiasHabilesMaxDuracion(Integer diasHabilesMaxDuracion) {
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
	}
	public String getNombreMacroproceso() {
		return nombreMacroproceso;
	}
	public void setNombreMacroproceso(String nombreMacroproceso) {
		this.nombreMacroproceso = nombreMacroproceso;
	}
	public String getCodigoProceso() {
		return codigoProceso;
	}
	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}
	@Override
	public String toString() {
		return "ProcesoDTO [idProceso=" + idProceso + ", descripcionProceso=" + descripcionProceso + ", nombreProceso="
				+ nombreProceso + ", vigente=" + vigente + ", diasHabilesMaxDuracion=" + diasHabilesMaxDuracion
				+ ", nombreMacroproceso=" + nombreMacroproceso + ", codigoProceso=" + codigoProceso + "]";
	}	
}
