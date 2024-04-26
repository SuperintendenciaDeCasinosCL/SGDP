package cl.gob.scj.sgdp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cl.gob.scj.sgdp.dao.ComplejidadExpedienteDao;
import cl.gob.scj.sgdp.dto.ComplejidadExpedienteDTO;
import cl.gob.scj.sgdp.model.ComplejidadExpediente;

@Repository
public class ComplejidadExpedienteDaoImpl extends GenericDaoImpl<ComplejidadExpediente> implements ComplejidadExpedienteDao{
	
	@Override
	public ComplejidadExpedienteDTO getLastByNombreExpediente(String nombreExpediente,
			ComplejidadExpedienteDTO complejidadExpedienteDTO) {
		Query query = getSession().getNamedQuery("ComplejidadExpediente.findLastByNombreExpediente");
		query.setString("nombreExpediente", nombreExpediente);
		query.setMaxResults(1);
		ComplejidadExpediente complejidad = (ComplejidadExpediente) query.uniqueResult();
        if (complejidad != null) {
        	complejidadExpedienteDTO.setId(complejidad.getIdComplejidadExpediente());         
            if(complejidad.getInstanciaDeProceso() != null){
            	complejidadExpedienteDTO.setIdInstanciaDeProceso(complejidad.getInstanciaDeProceso().getIdInstanciaDeProceso());    
            }                    
            complejidadExpedienteDTO.setComplejidad(complejidad.getComplejidad());
            complejidadExpedienteDTO.setMotivoComplejidad(complejidad.getMotivoComplejidad());
        }
		return complejidadExpedienteDTO;
	}
	
	@Override
	public ComplejidadExpedienteDTO getAllByIdInstanciaDeProceso(Long idInstanciaDeProceso,
			ComplejidadExpedienteDTO complejidadDTO) {
		Query query = getSession().getNamedQuery("ComplejidadExpediente.findLastByIdInstanciaProceso");
		query.setLong("idInstanciaDeProceso", idInstanciaDeProceso);
		query.setMaxResults(1);
		
		ComplejidadExpediente complejidad = (ComplejidadExpediente) query.uniqueResult();

		complejidadDTO.setId(complejidad.getIdComplejidadExpediente());
		complejidadDTO.setIdInstanciaDeProceso(complejidad.getInstanciaDeProceso().getIdInstanciaDeProceso());
		complejidadDTO.setComplejidad(complejidad.getComplejidad());
		complejidadDTO.setMotivoComplejidad(complejidad.getMotivoComplejidad());
		
		return complejidadDTO;
	}
	
	@Override
	public List<ComplejidadExpedienteDTO> getAllByNombreExpediente(String nombreExpediente) {
		Query query = getSession().getNamedQuery("ComplejidadExpediente.findAllByNombreExpediente");
		query.setString("nombreExpediente", nombreExpediente);
		List<ComplejidadExpediente> list = (List<ComplejidadExpediente>) query.list();
		List<ComplejidadExpedienteDTO> listResult = new ArrayList<>();
		
		for(ComplejidadExpediente complejidad : list) {
			ComplejidadExpedienteDTO complejidadDTO = new ComplejidadExpedienteDTO();
			complejidadDTO.setId(complejidad.getIdComplejidadExpediente());
			complejidadDTO.setIdInstanciaDeProceso(complejidad.getInstanciaDeProceso().getIdInstanciaDeProceso());
			complejidadDTO.setComplejidad(complejidad.getComplejidad());
			complejidadDTO.setMotivoComplejidad(complejidad.getMotivoComplejidad());
			listResult.add(complejidadDTO);
		}
		
		
		return listResult;
	}
	
	
}
