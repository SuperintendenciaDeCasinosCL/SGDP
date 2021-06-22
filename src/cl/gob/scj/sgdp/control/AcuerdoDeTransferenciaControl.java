package cl.gob.scj.sgdp.control;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.BuscarAcuerdoDTO;
import cl.gob.scj.sgdp.dto.MensajeVistaDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaAcuerdoDTO;
import cl.gob.scj.sgdp.dto.SerieDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
import cl.gob.scj.sgdp.service.AcuerdoDeTransferenciaService;
import cl.gob.scj.sgdp.service.SerieService;

@Controller
public class AcuerdoDeTransferenciaControl {		

	private static final Logger log = Logger.getLogger(AcuerdoDeTransferenciaControl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private AcuerdoDeTransferenciaService acuerdoDeTransferenciaService;
	
	@Autowired
	private SerieService serieService;
	
	@RequestMapping("/acuerdoDeTransferencia")	
	public String cargaBandejaDeEntrada(Model model, HttpServletRequest request) {
					
		log.debug("Inicio carga acuerdo de transferencia");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");			
		List<SerieDTO> seriesDTO;
		try {
			seriesDTO = this.serieService.getSeries();
			model.addAttribute("seriesDTO", seriesDTO);
		} catch (ArchivoNacionalException anex) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			mensajeVistaDTO.getMensajes().put(anex.getMessage(), configProps.getProperty("cssError"));
			model.addAttribute("mensajeVistaDTO", mensajeVistaDTO);
		}
		
		model.addAttribute("permisos", usuario.getPermisos());
		
		log.debug("Fin carga acuerdo de transferencia");
		return this.configProps.getProperty("vistaAcuerdoTransferencia");
	}	
		
	
	@RequestMapping("/getResultadoBusquedaAcuerdo")
	public String getResultadoDeBusqueda(@ModelAttribute("buscarAcuerdoDTO") BuscarAcuerdoDTO buscarDTO, Model model,
			HttpServletRequest request) throws UnsupportedEncodingException {

		log.debug("Inicio getResultadoBusquedaAcuerdo");
		log.debug(buscarDTO.toString());
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		model.addAttribute("permisos", usuario.getPermisos());
		ResultadoBusquedaAcuerdoDTO resultadoBusquedaAcuerdoDTO;
		try {
			resultadoBusquedaAcuerdoDTO = this.acuerdoDeTransferenciaService.getResultadoBusquedaAcuerdo(buscarDTO, true);
			model.addAttribute("resultadoBusquedaAcuerdoDTO", resultadoBusquedaAcuerdoDTO);
		} catch (ArchivoNacionalException anex) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			mensajeVistaDTO.getMensajes().put(anex.getMessage(), configProps.getProperty("cssError"));
			model.addAttribute("mensajeVistaDTO", mensajeVistaDTO);
		}
		

		return configProps.getProperty("vistaResultadoDeBusquedaAcuerdo");

	}
	
}
