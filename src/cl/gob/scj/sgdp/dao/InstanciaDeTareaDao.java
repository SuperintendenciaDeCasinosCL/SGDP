package cl.gob.scj.sgdp.dao;

import java.util.List;
import java.util.Map;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.HistoricoDeDocumentos;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;

public interface InstanciaDeTareaDao {
	
	List<InstanciaDeTarea> getInstanciasDeTareaPorIdUsrNombreEstadoTarea(String idUsuario, String nombreEstadoDeTarea);
	
	/*List<InstanciaDeTarea> getInstanciasDeTareaPorIdUsrIdRolNombreEstadoTarea(
			String idUsuario, long idRol, String nombreEstadoDeTarea);*/
	
	List<InstanciaDeTarea> getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(long idInstanciaDeTarea);
	
	void insertInstanciaDeTarea(InstanciaDeTarea instanciaDeTarea, Usuario usuario);
	
	InstanciaDeTarea getInstanciaDeTareaPorIdInstanciaDeTarea(long idInstanciaDeTarea);

	List<InstanciaDeTarea> getInstanciasDeTareasEnEjecucion(String idUsuario, long idEstadoFinalizada);
	
	List<InstanciaDeTarea> getTodasInstanciasDeTareasEnEjecucion();
	
	List<InstanciaDeTarea> getTodasInstanciasDeTareasEnEjecucionPorIdUnidad(long idUnidad);
	
	InstanciaDeTarea getInstanciaDeTareasSiLasAnterioresAsignadasEstanFinalizadas(InstanciaDeTarea instanciaDeTarea);
	
	List<InstanciaDeTarea> getInstanciasDeTareasPorIdExpediente(String idExpediente);
	
	List<InstanciaDeTarea> getInstanciasDeTareasPorIdExpedienteAsignadas(String idExpediente); 
	
	List<InstanciaDeTarea> getInstanciasDeTareasPorIdExpedienteAsignadasUsuario(String idExpediente , String usuario); 	
	
	List<HistoricoDeDocumentos> buscarHistorialDeDocumento (Integer idInstanciaDeTarea);
	
	InstanciaDeTarea getPrimeraInstanciaDeTareaPorId(long idInstanciaDeTarea);
	
	List<InstanciaDeTarea> getInstanciasDeTareasPorIdNoFinalizadasConAntAnd(long idInstanciaDeTarea);
	
	List<InstanciaDeTareaDTO> getInstanciasDeTareasPorIdExpedienteBusqueda(String idExpediente);
	
	List<InstanciaDeTarea> getInstanciasDeTareasPorIdExpedienteUsuarioFinalizada(
			String idExpediente, String usuario);

	List<InstanciaDeTarea> getNotificacionesSeguimientosPorUsuario(String idUsuario);
	
	List<InstanciaDeTarea> getInstanciasDeTareasAnterioresAsignadasPorIdInstanciaDeProcesoIdInstanciaDeTarea(
			long idInstanciaDeProceso, long idInstanciaDeTarea);
	
	List<InstanciaDeTarea> getInstanciasDeTareasAsignadasPorIdInstanciaDeProcesoDistintasDeIdInstanciaDeTarea(
			long idInstanciaDeProceso, long idInstanciaDeTarea);
	
	List<InstanciaDeTarea> getInstanciasDeTareasNuevasQueContinuanDeInstanciaDeTarea(long idInstanciaDeTarea);

	List<InstanciaDeTarea> getInstanciasDeTareaPorIdUsrNombreEstadoTareaPorEsperaRespuesta(
			String idUsuario, String nombreEstadoDeTarea,
			boolean buscarRespuestaEspera);
	
	List<InstanciaDeTarea> getInstanciasDeTareasOrdenUnoPorIdExpedienteAsignadas(
			String idExpediente);
	
	Map<Long, String> getEstadosDeInstProcPorIdUsrNombreEstadoTarea(
			String idUsuario, String nombreEstadoDeTarea);
	
	List<InstanciaDeTarea> getInstanciasDeTareasPorIdExpedienteAsignadasEnEspera(String idExpediente);
	
	List<InstanciaDeTarea> getInstanciasDeTareasAnterioresPorIdInstanciaDeTareaDeDestino(long idInstanciaDeTarea);
	
	InstanciaDeTarea getInstanciaDeTareaPorIdDiagramaTareaNombreExpediente(String idDiagramaTarea, String nombreExpediente);
	
}
