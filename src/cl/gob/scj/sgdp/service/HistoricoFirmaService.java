package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.HistoricoFirmaDTO;
import cl.gob.scj.sgdp.model.HistoricoFirma;

@Service
public interface HistoricoFirmaService {
	
	List<HistoricoFirmaDTO> getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario(HistoricoFirmaDTO historicoFirmaDTOConsulta);
	
	List<HistoricoFirmaDTO> getHistoricoFirmaDocumentoFEAPorIdArchivo(String idArchivoCMS) throws Exception;

}
