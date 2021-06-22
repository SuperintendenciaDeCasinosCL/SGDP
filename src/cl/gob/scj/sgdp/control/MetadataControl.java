package cl.gob.scj.sgdp.control;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cl.gob.scj.sgdp.dto.MensajeVistaDTO;
import cl.gob.scj.sgdp.dto.MetadataDTO;
import cl.gob.scj.sgdp.service.MetadataService;

@Controller
public class MetadataControl {		

	private static final Logger log = Logger.getLogger(MetadataControl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private MetadataService metadataService;

	
	@RequestMapping("/getTablaDetalleMetadataExpedienteDocumentoPorIdExpediente")	
	public String getTablaDetalleMetadataExpedienteDocumentoPorIdExpediente(@RequestParam("idExpediente") String idExpediente,
			Model model, HttpServletRequest request) {
					
		log.debug("Inicio getTablaDetalleMetadataExpedienteDocumentoPorIdExpediente");
		try {
			List<MetadataDTO> list = this.metadataService.getMetadataByIdExpediente(idExpediente);
			model.addAttribute("metadataList", list);
		} catch (Exception anex) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			mensajeVistaDTO.getMensajes().put(anex.getMessage(), configProps.getProperty("cssError"));
			model.addAttribute("mensajeVistaDTO", mensajeVistaDTO);
		}
		
		log.debug("Fin getTablaDetalleMetadataExpedienteDocumentoPorIdExpediente");
		return this.configProps.getProperty("vistaTablaDetalleMetadata");
	}	
		
	
}
