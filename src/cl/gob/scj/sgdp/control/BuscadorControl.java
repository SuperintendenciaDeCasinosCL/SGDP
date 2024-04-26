package cl.gob.scj.sgdp.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.internal.com.fasterxml.jackson.core.JsonParseException;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.JsonMappingException;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.BuscarDTO;
import cl.gob.scj.sgdp.dto.CargaFacetDTO;
import cl.gob.scj.sgdp.dto.ConfidencialidadDocumentoDTO;
import cl.gob.scj.sgdp.dto.ConfidencialidadDocumentoRolDTO;
import cl.gob.scj.sgdp.dto.ConfidencialidadDocumentoUsuarioDTO;
import cl.gob.scj.sgdp.dto.ElementoResultadoBusquedaDTO;
import cl.gob.scj.sgdp.dto.MensajeVistaDTO;
import cl.gob.scj.sgdp.dto.ParametroPorContextoDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaDTO;
import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.BusquedaService;
import cl.gob.scj.sgdp.service.ConfidencialidadDocumentoService;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;
import cl.gob.scj.sgdp.service.ParametroPorContextoService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.tipos.PermisoType;
import cl.gob.scj.sgdp.util.DataTableRequestDTO;
import cl.gob.scj.sgdp.util.pagination.DataTableResults;

@Controller
public class BuscadorControl {

	private static final Logger log = Logger.getLogger(BuscadorControl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private ParametroPorContextoService parametroPorContextoService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private BusquedaService busquedaService;	

	@Autowired
	private InstanciaDeProcesoService instanciaDeProcesoService;
	
	@Autowired
	private ConfidencialidadDocumentoService confidencialidadService;
	
	private PermisoType permisoNoFiltraPorConfidencialidadType = PermisoType.NO_FILTRA_POR_CONFIDENCIALIDAD;
	
	@RequestMapping("/buscador")
	public String buscador(Model model, HttpServletRequest request) {
		
		log.debug("Inicio buscador");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");			
		
		List<ParametroPorContextoDTO> parametrosPorContextoDTOTiposDeObjetosCMSSGDP = parametroPorContextoService.getParametrosPorContextoPorNombreParam(Constantes.NOMBRE_PARAMETRO_POR_CONTEXTO_CMS_SGDP_RELACION_TIPOS_DE_OBJETOS_EN_BUSQUEDA);
			 
	    List<String> listaNombreTipoDocumento =  busquedaService.buscaTodosLosNombreDeLosDocumentosSubidos();
	    List<String> listaNombreProcesosVigentes =  busquedaService.getTodosLosNombresDeProcesos();
		
		model.addAttribute("listaNombreTipoDocumento",listaNombreTipoDocumento);
		model.addAttribute("listaNombreProcesosVigentes",listaNombreProcesosVigentes);	
		model.addAttribute("permisos", usuario.getPermisos());
		model.addAttribute("parametrosPorContextoDTOTiposDeObjetosCMSSGDP", parametrosPorContextoDTOTiposDeObjetosCMSSGDP);
		
		return configProps.getProperty("vistaBuscador");
	}
	
	
	@RequestMapping("/getResultadoBusqueda")
	public String getResultadoDeBusqueda(@ModelAttribute("buscarDTO") BuscarDTO buscarDTO, Model model,
			HttpServletRequest request) throws UnsupportedEncodingException {

		log.debug("Inicio getResultadoDeBusqueda");
		log.debug(buscarDTO.toString());		
		
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
			
		String permisoNoFiltraPorConfidencialidad = usuario.getPermisos()
				.get(permisoNoFiltraPorConfidencialidadType.getNombrePermiso());
		log.debug("permisoNoFiltraPorConfidencialidad: " + permisoNoFiltraPorConfidencialidad);

		StringBuilder todosLosTipos = new StringBuilder();

		//CargaFacetDTO cargaFacetDTO = new CargaFacetDTO();		
		
		if (permisoNoFiltraPorConfidencialidad == null || !permisoNoFiltraPorConfidencialidad
				.equals(permisoNoFiltraPorConfidencialidadType.getNombrePermiso())) {
			//busquedaService.filtraPorConfidencialidad(resultadoBusquedaDTO, usuario);
			//Setear variable filtraPorConfidencialidad que senviar la buscar como SI
			buscarDTO.setFiltraPorConfidencialidad("SI");
		} else {
			buscarDTO.setFiltraPorConfidencialidad("NO");
			//busquedaService.filtraPorConfidencialidadRestringida(resultadoBusquedaDTO, usuario);
			//Setear variable filtraPorConfidencialidad que senviar la buscar como NO			
		}
		
		List<ParametroPorContextoDTO> parametrosPorContextoDTOTiposDeObjetosCMSSGDP = parametroPorContextoService
				.getParametrosPorContextoPorNombreParam(
						Constantes.NOMBRE_PARAMETRO_POR_CONTEXTO_CMS_SGDP_RELACION_TIPOS_DE_OBJETOS_EN_BUSQUEDA);

		int contador = 0;
		for (ParametroPorContextoDTO parametroPorContextoDTO : parametrosPorContextoDTOTiposDeObjetosCMSSGDP) {
			todosLosTipos.append(parametroPorContextoDTO.getValorParametroChar());
			contador = contador + 1;
			if (contador < parametrosPorContextoDTOTiposDeObjetosCMSSGDP.size()) {
				todosLosTipos.append(",");
			}
		}
		
		//List<String> TipoObjetoLista = new ArrayList<String>();
		String tipoDocumentoOficia = "";
		
		if (buscarDTO.getTipoDeObjeto() != null && !buscarDTO.getTipoDeObjeto().isEmpty()) {

			for (String tipoObjeto : buscarDTO.getTipoDeObjeto()) {
				if (tipoObjeto.equals("OFICIALES")) {
					tipoDocumentoOficia = "OFICIALES";
				} 
			}
		}
		
		if (buscarDTO.getTipoDeObjeto()==null || buscarDTO.getTipoDeObjeto().isEmpty() || buscarDTO.getTipoDeObjeto().equals("null") 
				||  buscarDTO.getTipoDeObjeto().size()>1 ) {
			log.debug("Cargando tipo de objeto para buscar NONE");
			buscarDTO.setTipoDeObjetoParaBuscar(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_SGDP_RELACION_TIPOS_DE_OBJETO_AMBOS_EN_BUSQUEDA).getValorParametroChar());
			if(buscarDTO.getSoloEnMiBandejaDeSalida()){
                String buscarBS = usuario.getIdUsuario() + " " + buscarDTO.getPalabraClave();
                buscarDTO.setPalabraClave(buscarBS.trim());
			}
		} else if (buscarDTO.getTipoDeObjeto().size()==1) {
			log.debug("Cargando tipo de objeto para buscar buscarDTO.getTipoDeObjeto().get(0): " + buscarDTO.getTipoDeObjeto().get(0));
			buscarDTO.setTipoDeObjetoParaBuscar(buscarDTO.getTipoDeObjeto().get(0));
		}

		// se agrega in if para el checkbox documento oficiales, nuevo filtro
		// solicitado
		if (tipoDocumentoOficia != null && !tipoDocumentoOficia.isEmpty() && tipoDocumentoOficia != ""
				&& tipoDocumentoOficia.equals("OFICIALES")) {
			buscarDTO.setTipoDocumentoOficial("OFICIALES");
		}

		
		try {
			
			///--------------------------------------------------------------------------------------////
			// ----- Se tiene que comentar esto para volver al paginado
			
            // Buscar informacion datatable
			DataTableRequestDTO dataTableInput = new DataTableRequestDTO();
	
			// Buscar informacion para cargar el datatable
			
			buscarDTO.setFlagExportaExcel("SI");	
			buscarDTO.setFlagTipoBusqueda("1"); // Servicios busqueda general
			ResultadoBusquedaDTO resultadoBusquedaDTO = busquedaService.buscarRegistrosPaginados(dataTableInput, buscarDTO, usuario);
			instanciaDeProcesoService.buscaInstanciaDeProcesoDTOPorIdExpediente(resultadoBusquedaDTO);
			
			// Carga los datos del face en los datos cargados en memoria
			busquedaService.cargaFacet(resultadoBusquedaDTO);
			busquedaService.ordenaFacet(resultadoBusquedaDTO);
					
			//busquedaService.cargaFacet(resultadoBusquedaDTO, cargaFacetDTO, usuario);

			log.debug(resultadoBusquedaDTO.toString());
			
			List<ElementoResultadoBusquedaDTO> newList = new ArrayList<ElementoResultadoBusquedaDTO>();
			List<String> ids = new ArrayList<>();
			
			for (ElementoResultadoBusquedaDTO elementoResultadoBusquedaDTO : resultadoBusquedaDTO.getElementosResultadoBusquedaDTO()) {	
				ids.add(elementoResultadoBusquedaDTO.getIdObjeto());
			}
			
			List<ConfidencialidadDocumentoDTO> confs = confidencialidadService.getByIdTipoDocumento(ids);
			ids = new ArrayList<>();
			
			for (ConfidencialidadDocumentoDTO conf : confs) {
				log.info("***********************************INICIO********************************************************");
				List<String> roles = new ArrayList<>();
							
				for(ConfidencialidadDocumentoRolDTO cdr : conf.getRolesAsignados()) {
					roles.add(cdr.getId() + "");
				}
				
				boolean enRol = false;
				for(RolDTO r: usuario.getTodosLosRoles()) {
					enRol = roles.contains(r.getIdRol() + "");
					if(enRol) {
						break;
					}
				}
				
				boolean porUsuario = conf.getUsuariosAsignados().size() == 0 || conf.getUsuariosAsignados().contains(usuario.getIdUsuario());
				boolean porRol = conf.getRolesAsignados().size() == 0 || enRol;
				
				log.info("por usuario " + porUsuario);
				log.info("por rol " + porRol);
				
				if(porRol && porUsuario) {
					ids.add(conf.getId());
				}
				
				log.info("***********************************FIN********************************************************");
			}
			
			for (ElementoResultadoBusquedaDTO elementoResultadoBusquedaDTO : resultadoBusquedaDTO.getElementosResultadoBusquedaDTO()) {	
				if(ids.contains(elementoResultadoBusquedaDTO.getIdObjeto())) {
					newList.add(elementoResultadoBusquedaDTO);
				}
			}
			
			resultadoBusquedaDTO.setElementosResultadoBusquedaDTO(newList);
			
			model.addAttribute("permisos", usuario.getPermisos());
			model.addAttribute("resultadoBusquedaDTO", resultadoBusquedaDTO);
			// /////////////////////////////////////////////////////////////////////////////////////////
		
			// /////////////////////////////////////////////////////////////////////////////////////////
			model.addAttribute("buscarDTO", buscarDTO);
			model.addAttribute("ticket", usuario.getAlfTicket());
			model.addAttribute("urlFuncPhp",
					parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FUNC_PHP).getValorParametroChar());
						
		} catch (SgdpException sgdpe) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			mensajeVistaDTO.getMensajes().put(sgdpe.getMessage(), configProps.getProperty("cssError"));
		}
		return configProps.getProperty("vistaResultadoDeBusqueda");

	}
	
	
	@RequestMapping("/getResultadoBusquedaConFiltro/{nombreFiltro}/{tipoFiltro}")
	public String getResultadoBusquedaConFiltro(// @ModelAttribute("buscarDTO")
												// BuscarDTO buscarDTO,
			@PathVariable("nombreFiltro") String nombreFiltro, @PathVariable("tipoFiltro") String tipoFiltro,
			Model model, HttpServletRequest request) throws UnsupportedEncodingException {		
		
		Usuario	usuario = (Usuario) request.getSession().getAttribute("usuario");		
		
		String permisoNoFiltraPorConfidencialidad = usuario.getPermisos()
				.get(permisoNoFiltraPorConfidencialidadType.getNombrePermiso());
		log.debug("permisoNoFiltraPorConfidencialidad: " + permisoNoFiltraPorConfidencialidad);

		BuscarDTO buscarDTO = new BuscarDTO();
		buscarDTO.setNombreFiltro(nombreFiltro);
		buscarDTO.setTipoFiltro(tipoFiltro);
		buscarDTO.setFlagTipoBusqueda("2"); // Servicios con filtros
		
		if (permisoNoFiltraPorConfidencialidad == null || !permisoNoFiltraPorConfidencialidad
				.equals(permisoNoFiltraPorConfidencialidadType.getNombrePermiso())) {
			//busquedaService.filtraPorConfidencialidad(resultadoBusquedaDTO, usuario);
			//Setear variable filtraPorConfidencialidad que senviar la buscar como SI
			buscarDTO.setFiltraPorConfidencialidad("SI");
		} else {
			buscarDTO.setFiltraPorConfidencialidad("NO");
			//busquedaService.filtraPorConfidencialidadRestringida(resultadoBusquedaDTO, usuario);
			//Setear variable filtraPorConfidencialidad que senviar la buscar como NO			
		}		

		log.debug("Inicio getResultadoDeBusqueda");
		log.debug(buscarDTO.toString());

		StringBuilder todosLosTipos = new StringBuilder();

		CargaFacetDTO cargaFacetDTO = new CargaFacetDTO();

		// List<ElementoResultadoBusquedaDTO>
		// elementosResultadoBusquedaDTOFiltro = new
		// ArrayList<ElementoResultadoBusquedaDTO>();

		List<ParametroPorContextoDTO> parametrosPorContextoDTOTiposDeObjetosCMSSGDP = parametroPorContextoService
				.getParametrosPorContextoPorNombreParam(
						Constantes.NOMBRE_PARAMETRO_POR_CONTEXTO_CMS_SGDP_RELACION_TIPOS_DE_OBJETOS_EN_BUSQUEDA);

		int contador = 0;
		for (ParametroPorContextoDTO parametroPorContextoDTO : parametrosPorContextoDTOTiposDeObjetosCMSSGDP) {
			todosLosTipos.append(parametroPorContextoDTO.getValorParametroChar());
			contador = contador + 1;
			if (contador < parametrosPorContextoDTOTiposDeObjetosCMSSGDP.size()) {
				todosLosTipos.append(",");
			}
		}
		
		//List<String> TipoObjetoLista = new ArrayList<String>();
		String tipoDocumentoOficia = "";
		
		if (buscarDTO.getTipoDeObjeto() != null && !buscarDTO.getTipoDeObjeto().isEmpty()) {

			for (String tipoObjeto : buscarDTO.getTipoDeObjeto()) {
				if (tipoObjeto.equals("OFICIALES")) {
					tipoDocumentoOficia = "OFICIALES";
				} 
			}
		}


		/*if (buscarDTO.getTipoDeObjeto() != null && !buscarDTO.getTipoDeObjeto().isEmpty()) {

			for (String tipoObjeto : buscarDTO.getTipoDeObjeto()) {
				if (!tipoObjeto.equals("OFICIALES")) {
					TipoObjetoLista.add(tipoObjeto);
				} else {
					tipoDocumentoOficia = "OFICIALES";
				}
			}
		}

		if (TipoObjetoLista == null || TipoObjetoLista.isEmpty() || TipoObjetoLista.equals("null")
		// || buscarDTO.getTipoDeObjeto().size()>1
				|| TipoObjetoLista.size() > 1) {
			log.debug("Cargando tipo de objeto para buscar NONE");
			buscarDTO.setTipoDeObjetoParaBuscar(parametroService
					.getParametroPorID(Constantes.ID_PARAM_CMS_SGDP_RELACION_TIPOS_DE_OBJETO_AMBOS_EN_BUSQUEDA)
					.getValorParametroChar());
			if (buscarDTO.getSoloEnMiBandejaDeSalida()) {
				String buscarBS = usuario.getIdUsuario() + " " + buscarDTO.getPalabraClave();
				buscarDTO.setPalabraClave(buscarBS.trim());
			}			
		} else if (TipoObjetoLista.size() == 1) {
			log.debug("Cargando tipo de objeto para buscar buscarDTO.getTipoDeObjeto().get(0): "
					+ buscarDTO.getTipoDeObjeto().get(0));
			buscarDTO.setTipoDeObjetoParaBuscar(TipoObjetoLista.get(0));
		}*/
		
		if (buscarDTO.getTipoDeObjeto()==null || buscarDTO.getTipoDeObjeto().isEmpty() || buscarDTO.getTipoDeObjeto().equals("null") 
				||  buscarDTO.getTipoDeObjeto().size()>1 ) {
			log.debug("Cargando tipo de objeto para buscar NONE");
			buscarDTO.setTipoDeObjetoParaBuscar(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_SGDP_RELACION_TIPOS_DE_OBJETO_AMBOS_EN_BUSQUEDA).getValorParametroChar());
			if(buscarDTO.getSoloEnMiBandejaDeSalida()){
                String buscarBS = usuario.getIdUsuario() + " " + buscarDTO.getPalabraClave();
                buscarDTO.setPalabraClave(buscarBS.trim());
			}
		} else if (buscarDTO.getTipoDeObjeto().size()==1) {
			log.debug("Cargando tipo de objeto para buscar buscarDTO.getTipoDeObjeto().get(0): " + buscarDTO.getTipoDeObjeto().get(0));
			buscarDTO.setTipoDeObjetoParaBuscar(buscarDTO.getTipoDeObjeto().get(0));
		}

		cargaFacetDTO.setFechaFin(buscarDTO.getFechaFin());
		cargaFacetDTO.setFechaInicio(buscarDTO.getFechaInicio());
		cargaFacetDTO.setNombreSubprocesoVigente(buscarDTO.getNombreSubprocesoVigente());
		cargaFacetDTO.setNombreTipoDocumento(buscarDTO.getNombreTipoDocumento());
		cargaFacetDTO.setPalabraClave(buscarDTO.getPalabraClave());
		cargaFacetDTO.setTipoObjeto(buscarDTO.getTipoDeObjetoParaBuscar());
		cargaFacetDTO.setFlagTipoBusqueda("2");
		cargaFacetDTO.setTipoFiltro(buscarDTO.getTipoFiltro());
		cargaFacetDTO.setNombreFiltro(buscarDTO.getNombreFiltro());
		cargaFacetDTO.setTipoDocumentoOficial(buscarDTO.getTipoDocumentoOficial());
		cargaFacetDTO.setFiltraPorConfidencialidad(buscarDTO.getFiltraPorConfidencialidad());

		// se agrega in if para el checkbox documento oficiales, nuevo filtro
		// solicitado
		if (tipoDocumentoOficia != null && !tipoDocumentoOficia.isEmpty() && tipoDocumentoOficia != ""
				&& tipoDocumentoOficia.equals("OFICIALES")) {

			buscarDTO.setTipoDocumentoOficial("OFICIALES");
		}

		//long startTime = 0;
		//long dif = 0;
		try {

			ResultadoBusquedaDTO resultadoBusquedaDTO = new ResultadoBusquedaDTO();
			// Buscar informacion para cargar el datatable
			DataTableRequestDTO dataTableInput = new DataTableRequestDTO();
					
			buscarDTO.setFlagExportaExcel("SI");	

			resultadoBusquedaDTO = busquedaService.buscarRegistrosPaginados(dataTableInput, buscarDTO, usuario);
				
			instanciaDeProcesoService.buscaInstanciaDeProcesoDTOPorIdExpediente(resultadoBusquedaDTO);

						
		//------------------------------------------------------------------------------------------------

			log.debug(resultadoBusquedaDTO.toString());
			model.addAttribute("permisos", usuario.getPermisos());
			model.addAttribute("resultadoBusquedaDTO", resultadoBusquedaDTO);
			model.addAttribute("buscarDTO", buscarDTO);
			model.addAttribute("ticket", usuario.getAlfTicket());
			model.addAttribute("urlFuncPhp", parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FUNC_PHP).getValorParametroChar());
			
			busquedaService.cargaFacet(resultadoBusquedaDTO, cargaFacetDTO, usuario);
			busquedaService.ordenaFacet(resultadoBusquedaDTO);

		} catch (SgdpException sgdpe) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			mensajeVistaDTO.getMensajes().put(sgdpe.getMessage(), configProps.getProperty("cssError"));
		}
		
		return configProps.getProperty("vistaResultadoDeBusqueda");

		/*
		 * ResultadoBusquedaDTO resultadoBusquedaDTO = new
		 * ResultadoBusquedaDTO(); try { resultadoBusquedaDTO =
		 * busquedaService.buscarConFiltro(buscarConFiltroDTO, usuario);
		 * 
		 * String permisoNoFiltraPorConfidencialidad =
		 * usuario.getPermisos().get(permisoNoFiltraPorConfidencialidadType.
		 * getNombrePermiso()); log.debug("permisoNoFiltraPorConfidencialidad: "
		 * + permisoNoFiltraPorConfidencialidad);
		 * 
		 * if (permisoNoFiltraPorConfidencialidad==null ||
		 * !permisoNoFiltraPorConfidencialidad.equals(
		 * permisoNoFiltraPorConfidencialidadType.getNombrePermiso())) {
		 * busquedaService.filtraPorConfidencialidad(resultadoBusquedaDTO,
		 * usuario); } else {
		 * busquedaService.filtraPorConfidencialidadRestringida(
		 * resultadoBusquedaDTO, usuario); }
		 * 
		 * instanciaDeProcesoService.buscaInstanciaDeProcesoDTOPorIdExpediente(
		 * resultadoBusquedaDTO);
		 * 
		 * } catch (SgdpException e) { e.printStackTrace(); }
		 * 
		 * busquedaService.cargaFacet(resultadoBusquedaDTO);
		 * model.addAttribute("resultadoBusquedaDTO", resultadoBusquedaDTO);
		 * 
		 * return configProps.getProperty("vistaResultadoDeBusqueda");
		 */
	}
	

	/*@RequestMapping("/cargaFacet")
	public String cargaFacet(@ModelAttribute("buscarDTO") CargaFacetDTO cargaFacetDTO, Model model, HttpServletRequest request) {
		log.debug("Inicio cargaFacet");
		log.debug(cargaFacetDTO.toString());
		RespuestaCargaFacetDTO respuestaCargaFacetDTO;
		if (usuario == null) {
			usuario = (Usuario)request.getSession().getAttribute("usuario");			
		}
		try {			
			respuestaCargaFacetDTO = busquedaService.cargaFacet(cargaFacetDTO, usuario);
			log.debug(respuestaCargaFacetDTO.toString());
			model.addAttribute("respuestaCargaFacetDTO", respuestaCargaFacetDTO);
		} catch (SgdpException sgdpe) {
			log.debug("sgdpe.getMessage(): " + sgdpe.getMessage());
			mensajeVistaDTO.getMensajes().put(sgdpe.getMessage(), configProps.getProperty("cssError"));	
			model.addAttribute("mensajeVistaDTO", mensajeVistaDTO);
		}		
		return configProps.getProperty("vistaResultadoCargaFacet");
	}*/
	
	@RequestMapping(value = "/getResultadoBusquedaPaginado", method = RequestMethod.POST)
	public @ResponseBody DataTableResults getResultadoBusquedaPaginado(HttpServletRequest request,
			HttpServletResponse response, Model model)
			throws SgdpException, JsonParseException, JsonMappingException, IOException {

		DataTableRequestDTO dataTableInput = new DataTableRequestDTO(request);
				
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
				
		/////////////////////////////// Transforma el json en un objeto
		/////////////////////////////// //////////////////////////////////
		
		String buscaDTOString = request.getParameter("buscaDTOString");

		ObjectMapper mapper = new ObjectMapper();

		BuscarDTO buscarDTO = mapper.readValue(buscaDTOString, BuscarDTO.class);

		//////////////////////////////////////////////////////////////////////////////////////

		ResultadoBusquedaDTO resultadoBusquedaDTO = new ResultadoBusquedaDTO();
		
		String permisoNoFiltraPorConfidencialidad = usuario.getPermisos()
				.get(permisoNoFiltraPorConfidencialidadType.getNombrePermiso());
		log.debug("permisoNoFiltraPorConfidencialidad: " + permisoNoFiltraPorConfidencialidad);
		
		// Filtro de confidencialidad
		if (permisoNoFiltraPorConfidencialidad == null || !permisoNoFiltraPorConfidencialidad
				.equals(permisoNoFiltraPorConfidencialidadType.getNombrePermiso())) {
			//busquedaService.filtraPorConfidencialidad(resultadoBusquedaDTO, usuario);
			//Setear variable filtraPorConfidencialidad que senviar la buscar como SI
			buscarDTO.setFiltraPorConfidencialidad("SI");
		} else {
			buscarDTO.setFiltraPorConfidencialidad("NO");
			//busquedaService.filtraPorConfidencialidadRestringida(resultadoBusquedaDTO, usuario);
			//Setear variable filtraPorConfidencialidad que senviar la buscar como NO			
		}

		// Buscar informacion para cargar el datatable
		resultadoBusquedaDTO = busquedaService.buscarRegistrosPaginados(dataTableInput, buscarDTO, usuario);

		instanciaDeProcesoService.buscaInstanciaDeProcesoDTOPorIdExpediente(resultadoBusquedaDTO);

		// Se agrega el filtro que busca documentos oficiales

		// -----------------------------------------------------------------------------------------------
		// Carga Dato del datable

		DataTableResults dataTableResult = new DataTableResults();

		dataTableResult.setDraw(Integer.parseInt(dataTableInput.getDraw()));
		dataTableResult.setData(resultadoBusquedaDTO.getElementosResultadoBusquedaDTO());	
		dataTableResult.setRecordsTotal(Long.parseLong(resultadoBusquedaDTO.getTotal().replaceAll("\\.", "")));
		dataTableResult.setRecordsFiltered(Long.parseLong(resultadoBusquedaDTO.getTotal().replaceAll("\\.", "")));

		return dataTableResult;

	}

	
	@RequestMapping(value = "/buscarRegistrosPaginadosExportExcel", method = RequestMethod.GET)
	public ModelAndView buscarRegistrosPaginadosExportExcel(
											@RequestParam("buscarDTO") String stringBuscarDTO,
										  HttpServletRequest request) throws SgdpException, JsonParseException, JsonMappingException, IOException {
		// public ModelAndView getExcel() {
		
		Usuario	usuario = (Usuario) request.getSession().getAttribute("usuario");
		
		//String buscaDTOString = request.getParameter("buscaDTOString");

		ObjectMapper mapper = new ObjectMapper();

		BuscarDTO buscarDTO = mapper.readValue(stringBuscarDTO, BuscarDTO.class);
		buscarDTO.setFlagExportaExcel("SI");
		//////////////////////////////////////////////////////////////////////////////////////

		ResultadoBusquedaDTO resultadoBusquedaDTO = new ResultadoBusquedaDTO();

		
		String permisoNoFiltraPorConfidencialidad = usuario.getPermisos()
				.get(permisoNoFiltraPorConfidencialidadType.getNombrePermiso());
		log.debug("permisoNoFiltraPorConfidencialidad: " + permisoNoFiltraPorConfidencialidad);
		
		// Filtro de confidencialidad
		if (permisoNoFiltraPorConfidencialidad == null || !permisoNoFiltraPorConfidencialidad
				.equals(permisoNoFiltraPorConfidencialidadType.getNombrePermiso())) {
		} else {
			buscarDTO.setFiltraPorConfidencialidad("NO");		
		}

		// Buscar informacion para cargar el datatable
		
		DataTableRequestDTO dataTableInput = new DataTableRequestDTO();
		
		resultadoBusquedaDTO = busquedaService.buscarRegistrosPaginados(dataTableInput,buscarDTO, usuario);

		instanciaDeProcesoService.buscaInstanciaDeProcesoDTOPorIdExpediente(resultadoBusquedaDTO);
		
	        
	    return new ModelAndView("ExcelBuscador", "listaElementosResultadoBusquedaDTO",
	    			resultadoBusquedaDTO.getElementosResultadoBusquedaDTO());  
	        
	}
	
	
	@RequestMapping(value = "/getResultadoBusquedaConListaFiltros", method = RequestMethod.POST)
	public String getResultadoBusquedaConListaFiltros(	@RequestBody BuscarDTO buscarDTO, Model model,
			HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
						
		log.debug("Inicio getResultadoDeBusqueda");
		log.debug(buscarDTO.toString());
		
		String permisoNoFiltraPorConfidencialidad = usuario.getPermisos()
				.get(permisoNoFiltraPorConfidencialidadType.getNombrePermiso());
		log.debug("permisoNoFiltraPorConfidencialidad: " + permisoNoFiltraPorConfidencialidad);

		StringBuilder todosLosTipos = new StringBuilder();

		//CargaFacetDTO cargaFacetDTO = new CargaFacetDTO();		
		
		if (permisoNoFiltraPorConfidencialidad == null || !permisoNoFiltraPorConfidencialidad
				.equals(permisoNoFiltraPorConfidencialidadType.getNombrePermiso())) {
			//busquedaService.filtraPorConfidencialidad(resultadoBusquedaDTO, usuario);
			//Setear variable filtraPorConfidencialidad que senviar la buscar como SI
			buscarDTO.setFiltraPorConfidencialidad("SI");
		} else {
			buscarDTO.setFiltraPorConfidencialidad("NO");
			//busquedaService.filtraPorConfidencialidadRestringida(resultadoBusquedaDTO, usuario);
			//Setear variable filtraPorConfidencialidad que senviar la buscar como NO			
		}
		
		List<ParametroPorContextoDTO> parametrosPorContextoDTOTiposDeObjetosCMSSGDP = parametroPorContextoService
				.getParametrosPorContextoPorNombreParam(
						Constantes.NOMBRE_PARAMETRO_POR_CONTEXTO_CMS_SGDP_RELACION_TIPOS_DE_OBJETOS_EN_BUSQUEDA);

		int contador = 0;
		for (ParametroPorContextoDTO parametroPorContextoDTO : parametrosPorContextoDTOTiposDeObjetosCMSSGDP) {
			todosLosTipos.append(parametroPorContextoDTO.getValorParametroChar());
			contador = contador + 1;
			if (contador < parametrosPorContextoDTOTiposDeObjetosCMSSGDP.size()) {
				todosLosTipos.append(",");
			}
		}
		
		//List<String> TipoObjetoLista = new ArrayList<String>();
		String tipoDocumentoOficia = "";
		
		if (buscarDTO.getTipoDeObjeto() != null && !buscarDTO.getTipoDeObjeto().isEmpty()) {

			for (String tipoObjeto : buscarDTO.getTipoDeObjeto()) {
				if (tipoObjeto.equals("OFICIALES")) {
					tipoDocumentoOficia = "OFICIALES";
				} 
			}
		}



		if (buscarDTO.getTipoDeObjeto()==null || buscarDTO.getTipoDeObjeto().isEmpty() || buscarDTO.getTipoDeObjeto().equals("null") 
				||  buscarDTO.getTipoDeObjeto().size()>1 ) {
			log.debug("Cargando tipo de objeto para buscar NONE");
			buscarDTO.setTipoDeObjetoParaBuscar(parametroService.getParametroPorID(Constantes.ID_PARAM_CMS_SGDP_RELACION_TIPOS_DE_OBJETO_AMBOS_EN_BUSQUEDA).getValorParametroChar());
			if(buscarDTO.getSoloEnMiBandejaDeSalida()){
                String buscarBS = usuario.getIdUsuario() + " " + buscarDTO.getPalabraClave();
                buscarDTO.setPalabraClave(buscarBS.trim());
			}
		} else if (buscarDTO.getTipoDeObjeto().size()==1) {
			log.debug("Cargando tipo de objeto para buscar buscarDTO.getTipoDeObjeto().get(0): " + buscarDTO.getTipoDeObjeto().get(0));
			buscarDTO.setTipoDeObjetoParaBuscar(buscarDTO.getTipoDeObjeto().get(0));
		}

		// se agrega in if para el checkbox documento oficiales, nuevo filtro
		// solicitado
		if (tipoDocumentoOficia != null && !tipoDocumentoOficia.isEmpty() && tipoDocumentoOficia != ""
				&& tipoDocumentoOficia.equals("OFICIALES")) {
			buscarDTO.setTipoDocumentoOficial("OFICIALES");
		}

		
		try {
			
			///--------------------------------------------------------------------------------------////
			// ----- Se tiene que comentar esto para volver al paginado
			
			ResultadoBusquedaDTO resultadoBusquedaDTO = new ResultadoBusquedaDTO();
            // Buscar informacion datatable
			DataTableRequestDTO dataTableInput = new DataTableRequestDTO();
	
			// Buscar informacion para cargar el datatable
			
			buscarDTO.setFlagExportaExcel("SI");	
			buscarDTO.setFlagTipoBusqueda("1"); // Servicios busqueda general
			resultadoBusquedaDTO = busquedaService.buscarRegistrosPaginados(dataTableInput, buscarDTO, usuario);
			instanciaDeProcesoService.buscaInstanciaDeProcesoDTOPorIdExpediente(resultadoBusquedaDTO);
			
			// Carga los datos del face en los datos cargados en memoria
			busquedaService.cargaFacet(resultadoBusquedaDTO);
			busquedaService.ordenaFacet(resultadoBusquedaDTO);
	

			log.debug(resultadoBusquedaDTO.toString());
			model.addAttribute("permisos", usuario.getPermisos());
			model.addAttribute("resultadoBusquedaDTO", resultadoBusquedaDTO);
			// /////////////////////////////////////////////////////////////////////////////////////////
		
			// /////////////////////////////////////////////////////////////////////////////////////////
			model.addAttribute("buscarDTO", buscarDTO);
			model.addAttribute("ticket", usuario.getAlfTicket());
			model.addAttribute("urlFuncPhp",
					parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FUNC_PHP).getValorParametroChar());
						
		} catch (SgdpException sgdpe) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			mensajeVistaDTO.getMensajes().put(sgdpe.getMessage(), configProps.getProperty("cssError"));
		}
		
		return configProps.getProperty("vistaResultadoDeBusqueda");
	}
	
	
	
}
