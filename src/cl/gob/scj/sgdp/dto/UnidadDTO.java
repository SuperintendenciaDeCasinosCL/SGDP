package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class UnidadDTO  extends RespuestaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idUnidad;
	private String codigoUnidad;
	private String nombreCompletoUnidad;	
	private Long idUnidadOperativa;
	private String nombreUnidadOperativa;
	private String loginHeaderMultiOficinaB64;
	
	public UnidadDTO() {
		super();
	}

	public UnidadDTO(Long idUnidad, String codigoUnidad,
			String nombreCompletoUnidad) {
		super();
		this.idUnidad = idUnidad;
		this.codigoUnidad = codigoUnidad;
		this.nombreCompletoUnidad = nombreCompletoUnidad;
	}
	
	public UnidadDTO(Long idUnidad, String codigoUnidad,
			String nombreCompletoUnidad, Long idUnidadOperativa, String loginHeaderMultiOficinaB64) {
		super();
		this.idUnidad = idUnidad;
		this.codigoUnidad = codigoUnidad;
		this.nombreCompletoUnidad = nombreCompletoUnidad;
		this.idUnidadOperativa = idUnidadOperativa;
		this.loginHeaderMultiOficinaB64 = loginHeaderMultiOficinaB64;
	}

	public Long getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Long idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getCodigoUnidad() {
		return codigoUnidad;
	}

	public void setCodigoUnidad(String codigoUnidad) {
		this.codigoUnidad = codigoUnidad;
	}

	public String getNombreCompletoUnidad() {
		return nombreCompletoUnidad;
	}

	public void setNombreCompletoUnidad(String nombreCompletoUnidad) {
		this.nombreCompletoUnidad = nombreCompletoUnidad;
	}

	public Long getIdUnidadOperativa() {
		return idUnidadOperativa;
	}

	public void setIdUnidadOperativa(Long idUnidadOperativa) {
		this.idUnidadOperativa = idUnidadOperativa;
	}

	public String getNombreUnidadOperativa() {
		return nombreUnidadOperativa;
	}

	public void setNombreUnidadOperativa(String nombreUnidadOperativa) {
		this.nombreUnidadOperativa = nombreUnidadOperativa;
	}

	public String getLoginHeaderMultiOficinaB64() {
		return loginHeaderMultiOficinaB64;
	}

	public void setLoginHeaderMultiOficinaB64(String loginHeaderMultiOficinaB64) {
		this.loginHeaderMultiOficinaB64 = loginHeaderMultiOficinaB64;
	}
	
}
