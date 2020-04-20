package cl.gob.scj.sgdp.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.FechaFeriadoDao;
import cl.gob.scj.sgdp.model.FechaFeriado;

@Repository
public class FechaFeriadoDaoImpl implements FechaFeriadoDao {

	@Autowired
	private SessionFactory sessionFactory;// para que se comunique con el DAO

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public FechaFeriado getFechaFeriadoPorFechaFeriado(String fechaFeriado) {	
		Query query = getSession().createQuery( "from FechaFeriado f where f.fechaFeriado =:fechaFeriado ");
		query.setString("fechaFeriado", fechaFeriado);
		return (FechaFeriado) query.uniqueResult();
	}
	
}
