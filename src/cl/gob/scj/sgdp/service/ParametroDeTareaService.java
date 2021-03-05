package cl.gob.scj.sgdp.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ParametroDeTareaDTO;
import cl.gob.scj.sgdp.dto.ParametroFormularioDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;

@Service
public interface ParametroDeTareaService {
	
	void cargarParametrosDeTareaDTO(List<ParametroFormularioDTO> listParametroFormularioDTO, long idInstanciaDeTarea);
	
	Map<String, List<ParametroDeTareaDTO>> getMapParametrosDeTareaDTOTituloPorIdInstanciaDeTarea(long idInstanciaDeTarea);
	
	List<ParametroDeTareaDTO> getParametrosDeTareaPorIdInstanciaDeTarea(long idInstanciaDeTarea);
	
	List<ParametroDeTareaDTO> getParametrosDeTareaDTOPorIdTarea(long idTarea);
	
	void guardaValorParametroDeTarea(List<ParametroFormularioDTO> listParametroFormularioDTO, InstanciaDeTarea instanciaDeTarea) throws SgdpException;
	
	void guardaHistoricoValorParametroDeTarea(List<ParametroFormularioDTO> listParametroFormularioDTO, HistoricoDeInstDeTarea historicoDeInstDeTarea, InstanciaDeTarea instanciaDeTarea, Usuario usuario) throws SgdpException;

}
