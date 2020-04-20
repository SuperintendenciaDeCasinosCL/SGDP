package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.SubirDocumentoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;

@Service
public interface SubirDocumentoService {

	public String subirDocumento(Usuario usuario, SubirDocumentoDTO subirDocumentoDTO) throws SgdpException;
	
}
