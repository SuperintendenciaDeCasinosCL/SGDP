package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class TareaYTipoDeDocumentoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long idTarea;
	private String nombreTarea;
	private long idTipoDeDocumento;
	private String nombreDeTipoDeDocumento;
	private Long idPlantilla;
	private String nombrePlantilla;
	private String codigoTarea;
	
	public TareaYTipoDeDocumentoDTO() {
	}

	public long getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(long idTarea) {
		this.idTarea = idTarea;
	}

	public String getNombreTarea() {
		return nombreTarea;
	}

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

	public long getIdTipoDeDocumento() {
		return idTipoDeDocumento;
	}

	public void setIdTipoDeDocumento(long idTipoDeDocumento) {
		this.idTipoDeDocumento = idTipoDeDocumento;
	}

	public String getNombreDeTipoDeDocumento() {
		return nombreDeTipoDeDocumento;
	}

	public void setNombreDeTipoDeDocumento(String nombreDeTipoDeDocumento) {
		this.nombreDeTipoDeDocumento = nombreDeTipoDeDocumento;
	}

	public Long getIdPlantilla() {
		return idPlantilla;
	}

	public void setIdPlantilla(Long idPlantilla) {
		this.idPlantilla = idPlantilla;
	}

	public String getNombrePlantilla() {
		return nombrePlantilla;
	}

	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}

	public String getCodigoTarea() {
		return codigoTarea;
	}

	public void setCodigoTarea(String codigoTarea) {
		this.codigoTarea = codigoTarea;
	}
	
	
}
