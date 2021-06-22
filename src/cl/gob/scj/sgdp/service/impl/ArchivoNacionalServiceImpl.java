package cl.gob.scj.sgdp.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.AcuerdoTransferenciaDTO;
import cl.gob.scj.sgdp.dto.ArchivoExpedienteDTO;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.BuscarAcuerdoDTO;
import cl.gob.scj.sgdp.dto.BuscarEnvioArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.BuscarTablaRetencionDTO;
import cl.gob.scj.sgdp.dto.CantidadArchivosDTO;
import cl.gob.scj.sgdp.dto.CargaDTO;
import cl.gob.scj.sgdp.dto.CargaMensajeDTO;
import cl.gob.scj.sgdp.dto.ConfiguracionArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.DocumentoDTO;
import cl.gob.scj.sgdp.dto.EnviarArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.ExpedienteArchNacDTO;
import cl.gob.scj.sgdp.dto.ExtensionDTO;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.RespuestaEnviarArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.RespuestaEnvioArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.RespuestaLoginArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.SerieDTO;
import cl.gob.scj.sgdp.dto.TablaRetencionDTO;
import cl.gob.scj.sgdp.dto.TokenDTO;
import cl.gob.scj.sgdp.dto.TransferenciaArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.UuidCarpeta;
import cl.gob.scj.sgdp.dto.rest.ErrorInicioTransferenciaDTO;
import cl.gob.scj.sgdp.dto.rest.InicioTransferenciaResponse;
import cl.gob.scj.sgdp.enums.EstadoAcuerdoArchivoNacionalEnum;
import cl.gob.scj.sgdp.enums.EstadoDocEnvioArchivoNacionaEnum;
import cl.gob.scj.sgdp.enums.EstadoProcesoEnum;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
//import cl.gob.scj.sgdp.model.Carga;
import cl.gob.scj.sgdp.service.ArchivoNacionalService;
import cl.gob.scj.sgdp.service.ArchivosInstDeTareaService;
import cl.gob.scj.sgdp.service.CargaService;
//import cl.gob.scj.sgdp.service.CargaService;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;
import cl.gob.scj.sgdp.service.ParametroArchivoNacionalService;
import cl.gob.scj.sgdp.service.ProcesoService;
import cl.gob.scj.sgdp.util.FileUtil;
import cl.gob.scj.sgdp.util.SingleObjectFactory;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.AutenticacionService;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.GestorDeDocumentosCMSService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ArchivoNacionalServiceImpl implements ArchivoNacionalService {

	private static final Logger log = Logger.getLogger(ArchivoNacionalServiceImpl.class);

	@Resource(name = "configProps")
	private Properties configProps;

	@Autowired
	private ParametroArchivoNacionalService parametroArchivoNacionalService;

	@Autowired
	private ArchivosInstDeTareaService archivosInstDeTareaService;

	@Autowired
	private ProcesoService procesoService;

	@Autowired
	private CargaService cargaService;

	@Autowired
	private InstanciaDeProcesoService instanciaDeProcesoService;

	@Autowired
	private AutenticacionService autenticacionService;

	@Autowired
	private GestorDeDocumentosCMSService gestorDeDocumentosCMSService;
	
	@Override
	public TokenDTO login() throws ArchivoNacionalException {
		TokenDTO token = null;
		log.info("Inicio login");
		try {
			ConfiguracionArchivoNacionalDTO config = this.parametroArchivoNacionalService
					.getConfiguracionLoginArchivoNacional();
			RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", this.configProps.getProperty("archivoNacionalBasic"));
			HttpEntity<String> request = new HttpEntity<String>(headers);
			String url = this.configProps.getProperty("urlLoginArchivoNacional")
					.replace("{password}", config.getPassArchivo()).replace("{username}", config.getUsuarioArchivo());
			TokenDTO respuesta = restTemplate.postForObject(url, request,
					TokenDTO.class);
			if (respuesta != null) {
				token = respuesta;
			} else {
				throw new ArchivoNacionalException(
						configProps.getProperty("errorAlConeccionArchivoNacionalLogin") );
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(configProps.getProperty("errorAlConeccionArchivoNacionalLogin"));
		}
		log.info("Fin login");
		return token;
	}

	@Override
	public List<SerieDTO> getSeries(TokenDTO token) throws ArchivoNacionalException {
		log.info("Inicio getSeries");
		List<SerieDTO> list = null;
		try {
			ConfiguracionArchivoNacionalDTO config = this.parametroArchivoNacionalService
					.getConfiguracionCodigoInstitucion();
			RestTemplate restTemplate = SingleObjectFactory.getRestTemplateInstance();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "bearer " + token.getAccessToken());
			HttpEntity<String> request = new HttpEntity<String>(headers);
			String url = this.configProps.getProperty("urlSeriesArchivoNacional").replace("{idinstitucion}",
					config.getCodigoInstitucion());
			ResponseEntity<List<SerieDTO>> response = restTemplate.exchange(url, HttpMethod.GET, request,
					new ParameterizedTypeReference<List<SerieDTO>>() {
					});
			if (response != null && response.getBody() != null && !response.getBody().isEmpty()) {
				list = response.getBody();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(configProps.getProperty("errorAlComunicacionArchivoNacionalSeries"));
		}
		log.info("Fin getSeries");
		return list;
	}

	@Override
	public List<AcuerdoTransferenciaDTO> getAcuerdosDeTransferencia(TokenDTO token, BuscarAcuerdoDTO buscarDTO)
			throws ArchivoNacionalException {
		log.info("Inicio getAcuerdosDeTransferencia");
		List<AcuerdoTransferenciaDTO> list = null;
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "bearer " + token.getAccessToken());
			HttpEntity<String> request = new HttpEntity<String>(headers);
			String url = this.configProps.getProperty("urlAcuerdoTransferenciaArchivoNacional").replace("{idserie}",
					buscarDTO.getCodSerie());
			ResponseEntity<List<AcuerdoTransferenciaDTO>> response = SingleObjectFactory.getRestTemplateInstance()
					.exchange(url, HttpMethod.GET, request,
							new ParameterizedTypeReference<List<AcuerdoTransferenciaDTO>>() {
							});
			if (response != null && response.getBody() != null && !response.getBody().isEmpty()) {
				list = response.getBody();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(configProps.getProperty("errorAlComunicacionArchivoNacionalADTS"));
		}
		log.info("Fin getAcuerdosDeTransferencia");
		return list;
	}

	@Override
	public List<TablaRetencionDTO> getResultadoDeBusquedaTablaRetencion(TokenDTO token,
			BuscarTablaRetencionDTO buscarDTO) throws ArchivoNacionalException {
		log.info("Inicio getAcuerdosDeTransferencia");
		List<TablaRetencionDTO> list = null;
		try {
			;
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "bearer " + token.getAccessToken());
			HttpEntity<String> request = new HttpEntity<String>(headers);
			String url = this.configProps.getProperty("urlTablaRetencionArchivoNacional").replace("{institucionId}",
					buscarDTO.getCodigoInstitucion());
			ResponseEntity<List<TablaRetencionDTO>> response = SingleObjectFactory.getRestTemplateInstance()
					.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<TablaRetencionDTO>>() {
					});
			if (response != null && response.getBody() != null && !response.getBody().isEmpty()) {
				list = response.getBody();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(configProps.getProperty("errorAlComunicacionArchivoNacionalTTRS"));
		}
		log.info("Fin getAcuerdosDeTransferencia");
		return list;
	}

	/**
	 * Metodo obtiene los archivos candidatos a ser enviado archivo nacional. Esta
	 * informacion se visualiza antes de enviar.
	 */
	@Override
	public RespuestaEnvioArchivoNacionalDTO getResultadoBusquedaEnvioArchivoNacional(
			BuscarEnvioArchivoNacionalDTO buscarEnvioArchivoNacionalDTO) throws ArchivoNacionalException {
		RespuestaEnvioArchivoNacionalDTO respuesta = new RespuestaEnvioArchivoNacionalDTO();
		setDataArchivoNacional(respuesta, buscarEnvioArchivoNacionalDTO);
		ProcesoDTO procesoDTO = null;
		try {
			procesoDTO =
					 this.procesoService.getProcesoByNombre(respuesta.getNombreSerie());
		} catch (Exception e) {
			throw new ArchivoNacionalException(e.getMessage());
		}

		if (procesoDTO != null) {
			// aca CDF1
			List<ArchivosInstDeTareaDTO> list = this.archivosInstDeTareaService.getArchivosInstDeTareaPorIdSerie(
					procesoDTO.getIdProceso(), EstadoProcesoEnum.FINALIZADO.getId(),
					EstadoDocEnvioArchivoNacionaEnum.NO_ENVIADO.getId(), respuesta.getFechaTransferirInicio(),
					respuesta.getFechaTransferirTermino());
			filtrarExtencionArchivos(list);
			if (!list.isEmpty()) {
				respuesta.setListArchivos(list);
				log.info("EstadoAcuerdoArchivoNacionalEnum.APROBADO_PROPUESTA.getIdStr():"+EstadoAcuerdoArchivoNacionalEnum.APROBADO_PROPUESTA.getIdStr());
				log.info("respuesta.getIdEstadoAcuerdo():"+respuesta.getIdEstadoAcuerdo());
				if (EstadoAcuerdoArchivoNacionalEnum.APROBADO_PROPUESTA.getIdStr()
						.equals(respuesta.getIdEstadoAcuerdo())) {
					int porcentaje = Integer
							.parseInt(this.configProps.getProperty("porcentajeTransferenciaAcuerdoPrueba"));
					int transferir = list.size() * porcentaje / 100;
					if (transferir <= 0) {
						transferir = 1;
					}
					respuesta.setCantidadArchivos(transferir);
				} else {
					respuesta.setCantidadArchivos(list.size());
				}
			}
		} else {
			throw new ArchivoNacionalException(this.configProps.getProperty("errorAlBuscarProcesoNoEncontrado"));
		}
		return respuesta;
	}

	/**
	 * Metodo filtra la extension de los archivos permitidos para el envio.
	 * 
	 * @param list lista de archivos.
	 */
	private void filtrarExtencionArchivos(List<ArchivosInstDeTareaDTO> list) throws ArchivoNacionalException {
		List<ArchivosInstDeTareaDTO> listAux = new ArrayList<ArchivosInstDeTareaDTO>();
		List<ExtensionDTO> listExt = this.getExtensiones();
		for (ArchivosInstDeTareaDTO archivo : list) {
			for (ExtensionDTO extensionDTO : listExt) {
				if (archivo.getNombreArchivo().contains(extensionDTO.getExtension())) {
					listAux.add(archivo);
					break;
				}
			}
		}
		list = listAux;

	}

	private List<ExtensionDTO> getExtensiones() throws ArchivoNacionalException {
		TokenDTO token = this.login();
		List<ExtensionDTO> list = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "bearer " + token.getAccessToken());
			HttpEntity<String> request = new HttpEntity<String>(headers);
			String url = this.configProps.getProperty("urlExtensionPermitdaArchivoNacional");
			ResponseEntity<List<ExtensionDTO>> response = SingleObjectFactory.getRestTemplateInstance().exchange(url,
					HttpMethod.GET, request, new ParameterizedTypeReference<List<ExtensionDTO>>() {
					});
			if (response != null && response.getBody() != null && !response.getBody().isEmpty()) {
				list = response.getBody();
			}
			return list;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(configProps.getProperty("errorAlComunicacionArchivoNacionalFormat"));
		}
	}

	private void setDataArchivoNacional(RespuestaEnvioArchivoNacionalDTO respuesta,
			BuscarEnvioArchivoNacionalDTO buscarEnvioArchivoNacionalDTO) throws ArchivoNacionalException {

		try {
			String[] dataSerie = buscarEnvioArchivoNacionalDTO.getCodSerie().split("\\|");
			String[] dataAcuerdo = buscarEnvioArchivoNacionalDTO.getCodAcuerdo().split("\\|");
			respuesta.setIdSerie(dataSerie[0]);
			respuesta.setNombreSerie(dataSerie[1]);
			respuesta.setIdEstadoAcuerdo(dataAcuerdo[0]);
			respuesta.setEstadoAcuerdo(dataAcuerdo[1]);
			respuesta.setIdAcuerdo(dataAcuerdo[2]);
			respuesta.setNombreAcuerdo(dataAcuerdo[3]);
			respuesta.setFechaTransferirInicio(dataAcuerdo[4]);
			respuesta.setFechaTransferirTermino(dataAcuerdo[5]);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(
					this.configProps.getProperty("errorAlObtenerFiltroDeBusquedaArchivoNacional"));
		}

	}

	/**
	 * Proceso de inicio de transferencia selecciona documentos a enviar archivo
	 * nacional.
	 */
	@Override
	public RespuestaEnviarArchivoNacionalDTO getInicioTransferencia(EnviarArchivoNacionalDTO enviarArchivoNacionalDTO,
			Usuario usuario) throws ArchivoNacionalException {

		RespuestaEnviarArchivoNacionalDTO respuesta = new RespuestaEnviarArchivoNacionalDTO();
		TransferenciaArchivoNacionalDTO transferencia = new TransferenciaArchivoNacionalDTO();

		ConfiguracionArchivoNacionalDTO config = this.parametroArchivoNacionalService
				.getConfiguracionCodigoInstitucion();

		List<ExtensionDTO> listExt = this.getExtensiones();

		// Lista de expdientes
		List<ExpedienteArchNacDTO> listaExpedientes = this.instanciaDeProcesoService
				.getMetadataListaExpediente(enviarArchivoNacionalDTO, usuario);

		// Filtra extension de archivos de expedientes
		filtrarExtensionExpedientes(listaExpedientes, listExt, usuario);

		// Lista de documentos
		List<DocumentoDTO> listaDocumentos = this.archivosInstDeTareaService
				.getMetadataListaDocumentos(enviarArchivoNacionalDTO);
		
		
		
		
		// Filtra extension de archivos de documentos
		filtrarExtensionDocumentos(listaDocumentos, listExt, usuario);
		
		CantidadArchivosDTO cantidad = getCantidadArchivosTransferir(enviarArchivoNacionalDTO);

		// TODO !!!!solo para pruebas descomentar 
		
		if (EstadoAcuerdoArchivoNacionalEnum.APROBADO_PROPUESTA.getIdStr()
				.equals(enviarArchivoNacionalDTO.getIdEstadoAcuerdo())) {

			listaExpedientes = setListaExpdientesPruebaTransferencia(listaExpedientes, cantidad);
			if (cantidad.getCantidadArchivosAux() < cantidad.getCantidadArchivos()) {
				listaDocumentos = setListaDocumentosPruebaTransferencia(listaDocumentos, cantidad);
			} else {
				listaDocumentos = new ArrayList<DocumentoDTO>();
			}
		}
		
		
		for (DocumentoDTO doc: listaDocumentos) {
			//log.info("DOCUMENTO ENVIADO:"+doc.getNombreArchivo()+",ID-CMS"+doc.getIdArchivoCms());
			log.info("################### DOCUMENTO ############:"+doc.getNombreArchivo());
			log.info(doc);
			log.info("##############END DOC####################");
		}
		
		for (ExpedienteArchNacDTO exp: listaExpedientes) {
			log.info("##############################Expediente:"+exp.getTituloExpediente());
			log.info(exp);
			/*List<ArchivoExpedienteDTO> listaArchivos = exp.getListaArchivos();
			for (ArchivoExpedienteDTO expSub: listaArchivos) {
				log.info("ARCHIVO de expendiente:"+expSub.getNombreArchivo()+",ID-CMS:"+expSub.getIdArchivoCms());
			}*/
			log.info("##############END EXPEDIENTE####################");
		}
		// cuando es de prueba se debe hacer un fix del volumen total del expediente
		fixVolumenExpediente(listaExpedientes);
		transferencia.setListaDocumentos(listaDocumentos);
		transferencia.setListaExpedientes(listaExpedientes);
		transferencia.setNombreAcuerdoTransferencia(enviarArchivoNacionalDTO.getNombreAcuerdo());
		transferencia.setNombreSerie(enviarArchivoNacionalDTO.getNombreSerie());
		transferencia.setCodigoAcuerdoTransferencia(enviarArchivoNacionalDTO.getIdAcuerdo());
		transferencia.setCodigoSerie(enviarArchivoNacionalDTO.getIdSerie());
		transferencia.setNombreInstitucion(config.getNombreInstitucion());
		transferencia.setIdInstitucion(Integer.parseInt(config.getCodigoInstitucion()));
		
		
		log.info("TRANSFERENCIA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		log.info(transferencia);
		log.info("FINAL TRANSFERENCIA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		// Comentado cduran para ver la entrada al servicio
		
		
		if (callServicioInicioTransferencia(transferencia, respuesta)) {
			CargaDTO carga = this.cargaService.guardarCargaArchivoNacional(enviarArchivoNacionalDTO,
					cantidad.getCantidadArchivos(), respuesta.getListaUuid().get(0).toString());

			try {
				autenticacionService.validaSesion(usuario);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
				throw new ArchivoNacionalException(this.configProps.getProperty("errorAlValidarSesionAlfresco"));
			}
			List<CargaMensajeDTO> list = setDocumentosEnviarArchivoNacional(transferencia, carga, usuario,
					cantidad.getCantidadArchivos(), respuesta.getListaUuid(), enviarArchivoNacionalDTO.getIdEstadoAcuerdo());
			this.enviarDocumentosArchivoNacional(list);
			respuesta.setMensajeError("OK");
			respuesta.setMensajeRespuesta("Mensaje OK");
		}

		return respuesta;
	}
	
	private void fixVolumenExpediente(List<ExpedienteArchNacDTO> listIn) {
		if (listIn==null || listIn.isEmpty()) {
			return;
		}
		
		for (ExpedienteArchNacDTO archNac: listIn) {
			long volumenExp = 0;
			for (ArchivoExpedienteDTO dto: archNac.getListaArchivos()) {
				volumenExp += dto.getVolumen();
			}
			archNac.setVolumenExpediente(volumenExp);
		}
	}

	/**
	 * Metodo Filtra extension permitida para los documentos
	 * 
	 * @param listaDocumentos lista de documentos
	 * @param listExt         lista de extensiones.
	 */
	private void filtrarExtensionDocumentos(List<DocumentoDTO> listaDocumentos, List<ExtensionDTO> listExt, Usuario usuario) throws ArchivoNacionalException{
		List<DocumentoDTO> listAux = new ArrayList<DocumentoDTO>();
		try {
			for (DocumentoDTO doc : listaDocumentos) {
				for (ExtensionDTO extension : listExt) {
					if (doc.getNombreArchivo().contains(extension.getExtension())) {
						byte[] byteArchivo = this.gestorDeDocumentosCMSService.getContenidoArchivo(doc.getIdArchivoCms(), usuario);
						doc.setVolumen(byteArchivo.length);
						doc.setChecksum(FileUtil.checkSumMD5(byteArchivo));
						listAux.add(doc);
						break;
					}
				}
			}
			listaDocumentos = listAux;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(this.configProps.getProperty("errorAlFiltrarArchivos"));
		}

	}

	/**
	 * Metodo filtra Expedientes con las extensiones permitidas.
	 * 
	 * @param listaExpedientes lista de expedientes.
	 * @param listExt          listas de extensiones permitdas
	 * @param usuario 
	 */
	private void filtrarExtensionExpedientes(List<ExpedienteArchNacDTO> listaExpedientes, List<ExtensionDTO> listExt, Usuario usuario) throws ArchivoNacionalException {
		List<ExpedienteArchNacDTO> listAux = new ArrayList<ExpedienteArchNacDTO>();
		try {
			for (ExpedienteArchNacDTO expediente : listaExpedientes) {
				List<ArchivoExpedienteDTO> listArchivoAux = new ArrayList<ArchivoExpedienteDTO>();
				long volumenExpediente = 0;
				for (ArchivoExpedienteDTO archivo : expediente.getListaArchivos()) {
					for (ExtensionDTO extensionDTO : listExt) {
						if (archivo.getNombreArchivo().contains(extensionDTO.getExtension())) {
							byte[] byteArchivo = this.gestorDeDocumentosCMSService.getContenidoArchivo(archivo.getIdArchivoCms(), usuario);
							archivo.setVolumen(byteArchivo.length);
							volumenExpediente = volumenExpediente + archivo.getVolumen();
							archivo.setChecksum(FileUtil.checkSumMD5(byteArchivo));
							listArchivoAux.add(archivo);
							break;
						}
					}
				}
				if (!listArchivoAux.isEmpty()) {
					expediente.setVolumenExpediente(volumenExpediente);
					expediente.setListaArchivos(listArchivoAux);
					listAux.add(expediente);
				}

			}
			listaExpedientes = listAux;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(this.configProps.getProperty("errorAlFiltrarExpedientes"));
		}

	}

	private List<CargaMensajeDTO> setDocumentosEnviarArchivoNacional(TransferenciaArchivoNacionalDTO transferencia,
			CargaDTO carga, Usuario usuario, int totalArchivos, List<UuidCarpeta> listaUuidCarpeta, String idEstadoAcuerdo) {
		List<CargaMensajeDTO> list = new ArrayList<CargaMensajeDTO>();
		CargaMensajeDTO dto = null;
		String[] archivoAux = null;
		// Recorre lista de expedientes con archivos
		for (ExpedienteArchNacDTO expediente : transferencia.getListaExpedientes()) {
			if (expediente.getListaArchivos() != null && !expediente.getListaArchivos().isEmpty()) {
				for (ArchivoExpedienteDTO archivo : expediente.getListaArchivos()) {
					dto = new CargaMensajeDTO();
					dto.setIdCarga(carga.getIdCarga());
					dto.setAlfTicket(usuario.getAlfTicket());
					dto.setIdArchivoCms(archivo.getIdArchivoCms());
					dto.setTotalArchvivos(totalArchivos);
					archivoAux = archivo.getNombreArchivo().split("/");
					String idTransferencia = "";
					if (archivoAux!= null && archivoAux.length == 3) {
						dto.setNombreArchivo(archivoAux[2]);
						String nombreCarpeta = archivoAux[1];
						dto.setCarpetaExp(nombreCarpeta);
						for (UuidCarpeta uuidCarpeta: listaUuidCarpeta) {
							if (uuidCarpeta.getCarpeta().equals(nombreCarpeta)) {
								idTransferencia = uuidCarpeta.getUuid().toString();
							}
						}
					}
					dto.setIdTransferencia(idTransferencia);
					dto.setIdEstadoAcuerdo(Long.parseLong(idEstadoAcuerdo));
					dto.setIdAcuerdo(Long.parseLong(transferencia.getCodigoAcuerdoTransferencia()));
					list.add(dto);
				}
			}
		}

		// Recorre lista de documentos con archvicos
		for (DocumentoDTO documento : transferencia.getListaDocumentos()) {
			dto = new CargaMensajeDTO();
			dto.setIdCarga(carga.getIdCarga());
			dto.setAlfTicket(usuario.getAlfTicket());
			dto.setIdArchivoCms(documento.getIdArchivoCms());
			dto.setTotalArchvivos(totalArchivos);
			archivoAux = documento.getNombreArchivo().split("/");
			String idTransferencia = "";
			if (archivoAux!= null && archivoAux.length == 3) {
				dto.setNombreArchivo(archivoAux[2]);
				//dto.setCarpetaExp(archivoAux[1]);
				String nombreCarpeta = archivoAux[1];
				dto.setCarpetaExp(nombreCarpeta);
				for (UuidCarpeta uuidCarpeta: listaUuidCarpeta) {
					if (uuidCarpeta.getCarpeta().equals(nombreCarpeta)) {
						idTransferencia = uuidCarpeta.getUuid().toString();
					}
				}
			}
			dto.setIdTransferencia(idTransferencia);
			dto.setIdEstadoAcuerdo(Long.parseLong(idEstadoAcuerdo));
			dto.setIdAcuerdo(Long.parseLong(transferencia.getCodigoAcuerdoTransferencia()));
			list.add(dto);
		}
		
		log.info("######setDocumentosEnviarArchivoNacional");
		for (CargaMensajeDTO car: list) {
			log.info(car);
		}

		return list;
	}
	// aca se envian por cola JMS
	private void enviarDocumentosArchivoNacional(List<CargaMensajeDTO> list) throws ArchivoNacionalException {
		for (CargaMensajeDTO cargaMensajeDTO : list) {
			try {
				URI uriRespuesta = new URI(this.configProps.getProperty("urlEnvioArchivoNacional"));
				log.info("urlEnvioArchivoNacional --> " + this.configProps.getProperty("urlEnvioArchivoNacional"));
				ResponseEntity<String> resultRespuesta = SingleObjectFactory.getRestTemplateInstance()
						.postForEntity(uriRespuesta, cargaMensajeDTO, String.class);
				if (resultRespuesta.getStatusCode().value() != HttpStatus.SC_OK) {
					throw new ArchivoNacionalException(this.configProps.getProperty("errorAlEnviarArchivoNacional"));
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new ArchivoNacionalException(this.configProps.getProperty("errorAlEnviarArchivoNacional"));

			}
		}

	}

	private boolean callServicioInicioTransferencia(TransferenciaArchivoNacionalDTO transferencia,
			RespuestaEnviarArchivoNacionalDTO respuesta) throws ArchivoNacionalException {
		TokenDTO token = login();
		boolean flag = false;
		try {
			String requestJson = SingleObjectFactory.getMapper().writeValueAsString(transferencia);
			HttpHeaders headers = new HttpHeaders();
			MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
			headers.setContentType(mediaType);
			headers.add("Authorization", "bearer " + token.getAccessToken());
			HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
			String url = this.configProps.getProperty("urlInicioTransferenciaArchivoNacional");

			ResponseEntity<InicioTransferenciaResponse> response = SingleObjectFactory.getRestTemplateInstance().exchange(url,
					HttpMethod.POST, entity, InicioTransferenciaResponse.class);
			if (response != null && response.getStatusCode().value() == HttpStatus.SC_OK) {
				InicioTransferenciaResponse inicioTransferencia = response.getBody();
				if (inicioTransferencia.getUuidTrans() != null) {
					flag = true;
					respuesta.setMensajeError("OK");
					respuesta.setMensajeRespuesta("Inicio de transferencia en curso");
					List<UuidCarpeta> listaUuid = inicioTransferencia.getUuidTrans();
					
					//respuesta.setIdTransferencia(inicioTransferencia.getUuidTrans().toString());
					respuesta.setListaUuid(listaUuid);
				}else {
					respuesta.setMensajeError("ERROR");
					String errores = "";
					for (ErrorInicioTransferenciaDTO error : inicioTransferencia.getErrores()) {
						errores = errores + error.getMensaje() + " <br>";
					}
					respuesta.setMensajeRespuesta(errores);
				}
			
			} else {
				respuesta.setMensajeError("ERROR");
				respuesta
						.setMensajeRespuesta(this.configProps.getProperty("errorAlInicioTransferenciaArchivoNacional"));
			}
			log.info("##### RESPUESTA SERVICIO CONTRATO#####");
			log.info("respuesta.getMensajeError:"+respuesta.getMensajeError());
			log.info("respuesta.getMensajeRespuesta:"+respuesta.getMensajeRespuesta());
			return flag;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(
					this.configProps.getProperty("errorAlConeccionArchivoNacionalInicioTransferencia"));
		}
	}

	private List<DocumentoDTO> setListaDocumentosPruebaTransferencia(List<DocumentoDTO> listaDocumentos,
			CantidadArchivosDTO cantidad) {
		List<DocumentoDTO> list = new ArrayList<DocumentoDTO>();
		int auxAproxTransferencia = cantidad.getCantidadArchivosAux();
		for (DocumentoDTO documento : listaDocumentos) {
			list.add(documento);
			auxAproxTransferencia++;
			if (auxAproxTransferencia >= cantidad.getCantidadArchivos()) {
				cantidad.setCantidadArchivosAux(auxAproxTransferencia);
				break;
			}
		}
		return list;
	}

	/**
	 * Setea nueva lista de prueba con la cantida de archivos requeridos para la
	 * prueba de transferencia.
	 * 
	 * @param listaExpedientes      lista de expedientes.
	 * @param aproxTransferencia    cantidad necesari de transferencia.
	 * @param auxAproxTransferencia auxiliar de transferencia.
	 * @return nueva lista.
	 */
	private List<ExpedienteArchNacDTO> setListaExpdientesPruebaTransferencia(
			List<ExpedienteArchNacDTO> listaExpedientes, CantidadArchivosDTO cantidad) {
		List<ExpedienteArchNacDTO> list = new ArrayList<ExpedienteArchNacDTO>();
		int auxAproxTransferencia = cantidad.getCantidadArchivosAux();
		for (ExpedienteArchNacDTO expediente : listaExpedientes) {
			List<ArchivoExpedienteDTO> archivoList = new ArrayList<ArchivoExpedienteDTO>();
			boolean flag = false;
			for (ArchivoExpedienteDTO archivo : expediente.getListaArchivos()) {
				archivoList.add(archivo);
				auxAproxTransferencia = auxAproxTransferencia + 1;
				if (auxAproxTransferencia >= cantidad.getCantidadArchivos()) {
					flag = true;
					cantidad.setCantidadArchivosAux(auxAproxTransferencia);
					break;
				}
			}
			expediente.setListaArchivos(archivoList);
			list.add(expediente);
			if (flag) {
				break;
			}

		}
		return list;
	}

	/**
	 * Metodo obtiene la cantidad de archivos a transferir de prueba para archivo
	 * nacional
	 * 
	 * @param enviarArchivoNacionalDTO datos de busqueda.
	 * @return cantidad de archivos.
	 * @throws ArchivoNacionalException Excepcion archivo nacional
	 */
	private CantidadArchivosDTO getCantidadArchivosTransferir(EnviarArchivoNacionalDTO enviarArchivoNacionalDTO)
			throws ArchivoNacionalException {
		CantidadArchivosDTO cantidaArchivosDTO = new CantidadArchivosDTO();
		try {

			BuscarEnvioArchivoNacionalDTO buscarEnvioArchivoNacionalDTO = setBuscarArchivoNacionalDTO(
					enviarArchivoNacionalDTO);
			RespuestaEnvioArchivoNacionalDTO respuesta = null;

			// Obtiene la cantidad de archivos a transferir
			respuesta = this.getResultadoBusquedaEnvioArchivoNacional(buscarEnvioArchivoNacionalDTO);

			cantidaArchivosDTO.setCantidadArchivos(respuesta.getCantidadArchivos());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(
					configProps.getProperty("errorAlObtenerPorcentajePruebaTransferenciaArchivoNacional"));
		}
		return cantidaArchivosDTO;
	}

	private BuscarEnvioArchivoNacionalDTO setBuscarArchivoNacionalDTO(EnviarArchivoNacionalDTO enviarDTO)
			throws ArchivoNacionalException {
		BuscarEnvioArchivoNacionalDTO buscarDTO = new BuscarEnvioArchivoNacionalDTO();

		try {
			buscarDTO.setCodSerie(enviarDTO.getIdSerie() + "|" + enviarDTO.getNombreSerie());
			buscarDTO.setCodAcuerdo(enviarDTO.getIdEstadoAcuerdo() + "|" + enviarDTO.getEstadoAcuerdo() + "|"
					+ enviarDTO.getIdAcuerdo() + "|" + enviarDTO.getNombreAcuerdo() + "|"
					+ enviarDTO.getFechaTransferirInicio() + "|" + enviarDTO.getFechaTransferirTermino());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(configProps.getProperty("errorAlObtenerSerieAcuerdoArchivoNacional"));
		}

		return buscarDTO;
	}

}