package cl.gob.scj.sgdp.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.ParametroDTO;

@Service
public interface ParametroService {

	ParametroDTO getParametroPorID(long idParametro);
	
	List<ParametroDTO> getParametrosPorNombre(String nombreParametro);
	
	ParametroDTO getParametroPorNombreParamYValorParam(String nombreParametro, String valorParametro);
	
	Map<Long, ParametroDTO> getParametrosMap();
	
	void insertaParametro(ParametroDTO parametroDTO);
	
	void actualizaParametroBD(ParametroDTO parametroDTO);
	
	void actualizaParametro(ParametroDTO parametroDTO);
	
	void borraParametro(long idParametro);
	
	void recargaParametros();
	
}
