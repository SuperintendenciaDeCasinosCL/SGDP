package cl.gob.scj.sgdp.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cl.gob.scj.sgdp.dto.ArchivosInstDeTareaDTO;
import cl.gob.scj.sgdp.dto.UsuarioResponsabilidadDTO;

public class SGDPUtil {
	
	private static final Logger log = Logger.getLogger(SGDPUtil.class);
	
	private static final String[] IP_HEADER_CANDIDATES = { 
		    "X-Forwarded-For",
		    "Proxy-Client-IP",
		    "WL-Proxy-Client-IP",
		    "HTTP_X_FORWARDED_FOR",
		    "HTTP_X_FORWARDED",
		    "HTTP_X_CLUSTER_CLIENT_IP",
		    "HTTP_CLIENT_IP",
		    "HTTP_FORWARDED_FOR",
		    "HTTP_FORWARDED",
		    "HTTP_VIA",
		    "REMOTE_ADDR" };

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
	
	/**
	 * Valida rut de la forma XXXXXXXX-X
	 */
	public static boolean validaRut(String rut) {		
		if (rut==null || rut.isEmpty() || rut.equals("null")) {
			return false;
		}		
		rut = rut.replace(".", "");
		Pattern pattern = Pattern.compile("^[0-9]+-[0-9kK]{1}$");
		Matcher matcher = pattern.matcher(rut);
		if (matcher.matches() == false)
			return false;
		String[] stringRut = rut.split("-");
		return stringRut[1].toLowerCase().equals(dv(stringRut[0]));
	}

	/**
	 * Valida el digito verificador
	 */
	public static String dv(String rut) {
		Integer M = 0, S = 1, T = Integer.parseInt(rut);
		for (; T != 0; T = (int) Math.floor(T /= 10))
			S = (S + T % 10 * (9 - M++ % 6)) % 11;
		return (S > 0) ? String.valueOf(S - 1) : "k";
	}
	
	public static String getStackTrace(Exception e) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter( writer );
        e.printStackTrace(printWriter);
        printWriter.flush();
        return writer.toString();
    }
	
	public static String getClientIpAddress(HttpServletRequest request) {
	    for (String header : IP_HEADER_CANDIDATES) {
	        String ip = request.getHeader(header);
	        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
	            return ip;
	        }
	    }
	    return request.getRemoteAddr();
	}

}