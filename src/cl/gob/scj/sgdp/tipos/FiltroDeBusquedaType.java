package cl.gob.scj.sgdp.tipos;

public enum FiltroDeBusquedaType {

	NOMBRE ("Nombre", 1),
    SUBPROCESO ("SubProceso", 2),
    MATERIA ("Materia", 3),
    AUTOR ("Autor", 4);
	
	private final String nombreFiltroDeBusquedaType;
    private final int codigoFiltroDeBusquedaType;
    
	private FiltroDeBusquedaType(String nombreFiltroDeBusquedaType,
			int codigoFiltroDeBusquedaType) {
		this.nombreFiltroDeBusquedaType = nombreFiltroDeBusquedaType;
		this.codigoFiltroDeBusquedaType = codigoFiltroDeBusquedaType;
	}

	public String getNombreFiltroDeBusquedaType() {
		return nombreFiltroDeBusquedaType;
	}

	public int getCodigoFiltroDeBusquedaType() {
		return codigoFiltroDeBusquedaType;
	}
    
	
}
