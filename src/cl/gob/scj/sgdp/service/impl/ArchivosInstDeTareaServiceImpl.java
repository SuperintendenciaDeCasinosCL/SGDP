package cl.gob.scj.sgdp.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.ArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dao.HistoricoArchivosInstDeTareaDao;
import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;
import cl.gob.scj.sgdp.service.ArchivosInstDeTareaService;
import cl.gob.scj.sgdp.util.SGDPUtil;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ArchivosInstDeTareaServiceImpl implements ArchivosInstDeTareaService {

	@Autowired
    private ArchivosInstDeTareaDao archivosInstDeTareaDao;
	
	@Autowired
	private HistoricoArchivosInstDeTareaDao historicoArchivosInstDeTareaDao;
	
	@Override
	public List<String> getIdDeDocumentosSubidosOficiales() {
		// TODO Auto-generated method stub
		return archivosInstDeTareaDao.getIdDeDocumentosSubidosOficiales();
	}
	
	@Override
	public List<ArchivosInstDeTareaDTO> getTodosLosDocSubidosPorIdExpediente(String idExpediente) {		
		List<ArchivosInstDeTareaDTO> archivosInstDeTareaDTOList = archivosInstDeTareaDao.getTodosLosDocSubidosPorIdExpediente(idExpediente);		
		List<ArchivosInstDeTareaDTO> archivosInstDeTareaDTOListHistoricoOrigen = historicoArchivosInstDeTareaDao.getTodosLosDocSubidosPorIdExpedienteHistoricoOrigen(idExpediente);
		SGDPUtil.agregaArchivosInstDeTareaDTOSiNoEstaEnDestino(archivosInstDeTareaDTOListHistoricoOrigen, archivosInstDeTareaDTOList);
		return archivosInstDeTareaDTOList;
	}
	
	@Override
	public ArchivosInstDeTareaDTO getArchivosInstDeTareaPorIdArchivoIdInstanciaDeTareaIdUsuario(String idArchivo, long idInstanciaDeTarea, String idUsuario) {		
		ArchivosInstDeTarea archivosInstDeTarea = archivosInstDeTareaDao.getArchivosInstDeTareaPorIdArchivoIdInstanciaDeTareaIdUsuario(idArchivo, idInstanciaDeTarea, idUsuario);
		ArchivosInstDeTareaDTO archivosInstDeTareaDTO = new ArchivosInstDeTareaDTO();		
		BeanUtils.copyProperties(archivosInstDeTarea, archivosInstDeTareaDTO);
		return archivosInstDeTareaDTO;
	}
	
}