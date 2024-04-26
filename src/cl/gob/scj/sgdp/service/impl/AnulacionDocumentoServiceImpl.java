package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.HistoricoArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.HistoricoFirmaDao;
import cl.gob.scj.sgdp.dto.AnulacionDoctoDto;
import cl.gob.scj.sgdp.dto.ArchivoAnulacionDocumentosDTO;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.LogDocumentoDTO;
import cl.gob.scj.sgdp.dto.ReportFilterDTO;
import cl.gob.scj.sgdp.dto.ResponseDto;
import cl.gob.scj.sgdp.dto.rest.BorraRegistroDocumentoRequestRest;
import cl.gob.scj.sgdp.dto.rest.BorraRegistroDocumentoResponseRest;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoFirma;
import cl.gob.scj.sgdp.service.AnulacionDocumentoService;
import cl.gob.scj.sgdp.service.GestorDeDocumentosService;
import cl.gob.scj.sgdp.service.LogDocumentoService;
import cl.gob.scj.sgdp.service.ObtenerDetalleDeArchivoService;
import cl.gob.scj.sgdp.service.RegistroDocumentoService;
import cl.gob.scj.sgdp.tipos.AccionType;
import cl.gob.scj.sgdp.tipos.ModuloType;
import cl.gob.scj.sgdp.util.SGDPUtil;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AnulacionDocumentoServiceImpl implements AnulacionDocumentoService {
	
	private Logger logger = Logger.getLogger(AnulacionDocumentoServiceImpl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private ArchivosInstDeTareaDao archivosInstDeTareaDao;
	
	@Autowired
	private HistoricoArchivosInstDeTareaDao historicoArchivosInstDeTareaDao;
	
	@Autowired
	private HistoricoFirmaDao historicoFirmaDao;
	
	@Autowired
	private RegistroDocumentoService registroDocumentoService;
	
	@Autowired
	private LogDocumentoService logDocumentoService;
	
	@Autowired
	private ObtenerDetalleDeArchivoService obtenerDetalleDeArchivoService;
	
	@Autowired
	private GestorDeDocumentosService gestorDeDocumentosService;
	
	@Override
	public ResponseDto getListWithLimits(Map<String, String> allRequestParams, Usuario usuario) {
		ResponseDto response = null;
		String prefix = "anulador.documento.list.code";
		List<ArchivoAnulacionDocumentosDTO> list = new ArrayList<ArchivoAnulacionDocumentosDTO>();
		List<HistoricoFirma> listEntity = null;
		Long count = 0L;
		try {
			ReportFilterDTO reportFilterDTO = getFilters(allRequestParams);
			int validation = validateFieldsReportFilterDto(reportFilterDTO);
			if(validation == 0) {
				listEntity = historicoFirmaDao.getListArchivoByCodeExpedienteWithLimit(reportFilterDTO);
				logger.info("listEntity.sixe(): " + listEntity.size());
				for (HistoricoFirma entity : listEntity) {
					logger.debug(entity.toString());
					ArchivoAnulacionDocumentosDTO dto = new ArchivoAnulacionDocumentosDTO(entity);
					dto.setNombreArchivo(obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, dto.getIdArchivo()).getNombre());
					logger.info(dto.toString());
					list.add(dto);
				}
				count = historicoFirmaDao.getCountListArchivoByCodeExpedienteWithLimit(reportFilterDTO);
				logger.info("count: " + count);
				response = new ResponseDto(0, list, count);
			}else {
				response = new ResponseDto(validation, new ArrayList<ArchivoAnulacionDocumentosDTO>());
			}
		} catch (Exception e) {
			logger.error(SGDPUtil.getStackTrace(e));
			response = new ResponseDto(1001, new ArrayList<ArchivoAnulacionDocumentosDTO>());
		}
		String codeProperty = String.format("%s.%d", prefix, response.getCode());
		String message = configProps.getProperty(codeProperty);
		response.setMessage(message);
		return response;
	}

	private int validateFieldsReportFilterDto(ReportFilterDTO reportFilterDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	private ReportFilterDTO getFilters(Map<String, String> allRequestParams) {
		ReportFilterDTO reportfilterDTO = new ReportFilterDTO();
		String searchValue = allRequestParams.get("search[value]");
		
		String start = allRequestParams.get("start");
		String length = allRequestParams.get("length");
		
		String orderColumnNum = allRequestParams.get("order[0][column]");
		String orderDir = allRequestParams.get("order[0][dir]");
		String orderColumName = allRequestParams.get("columns["+ orderColumnNum +"][data]");
		
		reportfilterDTO.setTextFilter(searchValue);
		
		reportfilterDTO.setLength(Integer.parseInt(length));
		reportfilterDTO.setStart(Integer.parseInt(start));
		
		reportfilterDTO.setOrderColumName(orderColumName);
		reportfilterDTO.setOrderDirection(orderDir);
		return reportfilterDTO;
	}

	@Override
	public ResponseDto anulDocumentByIdFileCms(AnulacionDoctoDto anulacionDoctoDto, Usuario usuario) throws Exception {
		String nombreExpediente = null;
		String codigoDocumento = null;
		DetalleDeArchivoDTO detalleDeArchivoDTO = new DetalleDeArchivoDTO();
		ResponseDto response = new ResponseDto();
		detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, anulacionDoctoDto.getIdArchivo());
		detalleDeArchivoDTO.setIdArchivo(anulacionDoctoDto.getIdArchivo());
		detalleDeArchivoDTO.setNombre(addPrefixAnulText(detalleDeArchivoDTO.getNombre()));
		detalleDeArchivoDTO.setEsDocumentoOficial("false");
		List<ArchivosInstDeTarea> listArchivos = archivosInstDeTareaDao.getArchivosPorIdArchivo(anulacionDoctoDto.getIdArchivo());
		List<HistoricoArchivosInstDeTarea> listArchivosHistoricos = historicoArchivosInstDeTareaDao.getHistoricoDeArchivosPorIdArchivoCMS(anulacionDoctoDto.getIdArchivo());
		List<HistoricoFirma> listHistoricoFirmas = historicoFirmaDao.getHistoricoFirmaDocumentoFEAPorIdArchivo(anulacionDoctoDto.getIdArchivo());
		if (listArchivos.size()>1) {
			throw new SgdpException("ERROR: existe mas de un archivo con el mismo id registrado en la tarea");
		}
		for (ArchivosInstDeTarea archivosInstDeTarea : listArchivos) {
			archivosInstDeTarea.setNombreArchivo(addPrefixAnulText(archivosInstDeTarea.getNombreArchivo()));
			archivosInstDeTarea.setAnulado(true);
		}
		for (HistoricoArchivosInstDeTarea historicoArchivosInstDeTarea : listArchivosHistoricos) {
			historicoArchivosInstDeTarea.setNombreArchivo(addPrefixAnulText(historicoArchivosInstDeTarea.getNombreArchivo()));
			historicoArchivosInstDeTarea.setAnulado(true);
		}
		for (HistoricoFirma historicoFirma : listHistoricoFirmas) {
			historicoFirma.setAnulado(true);
		}
		nombreExpediente = listHistoricoFirmas.get(0).getInstanciaDeTarea().getInstanciaDeProceso().getNombreExpediente();
		codigoDocumento = listHistoricoFirmas.get(0).getTipoDeDocumento().getCodTipoDocumento();
		gestorDeDocumentosService.actualizaMetaDataDeDocumento(usuario, detalleDeArchivoDTO);
		if (detalleDeArchivoDTO.getNumeroDocumento()!=null && !detalleDeArchivoDTO.getNumeroDocumento().isEmpty()) {
			BorraRegistroDocumentoRequestRest borraRegistroDocumentoRequestRest = new BorraRegistroDocumentoRequestRest();
			borraRegistroDocumentoRequestRest.setExpDoc(nombreExpediente);
			borraRegistroDocumentoRequestRest.setCodTipoDoc(codigoDocumento);
			borraRegistroDocumentoRequestRest.setNumeroDoc(Long.parseLong(detalleDeArchivoDTO.getNumeroDocumento()));
			borraRegistroDocumentoRequestRest.setMotivoAnulacion(anulacionDoctoDto.getMotivo());
			borraRegistroDocumentoRequestRest.setUser(configProps.getProperty("usrRegistroDoc"));
			borraRegistroDocumentoRequestRest.setPass(configProps.getProperty("passRegistroDoc"));
			BorraRegistroDocumentoResponseRest borraRegistroDocumentoResponseREst = registroDocumentoService
					.borraRegistroDocumento(borraRegistroDocumentoRequestRest);
			logger.info(borraRegistroDocumentoResponseREst.toString());
		}
		LogDocumentoDTO logDocumentoDTO = new LogDocumentoDTO();
		logDocumentoDTO.setIdDocumentoLogDocumento(anulacionDoctoDto.getIdArchivo());
		logDocumentoDTO.setTipoOperacionLogDocumento(AccionType.ANULA.getNombreAccion());
		logDocumentoDTO.setModuloLogDocumento(ModuloType.ANULACION_DE_DOCUMENTO.getNombreModulo());
		logDocumentoDTO.setIpLogDocumento(anulacionDoctoDto.getClientIpAddress());
		logDocumentoService.insertLogDocumento(usuario, logDocumentoDTO);
		response.setMessage(configProps.getProperty("sgdp.anulacion.documento.mensajeOK") + " " + nombreExpediente);
		return response;
	}
	
	private String addPrefixAnulText(String text) {
		String response = "";
		response = configProps.getProperty("sgdp.prefijo-anulado") + "_" + text;
		return response;
	}

}
