package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.HistoricoValorParametroDeTareaDao;
import cl.gob.scj.sgdp.dao.ResponsabilidadTareaDao;
import cl.gob.scj.sgdp.dto.HistoricoValorParametroDeTareaDTO;
import cl.gob.scj.sgdp.dto.InstanciaDeTareaDTO;
import cl.gob.scj.sgdp.dto.ParametroDeTareaDTO;
import cl.gob.scj.sgdp.dto.TipoParametroDeTareaDTO;
import cl.gob.scj.sgdp.model.HistoricoValorParametroDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.ParametroDeTarea;
import cl.gob.scj.sgdp.model.ResponsabilidadTarea;
import cl.gob.scj.sgdp.model.TareaRol;
import cl.gob.scj.sgdp.model.TipoParametroDeTarea;
import cl.gob.scj.sgdp.service.HistoricoValorParametroDeTareaService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class HistoricoValorParametroDeTareaServiceImpl implements HistoricoValorParametroDeTareaService {
	
	private static final Logger log = Logger.getLogger(HistoricoValorParametroDeTareaServiceImpl.class);

	@Autowired
	private HistoricoValorParametroDeTareaDao historicoValorParametroDeTareaDao;
	
	@Autowired
	private ResponsabilidadTareaDao responsabilidadTareaDao;

	@Override
	public List<HistoricoValorParametroDeTareaDTO> getHistoricoValorParametroDeTareaPorIdInstanciaDeTarea(
			long idInstanciaDeTarea) {
		log.debug("Inicio getHistoricoValorParametroDeTareaPorIdInstanciaDeTarea");
		log.debug("idInstanciaDeTarea: " + idInstanciaDeTarea);
		List<HistoricoValorParametroDeTarea> historicoValorParametroDeTareaList = historicoValorParametroDeTareaDao.getHistoricoValorParametroDeTareaPorIdInstanciaDeTarea(idInstanciaDeTarea);
		return getHistoricoValorParametroDeTareaDTOList(historicoValorParametroDeTareaList);
	}
	
	@Override
	public List<HistoricoValorParametroDeTareaDTO> getHistoricoValorParametroDeTareaPorIdExpediente(
			String idExpediente) {
		log.debug("Inicio getHistoricoValorParametroDeTareaPorIdExpediente");
		log.debug("idExpediente: " + idExpediente);
		List<HistoricoValorParametroDeTarea> historicoValorParametroDeTareaList = historicoValorParametroDeTareaDao.getHistoricoValorParametroDeTareaPorIdExpediente(idExpediente);
		return getHistoricoValorParametroDeTareaDTOList(historicoValorParametroDeTareaList);
	}
	
	@Override
	public List<HistoricoValorParametroDeTareaDTO> getHistoricoValorParametroDeTareaPorIdHistoricoInstanciaDeTarea(long idHistoricoDeInstDeTarea) {
		log.debug("Inicio getHistorialDeCondicionesDeSatisfaccionPorIdHistoricoInstanciaDeTarea");
		log.debug("idHistoricoDeInstDeTarea: " + idHistoricoDeInstDeTarea);
		List<HistoricoValorParametroDeTarea> historicoValorParametroDeTareaList = historicoValorParametroDeTareaDao.getHistoricoValorParametroDeTareaPorIdHistoricoInstanciaDeTarea(idHistoricoDeInstDeTarea);
		return getHistoricoValorParametroDeTareaDTOList(historicoValorParametroDeTareaList);
	}
	
	private List<HistoricoValorParametroDeTareaDTO> getHistoricoValorParametroDeTareaDTOList(List<HistoricoValorParametroDeTarea> historicoValorParametroDeTareaList) {
		List<HistoricoValorParametroDeTareaDTO> historicoValorParametroDeTareaDTOList = new ArrayList<HistoricoValorParametroDeTareaDTO>();
		for (HistoricoValorParametroDeTarea historicoValorParametroDeTarea : historicoValorParametroDeTareaList) {
			HistoricoValorParametroDeTareaDTO historicoValorParametroDeTareaDTO = new HistoricoValorParametroDeTareaDTO();
			historicoValorParametroDeTareaDTO.setIdHistoricoValorParametroDeTarea(historicoValorParametroDeTarea.getIdHistoricoValorParametroDeTarea());
			historicoValorParametroDeTareaDTO.setComentario(historicoValorParametroDeTarea.getComentario());
			historicoValorParametroDeTareaDTO.setValor(historicoValorParametroDeTarea.getValor());
			historicoValorParametroDeTareaDTO.setIdUsuario(historicoValorParametroDeTarea.getIdUsuario());
			historicoValorParametroDeTareaDTO.setFecha(historicoValorParametroDeTarea.getFecha());
			InstanciaDeTarea instanciaDeTarea = historicoValorParametroDeTarea.getInstanciaDeTarea();
			List<ResponsabilidadTarea> responsabilidadesTareas = responsabilidadTareaDao.getResponsabilidadesTareasPorIdTarea(instanciaDeTarea.getTarea().getIdTarea());
			if (responsabilidadesTareas!=null && responsabilidadesTareas.size()>=1) {
				ResponsabilidadTarea rt = responsabilidadesTareas.get(0);
				historicoValorParametroDeTareaDTO.setNombreResponsabilidad(rt.getId().getResponsabilidad().getNombreResponsabilidad());
			}
			ParametroDeTarea parametroDeTarea = historicoValorParametroDeTarea.getParametroDeTarea();
			ParametroDeTareaDTO parametroDeTareaDTO = new ParametroDeTareaDTO();
			parametroDeTareaDTO.setIdParamTarea(parametroDeTarea.getIdParamTarea());
			parametroDeTareaDTO.setNombreParamTarea(parametroDeTarea.getNombreParamTarea());
			parametroDeTareaDTO.setEsSNC(parametroDeTarea.getEsSNC());
			TipoParametroDeTarea tipoParametroDeTarea = parametroDeTarea.getTipoParametroDeTarea();
			TipoParametroDeTareaDTO tipoParametroDeTareaDTO = new TipoParametroDeTareaDTO();
			tipoParametroDeTareaDTO.setComenta(tipoParametroDeTarea.getComenta());
			tipoParametroDeTareaDTO.setIdTipoParametroDeTarea(tipoParametroDeTarea.getIdTipoParametroDeTarea());
			tipoParametroDeTareaDTO.setNombreTipoParametroDeTarea(tipoParametroDeTarea.getNombreTipoParametroDeTarea());
			tipoParametroDeTareaDTO.setTextoHtml(tipoParametroDeTarea.getTextoHtml());
			parametroDeTareaDTO.setTipoParametroDeTareaDTO(tipoParametroDeTareaDTO);
			InstanciaDeTareaDTO instanciaDeTareaDTO = new InstanciaDeTareaDTO();
			BeanUtils.copyProperties(instanciaDeTarea, instanciaDeTareaDTO);
			instanciaDeTareaDTO.setNombreDeTarea(instanciaDeTarea.getTarea().getNombreTarea());
			historicoValorParametroDeTareaDTO.setInstanciaDeTareaDTO(instanciaDeTareaDTO);
			historicoValorParametroDeTareaDTO.setParametroDeTareaDTO(parametroDeTareaDTO);
			log.debug(historicoValorParametroDeTareaDTO.toString());
			historicoValorParametroDeTareaDTOList.add(historicoValorParametroDeTareaDTO);
		}		
		return historicoValorParametroDeTareaDTOList;
	}
	
}
