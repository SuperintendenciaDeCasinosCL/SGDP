package cl.gob.scj.sgdp.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.MacroProcesoDTO;
import cl.gob.scj.sgdp.service.BandejaDeEntradaService;
import cl.gob.scj.sgdp.service.HabilitaFuncionalidadService;
import cl.gob.scj.sgdp.service.ParametroService;

@Component
@Scope("singleton")
public class AppContextControl {

	private static final Logger log = Logger.getLogger(AppContextControl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;	
	
	@Autowired
	private BandejaDeEntradaService bandejaDeEntradaService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private HabilitaFuncionalidadService habilitaFuncionalidadService;
	
	private static String urlReporteSGDP;
	
	private static String urlIndicadoresIgestion;	
	
	private static String urlFuncPhp;
	
	private static Map<Long, Boolean> habilitaFuncionalidadMap;

	private static String dominioCorreo;
	
	@PostConstruct
	public void init() {
		log.debug("Inico carga datos globales");
		log.info("System Property - dominio.correo: " + System.getProperty("sgdp.dominio.correo"));
		log.info("System Env - DOMINIO_CORREO: " + System.getenv("SGDP_DOMINIO_CORREO"));
		dominioCorreo = System.getenv("SGDP_DOMINIO_CORREO")!=null ? System.getenv("SGDP_DOMINIO_CORREO") : System.getProperty("sgdp.dominio.correo");
		log.info("dominioCorreo: " + dominioCorreo);
		urlReporteSGDP = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_REPORTE_SGDP).getValorParametroChar();
		urlIndicadoresIgestion = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_INDICADORES_IGESTION).getValorParametroChar();
		urlFuncPhp = parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FUNC_PHP).getValorParametroChar();
		habilitaFuncionalidadMap = habilitaFuncionalidadService.getAllHabilitaFuncionalidadMap();
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

	public static String getUrlFuncPhp() {
		return urlFuncPhp;
	}
	
	public static Map<Long, Boolean> getHabilitaFuncionalidadMap() {
		return habilitaFuncionalidadMap;
	}

	public static String getDominioCorreo() {
		return dominioCorreo;
	}

}