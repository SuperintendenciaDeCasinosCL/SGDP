package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.AnulacionDTO;
import cl.gob.scj.sgdp.dto.ArchivoInfoDTO;
import cl.gob.scj.sgdp.dto.CierraInstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.ContinuarProcesoDTO;
import cl.gob.scj.sgdp.dto.FinalizaProcesoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.ReabreInstanciaDeSubProcesoDTO;
import cl.gob.scj.sgdp.dto.RetrocedeProcesoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.InstanciaDeTareaService;
import cl.gob.scj.sgdp.service.MueveProcesoService;
import cl.gob.scj.sgdp.service.ObtenerArchivosExpedienteService;
import cl.gob.scj.sgdp.service.UsuarioResponsabilidadService;

@Controller
public class MueveProcesoControl {
	
	private static final Logger log = Logger.getLogger(MueveProcesoControl.class);
	
	@Resource(name = "configProps")
	private Properties configProps;

	@Autowired
	private MueveProcesoService mueveProcesoService;		
	
	@Autowired
	private UsuarioResponsabilidadService usuarioResponsabilidadService;
	
	@Autowired
	private InstanciaDeTareaService instanciaDeTareaService;
	
	@Autowired
	private ObtenerArchivosExpedienteService obtenerArchivosExpedienteService;
	
	@RequestMapping(value="/getInstanciasDeTareasQueContinuanDeInstanciaDeTarea", method=RequestMethod.GET)
	public @ResponseBody List<InstanciaDeTareaDTO> getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(@RequestParam("idInstanciaDeTarea") long idInstanciaDeTarea, HttpServletRequest request) {
		List<InstanciaDeTareaDTO> instanciasDeTareasDTOContinuanProceso = new ArrayList<InstanciaDeTareaDTO>();
		log.debug("idInstanciaDeTarea: " + idInstanciaDeTarea);
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
		instanciaDeTareaService.cargaInstanciaDeTareaPorIdInstanciaDeTarea(Long.parseLong(request.getParameter("idInstanciaDeTarea")), instanciaDeTareaDTO);
		//instanciasDeTareasDTOContinuanProceso = mueveProcesoService.getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(usuario, idInstanciaDeTarea, true, instanciasDeTareasDTOContinuanProceso);
		instanciasDeTareasDTOContinuanProceso = mueveProcesoService.getInstanciasDeTareasQueContinuanDeInstanciaDeTarea(usuario, instanciaDeTareaDTO, instanciasDeTareasDTOContinuanProceso);
		for (InstanciaDeTareaDTO instanciaDeTareaDTOR: instanciasDeTareasDTOContinuanProceso) {
			log.debug(instanciaDeTareaDTOR.toString());
		}
		return instanciasDeTareasDTOContinuanProceso;
	}	
	
	@RequestMapping(value = "/mueveProceso", method = RequestMethod.POST)
	public @ResponseBody ContinuarProcesoDTO mueveProceso(@ModelAttribute("continuarProcesoDTO") ContinuarProcesoDTO continuarProcesoDTO, Model model, HttpServletRequest request) {		
		log.info("Inicio Envia");
		log.info(continuarProcesoDTO.toString());
		try {				
			Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");	
			if (continuarProcesoDTO.getAvanzaRetrocede().equals(configProps.getProperty("avanzarProceso"))) {
				continuarProcesoDTO.setRecarga(true);
				mueveProcesoService.avanzaProceso(continuarProcesoDTO, usuario);			
			}				
		} catch (SgdpException sgdpe) {
			log.debug("sgdpe.getMessage(): " + sgdpe.getMessage());
			continuarProcesoDTO.setRespuestaMueveProceso(sgdpe.getMessage());			
			continuarProcesoDTO.setRecarga(sgdpe.getRecarga());
			StringWriter sw = new StringWriter();
			sgdpe.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.log(sgdpe.getNivelLog(), exceptionAsString);
			log.log(sgdpe.getNivelLog(), continuarProcesoDTO.toString());
			log.info("Fin Envia");
			return continuarProcesoDTO;				
		} catch (Exception e) {
			log.error("ERROR al mover proceso: ", e);
			continuarProcesoDTO.setRespuestaMueveProceso("ERROR al mover proceso");
			log.error(continuarProcesoDTO.toString());
			log.info("Fin Envia");
			return continuarProcesoDTO;		
		}
		log.info(continuarProcesoDTO.toString());
		continuarProcesoDTO.setRespuestaMueveProceso(configProps.getProperty("respuestaOK"));
		log.info("Fin Envia");
		return continuarProcesoDTO;
	}
	
	@RequestMapping(value = "/retrocedeProceso", method = RequestMethod.POST)
	public @ResponseBody RetrocedeProcesoDTO retrocedeProceso(@ModelAttribute("retrocedeProcesoDTO") RetrocedeProcesoDTO retrocedeProcesoDTO, Model model, HttpServletRequest request) {
		log.info("Inico retrocedeProceso");
		log.info(retrocedeProcesoDTO.toString());
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		try {
			String respuesta = mueveProcesoService.retrocedeProceso(retrocedeProcesoDTO, usuario);
			log.debug("respuesta: " + respuesta);
			retrocedeProcesoDTO.setRecarga(true);
			retrocedeProcesoDTO.setResultadoRetrocedeProceso(respuesta);
			retrocedeProcesoDTO.setCssStatus(configProps.getProperty("cssSucess"));			
		} catch (SgdpException sgdpe) {
			log.debug("sgdpe.getMessage(): " + sgdpe.getMessage());
			retrocedeProcesoDTO.setResultadoRetrocedeProceso(sgdpe.getMessage());
			retrocedeProcesoDTO.setCssStatus(configProps.getProperty("cssError"));
			retrocedeProcesoDTO.setRecarga(false);
			StringWriter sw = new StringWriter();
			sgdpe.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.log(sgdpe.getNivelLog(), exceptionAsString);
			log.log(sgdpe.getNivelLog(), retrocedeProcesoDTO.toString());
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			retrocedeProcesoDTO.setResultadoRetrocedeProceso(configProps.getProperty("errorAlDevolverProceso"));
			retrocedeProcesoDTO.setCssStatus(configProps.getProperty("cssError"));
			retrocedeProcesoDTO.setRecarga(false);
		}
		log.info("Fin retrocedeProceso");
		return retrocedeProcesoDTO;
	}	

	@RequestMapping(value = "/finalizarProceso", method = RequestMethod.POST)
	public @ResponseBody FinalizaProcesoDTO finalizarProceso(@ModelAttribute("finalizaProcesoDTO") FinalizaProcesoDTO finalizaProcesoDTO, HttpServletRequest request) {
		log.debug("Inicio finalizarProceso");
		log.debug(finalizaProcesoDTO.toString());
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		try {
			mueveProcesoService.finaliza(usuario, finalizaProcesoDTO);
			finalizaProcesoDTO.setRespuestaFinalizaProceso(configProps.getProperty("respuestaOK"));	
			finalizaProcesoDTO.setRecarga(true);
			return finalizaProcesoDTO;			
		} catch (SgdpException sgdpe) {
			log.error("ERROR al finalizar proceso: ", sgdpe);
			log.debug("sgdpe.getMessage(): " + sgdpe.getMessage());
			StringWriter sw = new StringWriter();
			sgdpe.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.log(sgdpe.getNivelLog(), exceptionAsString);
			finalizaProcesoDTO.setRespuestaFinalizaProceso(sgdpe.getMessage());
			finalizaProcesoDTO.setRecarga(false);
			return finalizaProcesoDTO;
		} catch (Exception e) {
			log.error("ERROR al finalizar: ", e);
			finalizaProcesoDTO.setRespuestaFinalizaProceso("ERROR al finalizar proceso");
			finalizaProcesoDTO.setRecarga(false);
			return finalizaProcesoDTO;
		}
	}	
	
	@RequestMapping(value = "/cerrarProceso", method = RequestMethod.POST) 
	public @ResponseBody AnulacionDTO anularProceso(@RequestBody AnulacionDTO anulacionDTO, HttpServletRequest request) {
		log.debug("Inicio cerrarProceso");
		log.debug(anulacionDTO.toString());
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		try {
			mueveProcesoService.anularProceso(usuario, anulacionDTO);			
			return anulacionDTO;			
		} catch (Exception e) {
			log.error("ERROR al cerrar: ", e);
			anulacionDTO.setRespuestaAnulacion(configProps.getProperty("respuestaError"));
			return anulacionDTO;
		}
	}
	
	@RequestMapping(value = "/getUsuariosPosiblesPorIdInstanciaDeTarea", method = RequestMethod.GET)
	public @ResponseBody List<String> getUsuariosRolesPosiblesPorIdInstanciaDeTarea(@RequestParam("idInstanciaDeTarea") long idInstanciaDeTarea, HttpServletRequest request) {
		List<String> posiblesUsuarios = new ArrayList<String>();		
		usuarioResponsabilidadService.cargaUsuariosRolesPosiblesPorIdInstanciaDeTarea(idInstanciaDeTarea, posiblesUsuarios);
		return posiblesUsuarios;
	}
	
	@RequestMapping(value = "/getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario/{idInstanciaDeTarea}", method = RequestMethod.GET)
	public @ResponseBody String getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario(@PathVariable("idInstanciaDeTarea") long idInstanciaDeTarea, HttpServletRequest request) {
		log.info("Inicio getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario");
		log.info("idInstanciaDeTarea: " + idInstanciaDeTarea);
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
		/*HistoricoFirmaDTO historicoFirmaDTOConsulta = new HistoricoFirmaDTO();
		historicoFirmaDTOConsulta.setIdInstanciaDeTarea(idInstanciaDeTarea);
		historicoFirmaDTOConsulta.setIdUsuario(usuario.getIdUsuario());*/
		try {
			
			instanciaDeTareaService.cargaInstanciaDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea, instanciaDeTareaDTO); 
			
			List<ArchivoInfoDTO> archivosExpedienteDTO = obtenerArchivosExpedienteService.obtenerArchivosExpediente(usuario, 
					instanciaDeTareaDTO.getIdExpediente(), true, instanciaDeTareaDTO.getPuedeVisarDocumentos(), instanciaDeTareaDTO.getPuedeAplicarFEA(), instanciaDeTareaDTO.getIdInstanciaDeTarea());
			
			obtenerArchivosExpedienteService.cargaArchivosInfoDTODeSalidaPorIdInstanciaDeTarea(usuario, instanciaDeTareaDTO, 
					new ArrayList<ArchivoInfoDTO>(), archivosExpedienteDTO);
			
			/*List<HistoricoFirmaDTO> historicoFirmaDTOList = historicoFirmaService.getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario(historicoFirmaDTOConsulta);
			if (historicoFirmaDTOList!=null && !historicoFirmaDTOList.isEmpty()) {
				log.info("Retornando true");
				return "true";
			} else {
				log.info("Retornando false");
				return "false";
			}*/	
			
			if (instanciaDeTareaDTO.isPuedeAvanzarProcesoConAdvertenciaFEA() == true) {
				log.info("Retornando false");
				return "false";
			} else {
				log.info("Retornando true");
				return "true";
			}				
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			return "error";
		}
			
	}
	
	@RequestMapping(value="/reabrirExpedientePorIdInstanciaDeTareaIdUsuario", method=RequestMethod.POST)
	public @ResponseBody ReabreInstanciaDeSubProcesoDTO reabrirExpedientePorIdInstanciaDeTareaIdUsuario(@RequestBody ReabreInstanciaDeSubProcesoDTO reabreInstanciaDeSubProcesoDTO, HttpServletRequest request) {
		log.info(reabreInstanciaDeSubProcesoDTO.toString());
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		try {
			mueveProcesoService.reabrirExpedientePorIdInstanciaDeTareaIdUsuario(reabreInstanciaDeSubProcesoDTO, usuario);
			reabreInstanciaDeSubProcesoDTO.setEstado("0");
			reabreInstanciaDeSubProcesoDTO.setGlosa("OK");
			return reabreInstanciaDeSubProcesoDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			ReabreInstanciaDeSubProcesoDTO reabreInstanciaDeSubProcesoDTOError = new ReabreInstanciaDeSubProcesoDTO();
			reabreInstanciaDeSubProcesoDTOError.setEstado("1");
			reabreInstanciaDeSubProcesoDTOError.setGlosa("ERROR: " + e.getMessage());
			return reabreInstanciaDeSubProcesoDTOError;
		}		
	}
	
	@RequestMapping(value="/cierraInstanciaDeTarea", method=RequestMethod.POST)
	public @ResponseBody CierraInstanciaDeTareaDTO cierraInstanciaDeTarea (@RequestBody CierraInstanciaDeTareaDTO cierraInstanciaDeTareaDTO, HttpServletRequest request) {
		log.info(cierraInstanciaDeTareaDTO.toString());
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		try {
			mueveProcesoService.cierraInstanciaDeTarea(cierraInstanciaDeTareaDTO, usuario);
			cierraInstanciaDeTareaDTO.setEstado("0");
			cierraInstanciaDeTareaDTO.setGlosa("OK");
			return cierraInstanciaDeTareaDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			CierraInstanciaDeTareaDTO cierraInstanciaDeTareaDTOError = new CierraInstanciaDeTareaDTO();
			cierraInstanciaDeTareaDTOError.setEstado("1");
			cierraInstanciaDeTareaDTOError.setGlosa("ERROR: " + e.getMessage());
			return cierraInstanciaDeTareaDTOError;
		}		
	}
	
}
