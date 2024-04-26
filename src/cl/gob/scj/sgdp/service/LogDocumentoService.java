package cl.gob.scj.sgdp.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.LogDocumentoDTO;
import cl.gob.scj.sgdp.dto.ResponseDto;

@Service
public interface LogDocumentoService {

	ResponseDto getListWithLimits(Map<String,String> allRequestParams);
	
	LogDocumentoDTO insertLogDocumento(Usuario usuario, LogDocumentoDTO dto) throws Exception;
	
	LogDocumentoDTO insertLogDocumentoSolicitudCreacionExpediente(Usuario usuario, LogDocumentoDTO dto) throws Exception;
	
	ResponseDto getById(long id);

}
