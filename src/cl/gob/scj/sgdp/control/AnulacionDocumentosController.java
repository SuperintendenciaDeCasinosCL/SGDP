package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.AnulacionDoctoDto;
import cl.gob.scj.sgdp.dto.ParametroDTO;
import cl.gob.scj.sgdp.dto.ResponseDto;
import cl.gob.scj.sgdp.service.AnulacionDocumentoService;
import cl.gob.scj.sgdp.tipos.PermisoType;
import cl.gob.scj.sgdp.util.SGDPUtil;

@Controller
public class AnulacionDocumentosController {
	
	private static final Logger log = Logger.getLogger(AnulacionDocumentosController.class);

	@Autowired
	private AnulacionDocumentoService anulacionDocumentoService;
	
	@RequestMapping(value = "/anuladorDocumentos", method = RequestMethod.GET)	
	public String anulacionDocumento(Model model, HttpServletRequest request) {
		log.debug("Inicio anulacionDocumento");		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		String vista = "viewAnulacionDocumento";
		String permisoPuedeAnularDocumentos = PermisoType.PUEDE_ANULAR_DOCUMENTOS.getNombrePermiso();
		String permisoUsrPuedeAnularDocumentos = usuario.getPermisos().get(permisoPuedeAnularDocumentos);
		log.debug("permisoPuedeAnularDocumentos: " + permisoPuedeAnularDocumentos);
		log.debug("permisoUsrMantenedorParametros: " + permisoUsrPuedeAnularDocumentos);		
		if (permisoUsrPuedeAnularDocumentos==null || (permisoUsrPuedeAnularDocumentos!=null && !permisoUsrPuedeAnularDocumentos.equals(permisoPuedeAnularDocumentos))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} 
		return vista;
	}
	
	@RequestMapping(value = "/getListDocumentoDataTable", method = RequestMethod.POST)	
	public @ResponseBody ResponseDto getList(
			@RequestParam Map<String,String> allRequestParams,
			Model model,
			HttpServletRequest request) {
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		log.debug("Inicio getListDocumentoDataTable");		
		log.debug(allRequestParams);					
		String permisoPuedeAnularDocumentos = PermisoType.PUEDE_ANULAR_DOCUMENTOS.getNombrePermiso();
		String permisoUsrPuedeAnularDocumentos = usuario.getPermisos().get(permisoPuedeAnularDocumentos);
		log.debug("permisoPuedeAnularDocumentos: " + permisoPuedeAnularDocumentos);
		log.debug("permisoUsrMantenedorParametros: " + permisoUsrPuedeAnularDocumentos);		
		if (permisoUsrPuedeAnularDocumentos==null || (permisoUsrPuedeAnularDocumentos!=null && !permisoUsrPuedeAnularDocumentos.equals(permisoPuedeAnularDocumentos))) {
			ResponseDto responseDtoError = new ResponseDto();
			responseDtoError.setCode(1000);
			return responseDtoError;
		} else {
			try {
				return anulacionDocumentoService.getListWithLimits(allRequestParams, usuario);
			} catch(Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);				
				ResponseDto responseDtoError = new ResponseDto();
				responseDtoError.setCode(1000);	
				responseDtoError.setMessage(e.getMessage());
				return responseDtoError;
			}			
		}
	}
	
	@RequestMapping(value = "/anular", method = RequestMethod.POST)
	public @ResponseBody ResponseDto getList(
			@RequestBody AnulacionDoctoDto anulacionDoctoDto,
			Model model,
			HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		log.debug("Inicio anular documento");		
		log.debug(anulacionDoctoDto.toString());					
		String permisoPuedeAnularDocumentos = PermisoType.PUEDE_ANULAR_DOCUMENTOS.getNombrePermiso();
		String permisoUsrPuedeAnularDocumentos = usuario.getPermisos().get(permisoPuedeAnularDocumentos);
		log.debug("permisoPuedeAnularDocumentos: " + permisoPuedeAnularDocumentos);
		log.debug("permisoUsrMantenedorParametros: " + permisoUsrPuedeAnularDocumentos);		
		if (permisoUsrPuedeAnularDocumentos==null || (permisoUsrPuedeAnularDocumentos!=null && !permisoUsrPuedeAnularDocumentos.equals(permisoPuedeAnularDocumentos))) {
			ResponseDto responseDtoError = new ResponseDto();
			responseDtoError.setCode(1000);
			return responseDtoError;
		} else {
			try {
				anulacionDoctoDto.setClientIpAddress(SGDPUtil.getClientIpAddress(request));
				return anulacionDocumentoService.anulDocumentByIdFileCms(anulacionDoctoDto, usuario);
			} catch(Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);				
				ResponseDto responseDtoError = new ResponseDto();
				responseDtoError.setCode(1000);	
				responseDtoError.setMessage(e.getMessage());
				return responseDtoError;
			}			
		}
	}
}
