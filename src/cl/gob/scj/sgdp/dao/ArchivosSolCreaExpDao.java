package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.dto.ArchivosSolCreaExpDTO;
import cl.gob.scj.sgdp.model.ArchivosSolCreaExp;

public interface ArchivosSolCreaExpDao extends GenericDao<ArchivosSolCreaExp> {
	
	List<ArchivosSolCreaExp> getArchivosCrearExp(ArchivosSolCreaExpDTO archivosSolCreaExpDTO);

}
