package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.Tarea;

public interface TareaDao {

	List<Tarea> getTareasPorVigenciaPorIdProceso(long idProceso, boolean vigente);
	
	Tarea getBuscaDocumentoOficialEnTarea(long idInstanciaDeTarea);
	
	Tarea getTareaPorIdTarea(long idTarea);
	
	List<Tarea> getTareasPorIdProceso(long idProceso);
	
	List<Tarea> getTareasPorCodigoProceso(String codigoProceso);
	
	List<Tarea> getSegundasTareasPorIdProceso(long idProceso);
	
}
