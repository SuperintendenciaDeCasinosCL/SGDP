package cl.gob.scj.sgdp.ws.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.rest.AgregaORemueveTagDeObjetoRequest;
import cl.gob.scj.sgdp.dto.rest.AgregaORemueveTagDeObjetoResponse;
import cl.gob.scj.sgdp.dto.rest.AnulacionProcesoRequest;
import cl.gob.scj.sgdp.dto.rest.AnulacionProcesoResponse;
import cl.gob.scj.sgdp.dto.rest.AvanzaEstadoRestDTO;
import cl.gob.scj.sgdp.dto.rest.ConsultaEstadoProceso;
import cl.gob.scj.sgdp.dto.rest.DocOficialesDeExpResponse;
import cl.gob.scj.sgdp.dto.rest.EtapaDeInstanciaDeProcesoRequest;
import cl.gob.scj.sgdp.dto.rest.EtapaDeInstanciaDeProcesoResponse;
import cl.gob.scj.sgdp.dto.rest.ExpedienteRestDTO;
import cl.gob.scj.sgdp.dto.rest.HistoricoDeInstDeTareaRequest;
import cl.gob.scj.sgdp.dto.rest.HistoricoDeInstDeTareaResponse;
import cl.gob.scj.sgdp.dto.rest.ObtenerTodasLasInstDeTareasAsigPorIdExpResponse;
import cl.gob.scj.sgdp.dto.rest.ObtenerTodasLasTareasPorCodigoProcesoResponse;
import cl.gob.scj.sgdp.dto.rest.ObtenerTodasLasTareasPorIdProcesoResponse;
import cl.gob.scj.sgdp.dto.rest.ObtenerTodosLosProcesosResponse;
import cl.gob.scj.sgdp.dto.rest.ObtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse;
import cl.gob.scj.sgdp.dto.rest.ObtenerTodosLosTiposDeDocumentosPorIdTareaResponse;
import cl.gob.scj.sgdp.dto.rest.RespuestaCambiaEstado;
import cl.gob.scj.sgdp.dto.rest.RespuestaConsultaAvanzadaEstadoProceso;
import cl.gob.scj.sgdp.dto.rest.RespuestaConsultaBasicaEstadoProceso;
import cl.gob.scj.sgdp.dto.rest.RespuestaExpedienteRestDTO;
import cl.gob.scj.sgdp.dto.rest.RespuestaSubirArchivoDTO;
import cl.gob.scj.sgdp.dto.rest.RespuestaTipoDocumentoPrimeraTareaDTO;
import cl.gob.scj.sgdp.dto.rest.RetrocedeEstadoDTO;
import cl.gob.scj.sgdp.dto.rest.SubirArchivoRestDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.tipos.SubeArchivoTareaType;

@Service
public interface ExpedienteRestService {

	RespuestaExpedienteRestDTO crearExpedienteService (ExpedienteRestDTO expedienteRestDto) throws SgdpException;
	
	RespuestaSubirArchivoDTO subirArchivo(SubirArchivoRestDTO subirArchivoRestDTO, SubeArchivoTareaType subeArchivoTareaType) throws SgdpException;
	
	RespuestaCambiaEstado avanzarEstado(AvanzaEstadoRestDTO cambioEstadoRestDTO) throws SgdpException ;
	
	RespuestaCambiaEstado retrocederEstado(RetrocedeEstadoDTO retrocedeEstadoDTO) ;
	
	List<RespuestaConsultaBasicaEstadoProceso> consultaBasicaEstadoProceso(ConsultaEstadoProceso consultaEstadoProceso);
	 
	List<RespuestaConsultaAvanzadaEstadoProceso> consultaAvanzadaEstadoProceso(ConsultaEstadoProceso consultaEstadoProceso);
	
	AnulacionProcesoResponse anularProcesoPorIdExpediente(Usuario usuario, AnulacionProcesoRequest anulacionProcesoRequest);
	
	AgregaORemueveTagDeObjetoResponse agregaRemueveTagDeObjeto(AgregaORemueveTagDeObjetoRequest agregaRemueveTagDeObjetoRequest) throws SgdpException;

	EtapaDeInstanciaDeProcesoResponse getEtapasDeInstanciaDeProcesoPorIdExpediente(EtapaDeInstanciaDeProcesoRequest etapasDeInstanciaDeProcesoRequest) throws SgdpException;
	
	HistoricoDeInstDeTareaResponse getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(HistoricoDeInstDeTareaRequest historicoDeInstDeTareaRequest);	
	
	RespuestaTipoDocumentoPrimeraTareaDTO buscarTipoDeDocumentoPrimeraTareaPorCodigoProceso(String codigoProceso);
	
	RespuestaSubirArchivoDTO subirArchivoDirectoCMS(SubirArchivoRestDTO subirArchivoRestDTO);
	
	ObtenerTodosLosProcesosResponse getBuscarTodosProcesosPorVigencia(boolean vigente);
	
	ObtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse getTiposDeDocumentosPorCodigoProceso(String codigoProceso);
	
	ObtenerTodasLasTareasPorCodigoProcesoResponse getTareasPorCodigoProceso(String codigoProceso);
	
	ObtenerTodasLasTareasPorIdProcesoResponse getTareasPorIdProceso(long idProceso);	
	
	ObtenerTodosLosTiposDeDocumentosPorIdTareaResponse getTiposDeDocumentosPorIdTarea(long idTarea);
	
	ObtenerTodosLosProcesosResponse getTodosProcesos();
	
	ObtenerTodasLasInstDeTareasAsigPorIdExpResponse obtenerTodasLasInstDeTareasAsigPorIdExp(String idExpediente);
	
	DocOficialesDeExpResponse getDocOficialesDeExpediente(String idExpediente, String idUsuario) throws SgdpException;
		
}