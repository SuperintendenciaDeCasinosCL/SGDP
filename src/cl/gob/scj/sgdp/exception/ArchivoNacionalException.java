package cl.gob.scj.sgdp.exception;

import org.apache.log4j.Level;

public class ArchivoNacionalException extends SgdpExceptionAbstract {

	public ArchivoNacionalException() {
		super();	
	}

	public ArchivoNacionalException(String message, Level nivelLog, boolean recarga) {
		super(message, nivelLog, recarga);	
	}

	public ArchivoNacionalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);		
	}

	public ArchivoNacionalException(String message, Throwable cause) {
		super(message, cause);	
	}

	public ArchivoNacionalException(String message) {
		super(message);		
	}

	public ArchivoNacionalException(Throwable cause) {
		super(cause);	
	}	
		
}
