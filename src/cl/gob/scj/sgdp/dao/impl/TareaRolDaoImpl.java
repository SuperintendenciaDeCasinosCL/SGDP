package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.TareaRolDao;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.TareaRol;

@Repository
public class TareaRolDaoImpl implements TareaRolDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TareaRol> getTareasRolesPorTarea(Tarea tarea) {
		Query query = getSession().createQuery(
				"from TareaRol tr"
				+ " where tr.id.tarea.idTarea =:idTarea ");
		query.setLong("idTarea", tarea.getIdTarea());
		return query.list();		
	}
	
}
