package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ConfidencialidadDocumentoDTO;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;

@Service
public interface ConfidencialidadDocumentoService {
	
	ConfidencialidadDocumentoDTO getByIdDocumento(String id);
	
	ConfidencialidadDocumentoDTO guardar(ConfidencialidadDocumentoDTO cd);
	
	ConfidencialidadDocumentoDTO eliminar(String id);
	
	boolean puedeVerPorUsuario(String idArchivoCMS, Usuario usuario);
	
	boolean puedeVerPorRol(String idArchivoCMS, Usuario usuario);
	
	List<ConfidencialidadDocumentoDTO> getByIdTipoDocumento(List<String> ids);
	
	ConfidencialidadDocumentoDTO getByIdTipoDocumento(String id);

	void eliminaConfidenciales(List<DetalleDeArchivoDTO> detallesDeArchivosDTO, Usuario usuario);
	
}
