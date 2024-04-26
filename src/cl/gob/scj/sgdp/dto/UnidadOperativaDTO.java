package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class UnidadOperativaDTO extends RespuestaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idUnidadOperativa;
	private String codigoUnidadOperativa;
	private String nombreUnidadOperativa;
	
	
	
	public UnidadOperativaDTO() {
		super();
	}
	
	public UnidadOperativaDTO(Long idUnidadOperativa, String codigoUnidadOperativa, String nombreUnidadOperativa) {
		super();
		this.idUnidadOperativa = idUnidadOperativa;
		this.codigoUnidadOperativa = codigoUnidadOperativa;
		this.nombreUnidadOperativa = nombreUnidadOperativa;
	}
	public Long getIdUnidadOperativa() {
		return idUnidadOperativa;
	}
	public void setIdUnidadOperativa(Long idUnidadOperativa) {
		this.idUnidadOperativa = idUnidadOperativa;
	}
	public String getCodigoUnidadOperativa() {
		return codigoUnidadOperativa;
	}
	public void setCodigoUnidadOperativa(String codigoUnidadOperativa) {
		this.codigoUnidadOperativa = codigoUnidadOperativa;
	}
	public String getNombreUnidadOperativa() {
		return nombreUnidadOperativa;
	}
	public void setNombreUnidadOperativa(String nombreUnidadOperativa) {
		this.nombreUnidadOperativa = nombreUnidadOperativa;
	}

	@Override
	public String toString() {
		return "UnidadOperativaDTO [idUnidadOperativa=" + idUnidadOperativa + ", codigoUnidadOperativa="
				+ codigoUnidadOperativa + ", nombreUnidadOperativa=" + nombreUnidadOperativa + "]";
	}	
	
	
	
}
