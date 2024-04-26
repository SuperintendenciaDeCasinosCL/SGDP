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
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.dto.UnidadDTO;
import cl.gob.scj.sgdp.dto.UsuarioRolDTO;
import cl.gob.scj.sgdp.service.RolService;
import cl.gob.scj.sgdp.service.UnidadOperativaService;
import cl.gob.scj.sgdp.service.UnidadService;
import cl.gob.scj.sgdp.service.UsuarioRolService;
import cl.gob.scj.sgdp.tipos.PermisoType;

@Controller
public class MantenedorUsuariosControl {
	
	private static final Logger log = Logger.getLogger(MantenedorUsuariosControl.class);	
	
	@Autowired
	private UsuarioRolService usuarioRolService;
	
	@Autowired
	private UnidadService unidadService;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private UnidadOperativaService unidadOperativaService;
	
	@RequestMapping(value = "/mantenedorUsuarios", method = RequestMethod.GET)	
	public String mantenedorUsuarios(Model model, HttpServletRequest request) {		
		log.debug("Inicio mantenedorUsuarios");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "mantenedorUsuarios";
		String permisoMantenedorUsuario = PermisoType.PUEDE_MANTENER_USUARIOS.getNombrePermiso();
		String permisoUsrMantenedorUsuarios = usuario.getPermisos().get(permisoMantenedorUsuario);
		log.debug("permisoMantenedorUsuario: " + permisoMantenedorUsuario);
		log.debug("permisoUsrMantenedorUsuarios: " + permisoUsrMantenedorUsuarios);		
		if (permisoUsrMantenedorUsuarios==null || (permisoUsrMantenedorUsuarios!=null && !permisoUsrMantenedorUsuarios.equals(permisoMantenedorUsuario))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos");
			//Map<Long, ParametroDTO> parametrosMap = parametroService.getParametrosMap();			
			model.addAttribute("usuariosRolDTO", usuarioRolService.getAll());
		}			
		return vista;
	}
	
	@RequestMapping(value = "/getUsuarios", method = RequestMethod.GET)
	public @ResponseBody List<?> getUsuarios(Model model, HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		List<?> usuarios = null;
		
		if (usuario != null) {
			usuarios = usuarioRolService.getAll();
		} 
		
		return usuarios;
	}
	
	@RequestMapping(value = "/nuevoUsuario", method = RequestMethod.GET)
	public String nuevoUsuario(Model model, HttpServletRequest request) {
		log.debug("Inicio nuevoUsuario");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/creaUsuarioCuerpo";
		String permisoMantenedorUsuario = PermisoType.PUEDE_MANTENER_USUARIOS.getNombrePermiso();
		String permisoUsrMantenedorUsuarios = usuario.getPermisos().get(permisoMantenedorUsuario);
		log.debug("permisoMantenedorParametros: " + permisoMantenedorUsuario);
		log.debug("permisoUsrMantenedorUsuarios: " + permisoUsrMantenedorUsuarios);		
		if (permisoUsrMantenedorUsuarios==null || (permisoUsrMantenedorUsuarios!=null && !permisoUsrMantenedorUsuarios.equals(permisoMantenedorUsuario))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos");				
			//model.addAttribute("todasLasUnidadesDTO", unidadService.getTodasLasUnidadesDTO());
			model.addAttribute("todasLasUnidadesOperativasDTO", unidadOperativaService.getTodasUidadesOperativas());
			model.addAttribute("todosLosRolesDTO", rolService.getAllRolesDTO());
			
		}			
		return vista;
	}
	
	@RequestMapping(value = "/mantenedorUsuariosCuerpo", method = RequestMethod.GET)	
	public String mantenedorUsuariosCuerpo(Model model, HttpServletRequest request) {		
		log.debug("Inicio mantenedorUsuariosCuerpo");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/mantenedorUsuariosCuerpo";
		String permisoMantenedorUsuario = PermisoType.PUEDE_MANTENER_USUARIOS.getNombrePermiso();
		String permisoUsrMantenedorUsuarios = usuario.getPermisos().get(permisoMantenedorUsuario);
		log.debug("permisoMantenedorUsuario: " + permisoMantenedorUsuario);
		log.debug("permisoUsrMantenedorUsuarios: " + permisoUsrMantenedorUsuarios);		
		if (permisoUsrMantenedorUsuarios==null || (permisoUsrMantenedorUsuarios!=null && !permisoUsrMantenedorUsuarios.equals(permisoMantenedorUsuario))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos");			
			model.addAttribute("usuariosRolDTO", usuarioRolService.getAll());
		}			
		return vista;
	}
	
	@RequestMapping(value = "creaUsuario", method = RequestMethod.POST)
	public @ResponseBody UsuarioRolDTO creaUsuario(@RequestBody UsuarioRolDTO usuarioRolDTO) {
		log.debug("Inicio creaUsuario");		
		log.debug(usuarioRolDTO.toString());
		try {
			return usuarioRolService.creaUsuarioRol(usuarioRolDTO);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			usuarioRolDTO.setEstado("ERROR");
			usuarioRolDTO.setGlosa(e.getMessage());
			return usuarioRolDTO;
		}	
	}
	
	@RequestMapping(value = "/editaUsuario/{idUsuarioRol}", method = RequestMethod.GET)
	public String editaUsuario(@PathVariable("idUsuarioRol") long idUsuarioRol, Model model, HttpServletRequest request) {
		log.debug("Inicio editaUsuario");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/editaUsuarioCuerpo";
		String permisoMantenedorUsuario = PermisoType.PUEDE_MANTENER_USUARIOS.getNombrePermiso();
		String permisoUsrMantenedorUsuarios = usuario.getPermisos().get(permisoMantenedorUsuario);
		log.debug("permisoMantenedorParametros: " + permisoMantenedorUsuario);
		log.debug("permisoUsrMantenedorUsuarios: " + permisoUsrMantenedorUsuarios);		
		if (permisoUsrMantenedorUsuarios==null || (permisoUsrMantenedorUsuarios!=null && !permisoUsrMantenedorUsuarios.equals(permisoMantenedorUsuario))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos");		
			UsuarioRolDTO usuarioRolDTO = usuarioRolService.getUsuarioRolDTOPorIdUsuarioRol(idUsuarioRol); 
			model.addAttribute("usuarioRolDTO", usuarioRolDTO);
			
			//model.addAttribute("todasLasUnidadesDTO", unidadService.getTodasLasUnidadesDTO());
			
			
			
			
			model.addAttribute("todasLasUnidadesOperativasDTO", unidadOperativaService.getTodasUidadesOperativas());
			
			
			RolDTO rolDto = rolService.getRolPorIdRol(usuarioRolDTO.getIdRol());
			model.addAttribute("rolDTO", rolDto);
			
			UnidadDTO unidadDTORol = unidadService.getUinidadPorId(rolDto.getIdUnidad());
			model.addAttribute("unidadDTORol" , unidadDTORol);
			
			//model.addAttribute("todosLosRolesDTO", rolService.getAllRolesDTO());
			model.addAttribute("rolesDTOUnidad", rolService.getRolesDTOPorIdUnidad(rolDto.getIdUnidad()));	
			
			model.addAttribute("unidadesDTOporOpertaiva", unidadService.getUnidadesPorIdUnidadOperativa(unidadDTORol.getIdUnidadOperativa()));	
			
		}			
		return vista;
	}	
	
	
	@RequestMapping(value = "actualizaUsuario", method = RequestMethod.POST)
	public @ResponseBody UsuarioRolDTO actualizaUsuario(@RequestBody UsuarioRolDTO usuarioRolDTO) {
		log.debug("Inicio creaUsuario");		
		log.debug(usuarioRolDTO.toString());
		try {
			return usuarioRolService.actualizaUsuario(usuarioRolDTO);		
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			usuarioRolDTO.setEstado("ERROR");
			usuarioRolDTO.setGlosa(e.getMessage());
			return usuarioRolDTO;
		}	
	}	
	
	
	

}
