package cl.gob.scj.sgdp.control;

import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.service.ParametroService;

@Controller
public class CargaSubProcesoControl {

	private static final Logger log = Logger.getLogger(BuscadorControl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private ParametroService parametroService;
	
	@RequestMapping("/cargaProcesosSerie")
	public String buscador(Model model, HttpServletRequest request) {
		
		log.debug("Inicio cargaProcesosSerie");
		log.info("Pasa por buscador");
		ParametroDTO phpUrl = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FUNC_PHP);
		log.info(phpUrl.toString());
		model.addAttribute("urlCamunda", phpUrl.getValorParametroChar() + "/sgdoc/proceso/bpm/");

		return configProps.getProperty("vistaCargaSubProceso");
	}
}
