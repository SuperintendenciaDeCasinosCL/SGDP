package cl.gob.scj.sgdp.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import cl.gob.scj.sgdp.dto.ArchivoInfoDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.service.BandejaDeEntradaService;
import cl.gob.scj.sgdp.service.ObtenerArchivosExpedienteService;


@Controller
public class NotificacionesSeguimientosControl {
 
	private static final Logger log = Logger.getLogger(DetalleDeTareaControl.class);
	
	@Autowired
	BandejaDeEntradaService bandejaDeEntradaService;
	
	@Autowired
	private ObtenerArchivosExpedienteService obtenerArchivosExpedienteService;
	
	
	@RequestMapping("/notificacionSeguimientos")
	public String notificacionSeguimientos(Model model , HttpServletRequest request){
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
			
		List<InstanciaDeTareaDTO> listaInstanciaDeTareaDTO = new ArrayList<InstanciaDeTareaDTO>();
		
		try {
			bandejaDeEntradaService.getNotificacionesSeguimientosPorUsuario(listaInstanciaDeTareaDTO, usuario);
			model.addAttribute("instanciasDeTareasDTOEnSeguimiento", listaInstanciaDeTareaDTO);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("ticket", usuario.getAlfTicket());
		
		return "div/notificacionesSeguimientos";
	}
	


	@RequestMapping(value = "/buscarTodosDocumentosSeguimiento", method = RequestMethod.POST)
	public @ResponseBody List<ArchivoInfoDTO> buscarTodosDocumentosSeguimiento(@RequestBody InstanciaDeProceso instanciaDeProceso,
												HttpServletRequest request) {

		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		
		List<ArchivoInfoDTO> archivosExpedienteDTO = null;
		
		try {
			archivosExpedienteDTO = obtenerArchivosExpedienteService.obtenerArchivosExpediente(usuario, 
					instanciaDeProceso.getIdExpediente(), false, false, false, 0);
			
		} catch (SgdpException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
		return archivosExpedienteDTO;			
	}


	@RequestMapping(value = "/buscarTodosDocumentosFinalesSeguimiento", method = RequestMethod.POST)
	public @ResponseBody List<ArchivoInfoDTO> buscarTodosDocumentosFinalesSeguimiento(@RequestBody InstanciaDeProceso instanciaDeProceso,
												HttpServletRequest request) {

		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");		
		
		List<ArchivoInfoDTO> listaArchivosExpedienteDTOSalida  = new ArrayList<ArchivoInfoDTO>();
		List<ArchivoInfoDTO> listaArchivosExpedienteDTO = null;
		

		List<String> listaDocumentosFirmado = obtenerArchivosExpedienteService.listaDocumentosFirmado(instanciaDeProceso);
		
	
		try {
			listaArchivosExpedienteDTO = obtenerArchivosExpedienteService.obtenerArchivosExpediente(usuario, 
					instanciaDeProceso.getIdExpediente(), false, false, false, 0);
		


			
			for (String documentoFirmado : listaDocumentosFirmado) {			
				for (ArchivoInfoDTO archivoInfoDTO : listaArchivosExpedienteDTO) {
			
					if (documentoFirmado.equals(archivoInfoDTO.getIdArchivo())){
						listaArchivosExpedienteDTOSalida.add(archivoInfoDTO);
					}
					
				}
							
			}


		} catch (SgdpException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
		return listaArchivosExpedienteDTOSalida;			
	}
	
}
