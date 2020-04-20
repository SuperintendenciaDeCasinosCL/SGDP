package cl.gob.scj.sgdp.tipos;

public enum NombreObjetoEnBusquedaType {

	DOCUMENTO ("Documento", 1),
    EXPEDIENTE ("Expediente", 2);
	
	private final String nombreNombreObjetoEnBusquedaType;
    private final int codigoNombreObjetoEnBusquedaType;
    
	private NombreObjetoEnBusquedaType(String nombreNombreObjetoEnBusquedaType,
			int codigoNombreObjetoEnBusquedaType) {
		this.nombreNombreObjetoEnBusquedaType = nombreNombreObjetoEnBusquedaType;
		this.codigoNombreObjetoEnBusquedaType = codigoNombreObjetoEnBusquedaType;
	}

	public String getNombreNombreObjetoEnBusquedaType() {
		return nombreNombreObjetoEnBusquedaType;
	}

	public int getCodigoNombreObjetoEnBusquedaType() {
		return codigoNombreObjetoEnBusquedaType;
	}
    
    
}
