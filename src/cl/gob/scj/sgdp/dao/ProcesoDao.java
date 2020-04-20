package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.Proceso;
import cl.gob.scj.sgdp.model.Tarea;

public interface ProcesoDao {
	
	List<Proceso> getProcesosPorIdUnidadYVigencia(long idUnidad, boolean vigente);
	
	List<Proceso> getProcesosPorIdMacroProceso(long idMacroProceso, boolean vigente);
	
	Proceso getProcesoPorIdProceso(long idProceso);
	
	void insertaProceso(Proceso proceso);

	List<Tarea> getTareaPorIdSubProceso(long idSubProceso, boolean vigente);
	
	List<Proceso> getBuscarTodosProcesosVigente(boolean vigente);

	List<String> getBuscarTodosLosNombresdeLosProcesoVigente(boolean vigente);
	
	Proceso getProcesoVigentePorCodigoProceso(String codigoProceso);
	
	List<Proceso> getTodosProcesos();
	
	List<String> getTodosLosNombresDeProcesos();
	
	List<Proceso> buscarTodosProcesoVigenteOrderPorCod(boolean vigente);
	
}
