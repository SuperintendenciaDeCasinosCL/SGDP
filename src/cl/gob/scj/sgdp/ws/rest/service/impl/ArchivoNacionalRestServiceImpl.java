package cl.gob.scj.sgdp.ws.rest.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.internal.org.bouncycastle.asn1.cms.TimeStampAndCRL;

import cl.gob.scj.sgdp.dao.ArchivoNacionalRestDao;
import cl.gob.scj.sgdp.dto.DestinatarioGrupoDTO;
import cl.gob.scj.sgdp.dto.rest.TiposDocumetosArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.rest.TiposDocumetosArchivoNacionalResponse;
import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.ws.rest.service.ArchivoNacionalRestService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ArchivoNacionalRestServiceImpl implements ArchivoNacionalRestService{

	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	ArchivoNacionalRestDao archivoNacionalRestDao;
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Override
	public TiposDocumetosArchivoNacionalResponse buscarTiposDocumetosArchivoNacional(String nombreExpediente) {
		
		TiposDocumetosArchivoNacionalResponse tiposDocumetosArchivoNacionalResponse = new TiposDocumetosArchivoNacionalResponse();
		try {
			//logger.info("ListaDoc: "+archivoNacionalRestDao.buscarTiposDocumetosArchivoNacionalDao(nombreExpediente));
			List<TiposDocumetosArchivoNacionalDTO> respDao = archivoNacionalRestDao.buscarTiposDocumetosArchivoNacionalDao(nombreExpediente);
			
			Iterator itr = respDao.iterator();
			List<TiposDocumetosArchivoNacionalDTO> listaTiposDocumetosArchivoNacionalDTO = new ArrayList<>();
			while(itr.hasNext()){
				TiposDocumetosArchivoNacionalDTO tiposDocumetosArchivoNacionalDTO = new TiposDocumetosArchivoNacionalDTO();
				Object[] obj = (Object[]) itr.next();
				tiposDocumetosArchivoNacionalDTO.setNombreTipoDocumento((String) obj[0]);
				tiposDocumetosArchivoNacionalDTO.setIdArchivoCms((String) obj[1]);
				tiposDocumetosArchivoNacionalDTO.setConformaExpediente((Boolean) obj[2]);
				tiposDocumetosArchivoNacionalDTO.setNombreArchivo((String) obj[3]);
				//tiposDocumetosArchivoNacionalDTO.setFechaSubido( (Timestamp) obj[4]);
				
				Timestamp  time = (Timestamp) obj[4];
				logger.info("Time: "+time);
				String date=time.toString();
				logger.info("Date: "+date);
				tiposDocumetosArchivoNacionalDTO.setFechaSubido(date);
				listaTiposDocumetosArchivoNacionalDTO.add(tiposDocumetosArchivoNacionalDTO);				
			}
			
			
			
			if 	(listaTiposDocumetosArchivoNacionalDTO.size() == 0){
				tiposDocumetosArchivoNacionalResponse.setMensajeRespuesta("Sin Registros");
			}else{
				tiposDocumetosArchivoNacionalResponse.setListaTiposDocumetosArchivoNacionalDTO(listaTiposDocumetosArchivoNacionalDTO);
				tiposDocumetosArchivoNacionalResponse.setCodigoRespuesta(configProps.getProperty("respuestaOK"));
				tiposDocumetosArchivoNacionalResponse.setMensajeRespuesta("Registro encontrado");
			}	
			
			return tiposDocumetosArchivoNacionalResponse;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			logger.error(exceptionAsString);
			tiposDocumetosArchivoNacionalResponse.setMensajeRespuesta("ERROR al obtener documentos ");
			//logger.debug("error buscarTiposDocumetosArchivoNacional " + e.getMessage());
			
			return tiposDocumetosArchivoNacionalResponse;
		}
	}

}
