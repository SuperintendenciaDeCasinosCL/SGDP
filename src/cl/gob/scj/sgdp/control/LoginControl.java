package cl.gob.scj.sgdp.control;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.service.UsuarioRolService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;

@Controller
public class LoginControl {
	
	private static final Logger log = Logger.getLogger(LoginControl.class);
	
	@Autowired
	private UsuarioRolService usuarioRolService;
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@RequestMapping("/")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {	
			
		return "login";
		
		/*HttpSession session = request.getSession(false);
        if (session == null) {
        	return "login";
        } else {
        	Usuario usuario = (Usuario) session.getAttribute("usuario");
        	if (usuario!=null) {
        		autenticacionService.logout(usuario.getAlfTicket());
        	}        	
        	session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        	session.invalidate();
        	return "login"; 
        }*/		
		
	}
	
	/*@RequestMapping("/logout")
	public String logout() {		
		return "login";
	}*/
	
	@RequestMapping(value="/login/getRolesUsuario", method=RequestMethod.GET)
	public @ResponseBody List<RolDTO> getRolesUsuario(@RequestParam("idUsuario") String idUsuario) {	
		List<RolDTO> roles = new ArrayList<RolDTO>();
		roles = usuarioRolService.getRolesUsuarioPorIdUsuario(idUsuario, roles);
	    return roles;
	}
	
	@RequestMapping("/verificarSession")
    public @ResponseBody boolean verificarSession(Model model, Principal principal, HttpServletRequest request){
		log.debug("verificarSession...");
		if (principal == null) {
			log.debug("principal == null");
			return false;
		} else {
			HttpSession session = request.getSession(false);
			if (session == null) {
				log.debug("session == null");
				return false;
			} else {
				Usuario usuario = (Usuario) session.getAttribute("usuario");
				if (usuario!=null) {
					try {
						autenticacionService.validaSesion(usuario);
					} catch (Exception e) {
						return false;
					}
				} else {
					log.debug("usuario == null");
					return false;
				}
			}			
			return true;
		}          
    }
	
    @RequestMapping("/sessionTimeout")
    public String sessionTimeout( HttpServletRequest request, HttpServletResponse response, Model model) {   
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          return "sessionTimeout";
    }   

}
