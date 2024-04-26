package cl.gob.scj.sgdp.dao;

import org.hibernate.Session;

import cl.gob.scj.sgdp.model.ReferenciaDeTarea;

public interface ReferenciaDeTareaDao {
	

	Long guardar(ReferenciaDeTarea ref, Session session);

}
