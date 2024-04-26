package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.EtapaDeInstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.dto.HistorialProcesoDTO;
import cl.gob.scj.sgdp.dto.InfoProcesoExternoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaDTO;
import cl.gob.scj.sgdp.dto.rest.MensajeJson;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.SeguimientoIntanciaProceso;

@Service
 public interface InstanciaDeProcesoService {
	
	 List<HistorialProcesoDTO> getHistorialDelProceso(String idExpediente);
	
	 List<EtapaDeInstanciaDeProcesoDTO> getEtapasDeInstanciaDeProcesoPorIdExpediente(String idExpediente);
	
	 List<HistorialProcesoDTO> getHistoricoDeInstDeTareaPorIdExpediente(String idExpediente);
	
	 void cargaInstanciaDeProcesoDTOPorIdExpediente(String idExpediente, InstanciaDeProcesoDTO instanciaDeProcesoDTO);

	 long buscaSiTieneSeguimiento(String idUsuario,
			long idInstanciaDeProceso);

	 void guardarSeguimiento(
			InstanciaDeProcesoDTO instanciaDeProcesoDTO/*SeguimientoIntanciaProceso seguimientoIntanciaProceso*/,
			MensajeJson mensajeJson, String idUsuarioQueSigue, Usuario usuario, String nombreTipoNotificacion);

	 void dejarDeSeguimiento(
			InstanciaDeProcesoDTO instanciaDeProcesoDTO/*SeguimientoIntanciaProceso seguimientoIntanciaProceso*/,
			MensajeJson mensajeJson, String idUsuarioQueDejaDeSeguir, Usuario usuario);

	 void buscaInstanciaDeProcesoDTOPorIdExpediente(ResultadoBusquedaDTO resultadoBusquedaDTO);
	
	 InstanciaDeProceso getInstanciaDeProcesoPorNombre(String expediente);
	
	 InfoProcesoExternoDTO getInstanciaDeProcesoPorNombreAPI(String expediente);
	
	 InstanciaDeProcesoDTO getInstanciaDeProcesoPorNombreExpediente(String nombreExpediente);
	 
	 boolean actualizaAsunto(InstanciaDeProcesoDTO instanciaDeProcesoDTO, Usuario usuario);
	 
	 InstanciaDeProcesoDTO getInstanciaDeProcesoDTOPorIdExpediente(String idExpediente);

}
