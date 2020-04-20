package cl.gob.scj.sgdp.tipos;

public enum EstadoDeProcesoType {

	NUEVO ("NUEVO", 1),
    ASIGNADO ("ASIGNADO", 2),
    FINALIZADO ("FINALIZADO", 3),
    ANULADO ("ANULADO", 4);
	    
    private final String nombreEstadoDeProceso;
    private final int codigoEstadoDeProceso;
    
    private EstadoDeProcesoType(String nombreEstadoDeProceso,
			int codigoEstadoDeProceso) {
		this.nombreEstadoDeProceso = nombreEstadoDeProceso;
		this.codigoEstadoDeProceso = codigoEstadoDeProceso;
	}
    
	public String getNombreEstadoDeProceso() {
		return nombreEstadoDeProceso;
	}
	
	public int getCodigoEstadoDeProceso() {
		return codigoEstadoDeProceso;
	}
	
}
