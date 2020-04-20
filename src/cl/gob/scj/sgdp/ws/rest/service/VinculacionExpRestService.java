package cl.gob.scj.sgdp.ws.rest.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.rest.VinculacionExpedienteRestDTO;

@Service
public interface VinculacionExpRestService {

	VinculacionExpedienteRestDTO getVinculacionExpedientRestDTO(String idExpediente);
	
	void vincularExp(VinculacionExpedienteRestDTO vinculacionExpedienteRestDTO) throws Exception;	
	
}
