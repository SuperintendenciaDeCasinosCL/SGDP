package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.TablaRetencion;

public interface TablaRetencionDao extends GenericDao<TablaRetencion> {
	
	void update(TablaRetencion tr);
	
	void deleteBySerieDocumental(long idSerieDocumental);
	
	List<TablaRetencion> getTablasDeRetencion(Long idTipoDocumento);
	
	TablaRetencion findByIdTipoDocumentoAndIdSerieDocumental(Long idTipoDocumento, Long idSerieDocumental);
	
}
