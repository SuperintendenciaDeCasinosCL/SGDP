package cl.gob.scj.sgdp.control.ws.rest;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.gob.scj.sgdp.dto.rest.EtapaDeInstanciaDeProcesoResponse;
import cl.gob.scj.sgdp.dto.rest.TiposDocumetosArchivoNacionalResponse;
import cl.gob.scj.sgdp.ws.rest.service.ArchivoNacionalRestService;

@RestController
@RequestMapping("/archivo")
public class ArchivoNacionalRestControl {
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Resource(name = "configProps")
	private Properties configProps;

	@Autowired
	ArchivoNacionalRestService archivoNacionalRestService; 

	@RequestMapping(value = "getTiposDocumetosArchivoNacional/{nombreExpediente}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody TiposDocumetosArchivoNacionalResponse getTiposDocumetosArchivoNacional (@PathVariable("nombreExpediente") String nombreExpediente,
			HttpServletRequest request) throws IOException {
				
		logger.info("Inicio getTiposDocumetosArchivoNacional");
				
		return archivoNacionalRestService.buscarTiposDocumetosArchivoNacional(nombreExpediente);
		
	}
}
