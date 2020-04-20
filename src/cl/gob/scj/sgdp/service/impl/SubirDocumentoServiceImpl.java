package cl.gob.scj.sgdp.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.InstanciaDeTareaDao;
import cl.gob.scj.sgdp.dao.TareaDao;
import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dto.DetalleDeArchivoDTO;
import cl.gob.scj.sgdp.dto.SubirDocumentoDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.model.UsuarioAsignado;
import cl.gob.scj.sgdp.service.EmailService;
import cl.gob.scj.sgdp.service.ObtenerDetalleDeArchivoService;
import cl.gob.scj.sgdp.service.SubirDocumentoService;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.ws.alfresco.rest.client.SubirDocumentoCMSService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class SubirDocumentoServiceImpl implements SubirDocumentoService {

	private static final Logger log = Logger.getLogger(SubirDocumentoServiceImpl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private SubirDocumentoCMSService subirDocumentoCMSService;
	
	@Autowired
	private InstanciaDeTareaDao instanciaDeTareaDao;
	
	@Autowired
	private ArchivosInstDeTareaDao archivosInstDeTareaDao;
	
	@Autowired
	private ObtenerDetalleDeArchivoService obtenerDetalleDeArchivoService;
	
	@Autowired
	private TipoDeDocumentoDao tipoDeDocumentoDao;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private TareaDao TareaDao;
	
	@Override
	public String subirDocumento(Usuario usuario,
			SubirDocumentoDTO subirDocumentoDTO) throws SgdpException {
		log.debug("Inicio... subirDocumento");
		List<String> listaDeUsuariosAsignados = new ArrayList<String>();
		try {				
			// Se sacan los datos del documento y se  busca si el documento es oficial en el campo B_CONFORMA_EXPEDIENTE
			TipoDeDocumento tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(subirDocumentoDTO.getIdTipoDeDocumentoSubir());			
			Tarea tarea = TareaDao.getBuscaDocumentoOficialEnTarea(subirDocumentoDTO.getIdInstanciaDeTareaOrigenSubirDocumento());
			if (tipoDeDocumento.getConformaExpediente() == true && tarea.getConformaExpediente()!=null && tarea.getConformaExpediente() == true ){			
				subirDocumentoDTO.setEsDocumentoOficial(true);
			//}else{
				//subirDocumentoDTO.setEsDocumentoOficial(false);				
			}
			// ----------------------------------------------------
			String respuestaSubirCarta = subirDocumentoCMSService.subirDocumento(usuario, subirDocumentoDTO);
			InstanciaDeTarea instanciaDeTarea = instanciaDeTareaDao.getPrimeraInstanciaDeTareaPorId(subirDocumentoDTO.getIdInstanciaDeTareaOrigenSubirDocumento());
			if (instanciaDeTarea!=null) {
				DetalleDeArchivoDTO detalleDeArchivoDTO = obtenerDetalleDeArchivoService.obtenerDetalleDeArchivo(usuario, respuestaSubirCarta);
				ArchivosInstDeTarea archivosInstDeTarea = new ArchivosInstDeTarea();
				archivosInstDeTarea.setFechaSubido(new Date());
				archivosInstDeTarea.setIdArchivoCms(respuestaSubirCarta);
				archivosInstDeTarea.setIdUsuario(usuario.getIdUsuario());
				archivosInstDeTarea.setInstanciaDeTarea(instanciaDeTarea);
				archivosInstDeTarea.setMimeType(detalleDeArchivoDTO.getMimeType());
				archivosInstDeTarea.setNombreArchivo(detalleDeArchivoDTO.getNombre());
				archivosInstDeTarea.setTipoDeDocumento(tipoDeDocumento);
				archivosInstDeTarea.setVersion(detalleDeArchivoDTO.getLabelUltimaVersion());				
				if (!subirDocumentoDTO.getFechaDeCreacionDocumento().isEmpty() && subirDocumentoDTO.getFechaDeCreacionDocumento()!=null) {
					try {
						Date fechaDoc = FechaUtil.simpleDateFormatForm.parse(subirDocumentoDTO.getFechaDeCreacionDocumento());
						archivosInstDeTarea.setFechaDocumento(fechaDoc);
					} catch (ParseException e) {
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						String exceptionAsString = sw.toString();
						log.info(exceptionAsString);
					}					
				}
				if (!subirDocumentoDTO.getFechaRecepcionDocumento().isEmpty() && subirDocumentoDTO.getFechaRecepcionDocumento()!=null) {
					try {
						Date fechaRecep = FechaUtil.simpleDateFormatForm.parse(subirDocumentoDTO.getFechaRecepcionDocumento());
						archivosInstDeTarea.setFechaRecepcion(fechaRecep);
					} catch (ParseException e) {
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						String exceptionAsString = sw.toString();
						log.info(exceptionAsString);
					}
				}	
				archivosInstDeTareaDao.insertaArchivosInstDeTarea(archivosInstDeTarea);
				List<UsuarioAsignado> usuarioAsignados = instanciaDeTarea.getUsuariosAsignados();
				for (UsuarioAsignado usuarioAsignado: usuarioAsignados) {
					listaDeUsuariosAsignados.add(usuarioAsignado.getId().getIdUsuario());
				}
				emailService.enviarMail(usuario, instanciaDeTarea, listaDeUsuariosAsignados, instanciaDeTarea.getInstanciaDeProceso().getAsunto(), instanciaDeTarea);
			}
			//actualizar instancia de proceso -- campo tiene documentos en CMS true
			instanciaDeTarea.getInstanciaDeProceso().setTieneDocumentosEnCMS(true);
			return respuestaSubirCarta;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			//log.error(e);
			log.debug("Fin... subirDocumento.. error");
			throw new SgdpException(configProps.getProperty("errorAlSubirCartaEnCMS"));
		}			
	}

}
