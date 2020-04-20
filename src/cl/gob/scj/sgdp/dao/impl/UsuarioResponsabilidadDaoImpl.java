package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.UsuarioResponsabilidadDao;
import cl.gob.scj.sgdp.model.UsuarioResponsabilidad;

@Repository
public class UsuarioResponsabilidadDaoImpl implements UsuarioResponsabilidadDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioResponsabilidad> getUsuariosResponsabilidadesPorIdInstanciaDeTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("UsuarioResponsabilidad.getUsuariosResponsabilidadesPorIdInstanciaDeTarea");
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);		
		return query.list();
	}
	
	@Override
	public UsuarioResponsabilidad getPrimerUsuarioResponsabilidadPorIdInstanciaDeTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("UsuarioResponsabilidad.getPrimerUsuarioResponsabilidadPorIdInstanciaDeTarea");
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		query.setFirstResult(0);
        query.setMaxResults(1);
		return (UsuarioResponsabilidad) query.uniqueResult();
	}
		
	@Override
	public UsuarioResponsabilidad getUsuarioResponsabilidadPorIdUsuarioIdTarea(String idUsuario, long idTarea) {
		Query query = getSession().getNamedQuery("UsuarioResponsabilidad.getUsuarioResponsabilidadPorIdUsuarioIdTarea");
		query.setString("idUsuario", idUsuario);
		query.setLong("idTarea", idTarea);		
		return (UsuarioResponsabilidad) query.uniqueResult();
	}
	
	@Override
	public UsuarioResponsabilidad getUsuarioResponsabilidadPorIdTarea(long idTarea) {
		Query query = getSession().getNamedQuery("UsuarioResponsabilidad.getUsuarioResponsabilidadPorIdTarea");
		query.setLong("idTarea", idTarea);
		query.setFirstResult(0);
        query.setMaxResults(1);
		return (UsuarioResponsabilidad) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioResponsabilidad> getTodosLosUsuariosResponsabilidadPorIdTarea(long idTarea) {
		Query query = getSession().getNamedQuery("UsuarioResponsabilidad.getTodosLosUsuariosResponsabilidadPorIdTarea");
		query.setLong("idTarea", idTarea);		
		return query.list();
	}

	@Override
	public List<UsuarioResponsabilidad> getUsuariosResponsabilidadesDePrimeraTareaExcluyePorIdProceso(long idProceso) {
		Query query = getSession().getNamedQuery("UsuarioResponsabilidad.getUsuariosResponsabilidadesDePrimeraTareaExcluyePorIdProceso");
		query.setLong("idProceso", idProceso);		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioResponsabilidad> getTodosLosUsuariosResponsabilidadExcluyePorIdTarea(long idTarea) {
		Query query = getSession().getNamedQuery("UsuarioResponsabilidad.getTodosLosUsuariosResponsabilidadExcluyePorIdTarea");
		query.setLong("idTarea", idTarea);		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioResponsabilidad> getUsuariosFueraOficinaResponsabilidadesPorIdInstanciaDeTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("UsuarioResponsabilidad.getUsuariosFueraOficinaResponsabilidadesPorIdInstanciaDeTarea");
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);		
		return query.list();
	}
	
}
