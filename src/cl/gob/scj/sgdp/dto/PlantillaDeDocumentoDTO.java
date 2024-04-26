package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class PlantillaDeDocumentoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long idPlantilla;
	private String nombre;
	private String codigo;
	private Boolean vigente;
	private String plantilla;
		
	public PlantillaDeDocumentoDTO() {

	}

	public long getIdPlantilla() {
		return idPlantilla;
	}

	public void setIdPlantilla(long idPlantilla) {
		this.idPlantilla = idPlantilla;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public String getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}

	
}
