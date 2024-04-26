package cl.gob.scj.sgdp.dao;

import java.util.List;

import org.hibernate.Session;

import cl.gob.scj.sgdp.model.Proceso;
import cl.gob.scj.sgdp.model.Tarea;

public interface ProcesoDao {
	
	List<Proceso> getProcesosPorIdUnidadYVigencia(long idUnidad, boolean vigente);
	
	List<Proceso> getProcesosPorSuperProceso(long idSuperProceso);
	
	List<Proceso> getProcesosPorIdMacroProceso(long idMacroProceso, boolean vigente);
	
	Proceso getProcesoPorIdProceso(long idProceso);
	
	Long insertaProceso(Proceso proceso);

	List<Tarea> getTareaPorIdSubProceso(long idSubProceso, boolean vigente);
	
	List<Proceso> getBuscarTodosProcesosVigente(boolean vigente);

	List<String> getBuscarTodosLosNombresdeLosProcesoVigente(boolean vigente);
	
	Proceso getProcesoVigentePorCodigoProceso(String codigoProceso);
	
	List<Proceso> getTodosProcesos();
	
	List<String> getTodosLosNombresDeProcesos();
	
	List<Proceso> buscarTodosProcesoVigenteOrderPorCod(boolean vigente);

	Long insertaProceso(Proceso proceso, Session session);

	Integer deshabilitaProceso(Long idProcesoNuevo, String codigoProceso, Session session);

	List<Proceso> buscarTodosProcesoVigente(boolean vigente);
	
}
