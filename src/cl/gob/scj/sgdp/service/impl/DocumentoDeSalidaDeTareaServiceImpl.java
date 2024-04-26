package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.DocumentoDeSalidaDeTareaDao;
import cl.gob.scj.sgdp.dao.PlantillaDeDocumentoDao;
import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dto.PlantillaDTO;
import cl.gob.scj.sgdp.dto.TareaYTipoDeDocumentoDTO;
import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTarea;
import cl.gob.scj.sgdp.model.PlantillaDeDocumento;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.service.DocumentoDeSalidaDeTareaService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class DocumentoDeSalidaDeTareaServiceImpl implements DocumentoDeSalidaDeTareaService {

	@Autowired
	private DocumentoDeSalidaDeTareaDao docSalidaDao;

	@Autowired
	private PlantillaDeDocumentoDao plantillaDao;
	
	@Autowired
	private TipoDeDocumentoDao tipoDeDocumentoDao;

	@Override
	public List<TareaYTipoDeDocumentoDTO> getTareaYTipoDeDocumentoPorIdProceso(Long idProceso) {
		List<DocumentoDeSalidaDeTarea> docPKs = docSalidaDao.getByIdProceso(idProceso);
		List<TareaYTipoDeDocumentoDTO> tyts = new ArrayList<TareaYTipoDeDocumentoDTO>();

		for (DocumentoDeSalidaDeTarea docPK : docPKs) {
			TareaYTipoDeDocumentoDTO tyt = new TareaYTipoDeDocumentoDTO();
			tyt.setIdTarea(docPK.getId().getTarea().getIdTarea());
			tyt.setCodigoTarea(docPK.getId().getTarea().getIdDiagrama());
			tyt.setNombreTarea(docPK.getId().getTarea().getNombreTarea());
			tyt.setIdTipoDeDocumento(docPK.getId().getTipoDeDocumento().getIdTipoDeDocumento());
			tyt.setNombreDeTipoDeDocumento(docPK.getId().getTipoDeDocumento().getNombreDeTipoDeDocumento());
			if (docPK.getId().getTipoDeDocumento().getPlantilla() != null) {
				tyt.setIdPlantilla(docPK.getId().getTipoDeDocumento().getPlantilla().getIdPlantillaDeDocumento());
				tyt.setNombrePlantilla(docPK.getId().getTipoDeDocumento().getPlantilla().getNombre());
			}

			tyts.add(tyt);
		}

		return tyts;

	}

	@Override
	public List<TareaYTipoDeDocumentoDTO> updatePlantillaDocumento(List<TareaYTipoDeDocumentoDTO> list) {
		for (TareaYTipoDeDocumentoDTO tyt : list) {
			DocumentoDeSalidaDeTarea docSalida = docSalidaDao.getByIdDocumentoSalida(tyt.getIdTipoDeDocumento(), tyt.getIdTarea());
			if (tyt.getIdPlantilla() != 0) {
				PlantillaDeDocumento pl = new PlantillaDeDocumento();
				pl.setIdPlantillaDeDocumento(tyt.getIdPlantilla());
				docSalida.getId().getTipoDeDocumento().setPlantilla(pl);
			} else {
				PlantillaDeDocumento pl = null;
				docSalida.getId().getTipoDeDocumento().setPlantilla(pl);
			}

			docSalidaDao.updatePlantilla(docSalida);
		}
		return list;
	}

	@Override
	public PlantillaDTO savePlantilla(PlantillaDTO plantillaDTO) {
		plantillaDao.actualizaVigencia(plantillaDTO.getCodigo());
		PlantillaDeDocumento plantillaDeDocumento = new PlantillaDeDocumento();
		plantillaDeDocumento.setNombre(plantillaDTO.getNombre());
		plantillaDeDocumento.setCodigo(plantillaDTO.getCodigo());
		plantillaDeDocumento.setDescripcion(plantillaDTO.getDescripcion());
		plantillaDeDocumento.setPlantilla(plantillaDTO.getPlantilla());
		plantillaDeDocumento.setVigente(true);
		plantillaDao.guardaPlantilla(plantillaDeDocumento);
		List<TipoDeDocumento> tiposDeDocumentos = tipoDeDocumentoDao.getTiposDeDocumentosPorCodigoPlantilla(plantillaDTO.getCodigo(), false);
		for (TipoDeDocumento tipoDeDocumento : tiposDeDocumentos) {
			tipoDeDocumento.setPlantilla(plantillaDeDocumento);
		}
		return plantillaDTO;
	}


	@Override
	public PlantillaDTO getPlantillaPorIdTipoDeDocumento(Long idTipoDeDocumento) {
		PlantillaDeDocumento pmodel = plantillaDao.getPlantillaPorIdTipoDeDocumento(idTipoDeDocumento);
		if (pmodel != null) {
			PlantillaDTO pl = new PlantillaDTO();
			pl.setNombre(pmodel.getNombre());
			pl.setDescripcion(pmodel.getDescripcion());
			pl.setCodigo(pmodel.getCodigo());
			pl.setVigente(pmodel.getVigente());
			pl.setPlantilla(pmodel.getPlantilla());
			return pl;
		} else {
			return null;
		}
	}

}
