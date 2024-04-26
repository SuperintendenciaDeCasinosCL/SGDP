package cl.gob.scj.sgdp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.TipoDeDestinatarioDao;
import cl.gob.scj.sgdp.model.TipoDeDestinatario;
import cl.gob.scj.sgdp.model.TipoParametroDeTarea;

@Repository
public class TipoDeDestinatarioDaoImpl extends GenericDaoImpl<TipoDeDestinatario> implements TipoDeDestinatarioDao {	
	
	@Override
	public TipoDeDestinatario getTipoDeDestinatarioPorNombre(String nombreTipoDestinatario) {
		Query query = getSession().getNamedQuery("TipoDeDestinatario.getTipoDeDestinatarioPorNombre");
		query.setString("nombreTipoDestinatario", nombreTipoDestinatario);
		return (TipoDeDestinatario) query.uniqueResult();
	}	
	
	@Override
	public List<TipoDeDestinatario> getTiposDeDestinatarioEnListaDistribucion(long idTipoDestinatario) {
		Query query = getSession().getNamedQuery("TipoDeDestinatario.getTipoDeDestinatarioEnListaDistribucion");
		query.setLong("idTipoDestinatario", idTipoDestinatario);
		return query.list();
	}	
	
	
	

}