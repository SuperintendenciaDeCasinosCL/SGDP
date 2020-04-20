package cl.gob.scj.sgdp.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.ParametroDao;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.model.Parametro;
import cl.gob.scj.sgdp.service.ParametroService;

@Service
public class ParametroServiceImpl implements ParametroService {
	
	private static final Logger log = Logger.getLogger(ParametroServiceImpl.class);	
	
	private List<Parametro> parametros;
	
	private Map<Long, ParametroDTO> parametrosMap = new HashMap<Long, ParametroDTO>();

	@Autowired
	private ParametroDao parametroDao;
	
	@PostConstruct
	public void init() throws Exception {
		try {
			parametros = parametroDao.getTodosLosParametros();
			for (Parametro parametro : parametros) {
				ParametroDTO parametroDTO = new ParametroDTO(parametro.getIdParametro(), parametro.getNombreParametro(), parametro.getValorParametroChar(), parametro.getValorParametroNumerico());
				log.debug(parametroDTO.toString());
				parametrosMap.put(parametro.getIdParametro(), parametroDTO);
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw e;
		}		
	}
	
	@Override
	public ParametroDTO getParametroPorID(long idParametro) { 
		return parametrosMap.get(idParametro);
	}
	
	@Override
	public List<ParametroDTO> getParametrosPorNombre(String nombreParametro) {		
		List<ParametroDTO> listaDeParametrosDTO = new ArrayList<ParametroDTO>();
		for (Map.Entry<Long, ParametroDTO> entry : parametrosMap.entrySet()){
			if (entry.getValue().getNombreParametro().equals(nombreParametro)) {
				listaDeParametrosDTO.add(entry.getValue());
			}
		}		
		return listaDeParametrosDTO;
	}
	
	@Override
	public ParametroDTO getParametroPorNombreParamYValorParam(String nombreParametro, String valorParametro) {	
		for (Map.Entry<Long, ParametroDTO> entry : parametrosMap.entrySet()){
			if (entry.getValue().getNombreParametro().equals(nombreParametro)  && entry.getValue().getValorParametroChar().equals(valorParametro)) {
				return entry.getValue();
			}
		}		
		return null;
	}

	@Override
	public Map<Long, ParametroDTO> getParametrosMap() {
		return parametrosMap;
	}
	
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void insertaParametro(ParametroDTO parametroDTO) {
		Parametro parametro = new Parametro();
		parametro.setNombreParametro(parametroDTO.getNombreParametro());
		parametro.setValorParametroChar(parametroDTO.getValorParametroChar());
		parametro.setValorParametroNumerico(parametroDTO.getValorParametroNumerico());
		parametroDao.insert(parametro);
		parametroDTO.setIdParametro(parametro.getIdParametro());
		parametrosMap.put(parametro.getIdParametro(), parametroDTO);				
	}
	
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void actualizaParametroBD(ParametroDTO parametroDTO) {
		Parametro parametro = parametroDao.find(parametroDTO.getIdParametroAnterior());		
		parametro.setIdParametro(parametroDTO.getIdParametro());
		parametro.setNombreParametro(parametroDTO.getNombreParametro());
		parametro.setValorParametroChar(parametroDTO.getValorParametroChar());
		parametro.setValorParametroNumerico(parametroDTO.getValorParametroNumerico());					
	}
	
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void actualizaParametro(ParametroDTO parametroDTO) {		
		parametrosMap.remove(parametroDTO.getIdParametroAnterior());
		parametrosMap.put(parametroDTO.getIdParametro(), parametroDTO);				
	}
	
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void borraParametro(long idParametro) {
		Parametro parametro = parametroDao.find(idParametro);
		parametroDao.delete(parametro);
		parametrosMap.remove(idParametro);						
	}
	
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void recargaParametros() {
		List<Parametro> parametrosBD = parametroDao.getTodosLosParametros();
		Iterator<Parametro> it = parametrosBD.iterator();
		while (it.hasNext()) {
			Parametro p = it.next();
			if (parametrosMap.containsKey(p.getIdParametro())) {
				parametrosMap.remove(p.getIdParametro());				
			}
			ParametroDTO parametroDTO = new ParametroDTO(p.getIdParametro(), p.getNombreParametro(), p.getValorParametroChar(), p.getValorParametroNumerico());
			parametrosMap.put(p.getIdParametro(), parametroDTO);
		}
		Iterator<Map.Entry<Long, ParametroDTO>> iteradorMapParametros = parametrosMap.entrySet().iterator();
		while (iteradorMapParametros.hasNext()) {
			Map.Entry<Long, ParametroDTO> entry = iteradorMapParametros.next();
			long idParam = entry.getKey();
			if (!paramEstaEnBD(parametrosBD, idParam)) {
				parametrosMap.remove(idParam);
			}
		}
	}
	
	private boolean paramEstaEnBD(List<Parametro> parametrosBD, long idParam) {
		Iterator<Parametro> it = parametrosBD.iterator();
		while (it.hasNext()) {
			Parametro p = it.next();
			if (p.getIdParametro() == idParam) {
				return true;
			}
		}
		return false;
	}
	
}
