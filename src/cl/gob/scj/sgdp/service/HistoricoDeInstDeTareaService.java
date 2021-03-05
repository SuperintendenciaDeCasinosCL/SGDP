package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.HistoricoDeInstDeTareaDTO;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;

@Service
public interface HistoricoDeInstDeTareaService {

	void cargaHistorialDeTareasPorIdIntanciaDeTarea(long idInstanciaDeTarea, List<HistoricoDeInstDeTareaDTO> historicosDeInstDeTareaDTO);
	
	List<HistoricoDeInstDeTareaDTO> getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(String idExpediente);
	
	HistoricoDeInstDeTareaDTO getHistoricoDeInstDeTareaDTOPorIdInstanciaDeTareaDeDestinoMaxFechaMovimiento(long idInstanciaDeTarea);
	
	HistoricoDeInstDeTareaDTO getHisDeInstDeTareaPorIdInstDeTareaDTODeDestIdUsrOrigenMaxFechaMov(long idInstanciaDeTarea, String idUsuarioOrigen);
	
	int getCantidadDeEjecutacionesInstanciaDeTarea(long idInstanciaDeTarea);
	
}
