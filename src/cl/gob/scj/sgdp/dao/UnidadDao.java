package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.Unidad;

public interface UnidadDao extends GenericDao<Unidad> {
	
	public List<Unidad> getTodasLasUnidades();

}
