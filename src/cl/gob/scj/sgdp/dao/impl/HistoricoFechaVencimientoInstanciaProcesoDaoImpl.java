package cl.gob.scj.sgdp.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.HistoricoFechaVencimientoInstanciaProcesoDao;
import cl.gob.scj.sgdp.model.HistoricoFechaVencimientoInstanciaProceso;

@Repository
public class HistoricoFechaVencimientoInstanciaProcesoDaoImpl implements HistoricoFechaVencimientoInstanciaProcesoDao {

	@Autowired
	private SessionFactory sessionFactory;// para que se comunique con el DAO

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void insertaHistoricoFechaVencimientoInstanciaProceso(
			HistoricoFechaVencimientoInstanciaProceso historicoFechaVencimientoInstanciaProceso) {
		getSession().save(historicoFechaVencimientoInstanciaProceso);

	}

}
