package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.dto.EntradaSubprocesoIndicadoresDTO;
import cl.gob.scj.sgdp.dto.IndicadorSubprocesoDTO;
import cl.gob.scj.sgdp.dto.SubprocesoIndicadoresDTO;

public interface IndicadorDao {

	List<SubprocesoIndicadoresDTO> buscarTodosSubprocesoConTipoiPorIdSubProcesoInicio(
			IndicadorSubprocesoDTO indicadorSubprocesoDTO, String tipoFecha,EntradaSubprocesoIndicadoresDTO entradaSubprocesoIndicadoresDTO);

}
