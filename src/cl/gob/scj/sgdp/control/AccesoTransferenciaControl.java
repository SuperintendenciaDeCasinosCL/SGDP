package cl.gob.scj.sgdp.control;

import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.AccesoArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.RespuestaAccesoArchivoNacionalDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.AccesoTransferenciaService;

@Controller
public class AccesoTransferenciaControl {		

	private static final Logger log = Logger.getLogger(AccesoTransferenciaControl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private AccesoTransferenciaService accesoTransferenciaService;

	@RequestMapping("/accesoTransferencia")	
	public String cargaAccesoTransferencia(Model model, HttpServletRequest request) {
					
		log.debug("Inicio carga acceso transferencia");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");			
		model.addAttribute("permisos", usuario.getPermisos());
		
		log.debug("Fin carga acceso transferencia");
		return configProps.getProperty("vistaAccesoTransferencia");
	}	
		
	@RequestMapping(value = "/guardarAccesoArchivoNacional", method = RequestMethod.POST)
	public @ResponseBody RespuestaAccesoArchivoNacionalDTO guardarAccesoArchivoNacional(@RequestBody AccesoArchivoNacionalDTO accesoArchivoNacionalDTO, Model model, HttpServletRequest request) {		 

		RespuestaAccesoArchivoNacionalDTO respuestaAccesoArchivoNacionalDTO = new RespuestaAccesoArchivoNacionalDTO();	
		log.info(accesoArchivoNacionalDTO.toString());
		try {

			Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
			this.accesoTransferenciaService.guardarAccesoArchivoNacional(accesoArchivoNacionalDTO, usuario);
			respuestaAccesoArchivoNacionalDTO.setMensajeRespuesta(configProps.getProperty("accesoConfigurado"));
			respuestaAccesoArchivoNacionalDTO.setMensajeError("OK");
			log.info(respuestaAccesoArchivoNacionalDTO.toString());
			return respuestaAccesoArchivoNacionalDTO;				
		} catch (SgdpException sgdpe) {
			log.log(sgdpe.getNivelLog(), sgdpe);
			respuestaAccesoArchivoNacionalDTO.setMensajeError("ERROR");
			respuestaAccesoArchivoNacionalDTO.setMensajeRespuesta(sgdpe.getMessage());
			log.info(respuestaAccesoArchivoNacionalDTO.toString());
			return respuestaAccesoArchivoNacionalDTO;					
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			respuestaAccesoArchivoNacionalDTO.setMensajeRespuesta(configProps.getProperty("errorAlGuardarAccesoArchivoNacional"));	
			respuestaAccesoArchivoNacionalDTO.setMensajeError("ERROR");
			log.info(respuestaAccesoArchivoNacionalDTO.toString());
			return respuestaAccesoArchivoNacionalDTO;	
		}		
	}
	
	@RequestMapping(value = "/guardarConfiguracionInstitucion", method = RequestMethod.POST)
	public @ResponseBody RespuestaAccesoArchivoNacionalDTO guardarConfiguracionInstitucion(@RequestBody AccesoArchivoNacionalDTO accesoArchivoNacionalDTO, Model model, HttpServletRequest request) {		 

		RespuestaAccesoArchivoNacionalDTO respuestaAccesoArchivoNacionalDTO = new RespuestaAccesoArchivoNacionalDTO();	
		log.info(accesoArchivoNacionalDTO.toString());
		try {

			Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
			this.accesoTransferenciaService.guardarConfigracionInstitucion(accesoArchivoNacionalDTO, usuario);
			respuestaAccesoArchivoNacionalDTO.setMensajeRespuesta(configProps.getProperty("institucionConfigurado"));
			respuestaAccesoArchivoNacionalDTO.setMensajeError("OK");
			log.info(respuestaAccesoArchivoNacionalDTO.toString());
			return respuestaAccesoArchivoNacionalDTO;				
		} catch (SgdpException sgdpe) {
			log.log(sgdpe.getNivelLog(), sgdpe);
			respuestaAccesoArchivoNacionalDTO.setMensajeError("ERROR");
			respuestaAccesoArchivoNacionalDTO.setMensajeRespuesta(sgdpe.getMessage());
			log.info(respuestaAccesoArchivoNacionalDTO.toString());
			return respuestaAccesoArchivoNacionalDTO;					
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			respuestaAccesoArchivoNacionalDTO.setMensajeRespuesta(configProps.getProperty("errorAlGuardarInstitucion"));	
			respuestaAccesoArchivoNacionalDTO.setMensajeError("ERROR");
			log.info(respuestaAccesoArchivoNacionalDTO.toString());
			return respuestaAccesoArchivoNacionalDTO;	
		}		
	}	

}
