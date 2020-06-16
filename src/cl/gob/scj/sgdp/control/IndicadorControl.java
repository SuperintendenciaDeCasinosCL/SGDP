package cl.gob.scj.sgdp.control;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.EntradaSubprocesoIndicadoresDTO;
import cl.gob.scj.sgdp.dto.RespuestaIndicadorDTO;
import cl.gob.scj.sgdp.dto.SalidaSubprocesoIndicadoresDTO;
import cl.gob.scj.sgdp.service.IndicadorService;
import cl.gob.scj.sgdp.tipos.PermisoType;
import cl.gob.scj.sgdp.ws.rest.client.IndicadoresClientRestService;

@Controller
public class IndicadorControl {

	private static final Logger log = Logger.getLogger(IndicadorControl.class);

	@Autowired
	IndicadoresClientRestService indicadoresClientRestService;

	@Autowired
	IndicadorService indicadorService;

	@RequestMapping("/indicador")
	public String cargaPantallIndicador(Model model, HttpServletRequest request) {
		
		log.debug("Inicio cargaPantallIndicador");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		String vista = "indicador";
		String permisoIndicador = PermisoType.PUEDE_VER_INDICADORES.getNombrePermiso();
		String permisoUsrIndicador = usuario.getPermisos().get(permisoIndicador);
		log.debug("permisoIndicador: " + permisoIndicador);
		log.debug("permisoUsrIndicador: " + permisoUsrIndicador);
		
		if (permisoUsrIndicador==null || (permisoUsrIndicador!=null && !permisoUsrIndicador.equals(permisoIndicador))) {
			vista = "error403";
		} else {
			RespuestaIndicadorDTO respuestaIndicadorDTO = indicadoresClientRestService.buscaTodosIndicadoresVigentes();
			model.addAttribute("listaIndicadores", respuestaIndicadorDTO.getListaIndicadoresDTO());
		}
		
		return vista;
	}

	@RequestMapping(value = "/buscarSubprocesoPorIdIndicador", method = RequestMethod.POST)
	public @ResponseBody SalidaSubprocesoIndicadoresDTO buscarSubprocesoPorIdIndicador(Model model,
			@RequestBody EntradaSubprocesoIndicadoresDTO entradaSubprocesoIndicadoresDTO) {

		SalidaSubprocesoIndicadoresDTO salidaSubprocesoIndicadoresDTO = indicadorService
				.buscarSubprocesoPorIdIndicador(entradaSubprocesoIndicadoresDTO);

		return salidaSubprocesoIndicadoresDTO;
	}

}
