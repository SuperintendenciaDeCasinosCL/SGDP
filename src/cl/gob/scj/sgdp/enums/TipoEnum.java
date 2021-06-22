package cl.gob.scj.sgdp.enums;

public enum TipoEnum {
	EXPEDIENTE(1L, "1"), 
	DOCUMENTO(2L, "2");

	private Long id;
	private String idString;

	TipoEnum(Long id, String idString) {
		this.id = id;
		this.idString = idString;
	}

	public Long getId() {
		return this.id;
	}
	
	public String getIdString() {
		return this.idString;
	}
}
