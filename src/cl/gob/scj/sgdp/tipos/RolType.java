package cl.gob.scj.sgdp.tipos;

public enum RolType {

	OFICINA_DE_PARTES ("OFICINA DE PARTES", 1),
	JEFE_DE_DIVISION ("JEFE DE DIVISION", 2),
    PROFESIONAL ("PROFESIONAL", 3),
    SUPERINTENDENTE("SUPERINTENDENTE(A)", 4),
    SECRETARIA("SECRETARIA", 5),
    PROFESIONAL_TI("PROFESIONAL TI", 6),
	ANALISTA_UGES("ANALISTA UGES", 7),
	COORDINADOR("COORDINADOR", 8),
	COORDINADOR_CREAR_EXP("COORDINADOR_CREAR_EXP", 9);
	
    private final String nombreRol;
    private final long idRol;    
    
	private RolType(String nombreRol, long idRol) {
		this.nombreRol = nombreRol;
		this.idRol = idRol;
	}
	public String getNombreRol() {
		return nombreRol;
	}
	public long getIdRol() {
		return idRol;
	}
		
}
