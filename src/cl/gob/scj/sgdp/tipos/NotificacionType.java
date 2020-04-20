package cl.gob.scj.sgdp.tipos;

public enum NotificacionType {
	
	MANUAL ("MANUAL", 1),
	PREDETERMINADA ("PREDETERMINADA", 2);
	
	private final String nombreTipoNotificacion;
	private final long idTipoNotificacion;	
	
	private NotificacionType(String nombreTipoNotificacion, long idTipoNotificacion) {
		this.nombreTipoNotificacion = nombreTipoNotificacion;
		this.idTipoNotificacion = idTipoNotificacion;
	}

	public String getNombreTipoNotificacion() {
		return nombreTipoNotificacion;
	}

	public long getIdTipoNotificacion() {
		return idTipoNotificacion;
	}
	
}
