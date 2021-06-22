package cl.gob.scj.sgdp.dao;

import cl.gob.scj.sgdp.model.ParametroArchivoNacional;

public interface ParametroArchivoNacionalDao {

	ParametroArchivoNacional getParametroArchivoNacionalByCodigo(String codigo);


	void insertarParametroArchivoNacional(ParametroArchivoNacional entity);

}
