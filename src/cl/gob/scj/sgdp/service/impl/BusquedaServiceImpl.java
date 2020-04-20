package cl.gob.scj.sgdp.service.impl;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.InstanciaDeTareaDao;
import cl.gob.scj.sgdp.dao.ProcesoDao;
import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dto.BuscarConFiltroDTO;
import cl.gob.scj.sgdp.dto.BuscarDTO;
import cl.gob.scj.sgdp.dto.CargaFacetDTO;
import cl.gob.scj.sgdp.dto.ElementoResultadoBusquedaDTO;
import cl.gob.scj.sgdp.dto.ExpedienteContieneDTO;
import cl.gob.scj.sgdp.dto.RespuestaCargaFacetDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.service.BusquedaService;
import cl.gob.scj.sgdp.tipos.FacetFechaType;
import cl.gob.scj.sgdp.tipos.FiltroDeBusquedaType;
import cl.gob.scj.sgdp.util.AppUtil;
import cl.gob.scj.sgdp.util.DataTableRequestDTO;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.BusquedaCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.ElementoBuscar;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.ExpedienteContiene;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.ResultadoBusquedaResponse;

@Service
public class BusquedaServiceImpl implements BusquedaService {

	private static final Logger log = Logger.getLogger(BusquedaServiceImpl.class);
	
	@Autowired
	private BusquedaCMSService busquedaCMSService;
	
	@Autowired
	private InstanciaDeTareaDao instanciaDeTareaDao;
	
	@Autowired
	private ArchivosInstDeTareaDao archivosInstDeTareaDao;
	
	@Autowired
	private TipoDeDocumentoDao tipoDeDocumentoDao;
	
	@Autowired
    private ProcesoDao procesoDao;
	
	FiltroDeBusquedaType filtroDeBusquedaTypeAutor = FiltroDeBusquedaType.AUTOR;
	FiltroDeBusquedaType filtroDeBusquedaTypeMateria = FiltroDeBusquedaType.MATERIA;
	FiltroDeBusquedaType filtroDeBusquedaTypeNombre = FiltroDeBusquedaType.NOMBRE;
	FiltroDeBusquedaType filtroDeBusquedaTypeSubProceso = FiltroDeBusquedaType.SUBPROCESO;
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Override
	@Transactional
	public ResultadoBusquedaDTO buscar(BuscarDTO buscarDTO, Usuario usuario) throws SgdpException {
		log.debug("Inicio buscar");
		ResultadoBusquedaDTO resultadoBusquedaDTO = new ResultadoBusquedaDTO();
		try {
			ResultadoBusquedaResponse resultadoBusquedaResponse = busquedaCMSService.buscar(buscarDTO, usuario);
			cargarResultadoBusquedaDTO(resultadoBusquedaDTO, resultadoBusquedaResponse);
			if (buscarDTO.getSoloEnMiBandejaDeSalida()==true) {
				filtraPorBandejaDeSalida(resultadoBusquedaDTO, usuario);
			}
			//cargaFacet(resultadoBusquedaDTO);
			return resultadoBusquedaDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new SgdpException(configProps.getProperty("errorAlCargarFormularioDeBusqueda"));
		}
	}
	
	@Override
	public void cargaFacet(ResultadoBusquedaDTO resultadoBusquedaDTO) {
		
		for (ElementoResultadoBusquedaDTO elementoResultadoBusquedaDTO : resultadoBusquedaDTO.getElementosResultadoBusquedaDTO()) {
			
			//Facet SubProcesos
			if (elementoResultadoBusquedaDTO.getSubProceso()!=null &&
					!elementoResultadoBusquedaDTO.getSubProceso().isEmpty() && 
					 resultadoBusquedaDTO.getFacetSubProcesos().containsKey(elementoResultadoBusquedaDTO.getSubProceso())){
				resultadoBusquedaDTO.getFacetSubProcesos().put(
															elementoResultadoBusquedaDTO.getSubProceso(), 
															resultadoBusquedaDTO.getFacetSubProcesos().get(elementoResultadoBusquedaDTO.getSubProceso())+1
															);
			} else if (elementoResultadoBusquedaDTO.getSubProceso()!=null &&
					!elementoResultadoBusquedaDTO.getSubProceso().isEmpty()) {
				resultadoBusquedaDTO.getFacetSubProcesos().put(
						elementoResultadoBusquedaDTO.getSubProceso(), 
						1
						);
			}
			
			//Facet Materia
			if (elementoResultadoBusquedaDTO.getMateria()!=null &&
					!elementoResultadoBusquedaDTO.getMateria().isEmpty() &&
					resultadoBusquedaDTO.getFacetMaterias().containsKey(elementoResultadoBusquedaDTO.getMateria())) {
				resultadoBusquedaDTO.getFacetMaterias().put(
															elementoResultadoBusquedaDTO.getMateria(), 
															resultadoBusquedaDTO.getFacetMaterias().get(elementoResultadoBusquedaDTO.getMateria())+1						
															);
			} else if (elementoResultadoBusquedaDTO.getMateria()!=null &&
					!elementoResultadoBusquedaDTO.getMateria().isEmpty()) {
				resultadoBusquedaDTO.getFacetMaterias().put(
						elementoResultadoBusquedaDTO.getMateria(), 
						1						
						);
			}
			
			//Facet Autores
			if (elementoResultadoBusquedaDTO.getAutor()!=null &&
					!elementoResultadoBusquedaDTO.getAutor().isEmpty() &&
					resultadoBusquedaDTO.getFacetAutores().containsKey(elementoResultadoBusquedaDTO.getAutor())) {
				resultadoBusquedaDTO.getFacetAutores().put(
															elementoResultadoBusquedaDTO.getAutor(), 
															resultadoBusquedaDTO.getFacetAutores().get(elementoResultadoBusquedaDTO.getAutor())+1
															);
			} else if (elementoResultadoBusquedaDTO.getAutor()!=null &&
					!elementoResultadoBusquedaDTO.getAutor().isEmpty()) {
				resultadoBusquedaDTO.getFacetAutores().put(
						elementoResultadoBusquedaDTO.getAutor(), 
						1
						);
			}
			
			//Facet Creadores
			if (elementoResultadoBusquedaDTO.getCreador()!=null &&
					!elementoResultadoBusquedaDTO.getCreador().isEmpty() &&
					resultadoBusquedaDTO.getFacetCreadores().containsKey(elementoResultadoBusquedaDTO.getCreador())) {
				resultadoBusquedaDTO.getFacetCreadores().put(
															elementoResultadoBusquedaDTO.getCreador(), 
															resultadoBusquedaDTO.getFacetCreadores().get(elementoResultadoBusquedaDTO.getCreador())+1
															);
			} else if (elementoResultadoBusquedaDTO.getCreador()!=null &&
					!elementoResultadoBusquedaDTO.getCreador().isEmpty()) {
				resultadoBusquedaDTO.getFacetCreadores().put(
						elementoResultadoBusquedaDTO.getCreador(), 
						1
						);
			}
			
			//Facet Fechas
			if (elementoResultadoBusquedaDTO.getFacetFechaCreacion()!=null &&
					!elementoResultadoBusquedaDTO.getFacetFechaCreacion().isEmpty() && 
				//	resultadoBusquedaDTO.getFacetFechasCreacion().containsKey(elementoResultadoBusquedaDTO.getFechaDeCreacion())) {
					resultadoBusquedaDTO.getFacetFechasCreacion().containsKey(elementoResultadoBusquedaDTO.getFacetFechaCreacion())) {
				resultadoBusquedaDTO.getFacetFechasCreacion().put(
															elementoResultadoBusquedaDTO.getFacetFechaCreacion(), 
															resultadoBusquedaDTO.getFacetFechasCreacion().get(elementoResultadoBusquedaDTO.getFacetFechaCreacion())+1
															);			
			} else if (elementoResultadoBusquedaDTO.getFacetFechaCreacion()!=null &&
					!elementoResultadoBusquedaDTO.getFacetFechaCreacion().isEmpty()) {
				resultadoBusquedaDTO.getFacetFechasCreacion().put(
						elementoResultadoBusquedaDTO.getFacetFechaCreacion(), 
						1
						);	
			}
		}
	}
	
	@Override
	public void cargaFacet(ResultadoBusquedaDTO resultadoBusquedaDTO, CargaFacetDTO cargaFacetDTO, Usuario usuario) throws SgdpException {
		RespuestaCargaFacetDTO respuestaCargaFacetDTO = cargaFacet(cargaFacetDTO, usuario);
		resultadoBusquedaDTO.setFacetProcesos(respuestaCargaFacetDTO.getFacetProcesos());
		resultadoBusquedaDTO.setFacetSubProcesos(respuestaCargaFacetDTO.getFacetSubProcesos());
		resultadoBusquedaDTO.setFacetMaterias(respuestaCargaFacetDTO.getFacetMaterias());
		resultadoBusquedaDTO.setFacetAutores(respuestaCargaFacetDTO.getFacetAutores());
		resultadoBusquedaDTO.setFacetCreadores(respuestaCargaFacetDTO.getFacetCreadores());
	}
	
	@Override
	public void filtrarResultado(BuscarDTO buscarDTO, ResultadoBusquedaDTO resultadoBusquedaDTO, 
			List<ElementoResultadoBusquedaDTO> elementosResultadoBusquedaDTOFiltro) 
			throws SgdpException {
		try {
			
			for (ElementoResultadoBusquedaDTO elementoResultadoBusquedaDTO: resultadoBusquedaDTO.getElementosResultadoBusquedaDTO()) {				
				log.debug(elementoResultadoBusquedaDTO.toString());
				for (String filtroBusqueda : buscarDTO.getFiltrosBusqueda()) {	
					log.debug("filtroBusqueda: " + filtroBusqueda);	
					log.debug("Integer.toString(filtroDeBusquedaTypeNombre.getCodigoFiltroDeBusquedaType()): " + Integer.toString(filtroDeBusquedaTypeNombre.getCodigoFiltroDeBusquedaType()));
					log.debug("Integer.toString(filtroDeBusquedaTypeProceso.getCodigoFiltroDeBusquedaType()): " + Integer.toString(filtroDeBusquedaTypeSubProceso.getCodigoFiltroDeBusquedaType()));
					log.debug("Integer.toString(filtroDeBusquedaTypeMateria.getCodigoFiltroDeBusquedaType()): " + Integer.toString(filtroDeBusquedaTypeMateria.getCodigoFiltroDeBusquedaType()));
					log.debug("Integer.toString(filtroDeBusquedaTypeAutor.getCodigoFiltroDeBusquedaType()): " + Integer.toString(filtroDeBusquedaTypeAutor.getCodigoFiltroDeBusquedaType()));
					
					if (filtroBusqueda.equals(Integer.toString(filtroDeBusquedaTypeNombre.getCodigoFiltroDeBusquedaType()))) {
						log.debug("Filtrando por nombre");
						log.debug("buscarDTO.getPalabraClave().toUpperCase(): " + buscarDTO.getPalabraClave().toUpperCase());
						log.debug("elementoResultadoBusquedaDTO.getNombreDeObjeto(): " + elementoResultadoBusquedaDTO.getNombreDeObjeto());
						boolean contiene = elementoResultadoBusquedaDTO.getNombreDeObjeto().toUpperCase().contains(buscarDTO.getPalabraClave().toUpperCase());
						log.debug("contiene: " + contiene);
						if (contiene == true) {
							elementosResultadoBusquedaDTOFiltro.add(elementoResultadoBusquedaDTO);
						}					
					}
					if (filtroBusqueda.equals(Integer.toString(filtroDeBusquedaTypeSubProceso.getCodigoFiltroDeBusquedaType()))) {
						log.debug("Filtrando por subProceso");
						log.debug("buscarDTO.getPalabraClave().toUpperCase(): " + buscarDTO.getPalabraClave().toUpperCase());
						log.debug("elementoResultadoBusquedaDTO.getProceso(): " + elementoResultadoBusquedaDTO.getProceso());
						boolean contiene = elementoResultadoBusquedaDTO.getSubProceso().toUpperCase().contains(buscarDTO.getPalabraClave().toUpperCase()); 
												
						if (contiene == true) {
							elementosResultadoBusquedaDTOFiltro.add(elementoResultadoBusquedaDTO);
						}					
					}
					if (filtroBusqueda.equals(Integer.toString(filtroDeBusquedaTypeMateria.getCodigoFiltroDeBusquedaType()))) {
						log.debug("Filtrando por materia");
						log.debug("buscarDTO.getPalabraClave().toUpperCase(): " + buscarDTO.getPalabraClave().toUpperCase());
						log.debug("elementoResultadoBusquedaDTO.getMateria(): " + elementoResultadoBusquedaDTO.getMateria());
						boolean contiene = elementoResultadoBusquedaDTO.getMateria().toUpperCase().contains(buscarDTO.getPalabraClave().toUpperCase());
						log.debug("contiene: " + contiene);
						if (contiene == true) {
							elementosResultadoBusquedaDTOFiltro.add(elementoResultadoBusquedaDTO);
						}					
					}
					if (filtroBusqueda.equals(Integer.toString(filtroDeBusquedaTypeAutor.getCodigoFiltroDeBusquedaType()))) {
						log.debug("Filtrando por autor");
						log.debug("buscarDTO.getPalabraClave().toUpperCase(): " + buscarDTO.getPalabraClave().toUpperCase());
						log.debug("elementoResultadoBusquedaDTO.getAutor(): " + elementoResultadoBusquedaDTO.getAutor());
						boolean contiene = elementoResultadoBusquedaDTO.getAutor().toUpperCase().contains(buscarDTO.getPalabraClave().toUpperCase());
						log.debug("contiene: " + contiene);
						if (contiene == true) {
							elementosResultadoBusquedaDTOFiltro.add(elementoResultadoBusquedaDTO);
						}					
					}
				}
			}
			
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new SgdpException(configProps.getProperty("errorAlFiltrarResultadoDeBusqueda"));
		}
		
	}
	
	private void cargarResultadoBusquedaDTO(ResultadoBusquedaDTO resultadoBusquedaDTO, ResultadoBusquedaResponse resultadoBusquedaResponse) {
		log.debug("Inicio cargarResultadoBusquedaDTO");
		resultadoBusquedaDTO.setTotal(resultadoBusquedaResponse.getTotal());
		resultadoBusquedaDTO.setTotalArchivos(resultadoBusquedaResponse.getTotalArchivos());
		resultadoBusquedaDTO.setTotalCarpetas(resultadoBusquedaResponse.getTotalCarpetas());
		List<ElementoResultadoBusquedaDTO> elementosResultadoBusquedaDTO = new ArrayList<ElementoResultadoBusquedaDTO>();
		for (ElementoBuscar elementoBuscar : resultadoBusquedaResponse.getListaDeArchivos()) {			
			ElementoResultadoBusquedaDTO elementoResultadoBusquedaDTO = new ElementoResultadoBusquedaDTO();			
			cargarElementoResultadoBusquedaDTO(elementoResultadoBusquedaDTO, elementoBuscar);			
			elementosResultadoBusquedaDTO.add(elementoResultadoBusquedaDTO);
		}
		resultadoBusquedaDTO.setElementosResultadoBusquedaDTO(elementosResultadoBusquedaDTO);
	}	
	
	private void cargarElementoResultadoBusquedaDTO(ElementoResultadoBusquedaDTO elementoResultadoBusquedaDTO, ElementoBuscar elementoBuscar) {
		log.debug("Inicio cargarElementoResultadoBusquedaDTO");
		List<ExpedienteContieneDTO> expedientesQueContieneDTO = new ArrayList<ExpedienteContieneDTO>();
		for (ExpedienteContiene expedienteContiene : elementoBuscar.getExpedientesContiene()) {
			ExpedienteContieneDTO expedienteContieneDTO = new ExpedienteContieneDTO();
			expedienteContieneDTO.setIdExpedienteContiene(expedienteContiene.getIdExpedienteContiene());
			expedienteContieneDTO.setNombreDeExpedienteContiene(expedienteContiene.getNombreDeExpedienteContiene());
			expedientesQueContieneDTO.add(expedienteContieneDTO);
		}
		elementoResultadoBusquedaDTO.setExpedientesQueContiene(expedientesQueContieneDTO);
		elementoResultadoBusquedaDTO.setAutor(elementoBuscar.getAutor());
		elementoResultadoBusquedaDTO.setCartaRelacionada(elementoBuscar.getCartaRelacionada());
		elementoResultadoBusquedaDTO.setCdr(elementoBuscar.getCdr());
		elementoResultadoBusquedaDTO.setCreador(elementoBuscar.getCreador());
		elementoResultadoBusquedaDTO.setEmisor(elementoBuscar.getEmisor());
		elementoResultadoBusquedaDTO.setFechaDeCreacion(elementoBuscar.getFechaDeCreacion());
		elementoResultadoBusquedaDTO.setIdObjeto(elementoBuscar.getIdObjeto());
		elementoResultadoBusquedaDTO.setIdExpedienteQueLoContiene(elementoBuscar.getIdExpedienteQueLoContiene());
		elementoResultadoBusquedaDTO.setMateria(elementoBuscar.getMateria());
		elementoResultadoBusquedaDTO.setNombreDeObjeto(elementoBuscar.getNombreDeObjeto());
		elementoResultadoBusquedaDTO.setNombreExpedienteQueLoContiene(elementoBuscar.getNombreExpedienteQueLoContiene());
		elementoResultadoBusquedaDTO.setNumeroDeDocumento(elementoBuscar.getNumeroDeDocumento());
		elementoResultadoBusquedaDTO.setOtro(elementoBuscar.getOtro());
		elementoResultadoBusquedaDTO.setPerspectiva(elementoBuscar.getPerspectiva());
		elementoResultadoBusquedaDTO.setProceso(elementoBuscar.getProceso());
		elementoResultadoBusquedaDTO.setSubProceso(elementoBuscar.getSubproceso());
		elementoResultadoBusquedaDTO.setTipoDeDocumento(elementoBuscar.getTipoDeDocumento());
		elementoResultadoBusquedaDTO.setTipoObjeto(elementoBuscar.getTipoObjeto());
		elementoResultadoBusquedaDTO.setFacetFechaCreacion(getFacetFechaTypeNombre(elementoResultadoBusquedaDTO.getFechaDeCreacion()));
		elementoResultadoBusquedaDTO.setLinkDescargaNavegador(elementoBuscar.getLinkDescargaNavegador());
		elementoResultadoBusquedaDTO.setEsConfidencial(elementoBuscar.getEsConfidencial());
		elementoResultadoBusquedaDTO.setUsuariosQueHanModificado(elementoBuscar.getUsuariosQueHanModificado());
		String[] usuariosQueHanParticipado = elementoBuscar.getUsuariosQueHanParticipado().split(",");
		List<String> usuariosQueHanParticipadoList = new ArrayList<String>();
		for (String s : usuariosQueHanParticipado) {
			usuariosQueHanParticipadoList.add(s);
		}
		elementoResultadoBusquedaDTO.setUsuariosQueHanParticipado(usuariosQueHanParticipadoList);
	}
	
	private String getFacetFechaTypeNombre(String fecha) {
		if (fecha==null || fecha.isEmpty()) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Calendar calFecha = Calendar.getInstance();
		Calendar calNow = Calendar.getInstance();
		try {
			Date fechaD = sdf.parse(fecha);
			calFecha.setTime(fechaD);
			
			calNow.setTime(new Date());
						
			if (calFecha.get(Calendar.YEAR) == calNow.get(Calendar.YEAR) 
					&& calFecha.get(Calendar.MONTH) == calNow.get(Calendar.MONTH) 
					&& calFecha.get(Calendar.DAY_OF_MONTH) == calNow.get(Calendar.DAY_OF_MONTH)) {
			   return FacetFechaType.HOY.getNombreFacetFecha();
			   
			} else if (calFecha.get(Calendar.YEAR) == calNow.get(Calendar.YEAR) 
					&& calFecha.get(Calendar.WEEK_OF_YEAR) == calNow.get(Calendar.WEEK_OF_YEAR)) {
				return FacetFechaType.ESTA_SEMANA.getNombreFacetFecha();
				
			} else if (calFecha.get(Calendar.YEAR) == calNow.get(Calendar.YEAR) 
					&& calFecha.get(Calendar.MONTH) == calNow.get(Calendar.MONTH)) {
				return FacetFechaType.ESTE_MES.getNombreFacetFecha();
				
			} else if (calFecha.get(Calendar.YEAR) == calNow.get(Calendar.YEAR)) {				
				return FacetFechaType.ESTE_YEAR.getNombreFacetFecha();
			} else {
				return FacetFechaType.MAYOR_A_UN_YEAR.getNombreFacetFecha();
			}
			
		} catch (ParseException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			return null;
		}
		
	}
	
	@Override
	@Transactional
	public void filtraPorBandejaDeSalida(ResultadoBusquedaDTO resultadoBusquedaDTO, Usuario usuario) {		
		log.debug("Inicio filtraPorBandejaDeSalida");
		List<InstanciaDeTarea> instanciaDeTareas;
		Iterator<ElementoResultadoBusquedaDTO> it = resultadoBusquedaDTO.getElementosResultadoBusquedaDTO().iterator();	
		while (it.hasNext()) {			
			ElementoResultadoBusquedaDTO elementoResultadoBusquedaDTO = it.next();
			log.debug(elementoResultadoBusquedaDTO.toString());
			try {
				if (elementoResultadoBusquedaDTO.getTipoObjeto().equals("Expediente")) {
					instanciaDeTareas = instanciaDeTareaDao.getInstanciasDeTareasPorIdExpedienteUsuarioFinalizada(elementoResultadoBusquedaDTO.getIdObjeto(), usuario.getIdUsuario());
				} else {
					instanciaDeTareas = instanciaDeTareaDao.getInstanciasDeTareasPorIdExpedienteUsuarioFinalizada(elementoResultadoBusquedaDTO.getIdExpedienteQueLoContiene(), usuario.getIdUsuario());
				}				
				if (instanciaDeTareas == null) {
					log.debug("instanciaDeTareas == null");
					it.remove();
				} else if (instanciaDeTareas != null && instanciaDeTareas.isEmpty()) {
					log.debug("instanciaDeTareas != null && instanciaDeTareas.isEmpty()");
					it.remove();
				}
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
				throw e;
			}			 
		}
	}
	
	@Override
	public void filtraPorConfidencialidad(ResultadoBusquedaDTO resultadoBusquedaDTO, Usuario usuario) {
		log.debug("Inicio filtraPorConfidencialidad");
		Iterator<ElementoResultadoBusquedaDTO> it = resultadoBusquedaDTO.getElementosResultadoBusquedaDTO().iterator();
		while (it.hasNext()) {
			ElementoResultadoBusquedaDTO elementoResultadoBusquedaDTO = it.next();
			log.debug(elementoResultadoBusquedaDTO.toString());
			log.debug("usuario.getIdUsuario(): " + usuario.getIdUsuario());
			
			log.debug("elementoResultadoBusquedaDTO.getEsConfidencial()!=null: " + elementoResultadoBusquedaDTO.getEsConfidencial()!=null);
			log.debug("elementoResultadoBusquedaDTO.getEsConfidencial().equals(\"true\") : " + elementoResultadoBusquedaDTO.getEsConfidencial().equals("true") );
			log.debug("elementoResultadoBusquedaDTO.getUsuariosQueHanModificado().contains(usuario.getIdUsuario()): " + elementoResultadoBusquedaDTO.getUsuariosQueHanModificado().contains(usuario.getIdUsuario()));
			log.debug("elementoResultadoBusquedaDTO.getUsuariosQueHanParticipado().contains(usuario.getIdUsuario()): " + elementoResultadoBusquedaDTO.getUsuariosQueHanParticipado().contains(usuario.getIdUsuario()));
						
			if (elementoResultadoBusquedaDTO.getEsConfidencial()!=null 
					&& elementoResultadoBusquedaDTO.getEsConfidencial().equals("true") 
					&& !elementoResultadoBusquedaDTO.getUsuariosQueHanModificado().contains(usuario.getIdUsuario())
					&& !elementoResultadoBusquedaDTO.getUsuariosQueHanParticipado().contains(usuario.getIdUsuario())
					) {	
				log.debug("Removiendo elemento");
				it.remove();
			}
		}
	}
	
	@Transactional
	@Override
	public void filtrarResultadoDocumentosOficiales(ResultadoBusquedaDTO resultadoBusquedaDTO) {
		
		List<ElementoResultadoBusquedaDTO> listaElementoResultadoBusquedaDTO = new ArrayList<ElementoResultadoBusquedaDTO>();
		
		
		for (ElementoResultadoBusquedaDTO elementoResultadoBusquedaDTO : resultadoBusquedaDTO.getElementosResultadoBusquedaDTO()) {
			List<ArchivosInstDeTarea> listaInstanciaTarea = archivosInstDeTareaDao.
							buscaArchivoFirmadoDeLaInstanciaDeTarea(elementoResultadoBusquedaDTO.getIdObjeto());
			
			if (listaInstanciaTarea.size() > 0 && elementoResultadoBusquedaDTO.getTipoObjeto().equals("Documento")){
				listaElementoResultadoBusquedaDTO.add(elementoResultadoBusquedaDTO);
			  }
			}
		
		resultadoBusquedaDTO.getElementosResultadoBusquedaDTO().clear();
		resultadoBusquedaDTO.getElementosResultadoBusquedaDTO().addAll(listaElementoResultadoBusquedaDTO);
		
	}

	
	@Transactional
	@Override
	public List<String> buscaTodosLosNombreDeLosDocumentos() {
		return tipoDeDocumentoDao.buscaTodosLosNombreDeLosDocumentos();
	}
	
	@Transactional
	@Override
	public List<String> buscaTodosLosNombreDeLosDocumentosSubidos() {
		return tipoDeDocumentoDao.buscaTodosLosNombreDeLosDocumentosSubidos();
	}

	@Transactional
	@Override
	public List<String> getBuscarTodosLosNombresdeLosProcesoVigente() {
		return procesoDao.getBuscarTodosLosNombresdeLosProcesoVigente(true);
	}	
	
	@Override
	public void filtraPorConfidencialidadRestringida(ResultadoBusquedaDTO resultadoBusquedaDTO, Usuario usuario) {
        log.debug("Inicio filtraPorConfidencialidadRestringida");
        Iterator<ElementoResultadoBusquedaDTO> it = resultadoBusquedaDTO.getElementosResultadoBusquedaDTO().iterator();
        while (it.hasNext()) {
               ElementoResultadoBusquedaDTO elementoResultadoBusquedaDTO = it.next();
               log.debug(elementoResultadoBusquedaDTO.toString());
               log.debug("usuario.getIdUsuario(): " + usuario.getIdUsuario());               
               log.debug("elementoResultadoBusquedaDTO.getEsConfidencial()!=null: " + elementoResultadoBusquedaDTO.getEsConfidencial()!=null);
               log.debug("elementoResultadoBusquedaDTO.getEsConfidencial().equals(\"restringido\") : " + elementoResultadoBusquedaDTO.getEsConfidencial().equals("restringido") );
        log.debug("elementoResultadoBusquedaDTO.getUsuariosQueHanModificado().contains(usuario.getIdUsuario()): " + elementoResultadoBusquedaDTO.getUsuariosQueHanModificado().contains(usuario.getIdUsuario()));
        log.debug("elementoResultadoBusquedaDTO.getUsuariosQueHanParticipado().contains(usuario.getIdUsuario()): " + elementoResultadoBusquedaDTO.getUsuariosQueHanParticipado().contains(usuario.getIdUsuario()));
                                   
               if (elementoResultadoBusquedaDTO.getEsConfidencial()!=null 
                             && elementoResultadoBusquedaDTO.getEsConfidencial().equals("restringido") 
                             && !elementoResultadoBusquedaDTO.getUsuariosQueHanModificado().contains(usuario.getIdUsuario())
                             && !elementoResultadoBusquedaDTO.getUsuariosQueHanParticipado().contains(usuario.getIdUsuario())
                             ) {    
                      log.debug("Removiendo elemento");
                      it.remove();
               }
        }
	}

	@Override
	public ResultadoBusquedaDTO buscarConFiltro(BuscarConFiltroDTO buscarConFiltroDTO, Usuario usuario) throws SgdpException {
		log.debug("Inicio buscarConFiltro");
		ResultadoBusquedaDTO resultadoBusquedaDTO = new ResultadoBusquedaDTO();
		try {
			ResultadoBusquedaResponse resultadoBusquedaResponse = busquedaCMSService.buscarConFiltro(buscarConFiltroDTO, usuario);
			cargarResultadoBusquedaDTO(resultadoBusquedaDTO, resultadoBusquedaResponse);
			/*if (buscarDTO.getSoloEnMiBandejaDeSalida()==true) {
				filtraPorBandejaDeSalida(resultadoBusquedaDTO, usuario);
			}*/
			return resultadoBusquedaDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new SgdpException(configProps.getProperty("errorAlCargarFormularioDeBusqueda"));
		}
	}
	
	@Override
	public RespuestaCargaFacetDTO cargaFacet(CargaFacetDTO cargaFacetDTO, Usuario usuario) throws SgdpException {
		log.debug("Inicio cargaFacet");
		try {
			RespuestaCargaFacetDTO respuestaCargaFacetDTO = busquedaCMSService.cargaFacet(cargaFacetDTO, usuario);
			return respuestaCargaFacetDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new SgdpException(configProps.getProperty("errorAlCargarFacet"));
		}
	}

	
	
	@Override
	public ResultadoBusquedaDTO buscarRegistrosPaginados(DataTableRequestDTO dataTableInput,BuscarDTO buscarDTO, Usuario usuario) throws SgdpException {
		log.debug("Inicio buscarConFiltro");
		ResultadoBusquedaDTO resultadoBusquedaDTO = new ResultadoBusquedaDTO();
		try {
			ResultadoBusquedaResponse resultadoBusquedaResponse = busquedaCMSService.buscarRegistrosPaginados(dataTableInput,buscarDTO, usuario);
			cargarResultadoBusquedaDTO(resultadoBusquedaDTO, resultadoBusquedaResponse);			
			return resultadoBusquedaDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new SgdpException(configProps.getProperty("errorAlCargarFormularioDeBusqueda"));
		}
	}

	@Override
	public void ordenaFacet(ResultadoBusquedaDTO resultadoBusquedaDTO) {
		
		if (resultadoBusquedaDTO.getFacetProcesos()!=null && !resultadoBusquedaDTO.getFacetProcesos().isEmpty()) {
			resultadoBusquedaDTO.setFacetProcesos(AppUtil.sortByComparator(resultadoBusquedaDTO.getFacetProcesos(), AppUtil.DESC));
		}
		
		if (resultadoBusquedaDTO.getFacetSubProcesos()!=null && !resultadoBusquedaDTO.getFacetSubProcesos().isEmpty()) {
			resultadoBusquedaDTO.setFacetSubProcesos(AppUtil.sortByComparator(resultadoBusquedaDTO.getFacetSubProcesos(), AppUtil.DESC));
		}
			
		if (resultadoBusquedaDTO.getFacetAutores()!=null && !resultadoBusquedaDTO.getFacetAutores().isEmpty()) {
			resultadoBusquedaDTO.setFacetAutores(AppUtil.sortByComparator(resultadoBusquedaDTO.getFacetAutores(), AppUtil.DESC));
		}
		
		if (resultadoBusquedaDTO.getFacetCreadores()!=null && !resultadoBusquedaDTO.getFacetCreadores().isEmpty()) {
			resultadoBusquedaDTO.setFacetCreadores(AppUtil.sortByComparator(resultadoBusquedaDTO.getFacetCreadores(), AppUtil.DESC));
		}
		
		if (resultadoBusquedaDTO.getFacetMaterias()!=null && !resultadoBusquedaDTO.getFacetMaterias().isEmpty()) {
			resultadoBusquedaDTO.setFacetMaterias(AppUtil.sortByComparator(resultadoBusquedaDTO.getFacetMaterias(), AppUtil.DESC));
		}
		
		if (resultadoBusquedaDTO.getFacetFechasCreacion()!=null && !resultadoBusquedaDTO.getFacetFechasCreacion().isEmpty()) {
			resultadoBusquedaDTO.setFacetFechasCreacion(AppUtil.sortByComparator(resultadoBusquedaDTO.getFacetFechasCreacion(), AppUtil.DESC));
		}
		
	}
	
	@Transactional
	@Override
	public List<String> getTodosLosNombresDeProcesos() {
		return procesoDao.getTodosLosNombresDeProcesos();
	}


}
