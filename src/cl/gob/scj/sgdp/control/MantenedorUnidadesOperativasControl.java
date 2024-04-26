package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;

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
import cl.gob.scj.sgdp.dto.UnidadDTO;
import cl.gob.scj.sgdp.dto.UnidadOperativaDTO;
import cl.gob.scj.sgdp.service.UnidadOperativaService;
import cl.gob.scj.sgdp.service.UnidadService;
import cl.gob.scj.sgdp.tipos.PermisoType;

@Controller
public class MantenedorUnidadesOperativasControl {

	private static final Logger log = Logger.getLogger(MantenedorUnidadesOperativasControl.class);	
	
	@Autowired
	private UnidadOperativaService unidadOperativaService;
	
	
	@RequestMapping(value = "/mantenedorUnidadesOperativas", method = RequestMethod.GET)	
	public String mantenedorUnidadesOperativas(Model model, HttpServletRequest request) {		
		log.debug("Inicio mantenedorUnidadesOperativas");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "mantenedorUnidadesOperativas";
		String permisoMantenedorUnidadOP = PermisoType.PUEDE_MANTENER_UNIDADES_OPERATIVAS.getNombrePermiso();
		String permisoUsrMantenedorUnidadesOP = usuario.getPermisos().get(permisoMantenedorUnidadOP);
		log.debug("permisoMantenedorUnidadOP: " + permisoMantenedorUnidadOP );
		log.debug("permisoUsrMantenedorUnidadesOP: " + permisoUsrMantenedorUnidadesOP);		
		if (permisoUsrMantenedorUnidadesOP==null || (permisoUsrMantenedorUnidadesOP!=null && !permisoUsrMantenedorUnidadesOP.equals(permisoMantenedorUnidadOP))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos Unidades");
			//Map<Long, ParametroDTO> parametrosMap = parametroService.getParametrosMap();			
			model.addAttribute("unidadOperativaDTO", unidadOperativaService.getTodasUidadesOperativas());
		}			
		return vista;
	}
	
	
	
	@RequestMapping(value = "/nuevaUnidadOperativa", method = RequestMethod.GET)
	public String nuevaUnidadOperativa(Model model, HttpServletRequest request) {
		log.debug("Inicio nuevaUnidadOperativa");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/creaUnidadOperativaCuerpo";
		String permisoMantenedorUnidadOP = PermisoType.PUEDE_MANTENER_UNIDADES_OPERATIVAS.getNombrePermiso();
		String permisoUsrMantenedorUnidadesOP = usuario.getPermisos().get(permisoMantenedorUnidadOP);
		log.debug("permisoMantenedorUnidadOP: " + permisoMantenedorUnidadOP );
		log.debug("permisoUsrMantenedorUnidadesOP: " + permisoUsrMantenedorUnidadesOP);		
		if (permisoUsrMantenedorUnidadesOP==null || (permisoUsrMantenedorUnidadesOP!=null && !permisoUsrMantenedorUnidadesOP.equals(permisoMantenedorUnidadOP))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos nuevo unidad operativa");				
			//model.addAttribute("todasLasUnidadesOperativasDTO", unidadOperativaService.getTodasUidadesOperativas());
			
		}			
		return vista;
	}
	
	
	
	@RequestMapping(value = "/mantenedorUnidadesOperativasCuerpo", method = RequestMethod.GET)	
	public String mantenedorUnidadesOperativasCuerpo(Model model, HttpServletRequest request) {		
		log.debug("Inicio mantenedorUnidadesOperativasCuerpo");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/mantenedorUnidadesOperativasCuerpo";
		String permisoMantenedorUnidadOP = PermisoType.PUEDE_MANTENER_UNIDADES_OPERATIVAS.getNombrePermiso();
		String permisoUsrMantenedorUnidadesOP = usuario.getPermisos().get(permisoMantenedorUnidadOP);
		log.debug("permisoMantenedorUnidadOP: " + permisoMantenedorUnidadOP );
		log.debug("permisoUsrMantenedorUnidadesOP: " + permisoUsrMantenedorUnidadesOP);		
		if (permisoUsrMantenedorUnidadesOP==null || (permisoUsrMantenedorUnidadesOP!=null && !permisoUsrMantenedorUnidadesOP.equals(permisoMantenedorUnidadOP))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos mantenedorUnidadesOperativasCuerpo");			
			model.addAttribute("unidadOperativaDTO", unidadOperativaService.getTodasUidadesOperativas());
		}			
		return vista;
	}
	
	
	@RequestMapping(value = "creaUnidadOperativa", method = RequestMethod.POST)
	public @ResponseBody UnidadOperativaDTO creaUnidadOperativa(@RequestBody UnidadOperativaDTO unidadOperativaDTO) {
		log.debug("Inicio cre unidadOperativa");		
		log.debug(unidadOperativaDTO.toString());
		try {
			return unidadOperativaService.creaUnidadOperativa(unidadOperativaDTO);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			unidadOperativaDTO.setEstado("ERROR");
			unidadOperativaDTO.setGlosa(e.getMessage());
			return unidadOperativaDTO;
		}	
	}
	

	@RequestMapping(value = "/editaUnidadOperativa/{idUnidadOperativa}", method = RequestMethod.GET)
	public String editaUnidad(@PathVariable("idUnidadOperativa") long idUnidadOperativa, Model model, HttpServletRequest request) {
		log.debug("Inicio editaUnidadOperativa");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/editaUnidadOperativaCuerpo";
		String permisoMantenedorUnidadOP = PermisoType.PUEDE_MANTENER_UNIDADES_OPERATIVAS.getNombrePermiso();
		String permisoUsrMantenedorUnidadesOP = usuario.getPermisos().get(permisoMantenedorUnidadOP);
		log.debug("permisoMantenedorUnidadOP: " + permisoMantenedorUnidadOP );
		log.debug("permisoUsrMantenedorUnidadesOP: " + permisoUsrMantenedorUnidadesOP);		
		if (permisoUsrMantenedorUnidadesOP==null || (permisoUsrMantenedorUnidadesOP!=null && !permisoUsrMantenedorUnidadesOP.equals(permisoMantenedorUnidadOP))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado edita unidad operativa");			
			model.addAttribute("unidadOperativaDTO", unidadOperativaService.getUnidadOperativPorId(idUnidadOperativa));
			
		}			
		return vista;
	}	
	
	
	@RequestMapping(value = "actualizaUnidadOperativa", method = RequestMethod.POST)
	public @ResponseBody UnidadOperativaDTO actualizaUnidadOperativa(@RequestBody UnidadOperativaDTO unidadOperativaDTO) {
		log.debug("Inicio Actualiza Unidad Operativa");		
		log.debug(unidadOperativaDTO.toString());
		try {
			return unidadOperativaService.actualizaUnidadOperativa(unidadOperativaDTO);	
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			unidadOperativaDTO.setEstado("ERROR");
			unidadOperativaDTO.setGlosa(e.getMessage());
			return unidadOperativaDTO;
		}	
	}
}
