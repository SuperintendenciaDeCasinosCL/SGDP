package cl.gob.scj.sgdp.dao;

import java.util.List;

import cl.gob.scj.sgdp.model.Autor;

public interface AutorDao {
	
	public List<Autor> getTodosLosAutores();
	
	public Autor getAutorPorIdAutor(long idAutor);

}
