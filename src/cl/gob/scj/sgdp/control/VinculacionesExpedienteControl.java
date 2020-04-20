package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.annotation.Resource;
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
import org.springframework.web.servlet.ModelAndView;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.VinculacionExpedienteDTO;
import cl.gob.scj.sgdp.excel.VinculacionExpExcel;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.VinculacionesExpedienteService;
import cl.gob.scj.sgdp.tipos.PermisoType;

@Controller
public class VinculacionesExpedienteControl {
	
	private static final Logger log = Logger.getLogger(VinculacionesExpedienteControl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private VinculacionesExpedienteService vinculacionesExpedienteService;
	
	@RequestMapping(value="/getVinculacionesDeExpediente/{idExpediente}", method=RequestMethod.GET)
	public String getVinculacionesDeExpediente(@PathVariable("idExpediente") String idExpediente, Model model, HttpServletRequest request) {
		log.debug("idExpediente: " + idExpediente);
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		VinculacionExpedienteDTO vinculacionExpedienteDTO;
		try {
			vinculacionExpedienteDTO = vinculacionesExpedienteService.getVinculacionExpedienteDTO(idExpediente);
			model.addAttribute("permisos", usuario.getPermisos());
			model.addAttribute("vinculacionExpedienteDTO", vinculacionExpedienteDTO);
			return "div/vinculacionesDeExpediente";
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			return "error";
		}	
	}
	
	@RequestMapping(value = "/vincularExp", method = RequestMethod.POST) 
	public @ResponseBody VinculacionExpedienteDTO vincularExp(@RequestBody VinculacionExpedienteDTO vinculacionExpedienteDTO, HttpServletRequest request) {
		log.debug("Inicio vincularExp");
		log.debug(vinculacionExpedienteDTO.toString());
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		try {
			String permisoPuedeVincularExp = PermisoType.PUEDE_VINCULAR_EXPEDIENTES.getNombrePermiso();
			String permisoUsrPuedeVincularExp = usuario.getPermisos().get(permisoPuedeVincularExp);
			log.debug("permisoPuedeVincularExp: " + permisoPuedeVincularExp);
			log.debug("permisoUsrPuedeVincularExp: " + permisoUsrPuedeVincularExp);			
			if (permisoUsrPuedeVincularExp==null || (permisoUsrPuedeVincularExp!=null && !permisoUsrPuedeVincularExp.equals(permisoPuedeVincularExp))) {
				vinculacionExpedienteDTO.setRespuestaVinculacion("No tiene permiso para vincular expedientes");
				vinculacionExpedienteDTO.setCssStatus(configProps.getProperty(Constantes.CSS_WARNING));
			} else {
				vinculacionExpedienteDTO.setIdUsuario(usuario.getIdUsuario());			
				vinculacionesExpedienteService.vincularExp(usuario, vinculacionExpedienteDTO);
				if (vinculacionExpedienteDTO.getRespuestaVinculacion()== null || vinculacionExpedienteDTO.getRespuestaVinculacion().isEmpty()) {
					vinculacionExpedienteDTO.setRespuestaVinculacion("Vinculaci\u00f3n realizada de manera exitosa");
				}				
				vinculacionExpedienteDTO.setCssStatus(configProps.getProperty(Constantes.CSS_SUCESS));
			}
			return vinculacionExpedienteDTO;			
		} catch(SgdpException e) {
			log.log(e.getNivelLog(), e.getMessage());
			vinculacionExpedienteDTO.setRespuestaVinculacion(e.getMessage());
			vinculacionExpedienteDTO.setCssStatus(configProps.getProperty(Constantes.CSS_ERROR));
			return vinculacionExpedienteDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			vinculacionExpedienteDTO.setRespuestaVinculacion("Ocurrio un error al vincular los expedientes");
			vinculacionExpedienteDTO.setCssStatus(configProps.getProperty(Constantes.CSS_ERROR));
			return vinculacionExpedienteDTO;
		}
	}
	
	@RequestMapping(value = "/vincularExpExcel/{idExpediente}", method = RequestMethod.GET) 
	public ModelAndView vincularExpExcel(@PathVariable("idExpediente") String idExpediente, HttpServletRequest request) {
		log.debug("idExpediente: " + idExpediente);
		VinculacionExpedienteDTO vinculacionExpedienteDTO;
		vinculacionExpedienteDTO = vinculacionesExpedienteService.getVinculacionExpedienteDTO(idExpediente);
		return new ModelAndView(new VinculacionExpExcel(), "vinculacionExpedienteDTO", vinculacionExpedienteDTO);
	}
	
	@RequestMapping(value = "/desVincularExp", method = RequestMethod.POST) 
	public @ResponseBody VinculacionExpedienteDTO desVincularExp(@RequestBody VinculacionExpedienteDTO vinculacionExpedienteDTO, HttpServletRequest request) {
		log.debug("Inicio deVincularExp");
		log.debug(vinculacionExpedienteDTO.toString());
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		try {
			String permisoPuedeDesVincularExp = PermisoType.PUEDE_DES_VINCULAR_EXPEDIENTES.getNombrePermiso();
			String permisoUsrPuedeDesVincularExp = usuario.getPermisos().get(permisoPuedeDesVincularExp);
			log.debug("permisoPuedeDesVincularExp: " + permisoPuedeDesVincularExp);
			log.debug("permisoUsrPuedeDesVincularExp: " + permisoUsrPuedeDesVincularExp);			
			if (permisoUsrPuedeDesVincularExp==null || (permisoUsrPuedeDesVincularExp!=null && !permisoUsrPuedeDesVincularExp.equals(permisoPuedeDesVincularExp))) {
				vinculacionExpedienteDTO.setRespuestaVinculacion("No tiene permiso para desvincular expedientes");
				vinculacionExpedienteDTO.setCssStatus(configProps.getProperty(Constantes.CSS_WARNING));
			} else {
				vinculacionExpedienteDTO.setIdUsuario(usuario.getIdUsuario());
				vinculacionesExpedienteService.desVincularExp(usuario, vinculacionExpedienteDTO);
				vinculacionExpedienteDTO.setRespuestaVinculacion("Desvinculaci\u00f3n realizada de manera exitosa");
				vinculacionExpedienteDTO.setCssStatus(configProps.getProperty(Constantes.CSS_SUCESS));
			}
			return vinculacionExpedienteDTO;			
		} catch(SgdpException e) {
			log.log(e.getNivelLog(), e.getMessage());
			vinculacionExpedienteDTO.setRespuestaVinculacion(e.getMessage());
			vinculacionExpedienteDTO.setCssStatus(configProps.getProperty(Constantes.CSS_ERROR));
			return vinculacionExpedienteDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			vinculacionExpedienteDTO.setRespuestaVinculacion("Ocurrio un error al vincular los expedientes");
			vinculacionExpedienteDTO.setCssStatus(configProps.getProperty(Constantes.CSS_ERROR));
			return vinculacionExpedienteDTO;
		}
		
	}

}