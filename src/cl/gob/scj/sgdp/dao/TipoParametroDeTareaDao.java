package cl.gob.scj.sgdp.dao;

import cl.gob.scj.sgdp.model.TipoParametroDeTarea;

public interface TipoParametroDeTareaDao extends GenericDao<TipoParametroDeTarea> {
	
	TipoParametroDeTarea getTipoParametroDeTareaPorNombreTipoParametroDeTarea(String nombreTipoParametroDeTarea);

}
