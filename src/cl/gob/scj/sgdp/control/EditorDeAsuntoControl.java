package cl.gob.scj.sgdp.control;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.InstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;
import cl.gob.scj.sgdp.tipos.PermisoType;

@Controller
public class EditorDeAsuntoControl {

	@Autowired
	private InstanciaDeProcesoService instanciaDeProcesoService;
	
	private static final Logger log = Logger.getLogger(EditorDeAsuntoControl.class);

	@RequestMapping(value = "/editorDeAsunto", method = RequestMethod.GET)
	public String editorDeAsunto(Model model, HttpServletRequest request) {
		if (usuarioValido(request)==null) {
			return "error403";
		}
		return "editorDeAsunto";
	}
	

	@RequestMapping(value = "/editorDeAsunto/{expediente}", method = RequestMethod.GET)
	public @ResponseBody InstanciaDeProcesoDTO editorDeAsunto(
			@PathVariable("expediente") String expediente,
			HttpServletRequest request) {
		if (usuarioValido(request)==null) {
			return null;
		}
		return instanciaDeProcesoService.getInstanciaDeProcesoPorNombreExpediente(expediente);
	}
	
	@RequestMapping(value = "/editorDeAsunto", method = RequestMethod.PUT)
	public @ResponseBody InstanciaDeProcesoDTO actualizaAsunto(
			@RequestBody InstanciaDeProcesoDTO ip,
			HttpServletRequest request) {
		Usuario usuario = usuarioValido(request);
		if (usuario==null) {
			return null;
		}
		return instanciaDeProcesoService.actualizaAsunto(ip, usuario) ? ip: null;
	}

	private Usuario usuarioValido(HttpServletRequest request) {
		String permisoPuedeEditarAsunto = PermisoType.PUEDE_EDITAR_ASUNTO.getNombrePermiso();
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		String permisoUsrPuedeEditarAsunto = usuario.getPermisos().get(permisoPuedeEditarAsunto);
		log.debug("permisoPuedeEditarAsunto: " + permisoPuedeEditarAsunto);
		log.debug("permisoUsrPuedeEditarAsunto: " + permisoUsrPuedeEditarAsunto);
		return (permisoUsrPuedeEditarAsunto!=null && permisoUsrPuedeEditarAsunto.equals(permisoPuedeEditarAsunto)) ? usuario: null;
	}

}
