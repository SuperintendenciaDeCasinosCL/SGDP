package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ListaDeDistribucionDTO;
import cl.gob.scj.sgdp.dto.RespuestaCargaListaDistribucionMasivaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.ListaDeDistribucionService;
import cl.gob.scj.sgdp.service.TipoDeDestinatarioService;
import cl.gob.scj.sgdp.tipos.PermisoType;
import cl.gob.scj.sgdp.util.SGDPUtil;

@Controller
public class ListaDeDistribucionControl {
	
	private static final Logger log = Logger.getLogger(ListaDeDistribucionControl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private ListaDeDistribucionService listaDeDistribucionService;
	
	@Autowired
	private TipoDeDestinatarioService tipoDeDestinatarioService;
	
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
			model.addAttribute("listaTotalDeDistribucionDTO", listaDeDistribucionService.getListaDeDistribucion(false));
			model.addAttribute("listaTipoDeDestinatarioDTO", tipoDeDestinatarioService.getAllTipoDeDestinatario());
		}			
		return vista;
	}
	
	@RequestMapping(value = "borrarRegistroDeListaDeDistribucion/{idListaDeDistribucion}/{motivo}", method = RequestMethod.DELETE)
	public @ResponseBody String borrarRegistroDeListaDeDistribucion(@PathVariable("idListaDeDistribucion") long idListaDeDistribucion,
			@PathVariable("motivo") String motivo, 
			HttpServletRequest request) {
		log.debug("Inicio borrarRegistroDeListaDeDistribucion");		
		log.debug("idListaDeDistribucion: " + idListaDeDistribucion);	
		log.debug("motivo: " + motivo);
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");	
		String permisoListaDist = PermisoType.PUEDE_MANTENER_LISTA_DISTRIBUCION.getNombrePermiso();
		String permisoUsrListaDist = usuario.getPermisos().get(permisoListaDist);
		log.debug("permisoListaDist: " + permisoListaDist);
		log.debug("permisoUsrListaDist: " + permisoUsrListaDist);		
		if (permisoUsrListaDist==null || (permisoUsrListaDist!=null && !permisoUsrListaDist.equals(permisoListaDist))) {
			return "ERROR";
		} else {
			try {
				listaDeDistribucionService.borrarRegistroDeListaDeDistribucion(idListaDeDistribucion, motivo, usuario);
				return "OK";
			} catch(Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);				
				return "ERROR";			
			}			
		}
	}
	
	@RequestMapping(value = "/cargaListaDeDistribucion", method = RequestMethod.GET)
	public String cargaListaDeDistribucion(Model model, HttpServletRequest request) {		
		log.debug("Inicio cargaListaDeDistribucion");		
		List<ListaDeDistribucionDTO> listaTotalDeDistribucionDTO = listaDeDistribucionService.getListaDeDistribucion(false);		
		model.addAttribute("listaTotalDeDistribucionDTO", listaTotalDeDistribucionDTO);			
		return "div/listaDeDistribucionCuerpo";
	}
	
	@RequestMapping(value = "/editaRegistroListaDeDistribucion/{idListaDeDistribucion}", method = RequestMethod.GET)
	public String editaRegistroListaDeDistribucion(@PathVariable("idListaDeDistribucion") long idListaDeDistribucion, 
			Model model, HttpServletRequest request) {		
		log.debug("Inicio editaRegistroListaDeDistribucion");	
		model.addAttribute("registroListaDeDistribucionDTO", listaDeDistribucionService.getRegistroDeListaDeDistribucionPorId(idListaDeDistribucion));	
		model.addAttribute("listaTipoDeDestinatarioDTO", tipoDeDestinatarioService.getAllTipoDeDestinatario());
		return "div/editaRegistroListaDeDistribucionCuerpo";
	}
	
	@RequestMapping(value = "actualizaRegistroDeListaDeDistribucion", method = RequestMethod.POST)
	public @ResponseBody ListaDeDistribucionDTO actualizaRegistroDeListaDeDistribucion(@RequestBody ListaDeDistribucionDTO registroListaDeDistribucionDTO, HttpServletRequest request) {
		log.debug("Inicio actualizaRegistroDeListaDeDistribucion");		
		log.debug(registroListaDeDistribucionDTO);	
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");	
		String permisoListaDist = PermisoType.PUEDE_MANTENER_LISTA_DISTRIBUCION.getNombrePermiso();
		String permisoUsrListaDist = usuario.getPermisos().get(permisoListaDist);
		log.debug("permisoListaDist: " + permisoListaDist);
		log.debug("permisoUsrListaDist: " + permisoUsrListaDist);		
		if (permisoUsrListaDist==null || (permisoUsrListaDist!=null && !permisoUsrListaDist.equals(permisoListaDist))) {
			registroListaDeDistribucionDTO.setRespuesta("ERROR");
			return registroListaDeDistribucionDTO;	
		} else {
			try {			
				listaDeDistribucionService.actualizaRegistroDeListaDeDistribucion(registroListaDeDistribucionDTO, usuario);
				registroListaDeDistribucionDTO.setRespuesta("OK");
				return registroListaDeDistribucionDTO;
			} catch(SgdpException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
				registroListaDeDistribucionDTO.setRespuesta(e.getMessage());
				return registroListaDeDistribucionDTO;			
			} catch(Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
				registroListaDeDistribucionDTO.setRespuesta("Ocurrio un error al actualizar el registro");
				return registroListaDeDistribucionDTO;			
			}		
		}	
	}
	
	@RequestMapping(value = "creaRegistroDeListaDeDistribucion", method = RequestMethod.POST)
	public @ResponseBody ListaDeDistribucionDTO creaRegistroDeListaDeDistribucion(@RequestBody ListaDeDistribucionDTO registroListaDeDistribucionDTO, HttpServletRequest request) {
		log.debug("Inicio creaRegistroDeListaDeDistribucion");		
		log.debug(registroListaDeDistribucionDTO);		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");	
		String permisoListaDist = PermisoType.PUEDE_MANTENER_LISTA_DISTRIBUCION.getNombrePermiso();
		String permisoUsrListaDist = usuario.getPermisos().get(permisoListaDist);
		log.debug("permisoListaDist: " + permisoListaDist);
		log.debug("permisoUsrListaDist: " + permisoUsrListaDist);		
		if (permisoUsrListaDist==null || (permisoUsrListaDist!=null && !permisoUsrListaDist.equals(permisoListaDist))) {
			registroListaDeDistribucionDTO.setRespuesta("ERROR");
			return registroListaDeDistribucionDTO;	
		} else {			
			try {			
				listaDeDistribucionService.creaRegistroDeListaDeDistribucion(registroListaDeDistribucionDTO, usuario);
				registroListaDeDistribucionDTO.setRespuesta("OK");
				return registroListaDeDistribucionDTO;
			} catch(SgdpException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
				registroListaDeDistribucionDTO.setRespuesta(e.getMessage());
				return registroListaDeDistribucionDTO;			
			} catch(Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
				registroListaDeDistribucionDTO.setRespuesta("Ocurrio un error al crear el registro");
				return registroListaDeDistribucionDTO;			
			}			
		}	
	}
	
	@RequestMapping(value = "getListaDistribucionPorIdTipoDestinatario/{idTipoDestinatario}", method = RequestMethod.GET)
	public @ResponseBody List<ListaDeDistribucionDTO> getListaDistribucionPorIdTipoDestinatario(@PathVariable("idTipoDestinatario") long idTipoDestinatario) {	
		return listaDeDistribucionService.getListaDistribucionPorIdTipoDestinatario(idTipoDestinatario);		
	}
	
	@RequestMapping(value = "/cargaListaDistribucionMasiva", method = RequestMethod.POST)
	public @ResponseBody RespuestaCargaListaDistribucionMasivaDTO cargaListaDistribucionMasiva(
			@ModelAttribute("listaDeDistribucionDTO") ListaDeDistribucionDTO listaDeDistribucionDTO, HttpServletRequest request) {
		try {
			log.debug("listaDeDistribucionDTO.getArchivo().getName(): " + listaDeDistribucionDTO.getArchivo().getName());
			Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");	
			String permisoListaDist = PermisoType.PUEDE_MANTENER_LISTA_DISTRIBUCION.getNombrePermiso();
			String permisoUsrListaDist = usuario.getPermisos().get(permisoListaDist);
			log.debug("permisoListaDist: " + permisoListaDist);
			log.debug("permisoUsrListaDist: " + permisoUsrListaDist);		
			if (permisoUsrListaDist==null || (permisoUsrListaDist!=null && !permisoUsrListaDist.equals(permisoListaDist))) {
				RespuestaCargaListaDistribucionMasivaDTO respuestaCargaListaDistribucionMasivaDTO = new RespuestaCargaListaDistribucionMasivaDTO();
				respuestaCargaListaDistribucionMasivaDTO.setCssStatus(configProps.getProperty("cssError"));
				respuestaCargaListaDistribucionMasivaDTO.setMensaje(configProps.getProperty("sgdp.sin-permiso-para-la-accion"));
				return respuestaCargaListaDistribucionMasivaDTO;
			} 
			return listaDeDistribucionService.cargaListaDistribucionMasivo(listaDeDistribucionDTO, usuario);
		} catch (Exception e) {
			log.error(SGDPUtil.getStackTrace(e));
			RespuestaCargaListaDistribucionMasivaDTO respuestaCargaListaDistribucionMasivaDTO = new RespuestaCargaListaDistribucionMasivaDTO();
			respuestaCargaListaDistribucionMasivaDTO.setCssStatus(configProps.getProperty("cssError"));
			respuestaCargaListaDistribucionMasivaDTO.setMensaje(e.getMessage());
			return respuestaCargaListaDistribucionMasivaDTO;			
		}	
	}

}
