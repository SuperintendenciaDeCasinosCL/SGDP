package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class UsuarioEntidadDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idUsuario;
	private String runUsuario;	
	private String nombreUsuario;
	private String emailUsuario;
	private String cargoUsuario;
	private String idEntidad;
	private String nombreEntidad;
	private String idOrganismo;
	private String nombreOrganismo;
	
	
	
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getRunUsuario() {
		return runUsuario;
	}
	public void setRunUsuario(String runUsuario) {
		this.runUsuario = runUsuario;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getEmailUsuario() {
		return emailUsuario;
	}
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
	public String getCargoUsuario() {
		return cargoUsuario;
	}
	public void setCargoUsuario(String cargoUsuario) {
		this.cargoUsuario = cargoUsuario;
	}
	public String getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	public String getIdOrganismo() {
		return idOrganismo;
	}
	public void setIdOrganismo(String idOrganismo) {
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
		return "UsuarioEntidadDTO [idUsuario=" + idUsuario + ", runUsuario=" + runUsuario + ", nombreUsuario="
				+ nombreUsuario + ", emailUsuario=" + emailUsuario + ", cargoUsuario=" + cargoUsuario + ", idEntidad="
				+ idEntidad + ", nombreEntidad=" + nombreEntidad + ", idOrganismo=" + idOrganismo + ", nombreOrganismo="
				+ nombreOrganismo + "]";
	}
	
	
	
	

}
