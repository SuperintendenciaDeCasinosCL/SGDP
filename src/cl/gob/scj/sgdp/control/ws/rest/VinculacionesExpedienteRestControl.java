package cl.gob.scj.sgdp.control.ws.rest;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.gob.scj.sgdp.dto.rest.VinculacionExpedienteRestDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.ws.rest.service.VinculacionExpRestService;

@RestController
@RequestMapping("/vinculacion")
public class VinculacionesExpedienteRestControl {

	private static final Logger log = Logger.getLogger(VinculacionesExpedienteRestControl.class);
			
	@Autowired
	private VinculacionExpRestService vinculacionExpRestService;
	
	@RequestMapping(value="/getVinculacionesDeExpediente/{idExpediente}", method=RequestMethod.GET)
	public @ResponseBody VinculacionExpedienteRestDTO getVinculacionesDeExpediente(@PathVariable("idExpediente") String idExpediente, Model model, HttpServletRequest request) {		
		log.info("idExpediente: "+ idExpediente);
		try {
			VinculacionExpedienteRestDTO vinculacionExpedienteRestDTO = vinculacionExpRestService.getVinculacionExpedientRestDTO(idExpediente);
			vinculacionExpedienteRestDTO.setEstado("0");
			vinculacionExpedienteRestDTO.setGlosa("OK");
			log.info(vinculacionExpedienteRestDTO.toString());
			return vinculacionExpedienteRestDTO;
		} catch (Exception e) {
			log.debug("Cayo aca");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			VinculacionExpedienteRestDTO vinculacionExpedienteRestDTO = new VinculacionExpedienteRestDTO();
			vinculacionExpedienteRestDTO.setEstado("1");
			vinculacionExpedienteRestDTO.setGlosa("ERROR: " + e.getMessage());
			log.info(vinculacionExpedienteRestDTO.toString());
			return vinculacionExpedienteRestDTO;
		}
	}
	
	@RequestMapping(value = "/vincularExp", method = RequestMethod.POST) 
	public @ResponseBody VinculacionExpedienteRestDTO vincularExp(@RequestBody VinculacionExpedienteRestDTO vinculacionExpedienteRestDTO, HttpServletRequest request) {
		log.debug("Inicio vincularExp");
		log.debug(vinculacionExpedienteRestDTO.toString());		
		try {		
			vinculacionExpRestService.vincularExp(vinculacionExpedienteRestDTO);	
			vinculacionExpedienteRestDTO.setEstado("0");
			vinculacionExpedienteRestDTO.setGlosa("OK");
			return vinculacionExpedienteRestDTO;			
		} catch(SgdpException e) {
			log.log(e.getNivelLog(), e.getMessage());			
			vinculacionExpedienteRestDTO.setEstado("1");
			vinculacionExpedienteRestDTO.setGlosa("ERROR: " + e.getMessage());			
			return vinculacionExpedienteRestDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			vinculacionExpedienteRestDTO.setEstado("1");
			vinculacionExpedienteRestDTO.setGlosa("ERROR: " + e.getMessage());
			return vinculacionExpedienteRestDTO;
		}
	}
	
	
}
