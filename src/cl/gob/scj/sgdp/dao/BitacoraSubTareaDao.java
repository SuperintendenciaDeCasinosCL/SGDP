package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.BitacoraSubTareas;


public interface BitacoraSubTareaDao extends GenericDao<BitacoraSubTareas> {	
	
	List<BitacoraSubTareas> getAllByIdInstTarea(Long idInstTarea);
	
	BitacoraSubTareas findByIdBitacoraSubTareaIdInstTarea(Long IdBitacoraSubTarea, Long idInstTarea);
	
}
