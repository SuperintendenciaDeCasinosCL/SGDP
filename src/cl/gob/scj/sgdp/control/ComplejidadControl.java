package cl.gob.scj.sgdp.control;

import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ComplejidadExpedienteDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.service.ComplejidadExpedienteService;
import cl.gob.scj.sgdp.tipos.PermisoType;
import cl.gob.scj.sgdp.util.SGDPUtil;

@Controller
public class ComplejidadControl {

	private static final Logger log = Logger.getLogger(ComplejidadControl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	private ComplejidadExpedienteService complejidadservice;
	
	@Autowired
	public ComplejidadControl(ComplejidadExpedienteService complejidadservice) {
		super();
		this.complejidadservice = complejidadservice;
	}

	@RequestMapping(value="/complejidad/{nombreExpediente}", method=RequestMethod.GET)
	public @ResponseBody ComplejidadExpedienteDTO getAllByNombreExpediente(@PathVariable String nombreExpediente, HttpServletRequest request	) { 
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		if(usuario == null ) {
			return null;
		}	
		try {
			return complejidadservice.getLastByNombreExpediente(nombreExpediente);
		} catch (Exception e) {
			log.error(SGDPUtil.getStackTrace(e));
			return null;
		}
	}

	@RequestMapping(value="/complejidad/{nombreExpediente}/{complejidad}", method=RequestMethod.POST)
	public @ResponseBody ComplejidadExpedienteDTO save(@PathVariable String nombreExpediente, @PathVariable String complejidad, HttpServletRequest request	) { 
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		if(usuario == null ) {
			return null;
		}
		try {
			String motivo = request.getParameter("motivo");
			ComplejidadExpedienteDTO c = new ComplejidadExpedienteDTO();
			c.setNombreExpediente(nombreExpediente);
			c.setComplejidad(complejidad);
			c.setMotivoComplejidad(motivo);
			c.setUsuario(usuario.getIdUsuario());
			String permisoIngresarComplejidad = PermisoType.PUEDE_INGRESAR_COMPLEJIDAD.getNombrePermiso();
			String permisoIngresarComplejidadUsr = usuario.getPermisos().get(permisoIngresarComplejidad);	
			if (permisoIngresarComplejidad==null || (permisoIngresarComplejidad!=null && !permisoIngresarComplejidad.equals(permisoIngresarComplejidadUsr))) {
				ComplejidadExpedienteDTO complejidadExpedienteDTO = new ComplejidadExpedienteDTO();
				complejidadExpedienteDTO.setMessage(configProps.getProperty("sgdp.sin-permiso-para-la-accion"));
				complejidadExpedienteDTO.setType(configProps.getProperty("sgdp.respuesta.type.warning"));
				return complejidadExpedienteDTO;
			} else {
				return complejidadservice.guardarPorNombre(usuario, c);
			}
		} catch (Exception e) {
			log.error(SGDPUtil.getStackTrace(e));
			ComplejidadExpedienteDTO complejidadExpedienteDTO = new ComplejidadExpedienteDTO();
			complejidadExpedienteDTO.setMessage(configProps.getProperty("errorAlGuardarComplejidad"));
			complejidadExpedienteDTO.setType(configProps.getProperty("sgdp.respuesta.type.danger"));
			return complejidadExpedienteDTO;
		}
	}

}
