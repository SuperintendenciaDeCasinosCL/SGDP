package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.MacroProcesoDao;
import cl.gob.scj.sgdp.model.MacroProceso;
import cl.gob.scj.sgdp.model.Proceso;

@Repository
public class MacroProcesoDaoImpl implements MacroProcesoDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MacroProceso> getTodosLosMacroProcesos() {
		//return getSession().createQuery("from MacroProceso").list();
		Query query = getSession().getNamedQuery("MacroProceso.getMacroProcesosPorVigentes");				
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MacroProceso> getMacroProcesosPorIdUnidad(long idUnidad) {
		Query query = getSession().getNamedQuery("MacroProceso.getMacroProcesosPorIdUnidad");		
		query.setLong("idUnidad", idUnidad);
		return query.list();	
	}
	
}
