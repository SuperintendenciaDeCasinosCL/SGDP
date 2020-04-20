package cl.gob.scj.sgdp.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.ObtenerDetalleDeArchivoService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.ObtenerDetalleDeArchivoCMSService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ObtenerDetalleDeArchivoServiceImpl implements ObtenerDetalleDeArchivoService {

private static final Logger log = Logger.getLogger(ObtenerDetalleDeArchivoServiceImpl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private ObtenerDetalleDeArchivoCMSService obtenerDetalleDeArchivoCMSService;
	
	@Override
	public DetalleDeArchivoDTO obtenerDetalleDeArchivo(Usuario usuario, String idArchivo) throws SgdpException {
		log.debug("Inicio... obtenerDetalleDeArchivo");
		try {
			return obtenerDetalleDeArchivoCMSService.obtenerDetalleDeArchivo(usuario, idArchivo).getDetalleDeArchivoDTO();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);		
			log.debug("Fin... obtenerDetalleDeArchivo.. error");
			throw new SgdpException(configProps.getProperty("errorAlObtenerDetalleDeArchivo"));
		}
		
	}

}
