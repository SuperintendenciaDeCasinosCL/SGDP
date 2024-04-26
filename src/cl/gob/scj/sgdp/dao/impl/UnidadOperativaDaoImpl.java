package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.UnidadOperativaDao;
import cl.gob.scj.sgdp.model.UnidadOperativa;

@Repository
public class UnidadOperativaDaoImpl extends GenericDaoImpl<UnidadOperativa> implements UnidadOperativaDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadOperativa> getTodasUnidadesOperativas() {
		
		return getSession().createQuery("from UnidadOperativa").list();
	}

	@Override
	public UnidadOperativa getUnidadOperativaPorId(long idUnidadOperativa) {
		
		return (UnidadOperativa) getSession().get(UnidadOperativa.class, idUnidadOperativa);
	}
	
	

	

}
