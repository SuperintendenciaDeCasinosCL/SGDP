package cl.gob.scj.sgdp.dao;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.model.CometarioDeArchivo;

public interface CometarioDeArchivoDao {

	public void insertCometarioDeArchivo(CometarioDeArchivo cometarioDeArchivo, Usuario usuario);
	
	public CometarioDeArchivo getCometarioDeArchivoPorNombreArchivo(String nombreArchivo, Usuario usuario);
	
}
