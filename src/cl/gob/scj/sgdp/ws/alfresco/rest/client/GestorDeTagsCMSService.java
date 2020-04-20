package cl.gob.scj.sgdp.ws.alfresco.rest.client;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.AgregaRemueveTagDeObjetoResponse;

@Service
public interface GestorDeTagsCMSService {

	//public ObtenerTodosLosTagsResponse obtenerTodosLosTags(Usuario usuario) throws Exception;
	
	public List<String> obtenerTodosLosTags(Usuario usuario) throws Exception;
	
	public AgregaRemueveTagDeObjetoResponse agregaRemueveTagDeObjeto(Usuario usuario, String idObjeto, String tag, String accion) throws Exception;
	
}
