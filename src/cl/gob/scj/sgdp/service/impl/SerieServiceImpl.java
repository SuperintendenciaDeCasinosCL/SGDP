package cl.gob.scj.sgdp.service.impl;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dto.SerieDTO;
import cl.gob.scj.sgdp.dto.TokenDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
import cl.gob.scj.sgdp.service.ArchivoNacionalService;
import cl.gob.scj.sgdp.service.SerieService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class SerieServiceImpl implements SerieService {

	private static final Logger log = Logger.getLogger(SerieServiceImpl.class);

	@Resource(name = "configProps")
	private Properties configProps;

	@Autowired
	private ArchivoNacionalService archivoNacionalService;

	@Override
	public List<SerieDTO> getSeries() throws ArchivoNacionalException {
		log.info("Inicio getSeries");
		List<SerieDTO> list = null;

		TokenDTO token = this.archivoNacionalService.login();
		list = this.archivoNacionalService.getSeries(token);
		log.info("Fin getSeries");
		return list;
	}

}