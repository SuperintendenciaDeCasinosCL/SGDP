package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.UsuarioAsignadoDao;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.UsuarioAsignado;

@Repository
public class UsuarioAsignadoDaoImpl implements UsuarioAsignadoDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void insertUsuarioAsignado(UsuarioAsignado usuarioAsignado, Usuario usuario) {
		getSession().save(usuarioAsignado);	
	}
	
	@Override
	public UsuarioAsignado getUsuarioAsignadoPorInstanciaDeTareaIdUsuario(InstanciaDeTarea instanciaDeTarea, String idUsuario){
		
		Query query = getSession().createQuery(
				"from UsuarioAsignado u where u.id.instanciaDeTarea.idInstanciaDeTarea =:idInstanciaDeTarea and u.id.idUsuario =:idUsuario");
		query.setLong("idInstanciaDeTarea", instanciaDeTarea.getIdInstanciaDeTarea());
		query.setString("idUsuario", idUsuario);
		return (UsuarioAsignado) query.uniqueResult();
	}
	
	@Override
	public void deleteUsuarioAsignado(UsuarioAsignado usuarioAsignado){
		getSession().delete(usuarioAsignado);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioAsignado> getUsuariosAsignadosPorInstanciaDeTarea(InstanciaDeTarea instanciaDeTarea) {
		Query query = getSession().createQuery(
				"from UsuarioAsignado u where u.id.instanciaDeTarea.idInstanciaDeTarea =:idInstanciaDeTarea");
		query.setLong("idInstanciaDeTarea", instanciaDeTarea.getIdInstanciaDeTarea());
		return query.list();
	}

}
