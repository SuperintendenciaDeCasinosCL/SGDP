package cl.gob.scj.sgdp.auth.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.dto.UnidadDTO;
import cl.gob.scj.sgdp.model.Permiso;
import cl.gob.scj.sgdp.model.UsuarioRol;

public class Usuario implements Serializable{

	private static final long serialVersionUID = 3229776787508456643L;
	private String idUsuario;
	private long idRolUsuarioSeleccionado;
	private RolDTO rolDTO;
	private UnidadDTO unidadDTO;
	private String alfTicket;
	private String idArchivoImagenQR;
	//private String clave;
	/*private String idUserAlfresco = "admin";
	private String passwordAlfresco = "scj.2016";*/
	private List<RolDTO> todosLosRoles = new ArrayList<RolDTO>();
	private boolean fueraDeOficina;	
	Map<String, String> permisos;
	private String nombreCompleto;
	
	private static final Logger log = Logger.getLogger(Usuario.class);

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}	
	
	public String getIdArchivoImagenQR() {
		return idArchivoImagenQR;
	}

	public void setIdArchivoImagenQR(String idArchivoImagenQR) {
		this.idArchivoImagenQR = idArchivoImagenQR;
	}

	public void setRolUnidadPermisosPorIdRolUsuarioSeleccionado(List<UsuarioRol> usuarioRoles) {
		permisos = new HashMap<String, String>();
		log.debug("idRolUsuarioSeleccionado: " + idRolUsuarioSeleccionado);
		for (UsuarioRol usuarioRol : usuarioRoles) {
			if (usuarioRol.getNombreCompleto()!=null && !usuarioRol.getNombreCompleto().isEmpty()) {
				this.setNombreCompleto(usuarioRol.getNombreCompleto());
			}
			todosLosRoles.add(new RolDTO(usuarioRol.getRol().getIdRol(), usuarioRol.getRol().getNombreRol()));
			if (usuarioRol.getRol().getIdRol()==idRolUsuarioSeleccionado) {
				rolDTO = new RolDTO(usuarioRol.getRol().getIdRol(), usuarioRol.getRol().getNombreRol());
				unidadDTO = new UnidadDTO(usuarioRol.getUnidad().getIdUnidad(), usuarioRol.getUnidad().getCodigoUnidad(), usuarioRol.getUnidad().getNombreCompletoUnidad());
				List<Permiso> permisosInt = usuarioRol.getRol().getPermisos();
				for (Permiso permiso : permisosInt) {
					log.debug("permiso.getNombrePermiso(): " + permiso.getNombrePermiso());
					permisos.put(permiso.getNombrePermiso(), permiso.getNombrePermiso());
				}
			}						
		}
	}

	public Map<String, String> getPermisos() {
		return permisos;
	}

	public void setPermisos(Map<String, String> permisos) {
		this.permisos = permisos;
	}

	public long getIdRolUsuarioSeleccionado() {
		return idRolUsuarioSeleccionado;
	}

	public void setIdRolUsuarioSeleccionado(long idRolUsuarioSeleccionado) {
		this.idRolUsuarioSeleccionado = idRolUsuarioSeleccionado;
	}
	
	public RolDTO getRolDTO() {
		return rolDTO;
	}

	public void setRolDTO(RolDTO rolDTO) {
		this.rolDTO = rolDTO;
	}
	
	public UnidadDTO getUnidadDTO() {
		return unidadDTO;
	}

	public void setUnidadDTO(UnidadDTO unidadDTO) {
		this.unidadDTO = unidadDTO;
	}
	
	public String getAlfTicket() {
		return alfTicket;
	}

	public void setAlfTicket(String alfTicket) {
		this.alfTicket = alfTicket;
	}
	
	/*public String getClave() {
		return clave;
	}
	
	public void setClave(String clave) {
		this.clave = clave;
	}	

	public String getIdUserAlfresco() {
		return idUserAlfresco;
	}

	public void setIdUserAlfresco(String idUserAlfresco) {
		this.idUserAlfresco = idUserAlfresco;
	}

	public String getPasswordAlfresco() {
		return passwordAlfresco;
	}

	public void setPasswordAlfresco(String passwordAlfresco) {
		this.passwordAlfresco = passwordAlfresco;
	}*/
	
	public List<RolDTO> getTodosLosRoles() {
		return todosLosRoles;
	}

	public void setTodosLosRoles(List<RolDTO> todosLosRoles) {
		this.todosLosRoles = todosLosRoles;
	}
	
	public boolean isFueraDeOficina() {
		return fueraDeOficina;
	}

	public void setFueraDeOficina(boolean fueraDeOficina) {
		this.fueraDeOficina = fueraDeOficina;
	}
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario
				+ ", idRolUsuarioSeleccionado=" + idRolUsuarioSeleccionado
				+ ", alfTicket=" + alfTicket + ", idArchivoImagenQR="
				+ idArchivoImagenQR 
				+ ", fueraDeOficina=" + fueraDeOficina
				+ ", nombreCompleto=" + nombreCompleto
				+ "]";
	}
	
}