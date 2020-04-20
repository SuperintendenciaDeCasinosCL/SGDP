package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;

@Service
public interface ArchivosInstDeTareaService {

	List<String> getIdDeDocumentosSubidosOficiales();
	
	List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdExpediente(String idExpediente);
	
	ArchivosInstDeTareaDTO getArchivosInstDeTareaPorIdArchivoIdInstanciaDeTareaIdUsuario(String idArchivo, long idInstanciaDeTarea, String idUsuario);
	
}
