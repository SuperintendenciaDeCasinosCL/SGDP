package cl.gob.scj.sgdp.dao;

import cl.gob.scj.sgdp.model.HistoricoSeguimientoIntanciaProceso;
import cl.gob.scj.sgdp.model.SeguimientoIntanciaProceso;

public interface SeguimientoIntanciaProcesoDao {	
	
	void guardarSeguimiento(SeguimientoIntanciaProceso seguimientoIntanciaProceso);
	
	void guardarSeguimientoHistorico(HistoricoSeguimientoIntanciaProceso historicoSeguimientoIntanciaProceso);
	
	void borrarSeguimiento(SeguimientoIntanciaProceso seguimientoIntanciaProceso);
	
	long buscaSiTieneSeguimiento(String idUsuario, long idInstanciaDeProceso);

}
