package cl.gob.scj.sgdp.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
//import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.filter.NoCacheFilter;
import cl.gob.scj.sgdp.model.UsuarioRol;
import cl.gob.scj.sgdp.service.GestorDeTagsService;
import cl.gob.scj.sgdp.service.UsuarioRolService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;

public class UserDataHandlerInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = Logger.getLogger(UserDataHandlerInterceptor.class);
	
	@Autowired
	private UsuarioRolService usuarioRolService;
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	@Autowired
	private GestorDeTagsService gestorDeTagsService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		log.debug("preHandle");

		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
	
		if (usuario==null) {
        	log.debug("usuario==null");
        	usuario = new Usuario();
        }
        
        if(!usuario.isEstaAutenticado()) {
        	
        	//KeycloakSecurityContext context = (KeycloakSecurityContext) request.getSession().getAttribute(KeycloakSecurityContext.class.getName());
        	
        	//String idUsuario = context.getToken().getPreferredUsername();
        	
        	//usuario.setIdUsuario(idUsuario);
    				
    		//List<UsuarioRol> usuarioRoles = usuarioRolService.getUsuarioRolesPorIdUsuario(idUsuario);		
    		
    		//cargaUsuario(request, usuarioRoles, usuario);
    		 
    		cargarTags(request);
    		
    		usuario.setSessionId(request.getSession().getId());
    		
    		usuario.setEstaAutenticado(true);
        	
        }

		return super.preHandle(request, response, handler);
		
	}
	
	private void cargaUsuario(HttpServletRequest request, List<UsuarioRol> usuarioRoles, Usuario usuario) throws SgdpException {		
		
		String alfTicket;
		
		try {
			alfTicket = autenticacionService.login(usuario.getIdUsuario());
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new SgdpException("Ocurrio un error en la autenticaci\u00f3n");
		}
				
		usuario.setIdUsuario(usuario.getIdUsuario());
		
		usuario.setPermisosRolesYUnidades(usuarioRoles);		

		cargaFueraDeOficina(usuario, usuarioRoles);
		
		usuario.setAlfTicket(alfTicket);
		
		log.debug(usuario.toString());
		
		request.getSession().setAttribute("usuario", usuario);
		
	}
	
	private void cargaFueraDeOficina(Usuario usuario, List<UsuarioRol> usuarioRoles) {
		log.debug("Inicio cargaFueraDeOficina");
		log.debug(usuario.toString());	
		for (UsuarioRol usuarioRol : usuarioRoles) {			
			if (usuarioRol.getFueraDeOficina()==true) {
				usuario.setFueraDeOficina(usuarioRol.getFueraDeOficina());
				return;
			}
		}
	}
	
	private void cargarTags(HttpServletRequest request) throws SgdpException {
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		List<String> todosLosTags = gestorDeTagsService.obtenerListaDeTags(usuario);
		
		request.getSession().setAttribute("todosLosTags", todosLosTags);
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)	{
			
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

			response.setHeader("Pragma", "no-cache");

			response.setDateHeader("Expires", 0); 
	
	}	

}