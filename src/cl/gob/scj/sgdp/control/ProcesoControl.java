package cl.gob.scj.sgdp.control;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.EtapaDeInstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.dto.HistorialProcesoDTO;
import cl.gob.scj.sgdp.dto.ProcesoFormCreaExpDTO;
import cl.gob.scj.sgdp.service.ArchivosInstDeTareaService;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;
import cl.gob.scj.sgdp.service.InstanciaDeTareaService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.ProcesoFormCreaExpService;

@Controller
public class ProcesoControl {

	private static final Logger log = Logger.getLogger(ProcesoControl.class);

	@Autowired
	private InstanciaDeProcesoService instanciaDeProcesoService;
		
	@Autowired
	private InstanciaDeTareaService instanciaDeTareaService;

	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private ArchivosInstDeTareaService archivosInstDeTareaService;
	
	@Autowired
	private ProcesoFormCreaExpService procesoService;
	
	@Resource(name = "configProps")
	private Properties configProps;
		
	@RequestMapping("/getTablaHistorialDeProcesoPorIdExpediente")
	public String getTablaHistorialDeProcesoPorIdExpediente(@RequestParam("idExpediente") String idExpediente, Model model, HttpServletRequest request) {
		log.debug("Inicio getTablaHistorialDeProcesoPorIdExpediente");
		//List<HistorialProcesoDTO> historialProcesoDTO = instanciaDeProcesoService.getHistorialDelProceso(idExpediente);
		List<HistorialProcesoDTO> historialProcesoDTO = instanciaDeProcesoService.getHistoricoDeInstDeTareaPorIdExpediente(idExpediente);
		model.addAttribute("historialProcesoDTO", historialProcesoDTO);
		return configProps.getProperty("vistaTablaHistorialDeProceso");
	}	
	
	
	@RequestMapping("/getTablaHistorialDeDocumentoPorIdExpediente/{idExpediente}/{idInstanciaDeTarea}/{nombreExpediente}")
	public String getTablaHistorialDeDocumentoPorIdExpediente(
								@PathVariable("idExpediente") String idExpediente,
								@PathVariable("idInstanciaDeTarea") Integer idInstanciaDeTarea,
								@PathVariable("nombreExpediente") String nombreExpediente,
								Model model, 
								HttpServletRequest request) {
		
		log.debug("Inicio getTablaHistorialDeDocumentoPorIdExpediente");
		log.debug("idExpediente : " + idExpediente);
		log.debug("idInstanciaDeTarea : " + idInstanciaDeTarea);		
		
		List<ArchivosInstDeTareaDTO> todosLosDocSubidos = instanciaDeTareaService.getTodosLosDocSubidosPorIdInstTarea(idInstanciaDeTarea);
		
		model.addAttribute("nombreExpediente", nombreExpediente);
		model.addAttribute("todosLosDocSubidos", todosLosDocSubidos);
		model.addAttribute("idInstanciaDeTarea", idInstanciaDeTarea);
		model.addAttribute("urlFuncPhp", parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FUNC_PHP).getValorParametroChar());
		
		return "div/tablaHistorialDeDocumento";
	}	

	@RequestMapping("/getTablaDetalleDeExpedientePorIdExpediente")
	public String getTablaDetalleDeExpedientePorIdExpediente(@RequestParam("idExpediente") String idExpediente,
			Model model, HttpServletRequest request) {
		log.debug("Inicio getTablaDetalleDeExpedientePorIdExpediente");
		List<ArchivosInstDeTareaDTO> todosLosDocSubidos = archivosInstDeTareaService.getTodosLosDocSubidosPorIdExpediente(idExpediente);
		
		model.addAttribute("nombreExpediente", "");
		model.addAttribute("todosLosDocSubidos", todosLosDocSubidos);
		model.addAttribute("idInstanciaDeTarea", "0");
		model.addAttribute("urlFuncPhp", parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FUNC_PHP).getValorParametroChar());
		model.addAttribute("numeroTabla", "");
		
		return "div/tablaHistorialDeDocumento";
		
	}
	
	@RequestMapping("/getTablaDetalleDeExpedientePorIdExpediente2")
	public String getTablaDetalleDeExpedientePorIdExpediente2(@RequestParam("idExpediente") String idExpediente,
			Model model, HttpServletRequest request) {
		log.debug("Inicio getTablaDetalleDeExpedientePorIdExpediente");
		List<ArchivosInstDeTareaDTO> todosLosDocSubidos = archivosInstDeTareaService.getTodosLosDocSubidosPorIdExpediente(idExpediente);
		
		model.addAttribute("nombreExpediente", "");
		model.addAttribute("todosLosDocSubidos", todosLosDocSubidos);
		model.addAttribute("idInstanciaDeTarea", "0");
		model.addAttribute("urlFuncPhp", parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FUNC_PHP).getValorParametroChar());
		model.addAttribute("numeroTabla", "2");
		
		return "div/tablaHistorialDeDocumento";
		
	}		
	
	@RequestMapping("/getVistaEtapasInstanciaDeProcesoPorIdExpediente")
	public String getVistaEtapasInstanciaDeProcesoPorIdExpediente(@RequestParam("idExpediente") String idExpediente, Model model, HttpServletRequest request) {
		log.debug("Inicio getVistaEtapasInstanciaDeProcesoPorIdExpediente");
		List<EtapaDeInstanciaDeProcesoDTO> etapasDeInstanciaDeProcesoDTO = instanciaDeProcesoService.getEtapasDeInstanciaDeProcesoPorIdExpediente(idExpediente);
		model.addAttribute("etapasDeInstanciaDeProcesoDTO", etapasDeInstanciaDeProcesoDTO);
		return configProps.getProperty("vistaEtapasInstanciaDeProceso");
	}
	
	@RequestMapping(value="/getTodosProcesoFormCreaExp", method=RequestMethod.GET)
	public @ResponseBody List<ProcesoFormCreaExpDTO> getTiposDeDocumentosDTOPorNombreExpediente(HttpServletRequest request, HttpServletResponse response) {
		log.debug("Inicio getTiposDeDocumentosDTOPorNombreExpediente");
		return procesoService.getTodosProcesoFormCreaExp();
	}
	
}
