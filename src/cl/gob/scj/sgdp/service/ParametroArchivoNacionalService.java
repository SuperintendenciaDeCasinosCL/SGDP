package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.ConfiguracionArchivoNacionalDTO;

@Service
public interface ParametroArchivoNacionalService {

	ConfiguracionArchivoNacionalDTO getConfiguracionLoginArchivoNacional();

	ConfiguracionArchivoNacionalDTO getConfiguracionCodigoInstitucion();


	
}
