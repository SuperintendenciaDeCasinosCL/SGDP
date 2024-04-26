package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

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
import cl.gob.scj.sgdp.service.UnidadOperativaService;
import cl.gob.scj.sgdp.service.UnidadService;
import cl.gob.scj.sgdp.tipos.PermisoType;

@Controller
public class MantenedorUnidadesControl {
	
	private static final Logger log = Logger.getLogger(MantenedorUnidadesControl.class);	
	
	@Autowired
	private UnidadService unidadService;
	
	@Autowired
	private UnidadOperativaService unidadOperativaService;
	
	@RequestMapping(value = "/mantenedorUnidades", method = RequestMethod.GET)	
	public String mantenedorUnidades(Model model, HttpServletRequest request) {		
		log.debug("Inicio mantenedorUnidades");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "mantenedorUnidades";
		String permisoMantenedorUnidad = PermisoType.PUEDE_MANTENER_UNIDADES.getNombrePermiso();
		String permisoUsrMantenedorUnidades = usuario.getPermisos().get(permisoMantenedorUnidad);
		log.debug("permisoMantenedorUnidad: " + permisoMantenedorUnidad );
		log.debug("permisoUsrMantenedorUnidades: " + permisoUsrMantenedorUnidades);		
		if (permisoUsrMantenedorUnidades==null || (permisoUsrMantenedorUnidades!=null && !permisoUsrMantenedorUnidades.equals(permisoMantenedorUnidad))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos Unidades");
			//Map<Long, ParametroDTO> parametrosMap = parametroService.getParametrosMap();			
			model.addAttribute("unidadDTO", unidadService.getTodasLasUnidadesDTO());
		}			
		return vista;
	}
	

	@RequestMapping(value = "/nuevaUnidad", method = RequestMethod.GET)
	public String nuevaUnidad(Model model, HttpServletRequest request) {
		log.debug("Inicio nuevaUnidad");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/creaUnidadCuerpo";
		String permisoMantenedorUnidad = PermisoType.PUEDE_MANTENER_UNIDADES.getNombrePermiso();
		String permisoUsrMantenedorUnidades = usuario.getPermisos().get(permisoMantenedorUnidad);
		log.debug("permisoMantenedorUnidad: " + permisoMantenedorUnidad );
		log.debug("permisoUsrMantenedorUnidades: " + permisoUsrMantenedorUnidades);		
		if (permisoUsrMantenedorUnidades==null || (permisoUsrMantenedorUnidades!=null && !permisoUsrMantenedorUnidades.equals(permisoMantenedorUnidad))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos nuevo unidad");				
			model.addAttribute("todasLasUnidadesOperativasDTO", unidadOperativaService.getTodasUidadesOperativas());
			
		}			
		return vista;
	}
	
	
	@RequestMapping(value = "/mantenedorUnidadesCuerpo", method = RequestMethod.GET)	
	public String mantenedorUnidadesCuerpo(Model model, HttpServletRequest request) {		
		log.debug("Inicio mantenedorUnidadesCuerpo");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/mantenedorUnidadesCuerpo";
		String permisoMantenedorUnidad = PermisoType.PUEDE_MANTENER_UNIDADES.getNombrePermiso();
		String permisoUsrMantenedorUnidades = usuario.getPermisos().get(permisoMantenedorUnidad);
		log.debug("permisoMantenedorUnidad: " + permisoMantenedorUnidad );
		log.debug("permisoUsrMantenedorUnidades: " + permisoUsrMantenedorUnidades);		
		if (permisoUsrMantenedorUnidades==null || (permisoUsrMantenedorUnidades!=null && !permisoUsrMantenedorUnidades.equals(permisoMantenedorUnidad))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos");			
			model.addAttribute("unidadDTO", unidadService.getTodasLasUnidadesDTO());
		}			
		return vista;
	}
	
	
	@RequestMapping(value = "creaUnidad", method = RequestMethod.POST)
	public @ResponseBody UnidadDTO creaUnidad(@RequestBody UnidadDTO unidadDTO) {
		log.debug("Inicio creaUnidad");		
		log.debug(unidadDTO.toString());
		try {
			return unidadService.creaUnidad(unidadDTO);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			unidadDTO.setEstado("ERROR");
			unidadDTO.setGlosa(e.getMessage());
			return unidadDTO;
		}	
	}
	
	
	
	
	@RequestMapping(value = "/editaUnidad/{idUnidad}", method = RequestMethod.GET)
	public String editaUnidad(@PathVariable("idUnidad") long idUnidad, Model model, HttpServletRequest request) {
		log.debug("Inicio editaUnidad");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/editaUnidadCuerpo";
		String permisoMantenedorUnidad = PermisoType.PUEDE_MANTENER_UNIDADES.getNombrePermiso();
		String permisoUsrMantenedorUnidades = usuario.getPermisos().get(permisoMantenedorUnidad);
		log.debug("permisoMantenedorUnidad: " + permisoMantenedorUnidad );
		log.debug("permisoUsrMantenedorUnidades: " + permisoUsrMantenedorUnidades);		
		if (permisoUsrMantenedorUnidades==null || (permisoUsrMantenedorUnidades!=null && !permisoUsrMantenedorUnidades.equals(permisoMantenedorUnidad))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado edita unidad");			
			model.addAttribute("unidadDTO", unidadService.getUinidadPorId(idUnidad));
			model.addAttribute("todasLasUnidadesOperativasDTO", unidadOperativaService.getTodasUidadesOperativas());
			
		}			
		return vista;
	}	
	
	
	@RequestMapping(value = "actualizaUnidad", method = RequestMethod.POST)
	public @ResponseBody UnidadDTO actualizaUnidad(@RequestBody UnidadDTO unidadDTO) {
		log.debug("Inicio Actualiza Unidad");		
		log.debug(unidadDTO.toString());
		try {
			return unidadService.actualizaUnidad(unidadDTO);	
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			unidadDTO.setEstado("ERROR");
			unidadDTO.setGlosa(e.getMessage());
			return unidadDTO;
		}	
	}
	
	
	
	@RequestMapping(value = "/getUnidadesPorUnidadOperativa/{idUnidadOperativa}", method = RequestMethod.GET)	
	public String getUnidadesPorUnidadOperativa(@PathVariable("idUnidadOperativa") long idUnidadOperativa, Model model, HttpServletRequest request) {		
		
		log.debug("Inicio getUnidadesPorUnidadOperativa");	
		
		String vista = "";
		if ( idUnidadOperativa == 0) {
			
			vista = "div/unidadPorUnidadOperativaCero";
			
		}else {
		
			 vista = "div/unidadPorUnidadOperativa";
			
			try {
				List<UnidadDTO> unidades = unidadService.getUnidadesPorIdUnidadOperativa(idUnidadOperativa);
				model.addAttribute("listaUnidades", unidades);
				  
			 }catch (Exception e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					log.error(exceptionAsString);
			}
		}
		return vista;	
	}
	
	
	@RequestMapping(value = "/getUnidadesPorUnidadOperativaEdita/{idUnidadOperativa}", method = RequestMethod.GET)	
	public String getUnidadesPorUnidadOperativaEdita(@PathVariable("idUnidadOperativa") long idUnidadOperativa, Model model, HttpServletRequest request) {		
		
		log.debug("Inicio getUnidadesPorUnidadOperativaEdita");					
		String vista = "div/unidadPorUnidadOperativaEdita";
		
		try {
			List<UnidadDTO> unidades = unidadService.getUnidadesPorIdUnidadOperativa(idUnidadOperativa);
			model.addAttribute("listaUnidades", unidades);
			  
		 }catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
		}
		return vista;	
	}

	
	
	@RequestMapping(value = "/getUnidadesPorUnidadOperativaUsuario/{idUnidadOperativa}", method = RequestMethod.GET)	
	public String getUnidadesPorUnidadOperativaUsuario(@PathVariable("idUnidadOperativa") long idUnidadOperativa, Model model, HttpServletRequest request) {		
		
		log.debug("Inicio getUnidadesPorUnidadOperativaUsuario: " +idUnidadOperativa);
		
		String vista = "";
		if ( idUnidadOperativa == 0) {
			
			vista = "div/unidadOperativaCero";
			
		}else {
			vista = "div/unidadPorUnidadOperativaUsuario";
			
			try {
				List<UnidadDTO> unidades = unidadService.getUnidadesPorIdUnidadOperativa(idUnidadOperativa);
				model.addAttribute("listaUnidades", unidades);
				  
			 }catch (Exception e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					log.error(exceptionAsString);
			}
		}
			
		
		
		return vista;	
	}
	
	
	
	@RequestMapping(value = "/getUnidadesPorUnidadOperativaUsuarioEdita/{idUnidadOperativa}", method = RequestMethod.GET)	
	public String getUnidadesPorUnidadOperativaUsuarioEdita(@PathVariable("idUnidadOperativa") long idUnidadOperativa, Model model, HttpServletRequest request) {		
		
		log.debug("Inicio getUnidadesPorUnidadOperativaUsuarioEdita");					
		String vista = "div/unidadePorUnidadOperativaUsuarioEdita";
		
		try {
			List<UnidadDTO> unidades = unidadService.getUnidadesPorIdUnidadOperativa(idUnidadOperativa);
			model.addAttribute("listaUnidades", unidades);
			  
		 }catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
		}
		return vista;	
	}


}
