package cl.gob.scj.sgdp.dao;

import java.util.List;

import org.hibernate.Session;

import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.model.TipoDeDocumento;

public interface TipoDeDocumentoDao {

	List<TipoDeDocumento> getTodosLosTiposDeDocumentos();

	TipoDeDocumento getTipoDeDocumentoPorIdTipoDeDocumento(long idTipoDeDocumento);

	TipoDeDocumento getTipoDeDocumentoPorNombreDeTipoDeDocumento(String nombreDeTipoDeDocumento);

	TipoDeDocumento getTipoDeDocumentoPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(String nombreDeTipoDeDocumento,
			long idInstanciaDeTarea);

	List<TipoDeDocumento> getTiposDeDocumentosDeSalidaPorIdInstanciaDeTarea(long idInstanciaDeTarea);

	TipoDeDocumento getTipoDeDocumentoPorIdProceso(long idProceso);

	TipoDeDocumento getTipoDeDocumentoRequeridoPorIdTipoDeDocumentoIdExpediente(long idTipoDeDocumento,
			String idExpediente);

	TipoDeDocumento getTipoDeDocumentoPorNombreDeTipoDeDocumentoPorIdExpediente(String nombreDeTipoDeDocumento,
			String idExpediente);

	List<TipoDeDocumentoDTO> getTipoDeDocumentoPrimeraTareaDocAdiccionales();

	List<String> buscaTodosLosNombreDeLosDocumentos();

	List<String> buscaTodosLosNombreDeLosDocumentosSubidos();

	List<TipoDeDocumento> buscarTipoDeDocumentoPrimeraTareaPorCodigoProceso(String codigoProceso);

	List<TipoDeDocumento> getTiposDeDocumentosPorCodigoProceso(String codigoProceso);

	List<TipoDeDocumento> getTiposDeDocumentosPorNombreExpediente(String nombreExpediente);
	
	List<TipoDeDocumento> getTiposDeDocumentosPorIdExpediente(String idExpediente);
	
	Long guardar(TipoDeDocumento td, Session session);

	List<TipoDeDocumento> getTiposDeDocumentosPorIdProceso(Long idProceso);
	
	List<TipoDeDocumento> getTiposDeDocumentosPorCodigoPlantilla(String codigoPlantilla, boolean vigente);

}
