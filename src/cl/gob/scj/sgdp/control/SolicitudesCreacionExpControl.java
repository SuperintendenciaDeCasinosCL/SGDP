package cl.gob.scj.sgdp.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.AutorDTO;
import cl.gob.scj.sgdp.dto.ProcesoFormCreaExpDTO;
import cl.gob.scj.sgdp.dto.SolicitudCreacionExpDTO;
import cl.gob.scj.sgdp.dto.SolicitudCreacionExpDTOJsonObject;
import cl.gob.scj.sgdp.dto.UsuarioResponsabilidadDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.BandejaDeEntradaService;
import cl.gob.scj.sgdp.service.ProcesoFormCreaExpService;
import cl.gob.scj.sgdp.service.ProcesoService;
import cl.gob.scj.sgdp.service.SolicitudCreacionExpService;
import cl.gob.scj.sgdp.service.UsuarioResponsabilidadService;
import cl.gob.scj.sgdp.tipos.EstadoSolicitudCreacionExpType;
import cl.gob.scj.sgdp.util.SingleObjectFactory;

@Controller
public class SolicitudesCreacionExpControl {

	private static final Logger log = Logger.getLogger(SolicitudesCreacionExpControl.class);
	
	@Autowired
	private SolicitudCreacionExpService solicitudesCreacionExpService;
	
	@Autowired
	private BandejaDeEntradaService bandejaDeEntradaService;
	
	@Autowired
	private ProcesoFormCreaExpService procesoFormCreaExpService;
	
    @Autowired
	private UsuarioResponsabilidadService usuarioResponsabilidadService;
    
    @Autowired
    private ProcesoService procesoService;
	
	@RequestMapping(value="/solicitudesCreacionExp", method=RequestMethod.GET)
	public String solicitudesCreacionExp(Model model, HttpServletRequest request) {
		
		log.debug("Inicio solicitudesCreacionExp");
		
		List<SolicitudCreacionExpDTO> solicitudesCreacionExpDTO = null;
		List<AutorDTO> autoresDTO = null;
		List<AutorDTO> autoresDTOVista = null;
		List<ProcesoFormCreaExpDTO> todosProcesoFormCreaExpDTO = null;
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		SolicitudCreacionExpDTO solicitudCreacionExpDTO = new SolicitudCreacionExpDTO();
		solicitudCreacionExpDTO.setIdEstadoSolicitudCreacionExp(EstadoSolicitudCreacionExpType.SOLICITADA.getIdEstadoSolicitudCreacionExp());
		solicitudCreacionExpDTO.setIdUsuarioCreadorExpediente(usuario.getIdUsuario());
		solicitudCreacionExpDTO.setIdUsuarioSolicitante(usuario.getIdUsuario());
		
		try {
			autoresDTO = bandejaDeEntradaService.getTodosLosAutores();
			autoresDTOVista = new ArrayList<AutorDTO>(autoresDTO);
			log.debug("autoresDTO.size(): " + autoresDTO.size());
			solicitudCreacionExpDTO.setAutoresDTO(autoresDTO);
			solicitudesCreacionExpDTO = solicitudesCreacionExpService.getSolicitudesCreaExpSolicitadasPorOAsignadasA(solicitudCreacionExpDTO);	
			todosProcesoFormCreaExpDTO = procesoFormCreaExpService.getTodosProcesoFormCreaExp();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);			
		}
		
		for (AutorDTO autorDTO: autoresDTO) {
			log.debug("autor solicitudesCreacionExp: " + autorDTO.toString());
		}
		
		model.addAttribute("solicitudesCreacionExpDTO", solicitudesCreacionExpDTO);
		model.addAttribute("permisos", usuario.getPermisos());		
		model.addAttribute("todosProcesoFormCreaExpDTO", todosProcesoFormCreaExpDTO);
		model.addAttribute("autoresDTO", autoresDTOVista);
		model.addAttribute("listaProcesoDto", procesoService.getProcesosPorVigencia(usuario, true));		
				
		return "solicitudesCreacionExp";
	
	}
	
	@RequestMapping(value="/getUsuariosResponsabilidadDTODeSegundasTareaPorIdProceso/{idProceso}", method=RequestMethod.GET)
	public @ResponseBody List<UsuarioResponsabilidadDTO> getTablaHistorialDeDocumentoPorIdExpediente(
								@PathVariable("idProceso") long idProceso,
								Model model, 
								HttpServletRequest request) {
		
		log.debug("Inicio getUsuariosResponsabilidadDTODeSegundasTareaPorIdProceso");
		log.debug("idProceso : " + idProceso);
		
		return usuarioResponsabilidadService.getUsuariosResponsabilidadDTODeSegundasTareaPorIdProceso(idProceso);
	}
	
	@RequestMapping(value = "/nuevaSolicitudCreacionExp", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody SolicitudCreacionExpDTO nuevaSolicitudCreacionExp(@RequestBody SolicitudCreacionExpDTO solicitudCreacionExpDTO, HttpServletRequest request) {
		log.info(solicitudCreacionExpDTO.toString());
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		try {
			solicitudesCreacionExpService.nuevaSolicitudCreacionExp(usuario, solicitudCreacionExpDTO);
			return solicitudCreacionExpDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			solicitudCreacionExpDTO.setEstado("1");
			solicitudCreacionExpDTO.setGlosa("ERROR " + e.getMessage());
			return solicitudCreacionExpDTO;
		}
		
	}
	
	@RequestMapping(value = "/rechazaSolicitudCreacionExp/{idSolicitudCreacionExp}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody SolicitudCreacionExpDTO rechazaSolicitudCreacionExp(@RequestBody SolicitudCreacionExpDTO solicitudCreacionExpDTO, HttpServletRequest request) {
		log.info(solicitudCreacionExpDTO.toString());		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		try {
			solicitudesCreacionExpService.rechazaSolicitudCreacionExp(usuario, solicitudCreacionExpDTO);
			return solicitudCreacionExpDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			solicitudCreacionExpDTO.setEstado("1");
			solicitudCreacionExpDTO.setGlosa("ERROR " + e.getMessage());
			solicitudCreacionExpDTO.setGlosa("ERROR " + e.getMessage());
			solicitudCreacionExpDTO.setEstado("1");
			return solicitudCreacionExpDTO;
		}
		
	}
	
	
	
	@RequestMapping(value="/getSolicitudesDeCreacionDeExpediente", method=RequestMethod.GET)
	public String getSolicitudesCreacionExp(Model model, HttpServletRequest request) {
		
		log.debug("Inicio getSolicitudesDeCreacionDeExpediente");
		
		List<SolicitudCreacionExpDTO> solicitudesCreacionExpDTO = null;
		List<AutorDTO> autoresDTO = null;
		List<ProcesoFormCreaExpDTO> todosProcesoFormCreaExpDTO = null;
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		SolicitudCreacionExpDTO solicitudCreacionExpDTO = new SolicitudCreacionExpDTO();
		solicitudCreacionExpDTO.setIdEstadoSolicitudCreacionExp(EstadoSolicitudCreacionExpType.SOLICITADA.getIdEstadoSolicitudCreacionExp());
		solicitudCreacionExpDTO.setIdUsuarioCreadorExpediente(usuario.getIdUsuario());
		solicitudCreacionExpDTO.setIdUsuarioSolicitante(usuario.getIdUsuario());
		
		try {
			autoresDTO = bandejaDeEntradaService.getTodosLosAutores();
			log.debug("autoresDTO.size(): " + autoresDTO.size());
			solicitudCreacionExpDTO.setAutoresDTO(autoresDTO);
			solicitudesCreacionExpDTO = solicitudesCreacionExpService.getSolicitudesCreaExpSolicitadasPorOAsignadasA(solicitudCreacionExpDTO);	
			todosProcesoFormCreaExpDTO = procesoFormCreaExpService.getTodosProcesoFormCreaExp();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);			
		}
		
		model.addAttribute("solicitudesCreacionExpDTO", solicitudesCreacionExpDTO);
		model.addAttribute("permisos", usuario.getPermisos());		
		model.addAttribute("todosProcesoFormCreaExpDTO", todosProcesoFormCreaExpDTO);
		model.addAttribute("autoresDTO", autoresDTO);
		
		return "div/solicitudesDeCreacionDeExpediente";
	
	}
	
	@RequestMapping(value="/getIdsUsuariosDestinatariosPorIdProceso/{idProceso}", method=RequestMethod.GET)
	public @ResponseBody List<String> getIdsUsuariosDestinatariosPorIdProceso(
								@PathVariable("idProceso") long idProceso,
								Model model, 
								HttpServletRequest request) {		
		log.debug("Inicio getIdsUsuariosDestinatariosPorIdProceso");
		log.debug("idProceso : " + idProceso);		
		List<String> idsUsuariosDestinatarios = new ArrayList<String>();
		SolicitudCreacionExpDTO solicitudCreacionExpDTO = new SolicitudCreacionExpDTO();
		solicitudCreacionExpDTO.setIdProceso(idProceso);		
		try {			
			List<UsuarioResponsabilidadDTO> usuariosResponsabilidadDTO = usuarioResponsabilidadService.getUsuariosResponsabilidadDTODeSegundasTareaPorIdProceso(solicitudCreacionExpDTO.getIdProceso());
			for (UsuarioResponsabilidadDTO usuarioResponsabilidadDTO : usuariosResponsabilidadDTO) {
				idsUsuariosDestinatarios.add(usuarioResponsabilidadDTO.getIdUsuario());
			}						
		}	catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);	
		}
		return idsUsuariosDestinatarios;		
	}
	
	@RequestMapping(value = "/creaExpedienteSolicitudCreacionExp", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody SolicitudCreacionExpDTO creaExpedienteSolicitudCreacionExp(@RequestBody SolicitudCreacionExpDTO solicitudCreacionExpDTO, HttpServletRequest request) {
		log.info(solicitudCreacionExpDTO.toString());
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		try {
			solicitudCreacionExpDTO.validaCreacionExp();
			solicitudesCreacionExpService.creaExpedienteSolicitudCreacionExp(usuario, solicitudCreacionExpDTO);			
			return solicitudCreacionExpDTO;
		} catch (SgdpException e) {
			log.warn(e.getMessage());
			solicitudCreacionExpDTO.setEstado("1");
			solicitudCreacionExpDTO.setGlosa(e.getMessage());
			return solicitudCreacionExpDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			solicitudCreacionExpDTO.setEstado("1");
			solicitudCreacionExpDTO.setGlosa("ERROR " + e.getMessage());
			return solicitudCreacionExpDTO;
		}
		
	}
	
	 @RequestMapping(value = "/getSolicitudesCreaExpFinalizadas", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	 public @ResponseBody String getSolicitudesCreaExpFinalizadas(HttpServletRequest  request) throws IOException {
		 log.info("Inicio getSolicitudesCreaExpFinalizadas");
		 Integer pagina = 0;	
		 pagina = Integer.valueOf(request.getParameter("iDisplayStart"));
		 int tamanoPagina = Integer.valueOf(request.getParameter("iDisplayLength"));
		 String parametroDeBusqueda = request.getParameter("sSearch");
		 log.info("pagina: " + pagina);
		 log.info("tamanoPagina: " + tamanoPagina);
		 log.info("parametroDeBusqueda: " + parametroDeBusqueda);
		 Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		 SolicitudCreacionExpDTO solicitudCreacionExpDTO = new SolicitudCreacionExpDTO();
		 solicitudCreacionExpDTO.setIdUsuarioSolicitante(usuario.getIdUsuario());
		 solicitudCreacionExpDTO.setIdUsuarioCreadorExpediente(usuario.getIdUsuario());
		 solicitudCreacionExpDTO.setPagina(pagina);
		 solicitudCreacionExpDTO.setTamanoPagina(tamanoPagina);
		 log.info(solicitudCreacionExpDTO.toString());
		 try {
			 List<SolicitudCreacionExpDTO> solFin = solicitudesCreacionExpService.getSolicitudesCreaExpFinalizadas(solicitudCreacionExpDTO);
			 log.info("solFin.size() antes de buscar por param busqueda: " + solFin.size() + " " + parametroDeBusqueda);
			 if (parametroDeBusqueda!=null && !parametroDeBusqueda.isEmpty()) {
				 solFin = solicitudesCreacionExpService.getSolicitudesCreaExpPorParamBusqueda(parametroDeBusqueda, solFin);
				 log.info("solFin.size() despues de buscar por param busqueda: " + solFin.size() + " " + parametroDeBusqueda);
			 }
			 int totalRegistros = solicitudesCreacionExpService.getTotalSolicitudesCreaExpFinalizadas(solicitudCreacionExpDTO);
			 SolicitudCreacionExpDTOJsonObject solFinJO = new SolicitudCreacionExpDTOJsonObject();
			 solFinJO.setAaData(solFin);
			 solFinJO.setiTotalDisplayRecords(totalRegistros);
			 solFinJO.setiTotalRecords(totalRegistros);
			 String responseJson;
			 ObjectMapper mapper = SingleObjectFactory.getMapper();
			 responseJson = mapper.writeValueAsString(solFinJO);
			 return responseJson;
		 } catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);				
				return null;
		}
	 }
	
}