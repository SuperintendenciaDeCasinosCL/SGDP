package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.ParametroDeTarea;

public interface ParametroDeTareaDao {
	
	List<ParametroDeTarea> getParametrosDeTareaPorIdTarea(long idTarea);

	List<ParametroDeTarea> getParametrosDeTareaPorIdInstanciaDeTarea(long idInstanciaDeTarea);
	
	 ParametroDeTarea getParametroDeTareaPorIdParamTarea(long idParamTarea);
	
}