package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ComplejidadExpedienteDTO;

@Service
public interface ComplejidadExpedienteService {
	
	ComplejidadExpedienteDTO getLastByNombreExpediente(String nombreExpediente);
	ComplejidadExpedienteDTO guardar(Usuario usuario, ComplejidadExpedienteDTO complejidadDTO);
	ComplejidadExpedienteDTO guardarPorNombre(Usuario usuario, ComplejidadExpedienteDTO complejidadDTO);
	
}
