package cl.gob.scj.sgdp.listener;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;

@Component
public class SessionListener implements ApplicationListener<HttpSessionDestroyedEvent> {

	private static final Logger log = Logger.getLogger(SessionListener.class);

	@Autowired
	private AutenticacionService autenticacionService;
	
	@Override
	public void onApplicationEvent(HttpSessionDestroyedEvent evt) {
		log.debug("Inicio onApplicationEvent");
		HttpSession session = evt.getSession();		
		Usuario usuario = (Usuario) session.getAttribute("usuario");				   
    	if (usuario!=null) {
    		log.debug("Inicio logout de Alfresco");
    		autenticacionService.logout(usuario.getAlfTicket());
    	}		
	}

}
