package cl.gob.scj.sgdp.control;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.InstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.service.DiagramaDeProceso2Service;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;
import cl.gob.scj.sgdp.service.ParametroService;

@Controller
public class DiagramaDeProceso2Control {

	private static final Logger log = Logger.getLogger(DiagramaDeProceso2Control.class);
			
	@Autowired
	DiagramaDeProceso2Service diagramaDeProceso2Service;
	
	@Autowired
	private InstanciaDeProcesoService instanciaDeProcesoService;
	
	@Autowired
	private ParametroService parametroService;
	
	@RequestMapping("/diagramaProceso2/{idDiagrama}/{idProceso}/{idInstanciaDeProceso}/{idExpediente}")
	public String diagramaProceso2(Model model , @PathVariable("idDiagrama") String idDiagrama,
										  @PathVariable("idProceso") String idProceso,
										  @PathVariable("idInstanciaDeProceso") long idInstanciaDeProceso,
										  @PathVariable("idExpediente") String idExpediente){
		
		InstanciaDeProcesoDTO instanciaDeProcesoDTO = new InstanciaDeProcesoDTO();
		
		instanciaDeProcesoService.cargaInstanciaDeProcesoDTOPorIdExpediente(idExpediente, instanciaDeProcesoDTO);				
		
		
		model.addAttribute("idDiagrama",idDiagrama);
		model.addAttribute("idProceso",idProceso);
		model.addAttribute("idInstanciaDeProceso",idInstanciaDeProceso);
		model.addAttribute("instanciaDeProcesoDTO",instanciaDeProcesoDTO);
		model.addAttribute("flagBuscarDiagrama",0); // Flag para diferenciar el metodo que viene con sobrecarga
		return "modals/diagramaDeProceso2";
	}
	
	
	@RequestMapping("/diagramaProceso2/{idExpediente}")
	public String diagramaProceso2PorIdExpediente(Model model ,@PathVariable("idExpediente") String idExpediente){
		
		InstanciaDeProcesoDTO instanciaDeProcesoDTO = new InstanciaDeProcesoDTO();
		
		instanciaDeProcesoService.cargaInstanciaDeProcesoDTOPorIdExpediente(idExpediente, instanciaDeProcesoDTO);				
		
		model.addAttribute("instanciaDeProcesoDTO",instanciaDeProcesoDTO);
		model.addAttribute("flagBuscarDiagrama",1);// Flag para diferenciar el metodo que viene con sobrecarga
		return "modals/diagramaDeProceso2";
	}
	
	@RequestMapping(value = "/diagramaDeProcesoReaperturaSalto/{nombreExpediente}", method = RequestMethod.GET)
	public String diagramaDeProcesoReaperturaSalto(Model model ,@PathVariable("nombreExpediente") String nombreExpediente) {
		log.debug("nombreExpediente: " + nombreExpediente);
		model.addAttribute("urlFuncPhp", parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FUNC_PHP).getValorParametroChar());
		model.addAttribute("nombreExpediente", nombreExpediente);	
		return "modals/diagramaDeProcesoReaperturaSalto";
	}
	
}
