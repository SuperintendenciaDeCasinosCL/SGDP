package cl.gob.scj.sgdp.ws.rest.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.rest.TiposDocumetosArchivoNacionalResponse;

@Service
public interface ArchivoNacionalRestService {
	
	TiposDocumetosArchivoNacionalResponse buscarTiposDocumetosArchivoNacional (String nombreExpediente);
}
