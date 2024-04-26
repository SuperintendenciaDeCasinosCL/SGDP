package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.control.MantenedorTipoDestinatarioControl;
import cl.gob.scj.sgdp.dao.TipoDeDestinatarioDao;
import cl.gob.scj.sgdp.dto.TipoDeDestinatarioDTO;
import cl.gob.scj.sgdp.model.TipoDeDestinatario;
import cl.gob.scj.sgdp.service.TipoDeDestinatarioService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class TipoDeDestinatarioServiceImpl implements TipoDeDestinatarioService {
	
	private static final Logger log = Logger.getLogger(TipoDeDestinatarioServiceImpl.class);
	
	@Autowired
	private TipoDeDestinatarioDao tipoDeDestinatarioDao;

	@Override
	public List<TipoDeDestinatarioDTO> getAllTipoDeDestinatario() {
		List<TipoDeDestinatario> tipoDeDestinatarioList = tipoDeDestinatarioDao.getAll(TipoDeDestinatario.class);
		List<TipoDeDestinatarioDTO> tipoDeDestinatarioDTOList = new ArrayList<>();
		for (TipoDeDestinatario tipoDeDestinatario: tipoDeDestinatarioList) {
			TipoDeDestinatarioDTO tipoDeDestinatarioDTO = new TipoDeDestinatarioDTO();
			BeanUtils.copyProperties(tipoDeDestinatario, tipoDeDestinatarioDTO);
			tipoDeDestinatarioDTOList.add(tipoDeDestinatarioDTO);
		}
		return tipoDeDestinatarioDTOList;
	}
	
	@Override
	public TipoDeDestinatarioDTO getTipoDeDestinatarioById(long idTipoDestinatario) {
		TipoDeDestinatario tipoDeDestinatario = tipoDeDestinatarioDao.find(Long.valueOf(idTipoDestinatario));
		TipoDeDestinatarioDTO tipoDeDestinatarioDTO = new TipoDeDestinatarioDTO();
		BeanUtils.copyProperties(tipoDeDestinatario, tipoDeDestinatarioDTO);
		return tipoDeDestinatarioDTO;
	}

	@Override
	public void insertaTipoDeDestinatario(TipoDeDestinatarioDTO tipoDeDestinatarioDTO, Usuario usuario) {		
		TipoDeDestinatario tipoDeDestinatarioExiste = tipoDeDestinatarioDao.getTipoDeDestinatarioPorNombre(tipoDeDestinatarioDTO.getNombreTipoDestinatario());
		if (tipoDeDestinatarioExiste!=null) {
			tipoDeDestinatarioDTO.setRespuesta("Ya existe un tipo de destinatario con nombre " + tipoDeDestinatarioDTO.getNombreTipoDestinatario());			
		} else {
			TipoDeDestinatario tipoDeDestinatario = new TipoDeDestinatario();
			tipoDeDestinatario.setNombreTipoDestinatario(tipoDeDestinatarioDTO.getNombreTipoDestinatario());
			tipoDeDestinatario.setUsuarioCreacion(usuario.getIdUsuario());
			tipoDeDestinatario.setFechaCreacion(new Date());
			tipoDeDestinatarioDao.insert(tipoDeDestinatario);
			log.info("Creando tipo de destinatario " + tipoDeDestinatario.getNombreTipoDestinatario() + ". Usuario que crea: " + usuario.getIdUsuario());
			tipoDeDestinatarioDTO.setRespuesta("OK");
		}		
	}
	
	@Override
	public void borraTipoDeDestinatario(TipoDeDestinatarioDTO tipoDeDestinatarioDTO, Usuario usuario) {
		List<TipoDeDestinatario> tiposDeDestinatarioExiste = tipoDeDestinatarioDao.getTiposDeDestinatarioEnListaDistribucion(tipoDeDestinatarioDTO.getIdTipoDestinatario());
		if (tiposDeDestinatarioExiste!=null && tiposDeDestinatarioExiste.size()>=1) {
			tipoDeDestinatarioDTO.setRespuesta("El tipo de destinatario no puede ser eliminado ya que tiene listas de distribuci&oacute;n asociadas");			
		} else {
			TipoDeDestinatario tipoDeDestinatario = tipoDeDestinatarioDao.find(tipoDeDestinatarioDTO.getIdTipoDestinatario());
			tipoDeDestinatarioDao.delete(tipoDeDestinatario);
			log.info("Borrando tipo de destinatario " + tipoDeDestinatario.getNombreTipoDestinatario() + ". Usuario que elimina: " + usuario.getIdUsuario());
			tipoDeDestinatarioDTO.setRespuesta("OK");
		}	
	}
	
	@Override
	public void actualizaTipoDeDestinatario(TipoDeDestinatarioDTO tipoDeDestinatarioDTO, Usuario usuario) {		
		TipoDeDestinatario tipoDeDestinatarioExiste = tipoDeDestinatarioDao.getTipoDeDestinatarioPorNombre(tipoDeDestinatarioDTO.getNombreTipoDestinatario());
		if (tipoDeDestinatarioExiste!=null) {
			tipoDeDestinatarioDTO.setRespuesta("Ya existe un tipo de destinatario con nombre " + tipoDeDestinatarioDTO.getNombreTipoDestinatario());			
		} else {
		
			TipoDeDestinatario tipoDeDestinatario = tipoDeDestinatarioDao.find(tipoDeDestinatarioDTO.getIdTipoDestinatario());
			tipoDeDestinatario.setNombreTipoDestinatario(tipoDeDestinatarioDTO.getNombreTipoDestinatario());
			log.info("Actualizando tipo de destinatario " + tipoDeDestinatario.getNombreTipoDestinatario() + ". Usuario que actualiza: " + usuario.getIdUsuario());
			tipoDeDestinatarioDTO.setRespuesta("OK");
		}		
	}

}