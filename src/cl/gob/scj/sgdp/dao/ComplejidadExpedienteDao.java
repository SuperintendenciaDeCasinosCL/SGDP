package cl.gob.scj.sgdp.dao;

import java.util.List;
import cl.gob.scj.sgdp.dto.ComplejidadExpedienteDTO;
import cl.gob.scj.sgdp.model.ComplejidadExpediente;

public interface ComplejidadExpedienteDao extends GenericDao<ComplejidadExpediente> {
	
	ComplejidadExpedienteDTO getAllByIdInstanciaDeProceso(Long idInstanciaDeProceso, ComplejidadExpedienteDTO complejidadDTO);
		
	List<ComplejidadExpedienteDTO> getAllByNombreExpediente(String nombreExpediente);
	
	ComplejidadExpedienteDTO getLastByNombreExpediente(String nombreExpediente, ComplejidadExpedienteDTO complejidadDTO);

}
