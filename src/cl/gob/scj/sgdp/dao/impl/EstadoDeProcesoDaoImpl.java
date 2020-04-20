package cl.gob.scj.sgdp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.EstadoDeProcesoDao;
import cl.gob.scj.sgdp.model.AccionesHistInstDeTarea;
import cl.gob.scj.sgdp.model.EstadoDeProceso;

@Repository
public class EstadoDeProcesoDaoImpl implements EstadoDeProcesoDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public EstadoDeProceso getEstadoDeProcesoPorId(long idEstadoDeProceso) {	
		return (EstadoDeProceso) getSession().get(EstadoDeProceso.class, idEstadoDeProceso);
	}
	
	@Override
	public EstadoDeProceso getEstadoDeProcesoPorCodigo(int codigoEstadoDeProceso) {
		Query query = getSession().createQuery(
				"from EstadoDeProceso e where e.codigoEstadoDeProceso =:codigoEstadoDeProceso ");
		query.setLong("codigoEstadoDeProceso", codigoEstadoDeProceso);
		return (EstadoDeProceso) query.uniqueResult();	
	}
	

}
