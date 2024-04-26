package cl.gob.scj.sgdp.tipos;

public enum HabilitaFuncionalidadType {

	FIRMA_MASIVA ("FIRMA_MASIVA", 1),
	OFICINA_VIRTUAL ("OFICINA_VIRTUAL", 2),
	UNIDADES_OPERATIVAS("UNIDADES_OPERATIVAS", 3);
	
	private final String nombreFuncionalidad;
	private final long idFuncionalidad;
	
	private HabilitaFuncionalidadType(String nombreFuncionalidad, long idFuncionalidad) {
		this.nombreFuncionalidad = nombreFuncionalidad;
		this.idFuncionalidad = idFuncionalidad;
	}

	public String getNombreFuncionalidad() {
		return nombreFuncionalidad;
	}

	public long getIdFuncionalidad() {
		return idFuncionalidad;
	}
	
}
