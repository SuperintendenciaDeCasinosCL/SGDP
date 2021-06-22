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
	public List<HistoricoValorParametroDeTarea> getHistoricoValorParametroDeTareaPorIdInstanciaDeTareaOrigen(
			long idInstanciaDeTareaOrigen) {
		Query query = getSession().getNamedQuery("HistoricoValorParametroDeTarea.getHistoricoValorParametroDeTareaPorIdInstanciaDeTareaOrigen");	
		query.setLong("idInstanciaDeTareaOrigen", idInstanciaDeTareaOrigen);
		return query.list();
	}

}
