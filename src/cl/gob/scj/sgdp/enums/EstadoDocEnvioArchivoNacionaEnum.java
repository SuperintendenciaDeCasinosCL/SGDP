package cl.gob.scj.sgdp.enums;

public enum EstadoDocEnvioArchivoNacionaEnum {
	NO_ENVIADO(0L, "No Enviado"), 
	ENVIADO(1L, "Enviado"),
	DESCARTADO(2L, "Descartado");

	private Long id;
	private String nombre;

	EstadoDocEnvioArchivoNacionaEnum(Long id, String idString) {
		this.id = id;
		this.nombre = idString;
	}

	public Long getId() {
		return this.id;
	}
	
	public String getNombre() {
		return this.nombre;
	}
}
