package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.HistoricoVinculacionExpDao;
import cl.gob.scj.sgdp.model.HistoricoVinculacionExp;

@Repository
public class HistoricoVinculacionExpDaoImpl extends GenericDaoImpl<HistoricoVinculacionExp> implements HistoricoVinculacionExpDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<HistoricoVinculacionExp> getHistoricoVinculacionExpPorIdInstProcIdInstProcAntecesor(
			long idInstanciaDeProceso, long idInstanciaDeProcesoAntecesor) {
		Query query = getSession().getNamedQuery("HistoricoVinculacionExp.getHistoricoVinculacionExpPorIdInstProcIdInstProcAntecesor");
		query.setLong("idInstanciaDeProceso", idInstanciaDeProceso);
		query.setLong("idInstanciaDeProcesoAntecesor", idInstanciaDeProcesoAntecesor);
		List<HistoricoVinculacionExp> resultado = (List<HistoricoVinculacionExp>)query.list();
		return resultado;
	}
	
}