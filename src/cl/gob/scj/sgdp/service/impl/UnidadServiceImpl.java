package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.UnidadDao;
import cl.gob.scj.sgdp.dao.UnidadOperativaDao;
import cl.gob.scj.sgdp.dto.UnidadDTO;
import cl.gob.scj.sgdp.model.Unidad;
import cl.gob.scj.sgdp.model.UnidadOperativa;
import cl.gob.scj.sgdp.service.UnidadService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UnidadServiceImpl implements UnidadService {
	
	@Autowired
	private UnidadDao unidadDao;
	
	@Autowired
	private UnidadOperativaDao unidadOperativaDao;

	@Override
	public List<UnidadDTO> getTodasLasUnidadesDTO() {
		List<Unidad> unidades = unidadDao.getTodasLasUnidades();
		List<UnidadDTO> unidadesDTO = new ArrayList<UnidadDTO>();
		for (Unidad unidad : unidades) {
			UnidadDTO unidadDTO = new UnidadDTO();
			BeanUtils.copyProperties(unidad, unidadDTO);
			if (unidad.getUnidadOperativa()!=null) {
				unidadDTO.setIdUnidadOperativa(unidad.getUnidadOperativa().getIdUnidadOperativa());
				unidadDTO.setNombreUnidadOperativa(unidad.getUnidadOperativa().getANombreCompletoUnidadOperativa());
			}
			unidadesDTO.add(unidadDTO);
		}
		return unidadesDTO;
	}
	
	

	@Override
	public UnidadDTO creaUnidad(UnidadDTO unidadDTO) {
		UnidadOperativa unidadOperativa = unidadOperativaDao.find(unidadDTO.getIdUnidadOperativa());
		Unidad unidad = new Unidad();
		unidad.setCodigoUnidad(unidadDTO.getCodigoUnidad());
		unidad.setNombreCompletoUnidad(unidadDTO.getNombreCompletoUnidad());
		unidad.setUnidadOperativa(unidadOperativa);
		unidadDao.insert(unidad);
		unidadDTO.setEstado("OK");
		return unidadDTO;
	}



	@Override
	public UnidadDTO getUinidadPorId(long idUnidad) {
		Unidad unidad = unidadDao.getUnidadPorId(idUnidad);
		UnidadDTO unidadDTO = new UnidadDTO();
		unidadDTO.setIdUnidad(unidad.getIdUnidad());
		unidadDTO.setCodigoUnidad(unidad.getCodigoUnidad());
		unidadDTO.setNombreCompletoUnidad(unidad.getNombreCompletoUnidad());
		unidadDTO.setIdUnidadOperativa(unidad.getUnidadOperativa().getIdUnidadOperativa());
		unidadDTO.setNombreUnidadOperativa(unidad.getUnidadOperativa().getANombreCompletoUnidadOperativa());
		return unidadDTO;
	}



	@Override
	public UnidadDTO actualizaUnidad(UnidadDTO unidadDTO) {
		
		UnidadOperativa unidadOperativa = unidadOperativaDao.find(unidadDTO.getIdUnidadOperativa());
		Unidad unidad = unidadDao.getUnidadPorId(unidadDTO.getIdUnidad());
		unidad.setCodigoUnidad(unidadDTO.getCodigoUnidad());
		unidad.setNombreCompletoUnidad(unidadDTO.getNombreCompletoUnidad());
		unidad.setUnidadOperativa(unidadOperativa);
		unidadDTO.setEstado("OK");
		return unidadDTO;
	}



	@Override
	public List<UnidadDTO> getUnidadesPorIdUnidadOperativa(long idUnidadOprativa) {
		
		List<Unidad> unidades = unidadDao.getUnidadesPorIdUnidadOperativa(idUnidadOprativa);
		List<UnidadDTO> unidadesDTO = new ArrayList<UnidadDTO>();
		for (Unidad unidad : unidades) {
			UnidadDTO unidadDTO = new UnidadDTO();
			//BeanUtils.copyProperties(unidad, unidadDTO);
			unidadDTO.setIdUnidad(unidad.getIdUnidad());
			unidadDTO.setCodigoUnidad(unidad.getCodigoUnidad());
			unidadDTO.setNombreCompletoUnidad(unidad.getNombreCompletoUnidad());
			unidadDTO.setIdUnidadOperativa(unidad.getUnidadOperativa().getIdUnidadOperativa());
			unidadDTO.setNombreUnidadOperativa(unidad.getUnidadOperativa().getANombreCompletoUnidadOperativa());
			unidadesDTO.add(unidadDTO);
		}
		return unidadesDTO;
	}

}
