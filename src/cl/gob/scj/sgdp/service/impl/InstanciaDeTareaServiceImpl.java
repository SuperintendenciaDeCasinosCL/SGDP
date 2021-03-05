package cl.gob.scj.sgdp.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.HistoricoArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.HistoricoDeInstDeTareaDao;
import cl.gob.scj.sgdp.dao.InstanciaDeTareaDao;
import cl.gob.scj.sgdp.dto.ArchivoInfoDTO;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.HistoricoDeDocumentos;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTarea;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoValorParametroDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.ParametroRelacionTarea;
import cl.gob.scj.sgdp.service.InstanciaDeTareaService;
import cl.gob.scj.sgdp.service.MueveProcesoService;
import cl.gob.scj.sgdp.service.ObtenerArchivosExpedienteService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.TipoDeDocumentoService;
import cl.gob.scj.sgdp.service.UsuarioResponsabilidadService;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.SGDPUtil;

@Service
@Transactional(rollbackFor = Throwable.class)
public class InstanciaDeTareaServiceImpl implements InstanciaDeTareaService {

	private static final Logger log = Logger.getLogger(InstanciaDeTareaServiceImpl.class);

	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private InstanciaDeTareaDao instanciaDeTareaDao;
	
	@Autowired
	private HistoricoDeInstDeTareaDao historicoDeInstDeTareaDao;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private ObtenerArchivosExpedienteService obtenerArchivosExpedienteService;
	
	@Autowired
	private ArchivosInstDeTareaDao archivosInstDeTareaDao;
	
	@Autowired
	private TipoDeDocumentoService tipoDeDocumentoService;
	
	@Autowired
	private HistoricoArchivosInstDeTareaDao historicoArchivosInstDeTareaDao;
	
	@Autowired
	private UsuarioResponsabilidadService usuarioResponsabilidadService;
	
	@Autowired
	private MueveProcesoService mueveProcesoService;
	
	@Override
	public void cargaInstanciaDeTareaPorIdInstanciaDeTarea(
			long idInstanciaDeTarea, InstanciaDeTareaDTO instanciaDeTareaDTO) {
		
		log.debug("Inicio getInstanciaDeTareaPorIdInstanciaDeTarea");
		log.debug("idInstanciaDeTarea: " + idInstanciaDeTarea);
		
		InstanciaDeTarea instanciaDeTarea = instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);
		
		if (instanciaDeTarea!=null) {
			log.debug("instanciaDeTarea!=null");
		}	
		
		instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTarea);
		instanciaDeTareaDTO.cargaUsuariosAsignadosString(configProps.getProperty("caracterSeparadorDeUsuarios"));		
		
		HistoricoDeInstDeTarea historicoDeInstDeTareaUltimo = historicoDeInstDeTareaDao.getUltimoHistoricoDeInstDeTareaPorInstanciaDeTareaDeDestino(instanciaDeTarea);		
				
		if (historicoDeInstDeTareaUltimo != null) {
			log.debug("historicoDeInstDeTareaUltimo: " + historicoDeInstDeTareaUltimo.toString());
			instanciaDeTareaDTO.setUltimoComentario(historicoDeInstDeTareaUltimo.getComentario());
			instanciaDeTareaDTO.setPuedeDevolver(true);
			/*List<HistoricoValorParametroDeTarea> historicosValorParametroDeTarea = historicoDeInstDeTareaUltimo.getHistoricosValorParametroDeTarea();
			if (historicosValorParametroDeTarea!=null && !historicosValorParametroDeTarea.isEmpty()) {
				instanciaDeTareaDTO.setIdUltimaInstanciaDeTareaAnterior(historicoDeInstDeTareaUltimo.getInstanciaDeTareaDeOrigen().getIdInstanciaDeTarea());
				instanciaDeTareaDTO.setNombreTareaUltimaInstanciaDeTareaAnterior(historicoDeInstDeTareaUltimo.getInstanciaDeTareaDeOrigen().getTarea().getNombreTarea());
			}*/
			InstanciaDeTarea instanciaDeTareaAnterior = historicoDeInstDeTareaUltimo.getInstanciaDeTareaDeOrigen();//instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(historicoDeInstDeTareaUltimo.getInstanciaDeTareaDeOrigen().getIdInstanciaDeTarea());
			if (instanciaDeTareaAnterior.getUsuariosAsignados()!=null && !instanciaDeTareaAnterior.getUsuariosAsignados().isEmpty()) {
				instanciaDeTareaDTO.setUsuarioAnterior(instanciaDeTareaAnterior.getUsuariosAsignados().get(0).getId().getIdUsuario());
			} else {
				List<String> listaPosiblesUsuarios = new ArrayList<>();				
				usuarioResponsabilidadService.cargaUsuariosRolesPosiblesConOrdenPorIdInstanciaDeTarea(instanciaDeTareaAnterior.getIdInstanciaDeTarea(), listaPosiblesUsuarios);				        
				if (listaPosiblesUsuarios.isEmpty()) {
					instanciaDeTareaDTO.setUsuarioAnterior("No se encuentran usuarios a quien retroceder la tarea");
				} else {
					instanciaDeTareaDTO.setUsuarioAnterior(mueveProcesoService.getUsuarioAAsignarIgualAUsuarioDeOrigenListaString(listaPosiblesUsuarios, historicoDeInstDeTareaUltimo.getIdUsuarioOrigen()));
				}	
				if (instanciaDeTareaDTO.getUsuarioAnterior() == null || instanciaDeTareaDTO.getUsuarioAnterior() .isEmpty()) {
					instanciaDeTareaDTO.setUsuarioAnterior(listaPosiblesUsuarios.get(0));
				}
			}
		}
	}
	
	@Override
	public List<InstanciaDeTareaDTO> getInstanciaDeTareaDTOAnteriores(InstanciaDeTareaDTO instanciaDeTareaDTO) {
		if (instanciaDeTareaDTO.getPuedeDevolver()==true) {
			List<InstanciaDeTarea> instanciasDeTareasAnteriores = instanciaDeTareaDao.getInstanciasDeTareasAnterioresPorIdInstanciaDeTareaDeDestino(instanciaDeTareaDTO.getIdInstanciaDeTarea());
			List<InstanciaDeTareaDTO> instanciasDeTareasDTOAnteriores = new ArrayList<InstanciaDeTareaDTO>();
			for (InstanciaDeTarea instanciaDeTarea: instanciasDeTareasAnteriores) {
				InstanciaDeTareaDTO instanciaDeTareaDTOAnterior = new InstanciaDeTareaDTO();
				instanciaDeTareaDTOAnterior.cargaInstanciaDeTareaDTO(instanciaDeTarea);
				instanciasDeTareasDTOAnteriores.add(instanciaDeTareaDTOAnterior);
			}
			return instanciasDeTareasDTOAnteriores;
		} else {
			return null;
		}
	}
	
	@Override
	public void cargaInstanciasDeTareasDTOPorIdExpediente(String idExpediente, List<InstanciaDeTareaDTO> instanciasDeTareasDTO) throws SgdpException{		
		log.debug("Inicio cargaInstanciasDeTareasDTOPorIdExpediente");
		log.debug("idExpediente: " + idExpediente);
		List<InstanciaDeTarea> instanciasDeTareasPorIdExpediente = instanciaDeTareaDao.getInstanciasDeTareasPorIdExpediente(idExpediente);		
		
		for (InstanciaDeTarea instanciaDeTarea: instanciasDeTareasPorIdExpediente) { 
			InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();		 
			instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTarea);
			instanciaDeTareaDTO.cargaUsuariosAsignadosString(configProps.getProperty("caracterSeparadorDeUsuarios"));
			try {
				instanciaDeTareaDTO.cargaAdvertenciaDePlazo(
				parametroService.getParametroPorID(Constantes.ID_PARAM_PORCENTAJE_ADVERTENCIA_TAREA).getValorParametroNumerico(),
				instanciaDeTarea.getTarea().getDiasHabilesMaxDuracion());				
			} catch (IOException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
				throw new SgdpException(configProps.getProperty("errorAlObtenerTareas"));
			}
			log.debug(instanciaDeTarea.toString());
			log.debug(instanciaDeTareaDTO.toString());
			instanciasDeTareasDTO.add(instanciaDeTareaDTO);			
		}
		
	}

	@Override
	public List<HistoricoDeDocumentos> getTablaHistorialDeDocumentoPorIdExpediente(
			String idExpediente, Integer idInstanciaDeTarea , Usuario usuario) {
		
		// Declaracion de variables
		
		InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
		List<ArchivoInfoDTO> archivosInfoDTODeSalidaPorIdInstanciaDeTarea = new ArrayList<ArchivoInfoDTO>();
		
		List<HistoricoDeDocumentos> listaHistoricoDeDocumentosSalida = new ArrayList<HistoricoDeDocumentos>();
		
		
		// NEGOCIO
		
	     // Se obtiene el historico de las instancias de tarea
		List<HistoricoDeDocumentos> listaHistoricoDeDocumentosEntrada = this.buscarHistorialDeDocumento(idInstanciaDeTarea);		
		
		for (HistoricoDeDocumentos historicoDeDocumentos : listaHistoricoDeDocumentosEntrada) {
			
			//instanciaDeTareaDTO = this.getInstanciaDeTareaPorIdInstanciaDeTarea(historicoDeDocumentos.getIdInstanciaDeTarea(), instanciaDeTareaDTO);
			cargaInstanciaDeTareaPorIdInstanciaDeTarea(historicoDeDocumentos.getIdInstanciaDeTarea(), instanciaDeTareaDTO);	
						
		try {
			archivosInfoDTODeSalidaPorIdInstanciaDeTarea.clear();
			
			List<ArchivoInfoDTO> archivosExpedienteDTO = obtenerArchivosExpedienteService.obtenerArchivosExpediente(usuario, 
					instanciaDeTareaDTO.getIdExpediente(), true, instanciaDeTareaDTO.getPuedeVisarDocumentos(), instanciaDeTareaDTO.getPuedeAplicarFEA(), idInstanciaDeTarea);	
			
			obtenerArchivosExpedienteService.cargaArchivosInfoDTODeSalidaPorIdInstanciaDeTarea(usuario, 
					instanciaDeTareaDTO, 
					archivosInfoDTODeSalidaPorIdInstanciaDeTarea, archivosExpedienteDTO);						
			
			for (ArchivoInfoDTO archivoInfoDTO : archivosInfoDTODeSalidaPorIdInstanciaDeTarea) {
				//historicoDeDocumentosSalida = null;
				
				if (archivoInfoDTO.getIdArchivo() != null && historicoDeDocumentos.getNombreDeTipoDeDocumento().equals(archivoInfoDTO.getTipoDeDocumento())){
				
				HistoricoDeDocumentos historicoDeDocumentosSalida = new HistoricoDeDocumentos();
				
				historicoDeDocumentosSalida.setFecha(historicoDeDocumentos.getFecha());
				historicoDeDocumentosSalida.setNombreTarea(historicoDeDocumentos.getNombreTarea());
				historicoDeDocumentosSalida.setIdUsuarioQueAsigna(historicoDeDocumentos.getIdUsuarioQueAsigna());
				historicoDeDocumentosSalida.setComentario(historicoDeDocumentos.getComentario());
				historicoDeDocumentosSalida.setNombreDeTipoDeDocumento(historicoDeDocumentos.getNombreDeTipoDeDocumento());
				historicoDeDocumentosSalida.setIdInstanciaDeTarea(historicoDeDocumentos.getIdInstanciaDeTarea());
				historicoDeDocumentosSalida.setIdArchivo(archivoInfoDTO.getIdArchivo());
				historicoDeDocumentosSalida.setEsVisable(archivoInfoDTO.getEsVisable());
				historicoDeDocumentosSalida.setAplicaFEA(archivoInfoDTO.getAplicaFEA());
				historicoDeDocumentosSalida.setAplicaFirmaApplet(archivoInfoDTO.getAplicaFirmaApplet());
				historicoDeDocumentosSalida.setIdExpediente(archivoInfoDTO.getIdExpediente());
				historicoDeDocumentosSalida.setNombre(archivoInfoDTO.getNombre());
				historicoDeDocumentosSalida.setMimeType(archivoInfoDTO.getMimeType());
				
				listaHistoricoDeDocumentosSalida.add(historicoDeDocumentosSalida);
			  }
			}
			
			
		} catch (SgdpException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
		}

		}
  
		return listaHistoricoDeDocumentosSalida;
	}

	@Override
	public List<HistoricoDeDocumentos> buscarHistorialDeDocumento(
			Integer idInstanciaDeTarea) {
		return instanciaDeTareaDao.buscarHistorialDeDocumento(idInstanciaDeTarea);
	}
	
	@Override
	public void cargaTodosLosDocSubidosEnInstaciaDeProcPorIdInstTareaDeInstDeTareasAntOIguales(
			String idExpediente, long idInstanciaDeTarea, Usuario usuario, List<ArchivosInstDeTareaDTO> todosLosDocSubidos){
		List<ArchivosInstDeTarea> archivosInstDeTareaList = archivosInstDeTareaDao.getTodosLosDocSubidosEnInstaciaDeProcPorIdInstTareaDeInstDeTareasAntOIguales(idInstanciaDeTarea);
		for (ArchivosInstDeTarea archivosInstDeTarea: archivosInstDeTareaList) {
			ArchivosInstDeTareaDTO archivosInstDeTareaDTO = new ArchivosInstDeTareaDTO();
			archivosInstDeTareaDTO.setFechaSubido(archivosInstDeTarea.getFechaSubido());
			archivosInstDeTareaDTO.setIdArchivoCms(archivosInstDeTarea.getIdArchivoCms());
			archivosInstDeTareaDTO.setIdExpediente(idExpediente);
			archivosInstDeTareaDTO.setIdInstanciaDeTarea(idInstanciaDeTarea);
			archivosInstDeTareaDTO.setIdTipoDeDocumento(archivosInstDeTarea.getTipoDeDocumento().getIdTipoDeDocumento());			
			archivosInstDeTareaDTO.setIdUsuario(archivosInstDeTarea.getIdUsuario());
			archivosInstDeTareaDTO.setMimeType(archivosInstDeTarea.getMimeType());
			archivosInstDeTareaDTO.setNombreArchivo(archivosInstDeTarea.getNombreArchivo());
			archivosInstDeTareaDTO.setNombreDeTipoDeDocumento(archivosInstDeTarea.getTipoDeDocumento().getNombreDeTipoDeDocumento());
			archivosInstDeTareaDTO.setNombreTarea(archivosInstDeTarea.getInstanciaDeTarea().getTarea().getNombreTarea());
			archivosInstDeTareaDTO.setVersion(archivosInstDeTarea.getVersion());
			cargaAplicaFirmas(archivosInstDeTareaDTO, archivosInstDeTarea.getInstanciaDeTarea().getTarea().getPuedeVisarDocumentos(), archivosInstDeTarea.getInstanciaDeTarea().getTarea().getPuedeAplicarFEA());
			todosLosDocSubidos.add(archivosInstDeTareaDTO);
		}
	}
	
	@Override
	public List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdInstTarea(long idInstanciaDeTarea) {
		log.debug("getTodosLosDocSubidosPorIdInstTarea...");
		List<ArchivosInstDeTareaDTO> archivosInstDeTareaDTOList = archivosInstDeTareaDao.getTodosLosDocSubidosPorIdInstTarea(idInstanciaDeTarea);
		List<ArchivosInstDeTareaDTO> archivosInstDeTareaDTOListHistoricoOrigen = historicoArchivosInstDeTareaDao.getTodosLosDocSubidosPorIdInstTareaHistoricoOrigen(idInstanciaDeTarea);
		SGDPUtil.agregaArchivosInstDeTareaDTOSiNoEstaEnDestino(archivosInstDeTareaDTOListHistoricoOrigen, archivosInstDeTareaDTOList);
		for (ArchivosInstDeTareaDTO archivosInstDeTareaDTO: archivosInstDeTareaDTOList) {
			log.debug(archivosInstDeTareaDTO.toString());
			cargaAplicaFirmas(archivosInstDeTareaDTO, archivosInstDeTareaDTO.getPuedeVisarDocumentos(), archivosInstDeTareaDTO.getPuedeAplicarFEA());
		}
		return archivosInstDeTareaDTOList;
	}
	
	@Override
	public List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdInstTareaDeInstDeTareasAntOIguales(
			String idExpediente, long idInstanciaDeTarea, Usuario usuario){
		List<ArchivosInstDeTareaDTO> archivosInstDeTareaDTOList = archivosInstDeTareaDao.getTodosLosDocSubidosPorIdInstTareaDeInstDeTareasAntOIguales(idInstanciaDeTarea);
		for (ArchivosInstDeTareaDTO archivosInstDeTareaDTO: archivosInstDeTareaDTOList) {
			archivosInstDeTareaDTO.setIdExpediente(idExpediente);
			cargaAplicaFirmas(archivosInstDeTareaDTO, archivosInstDeTareaDTO.getPuedeVisarDocumentos(), archivosInstDeTareaDTO.getPuedeAplicarFEA());
		}
		return archivosInstDeTareaDTOList;
	}
	
	public void cargaAplicaFirmas(ArchivosInstDeTareaDTO archivosInstDeTareaDTO, boolean tareaPuedeVisarDocumentos, boolean tareaPuedeAplicarFEA) {
		
		//TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoService.getTipoDeDocumentoDTOPorNombreDeTipoDeDocumento(archivosInstDeTareaDTO.getNombreDeTipoDeDocumento());
		TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoService.getTipoDeDocumentoDTOPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(archivosInstDeTareaDTO.getNombreDeTipoDeDocumento(), archivosInstDeTareaDTO.getIdInstanciaDeTarea());
		
		ParametroDTO parametroDTOEsEditable = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_EDITABLES, archivosInstDeTareaDTO.getMimeType());
		
		ParametroDTO parametroDTOEsMimeTypeVisable = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_VISABLES, archivosInstDeTareaDTO.getMimeType());
		
		ParametroDTO parametroDTOEsMimeTypeAplicaFEA = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_APLICA_FEA, archivosInstDeTareaDTO.getMimeType());
		
		ParametroDTO parametroDTOEsMimeTypeAplicaFirmaApplet = parametroService.getParametroPorNombreParamYValorParam(Constantes.NOMBRE_PARAM_MIME_TYPES_APLICA_FIRMA_APPLET, archivosInstDeTareaDTO.getMimeType());
							
		if (parametroDTOEsEditable!=null) {
			archivosInstDeTareaDTO.setEsEditable(true);
			//archivoInfoDTO.setCodigoMimeType(parametroDTOEsEditable.getValorParametroNumerico());
		}
		
		if (parametroDTOEsMimeTypeVisable!=null && tipoDeDocumentoDTO.getAplicaVisacion()==true && tareaPuedeVisarDocumentos) {
			archivosInstDeTareaDTO.setEsVisable(true);
		}
		
		if (parametroDTOEsMimeTypeAplicaFEA!=null && tipoDeDocumentoDTO.getAplicaFEA()==true && tareaPuedeAplicarFEA) {
			archivosInstDeTareaDTO.setAplicaFEA(true);
		}
		
		if (parametroDTOEsMimeTypeAplicaFirmaApplet!=null && tipoDeDocumentoDTO.getAplicaFEA()==true && tareaPuedeAplicarFEA) {
			archivosInstDeTareaDTO.setAplicaFirmaApplet(true);
		}
	}
	
	@Override
	public InstanciaDeTarea getInstanciaDeTareaPorIdInstanciaDeTarea(
			long idInstanciaDeTarea) {
		return instanciaDeTareaDao.getInstanciaDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);
	}
	
	@Override
	public InstanciaDeTareaDTO getInstanciaDeTareaPorIdDiagramaTareaNombreExpediente(String idDiagramaTarea, String nombreExpediente) {
		InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
		InstanciaDeTarea instanciaDeTarea = instanciaDeTareaDao.getInstanciaDeTareaPorIdDiagramaTareaNombreExpediente(idDiagramaTarea, nombreExpediente);
		instanciaDeTareaDTO.cargaInstanciaDeTareaDTO(instanciaDeTarea);
		usuarioResponsabilidadService.cargaUsuariosRolesPosiblesPorIdInstanciaDeTarea(instanciaDeTareaDTO.getIdInstanciaDeTarea(), instanciaDeTareaDTO.getPosiblesIdUsuarios());
		return instanciaDeTareaDTO;
	}
	
	/*public void cargaInstanciaDeTareaDTO(InstanciaDeTarea instanciaDeTarea, InstanciaDeTareaDTO instanciaDeTareaDTO) {
		log.debug("Inicio cargaInstanciaDeTareaDTO");		
		if (instanciaDeTarea.getInstanciaDeProceso().getInstanciaDeProcesoPadre()!= null) {
			instanciaDeTareaDTO.setOrigen(instanciaDeTarea.getInstanciaDeProceso().getInstanciaDeProcesoPadre().getProceso().getUnidad().getCodigoUnidad());
		} 
		instanciaDeTareaDTO.setIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea());
		instanciaDeTareaDTO.setNombreDeProceso(instanciaDeTarea.getTarea().getProceso().getNombreProceso());		
		instanciaDeTareaDTO.setNombreDeTarea(instanciaDeTarea.getTarea().getNombreTarea());			
		instanciaDeTareaDTO.setNombreExpediente(instanciaDeTarea.getInstanciaDeProceso().getNombreExpediente());
		instanciaDeTareaDTO.setFechaVencimientoInstanciaDeTarea(instanciaDeTarea.getFechaVencimiento());
		instanciaDeTareaDTO.setTieneDocumentosEnCMS(instanciaDeTarea.getInstanciaDeProceso().getTieneDocumentosEnCMS());
		instanciaDeTareaDTO.setOrden(instanciaDeTarea.getTarea().getOrden());
		instanciaDeTareaDTO.setIdExpediente(instanciaDeTarea.getInstanciaDeProceso().getIdExpediente());
		instanciaDeTareaDTO.setFechaDeInicio(instanciaDeTarea.getFechaInicio());
		instanciaDeTareaDTO.setIniciadorProceso(instanciaDeTarea.getInstanciaDeProceso().getIdUsuarioInicia());		
		instanciaDeTareaDTO.setEsUltimaTarea(instanciaDeTarea.getTarea().getEsUltimaTarea());
		instanciaDeTareaDTO.setFechaVencimientoInstanciaDeProceso(instanciaDeTarea.getInstanciaDeProceso().getFechaVencimiento());
		instanciaDeTareaDTO.setTipoDeBifurcacion(instanciaDeTarea.getTarea().getTipoDeBifurcacion());
		instanciaDeTareaDTO.setPuedeVisarDocumentos(instanciaDeTarea.getTarea().getPuedeVisarDocumentos());
		instanciaDeTareaDTO.setPuedeAplicarFEA(instanciaDeTarea.getTarea().getPuedeAplicarFEA());
		instanciaDeTareaDTO.cargaIdUsuariosAsignados(instanciaDeTarea.getUsuariosAsignados());	
		instanciaDeTareaDTO.setFechaVencimientoUsuario(instanciaDeTarea.getFechaVencimientoUsuario());
		instanciaDeTareaDTO.setEmisor(instanciaDeTarea.getInstanciaDeProceso().getEmisor());
		instanciaDeTareaDTO.setFechaInicioInstanciaDeProceso(instanciaDeTarea.getInstanciaDeProceso().getFechaInicio());
		instanciaDeTareaDTO.setAsunto(instanciaDeTarea.getInstanciaDeProceso().getAsunto());
		instanciaDeTareaDTO.setIdUsuarioQueAsigna(instanciaDeTarea.getIdUsuarioQueAsigna());
		instanciaDeTareaDTO.setObligatoria(instanciaDeTarea.getTarea().getObligatoria());
		//this.setNombreRolQueEjecuta(instanciaDeTarea.getTarea().getTareasRoles().get(0).getId().getRol().getNombreRol());
		instanciaDeTareaDTO.setNombreRolQueEjecuta(instanciaDeTarea.getTarea().getResponsabilidadesTareas().get(0).getId().getResponsabilidad().getNombreResponsabilidad());
		instanciaDeTareaDTO.setIdInstanciaDeProceso(instanciaDeTarea.getInstanciaDeProceso().getIdInstanciaDeProceso());
		instanciaDeTareaDTO.setUrlControl(instanciaDeTarea.getTarea().getUrlControl());
		instanciaDeTareaDTO.setIdDiagrama(instanciaDeTarea.getTarea().getIdDiagrama());
		instanciaDeTareaDTO.setIdProceso(instanciaDeTarea.getInstanciaDeProceso().getProceso().getIdProceso());
		instanciaDeTareaDTO.setIdEstadoTarea(instanciaDeTarea.getEstadoDeTarea().getIdEstadoDeTarea());
		instanciaDeTareaDTO.setAsignaNumDoc(instanciaDeTarea.getTarea().getAsignaNumDoc());
		instanciaDeTareaDTO.setEsperaRespuesta(instanciaDeTarea.getTarea().getEsperarResp());
		instanciaDeTareaDTO.setIdTarea(instanciaDeTarea.getTarea().getIdTarea());
		instanciaDeTareaDTO.setDistribuye(instanciaDeTarea.getTarea().getDistribuye() == null ? false : instanciaDeTarea.getTarea().getDistribuye().booleanValue());
		
		List<ParametroRelacionTarea> parametrosRelacionTarea = instanciaDeTarea.getTarea().getParametroRelacionTareas();
		
		if (parametrosRelacionTarea!=null && !parametrosRelacionTarea.isEmpty()) {
			instanciaDeTareaDTO.setEsRdsSnc(true);
		}
		
		instanciaDeTareaDTO.setProcesoTieneRdsSnc(instanciaDeTarea.getTarea().getProceso().getTieneRdsSnc() == null ? false : instanciaDeTarea.getTarea().getProceso().getTieneRdsSnc().booleanValue());
		
		if (instanciaDeTarea.getFechaVencimiento()!=null) {
			try {
				instanciaDeTareaDTO.setFechaVencimientoInstanciaDeTareaJavaScript(FechaUtil.toFormat(instanciaDeTarea.getFechaVencimiento(), FechaUtil.simpleDateFormatShortDate));			
			} catch (ParseException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
			}
		}
		List<DocumentoDeSalidaDeTarea> documentosDeSalidasDeTarea = instanciaDeTarea.getTarea().getDocumentosDeSalidasDeTarea();
		if (documentosDeSalidasDeTarea!=null && documentosDeSalidasDeTarea.size()>0) {
			instanciaDeTareaDTO.setTiposDeDocumentosDeSalida(new HashMap<String, TipoDeDocumentoDTO>());
			for (DocumentoDeSalidaDeTarea documentoDeSalidaDeTarea : documentosDeSalidasDeTarea) {
				TipoDeDocumentoDTO tipoDeDocumentoDTO = new TipoDeDocumentoDTO(documentoDeSalidaDeTarea.getId().getTipoDeDocumento().getIdTipoDeDocumento(), documentoDeSalidaDeTarea.getId().getTipoDeDocumento().getNombreDeTipoDeDocumento());
				instanciaDeTareaDTO.getTiposDeDocumentosDeSalida().put(documentoDeSalidaDeTarea.getId().getTipoDeDocumento().getNombreDeTipoDeDocumento(), tipoDeDocumentoDTO);
			}
		}
		BeanUtils.copyProperties(instanciaDeTarea.getInstanciaDeProceso(), instanciaDeTareaDTO.getInstanciaDeProcesoDTO());
	}*/
	
	
}
