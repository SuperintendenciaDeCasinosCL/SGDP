package cl.gob.scj.sgdp.ws.alfresco.rest.client;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.RespuestaActualizaMetaDataExpedienteDTO;
import cl.gob.scj.sgdp.dto.rest.ExpedienteRestActMetaDTO;

@Service
public interface GestorMetadataCMSService {
	
	void agregarComentarioANodo(Usuario usuario, String idNodo, String comentario) throws Exception;
	
	RespuestaActualizaMetaDataExpedienteDTO actualizaUsuariosQueHanParticipadoExpediente(Usuario usuario, String idExpediente, String idUsuarioQueParticipa) throws Exception;

	RespuestaActualizaMetaDataExpedienteDTO actualizaExpedientesAntecesores(Usuario usuario, String idExpediente, String nombreExpedienteAntecesor) throws Exception;

	RespuestaActualizaMetaDataExpedienteDTO remueveExpedientesAntecesor(Usuario usuario, String idExpediente, String nombreExpedienteAntecesor) throws Exception;
	
	RespuestaActualizaMetaDataExpedienteDTO actualizaMetaDataExpediente(Usuario usuario, ExpedienteRestActMetaDTO expedienteRestActMetaDTO) throws Exception;

}
