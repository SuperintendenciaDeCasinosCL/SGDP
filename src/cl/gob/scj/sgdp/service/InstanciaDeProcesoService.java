package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.EnviarArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.EtapaDeInstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.dto.ExpedienteArchNacDTO;
import cl.gob.scj.sgdp.dto.ExtensionDTO;
import cl.gob.scj.sgdp.dto.HistorialProcesoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaDTO;
import cl.gob.scj.sgdp.dto.rest.MensajeJson;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
import cl.gob.scj.sgdp.model.SeguimientoIntanciaProceso;

@Service
public interface InstanciaDeProcesoService {
	
	public List<HistorialProcesoDTO> getHistorialDelProceso(String idExpediente);
	
	public List<EtapaDeInstanciaDeProcesoDTO> getEtapasDeInstanciaDeProcesoPorIdExpediente(String idExpediente);
	
	public List<HistorialProcesoDTO> getHistoricoDeInstDeTareaPorIdExpediente(String idExpediente);
	
	public void cargaInstanciaDeProcesoDTOPorIdExpediente(String idExpediente, InstanciaDeProcesoDTO instanciaDeProcesoDTO);

	public long buscaSiTieneSeguimiento(String idUsuario,
			long idInstanciaDeProceso);

	public void guardarSeguimiento(
			InstanciaDeProcesoDTO instanciaDeProcesoDTO/*SeguimientoIntanciaProceso seguimientoIntanciaProceso*/,
			MensajeJson mensajeJson, String idUsuarioQueSigue, Usuario usuario, String nombreTipoNotificacion);

	public void dejarDeSeguimiento(
			InstanciaDeProcesoDTO instanciaDeProcesoDTO/*SeguimientoIntanciaProceso seguimientoIntanciaProceso*/,
			MensajeJson mensajeJson, String idUsuarioQueDejaDeSeguir, Usuario usuario);

	public void buscaInstanciaDeProcesoDTOPorIdExpediente(ResultadoBusquedaDTO resultadoBusquedaDTO);

	public List<ExpedienteArchNacDTO> getMetadataListaExpediente(EnviarArchivoNacionalDTO enviarDTO, Usuario usuario) throws ArchivoNacionalException;

}
