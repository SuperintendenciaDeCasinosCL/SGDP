package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
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

	@Override
	public HistoricoFirmaDTO getUltimoHistoricoFirmaDocumentoFEAPorIdArchivo(String idArchivoCMS) {
		HistoricoFirma historicoFirma = historicoFirmaDao.getUltimoHistoricoFirmaDocumentoFEAPorIdArchivo(idArchivoCMS);
		HistoricoFirmaDTO historicoFirmaDTO = new HistoricoFirmaDTO();
		BeanUtils.copyProperties(historicoFirma, historicoFirmaDTO);
		return historicoFirmaDTO;
	}

	@Override
	public long getIdDocumentoFirmado() {
		return historicoFirmaDao.getIdDocumentoFirmado();
	}
	
	@Override
	public HistoricoFirmaDTO getHistoricoFirmaDTOPorIdDocumentoFirmado(long idDocumentoFirmado) {
		try {
			HistoricoFirma historicoFirma = historicoFirmaDao.getHistoricoFirmaPorIdDocumentoFirmado(idDocumentoFirmado);
			HistoricoFirmaDTO historicoFirmaDTO = new HistoricoFirmaDTO();
			BeanUtils.copyProperties(historicoFirma, historicoFirmaDTO);
			return historicoFirmaDTO;
		} catch (Exception e) {
			log.info(e.getMessage());		
			return null;
		}		
	}
	
	@Override
	public boolean validaSiHayFirmaHoy(Long idTipoDeDocumento, Long idInstanciaDeTarea, String idUsuario) {
		log.info("idTipoDeDocumento: " + idTipoDeDocumento);
		log.info("idInstanciaDeTarea: " + idInstanciaDeTarea);
		log.info("idUsuario: " + idUsuario);
		try {
			List<HistoricoFirma> historicoFirmaList = historicoFirmaDao.getHistoricoFirmaPorIdTipoDocumentoIdInstanciaDeTareaIdUsuario(idTipoDeDocumento, idInstanciaDeTarea, idUsuario);
			if (historicoFirmaList == null || historicoFirmaList.isEmpty()) {
				log.info("historicoFirmaList == null || historicoFirmaList.isEmpty()");
				return false;
			} else {
				log.info("historicoFirmaList.size(): " + historicoFirmaList.size());
				for (HistoricoFirma historicoFirma : historicoFirmaList) {
					Date fecha = historicoFirma.getFechaFirma();
					Date ahora = new Date();
					Calendar fechaCal = Calendar.getInstance();
					Calendar ahoraCal = Calendar.getInstance();
					fechaCal.setTime(fecha);
					ahoraCal.setTime(ahora);
					boolean mismoDia = fechaCal.get(Calendar.DAY_OF_YEAR) == ahoraCal.get(Calendar.DAY_OF_YEAR) && fechaCal.get(Calendar.YEAR) == ahoraCal.get(Calendar.YEAR);
					log.info("historicoFirma.getIdArchivoCMS(): " + historicoFirma.getIdArchivoCMS() + " - mismoDia: " + mismoDia);
					if (mismoDia == true) {
						return true;
					}
				}
				return false;
			}
		}	catch (Exception e) {
			log.info(e.getMessage());		
			return false;
		}	
	}

}
