package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.SuperProceso;

public interface SuperProcesoDao {

	List<SuperProceso> getTodosLosSuperProcesosByIDMacroProceso(Long idMacroProceso);
	
}
