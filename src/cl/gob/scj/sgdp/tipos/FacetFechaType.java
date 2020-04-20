package cl.gob.scj.sgdp.tipos;

public enum FacetFechaType {

	HOY ("HOY", 1),
	ESTA_SEMANA ("ESTA_SEMANA", 2),
	ESTE_MES("ESTE_MES", 3),
	MAYOR_A_UN_YEAR("MAYOR_A_UN_A\u00d1O", 4),
	ESTE_YEAR("ESTE_A\u00d1O", 5);
	
	private final String nombreFacetFecha;
	private final int codigoFacetFecha;
	
	private FacetFechaType(String nombreFacetFecha, int codigoFacetFecha) {
		this.nombreFacetFecha = nombreFacetFecha;
		this.codigoFacetFecha = codigoFacetFecha;
	}
	public String getNombreFacetFecha() {
		return nombreFacetFecha;
	}
	public int getCodigoFacetFecha() {
		return codigoFacetFecha;
	}
	
	
}
