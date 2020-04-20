package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.dto.UsuarioRolDTO;
import cl.gob.scj.sgdp.model.UsuarioRol;

@Service
public interface UsuarioRolService {
	
	List<UsuarioRol> getUsuarioRolesPorIdUsuario(String idUsuario);
	
	List<RolDTO> getRolesUsuarioPorIdUsuario(String idUsuario, List<RolDTO> roles);
	
	void cargaUsuariosRolesPosiblesPorIdInstanciaDeTarea(long idInstanciaDeTarea, List<String> posiblesUsuarios);
	
	void cargaUsuarioRolPorIdUsuarioIdRol(UsuarioRolDTO usuarioRolDTO);
	
	void actualizaFueraDeOficina(UsuarioRolDTO usuarioRolDTO);
	
	public List<String> getTodosLosIdUsuariosPorVigencia(boolean vigente);
	
}
