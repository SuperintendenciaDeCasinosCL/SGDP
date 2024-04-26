package cl.gob.scj.sgdp.dao;

import java.util.List;

import org.hibernate.Session;

import cl.gob.scj.sgdp.model.Responsabilidad;
import cl.gob.scj.sgdp.model.ResponsabilidadTarea;

public interface ResponsabilidadDao {
	
	List<Responsabilidad> getAllByProcessId(long processId);
	
	Long guardar(Responsabilidad r, Session session);

}
