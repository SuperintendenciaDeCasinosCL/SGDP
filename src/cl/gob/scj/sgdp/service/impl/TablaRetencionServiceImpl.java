package cl.gob.scj.sgdp.service.impl;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.ParametroArchivoNacionalDao;
import cl.gob.scj.sgdp.dto.BuscarTablaRetencionDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaTablaDTO;
import cl.gob.scj.sgdp.dto.TablaRetencionDTO;
import cl.gob.scj.sgdp.dto.TokenDTO;
import cl.gob.scj.sgdp.enums.ParametroArchivoNacionalEnum;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
import cl.gob.scj.sgdp.model.ParametroArchivoNacional;
import cl.gob.scj.sgdp.service.ArchivoNacionalService;
import cl.gob.scj.sgdp.service.TablaRetencionService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class TablaRetencionServiceImpl implements TablaRetencionService {

	private static final Logger log = Logger.getLogger(TablaRetencionServiceImpl.class);
	@Resource(name = "configProps")
	private Properties configProps;

	@Autowired
	private ParametroArchivoNacionalDao parametroArchivoNacionalDao;
	
	@Autowired
	private ArchivoNacionalService archivoNacionalService;

	@Override
	public String getCodigoInstitucion() {
		log.info("Inicio getCodigoInstitucion");

		ParametroArchivoNacional entity = this.parametroArchivoNacionalDao
				.getParametroArchivoNacionalByCodigo(ParametroArchivoNacionalEnum.CODIGO_INSTITUCION.getCodigo());
		String codigoInstitucion = "";
		if (entity != null) {
			codigoInstitucion = entity.getValorParametroChar();
		}
		log.info("Fin getCodigoInstitucion");
		return codigoInstitucion;
	}

	@Override
	public ResultadoBusquedaTablaDTO getResultadoDeBusquedaTablaRetencion(BuscarTablaRetencionDTO buscarDTO)
			throws ArchivoNacionalException {
		ResultadoBusquedaTablaDTO dto = new ResultadoBusquedaTablaDTO();
		List<TablaRetencionDTO> list = null;
		TokenDTO token = this.archivoNacionalService.login();
		list = this.archivoNacionalService.getResultadoDeBusquedaTablaRetencion(token, buscarDTO);
		dto.setTablasDTO(list);
		return dto;
	}
}