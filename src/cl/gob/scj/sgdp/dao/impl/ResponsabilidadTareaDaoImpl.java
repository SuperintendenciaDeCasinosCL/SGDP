package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ResponsabilidadTareaDao;
import cl.gob.scj.sgdp.model.ResponsabilidadTarea;

@Repository
public class ResponsabilidadTareaDaoImpl implements ResponsabilidadTareaDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ResponsabilidadTarea> getResponsabilidadesTareasPorIdTarea(long idTarea) {
		Query query = getSession().getNamedQuery("ResponsabilidadTarea.getResponsabilidadesTareasPorIdTarea");	
		query.setLong("idTarea", idTarea);
		return query.list();
	}

}
