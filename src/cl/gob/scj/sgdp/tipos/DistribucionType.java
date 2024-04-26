package cl.gob.scj.sgdp.tipos;

public enum DistribucionType {

	DIST_CORREO("distCorreo"),
	DIST_API_DOC("apiDoc"),
	DIST_API_OFICINA_VIRTUAL("apiOficinaVirtual");
	
	private final String nombreDistribucion;
	
	private DistribucionType(String nombreDistribucion) {
		this.nombreDistribucion = nombreDistribucion;
	}

	public String getNombreDistribucion() {
		return nombreDistribucion;
	}

	
}
