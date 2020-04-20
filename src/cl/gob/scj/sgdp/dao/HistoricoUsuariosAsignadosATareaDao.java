package cl.gob.scj.sgdp.dao;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.model.HistoricoUsuariosAsignadosATarea;

public interface HistoricoUsuariosAsignadosATareaDao {

	void insertHistoricoUsuarioAsignadoATarea(HistoricoUsuariosAsignadosATarea historicoUsuariosAsignadosATarea, Usuario usuario);
	
	HistoricoUsuariosAsignadosATarea getHistoricoUsrAsigandoPorIdHistoricoInstTarea(long idHistoricoDeInstDeTarea, Usuario usuario);
	
}
