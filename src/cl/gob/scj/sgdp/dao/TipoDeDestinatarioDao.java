package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.TipoDeDestinatario;

public interface TipoDeDestinatarioDao extends GenericDao<TipoDeDestinatario> {
	
	TipoDeDestinatario getTipoDeDestinatarioPorNombre(String nombreTipoDestinatario);
	
	List<TipoDeDestinatario> getTiposDeDestinatarioEnListaDistribucion(long idTipoDestinatario);
	
}