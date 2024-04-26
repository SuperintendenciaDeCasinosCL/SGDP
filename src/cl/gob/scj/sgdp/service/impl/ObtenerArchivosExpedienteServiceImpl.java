package cl.gob.scj.sgdp.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.HistoricoDeInstDeTareaDao;
import cl.gob.scj.sgdp.dao.HistoricoFirmaDao;
import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dto.ArchivoInfoDTO;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.ConfidencialidadDocumentoDTO;
import cl.gob.scj.sgdp.dto.ConfidencialidadDocumentoRolDTO;
import cl.gob.scj.sgdp.dto.ConfidencialidadDocumentoUsuarioDTO;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.RolDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.service.ConfidencialidadDocumentoService;
import cl.gob.scj.sgdp.service.InstanciaDeTareaService;
import cl.gob.scj.sgdp.service.ObtenerArchivosExpedienteService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.TipoDeDocumentoService;
import cl.gob.scj.sgdp.tipos.PermisoType;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.StringUtilSGDP;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.ObtenerArchivosExpedienteCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.ObtenerDetalleDeArchivoCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.DetalleDeArchivoResponse;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ObtenerArchivosExpedienteServiceImpl implements
		ObtenerArchivosExpedienteService {

	private static final Logger log = Logger.getLogger(ObtenerArchivosExpedienteServiceImpl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private ObtenerArchivosExpedienteCMSService obtenerArchivosExpedienteCMSService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private TipoDeDocumentoService tipoDeDocumentoService;
	
	@Autowired
	private TipoDeDocumentoDao tipoDeDocumentoDao;
	
	@Autowired
	private ArchivosInstDeTareaDao archivosInstDeTareaDao;
	
	@Autowired
	private ObtenerDetalleDeArchivoCMSService obtenerDetalleDeArchivoCMSService;
	
	@Autowired
	private InstanciaDeTareaService instanciaDeTareaService;
	
	@Autowired
	private HistoricoFirmaDao historicoFirmaDao;
	
	@Autowired
	private HistoricoDeInstDeTareaDao historicoDeInstDeTareaDao;
	
	@Autowired
	private ConfidencialidadDocumentoService confidencialidadDocumentoService;
		
	@Override
	public List<ArchivoInfoDTO> obtenerArchivosExpediente(Usuario usuario, String idExpediente, boolean filtraFirmas, boolean tareaPuedeVisarDocumentos, boolean tareaPuedeAplicarFEA, long idInstanciaDeTarea) throws SgdpException {		
		log.debug("Inicio... obtenerArchivosExpediente");		
		try {			
			List<ArchivoInfoDTO> archivosExpedienteDTO = obtenerArchivosExpedienteCMSService.obtenerArchivosExpediente(usuario, idExpediente).getListaDeArchivos();			
			if (filtraFirmas == true) {				
				for (ArchivoInfoDTO archivoInfoDTO: archivosExpedienteDTO) {	
					cargaAplicaFirmas(archivoInfoDTO, tareaPuedeVisarDocumentos, tareaPuedeAplicarFEA, idExpediente, idInstanciaDeTarea);	
				}				
			}	
			return archivosExpedienteDTO;			
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);		
			log.debug("Fin... obtenerArchivosExpediente.. error");
			throw new SgdpException(configProps.getProperty("errorAlObtenerArchivosDeExpediente"));
		}	
	}
	
	@Override
	public List<ArchivoInfoDTO> getListaArchivoInfoDTOAdjuntos(Usuario usuario, String nombreArchivo, List<ArchivoInfoDTO> listaArchivoInfoDTO) {
		List<ArchivoInfoDTO> listaArchivoInfoDTOAdjuntos = new ArrayList<ArchivoInfoDTO>();
		for (ArchivoInfoDTO archivoInfoDTO : listaArchivoInfoDTO) {
			if(archivoInfoDTO.getCartaRelacionada().equals(nombreArchivo)) {
				listaArchivoInfoDTOAdjuntos.add(archivoInfoDTO);
			}
		}		
		return listaArchivoInfoDTOAdjuntos;
	}
	
	@Override
	public void cargaAplicaFirmas(ArchivoInfoDTO archivoInfoDTO, boolean tareaPuedeVisarDocumentos, boolean tareaPuedeAplicarFEA, String idExpediente, long idInstanciaDeTarea) throws SgdpException {
		log.debug("Inicio cargaAplicaFirmas...");
		log.debug(archivoInfoDTO.toString());
		log.debug("tareaPuedeVisarDocumentos: " + tareaPuedeVisarDocumentos);
		log.debug("tareaPuedeAplicarFEA: " + tareaPuedeAplicarFEA);
		log.debug("archivoInfoDTO.getTipoDeDocumento(): " + archivoInfoDTO.getTipoDeDocumento());
		log.debug("idExpediente: " + idExpediente);
		log.debug("idInstanciaDeTarea: " + idInstanciaDeTarea);		
		TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoService.getTipoDeDocumentoDTOPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(archivoInfoDTO.getTipoDeDocumento(), idInstanciaDeTarea);
		if (tipoDeDocumentoDTO!=null) {
			log.debug(tipoDeDocumentoDTO.toString());
			ParametroDTO parametroDTOEsEditable = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_EDITABLES, archivoInfoDTO.getMimeType());
			ParametroDTO parametroDTOEsMimeTypeVisable = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_VISABLES, archivoInfoDTO.getMimeType());
			ParametroDTO parametroDTOEsMimeTypeAplicaFEA = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_APLICA_FEA, archivoInfoDTO.getMimeType());
			ParametroDTO parametroDTOEsMimeTypeAplicaFirmaApplet = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_APLICA_FIRMA_APPLET, archivoInfoDTO.getMimeType());
			if (parametroDTOEsEditable!=null) {
				archivoInfoDTO.setEsEditable(true);
				archivoInfoDTO.setCodigoMimeType(parametroDTOEsEditable.getValorParametroNumerico());
			}		
			if (parametroDTOEsMimeTypeVisable!=null && tipoDeDocumentoDTO.getAplicaVisacion()==true && tareaPuedeVisarDocumentos) {
				archivoInfoDTO.setEsVisable(true);
			} 	
			if (parametroDTOEsMimeTypeAplicaFEA!=null && tipoDeDocumentoDTO.getAplicaFEA()==true && tareaPuedeAplicarFEA) {
				archivoInfoDTO.setAplicaFEA(true);
			} 		
			if (parametroDTOEsMimeTypeAplicaFirmaApplet!=null && tipoDeDocumentoDTO.getAplicaFEA()==true && tareaPuedeAplicarFEA) {
				archivoInfoDTO.setAplicaFirmaApplet(true);
			} 
			log.debug(archivoInfoDTO.toString());
		}
		
	}
	
	@Override
	public void cargaArchivosInfoDTODeSalidaPorIdInstanciaDeTarea(Usuario usuario, InstanciaDeTareaDTO instanciaDeTareaDTO, 
			List<ArchivoInfoDTO> archivosInfoDTODeSalidaPorIdInstanciaDeTarea, List<ArchivoInfoDTO> archivosExpedienteDTO) throws SgdpException {
		log.debug("Inicio cargaArchivosInfoDTODeSalidaPorIdInstanciaDeTarea");
		log.debug("Buscando getTiposDeDocumentosDeSalidaPorIdInstanciaDeTarea con idInstanciaDeTarea: " + instanciaDeTareaDTO.getIdInstanciaDeTarea());
		List<TipoDeDocumento> tiposDeDocumentosDeSalidaPorIdTarea = tipoDeDocumentoDao.getTiposDeDocumentosDeSalidaPorIdInstanciaDeTarea(instanciaDeTareaDTO.getIdInstanciaDeTarea());
		List<ArchivosInstDeTarea> archivosInstDeTareaList = 
				archivosInstDeTareaDao.getArchivosPorIdInstanciaDeTareaIdUsuarioTiposDeDocDeTarea(instanciaDeTareaDTO.getIdInstanciaDeTarea(), usuario.getIdUsuario());	
		int cantidadDeArchivosValidaContinuaProceso = 0;
		int cantidadDeDocumentoConVisacionDeTarea = 0;
		int cantidadDeDocumentoConFEADeTarea = 0;		
		int cantidadDeDocumentoVisados = 0;
		int cantidadDeDocumentoFirmadosConFEA = 0;
		boolean validaCantidadDeDocumentosEnInstanciaDeTarea = false;
		boolean validaCantidadDeDocumentoConVisacion = false;
		boolean validaCantidadDeDocumentoConFEA = false;
		if (instanciaDeTareaDTO.getPuedeVisarDocumentos()) {
			cantidadDeDocumentoConVisacionDeTarea = this.getCantidadDeDocumentoConVisacionDeTarea(tiposDeDocumentosDeSalidaPorIdTarea);
		}
		if (instanciaDeTareaDTO.getPuedeAplicarFEA()) {
			cantidadDeDocumentoConFEADeTarea = this.getCantidadDeDocumentoConFEADeTarea(tiposDeDocumentosDeSalidaPorIdTarea);
		}
		if (archivosInstDeTareaList!=null && archivosInstDeTareaList.size()>0) {
			log.debug("archivosInstDeTareaList.size(): " + archivosInstDeTareaList.size());
			for (ArchivosInstDeTarea archivoInstDeTarea : archivosInstDeTareaList) {
				
				log.debug(archivoInstDeTarea.toString());
				ArchivoInfoDTO archivoInfoDTO = obtenerArchivoDeExpedienteNombreTipoDeDocumentoPorIdArchivoCMS(usuario, 
						archivosExpedienteDTO,
						archivoInstDeTarea);			
				if (archivoInfoDTO!=null) {
					log.debug("archivoInfoDTO!=null");
					archivoInfoDTO.setIdExpediente(instanciaDeTareaDTO.getIdExpediente());
					archivoInfoDTO.setSubidoALaTareaPorElUsuario(true);
					TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoService.getTipoDeDocumentoDTOPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(archivoInfoDTO.getTipoDeDocumento(), instanciaDeTareaDTO.getIdInstanciaDeTarea());
					archivoInfoDTO.setAplicaFEAPorTipoDeDocumento(tipoDeDocumentoDTO.getAplicaFEA());					
					archivoInfoDTO.setAplicaVisacionPorTipoDeDocumento(tipoDeDocumentoDTO.getAplicaVisacion());
					archivoInfoDTO.setEstaVisado(archivoInstDeTarea.getEstaVisado()!=null ? archivoInstDeTarea.getEstaVisado() : false);
					archivoInfoDTO.setEstaFirmadoConFEACentralizada(archivoInstDeTarea.getEstaFirmadoConFEACentralizada()!=null ? archivoInstDeTarea.getEstaFirmadoConFEACentralizada(): false);
					archivoInfoDTO.setEstaFirmadoConFEAWebStart(archivoInstDeTarea.getEstaFirmadoConFEAWebStart()!=null ? archivoInstDeTarea.getEstaFirmadoConFEAWebStart() : false);
					if (archivoInfoDTO.getEstaVisado()==true) {
						cantidadDeDocumentoVisados ++;
					}					
					if (archivoInfoDTO.getEstaFirmadoConFEAWebStart()==true) {
						cantidadDeDocumentoFirmadosConFEA ++;
					} else if (archivoInfoDTO.getEstaFirmadoConFEACentralizada()==true) {
						cantidadDeDocumentoFirmadosConFEA ++;
					}
					cargaAplicaFirmas(archivoInfoDTO, instanciaDeTareaDTO.getPuedeVisarDocumentos(), instanciaDeTareaDTO.getPuedeAplicarFEA(), instanciaDeTareaDTO.getIdExpediente(), instanciaDeTareaDTO.getIdInstanciaDeTarea());
					log.debug(archivoInfoDTO.toString());
					log.debug("tiposDeDocumentosDeSalidaPorIdTarea.size(): " + tiposDeDocumentosDeSalidaPorIdTarea.size());		
					archivosInfoDTODeSalidaPorIdInstanciaDeTarea.add(archivoInfoDTO);
					//Agregado por Hugo Cifuentes. la idea es agregar al documento quienes, usuariosy roles, tienen permiso para visualizar el documento
					archivoInfoDTO.setPuedeVerPorUsuario(confidencialidadDocumentoService.puedeVerPorUsuario(archivoInfoDTO.getIdArchivo(), usuario));
					archivoInfoDTO.setPuedeVerPorRol(confidencialidadDocumentoService.puedeVerPorRol(archivoInfoDTO.getIdArchivo(), usuario));
					log.debug(archivoInfoDTO.toString());
					cantidadDeArchivosValidaContinuaProceso ++;
				}				
			}
		} 
		if (tiposDeDocumentosDeSalidaPorIdTarea!=null 
				&& archivosInstDeTareaList!=null 
				&& archivosInstDeTareaList.size()>0 
				&& tiposDeDocumentosDeSalidaPorIdTarea.size()>archivosInstDeTareaList.size()) {			
			for (TipoDeDocumento tipoDeDocumento : tiposDeDocumentosDeSalidaPorIdTarea) {
				boolean estaTipoDeDocEnArchivosInstDeTareaList = tipoDeDocEstaEnArchivosInstDeTareaList(tipoDeDocumento.getIdTipoDeDocumento(), archivosInstDeTareaList);
				if (estaTipoDeDocEnArchivosInstDeTareaList==false) {
					ArchivoInfoDTO archivoInfoDTO = new ArchivoInfoDTO();	
					archivoInfoDTO.setAplicaFEAPorTipoDeDocumento(tipoDeDocumento.getAplicaFEA());					
					archivoInfoDTO.setAplicaVisacionPorTipoDeDocumento(tipoDeDocumento.getAplicaVisacion());
					archivoInfoDTO.setIdTipoDeDocumento(tipoDeDocumento.getIdTipoDeDocumento());
					archivoInfoDTO.setTipoDeDocumento(tipoDeDocumento.getNombreDeTipoDeDocumento());
					archivoInfoDTO.setConformaExpediente(tipoDeDocumento.getConformaExpediente());
					archivoInfoDTO.setEsDocumentoConductor(tipoDeDocumento.getEsDocumentoConductor());						
					archivoInfoDTO.setIdExpediente(instanciaDeTareaDTO.getIdExpediente());
					archivoInfoDTO.setSubidoALaTareaPorElUsuario(false);
					archivoInfoDTO.setAplicaFEAPorTipoDeDocumento(tipoDeDocumento.getAplicaFEA());
					log.debug(archivoInfoDTO.toString());
					log.debug("tiposDeDocumentosDeSalidaPorIdTarea.size(): " + tiposDeDocumentosDeSalidaPorIdTarea.size());		
					archivosInfoDTODeSalidaPorIdInstanciaDeTarea.add(archivoInfoDTO);
				}
			}			
		} else if (archivosInstDeTareaList==null || archivosInstDeTareaList.isEmpty()) {
			for (TipoDeDocumento tipoDeDocumento : tiposDeDocumentosDeSalidaPorIdTarea) {
				ArchivoInfoDTO archivoInfoDTO = new ArchivoInfoDTO();
				archivoInfoDTO.setAplicaFEAPorTipoDeDocumento(tipoDeDocumento.getAplicaFEA());					
				archivoInfoDTO.setAplicaVisacionPorTipoDeDocumento(tipoDeDocumento.getAplicaVisacion());
				archivoInfoDTO.setIdTipoDeDocumento(tipoDeDocumento.getIdTipoDeDocumento());
				archivoInfoDTO.setTipoDeDocumento(tipoDeDocumento.getNombreDeTipoDeDocumento());
				archivoInfoDTO.setConformaExpediente(tipoDeDocumento.getConformaExpediente());
				archivoInfoDTO.setEsDocumentoConductor(tipoDeDocumento.getEsDocumentoConductor());						
				archivoInfoDTO.setIdExpediente(instanciaDeTareaDTO.getIdExpediente());
				archivoInfoDTO.setSubidoALaTareaPorElUsuario(false);
				log.debug(archivoInfoDTO.toString());
				log.debug("tiposDeDocumentosDeSalidaPorIdTarea.size(): " + tiposDeDocumentosDeSalidaPorIdTarea.size());		
				archivosInfoDTODeSalidaPorIdInstanciaDeTarea.add(archivoInfoDTO);
			}
		}			
		log.debug("cantidadDeArchivosValidaContinuaProceso: " + cantidadDeArchivosValidaContinuaProceso);
		log.debug("tiposDeDocumentosDeSalidaPorIdTarea.size(): " + tiposDeDocumentosDeSalidaPorIdTarea.size());		
		if (cantidadDeArchivosValidaContinuaProceso >= tiposDeDocumentosDeSalidaPorIdTarea.size()) {
			validaCantidadDeDocumentosEnInstanciaDeTarea = true;			
		} 	
		log.debug("cantidadDeDocumentoConVisacionDeTarea: " + cantidadDeDocumentoConVisacionDeTarea);
		log.debug("cantidadDeDocumentoVisados: " + cantidadDeDocumentoVisados);
		if (instanciaDeTareaDTO.getPuedeVisarDocumentos() == true && cantidadDeDocumentoConVisacionDeTarea == cantidadDeDocumentoVisados) {
			validaCantidadDeDocumentoConVisacion = true;
		} else if (instanciaDeTareaDTO.getPuedeVisarDocumentos() == false ) {
			validaCantidadDeDocumentoConVisacion = true;
		}
		log.debug("cantidadDeDocumentoConFEADeTarea: " + cantidadDeDocumentoConFEADeTarea);
		log.debug("cantidadDeDocumentoFirmadosConFEA: " + cantidadDeDocumentoFirmadosConFEA);
		if (instanciaDeTareaDTO.getPuedeAplicarFEA() == true && cantidadDeDocumentoConFEADeTarea == cantidadDeDocumentoFirmadosConFEA ) {
			validaCantidadDeDocumentoConFEA = true;
		} else if (instanciaDeTareaDTO.getPuedeAplicarFEA() == false) {
			validaCantidadDeDocumentoConFEA = true;
		}
		log.debug("validaCantidadDeDocumentosEnInstanciaDeTarea: " + validaCantidadDeDocumentosEnInstanciaDeTarea);
		log.debug("validaCantidadDeDocumentoConVisacion: " + validaCantidadDeDocumentoConVisacion);
		log.debug("validaCantidadDeDocumentoConFEA: " + validaCantidadDeDocumentoConFEA);
		if (validaCantidadDeDocumentosEnInstanciaDeTarea == true) {
			instanciaDeTareaDTO.setPuedeAvanzarProceso(true);
		} else {
			instanciaDeTareaDTO.setPuedeAvanzarProceso(false);
		}
		if (validaCantidadDeDocumentosEnInstanciaDeTarea == true && validaCantidadDeDocumentoConVisacion == false) {
			instanciaDeTareaDTO.setPuedeAvanzarProcesoConAdvertenciaVisacion(true);
		}
		if (validaCantidadDeDocumentosEnInstanciaDeTarea == true && validaCantidadDeDocumentoConFEA == false) {
			instanciaDeTareaDTO.setPuedeAvanzarProcesoConAdvertenciaFEA(true);
		}
		log.debug(instanciaDeTareaDTO.toString());				
	}
	
	private boolean tipoDeDocEstaEnArchivosInstDeTareaList(long idTipoDeDocumento, List<ArchivosInstDeTarea> archivosInstDeTareaList){		
		for (ArchivosInstDeTarea archivosInstDeTarea: archivosInstDeTareaList) {
			if (archivosInstDeTarea.getTipoDeDocumento().getIdTipoDeDocumento() == idTipoDeDocumento) {
				return true;
			}
		}		
		return false;
	}
	
	private ArchivoInfoDTO obtenerArchivoDeExpedienteNombreTipoDeDocumentoPorIdArchivoCMS(Usuario usuario, 	
			List<ArchivoInfoDTO> archivosExpedienteDTO,
			ArchivosInstDeTarea archivoInstDeTarea) {
		log.debug("Inicio obtenerArchivoDeExpedienteNombreTipoDeDocumentoPorIdArchivoCMS...");
		//boolean comparaSacandoEspaciosCaracteresEspecialesIgnorandoCase = false;
		boolean comparaStringConLevenshteinDistance = false;
		ParametroDTO parametroDTOToleranciaNombreTipoDoc = parametroService.getParametroPorID(Constantes.ID_PARAM_MAX_DIF_TOLERANCIA_NOMBRE_TIPO_DOC);
		for (ArchivoInfoDTO archivoInfoDTO: archivosExpedienteDTO) {
			log.debug("archivoInfoDTO.getIdArchivo(): " + archivoInfoDTO.getIdArchivo());
			log.debug("archivoInstDeTarea.getIdArchivoCms(): " + archivoInstDeTarea.getIdArchivoCms());
			log.debug("archivoInfoDTO.getTipoDeDocumento(): " + archivoInfoDTO.getTipoDeDocumento());
			log.debug("archivoInstDeTarea.getTipoDeDocumento().getNombreDeTipoDeDocumento(): " + archivoInstDeTarea.getTipoDeDocumento().getNombreDeTipoDeDocumento());
			//comparaSacandoEspaciosCaracteresEspecialesIgnorandoCase = StringUtilSGDP.comparaSacandoEspaciosCaracteresEspecialesIgnorandoCase(archivoInfoDTO.getTipoDeDocumento(), archivoInstDeTarea.getTipoDeDocumento().getNombreDeTipoDeDocumento());
			comparaStringConLevenshteinDistance = StringUtilSGDP.comparaStringConLevenshteinDistance(archivoInfoDTO.getTipoDeDocumento(), archivoInstDeTarea.getTipoDeDocumento().getNombreDeTipoDeDocumento(), parametroDTOToleranciaNombreTipoDoc.getValorParametroNumerico().intValue());
			log.debug("comparaStringConLevenshteinDistance: " + comparaStringConLevenshteinDistance);
			if (//archivoInfoDTO.getTipoDeDocumento().equalsIgnoreCase(archivoInstDeTarea.getTipoDeDocumento().getNombreDeTipoDeDocumento()) 
					comparaStringConLevenshteinDistance == true
					&& archivoInfoDTO.getIdArchivo().equals(archivoInstDeTarea.getIdArchivoCms()) ){	
				archivoInfoDTO.setIdTipoDeDocumento(archivoInstDeTarea.getTipoDeDocumento().getIdTipoDeDocumento());
				archivoInfoDTO.setTipoDeDocumento(archivoInstDeTarea.getTipoDeDocumento().getNombreDeTipoDeDocumento());
				archivoInfoDTO.setConformaExpediente(archivoInstDeTarea.getTipoDeDocumento().getConformaExpediente());
				archivoInfoDTO.setEsDocumentoConductor(archivoInstDeTarea.getTipoDeDocumento().getEsDocumentoConductor());
				archivoInfoDTO.setLabelVersionEnInstanciaDeTarea(archivoInstDeTarea.getVersion());

				return archivoInfoDTO;		
			}
		}
		return null;
	}
	
	@Override
	public boolean archivoEsEditable(String MimeType) {
		ParametroDTO parametroDTOEsEditable = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_EDITABLES, MimeType);		
		if (parametroDTOEsEditable!=null) {
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public void cargaDetalleDeArchivosDTO(String idExpediente, 
			Usuario usuario, 
			long idTipoDeDocumento,
			List<DetalleDeArchivoDTO> detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento,
			boolean puedeVisarDocumentos,
			boolean puedeAplicarFEA,
			long idInstanciaDeTarea) throws Exception {
		
		log.debug("puedeVisarDocumentos: " + puedeVisarDocumentos);
		log.debug("puedeAplicarFEA: " + puedeAplicarFEA);
		
		InstanciaDeTarea instanciaDeTarea = instanciaDeTareaService.getInstanciaDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);
		List<ArchivosInstDeTarea> todosLosArchivosSubidosRequeridos = archivosInstDeTareaDao.getTodosArchivosSubidosRequeridosPorIdExpediente(idExpediente);
		
		List<HistoricoDeInstDeTarea> todosAvanzaDevuelveHistoricoDeInstDeTarea = historicoDeInstDeTareaDao.getTodosEnviaDevuelveReasignaHistoricoDeInstDeTareaPorIdInstanciaDeProceso(instanciaDeTarea.getInstanciaDeProceso().getIdInstanciaDeProceso());
		TipoDeDocumentoDTO tipoDeDocumentoDTODInstanciaDeTarea = tipoDeDocumentoService.getTipoDeDocumentoDTOPorIdTipoDeDocumento(idTipoDeDocumento);			
		HistoricoDeInstDeTarea historicoDeInstDeTarea = historicoDeInstDeTareaDao.getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovEnviaODevuelve(idInstanciaDeTarea);
		
		String permisoVisarDocumento = usuario.getPermisos().get(PermisoType.PUEDE_VISAR_DOCUMENTO.getNombrePermiso());
		String permisoFirmarADocumento = usuario.getPermisos().get(PermisoType.PUEDE_FIRMAR_CON_APPLET.getNombrePermiso());
		String permisoFirmarDocumento = usuario.getPermisos().get(PermisoType.PUEDE_FIRMAR_CON_FEA.getNombrePermiso());
		
		log.debug("permisoVisarDocumento: " + permisoVisarDocumento);
		log.debug("PermisoType.PUEDE_VISAR_DOCUMENTO.getNombrePermiso(): " + PermisoType.PUEDE_VISAR_DOCUMENTO.getNombrePermiso());
		
		if (puedeVisarDocumentos == true && permisoVisarDocumento!=null && !permisoVisarDocumento.isEmpty() && permisoVisarDocumento.equals(PermisoType.PUEDE_VISAR_DOCUMENTO.getNombrePermiso())) {
			puedeVisarDocumentos = true;
		} else {
			puedeVisarDocumentos = false;
		}
		
		if (puedeAplicarFEA == true && permisoFirmarADocumento!=null && !permisoFirmarADocumento.isEmpty() && permisoFirmarADocumento.equals(PermisoType.PUEDE_FIRMAR_CON_FEA.getNombrePermiso())) {
			puedeAplicarFEA = true;
		} else if (puedeAplicarFEA == true && permisoFirmarDocumento!=null && !permisoFirmarDocumento.isEmpty() && permisoFirmarDocumento.equals(PermisoType.PUEDE_FIRMAR_CON_FEA.getNombrePermiso())) {
			puedeAplicarFEA = true;
		} else {
			puedeAplicarFEA = false;
		}	
		
		log.debug("puedeVisarDocumentos: " + puedeVisarDocumentos);
		log.debug("puedeAplicarFEA: " + puedeAplicarFEA);
		
		//Buscamos hacia atras
		if (todosAvanzaDevuelveHistoricoDeInstDeTarea!= null 
				&& !todosAvanzaDevuelveHistoricoDeInstDeTarea.isEmpty() 
				&& historicoDeInstDeTarea!=null) {
			cargaDocBuscandoHaciaAtrasEnHistoricoInsTarea(todosAvanzaDevuelveHistoricoDeInstDeTarea.size(), historicoDeInstDeTarea, 
					detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento, 
					todosLosArchivosSubidosRequeridos,
					usuario,
					puedeVisarDocumentos,
					puedeAplicarFEA,
					tipoDeDocumentoDTODInstanciaDeTarea
					);
		}		
	}
	
	private void cargaDocBuscandoHaciaAtrasEnHistoricoInsTarea(int cantidadArchivos, HistoricoDeInstDeTarea historicoDeInstDeTarea, 
			List<DetalleDeArchivoDTO> detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento, 
			List<ArchivosInstDeTarea> todosLosArchivosSubidosRequeridos,
			Usuario usuario,
			boolean puedeVisarDocumentos,
			boolean puedeAplicarFEA,
			TipoDeDocumentoDTO tipoDeDocumentoDTODInstanciaDeTarea
			) throws Exception {
		log.info("Inicio cargaDocBuscandoHaciaAtrasEnHistoricoInsTarea");
		log.debug(historicoDeInstDeTarea.toString());						
		for (int i = 0; i < cantidadArchivos; i++) {
			log.info("Primer for i: " + i);
			log.info("Inicia consulta getArchivosInstDeTareaPorIdInstTareaIdUsuarioNombreTipoDocFechaSubidoMayorA");
			List<ArchivosInstDeTarea> archivosInstDeTareaPorIDInstTareaIdUsuarioNombreTipoDoc = 
					archivosInstDeTareaDao.getArchivosInstDeTareaPorIdInstTareaIdUsuarioNombreTipoDocFechaSubidoMayorA(historicoDeInstDeTarea.getInstanciaDeTareaDeOrigen().getIdInstanciaDeTarea(),
							historicoDeInstDeTarea.getIdUsuarioOrigen(), tipoDeDocumentoDTODInstanciaDeTarea.getNombreDeTipoDeDocumento(),
							historicoDeInstDeTarea.getInstanciaDeTareaDeOrigen().getFechaAsignacion());
			log.info("Fin consulta getArchivosInstDeTareaPorIdInstTareaIdUsuarioNombreTipoDocFechaSubidoMayorA");
			if (archivosInstDeTareaPorIDInstTareaIdUsuarioNombreTipoDoc!=null && !archivosInstDeTareaPorIDInstTareaIdUsuarioNombreTipoDoc.isEmpty()) {
				log.info("archivosInstDeTareaPorIDInstTareaIdUsuarioNombreTipoDoc!=null && !archivosInstDeTareaPorIDInstTareaIdUsuarioNombreTipoDoc.isEmpty()");
				for (ArchivosInstDeTarea archivosInstDeTarea : archivosInstDeTareaPorIDInstTareaIdUsuarioNombreTipoDoc) {
					log.info("Segundo for");
					log.debug(archivosInstDeTarea.toString());
					DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoCMSService.obtenerDetalleDeArchivo(usuario, archivosInstDeTarea.getIdArchivoCms()).getDetalleDeArchivoDTO();
					detalleDeArchivoDTO.setIdArchivo(archivosInstDeTarea.getIdArchivoCms());
					log.debug(detalleDeArchivoDTO.toString());
					ParametroDTO parametroDTOEsMimeTypeAplicaVisacion = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_VISABLES, detalleDeArchivoDTO.getMimeType());
					ParametroDTO parametroDTOEsMimeTypeAplicaFEA = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_APLICA_FEA, detalleDeArchivoDTO.getMimeType());
					ParametroDTO parametroDTOEsEditable = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_EDITABLES, detalleDeArchivoDTO.getMimeType());
					if (parametroDTOEsEditable!=null) {
						detalleDeArchivoDTO.setEsEditable(true);
						detalleDeArchivoDTO.setCodigoMimeType(parametroDTOEsEditable.getValorParametroNumerico());
					}
					log.debug(tipoDeDocumentoDTODInstanciaDeTarea.toString());
					log.debug("puedeVisarDocumentos: " + puedeVisarDocumentos);
					log.debug("puedeAplicarFEA: " + puedeAplicarFEA);				
					if (parametroDTOEsMimeTypeAplicaVisacion!=null) {
						log.debug("parametroDTOEsMimeTypeAplicaVisacion: " + parametroDTOEsMimeTypeAplicaVisacion.toString());
					}
					if (parametroDTOEsMimeTypeAplicaFEA!=null) {
						log.debug("parametroDTOEsMimeTypeAplicaFEA: " + parametroDTOEsMimeTypeAplicaFEA.toString());
					}
					if (parametroDTOEsMimeTypeAplicaVisacion==null && tipoDeDocumentoDTODInstanciaDeTarea.getAplicaVisacion()==true && puedeVisarDocumentos) {
						detalleDeArchivoDTO.setConvertirAPDF(true);
					}
					if (parametroDTOEsMimeTypeAplicaFEA==null && tipoDeDocumentoDTODInstanciaDeTarea.getAplicaFEA()==true && puedeAplicarFEA) {
						 detalleDeArchivoDTO.setConvertirAPDF(true);
					}
					if (estaArchivoEnListaTodosLosArchivosSubidosRequeridos(detalleDeArchivoDTO.getIdArchivo(), todosLosArchivosSubidosRequeridos)) {
						detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento.add(detalleDeArchivoDTO);	
					}
				}
				return;						
			} else {				
				log.info("archivosInstDeTareaPorIDInstTareaIdUsuarioNombreTipoDoc==null || archivosInstDeTareaPorIDInstTareaIdUsuarioNombreTipoDoc.isEmpty()");
				log.debug(historicoDeInstDeTarea.toString());
				log.info("Inicia consulta getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovEnviaODevuelveMenFech");
				historicoDeInstDeTarea = historicoDeInstDeTareaDao.getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovEnviaODevuelveMenFech(
						historicoDeInstDeTarea.getInstanciaDeTareaDeOrigen().getIdInstanciaDeTarea(), historicoDeInstDeTarea.getFechaMovimiento());
				log.info("Fin consulta getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovEnviaODevuelveMenFech");
				if (historicoDeInstDeTarea==null) {
					return;
				}
			}
		}
		log.info("Fin cargaDocBuscandoHaciaAtrasEnHistoricoInsTarea");
	}
	
	@Override
	public DetalleDeArchivoDTO obtenerDetalleDeArchivoDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento(String idExpediente, Usuario usuario, long idTipoDeDocumento, long idInstanciaDeTarea) throws Exception {
		log.debug("Inicio obtenerDetalleDeArchivoDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento");
		List<ArchivoInfoDTO> archivosExpedienteDTO = obtenerArchivosExpedienteCMSService.obtenerArchivosExpediente(usuario, idExpediente).getListaDeArchivos();
		for (ArchivoInfoDTO archivoInfoDTO: archivosExpedienteDTO) {
			archivoInfoDTO.setIdTipoDeDocumento(tipoDeDocumentoDao.getTipoDeDocumentoPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(archivoInfoDTO.getTipoDeDocumento(), idInstanciaDeTarea).getIdTipoDeDocumento());
			log.debug("archivoInfoDTO.getIdTipoDeDocumento() / idTipoDeDocumento: " + archivoInfoDTO.getIdTipoDeDocumento() + " / " +idTipoDeDocumento);
			log.debug("archivoInfoDTO.getUsuarioUltimaModificacion() / usuario.getIdUsuario(): " + archivoInfoDTO.getUsuarioUltimaModificacion() + " / " +usuario.getIdUsuario());
			if (archivoInfoDTO.getIdTipoDeDocumento() == idTipoDeDocumento && archivoInfoDTO.getUsuarioUltimaModificacion().equals(usuario.getIdUsuario())) {
				DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoCMSService.obtenerDetalleDeArchivo(usuario, archivoInfoDTO.getIdArchivo()).getDetalleDeArchivoDTO();
				detalleDeArchivoDTO.setIdArchivo(archivoInfoDTO.getIdArchivo());
				return detalleDeArchivoDTO;
			}
		}
		return null;
	}
	
	@Override
	public void cargaDetalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento(String idExpediente, 
			Usuario usuario, 
			long idTipoDeDocumento,
			List<DetalleDeArchivoDTO> detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento,
			boolean puedeVisarDocumentos,
			boolean puedeAplicarFEA,
			long idInstanciaDeTarea) throws Exception {
		log.debug("Inicio obtenerDetalleDeArchivoDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento");
		TipoDeDocumentoDTO tipoDeDocumentoDTODInstanciaDeTarea = tipoDeDocumentoService.getTipoDeDocumentoDTOPorIdTipoDeDocumento(idTipoDeDocumento);
		List<ArchivoInfoDTO> archivosExpedienteDTO = obtenerArchivosExpedienteCMSService.obtenerArchivosExpediente(usuario, idExpediente).getListaDeArchivos();
		//List<ArchivosInstDeTarea> archivosInstDeTareaAnteriorList = archivosInstDeTareaDao.getArchivosEnviadosPorIdInstanciaDeTareaIdTipoDeDocumento(idInstanciaDeTarea, idTipoDeDocumento);
		log.debug("idInstanciaDeTarea: " + idInstanciaDeTarea);
		log.debug("tipoDeDocumentoDTODInstanciaDeTarea.getNombreDeTipoDeDocumento(): " + tipoDeDocumentoDTODInstanciaDeTarea.getNombreDeTipoDeDocumento());
		List<ArchivosInstDeTarea> archivosInstDeTareaAnteriorList = archivosInstDeTareaDao.getArchivosEnviadosPorIdInstanciaDeTareaNombreDeDocumento(idInstanciaDeTarea, tipoDeDocumentoDTODInstanciaDeTarea.getNombreDeTipoDeDocumento());
		log.debug("archivosInstDeTareaAnteriorList.size(): " + archivosInstDeTareaAnteriorList.size());
		List<ArchivosInstDeTarea> todosLosArchivosSubidosRequeridos = archivosInstDeTareaDao.getTodosArchivosSubidosRequeridosPorIdExpediente(idExpediente);		
		InstanciaDeTarea instanciaDeTarea = instanciaDeTareaService.getInstanciaDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);
		//Agrega todos los archivos subidos en la instancia de tarea anterior
		log.debug("Agrega todos los archivos subidos en la instancia de tarea anterior que tenga archivos");
		for (ArchivosInstDeTarea archivosInstDeTarea : archivosInstDeTareaAnteriorList) {
			log.debug(archivosInstDeTarea.toString());
			DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoCMSService.obtenerDetalleDeArchivo(usuario, archivosInstDeTarea.getIdArchivoCms()).getDetalleDeArchivoDTO();
			detalleDeArchivoDTO.setIdArchivo(archivosInstDeTarea.getIdArchivoCms());
			//ParametroDTO parametroDTOEsMimeTypeConvertibleAPDF = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_CONVERTIBLES_A_PDF, detalleDeArchivoDTO.getMimeType());
			ParametroDTO parametroDTOEsMimeTypeAplicaVisacion = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_VISABLES, detalleDeArchivoDTO.getMimeType());
			ParametroDTO parametroDTOEsMimeTypeAplicaFEA = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_APLICA_FEA, detalleDeArchivoDTO.getMimeType());
			TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoService.getTipoDeDocumentoDTOPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(archivosInstDeTarea.getTipoDeDocumento().getNombreDeTipoDeDocumento(), idInstanciaDeTarea);	
			if (/*parametroDTOEsMimeTypeConvertibleAPDF!=null &&*/ parametroDTOEsMimeTypeAplicaVisacion==null && tipoDeDocumentoDTO.getAplicaVisacion()==true && puedeVisarDocumentos) {
				detalleDeArchivoDTO.setConvertirAPDF(true);
			}
			if (/*parametroDTOEsMimeTypeConvertibleAPDF!=null &&*/ parametroDTOEsMimeTypeAplicaFEA==null && tipoDeDocumentoDTO.getAplicaFEA()==true && puedeAplicarFEA) {
				 detalleDeArchivoDTO.setConvertirAPDF(true);
			}
			if (estaArchivoEnListaTodosLosArchivosSubidosRequeridos(detalleDeArchivoDTO.getIdArchivo(), todosLosArchivosSubidosRequeridos)) {
				detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento.add(detalleDeArchivoDTO);	
			}					
		}
		//Agrega todos los archivos modificados en la 'instancia de tarea actual' que no este en archivosInstDeTareaAnteriorList pero
		//que tenga el mismo nombre sin extencion que otro que si este
		log.debug("Agrega todos los archivos modificados en la 'instancia de tarea actual' que no este en archivosInstDeTareaAnteriorList pero que tenga el mismo nombre sin extencion que otro que si este");
		for (ArchivoInfoDTO archivoInfoDTO : archivosExpedienteDTO) {			
			if (agregaArchivoDeExpedienteAListaDeArchivosDTO(usuario, archivoInfoDTO, archivosInstDeTareaAnteriorList)) {
				log.debug(archivoInfoDTO.toString());
				DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoCMSService.obtenerDetalleDeArchivo(usuario, archivoInfoDTO.getIdArchivo()).getDetalleDeArchivoDTO();
				detalleDeArchivoDTO.setIdArchivo(archivoInfoDTO.getIdArchivo());
				//ParametroDTO parametroDTOEsMimeTypeConvertibleAPDF = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_CONVERTIBLES_A_PDF, archivoInfoDTO.getMimeType());
				ParametroDTO parametroDTOEsMimeTypeAplicaVisacion = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_VISABLES, archivoInfoDTO.getMimeType());
				ParametroDTO parametroDTOEsMimeTypeAplicaFEA = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_APLICA_FEA, archivoInfoDTO.getMimeType());
				TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoService.getTipoDeDocumentoDTOPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(archivoInfoDTO.getTipoDeDocumento(), idInstanciaDeTarea);	
				if (/*parametroDTOEsMimeTypeConvertibleAPDF!=null &&*/ parametroDTOEsMimeTypeAplicaVisacion==null && tipoDeDocumentoDTO.getAplicaVisacion()==true && puedeVisarDocumentos) {
					detalleDeArchivoDTO.setConvertirAPDF(true);
				}
				if (/*parametroDTOEsMimeTypeConvertibleAPDF!=null &&*/ parametroDTOEsMimeTypeAplicaFEA==null && tipoDeDocumentoDTO.getAplicaFEA()==true && puedeAplicarFEA) {
					 detalleDeArchivoDTO.setConvertirAPDF(true);
				}
				if (estaArchivoEnListaTodosLosArchivosSubidosRequeridos(detalleDeArchivoDTO.getIdArchivo(), todosLosArchivosSubidosRequeridos)) {
					detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento.add(detalleDeArchivoDTO);
				}
				
			}
		}
		//Agrega todos los archivos modificados en la 'instancia de tarea actual' por el usuario que no este en detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento
		log.debug("Agrega todos los archivos modificados en la 'instancia de tarea actual' por el usuario que no este en detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento");
		//boolean comparaSacandoEspaciosCaracteresEspecialesIgnorandoCase = false;
		boolean comparaStringConLevenshteinDistance = false;
		ParametroDTO parametroDTOToleranciaNombreTipoDoc = parametroService.getParametroPorID(Constantes.ID_PARAM_MAX_DIF_TOLERANCIA_NOMBRE_TIPO_DOC);
		for (ArchivoInfoDTO archivoInfoDTO : archivosExpedienteDTO) {
			//comparaSacandoEspaciosCaracteresEspecialesIgnorandoCase = StringUtilSGDP.comparaSacandoEspaciosCaracteresEspecialesIgnorandoCase(archivoInfoDTO.getTipoDeDocumento(), tipoDeDocumentoDTODInstanciaDeTarea.getNombreDeTipoDeDocumento());
			comparaStringConLevenshteinDistance = StringUtilSGDP.comparaStringConLevenshteinDistance(archivoInfoDTO.getTipoDeDocumento(), tipoDeDocumentoDTODInstanciaDeTarea.getNombreDeTipoDeDocumento(), parametroDTOToleranciaNombreTipoDoc.getValorParametroNumerico().intValue());
			log.debug("comparaStringConLevenshteinDistance: " + comparaStringConLevenshteinDistance);
			if (!estaArchivoInfoDTOEnListaDetalleDeArchivosDTO(archivoInfoDTO.getIdArchivo(), detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento)
					&& archivoInfoDTO.getUsuarioUltimaModificacion().equals(usuario.getIdUsuario())
					//&& archivoInfoDTO.getTipoDeDocumento().equals(tipoDeDocumentoDTODInstanciaDeTarea.getNombreDeTipoDeDocumento())
					&& comparaStringConLevenshteinDistance == true
					) {
				log.debug(archivoInfoDTO.toString());
				DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoCMSService.obtenerDetalleDeArchivo(usuario, archivoInfoDTO.getIdArchivo()).getDetalleDeArchivoDTO();
				detalleDeArchivoDTO.setIdArchivo(archivoInfoDTO.getIdArchivo());
				//ParametroDTO parametroDTOEsMimeTypeConvertibleAPDF = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_CONVERTIBLES_A_PDF, archivoInfoDTO.getMimeType());
				ParametroDTO parametroDTOEsMimeTypeAplicaVisacion = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_VISABLES, archivoInfoDTO.getMimeType());
				ParametroDTO parametroDTOEsMimeTypeAplicaFEA = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_APLICA_FEA, archivoInfoDTO.getMimeType());
				TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoService.getTipoDeDocumentoDTOPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(archivoInfoDTO.getTipoDeDocumento(), idInstanciaDeTarea);	
				if (/*parametroDTOEsMimeTypeConvertibleAPDF!=null &&*/ parametroDTOEsMimeTypeAplicaVisacion==null && tipoDeDocumentoDTO.getAplicaVisacion()==true && puedeVisarDocumentos) {
					detalleDeArchivoDTO.setConvertirAPDF(true);
				}
				if (/*parametroDTOEsMimeTypeConvertibleAPDF!=null &&*/ parametroDTOEsMimeTypeAplicaFEA==null && tipoDeDocumentoDTO.getAplicaFEA()==true && puedeAplicarFEA) {
					 detalleDeArchivoDTO.setConvertirAPDF(true);
				}
				if (estaArchivoEnListaTodosLosArchivosSubidosRequeridos(detalleDeArchivoDTO.getIdArchivo(), todosLosArchivosSubidosRequeridos)) {
					detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento.add(detalleDeArchivoDTO);
				}				
			}			
		}	
		//Agrega todos los archivos subidos por otro usuario en la instancia de tarea actual 'al momento de crear el expediente' que no esten en detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento
		if (instanciaDeTarea.getTarea().getOrden()==1) {
			List<ArchivosInstDeTareaDTO> todosLosDocSubidosPorIdInstTarea = instanciaDeTareaService.getTodosLosDocSubidosPorIdInstTarea(idInstanciaDeTarea);
			for (ArchivosInstDeTareaDTO archivosInstDeTareaDTO : todosLosDocSubidosPorIdInstTarea) {
				//Si el archivo es del tipo, fue subido por otra persona y no esta en detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento se agrega
				if (archivosInstDeTareaDTO.getIdTipoDeDocumento()==idTipoDeDocumento
						&& !archivosInstDeTareaDTO.getIdUsuario().equals(usuario.getIdUsuario())
						&& !estaArchivoInfoDTOEnListaDetalleDeArchivosDTO(archivosInstDeTareaDTO.getIdArchivoCms(), detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento)
						) {
					DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoCMSService.obtenerDetalleDeArchivo(usuario, archivosInstDeTareaDTO.getIdArchivoCms()).getDetalleDeArchivoDTO();
					detalleDeArchivoDTO.setIdArchivo(archivosInstDeTareaDTO.getIdArchivoCms());
					//ParametroDTO parametroDTOEsMimeTypeConvertibleAPDF = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_CONVERTIBLES_A_PDF, archivosInstDeTareaDTO.getMimeType());
					ParametroDTO parametroDTOEsMimeTypeAplicaVisacion = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_VISABLES, detalleDeArchivoDTO.getMimeType());
					ParametroDTO parametroDTOEsMimeTypeAplicaFEA = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_APLICA_FEA, detalleDeArchivoDTO.getMimeType());
					TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoService.getTipoDeDocumentoDTOPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(detalleDeArchivoDTO.getTipoDeDocumento(), idInstanciaDeTarea);	
					if (/*parametroDTOEsMimeTypeConvertibleAPDF!=null &&*/ parametroDTOEsMimeTypeAplicaVisacion==null && tipoDeDocumentoDTO.getAplicaVisacion()==true && puedeVisarDocumentos) {
						detalleDeArchivoDTO.setConvertirAPDF(true);
					}
					if (/*parametroDTOEsMimeTypeConvertibleAPDF!=null &&*/ parametroDTOEsMimeTypeAplicaFEA==null && tipoDeDocumentoDTO.getAplicaFEA()==true && puedeAplicarFEA) {
						 detalleDeArchivoDTO.setConvertirAPDF(true);
					}
					if (estaArchivoEnListaTodosLosArchivosSubidosRequeridos(detalleDeArchivoDTO.getIdArchivo(), todosLosArchivosSubidosRequeridos)) {
						detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento.add(detalleDeArchivoDTO);	
					}									
				}
			}
		}		
	}	
	
	private boolean estaArchivoEnListaTodosLosArchivosSubidosRequeridos(String idArchivo, List<ArchivosInstDeTarea> todosLosArchivosSubidosRequeridos) {
		for (ArchivosInstDeTarea archivosInstDeTarea : todosLosArchivosSubidosRequeridos) {
			if (idArchivo.equals(archivosInstDeTarea.getIdArchivoCms())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean estaArchivoInfoDTOEnListaDetalleDeArchivosDTO(String idArchivo, List<DetalleDeArchivoDTO> detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento) {
		for (DetalleDeArchivoDTO detalleDeArchivoDTO: detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento) {
			if (idArchivo.equals(detalleDeArchivoDTO.getIdArchivo())) {
				return true;
			}
		}		
		return false;
	}
	
	private boolean estaDetalleDeArchivoDTOEnListaArchivosInfoDTO(String idArchivo, List<ArchivoInfoDTO> listaArchivosInfoDTO) {
		for (ArchivoInfoDTO archivoInfoDTO: listaArchivosInfoDTO) {
			if (idArchivo.equals(archivoInfoDTO.getIdArchivo())) {
				return true;
			}
		}		
		return false;
	}		
	
	private boolean agregaArchivoDeExpedienteAListaDeArchivosDTO(Usuario usuario, ArchivoInfoDTO archivoInfoDTO, List<ArchivosInstDeTarea> archivosInstDeTareaAnteriorList) throws ParseException {
		log.debug(archivoInfoDTO.toString());
		//boolean comparaSacandoEspaciosCaracteresEspecialesIgnorandoCase = false;
		boolean comparaStringConLevenshteinDistance = false;
		ParametroDTO parametroDTOToleranciaNombreTipoDoc = parametroService.getParametroPorID(Constantes.ID_PARAM_MAX_DIF_TOLERANCIA_NOMBRE_TIPO_DOC);
		for (ArchivosInstDeTarea archivosInstDeTarea : archivosInstDeTareaAnteriorList) {
			int l = archivoInfoDTO.getNombre().lastIndexOf('.');
		    int w = archivosInstDeTarea.getNombreArchivo().lastIndexOf('.');
		    String nombreArchivoInfoDTO = archivoInfoDTO.getNombre().substring(0, l);
		    String nombreArchivoInstDeTarea = archivosInstDeTarea.getNombreArchivo().substring(0, w);
		    //comparaSacandoEspaciosCaracteresEspecialesIgnorandoCase = StringUtilSGDP.comparaSacandoEspaciosCaracteresEspecialesIgnorandoCase(archivoInfoDTO.getTipoDeDocumento(), archivosInstDeTarea.getTipoDeDocumento().getNombreDeTipoDeDocumento());
		    comparaStringConLevenshteinDistance = StringUtilSGDP.comparaStringConLevenshteinDistance(archivoInfoDTO.getTipoDeDocumento(), archivosInstDeTarea.getTipoDeDocumento().getNombreDeTipoDeDocumento(), parametroDTOToleranciaNombreTipoDoc.getValorParametroNumerico().intValue());
		    log.debug("comparaStringConLevenshteinDistance: " + comparaStringConLevenshteinDistance);
			if (!archivoInfoDTO.getIdArchivo().equals(archivosInstDeTarea.getIdArchivoCms())
					&& nombreArchivoInfoDTO.equals(nombreArchivoInstDeTarea)
					//&& archivoInfoDTO.getTipoDeDocumento().equals(archivosInstDeTarea.getTipoDeDocumento().getNombreDeTipoDeDocumento()) 
					&& comparaStringConLevenshteinDistance == true
					&& !archivoInfoDTO.getMimeType().equals(archivosInstDeTarea.getMimeType())
					&& archivoInfoDTO.getUsuarioUltimaModificacion().equals(usuario.getIdUsuario())
					) {
				log.debug(archivosInstDeTarea.toString());
				Date fechaArchivoExp = FechaUtil.getSimpleDateFormatFormHHMMSS().parse(archivoInfoDTO.getFechaUltimaModificacionCompleta());				
				if (archivosInstDeTarea.getFechaSubido().before(fechaArchivoExp)) {
					log.debug("archivosInstDeTarea.getFechaSubido().before(fechaArchivoExp): true");
					return true;
				}				
			}
		}		
		return false;
	}

	@Override
	public int archivoCodigoMineType(String mimeType) {
		ParametroDTO parametroDTOEsEditable = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_EDITABLES, mimeType);	
		if (parametroDTOEsEditable!=null) {
			return parametroDTOEsEditable.getValorParametroNumerico();
		} else {
			return 0;
		}		
	}
	
	private int getCantidadDeDocumentoConVisacionDeTarea(List<TipoDeDocumento> tiposDeDocumentosDeSalidaPorIdTarea) {
		int cantidadDeDocumentoConVisacionDeTarea = 0;
		for (TipoDeDocumento tipoDeDocumento: tiposDeDocumentosDeSalidaPorIdTarea) {
			if (tipoDeDocumento.getAplicaVisacion()==true) {
				cantidadDeDocumentoConVisacionDeTarea ++;
			}
		}
		return cantidadDeDocumentoConVisacionDeTarea;
	}
	
	private int getCantidadDeDocumentoConFEADeTarea(List<TipoDeDocumento> tiposDeDocumentosDeSalidaPorIdTarea) {
		int cantidadDeDocumentoConFEADeTarea = 0;
		for (TipoDeDocumento tipoDeDocumento: tiposDeDocumentosDeSalidaPorIdTarea) {
			if (tipoDeDocumento.getAplicaFEA()==true) {
				cantidadDeDocumentoConFEADeTarea ++;
			}
		}
		return cantidadDeDocumentoConFEADeTarea;
	}
	
	private boolean estaDetalleDeArchivoDTOEnListaDetalleDeArchivoDTO(String idArchivo, List<DetalleDeArchivoDTO> listaDetalleDeArchivoDTO) {
		for (DetalleDeArchivoDTO detalleDeArchivoDTO: listaDetalleDeArchivoDTO) {
			if (idArchivo.equals(detalleDeArchivoDTO.getIdArchivo())) {
				return true;
			}
		}		
		return false;
	}

	@Override
	public void cargaArchivosObligatoriosDeInstDeTareaAnteriorPorIdInstanTarea(Usuario usuario, 
			long idInstanciaDeTarea, 
			List<DetalleDeArchivoDTO> detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea,
			List<ArchivoInfoDTO> archivosInfoDTODeSalidaPorIdInstanciaDeTarea) throws Exception {
		List<ArchivosInstDeTarea> archivosObligatoriosDeInstDeTareaAnteriorPorIdInstanTarea = archivosInstDeTareaDao.getArchivosObligatoriosDeInstDeTareaAnteriorPorIdInstanTarea(idInstanciaDeTarea);
		Iterator<ArchivosInstDeTarea> it = archivosObligatoriosDeInstDeTareaAnteriorPorIdInstanTarea.iterator();
		while (it.hasNext()) {
			ArchivosInstDeTarea archivosInstDeTarea = (ArchivosInstDeTarea) it.next();
			DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoCMSService.obtenerDetalleDeArchivo(usuario, archivosInstDeTarea.getIdArchivoCms()).getDetalleDeArchivoDTO();
			detalleDeArchivoDTO.setIdArchivo(archivosInstDeTarea.getIdArchivoCms());	
			ParametroDTO parametroDTOEsEditable = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_EDITABLES, detalleDeArchivoDTO.getMimeType());
			if (parametroDTOEsEditable!=null) {
				detalleDeArchivoDTO.setEsEditable(true);
			}
			log.debug(detalleDeArchivoDTO.toString());
			if (!estaDetalleDeArchivoDTOEnListaArchivosInfoDTO(detalleDeArchivoDTO.getIdArchivo(), archivosInfoDTODeSalidaPorIdInstanciaDeTarea)
					&& !estaDetalleDeArchivoDTOEnListaDetalleDeArchivoDTO(detalleDeArchivoDTO.getIdArchivo(), detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea)) {
				detalleDeArchivoDTO.setCodigoMimeType(archivoCodigoMineType(detalleDeArchivoDTO.getMimeType()));
				detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea.add(detalleDeArchivoDTO);
			}
		}		
	}
	
	@Override
	public List<String> listaDocumentosFirmado(
			InstanciaDeProceso instanciaDeProceso) {
		return historicoFirmaDao.getListaDocumentosFirmados(instanciaDeProceso.getIdExpediente());
	}
	
	@Override
	public void cargaArchivosObligatoriosDeInstDeTareaAnteriorPorIdInstanTarea(Usuario usuario, 
			InstanciaDeTareaDTO instanciaDeTareaDTO, 
			List<DetalleDeArchivoDTO> detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea
			) throws Exception {		
		Map<String, TipoDeDocumentoDTO> tiposDeDocumentosDeSalida = instanciaDeTareaDTO.getTiposDeDocumentosDeSalida();
		if (tiposDeDocumentosDeSalida!=null && !tiposDeDocumentosDeSalida.isEmpty()) {
			boolean puedeVisarDocumentos = usuario.getPermisos().containsKey(PermisoType.PUEDE_VISAR_DOCUMENTO);
			boolean puedeAplicarFEA = usuario.getPermisos().containsKey(PermisoType.PUEDE_FIRMAR_CON_FEA);
			for (Map.Entry<String, TipoDeDocumentoDTO> entry : tiposDeDocumentosDeSalida.entrySet()) {
				List<DetalleDeArchivoDTO> detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento = new ArrayList<DetalleDeArchivoDTO>();
				cargaDetalleDeArchivosDTO(instanciaDeTareaDTO.getIdExpediente(), 
						usuario, 
						entry.getValue().getIdTipoDeDocumento(), 
						detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento,
						puedeVisarDocumentos,
						puedeAplicarFEA,
						instanciaDeTareaDTO.getIdInstanciaDeTarea());
				detalleDeArchDTOObligatoriosDeInstDeTareaAntPorIdInstTarea.addAll(detalleDeArchivosDTOPorIdExpedienteIdUsuarioIdTipoDeDocumento);
			}
		}				
	}
	
	@Override
	public void filtraPorNumeroDeDocumento(Usuario usuario, List<ArchivosInstDeTareaDTO> listaDeDocumentos) throws Exception {
		log.debug("Inicio filtraPorNumeroDeDocumento...");
		Iterator<ArchivosInstDeTareaDTO> it = listaDeDocumentos.iterator();		
		while (it.hasNext()) {
			ArchivosInstDeTareaDTO archivoInstDeTareaDTO = it.next();
			//log.debug(archivoInstDeTareaDTO.toString());
			DetalleDeArchivoResponse detalleDeArchivoResponse = obtenerDetalleDeArchivoCMSService.obtenerDetalleDeArchivo(usuario, archivoInstDeTareaDTO.getIdArchivoCms());			
			if (detalleDeArchivoResponse.getDetalleDeArchivoDTO()!=null && (
					detalleDeArchivoResponse.getDetalleDeArchivoDTO().getNumeroDocumento()==null ||
					detalleDeArchivoResponse.getDetalleDeArchivoDTO().getNumeroDocumento().isEmpty()					
					)) {
				log.info("Removiendo archivo por que no tiene numero de documento: " + detalleDeArchivoResponse.getDetalleDeArchivoDTO().toString());
				it.remove();
			} else if (detalleDeArchivoResponse.getDetalleDeArchivoDTO()!=null && 
					detalleDeArchivoResponse.getDetalleDeArchivoDTO().getEsDocumentoOficial()!=null && !detalleDeArchivoResponse.getDetalleDeArchivoDTO().getEsDocumentoOficial().equals("true")
					 ) {
				log.info("Removiendo archivo por que no es documento oficial o no conforma expediente: " + detalleDeArchivoResponse.getDetalleDeArchivoDTO().toString());
				it.remove();
			} else {
				//Verificamos si se subio en una tarea con numero de documento obligatorio
				log.debug("Verificamos si se subio en una tarea con numero de documento obligatorio");
				List<ArchivosInstDeTarea> archivosInstDeTarea =  archivosInstDeTareaDao.getArchivosPorIdArchivoEnTareaConNumero(archivoInstDeTareaDTO.getIdArchivoCms());
				if (archivosInstDeTarea==null || archivosInstDeTarea.isEmpty()) {
					log.info("Removiendo archivo por que no se ha subido en una tarea con numero obligatorio: " + detalleDeArchivoResponse.getDetalleDeArchivoDTO().toString());
					it.remove();
				}	
			}
		}
	}
	
}
