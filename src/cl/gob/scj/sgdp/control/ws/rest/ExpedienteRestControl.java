package cl.gob.scj.sgdp.control.ws.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.HistoricoFirmaDTO;
import cl.gob.scj.sgdp.dto.rest.AgregaORemueveTagDeObjetoRequest;
import cl.gob.scj.sgdp.dto.rest.AgregaORemueveTagDeObjetoResponse;
import cl.gob.scj.sgdp.dto.rest.AnulacionProcesoRequest;
import cl.gob.scj.sgdp.dto.rest.AnulacionProcesoResponse;
import cl.gob.scj.sgdp.dto.rest.AvanzaEstadoRestDTO;
import cl.gob.scj.sgdp.dto.rest.ConsultaEstadoProceso;
import cl.gob.scj.sgdp.dto.rest.DetalleDeArchivoDTORest;
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
import cl.gob.scj.sgdp.service.ArchivosInstDeTareaService;
import cl.gob.scj.sgdp.service.GestorDeDocumentosService;
import cl.gob.scj.sgdp.service.HistoricoFirmaService;
import cl.gob.scj.sgdp.service.ObtenerDetalleDeArchivoService;
import cl.gob.scj.sgdp.tipos.SubeArchivoTareaType;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.rest.service.ExpedienteRestService;

@RestController
@RequestMapping("/servicios")
public class ExpedienteRestControl {

	private Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private ExpedienteRestService expedienteRestService;

	@Autowired
	private GestorDeDocumentosService gestorDeDocumentosService;

	@Autowired
	private AutenticacionService autenticacionService;

	@Autowired
	private ObtenerDetalleDeArchivoService obtenerDetalleDeArchivoService;
	
	@Autowired
	private HistoricoFirmaService historicoFirmaService;
	
	@Autowired
	private ArchivosInstDeTareaService archivosInstDeTareaService;

	@Resource(name = "configProps")
	private Properties configProps;

	// Bueno
	@RequestMapping(value = "crearExpediente", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody RespuestaExpedienteRestDTO crearExpediente(@RequestBody ExpedienteRestDTO expedienteRestDto) {

		logger.info(expedienteRestDto.toString());

		RespuestaExpedienteRestDTO respuestaEmpedienteRestDto;
		try {
			respuestaEmpedienteRestDto = expedienteRestService.crearExpedienteService(expedienteRestDto);
		} catch (SgdpException e) {
			logger.info("En el control crearExpediente SgdpException e");
			respuestaEmpedienteRestDto = new RespuestaExpedienteRestDTO();
			respuestaEmpedienteRestDto.setMensaje(configProps.getProperty("respuestaError"));
			logger.info(respuestaEmpedienteRestDto.toString());
			return respuestaEmpedienteRestDto;
		}

		logger.info(respuestaEmpedienteRestDto.toString());

		logger.info("Termino la ejecucion del metodo");

		return respuestaEmpedienteRestDto;
	}

	// Bueno
	@RequestMapping(value = "subirArchivo", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	public @ResponseBody RespuestaSubirArchivoDTO subirArchivo(@RequestBody SubirArchivoRestDTO subirArchivoRestDTO) {
		logger.info("Inicio subirArchivo Rest Control");
		logger.info(subirArchivoRestDTO.toString());
		RespuestaSubirArchivoDTO respuestaSubirArchivoDTO;
		try {
			respuestaSubirArchivoDTO = expedienteRestService.subirArchivo(subirArchivoRestDTO,
					SubeArchivoTareaType.PRIMERA);
			logger.debug("En el control subirArchivo despues del servicio expedienteRestService");
			if (respuestaSubirArchivoDTO != null) {
				logger.info(respuestaSubirArchivoDTO.toString());
			} else {
				logger.info("respuestaSubirArchivoDTO==null");
			}
			return respuestaSubirArchivoDTO;
		} catch (SgdpException e) {
			logger.debug("En el control subirArchivo SgdpException e");
			RespuestaSubirArchivoDTO respuestaSubirArchivoDTOError = new RespuestaSubirArchivoDTO();
			respuestaSubirArchivoDTOError.setMensaje(configProps.getProperty("respuestaError"));
			respuestaSubirArchivoDTOError.setDetalleRespuesta(e.getMessage());
			return respuestaSubirArchivoDTOError;
		}
	}

	@RequestMapping(value = "subirArchivoEnTareasAsignadas", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	public @ResponseBody RespuestaSubirArchivoDTO subirArchivoEnTareasAsignadas(
			@RequestBody SubirArchivoRestDTO subirArchivoRestDTO) {
		logger.info("Inicio subirArchivoEnTareasAsignadas Rest Control");
		logger.info(subirArchivoRestDTO.toString());
		RespuestaSubirArchivoDTO respuestaSubirArchivoDTO;
		try {
			subirArchivoRestDTO.setMarcaSubirDocTransversal("SI");
			respuestaSubirArchivoDTO = expedienteRestService.subirArchivo(subirArchivoRestDTO,
					SubeArchivoTareaType.ASIGNADAS);
			if (respuestaSubirArchivoDTO != null) {
				logger.info(respuestaSubirArchivoDTO.toString());
			} else {
				logger.info("respuestaSubirArchivoDTO==null");
			}
			return respuestaSubirArchivoDTO;
		} catch (SgdpException e) {
			logger.debug("En el control subirArchivoEnTareasAsignadas SgdpException e");
			RespuestaSubirArchivoDTO respuestaSubirArchivoDTOError = new RespuestaSubirArchivoDTO();
			respuestaSubirArchivoDTOError.setMensaje(configProps.getProperty("respuestaError"));
			respuestaSubirArchivoDTOError.setDetalleRespuesta(e.getMessage());
			return respuestaSubirArchivoDTOError;
		}
	}
	
	@RequestMapping(value = "subirArchivoEnTareasAsignadasEnEspera", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	public @ResponseBody RespuestaSubirArchivoDTO subirArchivoEnTareasAsignadasEnEspera(
			@RequestBody SubirArchivoRestDTO subirArchivoRestDTO) {
		logger.info("Inicio subirArchivoEnTareasAsignadas En espera Rest Control");
		logger.info(subirArchivoRestDTO.toString());
		RespuestaSubirArchivoDTO respuestaSubirArchivoDTO;
		try {
			subirArchivoRestDTO.setMarcaSubirDocTransversal("SI");
			respuestaSubirArchivoDTO = expedienteRestService.subirArchivo(subirArchivoRestDTO,
					SubeArchivoTareaType.ASIGNADAS_EN_ESPERA);
			if (respuestaSubirArchivoDTO != null) {
				logger.info(respuestaSubirArchivoDTO.toString());
			} else {
				logger.info("respuestaSubirArchivoDTO==null");
			}
			return respuestaSubirArchivoDTO;
		} catch (SgdpException e) {
			logger.debug("En el control subirArchivoEnTareasAsignadas SgdpException e");
			RespuestaSubirArchivoDTO respuestaSubirArchivoDTOError = new RespuestaSubirArchivoDTO();
			respuestaSubirArchivoDTOError.setMensaje(configProps.getProperty("respuestaError"));
			respuestaSubirArchivoDTOError.setDetalleRespuesta(e.getMessage());
			return respuestaSubirArchivoDTOError;
		}
	}

	@RequestMapping(value = "subirArchivoDirectoCMS", method = RequestMethod.POST, consumes = "application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	public @ResponseBody RespuestaSubirArchivoDTO subirArchivoDirectoCMS(
			@RequestBody SubirArchivoRestDTO subirArchivoRestDTO) {
		logger.info("Inicio subirArchivoDirectoCMS Rest Control");
		logger.info(subirArchivoRestDTO.toString());
		RespuestaSubirArchivoDTO respuestaSubirArchivoDTO;
		try {
			respuestaSubirArchivoDTO = expedienteRestService.subirArchivoDirectoCMS(subirArchivoRestDTO);
			if (respuestaSubirArchivoDTO != null) {
				logger.info(respuestaSubirArchivoDTO.toString());
			} else {
				logger.info("respuestaSubirArchivoDTO==null");
			}
			return respuestaSubirArchivoDTO;
		} catch (Exception e) {
			logger.debug("En el control subirArchivoDirectoCMS Exception e");
			RespuestaSubirArchivoDTO respuestaSubirArchivoDTOError = new RespuestaSubirArchivoDTO();
			respuestaSubirArchivoDTOError.setMensaje(configProps.getProperty("respuestaError"));
			respuestaSubirArchivoDTOError.setDetalleRespuesta(e.getMessage());
			return respuestaSubirArchivoDTOError;
		}
	}

	@RequestMapping(value = "descargarArchivo/{idArchivo}", method = RequestMethod.GET, produces = "application/json")
	public void descargarArchivo(@PathVariable("idArchivo") String idArchivo, HttpServletResponse response)
			throws IOException {

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(configProps.getProperty("usr"));
		//usuario.setClave(configProps.getProperty("pass"));
		

		DetalleDeArchivoDTO detalleDeArchivoDTO = new DetalleDeArchivoDTO();
		logger.info(detalleDeArchivoDTO.toString());

		try {
			usuario.setAlfTicket(
					autenticacionService.login(configProps.getProperty("usr")));
			
			detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, idArchivo);
			InputStream archivoDes = new ByteArrayInputStream(
					gestorDeDocumentosService.getContenidoArchivo(idArchivo, usuario));

			response.setHeader("Content-Disposition", "attachment; filename=" + detalleDeArchivoDTO.getNombre());
			org.apache.commons.io.IOUtils.copy(archivoDes, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception e) {
			logger.error("Error al descargar El archivo", e);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			// throw new RuntimeException("IOError writing file to output
			// stream");
		} finally {
			if (usuario.getAlfTicket() != null) {
				autenticacionService.logout(usuario.getAlfTicket());
			}
		}

	}
	
	@RequestMapping(value = "descargarArchivo/{idArchivo}/{idUsuario}", method = RequestMethod.GET, produces = "application/json")
	public void descargarArchivo(@PathVariable("idArchivo") String idArchivo, @PathVariable("idUsuario") String idUsuario, HttpServletResponse response)
			throws IOException {

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(idUsuario);		

		DetalleDeArchivoDTO detalleDeArchivoDTO = new DetalleDeArchivoDTO();
		logger.info(detalleDeArchivoDTO.toString());

		try {
			usuario.setAlfTicket(autenticacionService.login(usuario.getIdUsuario()));
			
			detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, idArchivo);
			InputStream archivoDes = new ByteArrayInputStream(
					gestorDeDocumentosService.getContenidoArchivo(idArchivo, usuario));

			response.setHeader("Content-Disposition", "attachment; filename=" + detalleDeArchivoDTO.getNombre());
			org.apache.commons.io.IOUtils.copy(archivoDes, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception e) {
			logger.error("Error al descargar El archivo", e);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			// throw new RuntimeException("IOError writing file to output
			// stream");
		} finally {
			if (usuario.getAlfTicket() != null) {
				autenticacionService.logout(usuario.getAlfTicket());
			}
		}

	}
	
	@RequestMapping(value = "descargarArchivoByte/{idArchivo}", method = RequestMethod.GET)
	public byte[] descargarArchivoByte(@PathVariable("idArchivo") String idArchivo, HttpServletResponse response) throws IOException {
		
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(configProps.getProperty("usr"));
	
		try {
			usuario.setAlfTicket(
					autenticacionService.login(configProps.getProperty("usr")));
		
			return gestorDeDocumentosService.getContenidoArchivo(idArchivo, usuario);
		
		} catch (Exception e) {
			logger.error("Error al descargar El archivo", e);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} finally {
			if (usuario.getAlfTicket() != null) {
				autenticacionService.logout(usuario.getAlfTicket());
			}
		}
		
	}

	@RequestMapping(value = "avanzarEstado", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RespuestaCambiaEstado avanzarEstado(@RequestBody AvanzaEstadoRestDTO avanzaEstadoRestDTO) {

		logger.info("Inicio avanzarEstado");
		logger.info(avanzaEstadoRestDTO.toString());

		RespuestaCambiaEstado respuestaCambiaEstado;
		try {
			respuestaCambiaEstado = expedienteRestService.avanzarEstado(avanzaEstadoRestDTO);
			logger.info(respuestaCambiaEstado.toString());
			return respuestaCambiaEstado;
		} catch (SgdpException e) {
			RespuestaCambiaEstado respuestaCambiaEstadoError = new RespuestaCambiaEstado();
			respuestaCambiaEstadoError.setMensaje(configProps.getProperty("respuestaError"));
			return respuestaCambiaEstadoError;
		}

	}

	@RequestMapping(value = "retrocederEstado", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RespuestaCambiaEstado retrocederEstado(@RequestBody RetrocedeEstadoDTO retrocedeEstadoDTO) {

		logger.info("Inicio retrocederEstado ");
		logger.info(retrocedeEstadoDTO.toString());
		RespuestaCambiaEstado respuestaCambiaEstado = new RespuestaCambiaEstado();
		respuestaCambiaEstado = expedienteRestService.retrocederEstado(retrocedeEstadoDTO);
		logger.info(respuestaCambiaEstado.toString());
		return respuestaCambiaEstado;
	}

	@RequestMapping(value = "ConsultaBasicaEstadoProceso", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<RespuestaConsultaBasicaEstadoProceso> consultaBasicaEstadoProceso(
			@RequestBody ConsultaEstadoProceso consultaEstadoProceso) {

		logger.info("Inicio consultaBasicaEstadoProceso ");
		List<RespuestaConsultaBasicaEstadoProceso> respuestaConsultaBasicaEstadoProceso = expedienteRestService
				.consultaBasicaEstadoProceso(consultaEstadoProceso);
		return respuestaConsultaBasicaEstadoProceso;
	}

	@RequestMapping(value = "ConsultaAvanzadaEstadoProceso", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<RespuestaConsultaAvanzadaEstadoProceso> ConsultaAvanzadaEstadoProceso(
			@RequestBody ConsultaEstadoProceso consultaEstadoProceso) {

		List<RespuestaConsultaAvanzadaEstadoProceso> ListaRespuestaConsultaEstadoProceso = expedienteRestService
				.consultaAvanzadaEstadoProceso(consultaEstadoProceso);
		return ListaRespuestaConsultaEstadoProceso;
	}

	@RequestMapping(value = "anularProcesoPorIdExpediente", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody AnulacionProcesoResponse anularProcesoPorIdExpediente(
			@RequestBody AnulacionProcesoRequest anulacionProcesoRequest) {
		logger.info("Inicio anularProceso");
		logger.info(anulacionProcesoRequest.toString());
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(anulacionProcesoRequest.getIdUsuario());
		AnulacionProcesoResponse anulacionProcesoResponse = expedienteRestService.anularProcesoPorIdExpediente(usuario,
				anulacionProcesoRequest);
		return anulacionProcesoResponse;
	}

	@RequestMapping(value = "agregaRemueveTagDeObjeto", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody AgregaORemueveTagDeObjetoResponse agregaRemueveTagDeObjeto(
			@RequestBody AgregaORemueveTagDeObjetoRequest agregaORemueveTagDeObjetoRequest) {
		logger.info("Inicio agregaRemueveTagDeObjeto");
		logger.info(agregaORemueveTagDeObjetoRequest.toString());
		AgregaORemueveTagDeObjetoResponse agregaRemueveTagDeObjetoResponse;
		try {
			agregaRemueveTagDeObjetoResponse = expedienteRestService
					.agregaRemueveTagDeObjeto(agregaORemueveTagDeObjetoRequest);
			logger.info(agregaRemueveTagDeObjetoResponse.toString());
			return agregaRemueveTagDeObjetoResponse;
		} catch (SgdpException e) {
			AgregaORemueveTagDeObjetoResponse agregaRemueveTagDeObjetoResponseError = new AgregaORemueveTagDeObjetoResponse();
			agregaRemueveTagDeObjetoResponseError.setMensajeRespuesta(configProps.getProperty("respuestaError"));
			return agregaRemueveTagDeObjetoResponseError;
		}
	}

	@RequestMapping(value = "getEtapasDeInstanciaDeProcesoPorIdExpediente", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody EtapaDeInstanciaDeProcesoResponse getEtapasDeInstanciaDeProcesoPorIdExpediente(
			HttpServletRequest request) throws IOException {
		logger.info("Inicio getEtapasDeInstanciaDeProcesoPorIdExpediente");
		String idExpediente = request.getParameter("idExpediente");
		String idUsuario = request.getParameter("idUsuario");
		String clave = request.getParameter("clave");
		EtapaDeInstanciaDeProcesoRequest etapaDeInstanciaDeProcesoRequest = new EtapaDeInstanciaDeProcesoRequest();
		etapaDeInstanciaDeProcesoRequest.setIdExpediente(idExpediente);
		etapaDeInstanciaDeProcesoRequest.setIdUsuario(idUsuario);
		etapaDeInstanciaDeProcesoRequest.setClave(clave);
		logger.info(etapaDeInstanciaDeProcesoRequest.toString());
		try {
			EtapaDeInstanciaDeProcesoResponse etapasDeInstanciaDeProcesoResponse = expedienteRestService
					.getEtapasDeInstanciaDeProcesoPorIdExpediente(etapaDeInstanciaDeProcesoRequest);
			logger.info(etapasDeInstanciaDeProcesoResponse.toString());
			return etapasDeInstanciaDeProcesoResponse;
		} catch (SgdpException e) {
			EtapaDeInstanciaDeProcesoResponse etapasDeInstanciaDeProcesoResponseError = new EtapaDeInstanciaDeProcesoResponse();
			etapasDeInstanciaDeProcesoResponseError.setCodigoRespuesta(configProps.getProperty("respuestaError"));
			return etapasDeInstanciaDeProcesoResponseError;
		}
	}

	@RequestMapping(value = "getHistoricoDeInstDeTareaPorIdExpedienteBusqueda", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody HistoricoDeInstDeTareaResponse getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(
			@RequestBody HistoricoDeInstDeTareaRequest historicoDeInstDeTareaRequest) throws IOException {
		logger.info("Inicio getHistoricoDeInstDeTareaPorIdExpedienteBusqueda");
		logger.info(historicoDeInstDeTareaRequest.toString());
		HistoricoDeInstDeTareaResponse historicoDeInstDeTareaResponse;
		historicoDeInstDeTareaResponse = expedienteRestService
				.getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(historicoDeInstDeTareaRequest);
		logger.info(historicoDeInstDeTareaResponse.toString());
		return historicoDeInstDeTareaResponse;
	}

	@RequestMapping(value = "obtenerTiposDocumentoPrimeraTarea/{codigoProceso}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody RespuestaTipoDocumentoPrimeraTareaDTO buscarTipoDeDocumentoPrimeraTareaPorCodigoProceso(
			@PathVariable("codigoProceso") String codigoProceso) {
		return expedienteRestService.buscarTipoDeDocumentoPrimeraTareaPorCodigoProceso(codigoProceso);
	}
	
	@RequestMapping(value = "obtenerTodosLosProcesos", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ObtenerTodosLosProcesosResponse obtenerTodosLosProcesos() {
		try {
			 return expedienteRestService.getTodosProcesos();
		} catch (Exception e) {
			ObtenerTodosLosProcesosResponse obtenerTodosLosSubprocesosVigentesResponse = new ObtenerTodosLosProcesosResponse();
			obtenerTodosLosSubprocesosVigentesResponse.setMensaje("ERROR");
			obtenerTodosLosSubprocesosVigentesResponse.setDetalleRespuesta(e.getMessage());
			return obtenerTodosLosSubprocesosVigentesResponse;
		}
	}
	
	@RequestMapping(value = "obtenerTodosLosTiposDeDocumentosPorCodigoProceso/{codigoProceso}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ObtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse obtenerTodosLosTiposDeDocumentosPorCodigoProceso(@PathVariable("codigoProceso") String codigoProceso) {
		try {
			return expedienteRestService.getTiposDeDocumentosPorCodigoProceso(codigoProceso);
		} catch (Exception e) {
			ObtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse obtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse = new ObtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse();
			obtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse.setMensaje("ERROR");
			obtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse.setDetalleRespuesta(e.getMessage());
			return obtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse;
		}
	}
	
	@RequestMapping(value = "obtenerTodasLasTareasPorCodigoProceso/{codigoProceso}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ObtenerTodasLasTareasPorCodigoProcesoResponse obtenerTodasLasTareasPorCodigoProceso(@PathVariable("codigoProceso") String codigoProceso) {
		try {
			return expedienteRestService.getTareasPorCodigoProceso(codigoProceso);
		} catch (Exception e) {
			ObtenerTodasLasTareasPorCodigoProcesoResponse obtenerTodasLasTareasPorCodigoProcesoResponse = new ObtenerTodasLasTareasPorCodigoProcesoResponse();
			obtenerTodasLasTareasPorCodigoProcesoResponse.setMensaje("ERROR");
			obtenerTodasLasTareasPorCodigoProcesoResponse.setDetalleRespuesta(e.getMessage());
			return obtenerTodasLasTareasPorCodigoProcesoResponse;
		}
	}
	
	@RequestMapping(value = "obtenerTodasLasTareasPorIdProceso/{idProceso}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ObtenerTodasLasTareasPorIdProcesoResponse obtenerTodasLasTareasPorIdProceso(@PathVariable("idProceso") long idProceso) {
		logger.info("idProceso: "+ idProceso);
		try {
			return expedienteRestService.getTareasPorIdProceso(idProceso);
		} catch (Exception e) {
			ObtenerTodasLasTareasPorIdProcesoResponse obtenerTodasLasTareasPorIdProcesoResponse = new ObtenerTodasLasTareasPorIdProcesoResponse();
			obtenerTodasLasTareasPorIdProcesoResponse.setMensaje("ERROR");
			obtenerTodasLasTareasPorIdProcesoResponse.setDetalleRespuesta(e.getMessage());
			return obtenerTodasLasTareasPorIdProcesoResponse;
		}
	}
	
	@RequestMapping(value = "obtenerTodosLosTiposDeDocumentosPorIdTarea/{idTarea}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ObtenerTodosLosTiposDeDocumentosPorIdTareaResponse obtenerTodosLosTiposDeDocumentosPorIdTarea(@PathVariable("idTarea") long idTarea) {
		try {
			return expedienteRestService.getTiposDeDocumentosPorIdTarea(idTarea);
		} catch (Exception e) {
			ObtenerTodosLosTiposDeDocumentosPorIdTareaResponse obtenerTodosLosTiposDeDocumentosPorIdTareaResponse = new ObtenerTodosLosTiposDeDocumentosPorIdTareaResponse();
			obtenerTodosLosTiposDeDocumentosPorIdTareaResponse.setMensaje("ERROR");
			obtenerTodosLosTiposDeDocumentosPorIdTareaResponse.setDetalleRespuesta(e.getMessage());
			return obtenerTodosLosTiposDeDocumentosPorIdTareaResponse;
		}
	}
	
	@RequestMapping(value = "obtenerTodasLasInstDeTareasAsigPorIdExp/{idExpediente}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ObtenerTodasLasInstDeTareasAsigPorIdExpResponse obtenerTodasLasInstDeTareasAsigPorIdExp(@PathVariable("idExpediente") String idExpediente) {		
		try {
			return expedienteRestService.obtenerTodasLasInstDeTareasAsigPorIdExp(idExpediente);
		} catch (Exception e) {
			ObtenerTodasLasInstDeTareasAsigPorIdExpResponse obtenerTodasLasInstDeTareasAsigPorIdExpResponse = new ObtenerTodasLasInstDeTareasAsigPorIdExpResponse();
			obtenerTodasLasInstDeTareasAsigPorIdExpResponse.setMensaje("ERROR");
			obtenerTodasLasInstDeTareasAsigPorIdExpResponse.setDetalleRespuesta(e.getMessage());
			return obtenerTodasLasInstDeTareasAsigPorIdExpResponse;
		}		
	}
	
	@RequestMapping(value = "getDetalleDeArchivo/{idArchivo}", method = RequestMethod.GET, produces = "application/json")
	public DetalleDeArchivoDTORest getDetalleDeArchivo(@PathVariable("idArchivo") String idArchivo, HttpServletResponse response) throws IOException {

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(configProps.getProperty("usr"));
		DetalleDeArchivoDTO detalleDeArchivoDTO = new DetalleDeArchivoDTO();
		logger.info(detalleDeArchivoDTO.toString());
		DetalleDeArchivoDTORest detalleDeArchivoDTORest = new DetalleDeArchivoDTORest();
		try {
			usuario.setAlfTicket(autenticacionService.login(configProps.getProperty("usr")));
			detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, idArchivo);
			BeanUtils.copyProperties(detalleDeArchivoDTO, detalleDeArchivoDTORest);
			List<HistoricoFirmaDTO> hf = historicoFirmaService.getHistoricoFirmaDocumentoFEAPorIdArchivo(idArchivo);
			if (hf!=null && !hf.isEmpty()) {
				detalleDeArchivoDTORest.setTieneFEA(true);
			}
		} catch (Exception e) {
			logger.error("Error al descargar El archivo", e);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);			
		} finally {
			if (usuario.getAlfTicket() != null) {
				autenticacionService.logout(usuario.getAlfTicket());
			}
		}		
		return detalleDeArchivoDTORest;
	}
	
	@RequestMapping(value = "avanzarEstadoTareasEnEspera", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RespuestaCambiaEstado avanzarEstadoTareasEnEspera(@RequestBody AvanzaEstadoRestDTO avanzaEstadoRestDTO) {
		logger.info("Inicio avanzarEstadoTareasEnEspera");
		logger.info(avanzaEstadoRestDTO.toString());
		RespuestaCambiaEstado respuestaCambiaEstado;
		try {
			avanzaEstadoRestDTO.setTareasEnEspera(true);
			logger.info(avanzaEstadoRestDTO.toString());
			respuestaCambiaEstado = expedienteRestService.avanzarEstado(avanzaEstadoRestDTO);
			logger.info(respuestaCambiaEstado.toString());
			return respuestaCambiaEstado;
		} catch (SgdpException e) {
			RespuestaCambiaEstado respuestaCambiaEstadoError = new RespuestaCambiaEstado();
			respuestaCambiaEstadoError.setMensaje(configProps.getProperty("respuestaError"));
			return respuestaCambiaEstadoError;
		}

	}
	
	@RequestMapping(value = "getDocOficialesDeExpediente/{idExpediente}/{idUsuario}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody DocOficialesDeExpResponse getDocOficialesDeExpediente(@PathVariable("idExpediente") String idExpediente
			, @PathVariable("idUsuario") String idUsuario) {		
		DocOficialesDeExpResponse docOficialesDeExpResponse;
		try {
			docOficialesDeExpResponse = expedienteRestService.getDocOficialesDeExpediente(idExpediente, idUsuario);
			docOficialesDeExpResponse.setEstado("0");
			docOficialesDeExpResponse.setGlosa("OK");
		} catch (SgdpException e) {
			docOficialesDeExpResponse = new DocOficialesDeExpResponse();
			docOficialesDeExpResponse.setEstado("1");
			docOficialesDeExpResponse.setGlosa(e.getMessage());
		}		
		return docOficialesDeExpResponse;		
	}
	
	@RequestMapping(value="/estaDocumentoFirmado/{idArchivo}/{idInstanciaDeTarea}/{idUsuario}", method=RequestMethod.GET)
	public @ResponseBody boolean estaDocumentoFirmado(@PathVariable("idArchivo") String idArchivo,	
										@PathVariable("idInstanciaDeTarea") long idInstanciaDeTarea,
										@PathVariable("idUsuario") String idUsuario,
										Model model, 
										HttpServletRequest request) {
		logger.debug("Inicio estaDocumentoFirmado");
		logger.debug("idArchivo: " + idArchivo);
		logger.debug("idInstanciaDeTarea: " + idInstanciaDeTarea);
		logger.debug("idUsuario: " + idUsuario);	
		try {
			ArchivosInstDeTareaDTO archivosInstDeTareaDTO = archivosInstDeTareaService.getArchivosInstDeTareaPorIdArchivoIdInstanciaDeTareaIdUsuario(idArchivo, idInstanciaDeTarea, idUsuario);
			if (archivosInstDeTareaDTO!=null && (archivosInstDeTareaDTO.getEstaFirmadoConFEACentralizada()==true || archivosInstDeTareaDTO.getEstaFirmadoConFEAWebStart() == true )) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			return false;
		}		
		
	}
	
	@RequestMapping(value="/getIdArchivoPorIdDocumentoFirmado/{idDocumentoFirmado}", method=RequestMethod.GET)
	public @ResponseBody String getIdArchivoPorIdDocumentoFirmado(@PathVariable("idDocumentoFirmado") long idDocumentoFirmado, Model model, HttpServletRequest request) {
		return expedienteRestService.getIdArchivoPorIdDocumentoFirmado(idDocumentoFirmado);		
	}
	
	@RequestMapping(value="/getIdArchivoPorIdErroneoIdExpediente/{idArhivoErroneo}/{idExpediente}/{idUsuario}", method=RequestMethod.GET)
	public @ResponseBody String getIdArchivoPorIdErroneoIdExpediente(@PathVariable("idArhivoErroneo") String idArhivoErroneo, @PathVariable("idExpediente") String idExpediente, @PathVariable("idUsuario") String idUsuario) {
		try {
			return expedienteRestService.getIdArchivoPorIdErroneoIdExpediente(idArhivoErroneo, idExpediente, idUsuario);
		} catch (Exception e) {			
			logger.info(e.getMessage());
			return "";
		}		
	}
	
	@RequestMapping(value="/validaSiHayFirmaHoy/{idTipoDeDocumento}/{idInstanciaDeTarea}/{idUsuario}", method=RequestMethod.GET)
	public @ResponseBody boolean validaSiHayFirmaHoy(@PathVariable("idTipoDeDocumento") long idTipoDeDocumento,
			@PathVariable("idInstanciaDeTarea") long idInstanciaDeTarea, @PathVariable("idUsuario") String idUsuario,
			Model model, HttpServletRequest request) {		
		try {
			return expedienteRestService.validaSiHayFirmaHoy(idTipoDeDocumento, idInstanciaDeTarea, idUsuario);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			return false;
		}

	}

}