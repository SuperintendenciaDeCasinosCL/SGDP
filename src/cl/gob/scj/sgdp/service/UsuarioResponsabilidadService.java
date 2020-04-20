package cl.gob.scj.sgdp.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.UsuarioResponsabilidadDTO;

@Service
public interface UsuarioResponsabilidadService {

	void cargaUsuariosRolesPosiblesPorIdInstanciaDeTarea(long idInstanciaDeTarea, List<String> posiblesUsuarios);
	
	UsuarioResponsabilidadDTO getPrimerUsuarioResponsabilidadDTOPorIdInstanciaDeTarea(long idInstanciaDeTarea);
	
	void cargaUsuariosRolesPosiblesConOrdenPorIdInstanciaDeTarea(long idInstanciaDeTarea,
			List<String> listaPosiblesUsuarios);
	
	List<UsuarioResponsabilidadDTO> getUsuariosResponsabilidadDTODeSegundasTareaPorIdProceso(long idProceso);
	
	void cargaUsuariosFueraOficinaRolesPosiblesPorIdInstanciaDeTarea(long idInstanciaDeTarea, List<String> posiblesUsuariosFueaOficina);
	
}
