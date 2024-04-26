package cl.gob.scj.sgdp.dao;

import java.util.List;


import cl.gob.scj.sgdp.model.UnidadOperativa;

public interface UnidadOperativaDao extends GenericDao<UnidadOperativa>{
	
	public List<UnidadOperativa> getTodasUnidadesOperativas();
	
	public UnidadOperativa getUnidadOperativaPorId(long idUnidadOperativa);

}
