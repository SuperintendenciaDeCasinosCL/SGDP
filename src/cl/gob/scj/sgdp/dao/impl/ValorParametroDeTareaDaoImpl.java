package cl.gob.scj.sgdp.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ValorParametroDeTareaDao;
import cl.gob.scj.sgdp.model.ValorParametroDeTarea;

@Repository
public class ValorParametroDeTareaDaoImpl extends GenericDaoImpl<ValorParametroDeTarea> implements ValorParametroDeTareaDao {

	@Override
	public ValorParametroDeTarea getValorParametroDeTareaPorIdParamIdInstanciaTarea(long idParamTarea,
			long idInstanciaDeTarea) {
		Query query = getSession().getNamedQuery("ValorParametroDeTarea.getValorParametroDeTareaPorIdParamIdInstanciaTarea");
		query.setLong("idParamTarea", idParamTarea);
		query.setLong("idInstanciaDeTarea", idInstanciaDeTarea);
		return (ValorParametroDeTarea) query.uniqueResult();
	}
	
}
