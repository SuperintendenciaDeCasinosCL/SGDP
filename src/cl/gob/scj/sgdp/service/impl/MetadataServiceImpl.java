package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.dto.MetadataDTO;
import cl.gob.scj.sgdp.enums.TipoEnum;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.service.MetadataService;
import cl.gob.scj.sgdp.util.FechaUtil;

@Service
@Transactional(rollbackFor = Throwable.class)
public class MetadataServiceImpl implements MetadataService {

	private static final Logger log = Logger.getLogger(MetadataServiceImpl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private InstanciaDeProcesoDao instanciaDeProcesoDao;

	@Override
	public List<MetadataDTO> getMetadataByIdExpediente(String idExpediente) throws ArchivoNacionalException {
		List<MetadataDTO> list = new ArrayList<MetadataDTO>();
		try {
			InstanciaDeProceso entity = this.instanciaDeProcesoDao.getInstanciaDeProcesoPorIdExpediente(idExpediente);
			
			log.info(entity);
			MetadataDTO dto = null;
			if (entity != null ) {
				for (InstanciaDeTarea tarea : entity.getInstanciasDeTareas()) {
					if (tarea.getArchivosInstDeTarea() != null && !tarea.getArchivosInstDeTarea().isEmpty()) {
						for (ArchivosInstDeTarea archivo : tarea.getArchivosInstDeTarea()) {
							if (archivo.getArchivosInstDeTareaMetadata() != null) {
								dto = new MetadataDTO();
								dto.setComposicionDocumental(archivo.getArchivosInstDeTareaMetadata().getTipo().getNombreTipo());
								if (archivo.getArchivosInstDeTareaMetadata().getTipo().getIdTipo() == TipoEnum.DOCUMENTO.getId()) {
									//Info desde InstanciaProcesoMetadata
									dto.setComuna(archivo.getArchivosInstDeTareaMetadata().getComuna());
									dto.setRegion(archivo.getArchivosInstDeTareaMetadata().getRegion());
									dto.setFechaDocumento(FechaUtil.toFormat(
											archivo.getArchivosInstDeTareaMetadata().getFechaDocumento(),
											FechaUtil.simpleDateFormatForm));
									
									dto.setTitulo(archivo.getArchivosInstDeTareaMetadata().getTitulo());
								} else {
									//Info desde ArchivosInstDeTarea
									dto.setTitulo(entity.getInstanciaProcesoMetadata().getTitulo());
									dto.setComuna(entity.getInstanciaProcesoMetadata().getComuna());
									dto.setRegion(entity.getInstanciaProcesoMetadata().getRegion());
									dto.setFechaDocumento(FechaUtil.toFormat(
											entity.getInstanciaProcesoMetadata().getFechaCreacion(),
											FechaUtil.simpleDateFormatForm));
								}
								list.add(dto);
							}
							log.info(archivo);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ArchivoNacionalException(this.configProps.getProperty("errorAlObtenerMetadata"), e);
		}
		return list;
	}
	
	
	


}