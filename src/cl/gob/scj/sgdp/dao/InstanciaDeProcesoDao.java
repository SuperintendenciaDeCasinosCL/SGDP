package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.FechaEstadoInstanciaProcesoDTO;
import cl.gob.scj.sgdp.dto.InfoProcesoExternoDTO;
import cl.gob.scj.sgdp.dto.rest.ConsultaEstadoProceso;
import cl.gob.scj.sgdp.dto.rest.MensajeJson;
import cl.gob.scj.sgdp.dto.rest.RespuestaConsultaAvanzadaEstadoProceso;
import cl.gob.scj.sgdp.dto.rest.RespuestaConsultaBasicaEstadoProceso;
import cl.gob.scj.sgdp.model.EtapaDeInstanciaDeProceso;
import cl.gob.scj.sgdp.model.HistorialProceso;
import cl.gob.scj.sgdp.model.HistoricoSeguimientoIntanciaProceso;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.SeguimientoIntanciaProceso;

public interface InstanciaDeProcesoDao {
	
	List<InstanciaDeProceso> getTodasLasInstanciasDeProcesos();

	void insertInstanciaDeProceso(InstanciaDeProceso instanciaDeProceso, Usuario usuario);
	
	String getNombreExpediente();
	
	List<HistorialProceso> getHistorialDelProceso(String idExpediente);
	
	List<EtapaDeInstanciaDeProceso> getEtapasDeInstanciaDeProcesoPorIdExpediente(String idExpediente);
	
	InstanciaDeProceso getInstanciaDeProcesoPorIdExpediente(String idExpediente);
	
	List<RespuestaConsultaAvanzadaEstadoProceso> ConsultaAvanzadaEstadoProceso(ConsultaEstadoProceso consultaEstadoProceso);
	
	List<RespuestaConsultaBasicaEstadoProceso> ConsultaBasicaEstadoProceso(ConsultaEstadoProceso consultaEstadoProceso);
	
	InstanciaDeProceso getInstanciaDeProcesoPorIdInstanciaDeProceso(long idInstanciaDeProceso);
	
	FechaEstadoInstanciaProcesoDTO getFechaEstadoInstanciaDeProcesoPorIdExpediente(String idExpediente);
	
	InstanciaDeProceso getInstanciaDeProcesoPorNombreExpediente(String nombreExpediente);

	Object getInstanciaDeProcesoPorNombreExpedienteAPI(String nombreExpediente);
	
}
