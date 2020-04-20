package cl.gob.scj.sgdp.dao;

import cl.gob.scj.sgdp.model.EstadoDeProceso;

public interface EstadoDeProcesoDao {
	
	public EstadoDeProceso getEstadoDeProcesoPorId(long idEstadoDeProceso);
	
	public EstadoDeProceso getEstadoDeProcesoPorCodigo(int codigoEstadoDeProceso);

}
