package cl.gob.scj.sgdp.tipos;

public enum ErroresType {

	E_MAIL("E_MAIL", 1);
	
	private final String nombreError;
	private final int codigoError;
	
	private ErroresType(String nombreError, int codigoError) {
		this.nombreError = nombreError;
		this.codigoError = codigoError;
	}

	public String getNombreError() {
		return nombreError;
	}

	public int getCodigoError() {
		return codigoError;
	}
		
}
