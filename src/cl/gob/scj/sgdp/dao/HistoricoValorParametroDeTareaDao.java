package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.HistoricoValorParametroDeTarea;

public interface HistoricoValorParametroDeTareaDao extends GenericDao<HistoricoValorParametroDeTarea>  {
	
	List<HistoricoValorParametroDeTarea> getHistoricoValorParametroDeTareaPorIdInstanciaDeTarea(long idInstanciaDeTarea);
	
	List<HistoricoValorParametroDeTarea> getHistoricoValorParametroDeTareaPorIdExpediente(String idExpediente);
	
	List<HistoricoValorParametroDeTarea> getHistoricoValorParametroDeTareaPorIdHistoricoInstanciaDeTarea(long idHistoricoDeInstDeTarea);

}
