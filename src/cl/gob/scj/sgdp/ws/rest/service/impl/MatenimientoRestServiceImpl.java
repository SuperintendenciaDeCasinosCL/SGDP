package cl.gob.scj.sgdp.ws.rest.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dao.ProcesoDao;
import cl.gob.scj.sgdp.dao.UnidadDao;
import cl.gob.scj.sgdp.dto.rest.ExpedienteRestActMetaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.Proceso;
import cl.gob.scj.sgdp.model.ReferenciaDeTarea;
import cl.gob.scj.sgdp.model.Responsabilidad;
import cl.gob.scj.sgdp.model.ResponsabilidadTarea;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.model.Unidad;
import cl.gob.scj.sgdp.model.UsuarioNotificacionTarea;
import cl.gob.scj.sgdp.model.UsuarioResponsabilidad;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.GestorMetadataCMSService;
import cl.gob.scj.sgdp.ws.rest.service.MatenimientoRestService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class MatenimientoRestServiceImpl implements MatenimientoRestService {
	
	private static final Logger log = Logger.getLogger(MatenimientoRestServiceImpl.class);
	
	@Autowired
	private GestorMetadataCMSService gestorMetadataCMSService;
	
	@Autowired
	private InstanciaDeProcesoDao instanciaDeProcesoDao;
	
	@Autowired
	private ProcesoDao procesoDao;
	
	@Autowired UnidadDao unidadDao;

	@Override
	public String actualizaMetaDataExpedientes(Usuario usuario, List<ExpedienteRestActMetaDTO> lista) throws SgdpException {
		InstanciaDeProceso instanciaDeProceso;
		for (ExpedienteRestActMetaDTO expedienteRestActMetaDTO : lista) {
			log.info(expedienteRestActMetaDTO.toString());
			instanciaDeProceso = instanciaDeProcesoDao.getInstanciaDeProcesoPorNombreExpediente(expedienteRestActMetaDTO.getNombreExpediente());
			if (instanciaDeProceso == null) {				
				throw new SgdpException("ERROR: Expediente " + expedienteRestActMetaDTO.getNombreExpediente() + " no existe");
			}
			expedienteRestActMetaDTO.setIdExpediente(instanciaDeProceso.getIdExpediente());
			if (expedienteRestActMetaDTO.getAutor()!=null && !expedienteRestActMetaDTO.getAutor().isEmpty()) {
				instanciaDeProceso.setEmisor(expedienteRestActMetaDTO.getAutor());
			}			
			if (expedienteRestActMetaDTO.getMateria()!=null && !expedienteRestActMetaDTO.getMateria().isEmpty()) {
				instanciaDeProceso.setAsunto(expedienteRestActMetaDTO.getMateria());
			}
			try {
				gestorMetadataCMSService.actualizaMetaDataExpediente(usuario, expedienteRestActMetaDTO);
			} catch (Exception e) {
				throw new SgdpException("ERROR: Ocurrio un error al actualizar metadado en gestor documental " + e.getMessage());
			}
		}				
		return "OK";		
	}
	
	public String copiaProcesos(Usuario usuario, List<Integer> idProcesos, int idNuevaUnidad) throws SgdpException {
		Proceso proceso;
		List<Tarea> tareas;		
		TipoDeDocumento tipoDeDocumento;
		List<DocumentoDeSalidaDeTarea> documentosDeSalidaDeTarea;		
		List<ReferenciaDeTarea> referenciasDeTareas;
		List<ResponsabilidadTarea> responsabilidadesTarea;
		Responsabilidad responsabilidad;
		List<UsuarioResponsabilidad> usuariosResponsabilidades;
		Unidad unidad = unidadDao.find(new Integer(idNuevaUnidad));
		List<UsuarioNotificacionTarea> usuariosNotificacionTarea;
		for (Integer idProceso : idProcesos) {
			proceso = procesoDao.getProcesoPorIdProceso(idProceso);
			Proceso procesoNuevo = new Proceso();
			procesoNuevo.setCodigoProceso(proceso.getCodigoProceso());
			procesoNuevo.setDescripcionProceso(proceso.getDescripcionProceso());
			procesoNuevo.setDiasHabilesMaxDuracion(proceso.getDiasHabilesMaxDuracion());
			procesoNuevo.setEsConfidencial(proceso.getEsConfidencial());
			procesoNuevo.setMacroProceso(proceso.getMacroProceso());
			procesoNuevo.setNombreProceso(proceso.getNombreProceso());
			procesoNuevo.setUnidad(unidad);
			procesoNuevo.setVigente(proceso.getVigente());
			tareas = proceso.getTareas();
			//List<Tarea> tareasNuevas = new ArrayList<Tarea>();
			for (Tarea tarea : tareas) {
				Tarea tareaNueva = new Tarea();
				tareaNueva.setAsignaNumDoc(tarea.getAsignaNumDoc());
				tareaNueva.setConformaExpediente(tarea.getConformaExpediente());
				tareaNueva.setDescripcionTarea(tarea.getDescripcionTarea());
				tareaNueva.setDiasHabilesMaxDuracion(tarea.getDiasHabilesMaxDuracion());
				tareaNueva.setDiasReseteo(tarea.getDiasReseteo());
				tareaNueva.setDistribuye(tarea.getDistribuye());
				tareaNueva.setEsperarResp(tarea.getEsperarResp());
				tareaNueva.setEsUltimaTarea(tarea.getEsUltimaTarea());
				tareaNueva.setEtapa(tarea.getEtapa());
				tareaNueva.setIdDiagrama(tarea.getIdDiagrama());
				tareaNueva.setNombreTarea(tarea.getNombreTarea());
				tareaNueva.setNumAuto(tarea.getNumAuto());
				tareaNueva.setObligatoria(tarea.getObligatoria());
				tareaNueva.setOrden(tarea.getOrden());
				tareaNueva.setProceso(procesoNuevo); 
				tareaNueva.setPuedeAplicarFEA(tarea.getPuedeAplicarFEA());
				tareaNueva.setPuedeVisarDocumentos(tarea.getPuedeVisarDocumentos());
				tareaNueva.setSoloInformar(tarea.getSoloInformar());
				tareaNueva.setTipoDeBifurcacion(tarea.getTipoDeBifurcacion());
				tareaNueva.setTipoReseteo(tarea.getTipoReseteo());
				tareaNueva.setUrlControl(tarea.getUrlControl());
				tareaNueva.setUrlWS(tarea.getUrlWS());
				tareaNueva.setVigente(tarea.getVigente());
							
				documentosDeSalidaDeTarea = tarea.getDocumentosDeSalidasDeTarea();
				//List<DocumentoDeSalidaDeTarea> documentosDeSalidaDeTareaNuevos = new ArrayList<DocumentoDeSalidaDeTarea>();
				for (DocumentoDeSalidaDeTarea documentoDeSalidaDeTarea: documentosDeSalidaDeTarea) {
					
					TipoDeDocumento tipoDeDocumentoNuevo = new TipoDeDocumento();
					tipoDeDocumento = documentoDeSalidaDeTarea.getId().getTipoDeDocumento();	
					tipoDeDocumentoNuevo.setAplicaFEA(tipoDeDocumento.getAplicaFEA());
					tipoDeDocumentoNuevo.setAplicaVisacion(tipoDeDocumento.getAplicaVisacion());
					tipoDeDocumentoNuevo.setCodTipoDocumento(tipoDeDocumento.getCodTipoDocumento());
					tipoDeDocumentoNuevo.setConformaExpediente(tipoDeDocumento.getConformaExpediente());
					tipoDeDocumentoNuevo.setEsDocumentoConductor(tipoDeDocumento.getEsDocumentoConductor());
					tipoDeDocumentoNuevo.setNombreCompletoCategoriaTipoDocumento(tipoDeDocumento.getNombreCompletoCategoriaTipoDocumento());
					tipoDeDocumentoNuevo.setNombreDeTipoDeDocumento(tipoDeDocumento.getNombreDeTipoDeDocumento());
					tipoDeDocumentoNuevo.setNumAuto(tipoDeDocumento.getNumAuto());
					
					DocumentoDeSalidaDeTarea documentoDeSalidaDeTareaNuevo = new DocumentoDeSalidaDeTarea();
					documentoDeSalidaDeTareaNuevo.getId().setOrden(documentoDeSalidaDeTarea.getId().getOrden());
					documentoDeSalidaDeTareaNuevo.getId().setTarea(tareaNueva);
					documentoDeSalidaDeTareaNuevo.getId().setTipoDeDocumento(tipoDeDocumentoNuevo);
					//documentosDeSalidaDeTareaNuevos.add(documentoDeSalidaDeTareaNuevo);
					
				}
				
				
				referenciasDeTareas = tarea.getReferenciasDeTareas();
				for (ReferenciaDeTarea referenciaDeTarea : referenciasDeTareas) {
					Tarea tareaSiguiente = referenciaDeTarea.getTareaSiguiente();
					
				}
				//tareaNueva.setReferenciasDeTareas(referenciasDeTareas);				
				
				responsabilidadesTarea = tarea.getResponsabilidadesTareas();
				for (ResponsabilidadTarea responsabilidadTarea: responsabilidadesTarea) {
					responsabilidad = responsabilidadTarea.getId().getResponsabilidad();
					usuariosResponsabilidades = responsabilidad.getUsuarioResponsabilidades();					
				}
				//tareaNueva.setResponsabilidadesTareas(responsabilidadesTareas);
				
				usuariosNotificacionTarea = tarea.getUsuarioNotificacionTarea();
				for (UsuarioNotificacionTarea usuarioNotificacionTarea : usuariosNotificacionTarea) {
					
				}
				//tareaNueva.setUsuarioNotificacionTarea(usuarioNotificacionTarea);
				
				
				//tareasNuevas.add(tareaNueva);
				
			}
			
			//procesoNuevo.setTareas(tareasNuevas);
			
		}		
		return "OK";
	}

}
