package cl.gob.scj.sgdp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.HistoricoUsuariosAsignadosATareaDao;
import cl.gob.scj.sgdp.model.CometarioDeArchivo;
import cl.gob.scj.sgdp.model.HistoricoUsuariosAsignadosATarea;

@Repository
public class HistoricoUsuariosAsignadosATareaDaoImpl implements
		HistoricoUsuariosAsignadosATareaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void insertHistoricoUsuarioAsignadoATarea(
			HistoricoUsuariosAsignadosATarea historicoUsuariosAsignadosATarea,
			Usuario usuario) {
		getSession().save(historicoUsuariosAsignadosATarea);
	}
	
	public HistoricoUsuariosAsignadosATarea getHistoricoUsrAsigandoPorIdHistoricoInstTarea(long idHistoricoDeInstDeTarea, Usuario usuario) {
		Query query = getSession().getNamedQuery("HistoricoUsuariosAsignadosATarea.getHistoricoUsrAsigandoPorIdHistoricoInstTarea");	
		query.setLong("idHistoricoDeInstDeTarea", idHistoricoDeInstDeTarea);		
		return (HistoricoUsuariosAsignadosATarea) query.uniqueResult();
	}

}
