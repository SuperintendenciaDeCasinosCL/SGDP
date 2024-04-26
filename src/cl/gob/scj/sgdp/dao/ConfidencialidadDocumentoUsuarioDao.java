package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.ConfidencialidadDocumentoUsuario;

public interface ConfidencialidadDocumentoUsuarioDao {
	
	public List<ConfidencialidadDocumentoUsuario> getUsuariosAsignados(String id); 

	public boolean save(ConfidencialidadDocumentoUsuario cdu);

	public void eliminar(String id);
}
