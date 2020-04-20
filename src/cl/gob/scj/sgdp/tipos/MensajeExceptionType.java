package cl.gob.scj.sgdp.tipos;

public enum MensajeExceptionType {

	AVANZA_PROCESO_CON_ADVERTENCIA_VISACION ("AVANZA_PROCESO_CON_ADVERTENCIA_VISACION", 1),
	AVANZA_PROCESO_CON_ADVERTENCIA_FEA ("AVANZA_PROCESO_CON_ADVERTENCIA_FEA", 2);
	    
    private final String nombreMensajeException;
    private final long MensajeException;  
    
	private MensajeExceptionType(String nombreMensajeException, long mensajeException) {
		this.nombreMensajeException = nombreMensajeException;
		MensajeException = mensajeException;
	}
	
	public String getNombreMensajeException() {
		return nombreMensajeException;
	}
	
	public long getMensajeException() {
		return MensajeException;
	}
    
	
}
