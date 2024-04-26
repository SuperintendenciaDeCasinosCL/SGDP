package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.UsuarioResponsabilidad;

public interface UsuarioResponsabilidadDao {
	
	List<UsuarioResponsabilidad> getUsuariosResponsabilidadesPorIdInstanciaDeTarea(long idInstanciaDeTarea);
	
	List<UsuarioResponsabilidad> getUsuariosResponsabilidadesPorIdInstanciaDeTareaUnidad(long idUnidadOperativa, long idInstanciaDeTarea);
	List<UsuarioResponsabilidad> getUsuariosResponsabilidadesPorIdInstanciaDeTareaUnidadOfi(long idUnidadOperativa, long idInstanciaDeTarea);
	
	UsuarioResponsabilidad getPrimerUsuarioResponsabilidadPorIdInstanciaDeTarea(long idInstanciaDeTarea);
	
	UsuarioResponsabilidad getUsuarioResponsabilidadPorIdUsuarioIdTarea(String idUsuario, long idTarea);
	
	UsuarioResponsabilidad getUsuarioResponsabilidadPorIdTarea(long idTarea);
	
	List<UsuarioResponsabilidad> getTodosLosUsuariosResponsabilidadPorIdTarea(long idTarea);
	
	List<UsuarioResponsabilidad> getUsuariosResponsabilidadesDePrimeraTareaExcluyePorIdProceso(long idProceso);
	
	List<UsuarioResponsabilidad> getTodosLosUsuariosResponsabilidadExcluyePorIdTarea(long idTarea);
	
	List<UsuarioResponsabilidad> getUsuariosFueraOficinaResponsabilidadesPorIdInstanciaDeTarea(long idInstanciaDeTarea);

	Integer eliminarUsuarioResponsabilidadPorIdresponsabilidad(long idResponsabilidad);
	
	void guardar(UsuarioResponsabilidad ur);

}
