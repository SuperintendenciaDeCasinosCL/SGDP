package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.ParametroRelacionTareaDao;
import cl.gob.scj.sgdp.model.ParametroRelacionTarea;

@Repository
@Transactional(rollbackFor = Throwable.class)
public class ParametroRelacionTareaDaoImpl extends GenericDaoImpl<ParametroRelacionTarea> implements ParametroRelacionTareaDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {		
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<ParametroRelacionTarea> getParamTareaPorIdProc(long idProceso) {
		Query query = getSession().getNamedQuery("ParametroRelacionTarea.getParamTareaPorIdProc");		
		query.setLong("idProceso", idProceso);
		return query.list();	
	}

}
