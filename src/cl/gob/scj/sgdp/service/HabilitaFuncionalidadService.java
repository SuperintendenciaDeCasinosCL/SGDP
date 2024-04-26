package cl.gob.scj.sgdp.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.HabilitaFuncionalidadDTO;

@Service
public interface HabilitaFuncionalidadService {
	
	HabilitaFuncionalidadDTO getHabilitaFuncionalidadDTOById(long idHabilitaFuncionalidad);
	
	List<HabilitaFuncionalidadDTO> getAllHabilitaFuncionalidadDTO();
	
	Map<Long, Boolean> getAllHabilitaFuncionalidadMap();

}
