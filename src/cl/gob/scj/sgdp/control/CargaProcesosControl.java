package cl.gob.scj.sgdp.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.CargaProcesoDataInicialDTO;
import cl.gob.scj.sgdp.dto.MacroProcesoDTO;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.SuggestionsDTO;
import cl.gob.scj.sgdp.dto.SuperProcesoDTO;
import cl.gob.scj.sgdp.service.BandejaDeEntradaService;
import cl.gob.scj.sgdp.service.CargaDeProcesosService;


@Controller
public class CargaProcesosControl {
	
	@Autowired
	private CargaDeProcesosService cargaDeProcesosService;

	private static final Logger log = Logger.getLogger(CargaProcesosControl.class);

	@RequestMapping(value = "/cargaProcesos", method = RequestMethod.GET)
	public String cargaPantallaCargaProcesos(Model model, HttpServletRequest request) {
		
		log.debug("Inicio cargaPantallIndicador");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		String vista = "cargaProcesos";
		//String permisoIndicador = PermisoType.PUEDE_VER_INDICADORES.getNombrePermiso();
		//String permisoUsrIndicador = usuario.getPermisos().get(permisoIndicador);
		//log.debug("permisoIndicador: " + permisoIndicador);
		//log.debug("permisoUsrIndicador: " + permisoUsrIndicador);
		
		//if (permisoUsrIndicador==null || (permisoUsrIndicador!=null && !permisoUsrIndicador.equals(permisoIndicador))) {
		//	vista = "error403";
		//} else {
			
			model.addAttribute("saludo", "holi");
		//}
		
		return vista;
	}

	
	@RequestMapping(value="/cargaProcesos/dataInicial", method=RequestMethod.GET)
	public @ResponseBody CargaProcesoDataInicialDTO getDataInicial() {			
		return cargaDeProcesosService.getDataInicial(new CargaProcesoDataInicialDTO());
	}
	
	@RequestMapping(value="/cargaProcesos/perspectivas", method=RequestMethod.GET)
	public @ResponseBody CargaProcesoDataInicialDTO getPerspectivas() {			
		return cargaDeProcesosService.getDataInicial(new CargaProcesoDataInicialDTO());
	}
	
	@RequestMapping(value="/cargaProcesos/macroProcesos/{perspectiva}", method=RequestMethod.GET)
	public @ResponseBody List<MacroProcesoDTO> getMacroProcesos(@PathVariable Long perspectiva) {			
		return cargaDeProcesosService.getTodosLosMacroProcesos(perspectiva, new ArrayList<MacroProcesoDTO>());
	}
	
	@RequestMapping(value="/cargaProcesos/superProcesos/{macroProceso}", method=RequestMethod.GET)
	public @ResponseBody List<SuperProcesoDTO> getSuperProcesos(@PathVariable Long macroProceso) {			
		return cargaDeProcesosService.getTodosLosSuperProcesos(macroProceso, new ArrayList<SuperProcesoDTO>());
	}
	
	@RequestMapping(value="/cargaProcesos/procesos/{superProceso}", method=RequestMethod.GET)
	public @ResponseBody List<ProcesoDTO> getProcesos(@PathVariable Long superProceso) {			
		return cargaDeProcesosService.getTodosLosProcesosBySuperProceso(superProceso, new ArrayList<ProcesoDTO>());
	}
	
	@RequestMapping(value = "/cargaProcesos", method = RequestMethod.POST, produces = "application/json", consumes = {"multipart/form-data"})
	public @ResponseBody ProcesoDTO guardaProceso(@ModelAttribute ProcesoDTO p) {
		return cargaDeProcesosService.guardarProceso(p);
	}
	
	@RequestMapping(value = "/cargaProcesosMultipart", method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = {"multipart/form-data;charset=UTF-8"})
	public @ResponseBody ProcesoDTO guardaProcesoMultipart(@ModelAttribute ProcesoDTO p) {
		ObjectMapper objectMapper = new ObjectMapper();
	
		try {
			ProcesoDTO proceso = objectMapper.readValue(p.getTmp(), ProcesoDTO.class);
			proceso.setImage(p.getImage());
			return cargaDeProcesosService.guardarProceso(proceso);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	@RequestMapping(value = "/cargaProcesos/{idProceso}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Boolean guardaImagenDeProceso(@PathVariable Long idProceso,  @RequestParam("imagen") MultipartFile imagen) {
		return true;
	}
	

}
