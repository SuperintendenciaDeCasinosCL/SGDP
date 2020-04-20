package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.AnulacionDTO;
import cl.gob.scj.sgdp.dto.CierraInstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.ContinuarProcesoDTO;
import cl.gob.scj.sgdp.dto.FinalizaProcesoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.ReabreInstanciaDeSubProcesoDTO;
import cl.gob.scj.sgdp.dto.RetrocedeProcesoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;

@Service
public interface MueveProcesoService {

	List<InstanciaDeTareaDTO> getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(Usuario usuario, long idInstanciaDeTarea, boolean vigente, 
			List<InstanciaDeTareaDTO> instanciasDeTareasDTOContinuanProceso);
	
	String avanzaProceso(ContinuarProcesoDTO continuarProcesoDTO, Usuario usuario) throws SgdpException;
	
	String retrocedeProceso(RetrocedeProcesoDTO retrocedeProcesoDTO, Usuario usuario) throws SgdpException;
	
	void finaliza(Usuario usuario, FinalizaProcesoDTO finalizaProcesoDTO) throws SgdpException;
	
	void anularProceso(Usuario usuario, AnulacionDTO anulacionDto);
	
	void anularProcesoPorIdExpediente(Usuario usuario, AnulacionDTO anulacionDTO);
	
	void cargaInstanciasDeTareasNuevasQueContinuanDeInstanciaDeTarea(Usuario usuario, long idInstanciaDeTarea, boolean vigente
			, List<InstanciaDeTareaDTO> instanciasDeTareasDTOContinuanProceso);
	
	List<InstanciaDeTareaDTO> getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(Usuario usuario, InstanciaDeTareaDTO instanciaDeTareaDTO, 
			List<InstanciaDeTareaDTO> instanciasDeTareasDTOContinuanProceso);
	
	void reabrirExpedientePorIdInstanciaDeTareaIdUsuario(ReabreInstanciaDeSubProcesoDTO reabreInstanciaDeSubProcesoDTO, Usuario usuario) throws SgdpException;
	
	void cierraInstanciaDeTarea(CierraInstanciaDeTareaDTO cierraInstanciaDeTareaDTO, Usuario usuario) throws SgdpException;
	
	String getUsuarioAAsignarIgualAUsuarioDeOrigenListaString(List<String> listaPosiblesUsuarios, String nombreDeUsuarioAsignado);
	
}
