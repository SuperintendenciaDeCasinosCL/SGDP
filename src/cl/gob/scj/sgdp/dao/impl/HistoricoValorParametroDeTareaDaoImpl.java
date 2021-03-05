package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.HistoricoValorParametroDeTareaDao;
import cl.gob.scj.sgdp.model.HistoricoValorParametroDeTarea;

@Repository
public class HistoricoValorParametroDeTareaDaoImpl extends GenericDaoImpl<HistoricoValorParametroDeTarea> implements HistoricoValorParametroDeTareaDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoricoValorParametroDeTarea> getHistoricoValorParametroDeTareaPorIdInstanciaDeTarea(
			long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("HistoricoValorParametroDeTarea.getHistoricoValorParametroDeTareaPorIdInstanciaDeTarea");	
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		return query.list();
	}

	@Override
	public List<HistoricoValorParametroDeTarea> getHistoricoValorParametroDeTareaPorIdExpediente(String idExpediente) {
		Query query = getSession().getNamedQuery("HistoricoValorParametroDeTarea.getHistoricoValorParametroDeTareaPorIdExpediente");	
		query.setString("idExpediente", idExpediente);
		return query.list();
	}
	
	@Override
	public List<HistoricoValorParametroDeTarea> getHistoricoValorParametroDeTareaPorIdHistoricoInstanciaDeTarea(long idHistoricoDeInstDeTarea) {
		Query query = getSession().getNamedQuery("HistoricoValorParametroDeTarea.getHistoricoValorParametroDeTareaPorIdHistoricoInstanciaDeTarea");	
		query.setLong("idHistoricoDeInstDeTarea", idHistoricoDeInstDeTarea);
		return query.list();
	}

}
