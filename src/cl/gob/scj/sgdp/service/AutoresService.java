/**
 * 
 */
package cl.gob.scj.sgdp.service;

import java.util.List;

import cl.gob.scj.sgdp.dto.AutorDTO;

public interface AutoresService {
	List<AutorDTO> getAutores();
	void crearAutor(AutorDTO autorDTO);
	AutorDTO buscarAutor(Long idAutor);
	void actualizarAutor(AutorDTO autorDTO);
	void borraAutor(Long idAutor);
}
