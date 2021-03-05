package cl.gob.scj.sgdp.ws.alfresco.rest.client;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.RespuestaConversionArchivoDTO;
import cl.gob.scj.sgdp.dto.RespuestaSimpleDTO;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.ArchivoImagenQRPorUsuarioResponse;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.FirmaSimpleDocumentoResponse;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.IdArchivoPorIdUsrNomCarpetaResponse;

@Service
public interface GestorDeDocumentosCMSService {
	
	FirmaSimpleDocumentoResponse firmaSimpleDocumento(String idDocumento, Usuario usuario) throws Exception;
	
	byte[] getContenidoArchivo(String idArchivo, Usuario usuario) throws Exception;
	
	ArchivoImagenQRPorUsuarioResponse getArchivoImagenQRPorUsuario(Usuario usuario) throws Exception ;
	
	RespuestaSimpleDTO actualizaMetaDataDeDocumento(Usuario usuario, DetalleDeArchivoDTO detalleDeArchivoDTO) throws Exception;
	
	RespuestaConversionArchivoDTO convertirArchivoAPDF(Usuario usuario, String idArchivo) throws Exception;
	
	byte[] convertirArchivoAPDF(Usuario usuario, byte[] byteArchivoAConvertir);
	
	IdArchivoPorIdUsrNomCarpetaResponse getIdArchivoPorIdUsrNomCarpeta(Usuario usuario, String nombreCarpeta) throws Exception;
	
	byte[] getContenidoArchivoDesdeUrlYVersion(DetalleDeArchivoDTO detalleDeArchivoDTO, String version, Usuario usuario) throws Exception;
	
	//RespuestaSimpleDTO copiaArchivo(Usuario usuario, String idExpedienteOrigen, String idExpedienteDestino, String idArchivo, String nuevoNombre) throws Exception;
	
}
