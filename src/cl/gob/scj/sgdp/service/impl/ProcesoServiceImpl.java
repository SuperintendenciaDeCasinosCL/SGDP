package cl.gob.scj.sgdp.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.ProcesoDao;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.model.Proceso;

import cl.gob.scj.sgdp.service.ProcesoService;
import cl.gob.scj.sgdp.tipos.PermisoType;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ProcesoServiceImpl implements ProcesoService {

	private static final Logger log = Logger.getLogger(ProcesoServiceImpl.class);
	
	@Autowired
	private ProcesoDao procesoDao;
	
	@Override
	public List<ProcesoDTO> buscarTodosProcesoVigente(boolean vigente) {
		List<ProcesoDTO> listaProcesoDto = new ArrayList<ProcesoDTO>();
		List<Proceso> procesos = procesoDao.buscarTodosProcesoVigente(vigente);
		for (Proceso proceso : procesos) {
			ProcesoDTO procesoDTO = new ProcesoDTO(proceso.getIdProceso(), proceso.getDescripcionProceso(), proceso.getNombreProceso(), 
					proceso.getVigente(), proceso.getDiasHabilesMaxDuracion(),proceso.getMacroProceso().getNombreMacroProceso(), proceso.getCodigoProceso());
			listaProcesoDto.add(procesoDTO);
		}
		return listaProcesoDto;
	}
	
	@Override
	public List<ProcesoDTO> buscarTodosProcesoVigenteOrderPorCod(boolean vigente) {
		List<ProcesoDTO> listaProcesoDto = new ArrayList<ProcesoDTO>();
		List<Proceso> procesos = procesoDao.buscarTodosProcesoVigenteOrderPorCod(vigente);
		for (Proceso proceso : procesos) {
			ProcesoDTO procesoDTO = new ProcesoDTO(proceso.getIdProceso(), proceso.getDescripcionProceso(), proceso.getNombreProceso(), 
					proceso.getVigente(), proceso.getDiasHabilesMaxDuracion(),proceso.getMacroProceso().getNombreMacroProceso(), proceso.getCodigoProceso());
			listaProcesoDto.add(procesoDTO);
		}
		return listaProcesoDto;
	}
	
	@Override
	public List<ProcesoDTO> getTodosProcesos() {
		List<ProcesoDTO> listaProcesoDto = new ArrayList<ProcesoDTO>();
		List<Proceso> procesos = procesoDao.getTodosProcesos();
		for (Proceso proceso : procesos) {
			ProcesoDTO procesoDTO = new ProcesoDTO(proceso.getIdProceso(), proceso.getDescripcionProceso(), proceso.getNombreProceso(), 
					proceso.getVigente(), proceso.getDiasHabilesMaxDuracion(),proceso.getMacroProceso().getNombreMacroProceso(), proceso.getCodigoProceso());
			listaProcesoDto.add(procesoDTO);
		}
		return listaProcesoDto;
	}
	
	@Override
	public List<ProcesoDTO> getBuscarTodosProcesosPorVigencia(boolean vigente) {
		List<ProcesoDTO> listaProcesoDto = new ArrayList<ProcesoDTO>();
		List<Proceso> procesos = procesoDao.getBuscarTodosProcesosVigente(vigente);
		for (Proceso proceso : procesos) {
			ProcesoDTO procesoDTO = new ProcesoDTO(proceso.getIdProceso(), proceso.getDescripcionProceso(), proceso.getNombreProceso(), 
					proceso.getVigente(), proceso.getDiasHabilesMaxDuracion(),proceso.getMacroProceso().getNombreMacroProceso(), proceso.getCodigoProceso());
			listaProcesoDto.add(procesoDTO);
		}
		return listaProcesoDto;
	}
	
	@Override
	public List<ProcesoDTO> getProcesosPorVigencia(Usuario usuario, boolean vigente) {		
		if (usuario.getPermisos().containsKey(PermisoType.INICIAR_TODOS_LOS_PROCESOS.getNombrePermiso())) {
			return getBuscarTodosProcesosPorVigencia(vigente);
		} else {
			List<ProcesoDTO> listaProcesoDto = new ArrayList<ProcesoDTO>();
			//log.debug("Buscando Procesos por id unidad: " + usuario.getUnidadDTO().getIdUnidad() );
			//List<Proceso> procesos = procesoDao.getProcesosPorIdUnidadYVigencia(usuario.getUnidadDTO().getIdUnidad(), vigente);
			Set<Long> idUnidades = usuario.getIdUnidades();
			for (Long idUnidad : idUnidades) {
				List<Proceso> procesos = procesoDao.getProcesosPorIdUnidadYVigencia(idUnidad, vigente);
				for (Proceso proceso : procesos) {
					ProcesoDTO procesoDTO = new ProcesoDTO(proceso.getIdProceso(), proceso.getDescripcionProceso(), proceso.getNombreProceso(), proceso.getVigente(), 
							proceso.getDiasHabilesMaxDuracion(),proceso.getMacroProceso().getNombreMacroProceso(), proceso.getCodigoProceso());
					listaProcesoDto.add(procesoDTO);
				}	
			}	
			return listaProcesoDto;
		}			
	}

	

}
