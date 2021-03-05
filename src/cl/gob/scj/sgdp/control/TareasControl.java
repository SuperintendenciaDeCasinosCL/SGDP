package cl.gob.scj.sgdp.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.FiltroExpedienteDTO;
import cl.gob.scj.sgdp.dto.HistoricoDeInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.KeyParametroPorContextoDTO;
import cl.gob.scj.sgdp.dto.MensajeVistaDTO;
import cl.gob.scj.sgdp.dto.ParametroPorContextoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.BandejaDeEntradaService;
import cl.gob.scj.sgdp.service.HistoricoDeInstDeTareaService;
import cl.gob.scj.sgdp.service.InstanciaDeTareaService;
import cl.gob.scj.sgdp.service.ParametroPorContextoService;
import cl.gob.scj.sgdp.tipos.EstadoDeTareaType;
import cl.gob.scj.sgdp.tipos.PermisoType;

@Controller
public class TareasControl {

private static final Logger log = Logger.getLogger(TareasControl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private BandejaDeEntradaService bandejaDeEntradaService;
	
	@Autowired
	private ParametroPorContextoService parametroPorContextoService;
	
	private EstadoDeTareaType estadoDeTareaAsignadaType = EstadoDeTareaType.ASIGNADA;
	private PermisoType permisoMuestraTareasEnEjecucionType = PermisoType.PUEDE_VER_TAREAS_EN_EJECUCION;
	
	@Autowired
	private InstanciaDeTareaService instanciaDeTareaService;
	
	@Autowired
	private HistoricoDeInstDeTareaService historicoDeInstDeTareaService;	

	@RequestMapping("/getInstanciasDeTarea")
	public String getInstanciasDeTarea(Model model, HttpServletRequest request) {
		
		boolean tareaEnEspera = false;
		boolean trabajoInterno = false;
		try {
			 tareaEnEspera = (boolean) request.getSession().getAttribute("tareaEnEspera");
			 trabajoInterno = (boolean) request.getSession().getAttribute("trabajoInterno");
		} catch (Exception e1) {

		}
		
		FiltroExpedienteDTO filtroExpedienteDTO = new FiltroExpedienteDTO();
		filtroExpedienteDTO.setRespuestaEspera(tareaEnEspera);
		filtroExpedienteDTO.setTrabajoInterno(trabajoInterno);
		
		log.debug("Inicio getInstanciasDeTarea");
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		//instanciasDeTareasDTO.clear();
		List<InstanciaDeTareaDTO> instanciasDeTareasDTO = new ArrayList<InstanciaDeTareaDTO>();
		try {
			
			instanciasDeTareasDTO = bandejaDeEntradaService.getInstanciasDeTareaPorIdUsrNombreEstadoTareaFiltro(usuario, 
					estadoDeTareaAsignadaType.getNombreEstadoDeTarea(), instanciasDeTareasDTO ,filtroExpedienteDTO);
		} catch (IOException e) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			mensajeVistaDTO.getMensajes().put("Error al cargar las tareas", configProps.getProperty("cssSucess"));
		}
		model.addAttribute("permisos", usuario.getPermisos());
		model.addAttribute("instanciasDeTareasDTO", instanciasDeTareasDTO);
		request.getSession().setAttribute("cantidadDeTareas", instanciasDeTareasDTO!=null ? instanciasDeTareasDTO.size(): 0);
		return configProps.getProperty("vistaTareasBandejaDeEntrada");		
	}
	
	@RequestMapping("/getTareasEnEjecucion")
	public String getTareasEnEjecucion(Model model, HttpServletRequest request) {
		log.debug("Inicio getTareasEnEjecucion");
		
		List<InstanciaDeTareaDTO> instanciasDeTareasDTOEnEjecucion = new ArrayList<InstanciaDeTareaDTO>();
	
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		//KeyParametroPorContextoDTO keyParametroPorContextoDTOMuestraTareasEnEjecucion = new KeyParametroPorContextoDTO();
		
		//keyParametroPorContextoDTOMuestraTareasEnEjecucion.setNombreParametro(Constantes.NOMBRE_PARAMETRO_POR_CONTEXTO_MUESTRA_TAREAS_EN_EJECUCION_POR_ID_ROL);				
		//keyParametroPorContextoDTOMuestraTareasEnEjecucion.setValorContexto(Long.toString(usuario.getRolDTO().getIdRol()));
		
		String permisoTareasEnEjecucion = usuario.getPermisos().get(permisoMuestraTareasEnEjecucionType.getNombrePermiso());
		log.debug("permisoTareasEnEjecucion: " + permisoTareasEnEjecucion);
		log.debug("permisoMuestraTareasEnEjecucionType.getNombrePermiso(): " + permisoMuestraTareasEnEjecucionType.getNombrePermiso());
		
		try {			
		
			if (permisoTareasEnEjecucion!=null && permisoTareasEnEjecucion.equals(permisoMuestraTareasEnEjecucionType.getNombrePermiso())) {
				log.debug("Buscando parametroPorContextoDTO...");
				//log.debug(keyParametroPorContextoDTOMuestraTareasEnEjecucion.toString());
				/*ParametroPorContextoDTO parametroPorContextoDTO = parametroPorContextoService.getParamPorContexto(keyParametroPorContextoDTOMuestraTareasEnEjecucion);
				log.debug(parametroPorContextoDTO);
				if (parametroPorContextoDTO!=null && parametroPorContextoDTO.getValorParametroChar().equals(Constantes.MUESTRA_TODAS_LAS_TAREAS_EN_EJECUCION)) {
					log.debug("Buscando todas las instanciasDeTareasDTOEnEjecucion");
					instanciasDeTareasDTOEnEjecucion = bandejaDeEntradaService.getTodasInstanciasDeTareasEnEjecucion(instanciasDeTareasDTOEnEjecucion);
				} else if (parametroPorContextoDTO!=null && parametroPorContextoDTO.getValorParametroChar().equals(Constantes.MUESTRA_LAS_TAREAS_DE_LA_UNIDAD)) {
					log.debug("Buscando instanciasDeTareasDTOEnEjecucion por unidad");
					instanciasDeTareasDTOEnEjecucion = bandejaDeEntradaService.getTodasInstanciasDeTareasEnEjecucionPorIdUnidad(usuario.getUnidadDTO().getIdUnidad(), instanciasDeTareasDTOEnEjecucion);
				}*/
				ParametroPorContextoDTO parametroPorContextoDTO = parametroPorContextoService.getParametroPorContextoDTOMuestraTodasLasTareaEjecucion(usuario);
				if (parametroPorContextoDTO!=null) {
					instanciasDeTareasDTOEnEjecucion = bandejaDeEntradaService.getTodasInstanciasDeTareasEnEjecucion(instanciasDeTareasDTOEnEjecucion);
				} else {
					//instanciasDeTareasDTOEnEjecucion = bandejaDeEntradaService.getTodasInstanciasDeTareasEnEjecucionPorIdUnidad(usuario.getUnidadDTO().getIdUnidad(), instanciasDeTareasDTOEnEjecucion);
					instanciasDeTareasDTOEnEjecucion = bandejaDeEntradaService.getTodasInstanciasDeTareasEnEjecucionPorIdUnidades(usuario, instanciasDeTareasDTOEnEjecucion);
					
				}
			}
		
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
		}
		
		log.debug("Instancias de tareas en ejecucion");
		for (InstanciaDeTareaDTO instanciaDeTareaDTO : instanciasDeTareasDTOEnEjecucion) {
			log.debug(instanciaDeTareaDTO.toString());				
		}
		
		model.addAttribute("permisos", usuario.getPermisos());
		model.addAttribute("instanciasDeTareasDTOEnEjecucion", instanciasDeTareasDTOEnEjecucion);
		return configProps.getProperty("vistaTareasEnEjecucion");
	}
	
	@RequestMapping("/getVistaInstanciasDeTareasPorIdExpediente")
	public String getVistaInstanciasDeTareasPorIdExpediente(@RequestParam("idExpediente") String idExpediente, Model model, HttpServletRequest request) {
		log.debug("Inicio getVistaInstanciasDeTareasPorIdExpediente");
		//instanciasDeTareasDTO.clear();
		List<InstanciaDeTareaDTO> instanciasDeTareasDTO = new ArrayList<InstanciaDeTareaDTO>();
		try {
			instanciaDeTareaService.cargaInstanciasDeTareasDTOPorIdExpediente(idExpediente, instanciasDeTareasDTO);
			model.addAttribute("instanciasDeTareasDTOPorIdExpediente", instanciasDeTareasDTO);
		} catch (SgdpException e) {
			log.error("ERROR al obtener VistaInstanciasDeTareasPorIdExpediente: ", e);			
		}
		return configProps.getProperty("vistaInstanciasDeTareasPorIdExpediente");
	}	
	
	@RequestMapping(value = "/getHistoricoDeInstDeTareaPorIdExpedienteBusqueda/{idExpediente}", method=RequestMethod.GET)
	public String getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(@PathVariable String idExpediente,
			Model model, HttpServletRequest request) {		
		List<HistoricoDeInstDeTareaDTO> historicoDeInstDeTareaPorIdExpedienteBusqueda = historicoDeInstDeTareaService.getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(idExpediente);
		model.addAttribute("numeroTabla", "");
		model.addAttribute("historicoDeInstDeTareaPorIdExpedienteBusqueda", historicoDeInstDeTareaPorIdExpedienteBusqueda);
		return configProps.getProperty("vistaHistoricoDeInstDeTareaPorIdExpedienteBusqueda");		
	}
	
	@RequestMapping(value = "/getHistoricoDeInstDeTareaPorIdExpedienteBusqueda2/{idExpediente}", method=RequestMethod.GET)
	public String getHistoricoDeInstDeTareaPorIdExpedienteBusqueda2(@PathVariable String idExpediente,
			Model model, HttpServletRequest request) {		
		List<HistoricoDeInstDeTareaDTO> historicoDeInstDeTareaPorIdExpedienteBusqueda = historicoDeInstDeTareaService.getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(idExpediente);
		model.addAttribute("numeroTabla", "2");
		model.addAttribute("historicoDeInstDeTareaPorIdExpedienteBusqueda", historicoDeInstDeTareaPorIdExpedienteBusqueda);
		return configProps.getProperty("vistaHistoricoDeInstDeTareaPorIdExpedienteBusqueda");		
	}	
	
	@RequestMapping(value = "/getHistoricoDeInstDeTareaPorIdExpedienteBusquedaJson/{idExpediente}", method=RequestMethod.GET)
	public @ResponseBody List<HistoricoDeInstDeTareaDTO> getHistoricoDeInstDeTareaPorIdExpedienteBusquedaJson(@PathVariable String idExpediente,
			Model model, HttpServletRequest request) {		
		List<HistoricoDeInstDeTareaDTO> historicoDeInstDeTareaPorIdExpedienteBusqueda = historicoDeInstDeTareaService.getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(idExpediente);
		return historicoDeInstDeTareaPorIdExpedienteBusqueda;	
	}
	
	@RequestMapping("/getVistaHistorialDeTareasPorIdIntanciaDeTarea")
	public String getVistaHistorialDeTareasPorIdIntanciaDeTarea(@RequestParam("idInstanciaDeTarea") Long idInstanciaDeTarea, Model model, HttpServletRequest request) {
		log.debug("Inicio getVistaHistorialDeTareasPorIdIntanciaDeTarea");
		log.debug("idInstanciaDeTarea: " + idInstanciaDeTarea);
		//historicosDeInstDeTareaDTO.clear();
		List<HistoricoDeInstDeTareaDTO> historicosDeInstDeTareaDTO = new ArrayList<HistoricoDeInstDeTareaDTO>();
		historicoDeInstDeTareaService.cargaHistorialDeTareasPorIdIntanciaDeTarea(idInstanciaDeTarea, historicosDeInstDeTareaDTO);
		model.addAttribute("historicosDeInstDeTareaDTO", historicosDeInstDeTareaDTO);
		return configProps.getProperty("vistaHistorialDeTareasPorIdIntanciaDeTarea");
	}
	
	@RequestMapping(value = "/getHistorialDeTareasPorIdIntanciaDeTarea", method = RequestMethod.GET)
	public @ResponseBody List<HistoricoDeInstDeTareaDTO> getHistorialDeTareasPorIdIntanciaDeTarea(@RequestParam("idInstanciaDeTarea") Long idInstanciaDeTarea, Model model, HttpServletRequest request) {
		log.debug("Inicio getHistorialDeTareasPorIdIntanciaDeTarea");
		log.debug("idInstanciaDeTarea: " + idInstanciaDeTarea);				
		List<HistoricoDeInstDeTareaDTO> historicosDeInstDeTareaDTO = new ArrayList<HistoricoDeInstDeTareaDTO>();
		historicoDeInstDeTareaService.cargaHistorialDeTareasPorIdIntanciaDeTarea(idInstanciaDeTarea, historicosDeInstDeTareaDTO);
		return historicosDeInstDeTareaDTO;		
	}	
	
	@RequestMapping(value = "/getInstanciaDeTareaPorIdDiagramaTareaNombreExpediente/{idDiagramaTarea}/{nombreExpediente}", method = RequestMethod.GET)
	public @ResponseBody InstanciaDeTareaDTO getInstanciaDeTareaPorIdDiagramaTareaNombreExpediente(@PathVariable("idDiagramaTarea") String idDiagramaTarea,
																						@PathVariable("nombreExpediente") String nombreExpediente,
																						Model model, HttpServletRequest request) {
		log.debug("Inicio getInstanciaDeTareaPorIdDiagramaTareaNombreExpediente");
		log.debug("idDiagramaTarea: " + idDiagramaTarea);	
		log.debug("nombreExpediente: " + nombreExpediente);	
		InstanciaDeTareaDTO instanciaDeTareaDTO;
		try {
			instanciaDeTareaDTO = instanciaDeTareaService.getInstanciaDeTareaPorIdDiagramaTareaNombreExpediente(idDiagramaTarea, nombreExpediente);
			instanciaDeTareaDTO.setEstado("0");
			instanciaDeTareaDTO.setGlosa("OK");
			log.debug(instanciaDeTareaDTO.toString());
			return instanciaDeTareaDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			instanciaDeTareaDTO = new InstanciaDeTareaDTO();
			instanciaDeTareaDTO.setEstado("1");
			instanciaDeTareaDTO.setGlosa("ERROR: " + e.getMessage());
			return instanciaDeTareaDTO;
		}
		
	}	
	
}
