package cl.gob.scj.sgdp.control;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
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
import org.springframework.web.servlet.ModelAndView;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.MensajeVistaDTO;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.ProcesoFormCreaExpDTO;
import cl.gob.scj.sgdp.excel.ProcSolCreaExpExcel;
import cl.gob.scj.sgdp.service.ParametroService;
import cl.gob.scj.sgdp.service.ProcesoFormCreaExpService;
import cl.gob.scj.sgdp.service.ProcesoService;
import cl.gob.scj.sgdp.tipos.PermisoType;

@Controller
public class MantenedorControl {
	
	private static final Logger log = Logger.getLogger(MantenedorControl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private ProcesoService procesoService;
	
	@Autowired
	private ProcesoFormCreaExpService procesoFormCreaExpService;

	@RequestMapping(value="/mantenedores", method=RequestMethod.GET)
	public String cargaMantenedores(Model model, HttpServletRequest request) {
		log.debug("Inicio cargaMantenedores");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
				
		model.addAttribute("permisos", usuario.getPermisos());
		
		model.addAttribute("parametrosMap", parametroService.getParametrosMap());
		
		return configProps.getProperty("vistaMantenedores");
	}
	
	@RequestMapping(value="/mantenedorAutor", method=RequestMethod.GET)
	public String cargaMantenedorAutor(Model model, HttpServletRequest request) {
		log.debug("Inicio cargaMantenedores");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		String vista = "mantenedorAutor";
		String permisoMantAutor = PermisoType.PUEDE_MANTENER_AUTORES.getNombrePermiso();
		String permisoUsrMantAutor = usuario.getPermisos().get(permisoMantAutor);
		log.debug("permisoMantAutor: " + permisoMantAutor);
		log.debug("permisoUsrMantAutor: " + permisoUsrMantAutor);
		
		if (permisoUsrMantAutor==null || (permisoUsrMantAutor!=null && !permisoUsrMantAutor.equals(permisoMantAutor))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			model.addAttribute("permisos", usuario.getPermisos());			
			model.addAttribute("parametrosMap", parametroService.getParametrosMap());
		}	
		
		return vista;
	}
	
	@RequestMapping(value="/mantenedorProcSolCreaExp", method=RequestMethod.GET)
	public String mantenedorProcSolCreaExp(Model model, @RequestParam("vistaCompleta") boolean vistaCompleta, HttpServletRequest request) {
		log.debug("Inicio mantenedorProcSolCreaExp");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		String vista = "mantenedorProcSolCreaExp";
		
		if (vistaCompleta == false) {
			vista = "div/mantProcSolCreaExp";
		}
		
		String permisoMantProcSolCreExp = PermisoType.PUEDE_MANTENER_PROCESOS_SOL_CREAC_EXP.getNombrePermiso();
		String permisoUsrMantProcSolCreExp = usuario.getPermisos().get(permisoMantProcSolCreExp);
		log.debug("permisoMantProcSolCreExp: " + permisoMantProcSolCreExp);
		log.debug("permisoUsrMantProcSolCreExp: " + permisoUsrMantProcSolCreExp);
		
		if (permisoUsrMantProcSolCreExp==null || (permisoUsrMantProcSolCreExp!=null && !permisoUsrMantProcSolCreExp.equals(permisoMantProcSolCreExp))) {
			log.debug("Devolviendo vista 403");
			vista = "error403";
		} else {
			try {
				List<ProcesoDTO> todosLosProcVigentes = procesoService.getBuscarTodosProcesosPorVigencia(true);
				List<ProcesoFormCreaExpDTO> todosLosProcFormCreaExp = procesoFormCreaExpService.getTodosProcesoFormCreaExp();
				procesoFormCreaExpService.eliminaProcesoDTOQueEstanEnProcesoFormCreaExpDTO(todosLosProcVigentes, todosLosProcFormCreaExp);
				model.addAttribute("permisos", usuario.getPermisos());			
				model.addAttribute("parametrosMap", parametroService.getParametrosMap());
				model.addAttribute("listaProcesosVigentes", todosLosProcVigentes);
				model.addAttribute("todosLosProcFormCreaExp", todosLosProcFormCreaExp);
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
				MensajeVistaDTO mensajeVistaDTO = new MensajeVistaDTO();
				mensajeVistaDTO.getMensajes().put("Ocurrio un error. Por favor contacte a soporte", configProps.getProperty("cssError"));
			}	
			
		}			
		return vista;	
	}
		
	@RequestMapping(value = "/guardarAsignacionProcCreaExp", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody ProcesoFormCreaExpDTO guardarAsignacionProcCreaExp(@RequestBody List<ProcesoFormCreaExpDTO> listaProcesosFormCreaExpDTO, HttpServletRequest request) {
		ProcesoFormCreaExpDTO procesoFormCreaExpDTO = new ProcesoFormCreaExpDTO();
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		for (ProcesoFormCreaExpDTO procesoFormCreaExpDTOL : listaProcesosFormCreaExpDTO) {
			log.info(procesoFormCreaExpDTOL.toString());
		}
		try {
			procesoFormCreaExpService.guardarAsignacionProcCreaExp(listaProcesosFormCreaExpDTO, usuario);
			procesoFormCreaExpDTO.setEstado("0");
			procesoFormCreaExpDTO.setGlosa("OK");
			return procesoFormCreaExpDTO;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			procesoFormCreaExpDTO.setEstado("1");
			procesoFormCreaExpDTO.setGlosa("ERROR " + e.getMessage());
			return procesoFormCreaExpDTO;
		}		
	}
	
	@RequestMapping(value = "/procSolCreaExpExcel", method = RequestMethod.GET)
	public ModelAndView procSolCreaExpExcel(HttpServletRequest request) {
		List<ProcesoDTO> todosLosProcVigentes = procesoService.getBuscarTodosProcesosPorVigencia(true);
		List<ProcesoFormCreaExpDTO> todosLosProcFormCreaExp = procesoFormCreaExpService.getTodosProcesoFormCreaExp();
		procesoFormCreaExpService.eliminaProcesoDTOQueEstanEnProcesoFormCreaExpDTO(todosLosProcVigentes, todosLosProcFormCreaExp);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("todosLosProcVigentes", todosLosProcVigentes);
		model.put("todosLosProcFormCreaExp", todosLosProcFormCreaExp);
		ModelAndView mv = new ModelAndView(new ProcSolCreaExpExcel(), model);		
		return mv;
	}
	
}
