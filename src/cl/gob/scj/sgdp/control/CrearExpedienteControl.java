package cl.gob.scj.sgdp.control;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.ExpedienteDTO;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.RespuestaCrearExpedienteDTO;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.service.CrearExpedienteService;
import cl.gob.scj.sgdp.service.SubirArchivoService;
import cl.gob.scj.sgdp.service.TipoDeDocumentoService;


@Controller
public class CrearExpedienteControl {
	
	private static final Logger log = Logger.getLogger(CrearExpedienteControl.class);	
	
	@Autowired
	private CrearExpedienteService crearExpedienteService;	
	
	@Resource(name = "configProps")
	private Properties configProps;	
	
	@Autowired
	private SubirArchivoService subirArchivoService;
	
	@Autowired
	private TipoDeDocumentoService tipoDeDocumentoService;	
	
	@RequestMapping(value = "/crearExpediente", method = RequestMethod.POST)
	public @ResponseBody RespuestaCrearExpedienteDTO crearExpediente(@RequestBody ExpedienteDTO expedienteDTO, Model model, HttpServletRequest request) {		 

		RespuestaCrearExpedienteDTO respuestaCrearExpedienteDTO = new RespuestaCrearExpedienteDTO();	
		log.info(expedienteDTO.toString());
		try {
			log.debug("expedienteDTO.getIdMacroProceso(): " + expedienteDTO.getIdMacroProceso());
			Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
			crearExpedienteService.crearExpediente(expedienteDTO, usuario);
			respuestaCrearExpedienteDTO.setIdInstanciaDeTareaSalida(expedienteDTO.getIdInstanciaDeTareaSalida());
			respuestaCrearExpedienteDTO.setIdExpediente(expedienteDTO.getIdExpediente());
			respuestaCrearExpedienteDTO.setMensajeRespuesta(configProps.getProperty("expedienteCreado") + " " + expedienteDTO.getNombreExpediente());
			respuestaCrearExpedienteDTO.setMensajeError("OK");
			respuestaCrearExpedienteDTO.setNombreExpediente(expedienteDTO.getNombreExpediente());
			log.info(respuestaCrearExpedienteDTO.toString());
			return respuestaCrearExpedienteDTO;				
		} catch (SgdpException sgdpe) {
			//log.error("ERROR al crear el expediente: ", sgdpe);
			log.log(sgdpe.getNivelLog(), sgdpe);
			respuestaCrearExpedienteDTO.setMensajeError("ERROR");
			respuestaCrearExpedienteDTO.setMensajeRespuesta(sgdpe.getMessage());
			log.info(respuestaCrearExpedienteDTO.toString());
			return respuestaCrearExpedienteDTO;					
		} catch (Exception e) {
			log.error("ERROR al crear el expediente: ", e);
			respuestaCrearExpedienteDTO.setMensajeRespuesta(configProps.getProperty("errorAlCrearExpEnCMS"));	
			respuestaCrearExpedienteDTO.setMensajeError("ERROR");
			log.info(respuestaCrearExpedienteDTO.toString());
			return respuestaCrearExpedienteDTO;	
		}		
	}	
	
	@RequestMapping(value="/getProcesosPorIdMacroProceso", method=RequestMethod.GET)
	public @ResponseBody List<ProcesoDTO> getProcesosPorIdMacroProceso(@RequestParam("idMacroProceso") long idMacroProceso) {	
		//procesosDTO.clear();
		List<ProcesoDTO> procesosDTO = new ArrayList<ProcesoDTO>();
		log.debug("idMacroProceso: " + idMacroProceso);
		procesosDTO = crearExpedienteService.getProcesosPorIdMacroProceso(idMacroProceso, procesosDTO);
		return procesosDTO;
	}		
	
	@RequestMapping(value="/getTareaPorIdSubProceso", method=RequestMethod.GET)
	public @ResponseBody String getTareaPorIdSubProceso(@RequestParam("idSubProceso") long idSubProceso) {	
		//procesosDTO.clear();
		log.debug("idSubProceso: " + idSubProceso);
		String respuesta = crearExpedienteService.getTareaPorIdSubProceso(idSubProceso);
		return respuesta;
	}		
	
	@RequestMapping(value="/getTipoDeDocumentoDTOPorIdProceso", method=RequestMethod.GET)
	public @ResponseBody TipoDeDocumentoDTO getTipoDeDocumentosDTOPorIdProceso(@RequestParam("idProceso") long idProceso) {			
		log.debug("idProceso: " + idProceso);
		return tipoDeDocumentoService.getTipoDeDocumentoDTOPorIdProceso(idProceso);		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/guardarAdjuntoTabla")
	public @ResponseBody SubirArhivoDTO guardarInforme(
		    @RequestParam("subirArhivoDTO") String subirArhivoDTOString,    
			@RequestParam("archivo") MultipartFile archivo,
			Model model,
			HttpServletRequest request
			) throws Exception{
	
		log.debug("Nombre Archivo" + archivo.getOriginalFilename());
		log.debug("subirArhivoDTO" + subirArhivoDTOString);
		
		ObjectMapper mapper = new ObjectMapper();
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		List<SubirArhivoDTO> listaSubirArhivoDTO = mapper.readValue(subirArhivoDTOString, new TypeReference<List<SubirArhivoDTO>>(){});
		
		//SubirAntecedenteDTO subirAntecedenteDTO =  mapper.readValue(subirArhivoDTOString, SubirAntecedenteDTO.class);
			
		 SubirArhivoDTO subirArhivoDTO = new SubirArhivoDTO();
		 subirArhivoDTO.setArchivo(archivo);
		 subirArhivoDTO.setDescripcion(listaSubirArhivoDTO.get(0).getDescripcion());
		 subirArhivoDTO.setIdTipoDeDocumentoSubir(listaSubirArhivoDTO.get(0).getIdTipoDeDocumentoSubir());
		 subirArhivoDTO.setNumeroDocumento(listaSubirArhivoDTO.get(0).getNumeroDocumento());
		 subirArhivoDTO.setFechaCreacionArchivo(listaSubirArhivoDTO.get(0).getFechaCreacionArchivo());
		 subirArhivoDTO.setIdAutorSubirDocumento(listaSubirArhivoDTO.get(0).getIdAutorSubirDocumento());
		 subirArhivoDTO.setIdExpedienteSubirArchivo(listaSubirArhivoDTO.get(0).getIdExpedienteSubirArchivo());
		 subirArhivoDTO.setIdInstanciaDeTareaSubirArchivo(listaSubirArhivoDTO.get(0).getIdInstanciaDeTareaSubirArchivo());
		 subirArhivoDTO.setCartaRelacionada(listaSubirArhivoDTO.get(0).getCartaRelacionada());
		 subirArhivoDTO.setTipoDeDocumento(listaSubirArhivoDTO.get(0).getTipoDeDocumento());
		 
		try {
			// subirArhivoDTO.setCssStatus(configProps.getProperty("cssError"));
			return subirArchivoService.subirArchivo(usuario, subirArhivoDTO);
		} catch (SgdpException sgdpe) {
			log.error("ERROR al subir archivo: ", sgdpe);			
			subirArhivoDTO.setResultadoSubirArchivo(sgdpe.getMessage());
			subirArhivoDTO.setCssStatus(configProps.getProperty("cssError"));
			log.info(subirArhivoDTO.toString());
			return subirArhivoDTO;
		}  catch (Exception e) {
			log.error("ERROR al subir archivo: ", e);
			subirArhivoDTO.setResultadoSubirArchivo(configProps.getProperty("respuestaError"));
			subirArhivoDTO.setCssStatus(configProps.getProperty("cssError"));
			log.info(subirArhivoDTO.toString());
			return subirArhivoDTO;			
		}
		
		/*catch (Exception e) {
			subirArhivoDTO.setCssStatus(configProps.getProperty("cssError"));
			return subirArchivoService.subirArchivo(usuario, subirArhivoDTO);			
		}*/
		
		//MensajeJson mensajeJson=new MensajeJson();
		//mensajeJson.setMensaje("ok");
		
		//return mensajeJson;
	}

}
