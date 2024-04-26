package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

public class RolDTO extends RespuestaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idRol;
	private String nombreRol;
	private Long idUnidad;
	private String nombreUnidad;
	private List<PermisoDTO> permisosDTO;

	public RolDTO() {

	}

	public RolDTO(Long idRol, String nombreRol, Long idUnidad, String nombreUnidad) {
		super();
		this.idRol = idRol;
		this.nombreRol = nombreRol;
		this.idUnidad = idUnidad;
		this.nombreUnidad = nombreUnidad;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public Long getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Long idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getNombreUnidad() {
		return nombreUnidad;
	}

	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}

	public List<PermisoDTO> getPermisosDTO() {
		return permisosDTO;
	}
	
	public void setPermisosDTO(List<PermisoDTO> permisosDTO) {
		this.permisosDTO = permisosDTO;
	}
	
	@Override
	public String toString() {
		return "RolDTO [idRol=" + idRol + ", nombreRol=" + nombreRol + ", idUnidad=" + idUnidad + ", nombreUnidad="
				+ nombreUnidad + "]";
	}

}
