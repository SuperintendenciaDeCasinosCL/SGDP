package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ParametroDeTareaDao;
import cl.gob.scj.sgdp.model.ParametroDeTarea;

@Repository
public class ParametroDeTareaDaoImpl implements ParametroDeTareaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {		
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<ParametroDeTarea> getParametrosDeTareaPorIdTarea(long idTarea) {
		Query query = getSession().getNamedQuery("ParametroDeTarea.getParametrosDeTareaPorIdTarea");	
		query.setLong("idTarea", idTarea);
		return query.list();		
	}
	
	@Override
	public List<ParametroDeTarea> getParametrosDeTareaPorIdInstanciaDeTarea(long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("ParametroDeTarea.getParametrosDeTareaPorIdInstanciaDeTarea");	
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		return query.list();		
	}
	
	@Override
	public ParametroDeTarea getParametroDeTareaPorIdParamTarea(long idParamTarea) {
		Query query = getSession().getNamedQuery("ParametroDeTarea.getParametroDeTareaPorIdParamTarea");	
		query.setLong("idParamTarea", idParamTarea);
		return (ParametroDeTarea) query.uniqueResult();		
	}

}
