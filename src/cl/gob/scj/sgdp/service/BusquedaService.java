package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.BuscarConFiltroDTO;
import cl.gob.scj.sgdp.dto.BuscarDTO;
import cl.gob.scj.sgdp.dto.CargaFacetDTO;
import cl.gob.scj.sgdp.dto.ElementoResultadoBusquedaDTO;
import cl.gob.scj.sgdp.dto.RespuestaCargaFacetDTO;
import cl.gob.scj.sgdp.dto.ResultadoBusquedaDTO;
import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.util.DataTableRequestDTO;
import cl.gob.scj.sgdp.util.DatatablesInput_SIMPLE;

@Service
public interface BusquedaService {
	
	ResultadoBusquedaDTO buscar(BuscarDTO buscarDTO, Usuario usuario) throws SgdpException;
	
	void filtrarResultado(BuscarDTO buscarDTO, ResultadoBusquedaDTO resultadoBusquedaDTO, 
			List<ElementoResultadoBusquedaDTO> elementosResultadoBusquedaDTOFiltro) 
			throws SgdpException;

	void filtraPorBandejaDeSalida(ResultadoBusquedaDTO resultadoBusquedaDTO, Usuario usuario);
	
	void filtraPorConfidencialidad(ResultadoBusquedaDTO resultadoBusquedaDTO, Usuario usuario);
	
	void cargaFacet(ResultadoBusquedaDTO resultadoBusquedaDTO);

	void filtrarResultadoDocumentosOficiales(ResultadoBusquedaDTO resultadoBusquedaDTO);

	List<String> buscaTodosLosNombreDeLosDocumentos();
	
	List<String> getBuscarTodosLosNombresdeLosProcesoVigente();
	
	void filtraPorConfidencialidadRestringida(ResultadoBusquedaDTO resultadoBusquedaDTO, Usuario usuario);

	ResultadoBusquedaDTO buscarConFiltro(BuscarConFiltroDTO buscarConFiltroDTO, Usuario usuario)  throws SgdpException;
	
	RespuestaCargaFacetDTO cargaFacet(CargaFacetDTO cargaFacetDTO, Usuario usuario) throws SgdpException;

	ResultadoBusquedaDTO buscarRegistrosPaginados(DataTableRequestDTO dataTableInRQ,BuscarDTO buscarDTO, Usuario usuario) throws SgdpException;
	
	void cargaFacet(ResultadoBusquedaDTO resultadoBusquedaDTO, CargaFacetDTO cargaFacetDTO, Usuario usuario) throws SgdpException;
	
	List<String> buscaTodosLosNombreDeLosDocumentosSubidos();
	
	void ordenaFacet(ResultadoBusquedaDTO resultadoBusquedaDTO);
	
	List<String> getTodosLosNombresDeProcesos();

}
