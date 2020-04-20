package cl.gob.scj.sgdp.dao;

import java.util.List;

import org.hibernate.Session;

import cl.gob.scj.sgdp.model.Parametro;

public interface ParametroDao extends GenericDao<Parametro> {

	public List<Parametro> getTodosLosParametros();
	
}
