package cl.gob.scj.sgdp.control;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.BuscarTablaRetencionDTO;
import cl.gob.scj.sgdp.dto.MensajeVistaDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaTablaDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
import cl.gob.scj.sgdp.service.TablaRetencionService;

@Controller
public class TablaRetencionControl {		

	private static final Logger log = Logger.getLogger(TablaRetencionControl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private TablaRetencionService tablaRetencionService;

	
	@RequestMapping("/tablaRetencion")	
	public String cargaBandejaDeEntrada(Model model, HttpServletRequest request) {
					
		log.debug("Inicio carga tabla Retencion");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");			
		String codigoInstitucion = this.tablaRetencionService.getCodigoInstitucion();
		model.addAttribute("permisos", usuario.getPermisos());
		model.addAttribute("codigoInstitucion", codigoInstitucion);
		log.debug("Fin carga tabla Retencion");
		return configProps.getProperty("vistaTablaRetencion");
	}	

	@RequestMapping("/getTablasRetencion")
	public String getResultadoDeBusqueda(@ModelAttribute("buscarTablaRetencionDTO") BuscarTablaRetencionDTO buscarDTO, Model model,
			HttpServletRequest request) throws UnsupportedEncodingException {

		log.debug("Inicio getResultadoDeBusquedaTablaRetencion");
		log.debug(buscarDTO.toString());
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		model.addAttribute("permisos", usuario.getPermisos());
		try {
			ResultadoBusquedaTablaDTO resultadoBusquedaTablaDTO = this.tablaRetencionService.getResultadoDeBusquedaTablaRetencion(buscarDTO);
			model.addAttribute("resultadoBusquedaTablaDTO", resultadoBusquedaTablaDTO);
		} catch (ArchivoNacionalException anex) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			mensajeVistaDTO.getMensajes().put(anex.getMessage(), configProps.getProperty("cssError"));
			model.addAttribute("mensajeVistaDTO", mensajeVistaDTO);
		}
		return configProps.getProperty("vistaResultadoDeBusquedaTablaRetencion");

	}
	
}
