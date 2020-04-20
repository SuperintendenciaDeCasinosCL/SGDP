package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.exception.SgdpExceptionValidaTareaEnBE;
import cl.gob.scj.sgdp.service.SubirArchivoService;

@Controller
public class SubirArchivoControl {

	private static final Logger log = Logger.getLogger(SubirArchivoControl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
		
	@Autowired
	private SubirArchivoService subirArchivoService;
	
	// Subir Archivo a Alfresco y marcar como subido
	@RequestMapping(value = "/subirArchivo", method = RequestMethod.POST)
	public @ResponseBody SubirArhivoDTO subirArchivo(@ModelAttribute("subirArhivoDTO") SubirArhivoDTO subirArhivoDTO, HttpServletRequest request) {
		try {
			log.info("Inicio subirArchivo");
			log.info(subirArhivoDTO.toString());	
			log.debug("subirArhivoDTO.getArchivo().getSize(): " + subirArhivoDTO.getArchivo().getSize());
			if (subirArhivoDTO.getArchivo()!=null) {
				log.info("subirArhivoDTO.getArchivo().getContentType(): " + subirArhivoDTO.getArchivo().getContentType());
			}
			Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");			
			subirArhivoDTO = subirArchivoService.subirArchivo(usuario, subirArhivoDTO);
			subirArhivoDTO.setResultadoSubirArchivo(configProps.getProperty("respuestaOK")); 
			subirArhivoDTO.setCssStatus(configProps.getProperty("cssSucess"));
			log.info(subirArhivoDTO.toString());
			return subirArhivoDTO;
		} catch (SgdpExceptionValidaTareaEnBE sgdpeBE) {
			log.log(sgdpeBE.getNivelLog(), sgdpeBE.getMessage());
			subirArhivoDTO.setResultadoSubirArchivo(sgdpeBE.getMessage()); 
			subirArhivoDTO.setCssStatus(configProps.getProperty("cssWaring"));
			subirArhivoDTO.setRecarga(true);
			log.info(subirArhivoDTO.toString());
			return subirArhivoDTO;
		} catch (SgdpException sgdpe) {
			if (sgdpe.getNivelLog()!=null) {
				/*StringWriter sw = new StringWriter();
				sgdpe.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.log(sgdpe.getNivelLog(), exceptionAsString);*/
				log.log(sgdpe.getNivelLog(), sgdpe);
			} else {
				log.error("PROBLEMA al subir archivo: ", sgdpe);
			}						
			subirArhivoDTO.setResultadoSubirArchivo(sgdpe.getMessage());
			subirArhivoDTO.setCssStatus(configProps.getProperty("cssError"));
			log.info(subirArhivoDTO.toString());
			return subirArhivoDTO;
		}  catch (Exception e) {
			log.error("ERROR al subir archivo: ", e);
			subirArhivoDTO.setResultadoSubirArchivo(configProps.getProperty("respuestaError"));
			subirArhivoDTO.setCssStatus(configProps.getProperty("cssError"));
			log.info(subirArhivoDTO.toString());
			return subirArhivoDTO;			
		}		
	}
	
	//Marcar Archivo como subido
	@RequestMapping(value = "/marcarArchivoComoSubido", method = RequestMethod.POST)
	public @ResponseBody SubirArhivoDTO marcarArchivoComoSubido(@ModelAttribute("subirArhivoDTO") SubirArhivoDTO subirArhivoDTO, HttpServletRequest request) {
		log.info("Inicio marcarArchivoComoSubido");
		log.info(subirArhivoDTO.toString());
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		try {			
			subirArchivoService.marcarArchivoComoSubido(usuario, subirArhivoDTO);		
			subirArhivoDTO.setResultadoSubirArchivo(configProps.getProperty("respuestaOK")); 
			subirArhivoDTO.setCssStatus(configProps.getProperty("cssSucess"));
			log.info(subirArhivoDTO.toString());
			return subirArhivoDTO;
		} catch (SgdpExceptionValidaTareaEnBE sgdpeBE) {
			log.log(sgdpeBE.getNivelLog(), sgdpeBE.getMessage());
			subirArhivoDTO.setResultadoSubirArchivo(sgdpeBE.getMessage()); 
			subirArhivoDTO.setCssStatus(configProps.getProperty("cssWaring"));
			subirArhivoDTO.setRecarga(true);
			log.info(subirArhivoDTO.toString());
			return subirArhivoDTO;
		} catch (SgdpException sgdpe) {
			log.error("ERROR al subir archivo: ", sgdpe);			
			subirArhivoDTO.setResultadoSubirArchivo(sgdpe.getMessage());
			subirArhivoDTO.setCssStatus(configProps.getProperty("cssError"));
			log.info(subirArhivoDTO.toString());
			return subirArhivoDTO;
		}	
	}
	
	
	// Subir Archivo a Alfresco y marcar como subido
	@RequestMapping(value = "/subirArchivoConAsignacion", method = RequestMethod.POST)
	public @ResponseBody SubirArhivoDTO subirArchivoConAsignacion(@RequestBody SubirArhivoDTO subirArhivoDTO, HttpServletRequest request) {
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		
		try {
						
			log.info("Inicio subirArchivoConAsignacion");
			
			subirArchivoService.subirArchivoConAsignacion(usuario, subirArhivoDTO);
			subirArhivoDTO.setResultadoSubirArchivo(configProps.getProperty("respuestaOK")); 
			subirArhivoDTO.setCssStatus(configProps.getProperty("cssSucess"));
			log.info(subirArhivoDTO.toString());
			return subirArhivoDTO;

		}  catch (SgdpExceptionValidaTareaEnBE sgdpeBE) {
			log.log(sgdpeBE.getNivelLog(), sgdpeBE.getMessage());
			subirArhivoDTO.setResultadoSubirArchivo(sgdpeBE.getMessage()); 
			subirArhivoDTO.setCssStatus(configProps.getProperty("cssWaring"));
			subirArhivoDTO.setRecarga(true);
			log.info(subirArhivoDTO.toString());
			return subirArhivoDTO;
		} catch (Exception sgdpe) {
			log.error("ERROR al subir archivo: ", sgdpe);			
			subirArhivoDTO.setResultadoSubirArchivo(sgdpe.getMessage());
			subirArhivoDTO.setCssStatus(configProps.getProperty("cssError"));
			log.info(subirArhivoDTO.toString());
			return subirArhivoDTO;		
		}		
	}
	
	
}
