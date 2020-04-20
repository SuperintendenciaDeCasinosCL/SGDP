package cl.gob.scj.sgdp.exception;

import org.apache.log4j.Level;

public class SgdpExceptionValidaTareaEnBE extends SgdpExceptionAbstract {

	public SgdpExceptionValidaTareaEnBE() {
		super();		
	}

	public SgdpExceptionValidaTareaEnBE(String message, Level nivelLog, boolean recarga) {
		super(message, nivelLog, recarga);		
	}

	public SgdpExceptionValidaTareaEnBE(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);		
	}

	public SgdpExceptionValidaTareaEnBE(String message, Throwable cause) {
		super(message, cause);		
	}

	public SgdpExceptionValidaTareaEnBE(String message) {
		super(message);		
	}

	public SgdpExceptionValidaTareaEnBE(Throwable cause) {
		super(cause);		
	}

}
