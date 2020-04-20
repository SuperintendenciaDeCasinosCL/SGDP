package cl.gob.scj.sgdp.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class StringUtilSGDP {
	
	private static final Logger log = Logger.getLogger(StringUtilSGDP.class);
	
	public static boolean estaValorEnListaDeString(String valor, List<String> listaDeString) {		
		for (String s : listaDeString) {
			log.debug("valor: " + valor);
			log.debug("s: " + s);
			if (s.equals(valor)) {
				return true;
			}
		}		
		return false;
	}
	
	public static String cambiaExtension(String nombreArchivo, String extension) {	    
	    if (nombreArchivo.contains(".")) {
	    	nombreArchivo = nombreArchivo.substring(0, nombreArchivo.lastIndexOf('.'));
	    }
	    nombreArchivo += "." + extension;	   
	    return nombreArchivo;
	}
	
	/*public static boolean comparaSacandoEspaciosCaracteresEspecialesIgnorandoCase(String s1, String s2) {
		int a = 160;
		char b = (char) a;
	    String s = String.valueOf(b);
	    return s1.replaceAll("\\s+","").replaceAll(s,"").equalsIgnoreCase(s2.replaceAll("\\s+","").replaceAll(s,""));    
	}*/
	
	public static boolean comparaStringConLevenshteinDistance(String s1, String s2, int tolerancia) {
		log.debug("Inicio comparaStringConLevenshteinDistance");
		LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
		Integer distancia = levenshteinDistance.apply(s1.replaceAll("\\s+","").toUpperCase(), s2.replaceAll("\\s+","").toUpperCase());
		log.debug("distancia: " + distancia);
		if (distancia.intValue()<=tolerancia) {
			return true;
		} else {
			return false;
		}
	}

}