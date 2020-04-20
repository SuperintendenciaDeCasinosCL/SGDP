package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ExpedienteDTO;
import cl.gob.scj.sgdp.dto.MacroProcesoDTO;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;

@Service
public interface CrearExpedienteService {
	
	String crearExpediente(ExpedienteDTO expedienteDTO, Usuario usuario) throws SgdpException;
	
	List<ProcesoDTO> getProcesosPorIdMacroProceso(long idMacroProceso, List<ProcesoDTO> procesosDTO);
	
	String getTareaPorIdSubProceso (long idSubProceso);	
	
	List<MacroProcesoDTO> getMacroProcesosDTO(Usuario usuario);
	
}
