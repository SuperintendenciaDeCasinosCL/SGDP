package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

public class ConfidencialidadDocumentoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private List<String> usuarios;
	private List<RolDTO> roles;
	private List<ConfidencialidadDocumentoUsuarioDTO> usuariosAsignados;
	private List<ConfidencialidadDocumentoRolDTO> rolesAsignados;
		
	public ConfidencialidadDocumentoDTO() {

	}

	public ConfidencialidadDocumentoDTO(String id, List<String> usuarios, List<RolDTO> roles,
			List<ConfidencialidadDocumentoUsuarioDTO> usuariosAsignados,
			List<ConfidencialidadDocumentoRolDTO> rolesAsignados) {
		super();
		this.id = id;
		this.usuarios = usuarios;
		this.roles = roles;
		this.usuariosAsignados = usuariosAsignados;
		this.rolesAsignados = rolesAsignados;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<String> usuarios) {
		this.usuarios = usuarios;
	}

	public List<RolDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RolDTO> roles) {
		this.roles = roles;
	}

	public List<ConfidencialidadDocumentoUsuarioDTO> getUsuariosAsignados() {
		return usuariosAsignados;
	}

	public void setUsuariosAsignados(List<ConfidencialidadDocumentoUsuarioDTO> usuariosAsignados) {
		this.usuariosAsignados = usuariosAsignados;
	}

	public List<ConfidencialidadDocumentoRolDTO> getRolesAsignados() {
		return rolesAsignados;
	}

	public void setRolesAsignados(List<ConfidencialidadDocumentoRolDTO> rolesAsignados) {
		this.rolesAsignados = rolesAsignados;
	}
	
	

}
