package cl.gob.scj.sgdp.service;


import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.CargaProcesoDataInicialDTO;
import cl.gob.scj.sgdp.dto.MacroProcesoDTO;
import cl.gob.scj.sgdp.dto.PerspectivaDTO;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.SuperProcesoDTO;
import cl.gob.scj.sgdp.dto.UnidadDTO;

@Service
public interface CargaDeProcesosService {
	
	List<PerspectivaDTO> getTodasLasPerspectivas(List<PerspectivaDTO> perspectivas);
	List<MacroProcesoDTO> getTodosLosMacroProcesos(Long idPerspectiva, List<MacroProcesoDTO> macroProcesos);
	List<SuperProcesoDTO> getTodosLosSuperProcesos(Long idMacroProceso, List<SuperProcesoDTO> superProcesos);
	List<ProcesoDTO> getTodosLosProcesosBySuperProceso(Long idSuperProceso, List<ProcesoDTO> procesos);
	
	List<UnidadDTO> getUnidades(List<UnidadDTO> unidad);
	
	CargaProcesoDataInicialDTO getDataInicial(CargaProcesoDataInicialDTO dataInicial);
	
	ProcesoDTO guardarProceso(ProcesoDTO proceso);
	
	
}