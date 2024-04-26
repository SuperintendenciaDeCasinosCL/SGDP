package cl.gob.scj.sgdp.control;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.tipos.PermisoType;

@Controller
public class MantenedorPlantillasControl {

	private static final Logger log = Logger.getLogger(MantenedorPlantillasControl.class);	
	
	@RequestMapping(value = "/mantenedorPlantillas", method = RequestMethod.GET)	
	public String mantenedorUnidadesOperativas(Model model, HttpServletRequest request) {		
		log.debug("Inicio mantenedorPlantillas");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "mantenedorPlantillas";
		String permisoMantenedor = PermisoType.PUEDE_MANTENER_PLANTILLAS.getNombrePermiso();
		String permisoUsrMantenedor = usuario.getPermisos().get(permisoMantenedor);
		
		
		if (permisoMantenedor==null || (permisoMantenedor!=null && !permisoUsrMantenedor.equals(permisoUsrMantenedor))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} 
		
		return vista;
	}
	
	
}
