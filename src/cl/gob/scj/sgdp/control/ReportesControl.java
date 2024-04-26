package cl.gob.scj.sgdp.control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.service.ReportesService;

@Controller
public class ReportesControl {

	private static final Logger log = Logger.getLogger(CargaProcesosControl.class);
	
	@Autowired
	private ReportesService reportesService;

	@RequestMapping(value = "/reportes", method = RequestMethod.GET)
	public String cargaPantallaCargaProcesos(Model model, HttpServletRequest request) {
		
		log.debug("Inicio cargaPantallIndicador");
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		String vista = "reportes";
		//String permisoIndicador = PermisoType.PUEDE_VER_INDICADORES.getNombrePermiso();
		//String permisoUsrIndicador = usuario.getPermisos().get(permisoIndicador);
		//log.debug("permisoIndicador: " + permisoIndicador);
		//log.debug("permisoUsrIndicador: " + permisoUsrIndicador);
		
		//if (permisoUsrIndicador==null || (permisoUsrIndicador!=null && !permisoUsrIndicador.equals(permisoIndicador))) {
		//	vista = "error403";
		//} else {
			
		//}
		
		return vista;
	}

	
	@RequestMapping(value="/reportes/{tipo}/{desde}/{hasta}/{filtro}", method=RequestMethod.GET)
	public @ResponseBody List<?> getDataInicial(
			@PathVariable String tipo,
			@PathVariable String desde,
			@PathVariable String hasta,
			@PathVariable String filtro
			) throws IOException, ParseException {			
		try {
			return reportesService.getReporte(tipo, desde, hasta, filtro);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	

}
