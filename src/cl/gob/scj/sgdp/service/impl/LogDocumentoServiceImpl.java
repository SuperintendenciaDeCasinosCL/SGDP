package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.LogDocumentoDao;
import cl.gob.scj.sgdp.dao.SolicitudCreacionExpDao;
import cl.gob.scj.sgdp.dto.LogDocumentoDTO;
import cl.gob.scj.sgdp.dto.ReportFilterDTO;
import cl.gob.scj.sgdp.dto.ResponseDto;
import cl.gob.scj.sgdp.model.LogDocumento;
import cl.gob.scj.sgdp.model.SolicitudCreacionExp;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;
import cl.gob.scj.sgdp.service.LogDocumentoService;
import cl.gob.scj.sgdp.util.SGDPUtil;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.ObtenerDetalleDeArchivoCMSService;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.DetalleDeArchivoResponse;

@Service
@Transactional(rollbackFor = Throwable.class)
public class LogDocumentoServiceImpl implements LogDocumentoService {

	private static final Logger log = Logger.getLogger(LogDocumentoService.class);
	
	@Autowired
	private LogDocumentoDao logDocumentoDao;
	
	@Autowired
	private ObtenerDetalleDeArchivoCMSService obtenerDetalleDeArchivoCMSService;
	
	@Autowired
	private InstanciaDeProcesoService instanciaDeProcesoService;
	
	@Autowired 
	private SolicitudCreacionExpDao solicitudCreacionExpDao;

	@Resource(name = "configProps")
	private Properties configProps;

	@Override
	public ResponseDto getListWithLimits(Map<String, String> allRequestParams) {
		ResponseDto response = null;
		String prefix = "log.documento.list.code";
		List<LogDocumentoDTO> list = new ArrayList<LogDocumentoDTO>();
		try {
			ReportFilterDTO reportFilterDTO = getFilters(allRequestParams);
			log.info(reportFilterDTO.toString());
			int validation = validateFieldsReportFilterDto(reportFilterDTO);
			if (validation == 0) {
				List<LogDocumento> listEntity = logDocumentoDao.getLogDocumentoWithLimit(reportFilterDTO);
				for (LogDocumento entity : listEntity) {
					log.info(entity.toString());
					LogDocumentoDTO dto = new LogDocumentoDTO(entity);
					list.add(dto);
				}
				Long count = logDocumentoDao.getCountLogDocumento(reportFilterDTO);
				log.debug("Retornando elementos de la consulta: " + count);
				response = new ResponseDto(0, list, count);
			} else {
				log.debug("reportFilterDTO no paso la validacion -- validation : " + validation);
				response = new ResponseDto(validation, new ArrayList<LogDocumentoDTO>());
			}
		} catch (Exception e) {
			log.debug("Retorna excepcion : " + e.getMessage());
			log.error(SGDPUtil.getStackTrace(e));
			response = new ResponseDto(1001, new ArrayList<LogDocumentoDTO>());
		}
		String codeProperty = String.format("%s.%d", prefix, response.getCode());
		String message = configProps.getProperty(codeProperty);
		response.setMessage(message);
		log.info(response.toString());
		return response;
	}

	@Override
	public ResponseDto getById(long id) {
		ResponseDto response = null;
		try {
			if (id > 0) {
				LogDocumento entity = logDocumentoDao.find(id);
				LogDocumentoDTO dto = new LogDocumentoDTO(entity);
				response = new ResponseDto(0, "OK", dto);
			} else {
				response = new ResponseDto(1001, "El idetificador enviado no es positivo");
			}
		} catch (Exception e) {
			response = new ResponseDto(1000, "El al procesar la peticion");
		}
		return response;
	}

	private int validateFieldsReportFilterDto(ReportFilterDTO dto) {
		int response = 0;
		return response;
	}

	private ReportFilterDTO getFilters(Map<String, String> allRequestParams) {
		ReportFilterDTO reportfilterDTO = new ReportFilterDTO();
		String dateFrom = allRequestParams.get("dateFrom");
		String dateTo = allRequestParams.get("dateTo");
		String searchValue = allRequestParams.get("search[value]");

		String start = allRequestParams.get("start");
		String length = allRequestParams.get("length");

		String orderColumnNum = allRequestParams.get("order[0][column]");
		String orderDir = allRequestParams.get("order[0][dir]");
		String orderColumName = allRequestParams.get("columns[" + orderColumnNum + "][data]");
		if (dateFrom != null && dateFrom.length() > 0) {
			reportfilterDTO.setDateFrom(dateFrom + " 00:00:00");
		} else {
			reportfilterDTO.setDateFrom("");
		}
		if (dateFrom != null && dateFrom.length() > 0) {
			reportfilterDTO.setDateTo(dateTo + " 23:59:59");
		} else {
			reportfilterDTO.setDateTo("");
		}

		reportfilterDTO.setTextFilter(searchValue);

		reportfilterDTO.setLength(Integer.parseInt(length));
		reportfilterDTO.setStart(Integer.parseInt(start));

		reportfilterDTO.setOrderColumName(orderColumName);
		reportfilterDTO.setOrderDirection(orderDir);
		return reportfilterDTO;
	}

	@Override
	public LogDocumentoDTO insertLogDocumento(Usuario usuario, LogDocumentoDTO dto) throws Exception {
		LogDocumento e = new LogDocumento();
		DetalleDeArchivoResponse detalleDeArchivoResponse = obtenerDetalleDeArchivoCMSService.obtenerDetalleDeArchivo(usuario, dto.getIdDocumentoLogDocumento());
		e.setCodigoExpedienteLogDocumento(instanciaDeProcesoService.getInstanciaDeProcesoDTOPorIdExpediente
				(detalleDeArchivoResponse.getDetalleDeArchivoDTO().getIdExpedienteQueLoContiene()).getNombreExpediente());
		e.setIdExpedienteLogDocumento(detalleDeArchivoResponse.getDetalleDeArchivoDTO().getIdExpedienteQueLoContiene());
		e.setNombreDocumento(detalleDeArchivoResponse.getDetalleDeArchivoDTO().getNombre());
		e.setIdUsuarioLogDocumento(usuario.getIdUsuario());
		e.setNombreUsuarioLogDocumento(usuario.getNombreCompleto()!=null && !usuario.getNombreCompleto().isEmpty() 
				? usuario.getNombreCompleto() : usuario.getIdUsuario());
		e.setIpLogDocumento(dto.getIpLogDocumento());
		e.setFechaLogDocumento(new Date());
		e.setIdDocumentoLogDocumento(dto.getIdDocumentoLogDocumento());
		e.setModuloLogDocumento(dto.getModuloLogDocumento());
		e.setTipoOperacionLogDocumento(dto.getTipoOperacionLogDocumento());
		log.info(e.toString());
		logDocumentoDao.insert(e);
		return dto;
	}
	
	@Override
	public LogDocumentoDTO insertLogDocumentoSolicitudCreacionExpediente(Usuario usuario, LogDocumentoDTO dto) throws Exception {
		LogDocumento e = new LogDocumento();
		DetalleDeArchivoResponse detalleDeArchivoResponse = obtenerDetalleDeArchivoCMSService.obtenerDetalleDeArchivo(usuario, dto.getIdDocumentoLogDocumento());
		SolicitudCreacionExp solicitudCreacionExp = solicitudCreacionExpDao.find(dto.getIdSolicitudCreacionExp());
		e.setSolicitudCreacionExp(solicitudCreacionExp);
		e.setCodigoExpedienteLogDocumento(Long.toString(solicitudCreacionExp.getIdCarpeta()));
		e.setIdExpedienteLogDocumento(solicitudCreacionExp.getIdCMSCarpeta());
		e.setNombreDocumento(detalleDeArchivoResponse.getDetalleDeArchivoDTO().getNombre());
		e.setIdUsuarioLogDocumento(usuario.getIdUsuario());
		e.setNombreUsuarioLogDocumento(usuario.getNombreCompleto()!=null && !usuario.getNombreCompleto().isEmpty() 
				? usuario.getNombreCompleto() : usuario.getIdUsuario());
		e.setIpLogDocumento(dto.getIpLogDocumento());
		e.setFechaLogDocumento(new Date());
		e.setIdDocumentoLogDocumento(dto.getIdDocumentoLogDocumento());
		e.setModuloLogDocumento(dto.getModuloLogDocumento());
		e.setTipoOperacionLogDocumento(dto.getTipoOperacionLogDocumento());
		log.info(e.toString());
		logDocumentoDao.insert(e);
		return dto;
	}
	
}
