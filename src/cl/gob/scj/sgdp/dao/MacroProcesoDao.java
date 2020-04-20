package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.MacroProceso;

public interface MacroProcesoDao {

	List<MacroProceso> getTodosLosMacroProcesos();
	
	List<MacroProceso> getMacroProcesosPorIdUnidad(long idUnidad);
	
}
