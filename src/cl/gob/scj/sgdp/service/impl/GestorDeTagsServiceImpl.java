package cl.gob.scj.sgdp.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.GestorDeTagsService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.GestorDeTagsCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.AgregaRemueveTagDeObjetoResponse;

@Service
public class GestorDeTagsServiceImpl implements GestorDeTagsService {

	private static final Logger log = Logger.getLogger(GestorDeTagsServiceImpl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private GestorDeTagsCMSService gestorDeTagsCMSService;
	
	private List<String> listaDeTags;	
	
	@Override
	public List<String> obtenerListaDeTags(Usuario usuario) throws SgdpException {		
		log.debug("Inicio... obtenerListaDeTags");
		try {			
			if (listaDeTags == null) {
				listaDeTags = new ArrayList<String>();	
				List<String> listaDeTagsResp = gestorDeTagsCMSService.obtenerTodosLosTags(usuario);
				for (String tag: listaDeTagsResp ) {
					listaDeTags.add(tag);
				}				
			}
			return listaDeTags;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);			
			throw new SgdpException(configProps.getProperty("errorAlObtenerListaDeTag"));
		}		
	}
	
	@Override
	public void agregaRemueveTagsDeObjeto(Usuario usuario, SubirArhivoDTO subirArhivoDTO, String accion) throws SgdpException {
		log.debug("Inicio... agregaRemueveTagDeObjeto");
		log.debug("accion: " + accion);
		try {
			for (String tag: subirArhivoDTO.getIdTags()) {
				log.debug("tag: " + tag);
				AgregaRemueveTagDeObjetoResponse agregaRemueveTagDeObjetoResponse = gestorDeTagsCMSService.agregaRemueveTagDeObjeto(usuario, subirArhivoDTO.getIdArchivoEnCMS(), tag, accion);
			}
			log.debug("Fin... agregaRemueveTagDeObjeto");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);			
			throw new SgdpException(configProps.getProperty("errorAlAgregarORemoverTagDeObjeto"));
		}	
	}

}
