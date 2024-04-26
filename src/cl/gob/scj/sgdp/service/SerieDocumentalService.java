package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.SerieDocumentalDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoSerieDocumentalDTO;


@Service
public interface SerieDocumentalService {
	
	SerieDocumentalDTO guardar(SerieDocumentalDTO serie);
	SerieDocumentalDTO actualizar(SerieDocumentalDTO serie);
	List<SerieDocumentalDTO> list();
	boolean elimina(SerieDocumentalDTO serieDocumental);
	List<TipoDeDocumentoSerieDocumentalDTO> documentosDeProceso(String codigoProceso);
	TipoDeDocumentoSerieDocumentalDTO saveTablaRetencion(TipoDeDocumentoSerieDocumentalDTO td);
}	