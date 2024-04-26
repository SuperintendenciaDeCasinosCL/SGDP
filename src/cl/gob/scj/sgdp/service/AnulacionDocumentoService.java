package cl.gob.scj.sgdp.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.AnulacionDoctoDto;
import cl.gob.scj.sgdp.dto.ResponseDto;
import cl.gob.scj.sgdp.exception.SgdpException;

@Service
public interface AnulacionDocumentoService {

	public ResponseDto getListWithLimits(Map<String, String> allRequestParams, Usuario usuario);
	public ResponseDto anulDocumentByIdFileCms(AnulacionDoctoDto anulacionDoctoDto, Usuario usuario) throws Exception;
}
