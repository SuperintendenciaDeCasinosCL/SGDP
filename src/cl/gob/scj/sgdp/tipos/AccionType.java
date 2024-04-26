package cl.gob.scj.sgdp.tipos;

public enum AccionType {

	INSERTA ("INSERTA", 1),
    BORRA ("BORRA", 2),
    ACTUALIZA ("ACTUALIZA", 3),
    VISUALIZA ("VISUALIZA", 4),
    EDITA ("EDITA", 5),
    DESCARGA ("DESCARGA", 6),
    ANULA ("ANULA", 7),
    REVISA_DETALLE_DE_DOCUMENTO ("REVISA DETALLE DE DOCUMENTO", 8),
    DISTRIBUYE_DOCUMENTOS ("DISTRIBUYE DOCUMENTOS", 9),
    CREA_REGISTRO_LISTA_DISTRIBUCION ("CREA REGISTRO LISTA DISTRIBUCION DOCUMENTOS", 10),
    ACTUALIZA_REGISTRO_LISTA_DISTRIBUCION ("ACTUALIZA REGISTRO LISTA DISTRIBUCION", 11),
    BORRA_REGISTRO_LISTA_DISTRIBUCION("BORRA REGISTRO LISTA DISTRIBUCION", 12);
	
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
