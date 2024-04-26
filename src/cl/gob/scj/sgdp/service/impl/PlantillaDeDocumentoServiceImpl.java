package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.PlantillaDeDocumentoDao;
import cl.gob.scj.sgdp.dto.PlantillaDeDocumentoDTO;
import cl.gob.scj.sgdp.model.PlantillaDeDocumento;
import cl.gob.scj.sgdp.service.PlantillaDeDocumentoService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class PlantillaDeDocumentoServiceImpl implements PlantillaDeDocumentoService {
	
	@Autowired
	private PlantillaDeDocumentoDao plantillaDao;

	@Override
	public List<PlantillaDeDocumentoDTO> getAll() {
		List<PlantillaDeDocumento> pbds = plantillaDao.getTodasLasPlantillas();
		List<PlantillaDeDocumentoDTO> pdtos = new ArrayList<PlantillaDeDocumentoDTO>();
		
		for (PlantillaDeDocumento pbd : pbds) {
			PlantillaDeDocumentoDTO pdto = new PlantillaDeDocumentoDTO();
			pdto.setIdPlantilla(pbd.getIdPlantillaDeDocumento());
			pdto.setNombre(pbd.getNombre());
			pdto.setCodigo(pbd.getCodigo());
			pdto.setVigente(pbd.getVigente());
			pdto.setPlantilla(pbd.getPlantilla());
			pdtos.add(pdto);
		}
		
		return pdtos;
	}

	
}
