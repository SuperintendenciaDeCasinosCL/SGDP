package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.DocumentoDTO;
import cl.gob.scj.sgdp.dto.EnviarArchivoNacionalDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;

@Service
public interface ArchivosInstDeTareaService {

	List<String> getIdDeDocumentosSubidosOficiales();
	
	List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdExpediente(String idExpediente);
	
	ArchivosInstDeTareaDTO getArchivosInstDeTareaPorIdArchivoIdInstanciaDeTareaIdUsuario(String idArchivo, long idInstanciaDeTarea, String idUsuario);

	List<ArchivosInstDeTareaDTO> getArchivosInstDeTareaPorIdSerie(long idSerie, Long idEstadoProceso, Long estadoDocumento, String fechaTransferirInicio, String fechaTransferirTermino);

	List<DocumentoDTO> getMetadataListaDocumentos(EnviarArchivoNacionalDTO enviarArchivoNacionalDTO) throws ArchivoNacionalException;

	void descartarArchivo(String idDocumentoCms) throws ArchivoNacionalException;
	
}
