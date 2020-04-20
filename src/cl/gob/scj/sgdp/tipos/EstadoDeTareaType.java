package cl.gob.scj.sgdp.tipos;

public enum EstadoDeTareaType {
	
	NUEVA ("NUEVA", 1),
    ASIGNADA ("ASIGNADA", 2),
    FINALIZADA ("FINALIZADA", 3),
    ANULADA ("ANULADA", 4);
	    
    private final String nombreEstadoDeTarea;
    private final int codigoEstadoDeTarea;

    private EstadoDeTareaType(String nombreEstadoDeTarea, int codigoEstadoDeTarea) {
        this.nombreEstadoDeTarea = nombreEstadoDeTarea;
        this.codigoEstadoDeTarea = codigoEstadoDeTarea;
    }

	public String getNombreEstadoDeTarea() {
		return nombreEstadoDeTarea;
	}

	public int getCodigoEstadoDeTarea() {
		return codigoEstadoDeTarea;
	}   
 

}
