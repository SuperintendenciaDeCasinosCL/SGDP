package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class EntidadDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idEntidad;
	private String nombreEntidad;
	private OrganismoDTO organismo;
	
	public Integer getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(Integer idEntidad) {
		this.idEntidad = idEntidad;
	}
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	public OrganismoDTO getOrganismo() {
		return organismo;
	}
	public void setOrganismo(OrganismoDTO organismo) {
		this.organismo = organismo;
	}
	@Override
	public String toString() {
		return "EntidadDTO [idEntidad=" + idEntidad + ", nombreEntidad=" + nombreEntidad + ", organismo=" + organismo
				+ "]";
	}
	
	
	

}
