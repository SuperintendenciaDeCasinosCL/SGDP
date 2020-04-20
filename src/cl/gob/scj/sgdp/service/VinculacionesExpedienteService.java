package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.VinculacionExpedienteDTO;
import cl.gob.scj.sgdp.dto.rest.VinculacionExpedienteRestDTO;

@Service
public interface VinculacionesExpedienteService {
	
	VinculacionExpedienteDTO getVinculacionExpedienteDTO(String idExpediente);
	
	void vincularExp(Usuario usuario, VinculacionExpedienteDTO vinculacionExpedienteDTO) throws Exception;
	
	void desVincularExp(Usuario usuario, VinculacionExpedienteDTO vinculacionExpedienteDTO) throws Exception;
	
}