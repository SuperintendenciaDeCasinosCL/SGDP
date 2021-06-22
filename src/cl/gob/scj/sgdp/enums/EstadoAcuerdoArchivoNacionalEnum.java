package cl.gob.scj.sgdp.enums;

public enum EstadoAcuerdoArchivoNacionalEnum {
	APROBADO_PROPUESTA(3L, "3", "Aprobado Propuesta"),
	APROBADO_PRUEBA(5L, "5", "Aprobado Prueba"); 
//	APROBADO_TRANSFERENCIA(6L, "6","Aprobado Transferencia");

	private Long id;
	private String idStr;
	private String nombre;

	EstadoAcuerdoArchivoNacionalEnum(Long id, String idStr, String idString) {
		this.id = id;
		this.idStr = idStr;
		this.nombre = idString;
	}

	public Long getId() {
		return this.id;
	}

	public String getIdStr() {
		return this.idStr;
	}

	public String getNombre() {
		return this.nombre;
	}
}
