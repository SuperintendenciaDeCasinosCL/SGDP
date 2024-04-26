package cl.gob.scj.sgdp.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.InstanciaDeProcesoDTO;
import cl.gob.scj.sgdp.service.InstanciaDeProcesoService;

@Controller
public class InstanciaDeProcesoControl {
	
	private InstanciaDeProcesoService instanciaDeProcesoService;
	
	@Autowired
	public InstanciaDeProcesoControl(InstanciaDeProcesoService instanciaDeProcesoService) {
		this.instanciaDeProcesoService = instanciaDeProcesoService;
	}
	
	@RequestMapping(value = "/instanciaDeProceso/{nombreExpediente}", method = RequestMethod.GET)
	public @ResponseBody InstanciaDeProcesoDTO actualizaAsunto(
			@PathVariable("nombreExpediente") String nombreExpediente,
			HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		if (usuario==null) {
			return null;
		}
		return instanciaDeProcesoService.getInstanciaDeProcesoPorNombreExpediente(nombreExpediente);
	}
	
	
}
