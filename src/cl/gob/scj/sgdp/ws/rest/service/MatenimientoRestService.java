package cl.gob.scj.sgdp.ws.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.rest.ExpedienteRestActMetaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;

@Service
public interface MatenimientoRestService {
	
	String actualizaMetaDataExpedientes(Usuario usuario, List<ExpedienteRestActMetaDTO> lista) throws SgdpException;

}