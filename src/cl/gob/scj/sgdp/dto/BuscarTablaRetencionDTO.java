package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class BuscarTablaRetencionDTO implements Serializable {

	private String codigoInstitucion;

	public BuscarTablaRetencionDTO() {
		super();
	}

	public String getCodigoInstitucion() {
		return codigoInstitucion;
	}

	public void setCodigoInstitucion(String codigoInstitucion) {
		this.codigoInstitucion = codigoInstitucion;
	}

	@Override
	public String toString() {
		return "BuscarTablaRetencionDTO [codigoInstitucion=" + codigoInstitucion + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4322419256162928916L;

}
