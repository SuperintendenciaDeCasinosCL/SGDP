package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.PlantillaDeDocumentoDTO;


@Service
public interface PlantillaDeDocumentoService {
	
	List<PlantillaDeDocumentoDTO> getAll();
	
	
}
