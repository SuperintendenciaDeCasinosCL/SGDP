package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.ComplejidadDao;
import cl.gob.scj.sgdp.dto.ComplejidadDTO;
import cl.gob.scj.sgdp.model.Complejidad;
import cl.gob.scj.sgdp.service.ComplejidadService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ComplejidadServiceImpl implements ComplejidadService {
	
	private ComplejidadDao complejidadDao;

	@Autowired
	public ComplejidadServiceImpl(ComplejidadDao complejidadDao) {
		super();
		this.complejidadDao = complejidadDao;
	}
	
	@Override
	public List<ComplejidadDTO> getComplejidades() {
		List<ComplejidadDTO> complejidadesDTO = new ArrayList<ComplejidadDTO>();		
		List<Complejidad> todasLasComplejidades = complejidadDao.getAll(Complejidad.class);
		for (Complejidad complejidad : todasLasComplejidades) {
			ComplejidadDTO complejidadDTO = new ComplejidadDTO();
			BeanUtils.copyProperties(complejidad, complejidadDTO);
			complejidadesDTO.add(complejidadDTO);
		}
		return complejidadesDTO;
	}

}
