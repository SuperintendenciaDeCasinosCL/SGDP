package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class UnidadDTO implements Serializable {

	private Long idUnidad;
	private String codigoUnidad;
	private String nombreCompletoUnidad;
	
	public UnidadDTO(Long idUnidad, String codigoUnidad,
			String nombreCompletoUnidad) {
		super();
		this.idUnidad = idUnidad;
		this.codigoUnidad = codigoUnidad;
		this.nombreCompletoUnidad = nombreCompletoUnidad;
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
	
	
	
}
