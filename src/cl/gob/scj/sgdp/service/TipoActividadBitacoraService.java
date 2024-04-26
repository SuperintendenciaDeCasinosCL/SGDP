package cl.gob.scj.sgdp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.dto.TipoActividadBitacoraDTO;

@Service
public interface TipoActividadBitacoraService {
	List<TipoActividadBitacoraDTO> getAll();
	TipoActividadBitacoraDTO getById(Long id);
	Boolean save(TipoActividadBitacoraDTO tipo);
	Boolean update(TipoActividadBitacoraDTO tipo);
	List<TipoActividadBitacoraDTO> getAllActive();
	Boolean remove(Long idActividad);
}
