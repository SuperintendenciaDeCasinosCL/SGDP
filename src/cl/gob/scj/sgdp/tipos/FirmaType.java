package cl.gob.scj.sgdp.tipos;

public enum FirmaType {

	VISACION ("VISACION", 1),
    CENTRALIZADO ("CENTRALIZADO", 2),
    WEB_START("WEB_START", 3);
	
    private final String nombreFirma;
    private final int codigoFirma;    
    
	private FirmaType(String nombreFirma, int codigoFirma) {
		this.nombreFirma = nombreFirma;
		this.codigoFirma = codigoFirma;
	}
	public String getNombreFirma() {
		return nombreFirma;
	}
	public int getCodigoFirma() {
		return codigoFirma;
	}
	
}
