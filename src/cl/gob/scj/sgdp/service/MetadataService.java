package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.MetadataDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;

@Service
public interface MetadataService {

	List<MetadataDTO> getMetadataByIdExpediente(String idExpediente) throws ArchivoNacionalException;



}
