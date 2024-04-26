package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.ResponsabilidadDTO;

@Service
public interface ResponsabilidadTareaService {
	
	public List<ResponsabilidadDTO> getAllByProcessId(Long processId);
	

}
