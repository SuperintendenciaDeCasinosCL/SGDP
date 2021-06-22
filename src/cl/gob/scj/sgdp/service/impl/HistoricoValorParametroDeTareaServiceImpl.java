package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.HistoricoValorParametroDeTareaDao;
import cl.gob.scj.sgdp.dto.HistoricoDeInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.HistoricoValorParametroDeTareaDTO;
import cl.gob.scj.sgdp.dto.ParametroDeTareaDTO;
import cl.gob.scj.sgdp.dto.TipoParametroDeTareaDTO;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoValorParametroDeTarea;
import cl.gob.scj.sgdp.model.ParametroDeTarea;
import cl.gob.scj.sgdp.model.TipoParametroDeTarea;
import cl.gob.scj.sgdp.service.HistoricoValorParametroDeTareaService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class HistoricoValorParametroDeTareaServiceImpl implements HistoricoValorParametroDeTareaService {
	
	private static final Logger log = Logger.getLogger(HistoricoValorParametroDeTareaServiceImpl.class);

	@Autowired
	private HistoricoValorParametroDeTareaDao historicoValorParametroDeTareaDao;

	@Override
	public List<HistoricoValorParametroDeTareaDTO> getHistoricoValorParametroDeTareaPorIdInstanciaDeTareaOrigen(
			long idInstanciaDeTareaOrigen) {
		log.debug("Inicio getHistoricoValorParametroDeTareaPorIdInstanciaDeTareaOrigen");
		log.debug("idInstanciaDeTareaOrigen: " + idInstanciaDeTareaOrigen);
		List<HistoricoValorParametroDeTarea> historicoValorParametroDeTareaList = historicoValorParametroDeTareaDao.getHistoricoValorParametroDeTareaPorIdInstanciaDeTareaOrigen(idInstanciaDeTareaOrigen);
		List<HistoricoValorParametroDeTareaDTO> historicoValorParametroDeTareaDTOList = new ArrayList<HistoricoValorParametroDeTareaDTO>();
		for (HistoricoValorParametroDeTarea historicoValorParametroDeTarea : historicoValorParametroDeTareaList) {
			HistoricoValorParametroDeTareaDTO historicoValorParametroDeTareaDTO = new HistoricoValorParametroDeTareaDTO();
			historicoValorParametroDeTareaDTO.setIdHistoricoValorParametroDeTarea(historicoValorParametroDeTarea.getIdHistoricoValorParametroDeTarea());
			historicoValorParametroDeTareaDTO.setComentario(historicoValorParametroDeTarea.getComentario());
			historicoValorParametroDeTareaDTO.setValor(historicoValorParametroDeTarea.getValor());
			HistoricoDeInstDeTarea historicoDeInstDeTarea = historicoValorParametroDeTarea.getHistoricoDeInstDeTarea();
			ParametroDeTarea parametroDeTarea = historicoValorParametroDeTarea.getParametroDeTarea();			
			HistoricoDeInstDeTareaDTO historicoDeInstDeTareaDTO = new HistoricoDeInstDeTareaDTO();
			historicoDeInstDeTareaDTO.setComentario(historicoDeInstDeTarea.getComentario());
			historicoDeInstDeTareaDTO.setFechaMovimiento(historicoDeInstDeTarea.getFechaMovimiento());
			historicoDeInstDeTareaDTO.setIdAccionHistoricoInstDeTarea(historicoDeInstDeTarea.getAccionHistInstDeTarea().getIdAccionHistoricoInstDeTarea());
			historicoDeInstDeTareaDTO.setNombreAccion(historicoDeInstDeTarea.getAccionHistInstDeTarea().getNombreAccion());
			historicoDeInstDeTareaDTO.setIdHistoricoDeInstDeTarea(historicoDeInstDeTarea.getIdHistoricoDeInstDeTarea());
			historicoDeInstDeTareaDTO.setIdInstanciaDeTareaDeDestino(historicoDeInstDeTarea.getInstanciaDeTareaDeDestino().getIdInstanciaDeTarea());
			historicoDeInstDeTareaDTO.setIdInstanciaDeTareaDeOrigen(historicoDeInstDeTarea.getInstanciaDeTareaDeOrigen().getIdInstanciaDeTarea());
			historicoDeInstDeTareaDTO.setIdUsuarioOrigen(historicoDeInstDeTarea.getIdUsuarioOrigen());
			historicoDeInstDeTareaDTO.setNombreTareaDeDestino(historicoDeInstDeTarea.getInstanciaDeTareaDeDestino().getTarea().getNombreTarea());
			historicoDeInstDeTareaDTO.setNombreTareaDeOrigen(historicoDeInstDeTarea.getInstanciaDeTareaDeOrigen().getTarea().getNombreTarea());		
			ParametroDeTareaDTO parametroDeTareaDTO = new ParametroDeTareaDTO();
			parametroDeTareaDTO.setIdParamTarea(parametroDeTarea.getIdParamTarea());
			parametroDeTareaDTO.setNombreParamTarea(parametroDeTarea.getNombreParamTarea());
			TipoParametroDeTarea tipoParametroDeTarea = parametroDeTarea.getTipoParametroDeTarea();
			TipoParametroDeTareaDTO tipoParametroDeTareaDTO = new TipoParametroDeTareaDTO();
			tipoParametroDeTareaDTO.setComenta(tipoParametroDeTarea.getComenta());
			tipoParametroDeTareaDTO.setIdTipoParametroDeTarea(tipoParametroDeTarea.getIdTipoParametroDeTarea());
			tipoParametroDeTareaDTO.setNombreTipoParametroDeTarea(tipoParametroDeTarea.getNombreTipoParametroDeTarea());
			tipoParametroDeTareaDTO.setTextoHtml(tipoParametroDeTarea.getTextoHtml());
			parametroDeTareaDTO.setTipoParametroDeTareaDTO(tipoParametroDeTareaDTO);
			historicoValorParametroDeTareaDTO.setHistoricoDeInstDeTareaDTO(historicoDeInstDeTareaDTO);
			historicoValorParametroDeTareaDTO.setParametroDeTareaDTO(parametroDeTareaDTO);
			historicoValorParametroDeTareaDTOList.add(historicoValorParametroDeTareaDTO);
		}		
		return historicoValorParametroDeTareaDTOList;
	}

}
