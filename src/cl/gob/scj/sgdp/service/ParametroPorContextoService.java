package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.KeyParametroPorContextoDTO;
import cl.gob.scj.sgdp.dto.ParametroPorContextoDTO;

@Service
public interface ParametroPorContextoService {
	
	public ParametroPorContextoDTO getParamPorContexto(KeyParametroPorContextoDTO keyParametroPorContextoDTO);
	
	public List<ParametroPorContextoDTO> getParametrosPorContextoPorNombreParam(String nombreParametroPorContexto);

}
