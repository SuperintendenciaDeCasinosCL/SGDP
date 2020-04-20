package cl.gob.scj.sgdp.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
	private static final Logger log = Logger.getLogger(FileUtil.class);
	
	public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
	        File convFile = new File( multipart.getOriginalFilename());
	        multipart.transferTo(convFile);	      
	        return convFile;
	}
	
	public static String encodeByteArrayToBase64(byte[] archiboByte, String codificacion) throws IOException {
		
		log.debug("Inicio encodeByteArrayToBase64");
		
		byte[] encoded = Base64.encode(archiboByte);
		String encodedString = new String(encoded, codificacion);
		
	    log.debug("Fin encodeByteArrayToBase64");
	    
	    return encodedString;		
		
	}
	
	public static byte[] decodeBase64ToByteArray(String encodedString, String codificacion) throws IOException {
		
		log.debug("Inicio encodeBase64ToByteArray");
		
		byte[] encoded = encodedString.getBytes(codificacion);
		
		log.debug("Fin encodeBase64ToByteArray");
		
		return Base64.decode(encoded); 		
		
	}
	
	public static String checkSum(byte[] archivoByte/*, String algoritmo*/) throws NoSuchAlgorithmException, IOException {
	    
		log.debug("Inicio checkSum");
	
    	ByteArrayInputStream bis = new ByteArrayInputStream(archivoByte);
    	
    	String sha256Hex = DigestUtils.sha256Hex(bis);
		
		log.debug("Fin checkSum");
		
		return sha256Hex;
	    
	}
	
	public static byte[] descargaArchivoDesdeUrl(String urlString, int connectTimeout, int readTimeout) throws IOException{
		
		log.debug("Inicio descargaArchivoDesdeUrl");
	    
		URL url = new URL(urlString);
		
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        conn.connect(); 

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(conn.getInputStream(), baos);
       
        log.debug("Fin descargaArchivoDesdeUrl");  
        
        return baos.toByteArray();
	    
	}		

	public static byte[] convertirFileEnBytes(File archivo){
		
		 FileInputStream fileInputStream = null;
  	      byte[] bFile = new byte[(int) archivo.length()];
  	      try
  	      {
  	         //convert file into array of bytes
  	         fileInputStream = new FileInputStream(archivo);
  	         fileInputStream.read(bFile);  	         
  	      }
  	      catch (Exception e)
  	      {
  	    	StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			log.error(exceptionAsString);
  	      } finally {
  	    	  if (fileInputStream!=null) {
  	    		try {
					fileInputStream.close();
				} catch (IOException e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					log.error(exceptionAsString);	
				}
  	    	  }
  	      }
				
		return bFile;
	}
	
}
