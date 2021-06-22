package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dto.AcuerdoTransferenciaDTO;
import cl.gob.scj.sgdp.dto.BuscarAcuerdoDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaAcuerdoDTO;
import cl.gob.scj.sgdp.dto.TokenDTO;
import cl.gob.scj.sgdp.enums.EstadoAcuerdoArchivoNacionalEnum;
import cl.gob.scj.sgdp.exception.ArchivoNacionalException;
import cl.gob.scj.sgdp.service.AcuerdoDeTransferenciaService;
import cl.gob.scj.sgdp.service.ArchivoNacionalService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AcuerdoDeTransferenciaServiceImpl implements AcuerdoDeTransferenciaService {

	@Autowired
	private ArchivoNacionalService archivoNacionalService;

	@Override
	public ResultadoBusquedaAcuerdoDTO getResultadoBusquedaAcuerdo(BuscarAcuerdoDTO buscarDTO, boolean all)
			throws ArchivoNacionalException {
		ResultadoBusquedaAcuerdoDTO dto = new ResultadoBusquedaAcuerdoDTO();
		List<AcuerdoTransferenciaDTO> list = null;
		TokenDTO token = this.archivoNacionalService.login();
		list = this.archivoNacionalService.getAcuerdosDeTransferencia(token, buscarDTO);
		if (!all) {
			List<AcuerdoTransferenciaDTO> listAux = new ArrayList<AcuerdoTransferenciaDTO>();
			for (AcuerdoTransferenciaDTO dtoAux : list) {
				if (dtoAux.getEstadoId() == Integer
						.parseInt(EstadoAcuerdoArchivoNacionalEnum.APROBADO_PROPUESTA.getIdStr())
						|| dtoAux.getEstadoId() == Integer.parseInt(EstadoAcuerdoArchivoNacionalEnum.APROBADO_PRUEBA.getIdStr())) {
					listAux.add(dtoAux);
				}
			}
			list = listAux;
		}
		dto.setAcuerdosDTO(list);
		dto.setMensajeError("OK");
		return dto;
	}

}