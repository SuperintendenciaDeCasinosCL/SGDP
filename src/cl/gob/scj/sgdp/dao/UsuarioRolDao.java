package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.LogFueraDeOficina;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.UsuarioRol;
import cl.gob.scj.sgdp.model.VinculacionExp;

public interface UsuarioRolDao extends GenericDao<UsuarioRol> {

	List<UsuarioRol> getUsuarioRolesPorIdUsuario(String idUsuario, boolean activo);
	
	List<UsuarioRol> getUsuarioRolesPorIdRolIdUnidad(long idRol, long idUnidad, boolean activo);
	
	List<UsuarioRol> getUsuariosRolesPorIdRol(long idRol, boolean activo);
	
	List<UsuarioRol> getUsuariosRolesPorIdUsuarioLike(String idUsuario, boolean activo);
	
	List<UsuarioRol> getUsuariosRolesPorTareaIdRol(Tarea tarea, boolean activo, int ordenTareaRol, long idRol);
	
	List<UsuarioRol> getUsuarioRolesPorIdUsuarioTarea(String idUsuario, Tarea tarea, boolean activo);
	
	List<UsuarioRol> getUsuariosRolesPosiblesPorIdInstanciaDeTarea(long idInstanciaDeTarea);
	
	List<UsuarioRol> getTodosLosUsuariosAsignadosPorIdRol(Long rolId);
	
	List<UsuarioRol> getTodosLosUsuariosActivos();
	
	UsuarioRol getUsuarioRolPorIdUsuarioIdRol(String idUsuario, long idRol);
	
	void insertLogFueraDeOficina(LogFueraDeOficina logFueraDeOficina);
	
	List<String> getTodosLosIdUsuariosPorVigencia(boolean vigente);
	
	List<String> getTodosLosIdUsuariosPorVigenciaOrdenadoPorUnidadYNombre(boolean vigente);
	
	List<String> getTodosLosIdUsuariosPorNombreRol(String nombreRol);
	
	List<UsuarioRol> getTodosLosUsuarios();
	
	UsuarioRol getUsuarioRolPorIdUsuarioRol(long idUsuarioRol);
	
}
