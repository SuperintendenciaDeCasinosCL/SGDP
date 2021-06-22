package cl.gob.scj.sgdp.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.ParametroArchivoNacionalDao;
import cl.gob.scj.sgdp.dto.AccesoArchivoNacionalDTO;
import cl.gob.scj.sgdp.enums.ParametroArchivoNacionalEnum;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.model.ParametroArchivoNacional;
import cl.gob.scj.sgdp.service.AccesoTransferenciaService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AccesoTransferenciaServiceImpl implements AccesoTransferenciaService {

	private static final Logger log = Logger.getLogger(AccesoTransferenciaServiceImpl.class);	
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	@Autowired
	private ParametroArchivoNacionalDao parametroArchivoNacionalDao;
	

	@Override
	public String guardarAccesoArchivoNacional(AccesoArchivoNacionalDTO accesoArchivoNacionalDTO, Usuario usuario)
			throws SgdpException {
		try {
			guardarAccesoArchivoNacional(accesoArchivoNacionalDTO.getUsuarioSftpArchivo(), ParametroArchivoNacionalEnum.USER_SFTP);
			guardarAccesoArchivoNacional(accesoArchivoNacionalDTO.getPassSftpArchivo(), ParametroArchivoNacionalEnum.PASS_SFTP);
			guardarAccesoArchivoNacional(accesoArchivoNacionalDTO.getUsuarioArchivo(), ParametroArchivoNacionalEnum.USER_API);
			guardarAccesoArchivoNacional(accesoArchivoNacionalDTO.getPassArchivo(), ParametroArchivoNacionalEnum.PASS_API);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof SgdpException) {
				throw (SgdpException)e;
			}
			throw new SgdpException(configProps.getProperty("errorAlGuardarAccesoArchivoNacional"));
		}

		return configProps.getProperty("respuestaOK");
	}

	private void guardarAccesoArchivoNacional(String valorParametro,
			ParametroArchivoNacionalEnum parametro) {
		ParametroArchivoNacional entity = null;
	
		entity = parametroArchivoNacionalDao.getParametroArchivoNacionalByCodigo(parametro.getCodigo());
		if (entity == null) {
			entity = new ParametroArchivoNacional();
			entity.setNombreParametro(parametro.getCodigo());
			entity.setValorParametroChar(valorParametro);
			entity.setFechaCreacion(new Date());
			this.parametroArchivoNacionalDao.insertarParametroArchivoNacional(entity);
		} else {
			entity.setNombreParametro(parametro.getCodigo());
			entity.setValorParametroChar(valorParametro);
			entity.setFechaActualizacion(new Date());
		}
	}

	@Override
	public String guardarConfigracionInstitucion(AccesoArchivoNacionalDTO accesoArchivoNacionalDTO, Usuario usuario)
			throws SgdpException {
		try {
			guardarAccesoArchivoNacional(accesoArchivoNacionalDTO.getCodigoInstitucion(), ParametroArchivoNacionalEnum.CODIGO_INSTITUCION);
			guardarAccesoArchivoNacional(accesoArchivoNacionalDTO.getNombreInstitucion(), ParametroArchivoNacionalEnum.NOMBRE_INSTITUCION);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
			log.error(e.getMessage(), e);
			e.printStackTrace();
			if (e instanceof SgdpException) {
				throw (SgdpException)e;
			}
			throw new SgdpException(configProps.getProperty("errorAlGuardarInstitucion"));
		}

		return configProps.getProperty("respuestaOK");
	}
}