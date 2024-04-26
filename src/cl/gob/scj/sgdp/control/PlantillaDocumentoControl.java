package cl.gob.scj.sgdp.control;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import cl.gob.scj.sgdp.auth.user.Usuario;



@Controller
public class PlantillaDocumentoControl {

	private static final Logger log = Logger.getLogger(PlantillaDocumentoControl.class);

	@RequestMapping(value = "/plantillaDocumento", method = RequestMethod.GET)
	public String cargaPantallaCargaProcesos(Model model, HttpServletRequest request) {
		
	
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		String vista = "plantillaDocumento";
		
		return vista;
	}

	
	

}
