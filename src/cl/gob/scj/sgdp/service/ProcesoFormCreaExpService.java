package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.ProcesoFormCreaExpDTO;

@Service
public interface ProcesoFormCreaExpService {

	List<ProcesoFormCreaExpDTO> getTodosProcesoFormCreaExp();
	
	void eliminaProcesoDTOQueEstanEnProcesoFormCreaExpDTO(List<ProcesoDTO> todosLosProc, List<ProcesoFormCreaExpDTO> todosLosProcFormCreaExp);
	
	void guardarAsignacionProcCreaExp(List<ProcesoFormCreaExpDTO> listaProcesosFormCreaExpDTO, Usuario usuario);
	
}
