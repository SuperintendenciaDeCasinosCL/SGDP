package cl.gob.scj.sgdp.control;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
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
import org.springframework.web.servlet.ModelAndView;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.service.UsuarioRolService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.config.Constantes;

@Controller
public class LoginControl {
	
	private static final Logger log = Logger.getLogger(LoginControl.class);
	
	@Autowired
	private UsuarioRolService usuarioRolService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	/**
	 * Para LDAP
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/autenticar")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {	
			
		return "login";	
		
	}
	
	/**
	 * Para Keycloack
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	/*@RequestMapping("/")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, Model model) {	
			
		return new ModelAndView("forward:/bandejaDeEntrada");
		
	}
	
	@RequestMapping(value="/login/getRolesUsuario", method=RequestMethod.GET)
	public @ResponseBody List<RolDTO> getRolesUsuario(@RequestParam("idUsuario") String idUsuario) {	
		List<RolDTO> roles = new ArrayList<RolDTO>();
		roles = usuarioRolService.getRolesUsuarioPorIdUsuario(idUsuario, roles);
	    return roles;
	}*/
	
	/**
	 * Para LDAP
	 * @param model
	 * @param request
	 * @return
	 */
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
	
	/**
	 * Para Keycloack
	 * @param model
	 * @param request
	 * @return
	 */
	/*@RequestMapping("/verificarSession")
    public @ResponseBody boolean verificarSession(Model model, HttpServletRequest request){
		log.debug("verificarSession...");
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
        if (usuario==null || usuario.isEstaAutenticado()== false) {
        	return false;
        } else {
        	return true;
        }
    }*/
	
    @RequestMapping("/sessionTimeout")
    public String sessionTimeout( HttpServletRequest request, HttpServletResponse response, Model model) {   
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          return "sessionTimeout";
    }
    
    /**
     * Para Keycloack
     * @param request
     * @return
     */
  /*  @RequestMapping(value="/logout" , method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
 
    	log.debug("logout");
        
        String urlSSO = System.getProperty("sgdp.auth.server.url");
        
        ParametroDTO parametroDTOUrlSGDP = parametroService.getParametroPorID(Constantes.SGDP_URL);
        
        String urlSGDP = parametroDTOUrlSGDP.getValorParametroChar();
 
        log.debug("urlSSO : " + urlSSO);
        log.debug("urlSGDP : " + urlSGDP);        
        
        if (!urlSSO.endsWith("/")) {
        	urlSSO += "/";
        }            
 
        String urlLogout = configProps.getProperty("logoutURL");
 
        log.debug("urlSSO + urlLogout + urlSGDP : " + urlSSO + urlLogout + urlSGDP);
        
        request.getSession().removeAttribute("usuario");
                
        return new ModelAndView("redirect:" + urlSSO + urlLogout + urlSGDP);
    }    */

}