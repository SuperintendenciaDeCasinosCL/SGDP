package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.RespuestaAsignarNumeroDocumento;
import cl.gob.scj.sgdp.dto.RespuestaMailDTO;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.exception.SgdpExceptionValidaTareaEnBE;

@Service
public interface SubirArchivoService {

	SubirArhivoDTO subirArchivo(Usuario usuario,
			SubirArhivoDTO subirArhivoDTO) throws SgdpException, SgdpExceptionValidaTareaEnBE;
	
	RespuestaAsignarNumeroDocumento agregarNumeroDocumentoPDF(MultipartFile archivo , long IdTipoDeDocumentoSubir) throws SgdpException, SgdpExceptionValidaTareaEnBE;
	
	void marcarArchivoComoSubido(Usuario usuario, SubirArhivoDTO subirArhivoDTO) throws SgdpException, SgdpExceptionValidaTareaEnBE;

	public SubirArhivoDTO subirArchivoConAsignacion(Usuario usuario, SubirArhivoDTO subirArhivoDTO) throws SgdpException, SgdpExceptionValidaTareaEnBE;
	
	RespuestaMailDTO guardarHistoricoArchivoYTareas(
			SubirArhivoDTO listaSubirArhivoDTO, Usuario usuario);	
}
