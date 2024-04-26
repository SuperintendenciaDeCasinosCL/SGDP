package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.ResponsabilidadTarea;

public interface ResponsabilidadTareaDao {
	
	List<ResponsabilidadTarea> getResponsabilidadesTareasPorIdTarea(long idTarea);
	List<ResponsabilidadTarea> getResponsabilidadesTareasPorIdProceso(long idProceso);
}
