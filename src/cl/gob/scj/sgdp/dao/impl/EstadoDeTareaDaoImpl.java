package cl.gob.scj.sgdp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.EstadoDeTareaDao;
import cl.gob.scj.sgdp.model.EstadoDeTarea;

@Repository
public class EstadoDeTareaDaoImpl implements EstadoDeTareaDao {

	@Autowired
	private SessionFactory sessionFactory;// para que se comunique con el DAO

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public EstadoDeTarea getEstadoDeTareaPorCodigo(int codigoEstadoDeTarea) {
		Query query = getSession().createQuery(
				"from EstadoDeTarea t where t.codigoEstadoDeTarea =:codigoEstadoDeTarea ");
		query.setLong("codigoEstadoDeTarea", codigoEstadoDeTarea);
		return (EstadoDeTarea) query.uniqueResult();
	}

}
