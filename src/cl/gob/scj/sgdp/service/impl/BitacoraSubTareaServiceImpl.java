package cl.gob.scj.sgdp.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.BitacoraSubTareaDao;
import cl.gob.scj.sgdp.dto.BitacoraSubTareaDTO;
import cl.gob.scj.sgdp.model.BitacoraSubTareas;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.TipoActividadBitacora;
import cl.gob.scj.sgdp.service.BitacoraSubTareaService;
import cl.gob.scj.sgdp.service.InstanciaDeTareaService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class BitacoraSubTareaServiceImpl implements BitacoraSubTareaService {

	@Autowired
	private BitacoraSubTareaDao bitacoraSubTareaDao;
	
	@Autowired
	private InstanciaDeTareaService instanciaDeTareaService;
	
	@Override
	public List<BitacoraSubTareaDTO> getAllByIdInstTarea(Long idInstTarea) {
		List<BitacoraSubTareaDTO> bitacoras = new ArrayList<>();
		List<BitacoraSubTareas> bitacorasModel = bitacoraSubTareaDao.getAllByIdInstTarea(idInstTarea);
		
		for(BitacoraSubTareas bd : bitacorasModel) {
			bitacoras.add(parseModelToDTO(bd));
		}
		
		return bitacoras;
	}
	
	private BitacoraSubTareaDTO parseModelToDTO(BitacoraSubTareas bd) {
		BitacoraSubTareaDTO bitacora = new BitacoraSubTareaDTO(); 
		bitacora.setNombreActividad(bd.getTipoActividadBitacora().getNombreActividad());
		bitacora.setNombreTarea(bd.getInstanciaDeTarea().getTarea().getNombreTarea());
		bitacora.setIdBitacora(bd.getIdBitacoraSubTarea());
		bitacora.setIdInstanciaDeTarea(bd.getInstanciaDeTarea().getIdInstanciaDeTarea());
		bitacora.setIdActividad(bd.getTipoActividadBitacora().getIdActividad());
		bitacora.setIdUsuario(bd.getIdUsuario());
		bitacora.setFecha(bd.getFecha());
		bitacora.setAccion(bd.getAccion());
		bitacora.setDuracion(bd.getDuracion());
		bitacora.setUsuarios(bd.getUsuariosAsociados());
		return bitacora;
	}
	
	private BitacoraSubTareas parseDTOToModel(BitacoraSubTareaDTO bt) {
		BitacoraSubTareas bd = new BitacoraSubTareas();
		InstanciaDeTarea it = instanciaDeTareaService.getInstanciaDeTareaPorIdInstanciaDeTarea(bt.getIdInstanciaDeTarea());
		TipoActividadBitacora ta = new TipoActividadBitacora();
		ta.setIdActividad(bt.getIdActividad());
		if(bt.getIdBitacora() != 0 || bt.getIdBitacora() != null) {
			bd.setIdBitacoraSubTarea(bt.getIdBitacora());
		}
		bd.setInstanciaDeTarea(it);
		bd.setTipoActividadBitacora(ta);
		bd.setIdUsuario(bt.getIdUsuario());
		bd.setAccion(bt.getAccion());
		bd.setFecha(new Date(bt.getFecha()));
		bd.setDuracion(bt.getDuracion());
		bd.setUsuariosAsociados(bt.getUsuarios());
		return bd;
	}

	@Override
	public BitacoraSubTareaDTO save(BitacoraSubTareaDTO bitacora) {
		BitacoraSubTareas bd = parseDTOToModel(bitacora);
		try {
			bitacoraSubTareaDao.insert(bd);
			return parseModelToDTO(bd);
		} catch (Exception e ) {
			return null;
		}
	}

	@Override
	public boolean delete(BitacoraSubTareaDTO bitacora) {
		BitacoraSubTareas bd = bitacoraSubTareaDao.find(bitacora.getIdBitacora());
		try {
			bd.setActivo(false);
			return true;
		} catch (Exception e ) {
			return false;
		}
	}
	
	
}
