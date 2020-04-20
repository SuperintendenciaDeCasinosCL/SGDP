package cl.gob.scj.sgdp.service;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.EntradaSubprocesoIndicadoresDTO;
import cl.gob.scj.sgdp.dto.SalidaSubprocesoIndicadoresDTO;

@Service
public interface IndicadorService {
	
	public  SalidaSubprocesoIndicadoresDTO buscarSubprocesoPorIdIndicador(EntradaSubprocesoIndicadoresDTO entradaSubprocesoIndicadoresDTO);
	
}
