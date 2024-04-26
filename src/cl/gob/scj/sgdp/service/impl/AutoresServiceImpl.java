package cl.gob.scj.sgdp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.gob.scj.sgdp.dao.AutorDao;
import cl.gob.scj.sgdp.dto.AutorDTO;
import cl.gob.scj.sgdp.model.Autor;
import cl.gob.scj.sgdp.service.AutoresService;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AutoresServiceImpl implements AutoresService {
	
	private static final Logger log = Logger.getLogger(AutoresServiceImpl.class);
	
	@Autowired
	private AutorDao autorDao;

	@Override
	public List<AutorDTO> getAutores() {
		List<Autor> listaAutoresDao = autorDao.getTodosLosAutores(); 
		List<AutorDTO> listaAutoresDto = new ArrayList<AutorDTO>();
		for (Autor autor : listaAutoresDao) {
			AutorDTO autorDTO = new AutorDTO(autor.getIdAutor(), autor.getNombreAutor());
			listaAutoresDto.add(autorDTO);
		}
		return listaAutoresDto;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void crearAutor(AutorDTO autorDTO) {
		Autor autor = new Autor();
		autor.setNombreAutor(autorDTO.getNombreAutor());
		autorDao.insert(autor);
	}
	
	@Override
	public AutorDTO buscarAutor(Long idAutor) {
		AutorDTO autorDTO = new AutorDTO();
		Autor autor = autorDao.getAutorPorIdAutor(idAutor);
		autorDTO.setNombreAutor(autor.getNombreAutor());
		autorDTO.setIdAutor(idAutor);
		return autorDTO;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void actualizarAutor(AutorDTO autorDTO) {
		Autor autor = autorDao.find(autorDTO.getIdAutor());
		autor.setNombreAutor(autorDTO.getNombreAutor());
	}
	
	@Override
	public void borraAutor(Long idAutor) {
		Autor autor = autorDao.find(idAutor);
		autorDao.delete(autor);
	}

}
