package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

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
import cl.gob.scj.sgdp.dto.ListaDeDistribucionDTO;
import cl.gob.scj.sgdp.service.ListaDeDistribucionService;
import cl.gob.scj.sgdp.tipos.PermisoType;

@Controller
public class ListaDeDistribucionControl {
	
	private static final Logger log = Logger.getLogger(ListaDeDistribucionControl.class);	
	
	@Autowired
	private ListaDeDistribucionService listaDeDistribucionService;
	
	@RequestMapping("/listaDeDistribucion")
	public String cargaPantallaListaDeDistribucion(Model model, HttpServletRequest request) {
		
		log.debug("Inicio cargaPantallaListaDeDistribucion");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		String vista = "listaDeDistribucion";
		String permisoListaDist = PermisoType.PUEDE_MANTENER_LISTA_DISTRIBUCION.getNombrePermiso();
		String permisoUsrListaDist = usuario.getPermisos().get(permisoListaDist);
		log.debug("permisoListaDist: " + permisoListaDist);
		log.debug("permisoUsrListaDist: " + permisoUsrListaDist);
		
		if (permisoUsrListaDist==null || (permisoUsrListaDist!=null && !permisoUsrListaDist.equals(permisoListaDist))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			log.debug("Cargado datos");
			List<ListaDeDistribucionDTO> listaTotalDeDistribucionDTO = listaDeDistribucionService.getListaDeDistribucion();
			
			model.addAttribute("listaTotalDeDistribucionDTO", listaTotalDeDistribucionDTO);
		}
			
		return vista;
	}
	
	@RequestMapping(value = "borrarRegistroDeListaDeDistribucion/{idListaDeDistribucion}", method = RequestMethod.POST)
	public @ResponseBody String borrarRegistroDeListaDeDistribucion(@PathVariable("idListaDeDistribucion") long idListaDeDistribucion, HttpServletRequest request) {
		log.debug("Inicio borrarRegistroDeListaDeDistribucion");		
		log.debug("idListaDeDistribucion: " + idListaDeDistribucion);
		try {
			listaDeDistribucionService.borrarRegistroDeListaDeDistribucion(idListaDeDistribucion);
			return "OK";
		} catch(Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);				
			return "ERROR";			
		}		
	}
	
	@RequestMapping("/cargaListaDeDistribucion")
	public String cargaListaDeDistribucion(Model model, HttpServletRequest request) {
		
		log.debug("Inicio cargaListaDeDistribucion");
		
		List<ListaDeDistribucionDTO> listaTotalDeDistribucionDTO = listaDeDistribucionService.getListaDeDistribucion();
		
		model.addAttribute("listaTotalDeDistribucionDTO", listaTotalDeDistribucionDTO);
			
		return "div/listaDeDistribucionCuerpo";
	}
	
	@RequestMapping("/editaRegistroListaDeDistribucion/{idListaDeDistribucion}")
	public String editaRegistroListaDeDistribucion(@PathVariable("idListaDeDistribucion") long idListaDeDistribucion, Model model, HttpServletRequest request) {		
		log.debug("Inicio editaRegistroListaDeDistribucion");		
		ListaDeDistribucionDTO registroListaDeDistribucionDTO = listaDeDistribucionService.getRegistroDeListaDeDistribucionPorId(idListaDeDistribucion);
		model.addAttribute("registroListaDeDistribucionDTO", registroListaDeDistribucionDTO);			
		return "div/editaRegistroListaDeDistribucionCuerpo";
	}
	
	@RequestMapping(value = "actualizaRegistroDeListaDeDistribucion")
	public @ResponseBody ListaDeDistribucionDTO actualizaRegistroDeListaDeDistribucion(@RequestBody ListaDeDistribucionDTO registroListaDeDistribucionDTO) {
		log.debug("Inicio actualizaRegistroDeListaDeDistribucion");		
		log.debug(registroListaDeDistribucionDTO);		
		try {			
			listaDeDistribucionService.actualizaRegistroDeListaDeDistribucion(registroListaDeDistribucionDTO);
			registroListaDeDistribucionDTO.setRespuesta("OK");
			return registroListaDeDistribucionDTO;
		} catch(Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			registroListaDeDistribucionDTO.setRespuesta("ERROR");
			return registroListaDeDistribucionDTO;			
		}		
	}
	
	@RequestMapping(value = "creaRegistroDeListaDeDistribucion")
	public @ResponseBody ListaDeDistribucionDTO creaRegistroDeListaDeDistribucion(@RequestBody ListaDeDistribucionDTO registroListaDeDistribucionDTO) {
		log.debug("Inicio creaRegistroDeListaDeDistribucion");		
		log.debug(registroListaDeDistribucionDTO);	
		try {			
			listaDeDistribucionService.creaRegistroDeListaDeDistribucion(registroListaDeDistribucionDTO);
			registroListaDeDistribucionDTO.setRespuesta("OK");
			return registroListaDeDistribucionDTO;
		} catch(Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			registroListaDeDistribucionDTO.setRespuesta("ERROR");
			return registroListaDeDistribucionDTO;			
		}		
	}

}