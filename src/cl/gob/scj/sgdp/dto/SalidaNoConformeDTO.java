package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class SalidaNoConformeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean esSNC;
	private int idTipoParametro;
	private String nombreParametro;
	
	
	public SalidaNoConformeDTO() {
	}


	public SalidaNoConformeDTO(Boolean esSNC, int idTipoParametro, String nombreParametro) {
		this.esSNC = esSNC;
		this.idTipoParametro = idTipoParametro;
		this.nombreParametro = nombreParametro;
	}


	public Boolean getEsSNC() {
		return esSNC;
	}


	public void setEsSNC(Boolean esSNC) {
		this.esSNC = esSNC;
	}


	public int getIdTipoParametro() {
		return idTipoParametro;
	}


	public void setIdTipoParametro(int idTipoParametro) {
		this.idTipoParametro = idTipoParametro;
	}


	public String getNombreParametro() {
		return nombreParametro;
	}


	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
