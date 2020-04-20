package cl.gob.scj.sgdp.exception;

import org.apache.log4j.Level;

public class SgdpException extends SgdpExceptionAbstract {

	public SgdpException() {
		super();	
	}

	public SgdpException(String message, Level nivelLog, boolean recarga) {
		super(message, nivelLog, recarga);	
	}

	public SgdpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);		
	}

	public SgdpException(String message, Throwable cause) {
		super(message, cause);	
	}

	public SgdpException(String message) {
		super(message);		
	}

	public SgdpException(Throwable cause) {
		super(cause);	
	}	
		
}
