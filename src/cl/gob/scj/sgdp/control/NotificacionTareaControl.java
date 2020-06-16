package cl.gob.scj.sgdp.control;

import java.io.IOException;
import java.util.List;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dto.RespuestaGetUsuariosNotificacionTareaPorIdTareaDTO;
import cl.gob.scj.sgdp.dto.TareaDTO;
import cl.gob.scj.sgdp.dto.UsuarioNotificacionTareaDTO;
import cl.gob.scj.sgdp.dto.UsuariosAsignadosDTO;
import cl.gob.scj.sgdp.dto.rest.MensajeJson;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.Proceso;
import cl.gob.scj.sgdp.service.BandejaDeEntradaService;
import cl.gob.scj.sgdp.service.CrearExpedienteService;
import cl.gob.scj.sgdp.service.NotificacionTareaService;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.ProcesoService;
import cl.gob.scj.sgdp.service.UsuarioNotificacionTareaService;
import cl.gob.scj.sgdp.tipos.PermisoType;



@Controller
public class NotificacionTareaControl {

	private static final Logger log = Logger.getLogger(NotificacionTareaControl.class);
	
	@Autowired
	private CrearExpedienteService crearExpedienteService;
	
	@Autowired
	private BandejaDeEntradaService bandejaDeEntradaService;
	
	@Autowired
	private UsuarioNotificacionTareaService usuarioNotificacionTareaService;
	
	@Autowired
	private NotificacionTareaService notificacionTareaService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private ProcesoService procesoService;
	
	@RequestMapping("/notificadorTarea")
	public String notificadorTarea(Model model, HttpServletRequest request) {
		
		log.debug("Inicio notificadorTarea");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		String vista = "notificarTarea";
		String permisoNotPrede = PermisoType.PUEDE_MANTENER_NOTIFICIONES_PREDETERMINADAS.getNombrePermiso();
		String permisoUsrNotPrede = usuario.getPermisos().get(permisoNotPrede);
		log.debug("permisoNotPrede: " + permisoNotPrede);
		log.debug("permisoUsrNotPrede: " + permisoUsrNotPrede);
		
		if (permisoUsrNotPrede==null || (permisoUsrNotPrede!=null && !permisoUsrNotPrede.equals(permisoNotPrede))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			model.addAttribute("listaProcesosVigentes", procesoService.getProcesosPorVigencia(usuario,true));
			model.addAttribute("listaProcesosNoVigentes", procesoService.getProcesosPorVigencia(usuario,false));
			model.addAttribute("urlFuncPhp", parametroService.getParametroPorID(Constantes.ID_PARAM_URL_FUNC_PHP).getValorParametroChar());
		}		
		
		return vista;
	
	}	
	
	@RequestMapping("/buscarTareasPorIdProcesoParaNotificar")
	public @ResponseBody List<TareaDTO> ModificarDocumentoAdjunto(@RequestBody Proceso proceso ) {
		return bandejaDeEntradaService.getTareasPorIdProceso(proceso.getIdProceso());		
	}	
	
	@RequestMapping("/buscarUsuariosPorIdTarea")
	public @ResponseBody RespuestaGetUsuariosNotificacionTareaPorIdTareaDTO  buscarUsuariosPorIdTarea(@RequestBody TareaDTO tarea,  HttpServletRequest request) {
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		RespuestaGetUsuariosNotificacionTareaPorIdTareaDTO respuestaGetUsuariosNotificacionTareaPorIdTareaDTO = new RespuestaGetUsuariosNotificacionTareaPorIdTareaDTO ();
			
        List<UsuarioNotificacionTareaDTO> usuariosNotificacionTareaDTO;
		try {
		usuariosNotificacionTareaDTO = usuarioNotificacionTareaService.getUsuariosNotificacionTareaPorIdTareaOnOut(tarea.getIdTarea(), usuario);
        respuestaGetUsuariosNotificacionTareaPorIdTareaDTO.setUsuariosNotificacionTareaDTO(usuariosNotificacionTareaDTO);
        respuestaGetUsuariosNotificacionTareaPorIdTareaDTO.setResultado("OK");               
		} catch (SgdpException e) {
            respuestaGetUsuariosNotificacionTareaPorIdTareaDTO.setResultado("ERROR");
		}
		
		return	respuestaGetUsuariosNotificacionTareaPorIdTareaDTO;
	}
	
	@RequestMapping(value = "/guardarUsuariosAsignadosNotificacion", method = RequestMethod.POST)
	public @ResponseBody MensajeJson guardarUsuariosAsignadosNotificacion(
					@RequestParam("usuariosAsignadosDTOString") String usuariosAsignadosDTOString,
					 HttpServletRequest request
					) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");				
		 
		List<UsuariosAsignadosDTO> listaUsuariosAsignadosDTO = mapper.readValue(usuariosAsignadosDTOString, new TypeReference<List<UsuariosAsignadosDTO>>(){});
		 		 
		MensajeJson mensajeJson = new MensajeJson();
		mensajeJson.setMensaje(notificacionTareaService.guardarUsuariosAsignadosNotificacion(usuario, listaUsuariosAsignadosDTO));
		 
		return mensajeJson ;		
	}
	
	
}
