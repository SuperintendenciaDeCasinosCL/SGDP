package cl.gob.scj.sgdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.InstanciaDeProcesoDao;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.service.DiagramaDeProceso2Service;

@Service
@Transactional(rollbackFor = Throwable.class)
public class DiagramaDeProceso2ServiceImpl implements DiagramaDeProceso2Service{

	@Autowired
	InstanciaDeProcesoDao instanciaDeProcesoDao;
	
	@Override
	public InstanciaDeProceso getInstanciaDeProcesoPorIdInstanciaDeProceso(long idInstanciaDeProceso) {
		
		return instanciaDeProcesoDao.getInstanciaDeProcesoPorIdInstanciaDeProceso(idInstanciaDeProceso);
	}

}
