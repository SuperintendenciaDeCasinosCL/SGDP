package cl.gob.scj.sgdp.tipos;

public enum AccionType {

	INSERTA ("INSERTA", 1),
    BORRA ("BORRA", 2),
    ACTUALIZA ("ACTUALIZA", 3)
    ;
	
	private final String nombreAccion;
	private final long idAccion;
	
	private AccionType(String nombreAccion, long idAccion) {
		this.nombreAccion = nombreAccion;
		this.idAccion = idAccion;
	}

	public String getNombreAccion() {
		return nombreAccion;
	}

	public long getIdAccion() {
		return idAccion;
	}	
	
}
