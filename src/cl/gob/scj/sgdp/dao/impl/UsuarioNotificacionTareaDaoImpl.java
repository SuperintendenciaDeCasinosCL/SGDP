package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.UsuarioNotificacionTareaDao;
import cl.gob.scj.sgdp.model.UsuarioNotificacionTarea;

@Repository
public class UsuarioNotificacionTareaDaoImpl implements UsuarioNotificacionTareaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void insertUsuarioNotificacionTarea(UsuarioNotificacionTarea usuarioNotificacionTarea) {
		getSession().save(usuarioNotificacionTarea);
	}
	
	
	public void eliminarUsuarioNotificacionTarea(UsuarioNotificacionTarea usuarioNotificacionTarea) {
		getSession().delete(usuarioNotificacionTarea);	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioNotificacionTarea> getUsuariosNotificacionTareaPorIdTarea(long idTarea) {
		Query query = getSession().getNamedQuery("UsuarioNotificacionTarea.getUsuariosNotificacionTareaPorIdTarea");
		query.setLong("idTarea", idTarea);	
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public UsuarioNotificacionTarea getUsuariosNotificacionTareaPorIdTareaYUsuario(long idTarea, String idUsuario) {
		Query query = getSession().getNamedQuery("UsuarioNotificacionTarea.getUsuariosNotificacionTareaPorIdTareaYUsuario");
		query.setLong("idTarea", idTarea);	
		query.setString("idUsuario", idUsuario);
		return (UsuarioNotificacionTarea) query.uniqueResult();
	}
	
}
