package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.dto.UsuarioRolDTO;
import cl.gob.scj.sgdp.dto.UsuariosPorRolDTO;
import cl.gob.scj.sgdp.model.UsuarioRol;

@Service
public interface UsuarioRolService {
	
	List<UsuarioRol> getUsuarioRolesPorIdUsuario(String idUsuario);
	
	List<UsuarioRolDTO> getTodosLosUsuariosAsignadosPorIdRol(Long rolId);
	
	List<RolDTO> getRolesUsuarioPorIdUsuario(String idUsuario, List<RolDTO> roles);
	
	void cargaUsuariosRolesPosiblesPorIdInstanciaDeTarea(long idInstanciaDeTarea, List<String> posiblesUsuarios);
	
	void cargaUsuarioRolPorIdUsuarioIdRol(UsuarioRolDTO usuarioRolDTO);
	
	void actualizaFueraDeOficina(UsuarioRolDTO usuarioRolDTO);
	
	List<String> getTodosLosIdUsuariosPorVigencia(boolean vigente);
	
	List<UsuarioRolDTO> getAll();
	
	UsuarioRolDTO creaUsuarioRol(UsuarioRolDTO usuarioRolDTO);
	
	UsuarioRolDTO getUsuarioRolDTOPorIdUsuarioRol(long idUsuarioRol);
	
	UsuarioRolDTO actualizaUsuario(UsuarioRolDTO usuarioRolDTO);
	
	List<UsuarioRolDTO> getTodosLosUsuariosActivos();
	
	public UsuariosPorRolDTO getUsuariosPorRol(Long idRol);
	
}

