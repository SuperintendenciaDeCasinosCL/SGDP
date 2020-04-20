package cl.gob.scj.sgdp.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.rest.BorraRegistroDocumentoRequestRest;
import cl.gob.scj.sgdp.dto.rest.BorraRegistroDocumentoResponseRest;
import cl.gob.scj.sgdp.dto.rest.GeneraRegistroDocumentoRequestRest;
import cl.gob.scj.sgdp.dto.rest.GeneraRegistroDocumentoResponseRest;

@Service
public interface RegistroDocumentoService {

	BorraRegistroDocumentoResponseRest borraRegistroDocumento(BorraRegistroDocumentoRequestRest borraRegistroDocumentoRequestRest) throws IOException;
	
	GeneraRegistroDocumentoResponseRest generaRegistroDocumento(GeneraRegistroDocumentoRequestRest generaRegistroDocumentoRequestRest, Usuario usuario) throws IOException;
	
}
