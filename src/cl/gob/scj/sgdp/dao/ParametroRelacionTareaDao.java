package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.ParametroRelacionTarea;

public interface ParametroRelacionTareaDao extends GenericDao<ParametroRelacionTarea> {	
	
	List<ParametroRelacionTarea> getParamTareaPorIdProc(long idProceso);

}
