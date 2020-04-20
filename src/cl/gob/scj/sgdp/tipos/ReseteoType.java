package cl.gob.scj.sgdp.tipos;

public enum ReseteoType {

	DOCUMENTO ("D"),
	TAREA ("T");
	
	private final String nombreReseteo;

	public String getNombreReseteo() {
		return nombreReseteo;
	}
	
	private ReseteoType(String nombreReseteo) {
		this.nombreReseteo = nombreReseteo;
	}
	
}
