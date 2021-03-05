package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.HistoricoValorParametroDeTareaDTO;

@Service
public interface HistoricoValorParametroDeTareaService {

	List<HistoricoValorParametroDeTareaDTO> getHistoricoValorParametroDeTareaPorIdInstanciaDeTarea(long idInstanciaDeTarea);
	
	public List<HistoricoValorParametroDeTareaDTO> getHistoricoValorParametroDeTareaPorIdExpediente(String idExpediente);
	
	List<HistoricoValorParametroDeTareaDTO> getHistoricoValorParametroDeTareaPorIdHistoricoInstanciaDeTarea(long idHistoricoDeInstDeTarea);
	
}
