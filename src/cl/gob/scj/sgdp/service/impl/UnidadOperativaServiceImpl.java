package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.UnidadOperativaDao;
import cl.gob.scj.sgdp.dto.UnidadOperativaDTO;
import cl.gob.scj.sgdp.model.UnidadOperativa;
import cl.gob.scj.sgdp.service.UnidadOperativaService;


@Service
@Transactional(rollbackFor = Throwable.class)
public class UnidadOperativaServiceImpl   implements  UnidadOperativaService{

	@Autowired
	private  UnidadOperativaDao unidadOperativaDao;
	
	@Override
	public List<UnidadOperativaDTO> getTodasUidadesOperativas() {
		
		List<UnidadOperativa> unidadesOperativas =  unidadOperativaDao.getTodasUnidadesOperativas();
		List<UnidadOperativaDTO> unidadesOperativasDTO = new ArrayList<UnidadOperativaDTO>();
		for(UnidadOperativa unidadOperativa : unidadesOperativas) {
			UnidadOperativaDTO unidadOperativaDTO = new UnidadOperativaDTO();
			unidadOperativaDTO.setIdUnidadOperativa(unidadOperativa.getIdUnidadOperativa());
			unidadOperativaDTO.setCodigoUnidadOperativa(unidadOperativa.getACodigoUnidadOperativa());
			unidadOperativaDTO.setNombreUnidadOperativa(unidadOperativa.getANombreCompletoUnidadOperativa());
			unidadesOperativasDTO.add(unidadOperativaDTO);
		}
		
		
		return unidadesOperativasDTO;
	}
	
	

	@Override
	public UnidadOperativaDTO creaUnidadOperativa(UnidadOperativaDTO unidadOperativaDTO) {
		
		UnidadOperativa unidadOperativa = new UnidadOperativa();
		unidadOperativa.setACodigoUnidadOperativa(unidadOperativaDTO.getCodigoUnidadOperativa().toUpperCase());
		unidadOperativa.setANombreCompletoUnidadOperativa(unidadOperativaDTO.getNombreUnidadOperativa());
		unidadOperativaDao.insert(unidadOperativa);
		unidadOperativaDTO.setEstado("OK");
		return unidadOperativaDTO;
	}


	
	
	

	@Override
	public UnidadOperativaDTO actualizaUnidadOperativa(UnidadOperativaDTO unidadOperativaDTO) {
		
		UnidadOperativa unidadOperativa = unidadOperativaDao.getUnidadOperativaPorId(unidadOperativaDTO.getIdUnidadOperativa());
		unidadOperativa.setACodigoUnidadOperativa(unidadOperativaDTO.getCodigoUnidadOperativa());
		unidadOperativa.setANombreCompletoUnidadOperativa(unidadOperativaDTO.getNombreUnidadOperativa());
		unidadOperativaDTO.setEstado("OK");
		return unidadOperativaDTO;
	}



	@Override
	public UnidadOperativaDTO getUnidadOperativPorId(long idUnidadOperativa) {
		UnidadOperativa unidadOperativa = unidadOperativaDao.getUnidadOperativaPorId(idUnidadOperativa);
		UnidadOperativaDTO unidadOperativaDTO = new UnidadOperativaDTO();
		unidadOperativaDTO.setIdUnidadOperativa(unidadOperativa.getIdUnidadOperativa());
		unidadOperativaDTO.setCodigoUnidadOperativa(unidadOperativa.getACodigoUnidadOperativa());
		unidadOperativaDTO.setNombreUnidadOperativa(unidadOperativa.getANombreCompletoUnidadOperativa());
		return unidadOperativaDTO;
	}

	
}
