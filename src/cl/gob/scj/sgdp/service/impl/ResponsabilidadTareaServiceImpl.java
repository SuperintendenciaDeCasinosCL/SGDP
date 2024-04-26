package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import cl.gob.scj.sgdp.dao.ResponsabilidadTareaDao;
import cl.gob.scj.sgdp.dto.ResponsabilidadDTO;

import cl.gob.scj.sgdp.model.ResponsabilidadTarea;
import cl.gob.scj.sgdp.service.ResponsabilidadTareaService;


@Service
@Transactional(rollbackFor = Throwable.class)
public class ResponsabilidadTareaServiceImpl implements ResponsabilidadTareaService {

	@Autowired
	private ResponsabilidadTareaDao responsabilidadTareaDao;
	
	@Override
	public List<ResponsabilidadDTO> getAllByProcessId(Long processId) {
		List<ResponsabilidadDTO> reps = new ArrayList<ResponsabilidadDTO>();
		Map<Long, Boolean> agregado = new HashMap<Long, Boolean>();
		
		for (ResponsabilidadTarea r : responsabilidadTareaDao.getResponsabilidadesTareasPorIdProceso(processId)) {
			Long id = r.getId().getResponsabilidad().getIdResponsabilidad();
			if (!agregado.containsKey(id)) {
				reps.add(new ResponsabilidadDTO(id, r.getId().getResponsabilidad().getNombreResponsabilidad(), processId));
				agregado.put(id, true);
			}
			
		}
		return reps;
	}

	

}
