package cl.gob.scj.sgdp.control;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.MensajeVistaDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaEstadoTransferenciaDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
import cl.gob.scj.sgdp.service.EstadoTransferenciaService;

@Controller
public class EstadoDeTransferenciaControl {		

	private static final Logger log = Logger.getLogger(EstadoDeTransferenciaControl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;

	@Autowired
	private EstadoTransferenciaService estadoTransferenciaService;
	
	@RequestMapping("/estadoTransferenciaInicio")	
	public String cargaBandejaDeEntrada(Model model, HttpServletRequest request) {
					
		log.debug("Inicio carga estado de transferencia");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");			

		
		model.addAttribute("permisos", usuario.getPermisos());
		
		log.debug("Fin carga estado de transferencia");
		return this.configProps.getProperty("vistaEstadoTransferencia");
	}	
		
	
	@RequestMapping("/getResultadoBusquedaEstadoTransferencia")
	public String getResultadoBusquedaEstadoTransferencia( Model model,
			HttpServletRequest request) throws UnsupportedEncodingException {

		log.debug("Inicio getResultadoBusquedaEstadoTransferencia");
	
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		model.addAttribute("permisos", usuario.getPermisos());
		ResultadoBusquedaEstadoTransferenciaDTO resultado;
		try {
			resultado = this.estadoTransferenciaService.getResultadoBusquedaEstadoTransferencia();
			model.addAttribute("resultadoBusquedaEstadoTransferenciaDTO", resultado);
		} catch (ArchivoNacionalException anex) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			mensajeVistaDTO.getMensajes().put(anex.getMessage(), configProps.getProperty("cssError"));
			model.addAttribute("mensajeVistaDTO", mensajeVistaDTO);
		}
		

		return this.configProps.getProperty("vistaResultadoDeBusquedaEstadoTransferencia");

	}
	
}
