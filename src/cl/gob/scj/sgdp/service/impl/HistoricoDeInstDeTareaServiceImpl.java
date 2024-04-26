package cl.gob.scj.sgdp.service.impl;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.HistoricoDeInstDeTareaDao;
import cl.gob.scj.sgdp.dto.HistoricoDeInstDeTareaDTO;
import cl.gob.scj.sgdp.model.HistoricoDeInstDeTarea;
import cl.gob.scj.sgdp.model.HistoricoUsuariosAsignadosATarea;
import cl.gob.scj.sgdp.service.BitacoraSubTareaService;
import cl.gob.scj.sgdp.service.HistoricoDeInstDeTareaService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class HistoricoDeInstDeTareaServiceImpl implements HistoricoDeInstDeTareaService {
	
	@Autowired
	private HistoricoDeInstDeTareaDao historicoDeInstDeTareaDao;
	
	@Autowired
	private BitacoraSubTareaService bitacoraSubTareaService;
	
	@Resource(name = "configProps")
	private Properties configProps;
	
	private static final Logger log = Logger.getLogger(HistoricoDeInstDeTareaServiceImpl.class);
	
	@Override
	public void cargaHistorialDeTareasPorIdIntanciaDeTarea(long idInstanciaDeTarea, List<HistoricoDeInstDeTareaDTO> historicosDeInstDeTareaDTO) {
		List<HistoricoDeInstDeTarea> historialDeTareasPorIdIntanciaDeTarea = historicoDeInstDeTareaDao.getHistorialDeTareasPorIdIntanciaDeTarea(idInstanciaDeTarea);
		for (HistoricoDeInstDeTarea historicoDeInstDeTarea: historialDeTareasPorIdIntanciaDeTarea) {
			HistoricoDeInstDeTareaDTO historicoDeInstDeTareaDTO = new HistoricoDeInstDeTareaDTO();
			historicoDeInstDeTareaDTO.setComentario(historicoDeInstDeTarea.getComentario());
			historicoDeInstDeTareaDTO.setFechaMovimiento(historicoDeInstDeTarea.getFechaMovimiento());
			historicoDeInstDeTareaDTO.setIdAccionHistoricoInstDeTarea(historicoDeInstDeTarea.getAccionHistInstDeTarea().getIdAccionHistoricoInstDeTarea());
			historicoDeInstDeTareaDTO.setIdHistoricoDeInstDeTarea(historicoDeInstDeTarea.getIdHistoricoDeInstDeTarea());
			historicoDeInstDeTareaDTO.setIdInstanciaDeTareaDeOrigen(historicoDeInstDeTarea.getInstanciaDeTareaDeOrigen().getIdInstanciaDeTarea());
			historicoDeInstDeTareaDTO.setIdInstanciaDeTareaDeDestino(historicoDeInstDeTarea.getInstanciaDeTareaDeDestino().getIdInstanciaDeTarea());
			historicoDeInstDeTareaDTO.setIdUsuarioOrigen(historicoDeInstDeTarea.getIdUsuarioOrigen());
			historicoDeInstDeTareaDTO.setNombreAccion(historicoDeInstDeTarea.getAccionHistInstDeTarea().getNombreAccion());
			historicoDeInstDeTareaDTO.setNombreTareaDeDestino(historicoDeInstDeTarea.getInstanciaDeTareaDeDestino().getTarea().getNombreTarea());
			historicoDeInstDeTareaDTO.setNombreTareaDeOrigen(historicoDeInstDeTarea.getInstanciaDeTareaDeOrigen().getTarea().getNombreTarea());
			for (HistoricoUsuariosAsignadosATarea historicoUsuarioAsignadosATarea : historicoDeInstDeTarea.getHistoricoUsuariosAsignadosATareas()) {
				historicoDeInstDeTareaDTO.getUsuariosDestino().add(historicoUsuarioAsignadosATarea.getId().getIdUsuario());
			}
			historicoDeInstDeTareaDTO.cargaUsuariosDestinoString(configProps.getProperty("caracterSeparadorDeUsuarios"));
			historicosDeInstDeTareaDTO.add(historicoDeInstDeTareaDTO);
		}
	}
	
	@Override
	public List<HistoricoDeInstDeTareaDTO> getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(String idExpediente) {
		List<HistoricoDeInstDeTareaDTO> list = historicoDeInstDeTareaDao.getHistoricoDeInstDeTareaPorIdExpedienteBusqueda(idExpediente);
		
		for(int i = 0; i < list.size() ; i++) {
			HistoricoDeInstDeTareaDTO h = list.get(i);
			Integer cant = bitacoraSubTareaService.getAllByIdInstTarea(h.getIdInstanciaDeTareaDeOrigen()).size();
			list.get(i).setCantAccionesEnBitacora(cant);
		}
		
		return list;
	}
	
	@Override
	public HistoricoDeInstDeTareaDTO getHistoricoDeInstDeTareaDTOPorIdInstanciaDeTareaDeDestinoMaxFechaMovimiento(long idInstanciaDeTarea) {
		HistoricoDeInstDeTarea historicoDeInstDeTarea = historicoDeInstDeTareaDao.getHistoricoDeInstDeTareaPorIdInstanciaDeTareaDeDestinoMaxFechaMovimiento(idInstanciaDeTarea);
		HistoricoDeInstDeTareaDTO historicoDeInstDeTareaDTO = new HistoricoDeInstDeTareaDTO();		
		BeanUtils.copyProperties(historicoDeInstDeTarea, historicoDeInstDeTareaDTO);
		return historicoDeInstDeTareaDTO;
	}

	@Override
	public HistoricoDeInstDeTareaDTO getHisDeInstDeTareaPorIdInstDeTareaDTODeDestIdUsrOrigenMaxFechaMov(
			long idInstanciaDeTarea, String idUsuarioOrigen) {
		HistoricoDeInstDeTarea historicoDeInstDeTarea = historicoDeInstDeTareaDao.getHisDeInstDeTareaPorIdInstDeTareaDeDestIdUsrOrigenMaxFechaMov(idInstanciaDeTarea, idUsuarioOrigen);
		HistoricoDeInstDeTareaDTO historicoDeInstDeTareaDTO = new HistoricoDeInstDeTareaDTO();		
		BeanUtils.copyProperties(historicoDeInstDeTarea, historicoDeInstDeTareaDTO);
		return historicoDeInstDeTareaDTO;
	}

	@Override
	public int getCantidadDeEjecutacionesInstanciaDeTarea(long idInstanciaDeTarea) {
		List<HistoricoDeInstDeTarea> historicoDeInstDeTareas = historicoDeInstDeTareaDao.getEjecutacionesInstanciaDeTarea(idInstanciaDeTarea);
		if (historicoDeInstDeTareas == null || historicoDeInstDeTareas.isEmpty()) {
			return 1;
		} else {
			return historicoDeInstDeTareas.size() + 1;
		}
	}

}
