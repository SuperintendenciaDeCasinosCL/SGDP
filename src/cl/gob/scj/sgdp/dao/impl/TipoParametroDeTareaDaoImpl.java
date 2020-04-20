package cl.gob.scj.sgdp.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.TipoParametroDeTareaDao;
import cl.gob.scj.sgdp.model.TipoParametroDeTarea;
import cl.gob.scj.sgdp.model.ValorParametroDeTarea;

@Repository
public class TipoParametroDeTareaDaoImpl extends GenericDaoImpl<TipoParametroDeTarea> implements TipoParametroDeTareaDao {

	@Override
	public TipoParametroDeTarea getTipoParametroDeTareaPorNombreTipoParametroDeTarea(
			String nombreTipoParametroDeTarea) {
		Query query = getSession().getNamedQuery("TipoParametroDeTarea.getTipoParametroDeTareaPorNombreTipoParametroDeTarea");
		query.setString("nombreTipoParametroDeTarea", nombreTipoParametroDeTarea);
		return (TipoParametroDeTarea) query.uniqueResult();
	}
	
}
