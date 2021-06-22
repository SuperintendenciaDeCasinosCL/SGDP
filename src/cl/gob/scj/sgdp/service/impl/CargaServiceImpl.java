package cl.gob.scj.sgdp.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.CargaDao;
import cl.gob.scj.sgdp.dto.CargaDTO;
import cl.gob.scj.sgdp.dto.EnviarArchivoNacionalDTO;
import cl.gob.scj.sgdp.dto.EstadoTransferenciaDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
import cl.gob.scj.sgdp.model.Carga;
import cl.gob.scj.sgdp.service.CargaService;
import cl.gob.scj.sgdp.util.FechaUtil;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CargaServiceImpl implements CargaService {

	private static final Logger log = Logger.getLogger(CargaServiceImpl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private CargaDao cargaDao;

	@Override
	public CargaDTO guardarCargaArchivoNacional(EnviarArchivoNacionalDTO dto, long cantidadArchivos, String idTransferencia) throws ArchivoNacionalException {
		CargaDTO carga = new CargaDTO();
		try {
			Carga entity = new Carga();
			entity.setANombreAcuerdo(dto.getNombreAcuerdo());
			entity.setANombreSerie(dto.getNombreSerie());
			entity.setATipoAcuerdo(dto.getEstadoAcuerdo());
			entity.setDFechaCreacion(new Date());
			entity.setNCantidadDocumentos(cantidadArchivos);
			entity.setaIdTransferencia(idTransferencia);
			this.cargaDao.guardarCargaArchivoNacional(entity);
			carga.setIdCarga(entity.getIdCarga());
			carga.setIdTransferencia(idTransferencia);
			return carga;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof ArchivoNacionalException) {
				throw (ArchivoNacionalException)e;
			}
			throw new ArchivoNacionalException(configProps.getProperty("errorAlGuardarCarga"));
		}
		
	}

	@Override
	public List<EstadoTransferenciaDTO> getResultadoBusquedaEstadoTransferencia() throws ArchivoNacionalException {
		List<EstadoTransferenciaDTO> list = new ArrayList<EstadoTransferenciaDTO>();
		try {
			List<Carga> enityList = this.cargaDao.getResultadoBusquedaEstadoTransferencia();
			//EstadoTransferenciaDTO dto = null;
			for (Carga carga : enityList) {
				EstadoTransferenciaDTO dto = new EstadoTransferenciaDTO();
				dto.setIdCarga(carga.getIdCarga());
				dto.setNombreAcuerdo(carga.getANombreAcuerdo());
				dto.setFechaTransferencia(FechaUtil.toFormat(carga.getDFechaCreacion(), FechaUtil.simpleDateFormatFormHHMMSS));
				dto.setTotalArchivos(carga.getNCantidadDocumentos());
				if (carga.getDetallesCargas() != null && !carga.getDetallesCargas().isEmpty()) {
					dto.setTotalArchivoEnviado(carga.getDetallesCargas().size());
				}
				dto.setTotalArchivoNoEnviado(carga.getNCantidadDocumentos() - dto.getTotalArchivoEnviado());
				if (carga.getCargasLog() != null && !carga.getCargasLog().isEmpty() ) {
					dto.setDescripcionEstado("Carga con Errores");
				} else {
					
					boolean flag = dto.getTotalArchivoEnviado() == dto.getTotalArchivos();
					if (flag) {
						dto.setDescripcionEstado("Transferencia Completada");
					} else {
						dto.setDescripcionEstado("Transferencia en Curso");
					}	
				}
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof ArchivoNacionalException) {
				throw (ArchivoNacionalException)e;
			}
			throw new ArchivoNacionalException(configProps.getProperty("errorAlBuscarCarga"));
		}
	}
	


}