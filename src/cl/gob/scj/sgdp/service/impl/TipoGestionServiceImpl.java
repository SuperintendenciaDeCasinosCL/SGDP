package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.TipoGestionDao;
import cl.gob.scj.sgdp.dto.TipoGestionDTO;
import cl.gob.scj.sgdp.model.Tipo;
import cl.gob.scj.sgdp.service.TipoGestionService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class TipoGestionServiceImpl implements TipoGestionService {
	
	@Autowired
	private TipoGestionDao tipoGestionDao;

	@Override
	public List<TipoGestionDTO> getAll() {
		List<TipoGestionDTO> tiposGestionDTO = new ArrayList<TipoGestionDTO>();
		TipoGestionDTO tipoGestionDTO = null;
		List<Tipo> tiposGestion = this.tipoGestionDao.getAll();
		for (Tipo tipo : tiposGestion) {
			tipoGestionDTO = new TipoGestionDTO(tipo.getIdTipo(), tipo.getNombreTipo());
			tiposGestionDTO.add(tipoGestionDTO);
			
		}
		return tiposGestionDTO;
	}
	

	
}
