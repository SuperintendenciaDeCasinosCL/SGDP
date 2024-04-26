package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.BitacoraSubTareaDTO;

@Service
public interface BitacoraSubTareaService {
	
	List<BitacoraSubTareaDTO> getAllByIdInstTarea(Long idInstTarea);
	BitacoraSubTareaDTO save(BitacoraSubTareaDTO bitacora);
	boolean delete(BitacoraSubTareaDTO bitacora);
	
}
