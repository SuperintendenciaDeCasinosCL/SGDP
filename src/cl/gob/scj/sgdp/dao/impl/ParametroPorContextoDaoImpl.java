package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.ParametroPorContextoDao;
import cl.gob.scj.sgdp.model.ParametroPorContexto;

@Repository
@Transactional(rollbackFor = Throwable.class)
public class ParametroPorContextoDaoImpl implements ParametroPorContextoDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {		
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ParametroPorContexto> getTodosLosParametrosPorContexto() {
		return getSession().createQuery("from ParametroPorContexto").list();
	}
	
	@Override
	public ParametroPorContexto getParametroPorContextoPorValorParametroChar(String valorParametroChar) {
		Query query = getSession().getNamedQuery("ParametroPorContexto.getParametroPorContextoPorValorParametroChar");	
		query.setString("valorParametroChar", valorParametroChar);			
		return (ParametroPorContexto) query.uniqueResult();
	}
	
	@Override
	public ParametroPorContexto getParametroPorContextoPorNombreParamValorContexto(String nombreParametro, String valorContexto) {
		Query query = getSession().getNamedQuery("ParametroPorContexto.getParametroPorContextoPorNombreParamValorContexto");	
		query.setString("nombreParametro", nombreParametro);	
		query.setString("valorContexto", valorContexto);
		return (ParametroPorContexto) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParametroPorContexto> getParametrosPorContextoPorNombreParam(String nombreParametro) {
		Query query = getSession().getNamedQuery("ParametroPorContexto.getParametrosPorContextoPorNombreParam");	
		query.setString("nombreParametro", nombreParametro);	
		return query.list();
	}

}
