package cl.gob.scj.sgdp.tipos;

public enum VinculacionType {

	ANTECESOR("Antecesor", 1),
	SUCESOR("Sucesor", 2);
	
	private final String nombreVinculacion;
	private final int codigoVinculacion;
	
	private VinculacionType(String nombreVinculacion, int codigoVinculacion) {
		this.nombreVinculacion = nombreVinculacion;
		this.codigoVinculacion = codigoVinculacion;
	}	
	public String getNombreVinculacion() {
		return nombreVinculacion;
	}
	public int getCodigoVinculacion() {
		return codigoVinculacion;
	}	
	
}
