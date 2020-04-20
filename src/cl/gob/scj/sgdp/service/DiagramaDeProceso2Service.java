package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.model.InstanciaDeProceso;

@Service
public interface DiagramaDeProceso2Service {

	public InstanciaDeProceso getInstanciaDeProcesoPorIdInstanciaDeProceso(long idInstanciaDeProceso);
}
