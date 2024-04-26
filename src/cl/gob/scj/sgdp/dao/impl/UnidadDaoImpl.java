package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.UnidadDao;

import cl.gob.scj.sgdp.model.Unidad;

@Repository
public class UnidadDaoImpl extends GenericDaoImpl<Unidad> implements UnidadDao {	
		
	@SuppressWarnings("unchecked")
	@Override
	public List<Unidad> getTodasLasUnidades() {
		// TODO Auto-generated method stub
		return getSession().createQuery("from Unidad").list();
	}

	@Override
	public Unidad getUnidadPorId(long idUnidad) 
	{
		return (Unidad) getSession().get(Unidad.class, idUnidad);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unidad> getUnidadesPorIdUnidadOperativa(long idUnidadOperativa) {
		Query query = getSession().getNamedQuery("Unidad.getUnidadesPorIdUnidadOperativa");
		query.setLong("idUnidadOperativa", idUnidadOperativa);
		return query.list();		
	}

}