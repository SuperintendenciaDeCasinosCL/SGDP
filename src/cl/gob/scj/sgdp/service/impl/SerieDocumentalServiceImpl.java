
package cl.gob.scj.sgdp.service.impl;

import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;

import cl.gob.scj.sgdp.config.Constantes;
import cl.gob.scj.sgdp.dao.MacroProcesoDao;
import cl.gob.scj.sgdp.dao.PerspectivaDao;
import cl.gob.scj.sgdp.dao.ProcesoDao;
import cl.gob.scj.sgdp.dao.ReferenciaDeTareaDao;
import cl.gob.scj.sgdp.dao.ResponsabilidadDao;
import cl.gob.scj.sgdp.dao.SerieDocumentalDao;
import cl.gob.scj.sgdp.dao.SuperProcesoDao;
import cl.gob.scj.sgdp.dao.TablaRetencionDao;
import cl.gob.scj.sgdp.dao.TareaDao;
import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dao.UnidadDao;
import cl.gob.scj.sgdp.dto.CargaProcesoDataInicialDTO;
import cl.gob.scj.sgdp.dto.MacroProcesoDTO;
import cl.gob.scj.sgdp.dto.ParametroDeTareaDTO;
import cl.gob.scj.sgdp.dto.PerspectivaDTO;
import cl.gob.scj.sgdp.dto.ProcesoDTO;
import cl.gob.scj.sgdp.dto.ResponsabilidadDTO;
import cl.gob.scj.sgdp.dto.SerieDocumentalDTO;
import cl.gob.scj.sgdp.dto.SuperProcesoDTO;
import cl.gob.scj.sgdp.dto.TareaDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoSerieDocumentalDTO;
import cl.gob.scj.sgdp.dto.UnidadDTO;
import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTarea;
import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTareaPK;
import cl.gob.scj.sgdp.model.Etapa;
import cl.gob.scj.sgdp.model.MacroProceso;
import cl.gob.scj.sgdp.model.ParametroDeTarea;
import cl.gob.scj.sgdp.model.ParametroRelacionTarea;
import cl.gob.scj.sgdp.model.ParametroRelacionTareaPK;
import cl.gob.scj.sgdp.model.Perspectiva;
import cl.gob.scj.sgdp.model.Proceso;
import cl.gob.scj.sgdp.model.ReferenciaDeTarea;
import cl.gob.scj.sgdp.model.Responsabilidad;
import cl.gob.scj.sgdp.model.ResponsabilidadTarea;
import cl.gob.scj.sgdp.model.ResponsabilidadTareaPK;
import cl.gob.scj.sgdp.model.SerieDocumental;
import cl.gob.scj.sgdp.model.SuperProceso;
import cl.gob.scj.sgdp.model.TablaRetencion;
import cl.gob.scj.sgdp.model.Tarea;
import cl.gob.scj.sgdp.model.TextoParametroDeTarea;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.model.TipoParametroDeTarea;
import cl.gob.scj.sgdp.model.Unidad;
import cl.gob.scj.sgdp.service.CargaDeProcesosService;
import cl.gob.scj.sgdp.service.SerieDocumentalService;

@SuppressWarnings({ "unused" })
@Service
@Transactional(rollbackFor = Throwable.class)
public class SerieDocumentalServiceImpl implements SerieDocumentalService {
	
	private static final Logger log = Logger.getLogger(SerieDocumentalServiceImpl.class);

	@Autowired
	private TipoDeDocumentoDao tipoDocumentoDao;

	@Autowired
	private TablaRetencionDao tablaRetencionDao;
	
	@Autowired
	private SerieDocumentalDao serieDocumentalDao;

	@Override
	public SerieDocumentalDTO guardar(SerieDocumentalDTO serie) {
		SerieDocumental serieModel = new SerieDocumental();
		DtoToModel(serie, serieModel);
		serieDocumentalDao.insert(serieModel);
		serie.setIdSerieDocumental(serieModel.getIdSerieDocumental());
		return serie;
	}

	private void DtoToModel(SerieDocumentalDTO serie, SerieDocumental serieModel) {
		MacroProceso macroProceso = new MacroProceso();
		SuperProceso superProceso = new SuperProceso();
		macroProceso.setIdMacroProceso(serie.getIdFuncion());
		superProceso.setIdSuperProceso(serie.getIdSubFuncion());
		serieModel.setFuncion(macroProceso);
		serieModel.setSubFuncion(superProceso);
		serieModel.setCodigoProceso(serie.getCodigoProceso());
		serieModel.setNombreProceso(serie.getNombreProceso());
		serieModel.setSerieDocumental(serie.getSerieDocumental());
		serieModel.setSubSerieDocumental(serie.getSubSerieDocumental());
	}

	@Override
	public SerieDocumentalDTO actualizar(SerieDocumentalDTO serie) {
		SerieDocumental serieModel = serieDocumentalDao.find(serie.getIdSerieDocumental());
		DtoToModel(serie, serieModel);
		return serie;
	}

	@Override
	public List<SerieDocumentalDTO> list() {
		List<SerieDocumental> lista = serieDocumentalDao.getAll(SerieDocumental.class);
		List<SerieDocumentalDTO> listaDTO = new ArrayList<SerieDocumentalDTO>();
		for (SerieDocumental serieModel : lista) {
			SerieDocumentalDTO serie = new SerieDocumentalDTO();
			serie.setIdSerieDocumental(serieModel.getIdSerieDocumental());
			serie.setIdFuncion(serieModel.getFuncion().getIdMacroProceso());
			serie.setNombreFuncion(serieModel.getFuncion().getNombreMacroProceso());
			serie.setIdSubFuncion(serieModel.getSubFuncion().getIdSuperProceso());
			serie.setNombreSubFuncion(serieModel.getSubFuncion().getNombreSuperProceso());
			serie.setCodigoProceso(serieModel.getCodigoProceso());
			serie.setNombreProceso(serieModel.getNombreProceso());
			serie.setSerieDocumental(serieModel.getSerieDocumental());
			serie.setSubSerieDocumental(serieModel.getSubSerieDocumental());
			listaDTO.add(serie);
		}
		return listaDTO;
	}

	@Override
	public boolean elimina(SerieDocumentalDTO serie) {
		SerieDocumental serieModel = serieDocumentalDao.find(serie.getIdSerieDocumental());
		serieDocumentalDao.delete(serieModel);
		tablaRetencionDao.deleteBySerieDocumental(serie.getIdSerieDocumental());
		return true;
	}

	@Override
	public List<TipoDeDocumentoSerieDocumentalDTO> documentosDeProceso(String codigoProceso) {
		List<TipoDeDocumento> tiposModel = tipoDocumentoDao.getTiposDeDocumentosPorCodigoProceso(codigoProceso);
		Map<String, TipoDeDocumentoSerieDocumentalDTO> doctos = new HashMap<>();
		for (TipoDeDocumento t : tiposModel) {
			if (t.getConformaExpediente()) {
				if (doctos.containsKey(t.getNombreDeTipoDeDocumento())) {
					doctos.get(t.getNombreDeTipoDeDocumento()).getIds().add(t.getIdTipoDeDocumento());
				} else {
					TipoDeDocumentoSerieDocumentalDTO tds = new TipoDeDocumentoSerieDocumentalDTO();
					tds.setIdTipoDeDocumento(t.getIdTipoDeDocumento());
					tds.setCodigoTipoDocumonto(t.getCodTipoDocumento());
					tds.setNombreDeTipoDeDocumento(t.getNombreDeTipoDeDocumento());
					tds.getIds().add(t.getIdTipoDeDocumento());
					List<TablaRetencion> trs = tablaRetencionDao.getTablasDeRetencion(t.getIdTipoDeDocumento());
					if (trs.size() > 0) {
						tds.setAniosDeRetencion(trs.get(0).getAniosRetencion());
						tds.setTransferibleAarchivo(trs.get(0).getTransferibleArchivo());
					} else {
						tds.setTransferibleAarchivo(false);
						tds.setAniosDeRetencion(0);
					}
					doctos.put(t.getNombreDeTipoDeDocumento(), tds);
				}
			}
		}
		return new ArrayList<TipoDeDocumentoSerieDocumentalDTO>(doctos.values());
	}

	@Override
	public TipoDeDocumentoSerieDocumentalDTO saveTablaRetencion(TipoDeDocumentoSerieDocumentalDTO td) {
		SerieDocumental serie = serieDocumentalDao.find(Long.parseLong(td.getSerieDocumental()));
		boolean insert = false;
		for (Long id : td.getIds()) {
			TablaRetencion tr = tablaRetencionDao.findByIdTipoDocumentoAndIdSerieDocumental(id, Long.parseLong(td.getSerieDocumental()));
			if (tr==null) {
				tr = new TablaRetencion();
				insert = true;
			}
			tr.setAniosRetencion(td.getAniosDeRetencion());
			tr.setTransferibleArchivo(td.getTransferibleAarchivo());
			tr.setSerieDocumental(serie);
			TipoDeDocumento tipoDoc = new TipoDeDocumento();
			tipoDoc.setIdTipoDeDocumento(id);
			tr.setTipoDocumento(tipoDoc);
			if (insert) {
				tablaRetencionDao.insert(tr);
			}
		}
		return td;
	}

}