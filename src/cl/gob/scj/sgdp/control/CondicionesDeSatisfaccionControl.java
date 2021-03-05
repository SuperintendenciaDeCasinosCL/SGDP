package cl.gob.scj.sgdp.control;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.gob.scj.sgdp.dto.HistoricoValorParametroDeTareaDTO;
import cl.gob.scj.sgdp.dto.ParametroDeTareaDTO;
import cl.gob.scj.sgdp.service.HistoricoValorParametroDeTareaService;
import cl.gob.scj.sgdp.service.ParametroDeTareaService;

@Controller
public class CondicionesDeSatisfaccionControl {
	
	private static final Logger log = Logger.getLogger(CondicionesDeSatisfaccionControl.class);
	
	@Autowired
	private ParametroDeTareaService parametroDeTareaService;
	
	@Autowired
	private HistoricoValorParametroDeTareaService historicoValorParametroDeTareaService;
	
	@RequestMapping(value = "/getCondicionesDeSatisfaccionPorIdInstanciaDeTarea/{idInstanciaDeTarea}" , method = RequestMethod.GET)
	public String getCondicionesDeSatisfaccionPorIdInstanciaDeTarea(@PathVariable("idInstanciaDeTarea") int idInstanciaDeTarea, Model model, HttpServletRequest request) {
		Map<String, List<ParametroDeTareaDTO>> mapParametrosDeTareaDTOTitulo = parametroDeTareaService.getMapParametrosDeTareaDTOTituloPorIdInstanciaDeTarea(idInstanciaDeTarea);
		model.addAttribute("mapParametrosDeTareaDTOTitulo", mapParametrosDeTareaDTOTitulo);
		return "div/condicionesDeSatisfaccion";
	}
	
	@RequestMapping(value="/getHistorialDeCondicionesDeSatisfaccionPorIdExpediente/{idExpediente}", method=RequestMethod.GET)
	public String getHistoricoValorParametroDeTareaPorIdExpediente(@PathVariable("idExpediente") String idExpediente, Model model, HttpServletRequest request) {
		log.debug("Inicio getHistoricoValorParametroDeTareaPorIdExpediente");
		log.debug("idExpediente: " + idExpediente);
		List<HistoricoValorParametroDeTareaDTO> historicoValorParametroDeTareaDTOList = historicoValorParametroDeTareaService.getHistoricoValorParametroDeTareaPorIdExpediente(idExpediente);
		model.addAttribute("historicoValorParametroDeTareaDTOList", historicoValorParametroDeTareaDTOList);
		return "div/historialDeCondicionesDeSatisfaccion";		
	}
	
	@RequestMapping(value="/getHistorialDeCondicionesDeSatisfaccionPorIdHistoricoDeInstDeTarea/{idHistoricoDeInstDeTarea}", method=RequestMethod.GET)
	public String getHistorialDeCondicionesDeSatisfaccionPorIdHistoricoInstanciaDeTarea(@PathVariable("idHistoricoDeInstDeTarea") long idHistoricoDeInstDeTarea, Model model, HttpServletRequest request) {
		log.debug("Inicio getHistorialDeCondicionesDeSatisfaccionPorIdInstanciaDeTarea");
		log.debug("idHistoricoDeInstDeTarea: " + idHistoricoDeInstDeTarea);
		List<HistoricoValorParametroDeTareaDTO> historicoValorParametroDeTareaDTOList = historicoValorParametroDeTareaService.getHistoricoValorParametroDeTareaPorIdHistoricoInstanciaDeTarea(idHistoricoDeInstDeTarea);
		model.addAttribute("historicoValorParametroDeTareaDTOList", historicoValorParametroDeTareaDTOList);
		return "div/historialDeCondicionesDeSatisfaccion";		
	}
	
	@RequestMapping(value="/getHistorialDeCondicionesDeSatisfaccionPorIdInstanciaDeTarea/{idInstanciaDeTarea}", method=RequestMethod.GET)
	public String historialDeCondicionesDeSatisfaccionPorIdInstanciaDeTarea(@PathVariable("idInstanciaDeTarea") long idInstanciaDeTarea, Model model, HttpServletRequest request) {
		log.debug("Inicio getHistorialDeCondicionesDeSatisfaccionPorIdInstanciaDeTarea");
		log.debug("idInstanciaDeTarea: " + idInstanciaDeTarea);
		List<HistoricoValorParametroDeTareaDTO> historicoValorParametroDeTareaDTOList = historicoValorParametroDeTareaService.getHistoricoValorParametroDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);
		model.addAttribute("historicoValorParametroDeTareaDTOList", historicoValorParametroDeTareaDTOList);
		return "div/historialDeCondicionesDeSatisfaccion";		
	}
	
	@RequestMapping(value = "/getCondDeSatisParaMostrarPorIdInstanciaDeTarea/{idInstanciaDeTarea}" , method = RequestMethod.GET)
	public String getCondDeSatisParaMostrarPorIdInstanciaDeTarea(@PathVariable("idInstanciaDeTarea") int idInstanciaDeTarea, Model model, HttpServletRequest request) {
		List<ParametroDeTareaDTO> parametrosDeTareaDTO = parametroDeTareaService.getParametrosDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);
		model.addAttribute("parametrosDeTareaDTO", parametrosDeTareaDTO);
		return "div/condicionesDeSatisfaccionParaMostrar";
	}

}