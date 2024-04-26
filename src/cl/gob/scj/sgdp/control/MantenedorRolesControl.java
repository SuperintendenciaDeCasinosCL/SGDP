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
import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.dto.UnidadDTO;
import cl.gob.scj.sgdp.service.RolService;
import cl.gob.scj.sgdp.service.UnidadOperativaService;
import cl.gob.scj.sgdp.service.UnidadService;

import cl.gob.scj.sgdp.tipos.PermisoType;

@Controller
public class MantenedorRolesControl {
	
	
private static final Logger log = Logger.getLogger(MantenedorRolesControl.class);	
	

	
	@Autowired
	private UnidadService unidadService;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private UnidadOperativaService unidadOperativaService;
	
	@RequestMapping(value = "/mantenedorRoles", method = RequestMethod.GET)	
	public String mantenedorRoles(Model model, HttpServletRequest request) {		
		log.debug("Inicio mantenedorRoles");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "mantenedorRoles";
		String permisoMantenedorRol = PermisoType.PUEDE_MANTENER_CARGOS.getNombrePermiso();
		String permisoUsrMantenedorRoles = usuario.getPermisos().get(permisoMantenedorRol);
		log.debug("permisoMantenedorCargo: " + permisoMantenedorRol );
		log.debug("permisoUsrMantenedorCargos: " + permisoUsrMantenedorRoles);		
		if (permisoUsrMantenedorRoles==null || (permisoUsrMantenedorRoles!=null && !permisoUsrMantenedorRoles.equals(permisoMantenedorRol))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos Roles");
			//Map<Long, ParametroDTO> parametrosMap = parametroService.getParametrosMap();			
			model.addAttribute("rolDTO", rolService.getAllRolesDTO());
		}			
		return vista;
	}
	
	
	
	@RequestMapping(value = "/nuevoRol", method = RequestMethod.GET)
	public String nuevoRol(Model model, HttpServletRequest request) {
		log.debug("Inicio nuevoRol");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/crearRolCuerpo";
		String permisoMantenedorRol = PermisoType.PUEDE_MANTENER_CARGOS.getNombrePermiso();
		String permisoUsrMantenedorRoles = usuario.getPermisos().get(permisoMantenedorRol);
		log.debug("permisoMantenedorCargo: " + permisoMantenedorRol );
		log.debug("permisoUsrMantenedorCargos: " + permisoUsrMantenedorRoles);		
		if (permisoUsrMantenedorRoles==null || (permisoUsrMantenedorRoles!=null && !permisoUsrMantenedorRoles.equals(permisoMantenedorRol))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos nuevo rol");				
			
			model.addAttribute("todasLasUnidadesOperativasDTO", unidadOperativaService.getTodasUidadesOperativas());
			
		}			
		return vista;
	}
	
	
	@RequestMapping(value = "/mantenedorRolesCuerpo", method = RequestMethod.GET)	
	public String mantenedorRolesCuerpo(Model model, HttpServletRequest request) {		
		log.debug("Inicio mantenedorRolesCuerpo");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/mantenedorRolesCuerpo";
		String permisoMantenedorRol = PermisoType.PUEDE_MANTENER_CARGOS.getNombrePermiso();
		String permisoUsrMantenedorRoles = usuario.getPermisos().get(permisoMantenedorRol);
		log.debug("permisoMantenedorCargo: " + permisoMantenedorRol );
		log.debug("permisoUsrMantenedorCargos: " + permisoUsrMantenedorRoles);		
		if (permisoUsrMantenedorRoles==null || (permisoUsrMantenedorRoles!=null && !permisoUsrMantenedorRoles.equals(permisoMantenedorRol))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos");			
			model.addAttribute("rolDTO", rolService.getAllRolesDTO());
		}			
		return vista;
	}
	
	
	@RequestMapping(value = "creaRol", method = RequestMethod.POST)
	public @ResponseBody RolDTO creaRol(@RequestBody RolDTO rolDTO) {
		log.debug("Inicio creaRol");		
		log.debug(rolDTO.toString());
		try {
			return rolService.crearRol(rolDTO);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			rolDTO.setEstado("ERROR");
			rolDTO.setGlosa(e.getMessage());
			return rolDTO;
		}	
	}
	
	
	@RequestMapping(value = "/editaRol/{idRol}", method = RequestMethod.GET)
	public String editaRol(@PathVariable("idRol") long idRol, Model model, HttpServletRequest request) {
		log.debug("Inicio editaRol");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "div/editaRolCuerpo";
		String permisoMantenedorRol = PermisoType.PUEDE_MANTENER_CARGOS.getNombrePermiso();
		String permisoUsrMantenedorRoles = usuario.getPermisos().get(permisoMantenedorRol);
		log.debug("permisoMantenedorCargo: " + permisoMantenedorRol );
		log.debug("permisoUsrMantenedorCargos: " + permisoUsrMantenedorRoles);		
		if (permisoUsrMantenedorRoles==null || (permisoUsrMantenedorRoles!=null && !permisoUsrMantenedorRoles.equals(permisoMantenedorRol))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado edita rol");			
			RolDTO rolDto = rolService.getRolPorIdRol(idRol);
			model.addAttribute("rolDTO", rolDto);
			model.addAttribute("todasLasUnidadesOperativasDTO", unidadOperativaService.getTodasUidadesOperativas());			
			
			UnidadDTO unidadDTORol = unidadService.getUinidadPorId(rolDto.getIdUnidad());
			model.addAttribute("unidadDTORol" , unidadDTORol);
			
			model.addAttribute("unidadesDTOporOpertaiva", unidadService.getUnidadesPorIdUnidadOperativa(unidadDTORol.getIdUnidadOperativa()));	
			
		}			
		return vista;
	}	
	
	
	@RequestMapping(value = "actualizaRol", method = RequestMethod.POST)
	public @ResponseBody RolDTO actualizaRol(@RequestBody RolDTO rolDTO) {
		log.debug("Inicio Actualiza Rol");		
		log.debug(rolDTO.toString());
		try {
			return rolService.actualizaRol(rolDTO);		
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			rolDTO.setEstado("ERROR");
			rolDTO.setGlosa(e.getMessage());
			return rolDTO;
		}	
	}
	
	

	
	@RequestMapping(value = "/getRolesPorUnidad/{idUnidad}", method = RequestMethod.GET)	
	public String getRolesPorUnidad(@PathVariable("idUnidad") long idUnidad, Model model, HttpServletRequest request) {		
		
		log.debug("Inicio getRolesPorUnidad");					
		String vista = "div/rolesPorUnidad";
		
		try {
			model.addAttribute("rolesDTOUnidad", rolService.getRolesDTOPorIdUnidad(idUnidad));			
			  
		 }catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
		}
		return vista;	
	}

	
	@RequestMapping(value = "/getRolesPorUnidadEdita/{idUnidad}", method = RequestMethod.GET)	
	public String getRolesPorUnidadEdita(@PathVariable("idUnidad") long idUnidad, Model model, HttpServletRequest request) {		
		
		log.debug("Inicio getRolesPorUnidadEdita");					
		String vista = "div/rolesPorUnidadEdita";
		
		try {
			//model.addAttribute("rolesDTOUnidad", rolService.getRolesDTOPorIdUnidad(idUnidad));
			// 2022-03-11 fmolins: Se cambia por metodo que trae listado completo a peticion de problema reportado por CONADI	
			model.addAttribute("rolesDTOUnidad", rolService.getAllRolesDTO());     		
			  
		 }catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
		}
		return vista;	
	}
	
}
