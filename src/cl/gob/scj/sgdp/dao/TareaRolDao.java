package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.TareaRol;

public interface TareaRolDao {
	
	public List<TareaRol> getTareasRolesPorTarea(Tarea tarea);

}
