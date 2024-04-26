package cl.gob.scj.sgdp.ws.rest.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.AsignacionNumeroDocDao;
import cl.gob.scj.sgdp.dto.HistoricoFirmaDTO;
import cl.gob.scj.sgdp.dto.SubirArhivoDTO;
import cl.gob.scj.sgdp.dto.rest.AsignacionesNumerosDocDto;
import cl.gob.scj.sgdp.dto.rest.RespuestaAsignacionesNumerosDocDto;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.exception.SgdpExceptionValidaTareaEnBE;
import cl.gob.scj.sgdp.model.AsignacionesNumerosDoc;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.service.GestorDeDocumentosService;
import cl.gob.scj.sgdp.service.SubirArchivoService;
import cl.gob.scj.sgdp.tipos.FirmaType;
import cl.gob.scj.sgdp.ws.rest.service.AsignacionNumeroDocService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AsignacionNumeroDocServiceImpl implements
		AsignacionNumeroDocService {

	private static final Logger log = Logger.getLogger(AsignacionNumeroDocServiceImpl.class);	

	@Autowired
	AsignacionNumeroDocDao asignacionesNumerosDocDao;
	
	@Autowired
	GestorDeDocumentosService gestorDeDocumentosService;
	
	@Autowired
	SubirArchivoService subirArchivoService;
	
	private FirmaType firmaTypeWebStart = FirmaType.WEB_START;
	
	@Override
	public RespuestaAsignacionesNumerosDocDto guardarAsignacionDocumento(AsignacionesNumerosDocDto asignacionesNumerosDocDto) {
		
		log.info("Inicio guardarAsignacionDocumento...");
		log.info(asignacionesNumerosDocDto.toString());

		RespuestaAsignacionesNumerosDocDto  respuestaAsignacionesNumerosDocDto = new RespuestaAsignacionesNumerosDocDto();
		// Se extrae el ano de la fecha actual
		
		Date date = new Date();
		//LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		//int year  = localDate.getYear();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		asignacionesNumerosDocDto.setAnio(year + "");
		
		AsignacionesNumerosDoc asignacionesNumerosDoc = new AsignacionesNumerosDoc();
        // -----
		String respuesta = this.buscarDocumentoAsignar(asignacionesNumerosDocDto);
	
		log.info("respuesta: " + respuesta);
		
		if (respuesta.equals("OK")) {
					
			if (!asignacionesNumerosDocDto.getEstado().equals(Constantes.NOMBRE_ESTADO_FIRMADO) && 
				!asignacionesNumerosDocDto.getEstado().equals(Constantes.NOMBRE_ESTADO_RESERVADO) &&
				!asignacionesNumerosDocDto.getEstado().equals("")) {
					
				// Se ingresar los valores a insertar
				asignacionesNumerosDoc.setEstado(Constantes.NOMBRE_ESTADO_RESERVADO);
				asignacionesNumerosDoc.setAnio(asignacionesNumerosDocDto.getAnio());
				asignacionesNumerosDoc.setFechaCreacion(new Timestamp(new Date().getTime()));
				asignacionesNumerosDoc.setNumeroDocumento(asignacionesNumerosDocDto.getUltimoNumeroDocumentoExtraido()+1);				
				TipoDeDocumento tipoDeDocumento = new TipoDeDocumento();
				tipoDeDocumento.setIdTipoDeDocumento(asignacionesNumerosDocDto.getTipoDeDocumento());
				asignacionesNumerosDoc.setTipoDeDocumento(tipoDeDocumento);
				
				// Se completa los datos de salida del DTO					
				respuestaAsignacionesNumerosDocDto.setRespuesta(asignacionesNumerosDocDao.guardarAsignacionDocumento(asignacionesNumerosDoc));
				respuestaAsignacionesNumerosDocDto.setNumeroDocumento(asignacionesNumerosDocDto.getUltimoNumeroDocumentoExtraido()+1);
				respuestaAsignacionesNumerosDocDto.setIdAsignacionNumeroDoc(asignacionesNumerosDoc.getIdAsignacionNumeroDoc());					
				log.debug(respuestaAsignacionesNumerosDocDto.toString());
				return respuestaAsignacionesNumerosDocDto;							
										
			} else {
				
					try {
						// Se asigna el id Al model para que se modifique los registros 
						
						AsignacionesNumerosDoc asignacionesNumerosDocExist = asignacionesNumerosDocDao.getAsignacionesNumerosDocPorId(asignacionesNumerosDocDto.getIdAsignacionNumeroDoc());

						//asignacionesNumerosDoc.setIdAsignacionNumeroDoc(asignacionesNumerosDocDto.getIdAsignacionNumeroDoc());
						asignacionesNumerosDocExist.setEstado(Constantes.NOMBRE_ESTADO_RESERVADO);
						asignacionesNumerosDocExist.setAnio(asignacionesNumerosDocDto.getAnio());
						// asignacionesNumerosDocExist.setFechaCreacion(new Timestamp(new Date().getTime()));
						asignacionesNumerosDocExist.setFechaModificacion(new Timestamp(new Date().getTime()));
						//asignacionesNumerosDocExist.setNumeroDocumento(asignacionesNumerosDocDto.getUltimoNumeroDocumentoExtraido());
						//JRT: sumo uno
						asignacionesNumerosDocExist.setNumeroDocumento(asignacionesNumerosDocDto.getUltimoNumeroDocumentoExtraido()+1);				
						TipoDeDocumento tipoDeDocumento = new TipoDeDocumento();
						tipoDeDocumento.setIdTipoDeDocumento(asignacionesNumerosDocDto.getTipoDeDocumento());
						asignacionesNumerosDocExist.setTipoDeDocumento(tipoDeDocumento);
						//return asignacionesNumerosDocDao.ModificarAsignacionDocumento(asignacionesNumerosDoc);
						
						// Se completa los datos de salida del DTO
						
						respuestaAsignacionesNumerosDocDto.setRespuesta("OK");
						respuestaAsignacionesNumerosDocDto.setNumeroDocumento(asignacionesNumerosDocDto.getUltimoNumeroDocumentoExtraido());
						//JRT: sumo uno
						respuestaAsignacionesNumerosDocDto.setNumeroDocumento(asignacionesNumerosDocDto.getUltimoNumeroDocumentoExtraido()+1);
						respuestaAsignacionesNumerosDocDto.setIdAsignacionNumeroDoc(asignacionesNumerosDocDto.getIdAsignacionNumeroDoc());
						log.debug(respuestaAsignacionesNumerosDocDto.toString());
						return respuestaAsignacionesNumerosDocDto;		
						
					} catch (Exception e) {
						
						log.error("GuardarAsignacionDocumento " + e.getMessage());
						respuestaAsignacionesNumerosDocDto.setRespuesta("Error");
						log.debug(respuestaAsignacionesNumerosDocDto.toString());
						return respuestaAsignacionesNumerosDocDto;		
						
					}
				
				}		
						
		} else {			
				asignacionesNumerosDoc.setEstado(Constantes.NOMBRE_ESTADO_RESERVADO);
				asignacionesNumerosDoc.setAnio(asignacionesNumerosDocDto.getAnio());
				asignacionesNumerosDoc.setFechaCreacion(new Timestamp(new Date().getTime()));
				asignacionesNumerosDoc.setNumeroDocumento(1);
			
				TipoDeDocumento tipoDeDocumento = new TipoDeDocumento();
				tipoDeDocumento.setIdTipoDeDocumento(asignacionesNumerosDocDto.getTipoDeDocumento());
				asignacionesNumerosDoc.setTipoDeDocumento(tipoDeDocumento);
							
				// Se completa los datos de salida del DTO
				
				respuestaAsignacionesNumerosDocDto.setRespuesta(asignacionesNumerosDocDao.guardarAsignacionDocumento(asignacionesNumerosDoc));
				respuestaAsignacionesNumerosDocDto.setNumeroDocumento(1);
				respuestaAsignacionesNumerosDocDto.setIdAsignacionNumeroDoc(asignacionesNumerosDoc.getIdAsignacionNumeroDoc());
				log.debug(respuestaAsignacionesNumerosDocDto.toString());
				return respuestaAsignacionesNumerosDocDto;
					
		}
				
	}

	@Override
	public String buscarDocumentoAsignar(AsignacionesNumerosDocDto asignacionesNumerosDocDto) {
		
		log.info("Inicio buscarDocumentoAsignar...");
		log.info(asignacionesNumerosDocDto.toString());

		//AsignacionesNumerosDoc asignacionesNumerosDoc = new AsignacionesNumerosDoc();
		
		AsignacionesNumerosDoc asignacionesNumerosDoc;
		
		// Se busca el id de un documento que fue reservado pero no utilizado por algun error o problema
		asignacionesNumerosDoc = asignacionesNumerosDocDao.buscarIdNumeroDocumentoReservadoNoUtilizado(asignacionesNumerosDocDto);
		
		if (asignacionesNumerosDoc != null){
			asignacionesNumerosDocDto.setUltimoNumeroDocumentoExtraido(asignacionesNumerosDoc.getNumeroDocumento());
			asignacionesNumerosDocDto.setIdAsignacionNumeroDoc(asignacionesNumerosDoc.getIdAsignacionNumeroDoc());
			asignacionesNumerosDocDto.setEstado(asignacionesNumerosDoc.getEstado());
			log.info(asignacionesNumerosDocDto.toString());
			log.info("Retornando OK");
		   	return "OK";
		}else{
			// Se busca el id del ultimo documento asignado
			//	AsignacionesNumerosDoc asignacionesNumerosDoc2 = new AsignacionesNumerosDoc();		
			asignacionesNumerosDoc = asignacionesNumerosDocDao.buscarIdNumeroDocumentoFirmado(asignacionesNumerosDocDto);			
			if (asignacionesNumerosDoc != null){
				asignacionesNumerosDocDto.setUltimoNumeroDocumentoExtraido(asignacionesNumerosDoc.getNumeroDocumento());
				asignacionesNumerosDocDto.setIdAsignacionNumeroDoc(asignacionesNumerosDoc.getIdAsignacionNumeroDoc());
				asignacionesNumerosDocDto.setEstado(asignacionesNumerosDoc.getEstado());
				log.debug(asignacionesNumerosDocDto.toString());
				log.debug("Retornando OK");
				return "OK";
			}else {
				log.debug("Retornando PrimerRegistroAnio");
				return "PrimerRegistroAnio";
			}
			
		}		
		
	}

	@Override
	public RespuestaAsignacionesNumerosDocDto modicarAsignacionDocumento(AsignacionesNumerosDocDto asignacionesNumerosDocDto) throws SgdpException, SgdpExceptionValidaTareaEnBE {

		log.info("Inicio modicarAsignacionDocumento");
		log.info(asignacionesNumerosDocDto.toString());
		
		RespuestaAsignacionesNumerosDocDto respuestaAsignacionesNumerosDocDto= new RespuestaAsignacionesNumerosDocDto();
		AsignacionesNumerosDoc asignacionesNumerosDoc= new AsignacionesNumerosDoc();
		
		// Obtengo el registro del numero de solicitud 
		
		asignacionesNumerosDoc = asignacionesNumerosDocDao.buscarAsignacionDocumentoPorId(asignacionesNumerosDocDto);
		
		SubirArhivoDTO subirArhivoDTO = new SubirArhivoDTO();
		Usuario usuario = new Usuario();		
			
		if (asignacionesNumerosDocDto.getCodigoMensaje().equals("OK") ){
			asignacionesNumerosDoc.setEstado(Constantes.NOMBRE_ESTADO_FIRMADO);
			asignacionesNumerosDoc.setFechaModificacion(new Timestamp(new Date().getTime()));		
			log.debug("Registrando HistoricoFirma");		
			HistoricoFirmaDTO historicoFirmaDTO = new HistoricoFirmaDTO();
			historicoFirmaDTO.setFechaFirma(new Date());
			historicoFirmaDTO.setIdArchivoCMS(asignacionesNumerosDocDto.getIdArchivo());
			historicoFirmaDTO.setIdInstanciaDeTarea(asignacionesNumerosDocDto.getIdInstanciaDeTarea());
			historicoFirmaDTO.setIdUsuario(asignacionesNumerosDocDto.getIdUsuario());
			historicoFirmaDTO.setTipoFirma(firmaTypeWebStart);
			historicoFirmaDTO.setIdTipoDeDocumento(asignacionesNumerosDocDto.getTipoDeDocumento());
			gestorDeDocumentosService.registraFirma(asignacionesNumerosDocDto.getIdUsuario(), historicoFirmaDTO);			
			usuario.setIdUsuario(asignacionesNumerosDocDto.getIdUsuario());
			usuario.setAlfTicket(asignacionesNumerosDocDto.getAlfTicket());
			subirArhivoDTO.setIdArchivoEnCMS(asignacionesNumerosDocDto.getIdArchivo());
			subirArhivoDTO.setIdTipoDeDocumentoSubir(asignacionesNumerosDocDto.getTipoDeDocumento());
			subirArhivoDTO.setIdInstanciaDeTareaSubirArchivo(asignacionesNumerosDocDto.getIdInstanciaDeTarea());
			subirArchivoService.marcarArchivoComoSubido(usuario, subirArhivoDTO);
			respuestaAsignacionesNumerosDocDto.setRespuesta("OkModificacion");
		} else if (asignacionesNumerosDocDto.getCodigoMensaje().equals("ERROR") ){
			asignacionesNumerosDoc.setEstado("");
			asignacionesNumerosDoc.setFechaModificacion(new Timestamp(new Date().getTime()));
			respuestaAsignacionesNumerosDocDto.setRespuesta("OkModificacion");
		}
		
		log.info(respuestaAsignacionesNumerosDocDto.toString());
		return respuestaAsignacionesNumerosDocDto;
	}

	
	
	@Override
	public RespuestaAsignacionesNumerosDocDto cambiarStatusFirmaAvanzada(
			AsignacionesNumerosDocDto asignacionesNumerosDocDto)
			throws SgdpException, SgdpExceptionValidaTareaEnBE {
		
		
		
			SubirArhivoDTO subirArhivoDTO = new SubirArhivoDTO();
			Usuario usuario = new Usuario();		
			RespuestaAsignacionesNumerosDocDto respuestaAsignacionesNumerosDocDto= new RespuestaAsignacionesNumerosDocDto();
			
			
			HistoricoFirmaDTO historicoFirmaDTO = new HistoricoFirmaDTO();
			historicoFirmaDTO.setFechaFirma(new Date());
			historicoFirmaDTO.setIdArchivoCMS(asignacionesNumerosDocDto.getIdArchivo());
			historicoFirmaDTO.setIdInstanciaDeTarea(asignacionesNumerosDocDto.getIdInstanciaDeTarea());
			historicoFirmaDTO.setIdUsuario(asignacionesNumerosDocDto.getIdUsuario());
			historicoFirmaDTO.setTipoFirma(firmaTypeWebStart);
			historicoFirmaDTO.setIdTipoDeDocumento(asignacionesNumerosDocDto.getTipoDeDocumento());
			historicoFirmaDTO.setIdDocumentoFirmado(asignacionesNumerosDocDto.getIdDocumentoFirmado());
			
			historicoFirmaDTO.setUuid(asignacionesNumerosDocDto.getUuid());
			
			//gestorDeDocumentosService.registraFirma(asignacionesNumerosDocDto.getIdUsuario(), historicoFirmaDTO);			
			usuario.setIdUsuario(asignacionesNumerosDocDto.getIdUsuario());
			usuario.setAlfTicket(asignacionesNumerosDocDto.getAlfTicket());
			subirArhivoDTO.setIdArchivoEnCMS(asignacionesNumerosDocDto.getIdArchivo());
			subirArhivoDTO.setIdTipoDeDocumentoSubir(asignacionesNumerosDocDto.getTipoDeDocumento());
			subirArhivoDTO.setIdInstanciaDeTareaSubirArchivo(asignacionesNumerosDocDto.getIdInstanciaDeTarea());
			//Esto ya no es necesario ya que  subirArchivoDirectoCMS usa subirArchivoService.subirArchivo el cual marca el archivo como subido
			//subirArchivoService.marcarArchivoComoSubido(usuario, subirArhivoDTO);
			gestorDeDocumentosService.registraFirma(asignacionesNumerosDocDto.getIdUsuario(), historicoFirmaDTO);
			respuestaAsignacionesNumerosDocDto.setRespuesta("OkModificacion");
	
			return respuestaAsignacionesNumerosDocDto;
	}
	

}
