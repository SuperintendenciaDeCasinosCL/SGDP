package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class OrganismoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idOrganismo;
	private String nombreOrganismo;
	public Integer getIdOrganismo() {
		return idOrganismo;
	}
	public void setIdOrganismo(Integer idOrganismo) {
		this.idOrganismo = idOrganismo;
	}
	public String getNombreOrganismo() {
		return nombreOrganismo;
	}
	public void setNombreOrganismo(String nombreOrganismo) {
		this.nombreOrganismo = nombreOrganismo;
	}
	@Override
	public String toString() {
		return "Organismo [idOrganismo=" + idOrganismo + ", nombreOrganismo=" + nombreOrganismo + "]";
	}
	
	
	
}
