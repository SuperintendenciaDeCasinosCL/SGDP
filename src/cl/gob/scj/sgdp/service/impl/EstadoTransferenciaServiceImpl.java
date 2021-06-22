package cl.gob.scj.sgdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dto.EstadoTransferenciaDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaEstadoTransferenciaDTO;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
import cl.gob.scj.sgdp.service.CargaService;
import cl.gob.scj.sgdp.service.EstadoTransferenciaService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class EstadoTransferenciaServiceImpl implements EstadoTransferenciaService {
	
	@Autowired
	private CargaService cargaService;

	@Override
	public ResultadoBusquedaEstadoTransferenciaDTO getResultadoBusquedaEstadoTransferencia()
			throws ArchivoNacionalException {
		ResultadoBusquedaEstadoTransferenciaDTO resultado = new ResultadoBusquedaEstadoTransferenciaDTO();
		List<EstadoTransferenciaDTO> list = this.cargaService.getResultadoBusquedaEstadoTransferencia();
		resultado.setEstadoTransferenciaList(list);
		resultado.setMensajeError("OK");
		return resultado;
	}

}
