package cl.gob.scj.sgdp.ws.rest.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dao.InstanciaDeTareaDao;
import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dto.AnulacionDTO;
import cl.gob.scj.sgdp.dto.ArchivoInfoDTO;
import cl.gob.scj.sgdp.dto.AsignacionTareaDTO;
import cl.gob.scj.sgdp.dto.ContinuarProcesoDTO;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.EtapaDeInstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.dto.ExpedienteDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.RetrocedeProcesoDTO;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.dto.TareaDTO;
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
import cl.gob.scj.sgdp.dto.rest.HistoricoDeInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.rest.HistoricoDeInstDeTareaRequest;
import cl.gob.scj.sgdp.dto.rest.HistoricoDeInstDeTareaResponse;
import cl.gob.scj.sgdp.dto.rest.InstanciaDeTareaDTORest;
import cl.gob.scj.sgdp.dto.rest.ObtenerTodasLasInstDeTareasAsigPorIdExpResponse;
import cl.gob.scj.sgdp.dto.rest.ObtenerTodasLasTareasPorCodigoProcesoResponse;
import cl.gob.scj.sgdp.dto.rest.ObtenerTodasLasTareasPorIdProcesoResponse;
import cl.gob.scj.sgdp.dto.rest.ObtenerTodosLosProcesosResponse;
import cl.gob.scj.sgdp.dto.rest.ObtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse;
import cl.gob.scj.sgdp.dto.rest.ObtenerTodosLosTiposDeDocumentosPorIdTareaResponse;
import cl.gob.scj.sgdp.dto.rest.ProcesoRestDTO;
import cl.gob.scj.sgdp.dto.rest.RespuestaCambiaEstado;
import cl.gob.scj.sgdp.dto.rest.RespuestaConsultaAvanzadaEstadoProceso;
import cl.gob.scj.sgdp.dto.rest.RespuestaConsultaBasicaEstadoProceso;
import cl.gob.scj.sgdp.dto.rest.RespuestaExpedienteRestDTO;
import cl.gob.scj.sgdp.dto.rest.RespuestaSubirArchivoDTO;
import cl.gob.scj.sgdp.dto.rest.RespuestaTipoDocumentoPrimeraTareaDTO;
import cl.gob.scj.sgdp.dto.rest.RetrocedeEstadoDTO;
import cl.gob.scj.sgdp.dto.rest.SubirArchivoRestDTO;
import cl.gob.scj.sgdp.dto.rest.TareaRestDTO;
import cl.gob.scj.sgdp.dto.rest.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.model.UsuarioAsignado;
import cl.gob.scj.sgdp.model.UsuarioRol;
import cl.gob.scj.sgdp.service.BandejaDeEntradaService;
import cl.gob.scj.sgdp.service.CrearExpedienteService;
import cl.gob.scj.sgdp.service.HistoricoDeInstDeTareaService;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;
import cl.gob.scj.sgdp.service.InstanciaDeTareaService;
import cl.gob.scj.sgdp.service.MueveProcesoService;
import cl.gob.scj.sgdp.service.ObtenerArchivosExpedienteService;
import cl.gob.scj.sgdp.service.ObtenerDetalleDeArchivoService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.ProcesoService;
import cl.gob.scj.sgdp.service.SubirArchivoService;
import cl.gob.scj.sgdp.service.TipoDeDocumentoService;
import cl.gob.scj.sgdp.service.UsuarioResponsabilidadService;
import cl.gob.scj.sgdp.service.UsuarioRolService;
import cl.gob.scj.sgdp.tipos.BifurcacionType;
import cl.gob.scj.sgdp.tipos.SubeArchivoTareaType;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.FileUtil;
import cl.gob.scj.sgdp.util.SgdpMultipartFile;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.impl.GestorDeTagsCMSServiceImpl;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.impl.SubirArchivoCMSServiceImpl;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.AgregaRemueveTagDeObjetoResponse;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.SubirArchivoResponse;
import cl.gob.scj.sgdp.ws.rest.service.ExpedienteRestService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ExpedienteRestServiceImpl implements ExpedienteRestService {
	private Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private AutenticacionService autenticacionService;

	@Autowired
	private CrearExpedienteService crearExpedienteService;

	@Autowired
	private ParametroService parametroService;

	@Autowired
	private SubirArchivoService subirArchivoService;

	@Autowired
	private ObtenerDetalleDeArchivoService obtenerDetalleDeArchivoService;

	@Autowired
	private InstanciaDeTareaDao instanciaDeTareaDao;

	@Autowired
	private MueveProcesoService mueveProcesoService;

	@Autowired
	private InstanciaDeProcesoDao instanciaDeProcesoDao;
	
	@Autowired
	private UsuarioResponsabilidadService usuarioResponsabilidadService;
	
	@Autowired
	private GestorDeTagsCMSServiceImpl gestorDeTagsCMSServiceImpl;
	
	@Autowired
	private InstanciaDeTareaService instanciaDeTareaService;
	
	@Autowired
	private InstanciaDeProcesoService instanciaDeProcesoService;
	
	@Autowired
	private HistoricoDeInstDeTareaService historicoDeInstDeTareaService;
	
	@Autowired
	private TipoDeDocumentoDao tipoDeDocumentoDao;
	
	@Autowired	
	private TipoDeDocumentoService tipoDeDocumentoService;
		
	@Autowired
	private SubirArchivoCMSServiceImpl subirArchivoCMSServiceImpl;
	
	@Autowired
	private BandejaDeEntradaService bandejaDeEntradaService;
	
	@Autowired
	private UsuarioRolService usuarioRolService;
	
	@Autowired
	private ObtenerArchivosExpedienteService obtenerArchivosExpedienteService;
	
	@Autowired
	private ProcesoService procesoService;
	
	@Resource(name = "configProps")
	private Properties configProps;

	@Override
	public RespuestaExpedienteRestDTO crearExpedienteService(ExpedienteRestDTO expedienteRestDto) throws SgdpException {

		logger.info("Inicio crearExpedienteService");
		logger.info(expedienteRestDto.toString());

		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		Usuario usuario = new Usuario();
		RespuestaExpedienteRestDTO respuestaEmpedienteRestDto = new RespuestaExpedienteRestDTO();
		//Proceso proceso;
		
		Date fechaDeInicioInstProc = null;

		try {
			
			expedienteDTO.setCreador(expedienteRestDto.getCreador());
			expedienteDTO.setMateria(expedienteRestDto.getMateria());
			expedienteDTO.setIdAutor(expedienteRestDto.getIdAutor());
			expedienteDTO.setIdMacroProceso(expedienteRestDto.getIdMacroProceso());
			expedienteDTO.setIdProceso(expedienteRestDto.getIdProceso());
			expedienteDTO.setIdUsuarioCrea(expedienteRestDto.getIdUsuarioCrea());
			expedienteDTO.setCodigoProceso(expedienteRestDto.getCodigoProceso());
									
			if (expedienteRestDto.getFechaDeInicioInstProc()!=null && !expedienteRestDto.getFechaDeInicioInstProc().equals("")) {
				fechaDeInicioInstProc = FechaUtil.simpleDateFormatFormHHMMSS.parse(expedienteRestDto.getFechaDeInicioInstProc());
				expedienteDTO.setFechaDeInicioInstProc(fechaDeInicioInstProc);
			}
			
			usuario.setIdUsuario(expedienteRestDto.getIdUsuario());
			usuario.setIdRolUsuarioSeleccionado(expedienteRestDto.getIdRolUsuarioSeleccionado());
				
			logger.info("Termino de setear los valores");
	
			usuario.setAlfTicket(autenticacionService.login(expedienteRestDto.getIdUsuario()));
			
			List<UsuarioRol> usuarioRoles = usuarioRolService.getUsuarioRolesPorIdUsuario(usuario.getIdUsuario());
			usuario.setRolUnidadPermisosPorIdRolUsuarioSeleccionado(usuarioRoles);
				
			respuestaEmpedienteRestDto.setMensaje(crearExpedienteService.crearExpediente(expedienteDTO, usuario));
			logger.info("Creo el expediente de manera exitosa");

			respuestaEmpedienteRestDto.setIdExpediente(expedienteDTO.getIdExpediente());
			respuestaEmpedienteRestDto.setNombreExpediente(expedienteDTO.getNombreExpediente());

			logger.info("Asigno el id Expdiente y el nombre expediente de forma exitosa");

			// Se genera un lista de los id de instancia de tarea separados por
			// coma
			String listaIdInstanciaDeTarea = "";		
			respuestaEmpedienteRestDto.setListaIdInstanciaDeTarea(listaIdInstanciaDeTarea);
			logger.debug("Retornando desde crearExpedienteService");
			
			return respuestaEmpedienteRestDto;

		} catch (ParseException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			throw new SgdpException();
		} catch (SgdpException e) {		
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			throw e;
		} catch (Exception e) {			
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			throw new SgdpException();
		} finally {
			if (usuario.getAlfTicket() != null) {
				autenticacionService.logout(usuario.getAlfTicket());
			}			
		}
	}
	
	private void validacionesSubirArchivo(SubirArhivoDTO subirArhivoDTO) throws SgdpException {
		cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoService.getTipoDeDocumentoDTOPorIdTipoDeDocumento(subirArhivoDTO.getIdTipoDeDocumentoSubir());
		String nombreTipoDoc =  null;
		if (tipoDeDocumentoDTO == null) {
			throw new SgdpException("El id del tipo del documento no existe: subirArhivoDTO.getIdTipoDeDocumentoSubir(): " + subirArhivoDTO.getIdTipoDeDocumentoSubir());
		}
		tipoDeDocumentoDTO = tipoDeDocumentoService.getTipoDeDocumentoDTOPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(tipoDeDocumentoDTO.getNombreDeTipoDeDocumento(), subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo());
		nombreTipoDoc = tipoDeDocumentoDTO!=null ? tipoDeDocumentoDTO.getNombreDeTipoDeDocumento() : null;
		if (tipoDeDocumentoDTO == null) {
			throw new SgdpException("El id del tipo del documento no esta asociado a la id de la tarea: nombreTipoDoc/subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo() " +
					nombreTipoDoc+ "/" + subirArhivoDTO.getIdInstanciaDeTareaSubirArchivo() );
		}
	}
	
	@Override
	public RespuestaSubirArchivoDTO subirArchivoDirectoCMS(SubirArchivoRestDTO subirArchivoRestDTO) {		
		logger.info("Inicio subirArchivoDirectoCMS");		
		SubirArhivoDTO subirArhivoDTO = new SubirArhivoDTO();
		Usuario usuario = new Usuario();
		RespuestaSubirArchivoDTO respuestaSubirArchivoDTO = new RespuestaSubirArchivoDTO();		
		SgdpMultipartFile sgdpMultipartFile = new SgdpMultipartFile();
		try {
			byte[] archivoByteArray = FileUtil.decodeBase64ToByteArray(subirArchivoRestDTO.getArchivo(),
					parametroService.getParametroPorID(Constantes.ID_PARAM_ENCODE_CHARACTER_TRANSFORMATION_FEA)
							.getValorParametroChar());
			sgdpMultipartFile.setBytes(archivoByteArray);
			sgdpMultipartFile.setContentType(subirArchivoRestDTO.getContentType());
			sgdpMultipartFile.setName(subirArchivoRestDTO.getName());
			sgdpMultipartFile.setOriginalFilename(subirArchivoRestDTO.getName());
			subirArhivoDTO.setArchivo(sgdpMultipartFile);
			usuario.setAlfTicket(subirArchivoRestDTO.getAlfTicket());
			usuario.setIdUsuario(subirArchivoRestDTO.getIdUsuario());
			subirArhivoDTO.setIdExpedienteSubirArchivo(subirArchivoRestDTO.getIdExpedienteSubirArchivo());
			subirArhivoDTO.setNombreDeArchivo(subirArchivoRestDTO.getName());
			subirArhivoDTO.setNumeroDocumento(subirArchivoRestDTO.getNumeroDocumento());
			logger.info(subirArhivoDTO.toString());
			SubirArchivoResponse subirArchivoResponse = subirArchivoCMSServiceImpl.subirArchivo(usuario, subirArhivoDTO);
			respuestaSubirArchivoDTO.setIdArchivo(subirArchivoResponse.getIdArchivo());
			respuestaSubirArchivoDTO.setMensaje(configProps.getProperty("respuestaOK"));
			return respuestaSubirArchivoDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			respuestaSubirArchivoDTO.setMensaje(configProps.getProperty("respuestaError"));
			respuestaSubirArchivoDTO.setDetalleRespuesta(e.getMessage());
			return respuestaSubirArchivoDTO;
		}		
	}

	@Override
	public RespuestaSubirArchivoDTO subirArchivo(SubirArchivoRestDTO subirArchivoRestDTO, SubeArchivoTareaType subeArchivoTareaType) throws SgdpException {

		logger.info("Inicio subirArchivo");
		
		SubirArhivoDTO subirArhivoDTO = new SubirArhivoDTO();
		Usuario usuario = new Usuario();
		RespuestaSubirArchivoDTO respuestaSubirArchivoDTO = new RespuestaSubirArchivoDTO();

		DetalleDeArchivoDTO detalleDeArchivoDTO = new DetalleDeArchivoDTO();

		SgdpMultipartFile sgdpMultipartFile = new SgdpMultipartFile();

		try {
			
			byte[] archivoByteArray = FileUtil.decodeBase64ToByteArray(subirArchivoRestDTO.getArchivo(),
					parametroService.getParametroPorID(Constantes.ID_PARAM_ENCODE_CHARACTER_TRANSFORMATION_FEA)
							.getValorParametroChar());			
						
			sgdpMultipartFile.setBytes(archivoByteArray);
			sgdpMultipartFile.setContentType(subirArchivoRestDTO.getContentType());
			sgdpMultipartFile.setName(URLEncoder.encode(subirArchivoRestDTO.getName()));
			sgdpMultipartFile.setOriginalFilename(URLEncoder.encode(subirArchivoRestDTO.getName()));
			
			subirArhivoDTO.setArchivo(sgdpMultipartFile);
			subirArhivoDTO.setCdr(subirArchivoRestDTO.getCdr());
			subirArhivoDTO.setNumeroDocumento(subirArchivoRestDTO.getNumeroDocumento());
			subirArhivoDTO.setFechaCreacionArchivo(subirArchivoRestDTO.getFechaCreacionArchivo());
			subirArhivoDTO.setIdAutorSubirDocumento(subirArchivoRestDTO.getIdAutorSubirDocumento());
			subirArhivoDTO.setOtro(subirArchivoRestDTO.getOtro());
			subirArhivoDTO.setIdTipoDeDocumentoSubir(subirArchivoRestDTO.getIdTipoDeDocumentoSubir());
			subirArhivoDTO.setIdTags(subirArchivoRestDTO.getIdTags());
			subirArhivoDTO.setDescripcion(subirArchivoRestDTO.getDescripcion());
			subirArhivoDTO.setIdExpedienteSubirArchivo(subirArchivoRestDTO.getIdExpedienteSubirArchivo());
			subirArhivoDTO.setIdArchivoEnCMS(subirArchivoRestDTO.getIdArchivoEnCMS());
			subirArhivoDTO.setResultadoSubirArchivo(subirArchivoRestDTO.getResultadoSubirArchivo());
			subirArhivoDTO.setCssStatus(subirArchivoRestDTO.getCssStatus());
			subirArhivoDTO.setIdInstanciaDeTareaSubirArchivo(subirArchivoRestDTO.getIdInstanciaDeTareaSubirArchivo());
			subirArhivoDTO.setComentario(subirArchivoRestDTO.getComentario());
			subirArhivoDTO.setTipoDeDocumento(subirArchivoRestDTO.getTipoDeDocumento());
			subirArhivoDTO.setEmisor(subirArchivoRestDTO.getEmisor());		
			subirArhivoDTO.setCartaRelacionada(subirArchivoRestDTO.getCartaRelacionada());
			subirArhivoDTO.setMarcaSubirDocTransversal(subirArchivoRestDTO.getMarcaSubirDocTransversal());
			
			usuario.setIdUsuario(subirArchivoRestDTO.getIdUsuario());

			logger.info("Termino de setear los valores");

			usuario.setAlfTicket(
					autenticacionService.login(subirArchivoRestDTO.getIdUsuario()));
			
			List<InstanciaDeTarea> respuestaInstanciasDeTareasPorIdExpediente = null;
			
			if (subeArchivoTareaType.getNombreTipoTareaSubeArch().equals(SubeArchivoTareaType.PRIMERA.getNombreTipoTareaSubeArch())) {
				respuestaInstanciasDeTareasPorIdExpediente = instanciaDeTareaDao.getInstanciasDeTareasOrdenUnoPorIdExpedienteAsignadas(subirArchivoRestDTO.getIdExpedienteSubirArchivo());
			} else if (subeArchivoTareaType.getNombreTipoTareaSubeArch().equals(SubeArchivoTareaType.ASIGNADAS.getNombreTipoTareaSubeArch())) {
				respuestaInstanciasDeTareasPorIdExpediente = instanciaDeTareaDao.getInstanciasDeTareasPorIdExpedienteAsignadas(subirArchivoRestDTO.getIdExpedienteSubirArchivo());
			} else if (subeArchivoTareaType.getNombreTipoTareaSubeArch().equals(SubeArchivoTareaType.ASIGNADAS_EN_ESPERA.getNombreTipoTareaSubeArch())) {
				respuestaInstanciasDeTareasPorIdExpediente = instanciaDeTareaDao.getInstanciasDeTareasPorIdExpedienteAsignadasEnEspera(subirArchivoRestDTO.getIdExpedienteSubirArchivo());
			}
						
			if (respuestaInstanciasDeTareasPorIdExpediente!=null && !respuestaInstanciasDeTareasPorIdExpediente.isEmpty()) {
				
				for (InstanciaDeTarea instanciaDeTarea : respuestaInstanciasDeTareasPorIdExpediente) {
					logger.debug(instanciaDeTarea.toString());
					subirArhivoDTO.setIdInstanciaDeTareaSubirArchivo(instanciaDeTarea.getIdInstanciaDeTarea());
					subirArhivoDTO.setIdUsuarioSube(instanciaDeTarea.getUsuariosAsignados().get(0).getId().getIdUsuario());
					logger.debug(subirArhivoDTO.toString());
					validacionesSubirArchivo(subirArhivoDTO);
					subirArchivoService.subirArchivo(usuario, subirArhivoDTO);
				}
				
				detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario,
						subirArhivoDTO.getIdArchivoEnCMS());
				
				respuestaSubirArchivoDTO.setIdArchivo(subirArhivoDTO.getIdArchivoEnCMS());
				
				if (subirArhivoDTO.getIdArchivoEnCMS()!=null) {
					respuestaSubirArchivoDTO.setMensaje(configProps.getProperty("respuestaOK"));
				} else {
					respuestaSubirArchivoDTO.setMensaje(configProps.getProperty("respuestaError"));
				}
				
				respuestaSubirArchivoDTO.setLink(detalleDeArchivoDTO.getLinkDescargaNavegador());
				
				logger.debug("Retornando desde subirArchivo");
				
				return respuestaSubirArchivoDTO;
				
			} else {
				throw new SgdpException("No Existen Instancias de Tareas donde subir el archivo");
			}			
			
		} catch (UnsupportedEncodingException e) {		
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			throw new SgdpException("Error de ecoding: " + e.getMessage());
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			throw new SgdpException("Error IOException: " + e.getMessage());
		} catch (SgdpException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			throw e;
		}  catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			throw new SgdpException("Error no especificado: " + e.getMessage());
		} finally {
			if (usuario.getAlfTicket() != null) {
				autenticacionService.logout(usuario.getAlfTicket());
			}
		}
		
	}

	@Override
	public RespuestaCambiaEstado avanzarEstado(AvanzaEstadoRestDTO avanzaEstadoRestDTO) throws SgdpException {
		
		logger.debug(avanzaEstadoRestDTO.toString());
		
		String respuesta = null;

		RespuestaCambiaEstado respuestaCambiaEstado = new RespuestaCambiaEstado();

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(avanzaEstadoRestDTO.getEmisor());		
		

		ObjectMapper mapper = SingleObjectFactory.getMapper();

		InstanciaDeTareaDTO instanciaDeTareaDTOR = new InstanciaDeTareaDTO();
		
		logger.info("Entro");
		// para avanzar tarea
		List<InstanciaDeTareaDTO> instanciasDeTareasDTOContinuanProceso = new ArrayList<InstanciaDeTareaDTO>();

		// Obtiene todas las instancias de tarea del proceso que estan en estado
		// asignaada
		
		try {
			
			usuario.setAlfTicket(
					autenticacionService.login(avanzaEstadoRestDTO.getEmisor()));
			
			List<InstanciaDeTarea> instanciasDeTareasPorIdExpediente;
			
			if (avanzaEstadoRestDTO.getTareasEnEspera()==true) {
				instanciasDeTareasPorIdExpediente = instanciaDeTareaDao.getInstanciasDeTareasPorIdExpedienteAsignadasEnEspera(avanzaEstadoRestDTO.getIdExpediente());
			} else {
				instanciasDeTareasPorIdExpediente = instanciaDeTareaDao.getInstanciasDeTareasOrdenUnoPorIdExpedienteAsignadas(avanzaEstadoRestDTO.getIdExpediente());				
			} 
			
			logger.debug("instanciasDeTareasPorIdExpediente.size(): " + instanciasDeTareasPorIdExpediente.size());

			for (InstanciaDeTarea instanciaDeTarea : instanciasDeTareasPorIdExpediente) {
				ContinuarProcesoDTO continuarProcesoDTO = new ContinuarProcesoDTO();
				continuarProcesoDTO.setComentario(avanzaEstadoRestDTO.getComentario());
				continuarProcesoDTO.setIdExpedienteContinuarProceso(avanzaEstadoRestDTO.getIdExpediente());
				continuarProcesoDTO.setIdInstanciaDeTareaOrigen(instanciaDeTarea.getIdInstanciaDeTarea());
				continuarProcesoDTO.setIdUsuarioMueve(avanzaEstadoRestDTO.getIdUsuarioMueve());
				instanciaDeTareaService.cargaInstanciaDeTareaPorIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea(), instanciaDeTareaDTOR);							
				List<InstanciaDeTareaDTO> instanciasDeTareasDTOQueContinuanProceso = mueveProcesoService
						.getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(usuario,
								instanciaDeTareaDTOR, instanciasDeTareasDTOContinuanProceso);				
				List<AsignacionTareaDTO> asignacionesTareasDTO = new ArrayList<AsignacionTareaDTO>();
				for (InstanciaDeTareaDTO instanciaDeTareaDTO : instanciasDeTareasDTOQueContinuanProceso) {
					AsignacionTareaDTO asignacionTareaDTO = new AsignacionTareaDTO();
					asignacionTareaDTO.setIdInstanciaDeTarea(instanciaDeTareaDTO.getIdInstanciaDeTarea());
					if (avanzaEstadoRestDTO.getReceptor()!=null && !avanzaEstadoRestDTO.getReceptor().equals("null") && !avanzaEstadoRestDTO.getReceptor().isEmpty()) {
						logger.debug("cambioEstadoRestDTO.getReceptor(): " + avanzaEstadoRestDTO.getReceptor());
						asignacionTareaDTO.setUsuariosAsignados(avanzaEstadoRestDTO.getReceptor());
					} else {		
						logger.debug("getPrimerUsuarioResponsabilidadDTOPorIdInstanciaDeTarea");
						asignacionTareaDTO.setUsuariosAsignados(usuarioResponsabilidadService.getPrimerUsuarioResponsabilidadDTOPorIdInstanciaDeTarea(instanciaDeTareaDTO.getIdInstanciaDeTarea()).getIdUsuario());
					}					
					asignacionesTareasDTO.add(asignacionTareaDTO);
				}
				
				continuarProcesoDTO.setAsignacionesTareasJSON(mapper.writeValueAsString(asignacionesTareasDTO));
				
				logger.debug(continuarProcesoDTO.toString());
				
				if (instanciaDeTarea.getTarea().getTipoDeBifurcacion()==null || instanciaDeTarea.getTarea().getTipoDeBifurcacion().isEmpty() || 
						instanciaDeTarea.getTarea().getTipoDeBifurcacion().equalsIgnoreCase(BifurcacionType.AND.getNombreTipoDeBifurcacion())) {
					respuesta = mueveProcesoService.avanzaProceso(continuarProcesoDTO, usuario);
					logger.info(respuesta);
				} else {
					logger.info("No avanza proceso por tipo de bifurcacion OR");
				}

				
				
				instanciasDeTareasDTOContinuanProceso.clear();
			}

			// Se valida que se encuentre ID Asignados
			// Se genera un lista de los id de instancia de tarea separados por
			// coma(Son de los proximos porque se cambio el estado)

			String listaIdInstanciaDeTarea = "";

			if (instanciasDeTareasPorIdExpediente.size() != 0) {
				logger.info("Es distinto de 0");
				List<InstanciaDeTarea> RespuestaInstanciasDeTareasPorIdExpediente = instanciaDeTareaDao
						.getInstanciasDeTareasPorIdExpedienteAsignadas(avanzaEstadoRestDTO.getIdExpediente());

				for (InstanciaDeTarea instanciaDeTarea : RespuestaInstanciasDeTareasPorIdExpediente) {

					if (listaIdInstanciaDeTarea == "") {
						listaIdInstanciaDeTarea = "" + instanciaDeTarea.getIdInstanciaDeTarea();
					} else {
						listaIdInstanciaDeTarea = listaIdInstanciaDeTarea + "," + instanciaDeTarea.getIdInstanciaDeTarea();
					}
				}

			} else {
				respuestaCambiaEstado.setDescripcionError("No se encuentra ninguna tarea asignada al usuario");
				respuestaCambiaEstado.setMensaje("error");
				return respuestaCambiaEstado;
			}
			
			respuestaCambiaEstado.setListaIdInstanciaDeTarea(listaIdInstanciaDeTarea);
			respuestaCambiaEstado.setMensaje(configProps.getProperty("respuestaOK"));
			return respuestaCambiaEstado;		
			
		} catch  (IOException e) {			
			logger.error("AvanzarRetrocederEstado " + e.getMessage());
			throw new SgdpException();
		} catch (SgdpException e) {		
			logger.error("AvanzarRetrocederEstado " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("AvanzarRetrocederEstado " + e.getMessage());
			throw new SgdpException();
		} finally {
			if (usuario.getAlfTicket() != null) {
				autenticacionService.logout(usuario.getAlfTicket());
			}
		}		
		
	}
	
	@Override
	public RespuestaCambiaEstado retrocederEstado(RetrocedeEstadoDTO retrocedeEstadoDTO) {

		RespuestaCambiaEstado respuestaCambiaEstado = new RespuestaCambiaEstado();
		RetrocedeProcesoDTO retrocedeProcesoDTO = new RetrocedeProcesoDTO();
		Usuario usuario = new Usuario();
		String mensaje = "";
		retrocedeProcesoDTO.setIdInstanciaDeTareaSeleccionada(retrocedeEstadoDTO.getIdInstanciaDeTareaSeleccionada());
		retrocedeProcesoDTO.setNombreDeTarea(retrocedeEstadoDTO.getNombreDeTarea());
		retrocedeProcesoDTO.setComentario(retrocedeEstadoDTO.getComentario());
		usuario.setIdUsuario(retrocedeEstadoDTO.getUsuario());

		try {
			usuario.setAlfTicket(
					autenticacionService.login(retrocedeEstadoDTO.getUsuario()));
			mensaje = mueveProcesoService.retrocedeProceso(retrocedeProcesoDTO, usuario);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			respuestaCambiaEstado.setDescripcionError(e.getMessage());
			respuestaCambiaEstado.setMensaje("Error");
			return respuestaCambiaEstado;
		} finally {
			if (usuario.getAlfTicket() != null) {
				autenticacionService.logout(usuario.getAlfTicket());
			}
		}

		respuestaCambiaEstado.setMensaje("OK");
		respuestaCambiaEstado
				.setListaIdInstanciaDeTarea(retrocedeProcesoDTO.getIdInstanciaDeTareaSeleccionadaActualSalida());
		return respuestaCambiaEstado;

	}

	@Override
	public List<RespuestaConsultaBasicaEstadoProceso> consultaBasicaEstadoProceso(
			ConsultaEstadoProceso consultaEstadoProceso) {
		// Se genera el objeto para probar el servicio

		return instanciaDeProcesoDao.ConsultaBasicaEstadoProceso(consultaEstadoProceso);
	}

	@Override
	public List<RespuestaConsultaAvanzadaEstadoProceso> consultaAvanzadaEstadoProceso(
			ConsultaEstadoProceso consultaEstadoProceso) {
		// Se genera el objeto para probar el servicio
		return instanciaDeProcesoDao.ConsultaAvanzadaEstadoProceso(consultaEstadoProceso);
	}
	
	@Override
	public AnulacionProcesoResponse anularProcesoPorIdExpediente(Usuario usuario, AnulacionProcesoRequest anulacionProcesoRequest) {
		AnulacionDTO anulacionDTO = new AnulacionDTO();
		AnulacionProcesoResponse anulacionProcesoResponse = new AnulacionProcesoResponse();
		try {
			usuario.setAlfTicket(autenticacionService.login(anulacionProcesoRequest.getIdUsuario()));	
			logger.debug("creo el ticket");
			anulacionDTO.setIdExpediente(anulacionProcesoRequest.getIdExpediente());
			anulacionDTO.setMotivoAnulacion(anulacionProcesoRequest.getMotivoAnulacion());
			anulacionDTO.setIdUsuarioAnula(anulacionProcesoRequest.getIdUsuarioAnula());
			mueveProcesoService.anularProcesoPorIdExpediente(usuario, anulacionDTO);
			anulacionProcesoResponse.setMensajeRespuesta(anulacionDTO.getRespuestaAnulacion());
		} catch (Exception e) {		
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			anulacionProcesoResponse.setMensajeRespuesta(configProps.getProperty("respuestaError"));
		} finally {
			if (usuario.getAlfTicket() != null) {
				autenticacionService.logout(usuario.getAlfTicket());
			}
		}
		return anulacionProcesoResponse;
	}

	@Override
	public AgregaORemueveTagDeObjetoResponse agregaRemueveTagDeObjeto(
			AgregaORemueveTagDeObjetoRequest agregaORemueveTagDeObjetoRequest) throws SgdpException {	
		Usuario usuario = new Usuario();
		AgregaORemueveTagDeObjetoResponse agregaORemueveTagDeObjetoResponse = new AgregaORemueveTagDeObjetoResponse();
		try {
			usuario.setIdUsuario(agregaORemueveTagDeObjetoRequest.getIdUsuario());
			usuario.setAlfTicket(autenticacionService.login(agregaORemueveTagDeObjetoRequest.getIdUsuario()));	
			logger.debug("creo el ticket");	
			AgregaRemueveTagDeObjetoResponse agregaRemueveTagDeObjetoResponse = gestorDeTagsCMSServiceImpl.agregaRemueveTagDeObjeto
					(usuario, agregaORemueveTagDeObjetoRequest.getIdObjeto(), agregaORemueveTagDeObjetoRequest.getTag(), agregaORemueveTagDeObjetoRequest.getAccion());
			agregaORemueveTagDeObjetoResponse.setMensajeRespuesta(configProps.getProperty("respuestaOK"));
			return agregaORemueveTagDeObjetoResponse;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			throw new SgdpException();
		} finally {
			if (usuario.getAlfTicket() != null) {
				autenticacionService.logout(usuario.getAlfTicket());
			}
		}		
	}
	
	@Override
	public EtapaDeInstanciaDeProcesoResponse getEtapasDeInstanciaDeProcesoPorIdExpediente(EtapaDeInstanciaDeProcesoRequest etapaDeInstanciaDeProcesoRequest) throws SgdpException {		
		EtapaDeInstanciaDeProcesoResponse etapasDeInstanciaDeProcesoResponse = new EtapaDeInstanciaDeProcesoResponse();		
		Usuario usuario = new Usuario();
		try {
			usuario.setIdUsuario(etapaDeInstanciaDeProcesoRequest.getIdUsuario());
			usuario.setAlfTicket(autenticacionService.login(etapaDeInstanciaDeProcesoRequest.getIdUsuario()));	
			logger.debug("creo el ticket");			
			List<EtapaDeInstanciaDeProcesoDTO> etapasDeInstanciaDeProcesoDTO = instanciaDeProcesoService.getEtapasDeInstanciaDeProcesoPorIdExpediente(etapaDeInstanciaDeProcesoRequest.getIdExpediente());
			etapasDeInstanciaDeProcesoResponse.setCodigoRespuesta(configProps.getProperty("respuestaOK"));
			etapasDeInstanciaDeProcesoResponse.setEtapasDeInstanciaDeProcesoDTO(etapasDeInstanciaDeProcesoDTO);
		} catch (Exception e) {		
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			throw new SgdpException();
		} finally {
			if (usuario.getAlfTicket() != null) {
				autenticacionService.logout(usuario.getAlfTicket());
			}
		}			
		return etapasDeInstanciaDeProcesoResponse;
	}
	
	@Override
	public HistoricoDeInstDeTareaResponse getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(HistoricoDeInstDeTareaRequest historicoDeInstDeTareaRequest) {		
		HistoricoDeInstDeTareaResponse historicoDeInstDeTareaResponse = new HistoricoDeInstDeTareaResponse();
		Usuario usuario = new Usuario();
		try {
			usuario.setIdUsuario(historicoDeInstDeTareaRequest.getIdUsuario());
			usuario.setAlfTicket(autenticacionService.login(historicoDeInstDeTareaRequest.getIdUsuario()));	
			logger.debug("creo el ticket");
			List<cl.gob.scj.sgdp.dto.HistoricoDeInstDeTareaDTO> historicoDeInstDeTareasDTO = historicoDeInstDeTareaService.getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(historicoDeInstDeTareaRequest.getIdExpediente());
			List<HistoricoDeInstDeTareaDTO> historicoDeInstDeTareasDTORest = new ArrayList<HistoricoDeInstDeTareaDTO>();
			for (cl.gob.scj.sgdp.dto.HistoricoDeInstDeTareaDTO historicoDeInstDeTareaDTO : historicoDeInstDeTareasDTO) {
				HistoricoDeInstDeTareaDTO HistoricoDeInstDeTareaDTORest = new HistoricoDeInstDeTareaDTO();
				BeanUtils.copyProperties(historicoDeInstDeTareaDTO, HistoricoDeInstDeTareaDTORest);
				historicoDeInstDeTareasDTORest.add(HistoricoDeInstDeTareaDTORest);
			}
			historicoDeInstDeTareaResponse.setHistoricoDeInstDeTareasDTO(historicoDeInstDeTareasDTORest);			
			historicoDeInstDeTareaResponse.setMensajeRespuesta(configProps.getProperty("respuestaOK"));					
		} catch (Exception e) {		
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			historicoDeInstDeTareaResponse.setMensajeRespuesta(configProps.getProperty("respuestaError"));
		} finally {
			if (usuario.getAlfTicket() != null) {
				autenticacionService.logout(usuario.getAlfTicket());
			}
		}
		return historicoDeInstDeTareaResponse;	
	}

	@Override
	public RespuestaTipoDocumentoPrimeraTareaDTO buscarTipoDeDocumentoPrimeraTareaPorCodigoProceso(String codigoProceso) {
		
		
		RespuestaTipoDocumentoPrimeraTareaDTO respuestaTipoDocumentoPrimeraTareaDTO = new RespuestaTipoDocumentoPrimeraTareaDTO();
		try {
			List<TipoDeDocumentoDTO> TipoDeDocumentoDTOSalida =new  ArrayList<TipoDeDocumentoDTO>();
			
			List<TipoDeDocumento> listaTipoDeDocumento = tipoDeDocumentoDao.buscarTipoDeDocumentoPrimeraTareaPorCodigoProceso(codigoProceso);

			for (TipoDeDocumento tipoDeDocumento : listaTipoDeDocumento) {
				TipoDeDocumentoDTO tipoDeDocumentoDTO = new TipoDeDocumentoDTO();
				tipoDeDocumentoDTO.setIdTipoDeDocumento(tipoDeDocumento.getIdTipoDeDocumento());
				tipoDeDocumentoDTO.setNombreDeTipoDeDocumento(tipoDeDocumento.getNombreDeTipoDeDocumento());
				tipoDeDocumentoDTO.setAplicaFEA(tipoDeDocumento.getAplicaFEA());
				tipoDeDocumentoDTO.setAplicaVisacion(tipoDeDocumento.getAplicaVisacion());
				tipoDeDocumentoDTO.setConformaExpediente(tipoDeDocumento.getConformaExpediente());
				tipoDeDocumentoDTO.setEsDocumentoConductor(tipoDeDocumento.getEsDocumentoConductor());
				
				TipoDeDocumentoDTOSalida.add(tipoDeDocumentoDTO);
			}
		
			
		if 	(listaTipoDeDocumento.size() == 0){
			respuestaTipoDocumentoPrimeraTareaDTO.setMensaje("Sin Registros");
		}else{
			respuestaTipoDocumentoPrimeraTareaDTO.setListaTipoDeDocumento(TipoDeDocumentoDTOSalida);
			respuestaTipoDocumentoPrimeraTareaDTO.setMensaje("OK");
		}	
			
			return respuestaTipoDocumentoPrimeraTareaDTO;
		} catch (Exception e) {
			respuestaTipoDocumentoPrimeraTareaDTO.setMensaje("ERROR al obtener la primera Tarea");
			logger.debug("error buscarTipoDeDocumentoPrimeraTareaPorCodigoProceso " + e.getMessage());
			return respuestaTipoDocumentoPrimeraTareaDTO;
		}
	
	}
	
	@Override
	public ObtenerTodosLosProcesosResponse getBuscarTodosProcesosPorVigencia(boolean vigente) {
		ObtenerTodosLosProcesosResponse obtenerTodosLosProcesosVigentesResponse = new ObtenerTodosLosProcesosResponse();
		List<ProcesoDTO> listaProcesotDTO = procesoService.getBuscarTodosProcesosPorVigencia(vigente);
		List<ProcesoRestDTO> listaProcesoRestDTO = new ArrayList<ProcesoRestDTO>();
		for (ProcesoDTO procesoDTO : listaProcesotDTO) {
			ProcesoRestDTO procesoRestDTO = new ProcesoRestDTO();
			BeanUtils.copyProperties(procesoDTO, procesoRestDTO);
			listaProcesoRestDTO.add(procesoRestDTO);
		}
		obtenerTodosLosProcesosVigentesResponse.setListaProcesoRestDTO(listaProcesoRestDTO);
		obtenerTodosLosProcesosVigentesResponse.setMensaje("OK");
		return obtenerTodosLosProcesosVigentesResponse;
	}

	@Override
	public ObtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse getTiposDeDocumentosPorCodigoProceso(String codigoProceso) {
		ObtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse obtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse = new ObtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse();
		List<cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO> tiposDeDocumentosPorCodigoProceso = tipoDeDocumentoService.getTiposDeDocumentosPorCodigoProceso(codigoProceso);
		List<cl.gob.scj.sgdp.dto.rest.TipoDeDocumentoDTO> listaTipoDeDocumentoRestDTO = new ArrayList<cl.gob.scj.sgdp.dto.rest.TipoDeDocumentoDTO>();
		for (cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO tipoDeDocumentoDTO : tiposDeDocumentosPorCodigoProceso) {
			cl.gob.scj.sgdp.dto.rest.TipoDeDocumentoDTO tipoDeDocumentoRestDTO = new cl.gob.scj.sgdp.dto.rest.TipoDeDocumentoDTO();
			BeanUtils.copyProperties(tipoDeDocumentoDTO, tipoDeDocumentoRestDTO);
			listaTipoDeDocumentoRestDTO.add(tipoDeDocumentoRestDTO);
		}
		obtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse.setListaTipoDeDocumento(listaTipoDeDocumentoRestDTO);
		obtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse.setMensaje("OK");
		return obtenerTodosLosTiposDeDocumentosPorCodigoProcesoResponse;
	}

	@Override
	public ObtenerTodasLasTareasPorCodigoProcesoResponse getTareasPorCodigoProceso(String codigoProceso) {
		ObtenerTodasLasTareasPorCodigoProcesoResponse obtenerTodasLasTareasPorCodigoProcesoResponse = new ObtenerTodasLasTareasPorCodigoProcesoResponse();
		List<TareaDTO> listaTareasPorCodigoProceso = bandejaDeEntradaService.getTareasPorCodigoProceso(codigoProceso);
		List<TareaRestDTO> listaTareasRestPorCodigoProceso = new ArrayList<TareaRestDTO>();
		for (TareaDTO tareaDTO : listaTareasPorCodigoProceso) {
			TareaRestDTO tareaRestDTO = new TareaRestDTO();
			BeanUtils.copyProperties(tareaDTO, tareaRestDTO);
			listaTareasRestPorCodigoProceso.add(tareaRestDTO);
		}
		obtenerTodasLasTareasPorCodigoProcesoResponse.setListaTareasRestDTO(listaTareasRestPorCodigoProceso);
		obtenerTodasLasTareasPorCodigoProcesoResponse.setMensaje("OK");
		return obtenerTodasLasTareasPorCodigoProcesoResponse;
	}
	
	@Override
	public ObtenerTodasLasTareasPorIdProcesoResponse getTareasPorIdProceso(long idProceso) {
		ObtenerTodasLasTareasPorIdProcesoResponse obtenerTodasLasTareasPorIdProcesoResponse = new ObtenerTodasLasTareasPorIdProcesoResponse();
		List<TareaDTO> listaTareasPorIdProceso = bandejaDeEntradaService.getTareasPorIdProceso(idProceso);
		logger.debug("listaTareasPorIdProceso.size(): " + listaTareasPorIdProceso.size());
		List<TareaRestDTO> listaTareasRestPorIdProceso = new ArrayList<TareaRestDTO>();
		for (TareaDTO tareaDTO : listaTareasPorIdProceso) {
			TareaRestDTO tareaRestDTO = new TareaRestDTO();
			BeanUtils.copyProperties(tareaDTO, tareaRestDTO);
			listaTareasRestPorIdProceso.add(tareaRestDTO);
		}
		obtenerTodasLasTareasPorIdProcesoResponse.setListaTareasRestDTO(listaTareasRestPorIdProceso);
		obtenerTodasLasTareasPorIdProcesoResponse.setMensaje("OK");
		logger.info(obtenerTodasLasTareasPorIdProcesoResponse.toString());		
		return obtenerTodasLasTareasPorIdProcesoResponse;
	}

	
	@Override
	public ObtenerTodosLosTiposDeDocumentosPorIdTareaResponse getTiposDeDocumentosPorIdTarea(long idTarea) {
		ObtenerTodosLosTiposDeDocumentosPorIdTareaResponse obtenerTodosLosTiposDeDocumentosPorIdTareaResponse = new ObtenerTodosLosTiposDeDocumentosPorIdTareaResponse();
		List<cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO> tiposDeDocumentoDTO = bandejaDeEntradaService.getTodosLosTiposDeDocumentosPorIdTarea(idTarea);
		List<TipoDeDocumentoDTO> tiposDeDocumentoDTORest = new ArrayList<TipoDeDocumentoDTO>();
		for (cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO td : tiposDeDocumentoDTO) {
			TipoDeDocumentoDTO tipoDeDocumentoDTORest = new TipoDeDocumentoDTO();
			BeanUtils.copyProperties(td, tipoDeDocumentoDTORest);
			tiposDeDocumentoDTORest.add(tipoDeDocumentoDTORest);
		}
		obtenerTodosLosTiposDeDocumentosPorIdTareaResponse.setListaTipoDeDocumento(tiposDeDocumentoDTORest);
		obtenerTodosLosTiposDeDocumentosPorIdTareaResponse.setMensaje("OK");
		return obtenerTodosLosTiposDeDocumentosPorIdTareaResponse;
	}
	
	@Override
	public ObtenerTodosLosProcesosResponse getTodosProcesos() {
		ObtenerTodosLosProcesosResponse obtenerTodosLosProcesosVigentesResponse = new ObtenerTodosLosProcesosResponse();
		List<ProcesoDTO> listaProcesotDTO = procesoService.getTodosProcesos();
		List<ProcesoRestDTO> listaProcesoRestDTO = new ArrayList<ProcesoRestDTO>();
		for (ProcesoDTO procesoDTO : listaProcesotDTO) {
			ProcesoRestDTO procesoRestDTO = new ProcesoRestDTO();
			BeanUtils.copyProperties(procesoDTO, procesoRestDTO);
			listaProcesoRestDTO.add(procesoRestDTO);
		}
		obtenerTodosLosProcesosVigentesResponse.setListaProcesoRestDTO(listaProcesoRestDTO);
		obtenerTodosLosProcesosVigentesResponse.setMensaje("OK");
		return obtenerTodosLosProcesosVigentesResponse;
	}
	
	@Override
	public ObtenerTodasLasInstDeTareasAsigPorIdExpResponse obtenerTodasLasInstDeTareasAsigPorIdExp(String idExpediente) {
		ObtenerTodasLasInstDeTareasAsigPorIdExpResponse obtenerTodasLasInstDeTareasAsigPorIdExpResponse = new ObtenerTodasLasInstDeTareasAsigPorIdExpResponse();
		List<InstanciaDeTareaDTORest> listaInstanciaDeTareaDTORest = new ArrayList<InstanciaDeTareaDTORest>();
		List<InstanciaDeTarea> instanciasDeTareasPorIdExpedienteAsignadas = instanciaDeTareaDao.getInstanciasDeTareasPorIdExpedienteAsignadas(idExpediente);
		for (InstanciaDeTarea instanciaDeTarea : instanciasDeTareasPorIdExpedienteAsignadas) {
			InstanciaDeTareaDTORest instanciaDeTareaDTORest = new InstanciaDeTareaDTORest();
			instanciaDeTareaDTORest.setFechaVencimientoInstanciaDeTarea(instanciaDeTarea.getFechaVencimiento());
			instanciaDeTareaDTORest.setIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea());
			instanciaDeTareaDTORest.setNombreDeProceso(instanciaDeTarea.getInstanciaDeProceso().getProceso().getNombreProceso());
			instanciaDeTareaDTORest.setNombreDeTarea(instanciaDeTarea.getTarea().getNombreTarea());
			instanciaDeTareaDTORest.setNombreExpediente(instanciaDeTarea.getInstanciaDeProceso().getNombreExpediente());
			List<UsuarioAsignado> usuariosAsignados = instanciaDeTarea.getUsuariosAsignados();
			List<String> idUsuariosAsignados = new ArrayList<String>();
			for (UsuarioAsignado usuarioAsignado : usuariosAsignados) {
				idUsuariosAsignados.add(usuarioAsignado.getId().getIdUsuario());
			}
			instanciaDeTareaDTORest.setIdUsuariosAsignados(idUsuariosAsignados);	
			listaInstanciaDeTareaDTORest.add(instanciaDeTareaDTORest);
		}	
		obtenerTodasLasInstDeTareasAsigPorIdExpResponse.setListaInstanciaDeTareaDTORest(listaInstanciaDeTareaDTORest);
		obtenerTodasLasInstDeTareasAsigPorIdExpResponse.setMensaje("OK");
		return obtenerTodasLasInstDeTareasAsigPorIdExpResponse;		
	}
	
	@Override
	public DocOficialesDeExpResponse getDocOficialesDeExpediente(String idExpediente, String idUsuario) throws SgdpException {
		DocOficialesDeExpResponse docOficialesDeExpResponse = new DocOficialesDeExpResponse();
		List<DetalleDeArchivoDTORest> detalleDeDocOficiales = new ArrayList<DetalleDeArchivoDTORest>();
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(idUsuario);
		List<ArchivoInfoDTO> archivosExpedienteDTO = obtenerArchivosExpedienteService.obtenerArchivosExpediente(usuario, idExpediente, false, false, false, 0);
		for (ArchivoInfoDTO archivoInfoDTO : archivosExpedienteDTO) {			
			if (archivoInfoDTO.getEsDocumentoOficial().equals("true")) {
				logger.debug(archivoInfoDTO.toString());
				DetalleDeArchivoDTORest detalleDeArchivoDTORest = new DetalleDeArchivoDTORest();
				BeanUtils.copyProperties(archivoInfoDTO, detalleDeArchivoDTORest);
				detalleDeArchivoDTORest.setFechaDeDocumento(archivoInfoDTO.getFechaCreacion());
				detalleDeDocOficiales.add(detalleDeArchivoDTORest);
			}			
		}
		docOficialesDeExpResponse.setDetalleDeDocOficiales(detalleDeDocOficiales);
		return docOficialesDeExpResponse;		
	}
	
}