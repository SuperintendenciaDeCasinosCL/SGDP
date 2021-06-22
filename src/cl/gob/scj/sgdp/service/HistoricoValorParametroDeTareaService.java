package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.HistoricoValorParametroDeTareaDTO;

@Service
public interface HistoricoValorParametroDeTareaService {

	List<HistoricoValorParametroDeTareaDTO> getHistoricoValorParametroDeTareaPorIdInstanciaDeTareaOrigen(long idInstanciaDeTareaOrigen);
	
}
