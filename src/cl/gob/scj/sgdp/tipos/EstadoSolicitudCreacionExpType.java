package cl.gob.scj.sgdp.tipos;

public enum EstadoSolicitudCreacionExpType {
	
	NUEVA ("NUEVA", 1),
	SOLICITADA ("SOLICITADA", 2),
	FINALIZADA ("FINALIZADA", 3),
	RECHAZADA ("RECHAZADA", 4),
	SOLICITADA_EXT ("SOLICITADA_EXT", 5);

	private final String nombreEstadoSolicitudCreacionExp;
	private final long idEstadoSolicitudCreacionExp;	
	
	private EstadoSolicitudCreacionExpType(String nombreEstadoSolicitudCreacionExp, long idEstadoSolicitudCreacionExp) {
		this.nombreEstadoSolicitudCreacionExp = nombreEstadoSolicitudCreacionExp;
		this.idEstadoSolicitudCreacionExp = idEstadoSolicitudCreacionExp;
	}

	public long getIdEstadoSolicitudCreacionExp() {
		return idEstadoSolicitudCreacionExp;
	}

	public String getNombreEstadoSolicitudCreacionExp() {
		return nombreEstadoSolicitudCreacionExp;
	}	
	
}