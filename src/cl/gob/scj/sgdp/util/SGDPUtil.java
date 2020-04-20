package cl.gob.scj.sgdp.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.UsuarioResponsabilidadDTO;

public class SGDPUtil {
	
	private static final Logger log = Logger.getLogger(SGDPUtil.class);

	public static void agregaArchivosInstDeTareaDTOSiNoEstaEnDestino(List<ArchivosInstDeTareaDTO> listaOrigen, List<ArchivosInstDeTareaDTO> listaDestino) {
		if (listaOrigen!=null && listaDestino!=null && !listaOrigen.isEmpty()) {
			for (ArchivosInstDeTareaDTO archivosInstDeTareaDTO : listaOrigen) {
				if (listaDestino.indexOf(archivosInstDeTareaDTO)==-1) {
					log.debug("Agregando archivo de historico....");
					log.debug(archivosInstDeTareaDTO.toString());
					listaDestino.add(archivosInstDeTareaDTO);
				}
			}
		}
	}	
	
	public static void filtraArchivosObligatorios(List<ArchivosInstDeTareaDTO> listaDeArchivosInstDeTareaDTO) {
		log.debug("Inicio filtraArchivosObligatorios...");
		Iterator<ArchivosInstDeTareaDTO> it = listaDeArchivosInstDeTareaDTO.iterator();		
		while (it.hasNext()) {			
			ArchivosInstDeTareaDTO archivosInstDeTareaDTO = it.next();
			log.debug(archivosInstDeTareaDTO.toString());
			if ( (archivosInstDeTareaDTO.getConformaExpediente() == null)  || (archivosInstDeTareaDTO.getConformaExpediente() != null && archivosInstDeTareaDTO.getConformaExpediente().booleanValue() != true)) {
				log.info("Removiendo archivo por que no conforma expediente " + archivosInstDeTareaDTO.getIdArchivoCms());
				it.remove();
			} 
		}		
	}
	
	public static Map<String, ArchivosInstDeTareaDTO> generaMapDeArchivosInstDeTareaDTOPorIdCms(List<ArchivosInstDeTareaDTO> listaDeArchivosInstDeTareaDTO) {
		Map<String, ArchivosInstDeTareaDTO> mapDeArchivosInstDeTareaDTOPorIdCms = new HashMap<String, ArchivosInstDeTareaDTO>();
		for (ArchivosInstDeTareaDTO archivosInstDeTareaDTO : listaDeArchivosInstDeTareaDTO) {
			mapDeArchivosInstDeTareaDTOPorIdCms.put(archivosInstDeTareaDTO.getIdArchivoCms(), archivosInstDeTareaDTO);
		}		
		return mapDeArchivosInstDeTareaDTOPorIdCms;		
	}	
	
	public static UsuarioResponsabilidadDTO getUsuarioAletarorio(List<UsuarioResponsabilidadDTO> usuariosResponsabilidadDTO) {		
		return usuariosResponsabilidadDTO.get(new Random().nextInt(usuariosResponsabilidadDTO.size()));
	}
	
}