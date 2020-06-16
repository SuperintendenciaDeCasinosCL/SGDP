package cl.gob.scj.sgdp.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.MacroProcesoDTO;
import cl.gob.scj.sgdp.service.BandejaDeEntradaService;
import cl.gob.scj.sgdp.service.ParametroService;

@Controller
@Scope("singleton")
public class AppContextControl {

	private static final Logger log = Logger.getLogger(AppContextControl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;	
	
	@Autowired
	private BandejaDeEntradaService bandejaDeEntradaService;
	
	@Autowired
	private ParametroService parametroService;

	private static List<MacroProcesoDTO> macroProcesosDTO;
	
	private static String urlReporteSGDP;
	
	private static String urlIndicadoresIgestion;
	
	@PostConstruct
	public void init() {
		log.debug("Inico carga datos globales");
		macroProcesosDTO = new ArrayList<MacroProcesoDTO>();	
		macroProcesosDTO = bandejaDeEntradaService.getTodosLosMacroProcesos(macroProcesosDTO);
		urlReporteSGDP = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_REPORTE_SGDP).getValorParametroChar();
		urlIndicadoresIgestion = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_INDICADORES_IGESTION).getValorParametroChar();
	}
	
	public static List<MacroProcesoDTO> getMacroProcesosDTO() {
		return macroProcesosDTO;
	}

	public static String getUrlReporteSGDP() {
		return urlReporteSGDP;
	}

	public static String getUrlIndicadoresIgestion() {
		return urlIndicadoresIgestion;
	}

	public static void setUrlIndicadoresIgestion(String urlIndicadoresIgestion) {
		AppContextControl.urlIndicadoresIgestion = urlIndicadoresIgestion;
	}
	
}
