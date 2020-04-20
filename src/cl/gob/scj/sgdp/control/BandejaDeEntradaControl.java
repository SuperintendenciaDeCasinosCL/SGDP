package cl.gob.scj.sgdp.control;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.AnadirAntecedenteDTO;
import cl.gob.scj.sgdp.dto.AutorDTO;
import cl.gob.scj.sgdp.dto.ExpedienteDTO;
import cl.gob.scj.sgdp.dto.FiltroExpedienteDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.KeyParametroPorContextoDTO;
import cl.gob.scj.sgdp.dto.RespuestaMailDTO;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.dto.SuggestionsDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.dto.UsuarioRolDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.exception.SgdpExceptionValidaTareaEnBE;
import cl.gob.scj.sgdp.service.BandejaDeEntradaService;
import cl.gob.scj.sgdp.service.CrearExpedienteService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.ProcesoService;
import cl.gob.scj.sgdp.service.SubirArchivoService;
import cl.gob.scj.sgdp.service.TipoDeDocumentoService;
import cl.gob.scj.sgdp.service.UsuarioRolService;
import cl.gob.scj.sgdp.tipos.EstadoDeTareaType;
import cl.gob.scj.sgdp.tipos.PermisoType;
import cl.gob.scj.sgdp.util.SingleObjectFactory;

@Controller
public class BandejaDeEntradaControl {		

	private static final Logger log = Logger.getLogger(BandejaDeEntradaControl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private BandejaDeEntradaService bandejaDeEntradaService;
	
	@Autowired
	private TipoDeDocumentoService tipoDeDocumentoService;
	
	private List<TipoDeDocumentoDTO> tiposDeDocumentosDTO;	
	
	private EstadoDeTareaType estadoDeTareaAsignadaType = EstadoDeTareaType.ASIGNADA;
		
	private PermisoType permisoMuestraTareasEnEjecucionType = PermisoType.PUEDE_VER_TAREAS_EN_EJECUCION;
	
	@Autowired
	private CrearExpedienteService crearExpedienteService;
	
	@Autowired
	private ParametroService parametroService;

	@Autowired
	private SubirArchivoService subirArchivoService;
	
	@Autowired
	private UsuarioRolService usuarioRolService;
	
	@Autowired
	private ProcesoService procesoService;
	
	@RequestMapping("/bandejaDeEntrada")	
	public String cargaBandejaDeEntrada(Model model, HttpServletRequest request) {
					
		try { 
			
			KeyParametroPorContextoDTO keyParametroPorContextoDTOMuestraTareasEnEjecucion = new KeyParametroPorContextoDTO();
			keyParametroPorContextoDTOMuestraTareasEnEjecucion.setNombreParametro(Constantes.NOMBRE_PARAMETRO_POR_CONTEXTO_MUESTRA_TAREAS_EN_EJECUCION_POR_ID_ROL);				
		
			boolean tareaEnEspera = false;
			boolean trabajoInterno = false;
			
			try {
				
				tareaEnEspera = (boolean) request.getSession().getAttribute("tareaEnEspera");
				trabajoInterno = (boolean) request.getSession().getAttribute("trabajoInterno");

				model.addAttribute("tareaEnEspera", tareaEnEspera);
				model.addAttribute("trabajoInterno", trabajoInterno);

			} catch (Exception e1) {
				tareaEnEspera = false;
				trabajoInterno = false;
			}
		
			FiltroExpedienteDTO filtroExpedienteDTO = new FiltroExpedienteDTO();
			filtroExpedienteDTO.setRespuestaEspera(tareaEnEspera);
			filtroExpedienteDTO.setTrabajoInterno(trabajoInterno);
			
			log.debug("Inicio Carga Bandeja de Entrada");			
			
			Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
			
			model.addAttribute("listaProcesoDto", procesoService.getProcesosPorVigencia(usuario, true));
			
			model.addAttribute("listaMacroProcesosDTO", crearExpedienteService.getMacroProcesosDTO(usuario));
			
			log.debug("usuario.toString(): " + usuario.toString());	
						
			log.debug("estadoDeTareaType.getCodigoEstadoDeTarea(): " + estadoDeTareaAsignadaType.getCodigoEstadoDeTarea());
			
			keyParametroPorContextoDTOMuestraTareasEnEjecucion.setValorContexto(Long.toString(usuario.getRolDTO().getIdRol()));
			
			log.debug(keyParametroPorContextoDTOMuestraTareasEnEjecucion);
			
			List<InstanciaDeTareaDTO> instanciasDeTareasDTO = new ArrayList<InstanciaDeTareaDTO>();
			//List<InstanciaDeTareaDTO> instanciasDeTareasDTOEnEjecucion = new ArrayList<InstanciaDeTareaDTO>();
			
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		
			log.debug("Inicio buscando instanciasDeTareasDTO");				
			instanciasDeTareasDTO = bandejaDeEntradaService.getInstanciasDeTareaPorIdUsrNombreEstadoTareaFiltro(usuario, 
					estadoDeTareaAsignadaType.getNombreEstadoDeTarea(), instanciasDeTareasDTO,filtroExpedienteDTO);		
			log.debug("Fin buscando instanciasDeTareasDTO");			
			
			tiposDeDocumentosDTO = tipoDeDocumentoService.getTodosLosTiposDeDocumentos();
			
			expedienteDTO.setCreador(usuario.getUnidadDTO().getNombreCompletoUnidad());	
			
			List<AutorDTO> autoresDTO = bandejaDeEntradaService.getTodosLosAutores();
			for (AutorDTO autorDTO: autoresDTO) {
				log.debug("autor cargaBandejaDeEntrada: " + autorDTO.toString());
			}
				
			String permisoTareasEnEjecucion = usuario.getPermisos().get(permisoMuestraTareasEnEjecucionType.getNombrePermiso());
			log.debug("permisoTareasEnEjecucion: " + permisoTareasEnEjecucion);
			log.debug("permisoMuestraTareasEnEjecucionType.getNombrePermiso(): " + permisoMuestraTareasEnEjecucionType.getNombrePermiso());
			/*if (permisoTareasEnEjecucion!=null && permisoTareasEnEjecucion.equals(permisoMuestraTareasEnEjecucionType.getNombrePermiso())) {
				log.debug("Buscando parametroPorContextoDTO...");
				log.debug(keyParametroPorContextoDTOMuestraTareasEnEjecucion.toString());
				ParametroPorContextoDTO parametroPorContextoDTO = parametroPorContextoService.getParamPorContexto(keyParametroPorContextoDTOMuestraTareasEnEjecucion);
				log.debug(parametroPorContextoDTO);
				if (parametroPorContextoDTO!=null && parametroPorContextoDTO.getValorParametroChar().equals(Constantes.MUESTRA_TODAS_LAS_TAREAS_EN_EJECUCION)) {
					log.debug("Inicio buscando todas las instanciasDeTareasDTOEnEjecucion");
					instanciasDeTareasDTOEnEjecucion = bandejaDeEntradaService.getTodasInstanciasDeTareasEnEjecucion(instanciasDeTareasDTOEnEjecucion);
					log.debug("Fin buscando todas las instanciasDeTareasDTOEnEjecucion");
				} else if (parametroPorContextoDTO!=null && parametroPorContextoDTO.getValorParametroChar().equals(Constantes.MUESTRA_LAS_TAREAS_DE_LA_UNIDAD)) {
					log.debug("Inicio buscando instanciasDeTareasDTOEnEjecucion por unidad");
					instanciasDeTareasDTOEnEjecucion = bandejaDeEntradaService.getTodasInstanciasDeTareasEnEjecucionPorIdUnidad(usuario.getUnidadDTO().getIdUnidad(), instanciasDeTareasDTOEnEjecucion);
					log.debug("Fin buscando instanciasDeTareasDTOEnEjecucion por unidad");
				}
			}*/
			
			model.addAttribute("instanciasDeTareasDTO", instanciasDeTareasDTO);
			model.addAttribute("autoresDTO", autoresDTO);
			model.addAttribute("permisos", usuario.getPermisos());	
			model.addAttribute("expedienteDTO", expedienteDTO);
			model.addAttribute("tiposDeDocumentosDTO", tiposDeDocumentosDTO);		
			//model.addAttribute("instanciasDeTareasDTOEnEjecucion", instanciasDeTareasDTOEnEjecucion);
			model.addAttribute("urlFuncPhp", parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FUNC_PHP).getValorParametroChar());
			
            // Se genera una lista de los documentos de la primera tarea, para que oficina de parte pueda subir documentos en 
			// cualquier parte del proceso
			
			
			/*List<TipoDeDocumentoDTO> listaTipoDocumentoPrimeraTarea = tipoDeDocumentoService.getTipoDeDocumentoPrimeraTareaDocAdiccionales();
			model.addAttribute("listaTipoDocumentoPrimeraTarea", new Gson().toJson(listaTipoDocumentoPrimeraTarea));*/
			
			request.getSession().setAttribute("cantidadDeTareas", instanciasDeTareasDTO!=null ? instanciasDeTareasDTO.size(): 0);		
			
		} catch (Exception e) {
			log.error("Error...", e);
		} 
		log.debug("Fin Carga Bandeja de Entrada");
		return configProps.getProperty("vistaBandejaDeEntrada");
	}	
		
	@RequestMapping(value="/getUsuariosSugeridos", method=RequestMethod.GET)
	public @ResponseBody List<SuggestionsDTO> getUsuariosSugeridos(@RequestParam("usuariosAsignados") String idUsuarioLike) {			
		List<SuggestionsDTO> listaDeUsuariosSugerida = new ArrayList<SuggestionsDTO>();
		log.debug("idUsuarioLike: " + idUsuarioLike);
		listaDeUsuariosSugerida = bandejaDeEntradaService.getUsuariosSugeridos(idUsuarioLike, listaDeUsuariosSugerida);
		return listaDeUsuariosSugerida;		
	}
	
	@RequestMapping(value="/getCantidadDeTareas", method=RequestMethod.GET)
	public @ResponseBody String getCantidadDeTareas(HttpServletRequest request) {
		return  ((Integer)request.getSession().getAttribute("cantidadDeTareas")).toString();
	}	
	
	@RequestMapping("/cargaVideo")
	public String cargaVideo(Model model, HttpServletRequest request) {	
		String idVideo = request.getParameter("idVideo");
		log.debug("idVideo: " + idVideo);
		model.addAttribute("idVideo", idVideo);
		return configProps.getProperty("vistaVideoManual");  
	}

	/*public Long getIdMacroProceso() {
		return idMacroProceso;
	}

	public void setIdMacroProceso(Long idMacroProceso) {
		this.idMacroProceso = idMacroProceso;
	}*/

	/*public List<TipoDeDocumentoDTO> getTiposDeDocumentosDTO() {
		return tiposDeDocumentosDTO;
	}*/

	/*public void setTiposDeDocumentosDTO(
			List<TipoDeDocumentoDTO> tiposDeDocumentosDTO) {
		this.tiposDeDocumentosDTO = tiposDeDocumentosDTO;
	}*/
	
	@RequestMapping("/tareasBandejaEntrada")
	public String tareasBandejaEntrada() {
		return "div/tareasBandejaDeEntrada";
	}	
	
	@RequestMapping(value="/getTiposDeDocumentosDTO", method=RequestMethod.GET)
	public @ResponseBody List<TipoDeDocumentoDTO> getTiposDeDocumentosDTO() {
		return tipoDeDocumentoService.getTodosLosTiposDeDocumentos();
		//return AppContextControl.getTiposDeDocumentosDTO();
	}
	
	
	
	@RequestMapping(value = "/filtrarExpediente" , method = RequestMethod.POST)
	public @ResponseBody RespuestaMailDTO filtrarExpediente(@RequestBody FiltroExpedienteDTO filtroExpedienteDTO,HttpServletRequest request) {
		log.info("Ejecucion metodo  filtrarExpediente");	
		
		RespuestaMailDTO respuestaMailDTO = new RespuestaMailDTO();
		
		try {
			request.getSession().setAttribute("tareaEnEspera", filtroExpedienteDTO.getRespuestaEspera());        
			request.getSession().setAttribute("trabajoInterno", filtroExpedienteDTO.getTrabajoInterno());
			
			
			respuestaMailDTO.setRespuesta("OK");
			log.debug("Se guardardo la informacion de la focalizacion correctamente");
			return respuestaMailDTO;
			
		} catch (Exception e) {
			log.error("Error al guardar la informacion de la focalizacion");
			log.error("Descripcion error : " + e.getMessage());
			
			respuestaMailDTO.setRespuesta("ERROR");
			respuestaMailDTO.setCodigoError(e.getMessage());
			e.printStackTrace();
			
			return respuestaMailDTO;
		}        

	}
	
	
	@RequestMapping(value = "/guardarAntecedenteTodaEtapa" , method = RequestMethod.POST)
	public @ResponseBody List<RespuestaMailDTO> guardarAntecedenteTodaEtapa( 
			//@RequestParam("listaArchivoFile") MultipartFile listaArchivo, 
			@RequestParam("listaArchivoFile2") List<MultipartFile> listaArchivo2, 
			@RequestParam("anadirAntecedentesTablaString") String anadirAntecedentesTablaString, 
			@RequestParam("anadirAntecedenteDTOString") String anadirAntecedenteDTOString, 
			//@RequestParam("definirVariable") String PlanEstrategicoString ,
			HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
	   					
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		
		anadirAntecedentesTablaString = URLDecoder.decode(anadirAntecedentesTablaString, "UTF-8"); 

		ObjectMapper mapper = SingleObjectFactory.getMapper();
		List<AnadirAntecedenteDTO> listaAntecedenteDTO = mapper.readValue(anadirAntecedentesTablaString, new TypeReference<List<AnadirAntecedenteDTO>>(){});
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");

		AnadirAntecedenteDTO anadirAntecedenteDTOEntrada = mapper.readValue(anadirAntecedenteDTOString, AnadirAntecedenteDTO.class);
		// List<AnadirAntecedenteDTO>  listaAnadirAntecedenteDTOTarea = mapper.readValue(anadirAntecedenteDTOString, new TypeReference<List<AnadirAntecedenteDTO>>(){});
		
		log.info("anadirAntecedentesTablaString" + anadirAntecedentesTablaString );
		
		
	
		// Definir objeto que cargara toda la informacion en alfresco
		
		Set<SubirArhivoDTO> listaSubirArhivoDTO = new HashSet<SubirArhivoDTO>();
		//----------------------------------------------------------------
		
		for (MultipartFile archivo : listaArchivo2) {
						
			for (AnadirAntecedenteDTO anadirAntecedenteDTO : listaAntecedenteDTO) {
			
				
				String nombreArchivoDecodificado = URLDecoder.decode(archivo.getOriginalFilename(), "UTF-8");
				
				log.info(" --------------------------------------------------------- ");
				log.info("Nombre Archivo " + nombreArchivoDecodificado);
				log.info(" --------------------------------------------------------- ");
				
				if (anadirAntecedenteDTO.getNombreDocumento().equals(nombreArchivoDecodificado)){
					 SubirArhivoDTO subirArhivoDTO = new SubirArhivoDTO();
					 subirArhivoDTO.setTipoDeDocumento(anadirAntecedenteDTO.getNombreTipoDocumento());
					 subirArhivoDTO.setIdTipoDeDocumentoSubir(anadirAntecedenteDTO.getIdTipoDocumento());
					 subirArhivoDTO.setIdExpedienteSubirArchivo(anadirAntecedenteDTOEntrada.getIdExpediente());
					 subirArhivoDTO.setIdInstanciaDeTareaSubirArchivo(anadirAntecedenteDTOEntrada.getIdInstanciaDeTarea());		
					 subirArhivoDTO.setEmisor(anadirAntecedenteDTOEntrada.getEmisor());
					 subirArhivoDTO.setFechaCreacionArchivo(dateFormat.format(date));
					 // Datos Documento
					 subirArhivoDTO.setArchivo(archivo);
					 subirArhivoDTO.setNombreDeArchivo(anadirAntecedenteDTO.getNombreDocumento());
					 boolean b = listaSubirArhivoDTO.add(subirArhivoDTO);
					 log.debug("b: " + b);
					log.info("Entro");
				}else{
					log.info("No entro");
				}
				
			}
				
		}
			
		
		//List<RespuestaMailDTO> listaRespuestaMailDTO = new ArrayList<RespuestaMailDTO>();
		
		//listaRespuestaMailDTO = bandejaDeEntradaService.guardarAntecedenteTodaEtapa(listaSubirArhivoDTO,usuario);

		List<RespuestaMailDTO> listaRespuestaMailDTOSalida = new ArrayList<RespuestaMailDTO>();
		for (SubirArhivoDTO subirArhivoDTO : listaSubirArhivoDTO) {
			RespuestaMailDTO respuestaMailDTO = new RespuestaMailDTO();
					
	        try {
	        	subirArhivoDTO.setMarcaSubirDocTransversal("SI");
				subirArhivoDTO = subirArchivoService.subirArchivo(usuario, subirArhivoDTO);
				respuestaMailDTO.setRespuesta("SUBIDO" );
				respuestaMailDTO.setCodigoError("Subido Correctamente");
				respuestaMailDTO.setNombreArchivo(subirArhivoDTO.getNombreDeArchivo());
				listaRespuestaMailDTOSalida.add(respuestaMailDTO);
			} catch (SgdpException|SgdpExceptionValidaTareaEnBE e) {
				respuestaMailDTO.setRespuesta("ERROR");
				respuestaMailDTO.setNombreArchivo(subirArhivoDTO.getNombreDeArchivo());
				respuestaMailDTO.setCodigoError(e.getMessage());
				log.error("ERROR al subir Documento adjunto  " + e.getMessage());
				listaRespuestaMailDTOSalida.add(respuestaMailDTO);
			}
	

		}

		RespuestaMailDTO respuestaMailDTO = bandejaDeEntradaService.preparaEnvioMail(listaRespuestaMailDTOSalida, anadirAntecedenteDTOEntrada, usuario);
		
		if (!respuestaMailDTO.getRespuesta().equals("OK")){
			log.info("Error Al enviar el Correo");
		}
		
		return listaRespuestaMailDTOSalida;
	}
	
	
	@RequestMapping(value = "/actualizaFueraDeOficina/{checkBoxFueraDeOficina}" , method = RequestMethod.POST)
	public @ResponseBody String actualizaFueraDeOficina(@PathVariable("checkBoxFueraDeOficina") Boolean checkBoxFueraDeOficina, HttpServletRequest request) {
		
		log.debug("Inicio actualizaFueraDeOficina");	
		
		String respuesta;
		
		log.debug("checkBoxFueraDeOficina: " + checkBoxFueraDeOficina.toString());
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		usuario.setFueraDeOficina(checkBoxFueraDeOficina);
		
		try {
			
			UsuarioRolDTO usuarioRolDTO = new UsuarioRolDTO();
			
			usuarioRolDTO.setIdUsuario(usuario.getIdUsuario());
			usuarioRolDTO.setIdRol(usuario.getIdRolUsuarioSeleccionado());
			usuarioRolDTO.setFueraDeOficina(checkBoxFueraDeOficina);
			
			usuarioRolService.actualizaFueraDeOficina(usuarioRolDTO);
			
			respuesta = "OK";
			return respuesta;		
			
		} catch (Exception e) {
			log.error("ERROR al actualizaFueraDeOficina: ", e);			
			
			return "ERROR";
		}      

	}
	
	@RequestMapping(value="/getTiposDeDocumentosDTOPorNombreExpediente/{nombreExpediente}", method=RequestMethod.GET)
	public @ResponseBody List<TipoDeDocumentoDTO> getTiposDeDocumentosDTOPorNombreExpediente(@PathVariable("nombreExpediente") String nombreExpediente
			, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		if (usuario==null) {
			log.debug("usuario==null");
			throw new Exception();
		}
		return tipoDeDocumentoService.getTiposDeDocumentosPorNombreExpediente(nombreExpediente);
	}
	
	
}
