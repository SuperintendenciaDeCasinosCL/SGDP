package cl.gob.scj.sgdp.ws.alfresco.rest.client;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.dto.BuscarConFiltroDTO;
import cl.gob.scj.sgdp.dto.BuscarDTO;

import cl.gob.scj.sgdp.util.DataTableRequestDTO;

import cl.gob.scj.sgdp.dto.CargaFacetDTO;
import cl.gob.scj.sgdp.dto.RespuestaCargaFacetDTO;

import cl.gob.scj.sgdp.ws.alfresco.rest.response.ResultadoBusquedaResponse;

@Service
public interface BusquedaCMSService {
	
	public ResultadoBusquedaResponse buscar(BuscarDTO buscarDTO, Usuario usuario) throws Exception;

	public ResultadoBusquedaResponse buscarConFiltro(BuscarConFiltroDTO buscarConFiltroDTO, Usuario usuario) throws Exception;
	
	RespuestaCargaFacetDTO cargaFacet(CargaFacetDTO cargaFacetDTO, Usuario usuario) throws Exception;

	public ResultadoBusquedaResponse buscarRegistrosPaginados(DataTableRequestDTO dataTableInput,BuscarDTO buscarDTO, Usuario usuario) throws Exception;

}
