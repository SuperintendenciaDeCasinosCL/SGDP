package cl.gob.scj.sgdp.ws.rest.client.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.control.BandejaDeEntradaControl;
import cl.gob.scj.sgdp.dto.IndicadorDTO;
import cl.gob.scj.sgdp.dto.RespuestaBuscarSubprocesoPorIndicador;
import cl.gob.scj.sgdp.dto.RespuestaIndicadorDTO;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.rest.client.IndicadoresClientRestService;

@Service
public class IndicadoresClientRestServiceImpl implements IndicadoresClientRestService {

	private static final Logger log = Logger.getLogger(BandejaDeEntradaControl.class);

	@Autowired
	private ParametroService parametroService;

	public RespuestaIndicadorDTO buscaTodosIndicadoresVigentes() {

		log.debug("ejecucion servicio buscaTodosIndicadoresVigentes ");

		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
		// String serviceRestURLbuscaTodosIndicadoresVigentes =
		// "http://172.16.10.77:8080/igestion-mantenedor-indicadores/indicadorServicios/listaIndicadores";
		String serviceRestURLbuscaTodosIndicadoresVigentes = parametroService
				.getParametroPorID(Constantes.ID_URL_LISTA_INDICADORES).getValorParametroChar();
		RespuestaIndicadorDTO respuestaIndicadorDTO = restTemplate
				.getForObject(serviceRestURLbuscaTodosIndicadoresVigentes, RespuestaIndicadorDTO.class);

		log.debug(respuestaIndicadorDTO.toString());
		return respuestaIndicadorDTO;
	}

	@Override
	public RespuestaBuscarSubprocesoPorIndicador buscarTodosSubprocesoPorIdIndicadorRest(IndicadorDTO indicadorDTO) {

		log.debug("ejecucion servicio buscarTodosSubprocesoPorIdIndicadorRest ");
		RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();

		// String serviceRestURbuscarSubprocesoAsociadoPorIdIndicador =
		// "http://172.16.10.77:8080/igestion-mantenedor-indicadores/indicadorServicios/buscarSubprocesoAsociadoPorIdIndicador";
		String serviceRestURbuscarSubprocesoAsociadoPorIdIndicador = parametroService
				.getParametroPorID(Constantes.ID_PARAM_URL_BUSCA_SUBPROCESO_ASOCIADO_ID_INDICADOR)
				.getValorParametroChar();
		RespuestaBuscarSubprocesoPorIndicador respuestaBuscarSubprocesoPorIndicador = restTemplate.postForObject(
				serviceRestURbuscarSubprocesoAsociadoPorIdIndicador, indicadorDTO,
				RespuestaBuscarSubprocesoPorIndicador.class);

		return respuestaBuscarSubprocesoPorIndicador;
	}

}
