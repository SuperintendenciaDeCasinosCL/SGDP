package cl.gob.scj.sgdp.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaMetadataDao;
import cl.gob.scj.sgdp.dao.HistoricoArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.DocumentoDTO;
import cl.gob.scj.sgdp.dto.EnviarArchivoNacionalDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.ArchivosInstDeTareaMetadata;
import cl.gob.scj.sgdp.service.ArchivosInstDeTareaService;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.SGDPUtil;
import cl.gob.scj.sgdp.util.UtilArchivosTarea;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ArchivosInstDeTareaServiceImpl implements ArchivosInstDeTareaService {

	private static final Logger log = Logger.getLogger(ArchivosInstDeTareaServiceImpl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
    private ArchivosInstDeTareaDao archivosInstDeTareaDao;
	
	@Autowired
	private HistoricoArchivosInstDeTareaDao historicoArchivosInstDeTareaDao;
	
	@Autowired
	private ArchivosInstDeTareaMetadataDao archivoInstTareaMetadataDAO;
	
	@Override
	public List<String> getIdDeDocumentosSubidosOficiales() {
		// TODO Auto-generated method stub
		return archivosInstDeTareaDao.getIdDeDocumentosSubidosOficiales();
	}
	
	@Override
	public List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdExpediente(String idExpediente) {		
		List<ArchivosInstDeTareaDTO> archivosInstDeTareaDTOList = archivosInstDeTareaDao.getTodosLosDocSubidosPorIdExpediente(idExpediente);		
		List<ArchivosInstDeTareaDTO> archivosInstDeTareaDTOListHistoricoOrigen = historicoArchivosInstDeTareaDao.getTodosLosDocSubidosPorIdExpedienteHistoricoOrigen(idExpediente);
		SGDPUtil.agregaArchivosInstDeTareaDTOSiNoEstaEnDestino(archivosInstDeTareaDTOListHistoricoOrigen, archivosInstDeTareaDTOList);
		return archivosInstDeTareaDTOList;
	}
	
	@Override
	public ArchivosInstDeTareaDTO getArchivosInstDeTareaPorIdArchivoIdInstanciaDeTareaIdUsuario(String idArchivo, long idInstanciaDeTarea, String idUsuario) {		
		ArchivosInstDeTarea archivosInstDeTarea = archivosInstDeTareaDao.getArchivosInstDeTareaPorIdArchivoIdInstanciaDeTareaIdUsuario(idArchivo, idInstanciaDeTarea, idUsuario);
		ArchivosInstDeTareaDTO archivosInstDeTareaDTO = new ArchivosInstDeTareaDTO();		
		BeanUtils.copyProperties(archivosInstDeTarea, archivosInstDeTareaDTO);
		return archivosInstDeTareaDTO;
	}
	
	
	

	@Override
	public List<ArchivosInstDeTareaDTO> getArchivosInstDeTareaPorIdSerie(long idSerie, Long idEstadoProceso, Long estadoDocumento, String fechaTransferirInicio, String fechaTransferirTermino) {
		List<ArchivosInstDeTareaDTO> list = new ArrayList<ArchivosInstDeTareaDTO>();
		List<ArchivosInstDeTareaDTO> listaRealSalida = new ArrayList<ArchivosInstDeTareaDTO>();
		ArchivosInstDeTareaDTO dto = null;
		List<ArchivosInstDeTarea> archivosInstDeTareaList = this.archivosInstDeTareaDao.getArchivosInstDeTareaPorIdSerie(idSerie, idEstadoProceso, estadoDocumento, fechaTransferirInicio, fechaTransferirTermino);
		log.info("########## Archivos elegibles para tranferir PREVIOS A FIX ##################");
		for (ArchivosInstDeTarea entity : archivosInstDeTareaList) {
			if (entity.getArchivosInstDeTareaMetadata() != null) {
				dto = new ArchivosInstDeTareaDTO();
				dto.setNombreArchivo(entity.getNombreArchivo());
				dto.setIdArchivoMetadataTarea(entity.getArchivosInstDeTareaMetadata().getIdArchivosInstDeTareaMetadata());
				dto.setArchivoMetadata(entity.getArchivosInstDeTareaMetadata());
				log.info("ID ARCHIVO:"+entity.getIdArchivosInstDeTarea()+", NOMBRE ARCHIVO:"+entity.getNombreArchivo());
				list.add(dto);
			}
		}
		log.info("###### FIN--> Archivos elegibles para tranferir PREVIOS A FIX  ####################");
		
		List<ArchivosInstDeTarea> masiveFix = UtilArchivosTarea.masiveFix(archivosInstDeTareaList);
		
		for (ArchivosInstDeTarea archivosInstDeTarea: masiveFix) {
	    	//ArchivosInstDeTarea archivosInstDeTarea = hashArchivosUnicos.get(llave);
	    	ArchivosInstDeTareaDTO dtoReal = new ArchivosInstDeTareaDTO();
	    	dtoReal.setNombreArchivo(archivosInstDeTarea.getNombreArchivo());
	    	dtoReal.setIdArchivoInstanciaTarea(archivosInstDeTarea.getIdArchivosInstDeTarea());
	    	dtoReal.setIdArchivoMetadataTarea(archivosInstDeTarea.getArchivosInstDeTareaMetadata().getIdArchivosInstDeTareaMetadata());
	    	dtoReal.setArchivoMetadata(archivosInstDeTarea.getArchivosInstDeTareaMetadata());
	    	listaRealSalida.add(dtoReal);
	    }
		
		log.info("########## Archivos elegibles para tranferir FIX!!!!!!!!!!!!!! ##################");
		for (ArchivosInstDeTareaDTO arch: listaRealSalida) {
			log.info("ID ARCHIVO:"+arch.getIdArchivoInstanciaTarea()+", NOMBRE ARCHIVO:"+arch.getNombreArchivo());
		}
		log.info("########## Archivos elegibles para tranferir FIN FIX!!!!!!!!!!!!!! ##################");
		
		//aca se deberian marcar como enviados los descartables
		
		for (ArchivosInstDeTareaDTO archTodos: list) {
			boolean encontrado = false;
			for (ArchivosInstDeTareaDTO real: listaRealSalida) {
				if (archTodos.getIdArchivoMetadataTarea()==real.getIdArchivoMetadataTarea()) {
					encontrado = true;
					break;
				}
			}
			if (!encontrado) {
				ArchivosInstDeTareaMetadata archivoMetadata = archTodos.getArchivoMetadata();
				archivoMetadata.setFlagEnvio(1L);
				archivoInstTareaMetadataDAO.updateArchivosInstDeTareaMetada(archivoMetadata);
			}
		}
		
		
		return listaRealSalida;
	}

	@Override
	public List<DocumentoDTO> getMetadataListaDocumentos(EnviarArchivoNacionalDTO buscarDTO)
			throws ArchivoNacionalException {
		List<DocumentoDTO> list = new ArrayList<DocumentoDTO>();
		DocumentoDTO dto = null;
		try {
			List<ArchivosInstDeTarea> archivosInstDeTareaListPre = this.archivosInstDeTareaDao.getArchivosInstDeTareaPorNombreSerie(buscarDTO.getNombreSerie(), buscarDTO.getFechaTransferirInicio(), buscarDTO.getFechaTransferirTermino());
			List<ArchivosInstDeTarea> archivosInstDeTareaList = UtilArchivosTarea.masiveFix(archivosInstDeTareaListPre);
			
			for (ArchivosInstDeTarea entity : archivosInstDeTareaList) {
				if (entity.getArchivosInstDeTareaMetadata() != null) {
					dto = new DocumentoDTO();
					dto.setApellidoMaternoInteresado(entity.getArchivosInstDeTareaMetadata().getApellidoMaterno());
					dto.setApellidoPaternoInteresado(entity.getArchivosInstDeTareaMetadata().getApellidoPaterno());
					dto.setAutor(entity.getArchivosInstDeTareaMetadata().getAutor());
					dto.setComuna(entity.getArchivosInstDeTareaMetadata().getComuna());
					dto.setDestinatario(entity.getArchivosInstDeTareaMetadata().getDestinatarios());
					dto.setDocumentoDigitalizado(entity.getArchivosInstDeTareaMetadata().getDigitalizado().toString());
					dto.setEtiquetas(entity.getArchivosInstDeTareaMetadata().getEtiquetas());
					dto.setIdArchivoCms(entity.getIdArchivoCms());
					//TECNOVA: SE QUITA LA FECHA DE CAPTURA
//					if (entity.getArchivosInstDeTareaMetadata().getFechaCaptura() != null) {
//						dto.setFechaCapturaDocumento(FechaUtil.toFormat(entity.getArchivosInstDeTareaMetadata().getFechaCaptura(), FechaUtil.simpleDateFormatForm));	
//					}
					if (entity.getArchivosInstDeTareaMetadata().getFechaDocumento() != null) {
						dto.setFechaDocumento(FechaUtil.toFormat(entity.getArchivosInstDeTareaMetadata().getFechaDocumento(), FechaUtil.simpleDateFormatForm));	
					}
					dto.setFormato(entity.getMimeType());
					dto.setIdentificador(entity.getIdArchivoCms());
					if (entity.getInstanciaDeTarea() != null
							&& entity.getInstanciaDeTarea().getInstanciaDeProceso() != null
							&& entity.getInstanciaDeTarea().getInstanciaDeProceso().getAcceso() != null) {
						dto.setNivelAcceso(entity.getInstanciaDeTarea().getInstanciaDeProceso().getAcceso().getValorAccesoChar());
					}
					dto.setNombreArchivo(
							"/" + entity.getInstanciaDeTarea().getInstanciaDeProceso().getNombreExpediente() + "/"
									+ entity.getNombreArchivo());
					dto.setNombreInteresado(entity.getArchivosInstDeTareaMetadata().getNombreInteresado());
					dto.setRegion(entity.getArchivosInstDeTareaMetadata().getRegion());
					dto.setRutInteresado(entity.getArchivosInstDeTareaMetadata().getRut());
					dto.setTitulo(entity.getArchivosInstDeTareaMetadata().getTitulo());
					dto.setVolumen(0);
					list.add(dto);
				}
				
			}
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(this.configProps.getProperty("errorAlObtenerDocumentos"));
		}
		return list;
	}

	@Override
	public void descartarArchivo(String idDocumentoCms) throws ArchivoNacionalException {
		try {
			List<ArchivosInstDeTarea> list = this.archivosInstDeTareaDao.getArchivosPorIdArchivo(idDocumentoCms);
			for (ArchivosInstDeTarea entity : list) {
				if (entity.getArchivosInstDeTareaMetadata() != null) {
					entity.getArchivosInstDeTareaMetadata().setFlagEnvio(-1L);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			throw new ArchivoNacionalException(this.configProps.getProperty("errorAlDescartarArchivo"));
		}
		
	}
	
}