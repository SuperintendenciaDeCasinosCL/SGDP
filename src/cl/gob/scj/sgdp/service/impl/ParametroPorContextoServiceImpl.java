package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dao.ParametroPorContextoDao;
import cl.gob.scj.sgdp.dto.KeyParametroPorContextoDTO;
import cl.gob.scj.sgdp.dto.ParametroPorContextoDTO;
import cl.gob.scj.sgdp.model.ParametroPorContexto;
import cl.gob.scj.sgdp.service.ParametroPorContextoService;

@Service
public class ParametroPorContextoServiceImpl implements
		ParametroPorContextoService {
	
	private static final Logger log = Logger.getLogger(ParametroPorContextoServiceImpl.class);	

	//private List<ParametroPorContexto> parametrosPorContexto;
	
	//private Map<KeyParametroPorContextoDTO, ParametroPorContextoDTO> parametrosPorContextoMap = new HashMap<KeyParametroPorContextoDTO, ParametroPorContextoDTO>();
	
	@Autowired
	private ParametroPorContextoDao parametroPorContextoDao;
	
	/*@PostConstruct
	public void init() throws Exception {
		try {
			parametrosPorContexto = parametroPorContextoDao.getTodosLosParametrosPorContexto();						
			for (ParametroPorContexto parametroPorContexto : parametrosPorContexto) {
				if (log.isInfoEnabled()) {
					log.debug(parametroPorContexto.toString());
				}
				ParametroPorContextoDTO parametroPorContextoDTO = 
						new ParametroPorContextoDTO(parametroPorContexto.getIdParametroPorContexto(),
								parametroPorContexto.getNombreParametro(),
								parametroPorContexto.getValorContexto(),
								parametroPorContexto.getValorParametroChar(),
								parametroPorContexto.getValorParametroNumerico());
				KeyParametroPorContextoDTO keyParametroPorContextoDTO = new KeyParametroPorContextoDTO(parametroPorContexto.getNombreParametro(), parametroPorContexto.getValorContexto());
				parametrosPorContextoMap.put(keyParametroPorContextoDTO, parametroPorContextoDTO);
			}
			if (log.isInfoEnabled()) {
				for (Map.Entry<KeyParametroPorContextoDTO, ParametroPorContextoDTO> entry : parametrosPorContextoMap.entrySet()){
				    log.debug(entry.getKey().toString());
				    log.debug(entry.getValue().toString());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}	
	}*/
	
	@Override
	public ParametroPorContextoDTO getParamPorContexto(KeyParametroPorContextoDTO keyParametroPorContextoDTO) {
		
		log.debug("Inicio getParamPorContexto");
		log.debug(keyParametroPorContextoDTO.toString());
		
		/*if (log.isInfoEnabled()) {
			for (Map.Entry<KeyParametroPorContextoDTO, ParametroPorContextoDTO> entry : parametrosPorContextoMap.entrySet()){
			    log.debug(entry.getKey().toString());
			    log.debug(entry.getValue().toString());
			}
		}
		return parametrosPorContextoMap.get(keyParametroPorContextoDTO);*/
		
		ParametroPorContexto parametroPorContexto = parametroPorContextoDao.
				getParametroPorContextoPorNombreParamValorContexto(keyParametroPorContextoDTO.getNombreParametro(), keyParametroPorContextoDTO.getValorContexto());
		
		ParametroPorContextoDTO parametroPorContextoDTO = new ParametroPorContextoDTO();
		
		BeanUtils.copyProperties(parametroPorContexto, parametroPorContextoDTO);
		
		return parametroPorContextoDTO;
	}
	
	@Override
	public List<ParametroPorContextoDTO> getParametrosPorContextoPorNombreParam(String nombreParametroPorContexto) {
		
		log.debug("Inicio getParametrosPorContextoPorNombreParam");
		log.debug("nombreParametroPorContexto: " + nombreParametroPorContexto);
		List<ParametroPorContextoDTO> parametrosPorContextoDTO = new ArrayList<ParametroPorContextoDTO>();
		
		/*for (Map.Entry<KeyParametroPorContextoDTO, ParametroPorContextoDTO> entry : parametrosPorContextoMap.entrySet()){
		    log.debug(entry.getKey().toString());
		    log.debug(entry.getValue().toString());
		    if (nombreParametroPorcontexto.equals(entry.getKey().getNombreParametro())) {
		    	parametrosPorContextoDTO.add(entry.getValue());
		    }
		}*/
		
		List<ParametroPorContexto> parametrosPorContexto = parametroPorContextoDao.getParametrosPorContextoPorNombreParam(nombreParametroPorContexto);
		
		for (ParametroPorContexto parametroPorContexto: parametrosPorContexto) {
			ParametroPorContextoDTO parametroPorContextoDTO = new ParametroPorContextoDTO();
			BeanUtils.copyProperties(parametroPorContexto, parametroPorContextoDTO);
			parametrosPorContextoDTO.add(parametroPorContextoDTO);
		}
		
		return parametrosPorContextoDTO;
		
	}
	
	

}
