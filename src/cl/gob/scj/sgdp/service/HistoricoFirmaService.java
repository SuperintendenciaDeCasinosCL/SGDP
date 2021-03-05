package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.HistoricoFirmaDTO;

@Service
public interface HistoricoFirmaService {
	
	List<HistoricoFirmaDTO> getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario(HistoricoFirmaDTO historicoFirmaDTOConsulta);
	
	List<HistoricoFirmaDTO> getHistoricoFirmaDocumentoFEAPorIdArchivo(String idArchivoCMS) throws Exception;
	
	HistoricoFirmaDTO getUltimoHistoricoFirmaDocumentoFEAPorIdArchivo(String idArchivoCMS);
	
	long getIdDocumentoFirmado();
	
	HistoricoFirmaDTO getHistoricoFirmaDTOPorIdDocumentoFirmado(long idDocumentoFirmado);
	
	boolean validaSiHayFirmaHoy(Long idTipoDeDocumento, Long idInstanciaDeTarea, String idUsuario);

}
