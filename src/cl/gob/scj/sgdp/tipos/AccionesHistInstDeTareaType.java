package cl.gob.scj.sgdp.tipos;

public enum AccionesHistInstDeTareaType {

	CREA ("CREA", 1),
    DEVUELVE ("DEVUELVE", 2),
    ENVIA ("ENVIA", 3),
    REASIGNA ("REASIGNA", 4),
    DESPACHA ("DESPACHA", 5),
    FINALIZA ("FINALIZA", 6),
    ANULA ("ANULA", 7),
    REABRE ("REABRE", 8),
    CERRAR ("CERRAR", 9),
    ABRE ("ABRE", 10),
    SUBE ("SUBE", 11);
	    
    private final String nombreAccion;
    private final long idAccionHistoricoInstDeTarea;
    
	private AccionesHistInstDeTareaType(String nombreAccion,
			long idAccionHistoricoInstDeTarea) {
		this.nombreAccion = nombreAccion;
		this.idAccionHistoricoInstDeTarea = idAccionHistoricoInstDeTarea;
	}
	
	public String getNombreAccion() {
		return nombreAccion;
	}
	public long getIdAccionHistoricoInstDeTarea() {
		return idAccionHistoricoInstDeTarea;
	}
	
}
