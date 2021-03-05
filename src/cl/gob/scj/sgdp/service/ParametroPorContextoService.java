package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.KeyParametroPorContextoDTO;
import cl.gob.scj.sgdp.dto.ParametroPorContextoDTO;

@Service
public interface ParametroPorContextoService {
	
	ParametroPorContextoDTO getParamPorContexto(KeyParametroPorContextoDTO keyParametroPorContextoDTO);
	
	List<ParametroPorContextoDTO> getParametrosPorContextoPorNombreParam(String nombreParametroPorContexto);
	
	ParametroPorContextoDTO getParametroPorContextoDTOMuestraTodasLasTareaEjecucion(Usuario usuario);

}
