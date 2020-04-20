package cl.gob.scj.sgdp.service.impl;

import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.GestorMetadataService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.GestorMetadataCMSService;

@Service
public class GestorMetadataServiceImpl implements GestorMetadataService {
	
	private static final Logger log = Logger.getLogger(GestorMetadataServiceImpl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private GestorMetadataCMSService gestorMetadataCMSService;

	@Override
	public String agregarComentarioANodo(Usuario usuario, String idNodo, String comentario) throws SgdpException {		
		try {
			gestorMetadataCMSService.agregarComentarioANodo(usuario, idNodo, comentario);
			return configProps.getProperty("seAgregoElComentarioCorrectamente");
		} catch (Exception e) {
			log.error(e);
			throw new SgdpException(configProps.getProperty("errorAlAgregarComentario"));
		}

	}
	
	@Override
	public void actualizaUsuariosQueHanModificadoExpediente(Usuario usuario, String idExpediente, String idUsuarioQueParticipa) throws SgdpException {
		try {
			gestorMetadataCMSService.actualizaUsuariosQueHanParticipadoExpediente(usuario, idExpediente, idUsuarioQueParticipa);			
		} catch (Exception e) {
			log.error(e);
			throw new SgdpException(configProps.getProperty("errorAlActualizarUsuariosQueHanParticipado"));
		}
	}

}
