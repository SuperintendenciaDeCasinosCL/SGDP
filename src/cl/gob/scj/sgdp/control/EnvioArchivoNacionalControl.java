package cl.gob.scj.sgdp.control;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.BuscarAcuerdoDTO;
import cl.gob.scj.sgdp.dto.BuscarEnvioArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.EnviarArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.MensajeVistaDTO;
import cl.gob.scj.sgdp.dto.RespuestaDescartarArchivoDTO;
import cl.gob.scj.sgdp.dto.RespuestaEnviarArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.RespuestaEnvioArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaAcuerdoDTO;
import cl.gob.scj.sgdp.dto.SerieDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
import cl.gob.scj.sgdp.service.AcuerdoDeTransferenciaService;
import cl.gob.scj.sgdp.service.ArchivoNacionalService;
import cl.gob.scj.sgdp.service.ArchivosInstDeTareaService;
import cl.gob.scj.sgdp.service.SerieService;
import cl.gob.scj.sgdp.service.TablaRetencionService;

@Controller
public class EnvioArchivoNacionalControl {		

	private static final Logger log = Logger.getLogger(EnvioArchivoNacionalControl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private AcuerdoDeTransferenciaService acuerdoDeTransferenciaService;
	
	@Autowired
	private TablaRetencionService tablaRetencionService;
	
	@Autowired
	private SerieService serieService;
	
	@Autowired
	private ArchivoNacionalService archivoNacionalService;
	
	@Autowired
	private ArchivosInstDeTareaService archivosInstDeTareaService;
	
	
	/**
	 * Metodo de carga la pantalla de inicio de envio archivo nacional, busca las series de una institucion.
	 * @param model model.
	 * @param request request.
	 * @return pagina de inicio envio archivo nacional.
	 */
	@RequestMapping(value = "/envioArchivoNacional")	
	public String cargaEnvioArchivoNacional(Model model, HttpServletRequest request) {
					
		log.debug("Inicio carga envio archivo nacional");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");			
		List<SerieDTO> seriesDTO;
		try {
			String codigoInstitucion = this.tablaRetencionService.getCodigoInstitucion();
			model.addAttribute("codigoInstitucion", codigoInstitucion);
			seriesDTO = this.serieService.getSeries();
			model.addAttribute("seriesDTO", seriesDTO);
		} catch (ArchivoNacionalException anex) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			mensajeVistaDTO.getMensajes().put(anex.getMessage(), configProps.getProperty("cssError"));
			model.addAttribute("mensajeVistaDTO", mensajeVistaDTO);
		}
		
		model.addAttribute("permisos", usuario.getPermisos());
		
		log.debug("Fin carga envio archivo nacional");
		return this.configProps.getProperty("vistaEnvioArchivoNacional");
	}	
		
	
	/**
	 * Metodo busca los acuerdo de transferencia dada una serie.
	 * @param idSerie codigo de la serie.
	 * @param response response.
	 * @return listas de acuerdos de transferecnias.
	 */
	@RequestMapping(value = "/getAcuerdosTransferencia/{idSerie}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResultadoBusquedaAcuerdoDTO getResultadoBusquedaAcuerdo(
			@PathVariable("idSerie") String idSerie, HttpServletResponse response) {

		log.info("Inicio avanzarEstado");
		log.info("idSerie " + idSerie);

		ResultadoBusquedaAcuerdoDTO resultadoADTS = null;
		try {
			BuscarAcuerdoDTO buscarDTO = new BuscarAcuerdoDTO();
			buscarDTO.setCodSerie(idSerie);
			resultadoADTS = this.acuerdoDeTransferenciaService.getResultadoBusquedaAcuerdo(buscarDTO, false);

			return resultadoADTS;
		} catch (ArchivoNacionalException sgdpe) {
			resultadoADTS = new ResultadoBusquedaAcuerdoDTO();
			resultadoADTS.setMensajeError("ERROR");
			resultadoADTS.setMensajeRespuesta(sgdpe.getMessage());
			return resultadoADTS;
		}

	}
	
	/**
	 * Metodo encargado de buscar la cantidad de archivos a ser enviados archivo nacional.
	 * @param buscarEnvioArchivoNacionalDTO dto de busqueda.
	 * @param model model.
	 * @param request request.
	 * @return Pagina de resultado.
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getResultadoBusquedaEnvioArchivoNacional", method = RequestMethod.GET)
	public String getResultadoDeBusqueda(
			@ModelAttribute("BuscarEnvioArchivoNacionalDTO") BuscarEnvioArchivoNacionalDTO buscarEnvioArchivoNacionalDTO,
			Model model, HttpServletRequest request) throws UnsupportedEncodingException {

		log.debug("Inicio getResultadoBusquedaEnvioArchivoNacional");
		log.debug(buscarEnvioArchivoNacionalDTO.toString());
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		model.addAttribute("permisos", usuario.getPermisos());
		RespuestaEnvioArchivoNacionalDTO respuesta = null;
		try {
			respuesta = this.archivoNacionalService
					.getResultadoBusquedaEnvioArchivoNacional(buscarEnvioArchivoNacionalDTO);
			model.addAttribute("respuestaDTO", respuesta);
		} catch (ArchivoNacionalException sgdpe) {
			MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
			mensajeVistaDTO.getMensajes().put(sgdpe.getMessage(), configProps.getProperty("cssError"));
			model.addAttribute("mensajeVistaDTO", mensajeVistaDTO);
		}

		return configProps.getProperty("vistaResultadoDeBusquedaEnvioArchivoNacional");

	}
	
	@RequestMapping(value = "/enviarArchivoNacional" , method = RequestMethod.POST)
	public @ResponseBody RespuestaEnviarArchivoNacionalDTO enviarArchivoNacional(@RequestBody EnviarArchivoNacionalDTO enviarArchivoNacionalDTO, Model model, HttpServletRequest request) {		 

		RespuestaEnviarArchivoNacionalDTO respuesta = new RespuestaEnviarArchivoNacionalDTO();
		try {
			Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
			log.info("Inicio proceso de envio contrato archivo Nacional:"+enviarArchivoNacionalDTO);
			respuesta = this.archivoNacionalService.getInicioTransferencia(enviarArchivoNacionalDTO, usuario);
		} catch (ArchivoNacionalException e) {
			respuesta.setMensajeError("ERROR");
			respuesta.setMensajeRespuesta(e.getMessage());
		}
		
		return respuesta;
	}
	
	@RequestMapping(value = "/descartarArchivo/{idDocumentoCms}" , method = RequestMethod.POST)
	public @ResponseBody RespuestaDescartarArchivoDTO descartarArchivo(@PathVariable("idDocumentoCms") String idDocumentoCms,
			HttpServletRequest request) {
		RespuestaDescartarArchivoDTO respuesta = new RespuestaDescartarArchivoDTO();
		try {
			this.archivosInstDeTareaService.descartarArchivo(idDocumentoCms);
			respuesta.setMensajeError("OK");
			respuesta.setMensajeRespuesta("Archivo descartado exitosamente");
		} catch (ArchivoNacionalException e) {
			respuesta.setMensajeRespuesta(e.getMessage());	
			respuesta.setMensajeError("ERROR");
		}
		return respuesta;
	}
	
}
