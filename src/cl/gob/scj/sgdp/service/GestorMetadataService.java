package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.exception.SgdpException;

@Service
public interface GestorMetadataService {

	String agregarComentarioANodo(Usuario usuario, String idNodo, String comentario) throws SgdpException;
	
	void actualizaUsuariosQueHanModificadoExpediente(Usuario usuario, String idExpediente, String idUsuarioQueParticipa) throws SgdpException;
	
}
