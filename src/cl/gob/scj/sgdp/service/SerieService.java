package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.SerieDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;

@Service
public interface SerieService {

	List<SerieDTO> getSeries() throws ArchivoNacionalException;

	
}
