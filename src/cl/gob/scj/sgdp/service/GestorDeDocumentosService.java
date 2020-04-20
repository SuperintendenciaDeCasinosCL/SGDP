package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.FirmaAvanzadaDTO;
import cl.gob.scj.sgdp.dto.HistoricoFirmaDTO;
import cl.gob.scj.sgdp.dto.KeyParametroPorContextoDTO;
import cl.gob.scj.sgdp.dto.RespuestaConversionArchivoDTO;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.FirmaSimpleDocumentoResponse;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.request.FirmaAvanzadaArchivoRequest;
import cl.gob.scj.sgdp.ws.firmaElectronica.rest.request.FirmaAvanzadaRequest;

@Service
public interface GestorDeDocumentosService {

	FirmaSimpleDocumentoResponse firmaSimpleDocumento(String idDocumento, Usuario usuario, long idInstanciaDeTarea, long idTipoDeDocumento) throws SgdpException;
	
	String getIdArchivoImagenQRPorUsuario(Usuario usuario) throws SgdpException;
	
	byte[] getContenidoArchivo(String idArchivo, Usuario usuario) throws SgdpException;

	FirmaAvanzadaDTO firmarDocumentoConFEA(FirmaAvanzadaRequest firmaAvanzadaRequest,
			FirmaAvanzadaArchivoRequest firmaAvanzadaArchivoRequest, 
			FirmaAvanzadaDTO firmaAvanzadaDTO,
			Usuario usuario, KeyParametroPorContextoDTO keyParametroPorContextoDTOTipoDocFeaPorMimeTypeEnCMS);

	String actualizaMetaDataDeDocumento(Usuario usuario, DetalleDeArchivoDTO detalleDeArchivoDTO) throws SgdpException;
	
	String getJnlp(String iddoc, String ticket, String nameDoc, String idexpediente,long idTipoDeDocumento, Usuario usuario, long idInstanciaDeTarea) throws Exception;
	
	void registraFirma(String idUsuario, HistoricoFirmaDTO historicoFirmaDTO);
	
	//RespuestaConversionArchivoDTO convertirArchivoAPDFYSubirACMS(Usuario usuario, String idArchivo) throws SgdpException;
	
	RespuestaConversionArchivoDTO convertirArchivoAPDF(Usuario usuario, SubirArhivoDTO subirArhivoDTO) throws Exception;
	
	byte[] getContenidoArchivoDesdeUrlYVersion(DetalleDeArchivoDTO detalleDeArchivoDTO, String version, Usuario usuario) throws Exception;
	
}
