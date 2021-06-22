package cl.gob.scj.sgdp.enums;

public enum EstadoProcesoEnum {
	NUEVO(1L, "NUEVO"), 
	ASIGNADO(2L, "ASIGNADO"),
	FINALIZADO(3L, "FINALIZADO"),
	ANULADO(4L, "ANULADO");

	private Long id;
	private String nombre;

	EstadoProcesoEnum(Long id, String idString) {
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
