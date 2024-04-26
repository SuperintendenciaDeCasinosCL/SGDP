package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.util.ArrayList;
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
import cl.gob.scj.sgdp.dto.BitacoraSubTareaDTO;
import cl.gob.scj.sgdp.dto.TipoActividadBitacoraDTO;
import cl.gob.scj.sgdp.service.BitacoraSubTareaService;
import cl.gob.scj.sgdp.service.TipoActividadBitacoraService;
import cl.gob.scj.sgdp.tipos.PermisoType;


@Controller
public class BitacoraSubTareasControl {
	

	@Autowired
	private BitacoraSubTareaService bitacoraSubTareaService;
	
	@Autowired
	private TipoActividadBitacoraService tipoActividadService;

	private static final Logger log = Logger.getLogger(BitacoraSubTareasControl.class);

	@RequestMapping(value = "/bitacoraSubTareas/jsp/{idInstTarea}/{idEstadoTarea}", method = RequestMethod.GET)
	public String bitacoraSubTareas(Model model, @PathVariable Long idInstTarea, @PathVariable Integer idEstadoTarea, HttpServletRequest request) {
		
		log.debug("Inicio cargaPantallIndicador");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		model.addAttribute("idInstTarea", idInstTarea);
		model.addAttribute("idEstadoTarea", idEstadoTarea);
		return "bitacoraSubTareas";

	}

	@RequestMapping(value="/bitacoraSubTareas/tiposActividad", method=RequestMethod.GET)
	public @ResponseBody List<TipoActividadBitacoraDTO> getTodosLosTiposDeActividades() {			
		return tipoActividadService.getAll();
	}
	
	@RequestMapping(value="/bitacoraSubTareas/{idInstTarea}", method=RequestMethod.GET)
	public @ResponseBody List<BitacoraSubTareaDTO> getAllByIdInstTarea(@PathVariable Long idInstTarea, HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		List<BitacoraSubTareaDTO> listFinal = new ArrayList<>();
		List<BitacoraSubTareaDTO> list = bitacoraSubTareaService.getAllByIdInstTarea(idInstTarea);
		
		for(BitacoraSubTareaDTO bit : list) {
			bit.setCanDelete(bit.getIdUsuario().equals(usuario.getIdUsuario()));
			listFinal.add(bit);
		}
		
		return listFinal;
	}
	
	@RequestMapping(value="/bitacoraSubTareas", method=RequestMethod.POST)
	public @ResponseBody BitacoraSubTareaDTO save(@RequestBody BitacoraSubTareaDTO bitacora, HttpServletRequest request) {	
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		bitacora.setIdUsuario(usuario.getIdUsuario());
		bitacora.setFecha(new Date(System.currentTimeMillis()));
		return bitacoraSubTareaService.save(bitacora);
	}
	
	@RequestMapping(value="/bitacoraSubTareas", method=RequestMethod.DELETE)
	public @ResponseBody boolean delete(@RequestBody BitacoraSubTareaDTO bitacora, HttpServletRequest request) {	
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		if(usuario.getIdUsuario().equals(bitacora.getIdUsuario())) {
			return bitacoraSubTareaService.delete(bitacora);
		} else {
			return false;
		}
		
	}
	
	@RequestMapping(value = "/mantenedorTiposSubtareaBitacora", method = RequestMethod.GET)	
	public String mantenedorUnidadesOperativas(Model model, HttpServletRequest request) {		
		log.debug("Inicio mantenedorTiposSubtareaBitacora");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		if(usuario == null ) {
			return "error403";
		}
		String vista = "mantenedorTiposSubtareaBitacora";
		String permisoMantenedorTiposSubtareaBitacora = PermisoType.PUEDE_MANTENER_TIPOS_SUBTAREAS_BITACORA.getNombrePermiso();
		String permisoMantenedorTiposSubtareaBitacoraUsr = usuario.getPermisos().get(permisoMantenedorTiposSubtareaBitacora);

		if (permisoMantenedorTiposSubtareaBitacoraUsr==null || (permisoMantenedorTiposSubtareaBitacoraUsr!=null && !permisoMantenedorTiposSubtareaBitacora.equals(permisoMantenedorTiposSubtareaBitacoraUsr))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos TiposSubtareaBitacora");
			//Map<Long, ParametroDTO> parametrosMap = parametroService.getParametrosMap();			
			model.addAttribute("TiposSubtareaBitacora", tipoActividadService.getAllActive());
		}			
		return vista;
	}
	
	@RequestMapping(value = "/nuevoTipoSubTareaBitacora", method = RequestMethod.GET)
	public String nuevaUnidadOperativa(Model model, HttpServletRequest request) {
	
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/creaTipoSubtareaBitacora";
		String permisoMantenedorTiposSubtareaBitacora = PermisoType.PUEDE_MANTENER_TIPOS_SUBTAREAS_BITACORA.getNombrePermiso();
		String permisoMantenedorTiposSubtareaBitacoraUsr = usuario.getPermisos().get(permisoMantenedorTiposSubtareaBitacora);

		if (permisoMantenedorTiposSubtareaBitacoraUsr==null || (permisoMantenedorTiposSubtareaBitacoraUsr!=null && !permisoMantenedorTiposSubtareaBitacora.equals(permisoMantenedorTiposSubtareaBitacoraUsr))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} 
		
		return vista;
	}
	
	@RequestMapping(value = "/editaTipoSubTareaBitacora", method = RequestMethod.GET)
	public String editaTipoSubTareaBitacora(Model model, HttpServletRequest request) {
	
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/editaTipoSubtareaBitacoraCuerpo";
		String permisoMantenedorTiposSubtareaBitacora = PermisoType.PUEDE_MANTENER_TIPOS_SUBTAREAS_BITACORA.getNombrePermiso();
		String permisoMantenedorTiposSubtareaBitacoraUsr = usuario.getPermisos().get(permisoMantenedorTiposSubtareaBitacora);

		if (permisoMantenedorTiposSubtareaBitacoraUsr==null || (permisoMantenedorTiposSubtareaBitacoraUsr!=null && !permisoMantenedorTiposSubtareaBitacora.equals(permisoMantenedorTiposSubtareaBitacoraUsr))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} 
		
		return vista;
	}
	
	@RequestMapping(value = "/eliminaTipoSubTareaBitacora/{idTipoActividad}", method = RequestMethod.DELETE)
	public @ResponseBody Boolean eliminaTipoSubTareaBitacora(@PathVariable Long idTipoActividad, HttpServletRequest request) {
	
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String permisoMantenedorTiposSubtareaBitacora = PermisoType.PUEDE_MANTENER_TIPOS_SUBTAREAS_BITACORA.getNombrePermiso();
		String permisoMantenedorTiposSubtareaBitacoraUsr = usuario.getPermisos().get(permisoMantenedorTiposSubtareaBitacora);

		if (permisoMantenedorTiposSubtareaBitacoraUsr==null || (permisoMantenedorTiposSubtareaBitacoraUsr!=null && !permisoMantenedorTiposSubtareaBitacora.equals(permisoMantenedorTiposSubtareaBitacoraUsr))) {
			log.debug("Usuario no tiene permiso para mantener tipos de subtarea de bitacora");
			return false;
		} 
		
		
		
		return tipoActividadService.remove(idTipoActividad);

	}
	
	@RequestMapping(value = "creaTipoSubtareaBitacora", method = RequestMethod.POST)
	public @ResponseBody TipoActividadBitacoraDTO creaTipoSubtareaBitacora(@RequestBody TipoActividadBitacoraDTO tipo) {
		log.debug("Inicio cre unidadOperativa");		
		log.debug(tipo.toString());
		try {
			tipo.setEstado("OK");
			if(tipoActividadService.save(tipo)) {
				tipo.setEstado("OK");
			} else {
				tipo.setEstado("ERROR");
				tipo.setGlosa("No se pudo guardar en base de datos");
			}
			
			return tipo;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			tipo.setEstado("ERROR");
			tipo.setGlosa(e.getMessage());
			return tipo;
		}	
	}
	
	@RequestMapping(value = "/editaTipoSubtareaBitacora/{id}", method = RequestMethod.GET)
	public String editaUnidad(@PathVariable("id") long id, Model model, HttpServletRequest request) {
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/editaTipoSubtareaBitacoraCuerpo";
		String permisoMantenedorTiposSubtareaBitacora = PermisoType.PUEDE_MANTENER_TIPOS_SUBTAREAS_BITACORA.getNombrePermiso();
		String permisoMantenedorTiposSubtareaBitacoraUsr = usuario.getPermisos().get(permisoMantenedorTiposSubtareaBitacora);

		if (permisoMantenedorTiposSubtareaBitacoraUsr==null || (permisoMantenedorTiposSubtareaBitacoraUsr!=null && !permisoMantenedorTiposSubtareaBitacora.equals(permisoMantenedorTiposSubtareaBitacoraUsr))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado edita unidad operativa");			
			model.addAttribute("TipoActividadBitacoraDTO", tipoActividadService.getById(id));
			
		}			
		return vista;
	}	
	
	@RequestMapping(value = "editarTipoSubtareaBitacora", method = RequestMethod.POST)
	public @ResponseBody TipoActividadBitacoraDTO editarTipoSubtareaBitacora(@RequestBody TipoActividadBitacoraDTO tipo) {
		log.debug("Inicio cre unidadOperativa");		
		log.debug(tipo.toString());
		try {
			tipo.setEstado("OK");
			if(tipoActividadService.update(tipo)) {
				tipo.setEstado("OK");
			} else {
				tipo.setEstado("ERROR");
				tipo.setGlosa("No se pudo guardar en base de datos");
			}
			
			return tipo;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			tipo.setEstado("ERROR");
			tipo.setGlosa(e.getMessage());
			return tipo;
		}	
	}
	
	@RequestMapping(value = "/mantenedorTiposSubtareaBitacoraCuerpo", method = RequestMethod.GET)	
	public String mantenedorTiposSubtareaBitacoraCuerpo(Model model, HttpServletRequest request) {		
		log.debug("Inicio mantenedorTiposSubtareaBitacoraCuerpo");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		if(usuario == null ) {
			return "error403";
		}
		String vista = "div/mantenedorTiposSubtareaBitacoraCuerpo";
		String permisoMantenedorTiposSubtareaBitacora = PermisoType.PUEDE_MANTENER_TIPOS_SUBTAREAS_BITACORA.getNombrePermiso();
		String permisoMantenedorTiposSubtareaBitacoraUsr = usuario.getPermisos().get(permisoMantenedorTiposSubtareaBitacora);

		if (permisoMantenedorTiposSubtareaBitacoraUsr==null || (permisoMantenedorTiposSubtareaBitacoraUsr!=null && !permisoMantenedorTiposSubtareaBitacora.equals(permisoMantenedorTiposSubtareaBitacoraUsr))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos TiposSubtareaBitacora");
			//Map<Long, ParametroDTO> parametrosMap = parametroService.getParametrosMap();			
			model.addAttribute("TiposSubtareaBitacora", tipoActividadService.getAll());
		}			
		return vista;
	}
	
}
