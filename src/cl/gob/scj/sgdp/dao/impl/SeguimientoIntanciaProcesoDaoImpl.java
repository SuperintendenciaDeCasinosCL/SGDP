package cl.gob.scj.sgdp.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.SeguimientoIntanciaProcesoDao;
import cl.gob.scj.sgdp.model.HistoricoSeguimientoIntanciaProceso;
import cl.gob.scj.sgdp.model.SeguimientoIntanciaProceso;

@Repository
public class SeguimientoIntanciaProcesoDaoImpl implements SeguimientoIntanciaProcesoDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public long buscaSiTieneSeguimiento(String idUsuario,
			long idInstanciaDeProceso) {
		
		// TODO Auto-generated method stub
		long cantidadSeguidores = (long) getSession()
				.createQuery(
						  " select count(*) from SeguimientoIntanciaProceso si "
						+ " where si.id.idUsuario = :idUsuario "
						+ "and si.id.instanciaDeProceso.idInstanciaDeProceso = :idInstanciaDeProceso")		
		.setString("idUsuario", idUsuario)
		.setLong("idInstanciaDeProceso",idInstanciaDeProceso)
		.uniqueResult();
				
		return cantidadSeguidores;
		
	}

	@Override
	public void guardarSeguimiento(SeguimientoIntanciaProceso seguimientoIntanciaProceso) {		
		getSession().save(seguimientoIntanciaProceso);
	}

	@Override
	public void guardarSeguimientoHistorico(HistoricoSeguimientoIntanciaProceso historicoSeguimientoIntanciaProceso) {		
		getSession().save(historicoSeguimientoIntanciaProceso);		
	}

	@Override
	public void borrarSeguimiento(SeguimientoIntanciaProceso seguimientoIntanciaProceso) {		
		getSession().delete(seguimientoIntanciaProceso);		
	}		
	
}
