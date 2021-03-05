package cl.gob.scj.sgdp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.AnadirAntecedenteDTO;
import cl.gob.scj.sgdp.dto.AutorDTO;
import cl.gob.scj.sgdp.dto.EstadoDeProcesoDTO;
import cl.gob.scj.sgdp.dto.FiltroExpedienteDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.MacroProcesoDTO;
import cl.gob.scj.sgdp.dto.RespuestaMailDTO;
import cl.gob.scj.sgdp.dto.SuggestionsDTO;
import cl.gob.scj.sgdp.dto.TareaDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;

@Service
public interface BandejaDeEntradaService {
	
	List<InstanciaDeTareaDTO> getInstanciasDeTareaPorIdUsrNombreEstadoTarea(
			Usuario usuario, String nombreEstadoDeTarea, List<InstanciaDeTareaDTO> instanciasDeTareasDTO) throws IOException;
	
	List<InstanciaDeTareaDTO> getInstanciasDeTareaPorIdUsrNombreEstadoTareaFiltro(
			Usuario usuario, String nombreEstadoDeTarea, List<InstanciaDeTareaDTO> instanciasDeTareasDTO,FiltroExpedienteDTO filtroExpedienteDTO) throws IOException;
	
	
	List<MacroProcesoDTO> getTodosLosMacroProcesos(List<MacroProcesoDTO> macroProcesosDTO);
	
	List<InstanciaDeProceso> getTodasLasInstanciasDeProcesos();
	
	EstadoDeProcesoDTO getEstadoDeProcesoPorId(long idEstadoDeProceso);
	
	List<TareaDTO> getTareasPorVigenciaPorIdProceso(long idProceso);
	
	List<SuggestionsDTO> getUsuariosSugeridos(String idUsuarioLike, List<SuggestionsDTO> listaDeUsuariosSugerida);
	
	List<AutorDTO> getTodosLosAutores();

	//List<TipoDeDocumentoDTO> getTodosLosTiposDeDocumentos(List<TipoDeDocumentoDTO> tiposDeDocumentosDTO);	
	
	List<InstanciaDeTareaDTO> getInstanciasDeTareaEnEjecucion(Usuario usuario, long idEstadoFinalizada, List<InstanciaDeTareaDTO> instanciasDeTareasDTOEnEjecucion) throws IOException;
	
	List<InstanciaDeTareaDTO> getTodasInstanciasDeTareasEnEjecucion(List<InstanciaDeTareaDTO> instanciasDeTareasDTOEnEjecucion) throws IOException;
	
	List<InstanciaDeTareaDTO> getTodasInstanciasDeTareasEnEjecucionPorIdUnidad(long idUnidad, List<InstanciaDeTareaDTO> instanciasDeTareasDTOEnEjecucion) throws IOException;

	List<InstanciaDeTareaDTO> getNotificacionesSeguimientosPorUsuario(
			List<InstanciaDeTareaDTO> instanciasDeTareasDTOEnEjecucion,
			Usuario usuario) throws IOException;	
	
	RespuestaMailDTO preparaEnvioMail(List<RespuestaMailDTO> listaRespuestaMailDto, AnadirAntecedenteDTO anadirAntecedenteDTO ,Usuario usuario);
	
	List<TareaDTO> getTareasPorIdProceso(long idProceso);
	
	List<TareaDTO> getTareasPorCodigoProceso(String codigoProceso);	
	
	List<TipoDeDocumentoDTO> getTodosLosTiposDeDocumentosPorIdTarea(long idTarea);
	
	List<InstanciaDeTareaDTO> getTodasInstanciasDeTareasEnEjecucionPorIdUnidades(Usuario usuario, List<InstanciaDeTareaDTO> instanciasDeTareasDTOEnEjecucion) throws IOException;

}