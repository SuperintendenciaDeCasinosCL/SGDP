package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.dto.rest.TiposDocumetosArchivoNacionalDTO;

public interface ArchivoNacionalRestDao {

	List<TiposDocumetosArchivoNacionalDTO> buscarTiposDocumetosArchivoNacionalDao (String nombreExpediente);
	
}
