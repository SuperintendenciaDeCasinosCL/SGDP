package cl.gob.scj.sgdp.dao;

import java.util.Date;
import java.util.List;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.HistoricoDeInstDeTareaDTO;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;

public interface HistoricoDeInstDeTareaDao {
	
	HistoricoDeInstDeTarea getHistoricoDeInstDeTareaPorId(long idHistoricoDeInstDeTarea);
	
	void insertHistoricoDeInstDeTarea(HistoricoDeInstDeTarea historicoDeInstDeTarea, Usuario usuario);
	
	HistoricoDeInstDeTarea getUltimoHistoricoDeInstDeTareaPorInstanciaDeTareaDeDestino(InstanciaDeTarea instanciaDeTareaDeDestino);
	
	List<HistoricoDeInstDeTarea> getHistorialDeTareasPorIdIntanciaDeTarea(long idInstanciaDeTarea);
	
	List<HistoricoDeInstDeTarea> getHistoricoDeInstDeTareaPorIdExpediente(String idExpediente);
	
	List<HistoricoDeInstDeTareaDTO> getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(String idExpediente);
	
	HistoricoDeInstDeTarea getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovimiento(long idInstanciaDeTarea);
	
	List<HistoricoDeInstDeTarea> getTodosEnviaDevuelveReasignaHistoricoDeInstDeTareaPorIdInstanciaDeProceso(long idInstanciaDeProceso);	
	
	HistoricoDeInstDeTarea getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovEnviaODevuelve(long idInstanciaDeTarea);
		
	HistoricoDeInstDeTarea getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovEnviaODevuelveMenFech(long idInstanciaDeTarea, Date fechaMovimiento);
	
	HistoricoDeInstDeTarea getHisDeInstDeTareaPorIdInstDeTareaDeDestIdUsrOrigenMaxFechaMov(long idInstanciaDeTarea, String idUsuarioOrigen);
	
	HistoricoDeInstDeTarea getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeOrigenYDestinoMaxFechaMovimiento(long idInstanciaDeTareaOrigen, long idInstanciaDeTareaDestino);
	
	HistoricoDeInstDeTarea getHistDeInstDeTareaPorIdInstaDeTareaDeOrigenMaxFechaMov(long idInstanciaDeTareaOrigen);
	
	List<HistoricoDeInstDeTarea> getHistoricosDeInstDeTareaPorIdInstanciaDeTareaDeOrigenYDestino(long idInstanciaDeTareaOrigen, long idInstanciaDeTareaDestino);

}
