package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.ComplejidadDTO;

@Service
public interface ComplejidadService {

	List<ComplejidadDTO> getComplejidades();
	
}
