package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ProcesoDTO;

@Service
public interface ProcesoService {

	List<ProcesoDTO> buscarTodosProcesoVigenteOrderPorCod(boolean vigente);
	
	List<ProcesoDTO> getTodosProcesos();
	
	List<ProcesoDTO> getBuscarTodosProcesosPorVigencia(boolean vigente);
	
	List<ProcesoDTO> getProcesosPorVigencia(Usuario usuario, boolean vigente);
}
