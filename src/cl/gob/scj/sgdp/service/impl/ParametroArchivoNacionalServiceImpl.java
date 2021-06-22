package cl.gob.scj.sgdp.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.ParametroArchivoNacionalDao;
import cl.gob.scj.sgdp.dto.ConfiguracionArchivoNacionalDTO;
import cl.gob.scj.sgdp.enums.ParametroArchivoNacionalEnum;
import cl.gob.scj.sgdp.model.ParametroArchivoNacional;
import cl.gob.scj.sgdp.service.ParametroArchivoNacionalService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ParametroArchivoNacionalServiceImpl implements ParametroArchivoNacionalService {
	
	private static final Logger log = Logger.getLogger(ParametroArchivoNacionalServiceImpl.class);
	
	@Autowired
	private ParametroArchivoNacionalDao parametroArchivoNacionalDao;

	@Override
	public ConfiguracionArchivoNacionalDTO getConfiguracionLoginArchivoNacional() {
		ConfiguracionArchivoNacionalDTO dto = new ConfiguracionArchivoNacionalDTO();
		log.info("Inicio getConfiguracionLoginArchivoNacional");
		ParametroArchivoNacional entityUser = this.parametroArchivoNacionalDao
				.getParametroArchivoNacionalByCodigo(ParametroArchivoNacionalEnum.USER_API.getCodigo());
		if (entityUser != null ) {
			dto.setUsuarioArchivo(entityUser.getValorParametroChar());
		}
		ParametroArchivoNacional entityPass = this.parametroArchivoNacionalDao
				.getParametroArchivoNacionalByCodigo(ParametroArchivoNacionalEnum.PASS_API.getCodigo());
		if (entityPass != null ) {
			dto.setPassArchivo(entityPass.getValorParametroChar());
		}
		log.info("Fin getConfiguracionLoginArchivoNacional");
		return dto;
	}

	@Override
	public ConfiguracionArchivoNacionalDTO getConfiguracionCodigoInstitucion() {
		ConfiguracionArchivoNacionalDTO dto = new ConfiguracionArchivoNacionalDTO();
		log.info("Inicio getConfiguracionCodigoInstitucion");
		ParametroArchivoNacional entity = this.parametroArchivoNacionalDao
				.getParametroArchivoNacionalByCodigo(ParametroArchivoNacionalEnum.CODIGO_INSTITUCION.getCodigo());
		if (entity != null ) {
			dto.setCodigoInstitucion(entity.getValorParametroChar());
		}
		entity = this.parametroArchivoNacionalDao
				.getParametroArchivoNacionalByCodigo(ParametroArchivoNacionalEnum.NOMBRE_INSTITUCION.getCodigo());
		if (entity != null) {
			dto.setNombreInstitucion(entity.getValorParametroChar());
		}
		log.info("Fin getConfiguracionCodigoInstitucion");
		return dto;
	}	
	
	
}
