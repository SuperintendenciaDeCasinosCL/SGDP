package cl.gob.scj.sgdp.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.UsuarioRol;
import cl.gob.scj.sgdp.service.GestorDeTagsService;
import cl.gob.scj.sgdp.service.UsuarioRolService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	private static final Logger log = Logger.getLogger(AuthenticationSuccessHandlerImpl.class);
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private UsuarioRolService usuarioRolService;
	
	@Autowired
	private AutenticacionService autenticacionService;
	
	/*@Autowired
	private GestorDeDocumentosService gestorDeDocumentosService;*/
	
	@Autowired
	private GestorDeTagsService gestorDeTagsService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {		
		try {
			handle(request, response, authentication);
		} catch (SgdpException e) {
			redirectStrategy.sendRedirect(request, response, "/?error="+e.getMessage()); 
		}
        clearAuthenticationAttributes(request);
	}
	
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, SgdpException {
        
		String idUsuario = request.getParameter("idUsuario");
		
		log.info("Autenticacion correcta ---- idUsuario: " + idUsuario);
				
		List<UsuarioRol> usuarioRoles = usuarioRolService.getUsuarioRolesPorIdUsuario(idUsuario);		
		
		cargaUsuario(request, usuarioRoles, idUsuario);
		 
		cargarTags(request);
		
		String targetUrl = determineTargetUrl(idUsuario, usuarioRoles);
 
        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
 
        redirectStrategy.sendRedirect(request, response, targetUrl);        
        
    }
	
	protected String determineTargetUrl(String idUsuario, List<UsuarioRol> usuarioRoles) {
        
		boolean esUsuarioRegitrado = false; 
		
		esUsuarioRegitrado = !usuarioRoles.isEmpty();		
		
        if (esUsuarioRegitrado) {
            return Constantes.URL_PAGINA_BANDEJA_ENTRADA;
        }  else {
            throw new IllegalStateException();
        }
    }
	
	private void cargaUsuario(HttpServletRequest request, List<UsuarioRol> usuarioRoles, String idUsuario/*, String clave*/) throws SgdpException {
		
		Usuario usuario;
		
		Long idRolUsuarioSeleccionado = new Long(request.getParameter("idRolUsuarioSeleccionado"));
		
		request.getSession().removeAttribute("usuario");	
			
		usuario = new Usuario();	
		
		String alfTicket;
		try {
			alfTicket = autenticacionService.login(idUsuario);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new SgdpException("Ocurrio un error en la autenticaci\u00f3n");
		}
				
		usuario.setIdUsuario(idUsuario);
		//usuario.setClave(clave);
		
		usuario.setIdRolUsuarioSeleccionado(idRolUsuarioSeleccionado);	
		
		usuario.setRolUnidadPermisosPorIdRolUsuarioSeleccionado(usuarioRoles);
		
		cargaFueraDeOficina(usuario, idRolUsuarioSeleccionado, usuarioRoles);
		
		usuario.setAlfTicket(alfTicket);
		
		log.debug(usuario.toString());
		
		request.getSession().setAttribute("usuario", usuario);
		
	}
	
	private void cargaFueraDeOficina(Usuario usuario, Long idRolUsuarioSeleccionado, List<UsuarioRol> usuarioRoles) {
		log.debug("Inicio cargaFueraDeOficina");
		log.debug(usuario.toString());
		log.debug("idRolUsuarioSeleccionado: " + idRolUsuarioSeleccionado);
		for (UsuarioRol usuarioRol : usuarioRoles) {
			log.debug(usuarioRol.toString());
			if (usuarioRol.getRol().getIdRol() == idRolUsuarioSeleccionado.longValue()) {
				log.debug("usuarioRol.getRol().getIdRol() == idRolUsuarioSeleccionado.longValue()");				
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
	
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		log.debug("Inicio clearAuthenticationAttributes");
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }         
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);      
    }
 
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
