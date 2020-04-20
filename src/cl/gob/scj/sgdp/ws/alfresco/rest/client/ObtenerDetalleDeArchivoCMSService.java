package cl.gob.scj.sgdp.ws.alfresco.rest.client;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.gob.scj.sgdp.auth.user.Usuario;
import cl.gob.scj.sgdp.model.InstanciaDeProceso;
import cl.gob.scj.sgdp.ws.alfresco.rest.response.DetalleDeArchivoResponse;

@Service
public interface ObtenerDetalleDeArchivoCMSService {
	
	public DetalleDeArchivoResponse obtenerDetalleDeArchivo(Usuario usuario, String idArchivo) throws Exception;

}
