package cl.gob.scj.sgdp.control;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.LogDocumentoDTO;
import cl.gob.scj.sgdp.dto.ResponseDto;
import cl.gob.scj.sgdp.service.LogDocumentoService;
import cl.gob.scj.sgdp.tipos.PermisoType;
import cl.gob.scj.sgdp.util.SGDPUtil;

@Controller
public class LogDocumentoControl {
	
	private static final Logger log = Logger.getLogger(LogDocumentoControl.class);
	
	@Autowired
	private LogDocumentoService logDocumentoService;
	
	@RequestMapping(value = "logDocumento", method = RequestMethod.POST, produces = "application/json" )
	public @ResponseBody LogDocumentoDTO logDocumento(@RequestBody LogDocumentoDTO logDocumentoDTO, HttpServletRequest request) throws Exception {
		log.info(logDocumentoDTO);	
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		logDocumentoDTO.setIpLogDocumento(SGDPUtil.getClientIpAddress(request));
		return logDocumentoService.insertLogDocumento(usuario, logDocumentoDTO);
		
	}
	
	@RequestMapping(value = "logDocumentoSolicitudCreacionExpediente", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody LogDocumentoDTO logDocumentoSolicitudCreacionExpediente(@RequestBody LogDocumentoDTO logDocumentoDTO, HttpServletRequest request) throws Exception {
		log.info(logDocumentoDTO);	
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		logDocumentoDTO.setIpLogDocumento(SGDPUtil.getClientIpAddress(request));
		return logDocumentoService.insertLogDocumentoSolicitudCreacionExpediente(usuario, logDocumentoDTO);
	}
	
	
	@RequestMapping(value = "/reportLogDocumento", method = RequestMethod.GET)	
	public String reportLogDocumento(Model model, HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		String vista = "viewReportLogDocumento";
		String permisoPuedeVerLogDocumento = PermisoType.PUEDE_VER_LOG_DOCUMENTOS.getNombrePermiso();
		String permisoPuedeVerLogDocumentoUsr = usuario.getPermisos().get(permisoPuedeVerLogDocumento);
		if (permisoPuedeVerLogDocumentoUsr==null || (permisoPuedeVerLogDocumentoUsr!=null && !permisoPuedeVerLogDocumentoUsr.equals(permisoPuedeVerLogDocumento))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		}
		return vista;
	}
	
	@RequestMapping(value = "/getListLogDocumentoDataTable", method = RequestMethod.POST)	
	public @ResponseBody ResponseDto getList(
			@RequestParam Map<String,String> allRequestParams,
			Model model,
			HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		String permisoPuedeVerLogDocumento = PermisoType.PUEDE_VER_LOG_DOCUMENTOS.getNombrePermiso();
		String permisoPuedeVerLogDocumentoUsr = usuario.getPermisos().get(permisoPuedeVerLogDocumento);
		if (permisoPuedeVerLogDocumentoUsr==null || (permisoPuedeVerLogDocumentoUsr!=null && !permisoPuedeVerLogDocumentoUsr.equals(permisoPuedeVerLogDocumento))) {
			log.debug("No tiene permiso para buscar logs de documentos");
			return new ResponseDto(0, new ArrayList<LogDocumentoDTO>());
		} else {
			log.debug("Buscando logs de documentos");
			return logDocumentoService.getListWithLimits(allRequestParams);
		}
	}
	
	@RequestMapping(value = "/getLogDocumentobyId/{id}", method = RequestMethod.GET)	
	public @ResponseBody ResponseDto getById(
			@PathVariable("id")long id,
			Model model,
			HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		String permisoPuedeVerLogDocumento = PermisoType.PUEDE_VER_LOG_DOCUMENTOS.getNombrePermiso();
		String permisoPuedeVerLogDocumentoUsr = usuario.getPermisos().get(permisoPuedeVerLogDocumento);
		if (permisoPuedeVerLogDocumentoUsr==null || (permisoPuedeVerLogDocumentoUsr!=null && !permisoPuedeVerLogDocumentoUsr.equals(permisoPuedeVerLogDocumento))) {
			return new ResponseDto(0, new ArrayList<LogDocumentoDTO>());
		} else {
			return logDocumentoService.getById(id);
		}
	}


}
