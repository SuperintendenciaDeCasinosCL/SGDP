package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.UnidadOperativaDTO;

@Service
public interface UnidadOperativaService {
	
	List<UnidadOperativaDTO> getTodasUidadesOperativas();
	UnidadOperativaDTO creaUnidadOperativa(UnidadOperativaDTO unidadOperativaDTO);
	UnidadOperativaDTO actualizaUnidadOperativa(UnidadOperativaDTO unidadOperativaDTO); 
	UnidadOperativaDTO getUnidadOperativPorId ( long idUnidadOperativa);
}
