package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.UnidadDTO;

@Service
public interface UnidadService {

	List<UnidadDTO> getTodasLasUnidadesDTO();
	UnidadDTO creaUnidad(UnidadDTO unidadDTO);
	UnidadDTO getUinidadPorId(long idUnidad);
	UnidadDTO actualizaUnidad(UnidadDTO unidadDTO);
	List<UnidadDTO> getUnidadesPorIdUnidadOperativa(long idUnidadOprativa);
}