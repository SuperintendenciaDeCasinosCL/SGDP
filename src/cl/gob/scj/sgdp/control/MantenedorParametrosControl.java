package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.tipos.PermisoType;

@Controller
public class MantenedorParametrosControl {
	
	private static final Logger log = Logger.getLogger(MantenedorParametrosControl.class);	
	
	@Autowired
	ParametroService parametroService;	
	
	@RequestMapping(value = "/mantenedorParametros", method = RequestMethod.GET)	
	public String cargaPantallaMantenedorParametros(Model model, HttpServletRequest request) {		
		log.debug("Inicio cargaPantallaListaDeDistribucion");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "mantenedorParametros";
		String permisoMantenedorParametros = PermisoType.PUEDE_MANTENER_PARAMETROS.getNombrePermiso();
		String permisoUsrMantenedorParametros = usuario.getPermisos().get(permisoMantenedorParametros);
		log.debug("permisoMantenedorParametros: " + permisoMantenedorParametros);
		log.debug("permisoUsrMantenedorParametros: " + permisoUsrMantenedorParametros);		
		if (permisoUsrMantenedorParametros==null || (permisoUsrMantenedorParametros!=null && !permisoUsrMantenedorParametros.equals(permisoMantenedorParametros))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos");
			Map<Long, ParametroDTO> parametrosMap = parametroService.getParametrosMap();			
			model.addAttribute("parametrosMap", parametrosMap);
		}			
		return vista;
	}
	
	@RequestMapping(value = "/cargaParametros", method = RequestMethod.GET)
	public String cargaParametros(Model model, HttpServletRequest request) {		
		log.debug("Cargado datos");
		Map<Long, ParametroDTO> parametrosMap = parametroService.getParametrosMap();			
		model.addAttribute("parametrosMap", parametrosMap);			
		return "div/mantenedorParametrosCuerpo";
	}
	
	
	@RequestMapping(value = "creaParametro", method = RequestMethod.POST)
	public @ResponseBody ParametroDTO creaParametro(@RequestBody ParametroDTO parametroDTO) {
		log.debug("Inicio creaParametro");		
		log.debug(parametroDTO.toString());	
		try {			
			parametroService.insertaParametro(parametroDTO);
			parametroDTO.setRespuesta("OK");
			log.debug(parametroDTO.toString());
			return parametroDTO;
		} catch(Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			parametroDTO.setRespuesta("ERROR");
			return parametroDTO;			
		}		
	}
	
	@RequestMapping(value="/editaParametro/{idParametro}", method = RequestMethod.GET)
	public String editaParametro(@PathVariable("idParametro") long idParametro, Model model, HttpServletRequest request) {		
		log.debug("Inicio editaParametro");	
		log.debug("idParametro: " + idParametro);
		ParametroDTO parametroDTO = parametroService.getParametroPorID(idParametro);
		log.debug(parametroDTO.toString());
		model.addAttribute("parametroDTO", parametroDTO);			
		return "div/editaParametroCuerpo";
	}
	
	@RequestMapping(value = "actualizaParametro", method = RequestMethod.POST)
	public @ResponseBody ParametroDTO actualizaParametro(@RequestBody ParametroDTO parametroDTO) {
		log.debug("Inicio actualizaParametro");		
		log.debug(parametroDTO.toString());
		try {
			parametroService.actualizaParametroBD(parametroDTO);
			parametroService.actualizaParametro(parametroDTO);
			parametroDTO.setRespuesta("OK");
			return parametroDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			parametroDTO.setRespuesta("ERROR");
			return parametroDTO;
		}
	
	}
	
	@RequestMapping(value = "borraParametro/{idParametro}", method = RequestMethod.POST)
	public @ResponseBody String borraParametro(@PathVariable("idParametro") long idParametro, HttpServletRequest request) {
		log.debug("Inicio borraParametro");		
		log.debug("idParametro: " + idParametro);
		try {	
			parametroService.borraParametro(idParametro);
			return "OK";
		} catch(Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);				
			return "ERROR";			
		}		
	}

}
