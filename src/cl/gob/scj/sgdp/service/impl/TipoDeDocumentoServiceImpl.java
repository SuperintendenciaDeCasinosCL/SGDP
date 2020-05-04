package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.TipoDeDocumentoDao;
import cl.gob.scj.sgdp.dto.TipoDeDocumentoDTO;
import cl.gob.scj.sgdp.model.TipoDeDocumento;
import cl.gob.scj.sgdp.service.TipoDeDocumentoService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class TipoDeDocumentoServiceImpl implements TipoDeDocumentoService {
	
	@Autowired
	private TipoDeDocumentoDao tipoDeDocumentoDao;
	
	@Override
	public TipoDeDocumentoDTO getTipoDeDocumentoDTOPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(String nombreDeTipoDeDocumento, long idInstanciaDeTarea) {
		TipoDeDocumento tipoDeDocumento;
		if (nombreDeTipoDeDocumento!=null && nombreDeTipoDeDocumento.equals("Antecedente")) {
			tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorNombreDeTipoDeDocumento(nombreDeTipoDeDocumento);
		} else {
			tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorNombreDeTipoDeDocumentoIdInstanciaDeTarea(nombreDeTipoDeDocumento, idInstanciaDeTarea);
		}
		TipoDeDocumentoDTO tipoDeDocumentoDTO = new TipoDeDocumentoDTO();
		if (tipoDeDocumento!=null) {
			BeanUtils.copyProperties(tipoDeDocumento, tipoDeDocumentoDTO);
		}		
		return tipoDeDocumentoDTO;
	}

	@Override
	public TipoDeDocumentoDTO getTipoDeDocumentoDTOPorIdTipoDeDocumento(long idTipoDeDocumento) {
		TipoDeDocumento tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorIdTipoDeDocumento(idTipoDeDocumento);
		TipoDeDocumentoDTO tipoDeDocumentoDTO = new TipoDeDocumentoDTO();
		BeanUtils.copyProperties(tipoDeDocumento, tipoDeDocumentoDTO);
		return tipoDeDocumentoDTO;
	}
	
	/*@Override
	public TipoDeDocumentoDTO getTipoDeDocumentoPorNombreDeTipoDeDocumentoPorIdExpediente(String nombreDeTipoDeDocumento, String idExpediente) {
		TipoDeDocumento tipoDeDocumento;
		if (nombreDeTipoDeDocumento!=null && nombreDeTipoDeDocumento.equals("Antecedente")) {
			tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorNombreDeTipoDeDocumento(nombreDeTipoDeDocumento);
		} else {
			tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorNombreDeTipoDeDocumentoPorIdExpediente(nombreDeTipoDeDocumento, idExpediente);
		}
		TipoDeDocumentoDTO tipoDeDocumentoDTO = new TipoDeDocumentoDTO();
		BeanUtils.copyProperties(tipoDeDocumento, tipoDeDocumentoDTO);
		return tipoDeDocumentoDTO;
	}*/
	
	@Override
	public TipoDeDocumentoDTO getTipoDeDocumentoDTOPorIdProceso(long idProceso) {
		TipoDeDocumento tipoDeDocumento = tipoDeDocumentoDao.getTipoDeDocumentoPorIdProceso(idProceso);
		if (tipoDeDocumento!=null) {
			TipoDeDocumentoDTO tipoDeDocumentoDTO = new TipoDeDocumentoDTO();
			BeanUtils.copyProperties(tipoDeDocumento, tipoDeDocumentoDTO);
			return tipoDeDocumentoDTO;
		} else {
			return null;
		}
		
	}
	
	@Override
	public List<TipoDeDocumentoDTO> getTodosLosTiposDeDocumentos() {
		List<TipoDeDocumentoDTO> tiposDeDocumentosDTO = new ArrayList<TipoDeDocumentoDTO>();
		List<TipoDeDocumento> tiposDeDocumentos = tipoDeDocumentoDao.getTodosLosTiposDeDocumentos();
		for(TipoDeDocumento tipoDeDocumento: tiposDeDocumentos) {
			TipoDeDocumentoDTO tipoDeDocumentoDTO = new TipoDeDocumentoDTO(tipoDeDocumento.getIdTipoDeDocumento(), tipoDeDocumento.getNombreDeTipoDeDocumento());
			tiposDeDocumentosDTO.add(tipoDeDocumentoDTO);
		}
		return tiposDeDocumentosDTO;
	}

	@Override
	public List<TipoDeDocumentoDTO> getTipoDeDocumentoPrimeraTareaDocAdiccionales() {
		return tipoDeDocumentoDao.getTipoDeDocumentoPrimeraTareaDocAdiccionales();
	}
	
	@Override
	public List<TipoDeDocumentoDTO> getTiposDeDocumentosPorCodigoProceso(String codigoProceso) {
		List<TipoDeDocumentoDTO> tiposDeDocumentosDTO = new ArrayList<TipoDeDocumentoDTO>();
		List<TipoDeDocumento> tiposDeDocumentos = tipoDeDocumentoDao.getTiposDeDocumentosPorCodigoProceso(codigoProceso);
		for(TipoDeDocumento tipoDeDocumento: tiposDeDocumentos) {
			TipoDeDocumentoDTO tipoDeDocumentoDTO = new TipoDeDocumentoDTO(tipoDeDocumento.getIdTipoDeDocumento(), tipoDeDocumento.getNombreDeTipoDeDocumento());
			tiposDeDocumentosDTO.add(tipoDeDocumentoDTO);
		}
		return tiposDeDocumentosDTO;
	}
	
	@Override
	public List<TipoDeDocumentoDTO> getTiposDeDocumentosPorNombreExpediente(String nombreExpediente) {
		List<TipoDeDocumentoDTO> tiposDeDocumentosDTO = new ArrayList<TipoDeDocumentoDTO>();
		List<TipoDeDocumento> tiposDeDocumentos = tipoDeDocumentoDao.getTiposDeDocumentosPorNombreExpediente(nombreExpediente);
		for(TipoDeDocumento tipoDeDocumento: tiposDeDocumentos) {
			TipoDeDocumentoDTO tipoDeDocumentoDTO = new TipoDeDocumentoDTO(tipoDeDocumento.getIdTipoDeDocumento(), tipoDeDocumento.getNombreDeTipoDeDocumento());
			tiposDeDocumentosDTO.add(tipoDeDocumentoDTO);
		}
		return tiposDeDocumentosDTO;
	}
	
	@Override
	public List<TipoDeDocumentoDTO> getTiposDeDocumentosPorIdExpediente(String idExpediente) {
		List<TipoDeDocumentoDTO> tiposDeDocumentosDTO = new ArrayList<TipoDeDocumentoDTO>();
		List<TipoDeDocumento> tiposDeDocumentos = tipoDeDocumentoDao.getTiposDeDocumentosPorIdExpediente(idExpediente);
		for(TipoDeDocumento tipoDeDocumento: tiposDeDocumentos) {
			TipoDeDocumentoDTO tipoDeDocumentoDTO = new TipoDeDocumentoDTO(tipoDeDocumento.getIdTipoDeDocumento(), tipoDeDocumento.getNombreDeTipoDeDocumento());
			tiposDeDocumentosDTO.add(tipoDeDocumentoDTO);
		}
		return tiposDeDocumentosDTO;
	}
	
}
