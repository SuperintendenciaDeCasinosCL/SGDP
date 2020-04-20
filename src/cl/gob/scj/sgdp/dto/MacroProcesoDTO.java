package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class MacroProcesoDTO implements Serializable {

	private Long idMacroProceso;
	private String descripcionMacroProceso;
	private String nombreMacroProceso;
	
	public Long getIdMacroProceso() {
		return idMacroProceso;
	}
	public void setIdMacroProceso(Long idMacroProceso) {
		this.idMacroProceso = idMacroProceso;
	}
	public String getDescripcionMacroProceso() {
		return descripcionMacroProceso;
	}
	public void setDescripcionMacroProceso(String descripcionMacroProceso) {
		this.descripcionMacroProceso = descripcionMacroProceso;
	}
	public String getNombreMacroProceso() {
		return nombreMacroProceso;
	}
	public void setNombreMacroProceso(String nombreMacroProceso) {
		this.nombreMacroProceso = nombreMacroProceso;
	}
	
	public MacroProcesoDTO(Long idMacroProceso, String descripcionMacroProceso,
			String nombreMacroProceso) {
		super();
		this.idMacroProceso = idMacroProceso;
		this.descripcionMacroProceso = descripcionMacroProceso;
		this.nombreMacroProceso = nombreMacroProceso;
	}
	
}
