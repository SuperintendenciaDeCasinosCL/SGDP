package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.ResponsabilidadDao;
import cl.gob.scj.sgdp.dto.ResponsabilidadDTO;
import cl.gob.scj.sgdp.model.Responsabilidad;
import cl.gob.scj.sgdp.model.ResponsabilidadTarea;
import cl.gob.scj.sgdp.service.ResponsabilidadService;


@Service
@Transactional(rollbackFor = Throwable.class)
public class ResponsabilidadServiceImpl implements ResponsabilidadService {

	@Autowired
	private ResponsabilidadDao responsabilidadDao;
	
	@Override
	public List<ResponsabilidadDTO> getAllByProcessId(Long processId) {
		List<ResponsabilidadDTO> reps = new ArrayList<ResponsabilidadDTO>();
		for (Responsabilidad r : responsabilidadDao.getAllByProcessId(processId)) {
			reps.add(new ResponsabilidadDTO(r.getIdResponsabilidad(), r.getNombreResponsabilidad(), processId));
		}
		return reps;
	}

	

}
