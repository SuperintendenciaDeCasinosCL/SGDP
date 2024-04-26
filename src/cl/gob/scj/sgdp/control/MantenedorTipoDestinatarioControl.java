package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.TipoDeDestinatarioDTO;
import cl.gob.scj.sgdp.service.TipoDeDestinatarioService;
import cl.gob.scj.sgdp.tipos.PermisoType;
import cl.gob.scj.sgdp.util.SGDPUtil;

@Controller
public class MantenedorTipoDestinatarioControl {
	
	private static final Logger log = Logger.getLogger(MantenedorTipoDestinatarioControl.class);
	
	@Autowired
	private TipoDeDestinatarioService tipoDeDestinatarioService;
	
	@RequestMapping(value="/mantenedorTipoDestinatario", method=RequestMethod.GET)
	public String mantenedorTipoDestinatario(Model model, HttpServletRequest request) {	
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String permisoTipoDest = PermisoType.PUEDE_MANTENER_TIPO_DE_DESTINATARIO.getNombrePermiso();
		String permisoUsrTipoDest = usuario.getPermisos().get(permisoTipoDest);
		log.debug("permisoTipoDest: " + permisoTipoDest);
		log.debug("permisoUsrTipoDest: " + permisoUsrTipoDest);		
		if (permisoUsrTipoDest==null || (permisoUsrTipoDest!=null && !permisoUsrTipoDest.equals(permisoTipoDest))) {
			log.debug("Devolviendo vista 403");
			return "error403";
		} else {
			model.addAttribute("todosTipoDeDestinatarioDTO", tipoDeDestinatarioService.getAllTipoDeDestinatario());
			return "mantenedorTipoDestinatario"; 
		}		 
	}
	
	@RequestMapping(value = "/creaTipoDestinatario", method = RequestMethod.PUT)
	public @ResponseBody TipoDeDestinatarioDTO creaTipoDestinatario(@RequestBody TipoDeDestinatarioDTO tipoDeDestinatarioDTO, HttpServletRequest request) {
		log.debug("Inicio creaTipoDestinatario");		
		log.debug(tipoDeDestinatarioDTO.toString());		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String permisoTipoDest = PermisoType.PUEDE_MANTENER_TIPO_DE_DESTINATARIO.getNombrePermiso();
		String permisoUsrTipoDest = usuario.getPermisos().get(permisoTipoDest);
		log.debug("permisoTipoDest: " + permisoTipoDest);
		log.debug("permisoUsrTipoDest: " + permisoUsrTipoDest);		
		if (permisoUsrTipoDest==null || (permisoUsrTipoDest!=null && !permisoUsrTipoDest.equals(permisoTipoDest))) {
			tipoDeDestinatarioDTO.setRespuesta("No tiene permiso para crear tipos de destinatarios");
			return tipoDeDestinatarioDTO;
		} else {
			try {			
				tipoDeDestinatarioService.insertaTipoDeDestinatario(tipoDeDestinatarioDTO, usuario);				
				log.debug(tipoDeDestinatarioDTO.toString());
				return tipoDeDestinatarioDTO;
			} catch(Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
				tipoDeDestinatarioDTO.setRespuesta("ERROR");
				return tipoDeDestinatarioDTO;			
			}	
		}
	}	
	
	@RequestMapping(value="/mantenedorTipoDestinatarioCuerpo", method=RequestMethod.GET)
	public String mantenedorTipoDestinatarioCuerpo(Model model, HttpServletRequest request) {		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String permisoTipoDest = PermisoType.PUEDE_MANTENER_TIPO_DE_DESTINATARIO.getNombrePermiso();
		String permisoUsrTipoDest = usuario.getPermisos().get(permisoTipoDest);
		log.debug("permisoTipoDest: " + permisoTipoDest);
		log.debug("permisoUsrTipoDest: " + permisoUsrTipoDest);		
		if (permisoUsrTipoDest==null || (permisoUsrTipoDest!=null && !permisoUsrTipoDest.equals(permisoTipoDest))) {
			log.debug("Devolviendo vista 403");
			return "error403";
		} else {
			model.addAttribute("todosTipoDeDestinatarioDTO", tipoDeDestinatarioService.getAllTipoDeDestinatario());
			return "div/mantenedorTipoDestinatarioCuerpo";
		}		
	}
	
	@RequestMapping(value = "/borraTipoDestinatario/{idTipoDestinatario}", method = RequestMethod.DELETE)
	public @ResponseBody TipoDeDestinatarioDTO borraTipoDestinatario(@PathVariable("idTipoDestinatario") long idTipoDestinatario, HttpServletRequest request) {
		TipoDeDestinatarioDTO tipoDeDestinatarioDTO = new TipoDeDestinatarioDTO();
		tipoDeDestinatarioDTO.setIdTipoDestinatario(idTipoDestinatario);
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String permisoTipoDest = PermisoType.PUEDE_MANTENER_TIPO_DE_DESTINATARIO.getNombrePermiso();
		String permisoUsrTipoDest = usuario.getPermisos().get(permisoTipoDest);
		log.debug("permisoTipoDest: " + permisoTipoDest);
		log.debug("permisoUsrTipoDest: " + permisoUsrTipoDest);		
		if (permisoUsrTipoDest==null || (permisoUsrTipoDest!=null && !permisoUsrTipoDest.equals(permisoTipoDest))) {
			tipoDeDestinatarioDTO.setRespuesta("No tiene permiso para borrar tipos de destinatarios");
			return tipoDeDestinatarioDTO;
		} else {
			try {			
				tipoDeDestinatarioService.borraTipoDeDestinatario(tipoDeDestinatarioDTO, usuario);				
				log.debug(tipoDeDestinatarioDTO.toString());
				return tipoDeDestinatarioDTO;
			} catch(Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
				tipoDeDestinatarioDTO.setRespuesta("ERROR");
				return tipoDeDestinatarioDTO;			
			}	
		}
	}
	
	
	@RequestMapping(value = "/actualizaTipoDestinatario", method = RequestMethod.PUT)
	public @ResponseBody TipoDeDestinatarioDTO actualizaTipoDestinatario(@RequestBody TipoDeDestinatarioDTO tipoDeDestinatarioDTO, HttpServletRequest request) {
		log.debug("Inicio creaTipoDestinatario");		
		log.debug(tipoDeDestinatarioDTO.toString());		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String permisoTipoDest = PermisoType.PUEDE_MANTENER_TIPO_DE_DESTINATARIO.getNombrePermiso();
		String permisoUsrTipoDest = usuario.getPermisos().get(permisoTipoDest);
		log.debug("permisoTipoDest: " + permisoTipoDest);
		log.debug("permisoUsrTipoDest: " + permisoUsrTipoDest);		
		if (permisoUsrTipoDest==null || (permisoUsrTipoDest!=null && !permisoUsrTipoDest.equals(permisoTipoDest))) {
			tipoDeDestinatarioDTO.setRespuesta("No tiene permiso para actualizar tipos de destinatarios");
			return tipoDeDestinatarioDTO;
		} else {
			try {			
				tipoDeDestinatarioService.actualizaTipoDeDestinatario(tipoDeDestinatarioDTO, usuario);				
				log.debug(tipoDeDestinatarioDTO.toString());
				return tipoDeDestinatarioDTO;
			} catch(Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
				tipoDeDestinatarioDTO.setRespuesta("ERROR");
				return tipoDeDestinatarioDTO;			
			}	
		}
	}	
	
	@RequestMapping(value = "/getAllTipoDeDestinatario", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<TipoDeDestinatarioDTO>> getAllTipoDeDestinatario(HttpServletRequest request) {
		log.debug("Inicio getAllTipoDeDestinatario");			
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String permisoTipoDest = PermisoType.PUEDE_MANTENER_TIPO_DE_DESTINATARIO.getNombrePermiso();
		String permisoUsrTipoDest = usuario.getPermisos().get(permisoTipoDest);
		log.debug("permisoTipoDest: " + permisoTipoDest);
		log.debug("permisoUsrTipoDest: " + permisoUsrTipoDest);		
		if (permisoUsrTipoDest==null || (permisoUsrTipoDest!=null && !permisoUsrTipoDest.equals(permisoTipoDest))) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} else {
			try {			
				return new ResponseEntity<>(tipoDeDestinatarioService.getAllTipoDeDestinatario(), HttpStatus.OK);
			} catch(Exception e) {
				log.error(SGDPUtil.getStackTrace(e));
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);			
			}	
		}
	}
	

}