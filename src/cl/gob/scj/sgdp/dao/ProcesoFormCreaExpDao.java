package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.dto.ProcesoFormCreaExpDTO;
import cl.gob.scj.sgdp.model.ProcesoFormCreaExp;

public interface ProcesoFormCreaExpDao extends GenericDao<ProcesoFormCreaExp> {	
	
	List<ProcesoFormCreaExpDTO> getTodosProcesoFormCreaExp();
	
}