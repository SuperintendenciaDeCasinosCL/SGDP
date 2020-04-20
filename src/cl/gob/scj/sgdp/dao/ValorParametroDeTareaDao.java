package cl.gob.scj.sgdp.dao;

import cl.gob.scj.sgdp.model.ValorParametroDeTarea;

public interface ValorParametroDeTareaDao extends GenericDao<ValorParametroDeTarea> {
	
	ValorParametroDeTarea getValorParametroDeTareaPorIdParamIdInstanciaTarea(long idParamTarea, long idInstanciaDeTarea);


}
