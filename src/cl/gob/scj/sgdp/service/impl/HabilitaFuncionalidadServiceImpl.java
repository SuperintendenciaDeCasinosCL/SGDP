package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.HabilitaFuncionalidadDao;
import cl.gob.scj.sgdp.dto.HabilitaFuncionalidadDTO;
import cl.gob.scj.sgdp.model.HabilitaFuncionalidad;
import cl.gob.scj.sgdp.service.HabilitaFuncionalidadService;

@Service
@Transactional
public class HabilitaFuncionalidadServiceImpl implements HabilitaFuncionalidadService {
	
	@Autowired
	private HabilitaFuncionalidadDao habilitaFuncionalidadDao;

	@Override
	public HabilitaFuncionalidadDTO getHabilitaFuncionalidadDTOById(long idHabilitaFuncionalidad) {
		HabilitaFuncionalidadDTO habilitaFuncionalidadDTO = new HabilitaFuncionalidadDTO();
		HabilitaFuncionalidad habilitaFuncionalidad = habilitaFuncionalidadDao.find(idHabilitaFuncionalidad);
		BeanUtils.copyProperties(habilitaFuncionalidad, habilitaFuncionalidadDTO);
		return habilitaFuncionalidadDTO;
	}

	@Override
	public List<HabilitaFuncionalidadDTO> getAllHabilitaFuncionalidadDTO() {
		List<HabilitaFuncionalidadDTO> listHabilitaFuncionalidadDTO = new ArrayList<HabilitaFuncionalidadDTO>();
		List<HabilitaFuncionalidad> allHabilitaFuncionalidad = habilitaFuncionalidadDao.getAll(HabilitaFuncionalidad.class);
		for (HabilitaFuncionalidad habilitaFuncionalidad : allHabilitaFuncionalidad) {
			HabilitaFuncionalidadDTO habilitaFuncionalidadDTO = new HabilitaFuncionalidadDTO();
			BeanUtils.copyProperties(habilitaFuncionalidad, habilitaFuncionalidadDTO);
			listHabilitaFuncionalidadDTO.add(habilitaFuncionalidadDTO);
		}
		return listHabilitaFuncionalidadDTO;
	}

	@Override
	public Map<Long, Boolean> getAllHabilitaFuncionalidadMap() {
		Map<Long, Boolean> listHabilitaFuncionalidadMap = new HashMap<>();
		List<HabilitaFuncionalidadDTO> listHabilitaFuncionalidadDTO = getAllHabilitaFuncionalidadDTO();
		for (HabilitaFuncionalidadDTO habilitaFuncionalidadDTO : listHabilitaFuncionalidadDTO) {
			listHabilitaFuncionalidadMap.put(habilitaFuncionalidadDTO.getIdHabilitaFuncionalidad(), habilitaFuncionalidadDTO.isHabilitada());
		}
		return listHabilitaFuncionalidadMap;
	}
	
}
