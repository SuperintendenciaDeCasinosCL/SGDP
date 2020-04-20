package cl.gob.scj.sgdp.tipos;

public enum SubeArchivoTareaType {
	
	PRIMERA ("PRIMERA", 1),
    ASIGNADAS ("ASIGNADAS", 2),
    ASIGNADAS_EN_ESPERA ("ASIGNADAS_EN_ESPERA", 3),
    EN_ESPERA ("EN_ESPERA", 4);
	
	private final String nombreTipoTareaSubeArch;
	private final long idTipoTareaSubeArch;
	
	private SubeArchivoTareaType(String nombreTipoTareaSubeArch, long idTipoTareaSubeArch) {
		this.nombreTipoTareaSubeArch = nombreTipoTareaSubeArch;
		this.idTipoTareaSubeArch = idTipoTareaSubeArch;
	}

	public String getNombreTipoTareaSubeArch() {
		return nombreTipoTareaSubeArch;
	}

	public long getIdTipoTareaSubeArch() {
		return idTipoTareaSubeArch;
	}
	
}
