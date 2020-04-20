package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.HistoricoVinculacionExp;

public interface HistoricoVinculacionExpDao extends GenericDao<HistoricoVinculacionExp> {
		
	List<HistoricoVinculacionExp> getHistoricoVinculacionExpPorIdInstProcIdInstProcAntecesor(long idInstanciaDeProceso, long idInstanciaDeProcesoAntecesor);

}