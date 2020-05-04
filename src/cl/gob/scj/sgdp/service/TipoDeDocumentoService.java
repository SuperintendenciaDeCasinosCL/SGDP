package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.model.TipoDeDocumento;

@Service
public interface TipoDeDocumentoService {

	TipoDeDocumentoDTO getTipoDeDocumentoDTOPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(String nombreDeTipoDeDocumento, long idInstanciaDeTarea);
	
	TipoDeDocumentoDTO getTipoDeDocumentoDTOPorIdTipoDeDocumento(long idTipoDeDocumento);
	
	TipoDeDocumentoDTO getTipoDeDocumentoDTOPorIdProceso(long idProceso);
	
	List<TipoDeDocumentoDTO> getTodosLosTiposDeDocumentos();
	
	//TipoDeDocumentoDTO getTipoDeDocumentoPorNombreDeTipoDeDocumentoPorIdExpediente(String nombreDeTipoDeDocumento, String idExpediente);

	List<TipoDeDocumentoDTO> getTipoDeDocumentoPrimeraTareaDocAdiccionales();
	
	List<TipoDeDocumentoDTO> getTiposDeDocumentosPorCodigoProceso(String codigoProceso);
	
	List<TipoDeDocumentoDTO> getTiposDeDocumentosPorNombreExpediente(String nombreExpediente);
	
	List<TipoDeDocumentoDTO> getTiposDeDocumentosPorIdExpediente(String idExpediente);
}
