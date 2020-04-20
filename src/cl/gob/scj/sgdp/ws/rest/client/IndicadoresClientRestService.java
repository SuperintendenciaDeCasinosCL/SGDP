package cl.gob.scj.sgdp.ws.rest.client;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.IndicadorDTO;
import cl.gob.scj.sgdp.dto.RespuestaBuscarSubprocesoPorIndicador;
import cl.gob.scj.sgdp.dto.RespuestaIndicadorDTO;

@Service
public interface IndicadoresClientRestService {

	public RespuestaIndicadorDTO buscaTodosIndicadoresVigentes();
	
	public RespuestaBuscarSubprocesoPorIndicador buscarTodosSubprocesoPorIdIndicadorRest(IndicadorDTO indicadorDTO);
	
}
