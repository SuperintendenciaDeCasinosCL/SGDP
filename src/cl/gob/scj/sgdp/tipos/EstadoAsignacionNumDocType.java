package cl.gob.scj.sgdp.tipos;

public enum EstadoAsignacionNumDocType {

	RESERVADO("RESERVADO", 1), FIRMADO("FIRMADO", 2);

	private final String nombreEstadoAsignacionNumDoc;
	private final int codigoEstadoAsignacionNumDoc;

	private EstadoAsignacionNumDocType(String nombreEstadoAsignacionNumDoc,
			int codigoEstadoAsignacionNumDoc) {
		this.nombreEstadoAsignacionNumDoc = nombreEstadoAsignacionNumDoc;
		this.codigoEstadoAsignacionNumDoc = codigoEstadoAsignacionNumDoc;
	}

	public String getNombreEstadoAsignacionNumDoc() {
		return nombreEstadoAsignacionNumDoc;
	}

	public int getCodigoEstadoAsignacionNumDoc() {
		return codigoEstadoAsignacionNumDoc;
	}

}
