package cl.gob.scj.sgdp.exception;

import org.apache.log4j.Level;

public abstract class SgdpExceptionAbstract extends Exception {
	
	protected Level nivelLog = Level.ERROR;
	protected boolean recarga;
	
	public SgdpExceptionAbstract() {
		super();		
	}
	public SgdpExceptionAbstract(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);		
	}
	public SgdpExceptionAbstract(String message, Throwable cause) {
		super(message, cause);		
	}
	public SgdpExceptionAbstract(String message) {
		super(message);	
	}
	public SgdpExceptionAbstract(Throwable cause) {
		super(cause);		
	}
	public SgdpExceptionAbstract(String message, Level nivelLog, boolean recarga) {
		super(message);
		this.nivelLog = nivelLog;
		this.recarga = recarga;
	}
	public Level getNivelLog() {
		return nivelLog;
	}
	public void setNivelLog(Level nivelLog) {
		this.nivelLog = nivelLog;
	}
	public boolean getRecarga() {
		return recarga;
	}
	public void setRecarga(boolean recarga) {
		this.recarga = recarga;
	}
	
}
