package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.ConfidencialidadDocumentoRol;

public interface ConfidencialidadDocumentoRolDao {
	
	public List<ConfidencialidadDocumentoRol> getRolesAsignados(String id); 
	
	public boolean save(ConfidencialidadDocumentoRol cdr);

	void eliminar(String id);

}
