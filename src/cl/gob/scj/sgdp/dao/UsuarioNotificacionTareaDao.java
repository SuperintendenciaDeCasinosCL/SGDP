package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.UsuarioNotificacionTarea;

public interface UsuarioNotificacionTareaDao {
	
	void insertUsuarioNotificacionTarea(UsuarioNotificacionTarea usuarioNotificacionTarea);

	List<UsuarioNotificacionTarea> getUsuariosNotificacionTareaPorIdTarea(long idTarea);
	
	void eliminarUsuarioNotificacionTarea(UsuarioNotificacionTarea usuarioNotificacionTarea);
	
	UsuarioNotificacionTarea getUsuariosNotificacionTareaPorIdTareaYUsuario(long idTarea, String idUsuario);
}
