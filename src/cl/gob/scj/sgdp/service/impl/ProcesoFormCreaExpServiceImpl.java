package cl.gob.scj.sgdp.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dao.ProcesoFormCreaExpDao;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.ProcesoFormCreaExpDTO;
import cl.gob.scj.sgdp.model.ProcesoFormCreaExp;
import cl.gob.scj.sgdp.service.ProcesoFormCreaExpService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ProcesoFormCreaExpServiceImpl implements ProcesoFormCreaExpService {
	
	private static final Logger log = Logger.getLogger(ProcesoFormCreaExpServiceImpl.class);
	
	@Autowired
	private ProcesoFormCreaExpDao procesoFormCreaExpDao;

	@Override
	public List<ProcesoFormCreaExpDTO> getTodosProcesoFormCreaExp() {
		log.debug("Inicio getTodosProcesoFormCreaExp");		
		return procesoFormCreaExpDao.getTodosProcesoFormCreaExp();
	}
	
	@Override
	public void eliminaProcesoDTOQueEstanEnProcesoFormCreaExpDTO(List<ProcesoDTO> todosLosProc, List<ProcesoFormCreaExpDTO> todosLosProcFormCreaExp) {		
		Iterator<ProcesoDTO> itProcesoDTO = todosLosProc.iterator();		
		while (itProcesoDTO.hasNext()) {
			ProcesoDTO procesoDTO = itProcesoDTO.next();
			boolean procesoDTOEstaEnListaProcesoFormCreaExpDTO = procesoDTOEstaEnListaProcesoFormCreaExpDTO(procesoDTO, todosLosProcFormCreaExp);
			if (procesoDTOEstaEnListaProcesoFormCreaExpDTO == true) {
				itProcesoDTO.remove();
			}
		}		
	}
	
	private boolean procesoDTOEstaEnListaProcesoFormCreaExpDTO(ProcesoDTO procesoDTO, List<ProcesoFormCreaExpDTO> todosLosProcFormCreaExp) {		
		for (ProcesoFormCreaExpDTO procesoFormCreaExpDTO : todosLosProcFormCreaExp) {
			if (procesoDTO.getCodigoProceso()!=null && procesoFormCreaExpDTO.getCodigoProceso()!=null && procesoDTO.getCodigoProceso().equals(procesoFormCreaExpDTO.getCodigoProceso()) ) {
				return true;
			}
		}		
		return false;
	}
	
	@Override
	public void guardarAsignacionProcCreaExp(List<ProcesoFormCreaExpDTO> listaProcesosFormCreaExpDTO, Usuario usuario) {
		List<ProcesoFormCreaExp> todosProcesoFormCreaExp =  procesoFormCreaExpDao.getAll(ProcesoFormCreaExp.class);		
		boolean objetoEstaEnLista;
		for (ProcesoFormCreaExp procesoFormCreaExp: todosProcesoFormCreaExp) {
			objetoEstaEnLista = procesoFormCreaExpEstaEnListaProcesoFormCreaExpDTO(procesoFormCreaExp, listaProcesosFormCreaExpDTO);
			if (objetoEstaEnLista == false) {
				procesoFormCreaExpDao.delete(procesoFormCreaExp);
			}
		}	
		for (ProcesoFormCreaExpDTO procesoFormCreaExpDTO : listaProcesosFormCreaExpDTO) {
			objetoEstaEnLista = procesoFormCreaExpDTOEstaEnListaProcesoFormCreaExp(procesoFormCreaExpDTO, todosProcesoFormCreaExp);
			if (objetoEstaEnLista == false) {
				ProcesoFormCreaExp procesoFormCreaExp = new ProcesoFormCreaExp();
				procesoFormCreaExp.setCodigoProceso(procesoFormCreaExpDTO.getCodigoProceso());
				procesoFormCreaExp.setIdUsuario(usuario.getIdUsuario());
				procesoFormCreaExp.setFecha(new Date());
				procesoFormCreaExpDao.insert(procesoFormCreaExp);
			}
		}
	}
	
	private boolean procesoFormCreaExpEstaEnListaProcesoFormCreaExpDTO(ProcesoFormCreaExp procesoFormCreaExp, List<ProcesoFormCreaExpDTO> listaProcesosFormCreaExpDTO) {		
		for (ProcesoFormCreaExpDTO procesoFormCreaExpDTO: listaProcesosFormCreaExpDTO) {
			if (procesoFormCreaExp.getCodigoProceso().equals(procesoFormCreaExpDTO.getCodigoProceso())) {
				return true;
			}
		}		
		return false;		
	}
	
	private boolean procesoFormCreaExpDTOEstaEnListaProcesoFormCreaExp(ProcesoFormCreaExpDTO procesoFormCreaExpDTO, List<ProcesoFormCreaExp> listaProcesosFormCreaExp) {		
		for (ProcesoFormCreaExp procesoFormCreaExp: listaProcesosFormCreaExp) {
			if (procesoFormCreaExp.getCodigoProceso().equals(procesoFormCreaExpDTO.getCodigoProceso())) {
				return true;
			}
		}		
		return false;		
	}

}
