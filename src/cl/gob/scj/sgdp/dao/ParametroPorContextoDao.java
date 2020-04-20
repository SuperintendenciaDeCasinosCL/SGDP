package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.ParametroPorContexto;

public interface ParametroPorContextoDao {

	List<ParametroPorContexto> getTodosLosParametrosPorContexto();
	
	ParametroPorContexto getParametroPorContextoPorValorParametroChar(String valorParametroChar);
	
	ParametroPorContexto getParametroPorContextoPorNombreParamValorContexto(String nombreParametro, String valorContexto);
	
	List<ParametroPorContexto> getParametrosPorContextoPorNombreParam(String nombreParametro);
	
}
