package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.HistoricoDeDocumentos;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;


@Service
public interface InstanciaDeTareaService {
	
	void cargaInstanciaDeTareaPorIdInstanciaDeTarea(long idInstanciaDeTarea, InstanciaDeTareaDTO instanciaDeTareaDTO);
	
	void cargaInstanciasDeTareasDTOPorIdExpediente(String idExpediente, List<InstanciaDeTareaDTO> instanciasDeTareasDTO) throws SgdpException;
	
	List<HistoricoDeDocumentos> getTablaHistorialDeDocumentoPorIdExpediente (String idExpediente , Integer idInstanciaDeTarea, Usuario usuario);
	
	List<HistoricoDeDocumentos> buscarHistorialDeDocumento (Integer idInstanciaDeTarea);
	
	void cargaTodosLosDocSubidosEnInstaciaDeProcPorIdInstTareaDeInstDeTareasAntOIguales(
			String idExpediente, long idInstanciaDeTarea, Usuario usuario, List<ArchivosInstDeTareaDTO> todosLosDocSubidos);
	
	List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdInstTareaDeInstDeTareasAntOIguales(
			String idExpediente, long idInstanciaDeTarea, Usuario usuario);
	
	List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdInstTarea(long idInstanciaDeTarea);
	
	InstanciaDeTarea getInstanciaDeTareaPorIdInstanciaDeTarea(
			long idInstanciaDeTarea);
	
	List<InstanciaDeTareaDTO> getInstanciaDeTareaDTOAnteriores(InstanciaDeTareaDTO instanciaDeTareaDTO);
	
	InstanciaDeTareaDTO getInstanciaDeTareaPorIdDiagramaTareaNombreExpediente(String idDiagramaTarea, String nombreExpediente);
	
	//void cargaInstanciaDeTareaDTO(InstanciaDeTarea instanciaDeTarea, InstanciaDeTareaDTO instanciaDeTareaDTO);

}
