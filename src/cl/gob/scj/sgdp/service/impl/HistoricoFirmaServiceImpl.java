package cl.gob.scj.sgdp.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.HistoricoFirmaDao;
import cl.gob.scj.sgdp.dto.HistoricoFirmaDTO;
import cl.gob.scj.sgdp.model.HistoricoFirma;
import cl.gob.scj.sgdp.service.HistoricoFirmaService;
import cl.gob.scj.sgdp.tipos.FirmaType;

@Service
@Transactional(rollbackFor = Throwable.class)
public class HistoricoFirmaServiceImpl implements HistoricoFirmaService {
	
	private static final Logger log = Logger.getLogger(HistoricoFirmaServiceImpl.class);
	
	@Autowired
	private HistoricoFirmaDao historicoFirmaDao;

	@Override
	public List<HistoricoFirmaDTO> getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario(HistoricoFirmaDTO historicoFirmaDTOConsulta) {
		List<HistoricoFirma> historicoFirmaList = historicoFirmaDao.getHistoricoFirmaDocumentoFEAPorIdInstanciaDeTareaIdUsuario( 
				historicoFirmaDTOConsulta.getIdInstanciaDeTarea(), historicoFirmaDTOConsulta.getIdUsuario());
		List<HistoricoFirmaDTO> historicoFirmaDTOList = new ArrayList<HistoricoFirmaDTO>();
		for (HistoricoFirma historicoFirma: historicoFirmaList) {
			HistoricoFirmaDTO historicoFirmaDTO = new HistoricoFirmaDTO();
			historicoFirmaDTO.setFechaFirma(historicoFirma.getFechaFirma());
			historicoFirmaDTO.setIdArchivoCMS(historicoFirma.getIdArchivoCMS());
			historicoFirmaDTO.setIdHistoricoFirma(historicoFirma.getIdHistoricoFirma());
			historicoFirmaDTO.setIdInstanciaDeTarea(historicoFirmaDTOConsulta.getIdInstanciaDeTarea());
			historicoFirmaDTO.setIdTipoDeDocumento(historicoFirma.getTipoDeDocumento().getIdTipoDeDocumento());
			historicoFirmaDTO.setIdUsuario(historicoFirma.getIdUsuario());
			historicoFirmaDTOList.add(historicoFirmaDTO);
		}
		return historicoFirmaDTOList;
	}

	@Override
	public List<HistoricoFirmaDTO> getHistoricoFirmaDocumentoFEAPorIdArchivo(String idArchivoCMS) throws Exception {
		log.debug("Inicio getHistoricoFirmaDocumentoFEAPorIdArchivo");
		log.debug("idArchivoCMS: " + idArchivoCMS);		
		List<HistoricoFirma> historicoFirmaList = historicoFirmaDao.getHistoricoFirmaDocumentoFEAPorIdArchivo(idArchivoCMS);
		if (historicoFirmaList!=null) {
			log.debug("historicoFirmaList.size(): " + historicoFirmaList.size());
		}
		List<HistoricoFirmaDTO> historicoFirmaDTOList = new ArrayList<HistoricoFirmaDTO>();
		for (HistoricoFirma historicoFirma: historicoFirmaList) {
			log.debug(historicoFirma.toString());
			HistoricoFirmaDTO historicoFirmaDTO = new HistoricoFirmaDTO();
			historicoFirmaDTO.setFechaFirma(historicoFirma.getFechaFirma());
			historicoFirmaDTO.setIdArchivoCMS(historicoFirma.getIdArchivoCMS());
			historicoFirmaDTO.setIdHistoricoFirma(historicoFirma.getIdHistoricoFirma());				
			historicoFirmaDTO.setIdTipoDeDocumento(historicoFirma.getTipoDeDocumento().getIdTipoDeDocumento());
			historicoFirmaDTO.setIdUsuario(historicoFirma.getIdUsuario());
			historicoFirmaDTO.setTipoFirma(FirmaType.valueOf(historicoFirma.getTipoFirma()));
			log.debug(historicoFirmaDTO.toString());
			historicoFirmaDTOList.add(historicoFirmaDTO);
		}
		return historicoFirmaDTOList;				
	}

}
