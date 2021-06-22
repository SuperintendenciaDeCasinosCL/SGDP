package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.Carga;

public interface CargaDao {

	void guardarCargaArchivoNacional(Carga entity);

	List<Carga> getResultadoBusquedaEstadoTransferencia();
}
