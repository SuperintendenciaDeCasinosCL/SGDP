package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.UsuarioAsignado;

public interface UsuarioAsignadoDao {

	public void insertUsuarioAsignado(UsuarioAsignado usuarioAsignado, Usuario usuario);
	
	public UsuarioAsignado getUsuarioAsignadoPorInstanciaDeTareaIdUsuario(InstanciaDeTarea instanciaDeTarea, String idUsuario);
	
	public void deleteUsuarioAsignado(UsuarioAsignado usuarioAsignado);
	
	public List<UsuarioAsignado> getUsuariosAsignadosPorInstanciaDeTarea(InstanciaDeTarea instanciaDeTarea) ;
	
}
