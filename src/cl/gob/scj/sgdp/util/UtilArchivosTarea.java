package cl.gob.scj.sgdp.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import cl.gob.scj.sgdp.model.ArchivosInstDeTarea;

public class UtilArchivosTarea {
	
	private static final Logger log = Logger.getLogger(UtilArchivosTarea.class);
	
	public static List<ArchivosInstDeTarea> masiveFix(List<ArchivosInstDeTarea> in) {
		List<ArchivosInstDeTarea> out = new ArrayList<ArchivosInstDeTarea>();
		// CDF aca se deben filtrar aquellos que son los definitivos
		HashMap<String, String> hashProcesos = new HashMap<String, String>();
		for (ArchivosInstDeTarea entity : in) {
			if (entity.getArchivosInstDeTareaMetadata() != null) {
				hashProcesos.put(entity.getInstanciaDeTarea().getInstanciaDeProceso().getIdInstanciaDeProceso()+"", "");
			}
		}
		// con los procesos tengo que solo enviar aquellos ultimos documentos que aparecen.
		for (String key : hashProcesos.keySet()) {
		    List<ArchivosInstDeTarea> archPorProceso = new ArrayList<>();
		    for (ArchivosInstDeTarea entity : in) {
		    	if (entity.getArchivosInstDeTareaMetadata() != null) {
		    		if (entity.getInstanciaDeTarea().getInstanciaDeProceso().getIdInstanciaDeProceso()==Long.parseLong(key)) {
		    			archPorProceso.add(entity);
		    		}
		    	}
		    }
		    // ya tengo los archivos por instancia de proceso.
		    // hay que poner el documento mas reciente y solo una vez
		    HashMap<String, ArchivosInstDeTarea> hashArchivosUnicos = new HashMap<String, ArchivosInstDeTarea>();
		    for (ArchivosInstDeTarea archProceso: archPorProceso) {
		    	String nombreTipoDoc = archProceso.getTipoDeDocumento().getNombreDeTipoDeDocumento();
		    	ArchivosInstDeTarea archUnico = hashArchivosUnicos.get(nombreTipoDoc);
		    	if (archUnico==null) {
		    		hashArchivosUnicos.put(nombreTipoDoc, archProceso);
		    		continue;
		    	} else {
		    		Date fechaSubido = archUnico.getFechaSubido(); // fecha Subido anterior
		    		if (fechaSubido.before(archProceso.getFechaSubido())) {
		    			hashArchivosUnicos.put(nombreTipoDoc, archProceso);
		    		}
		    	}
		    }
		    for (String llave: hashArchivosUnicos.keySet()) {
		    	ArchivosInstDeTarea archivosInstDeTarea = hashArchivosUnicos.get(llave);
		    	//ArchivosInstDeTareaDTO dtoReal = new ArchivosInstDeTareaDTO();
		    	//dtoReal.setNombreArchivo(archivosInstDeTarea.getNombreArchivo());
		    	//dtoReal.setIdArchivoInstanciaTarea(archivosInstDeTarea.getIdArchivosInstDeTarea());
		    	//listaRealSalida.add(dtoReal);
		    	out.add(archivosInstDeTarea);
		    }
		}
		
		return out;
	}

}
