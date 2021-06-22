package cl.gob.scj.sgdp.enums;

public enum ParametroArchivoNacionalEnum {
	USER_API("USER_API"),
	PASS_API("PASS_API"),
	USER_SFTP("USER_SFTP"),
	PASS_SFTP("PASS_SFTP"),
	CODIGO_INSTITUCION("CODIGO_INSTITUCION"), 
	NOMBRE_INSTITUCION("NOMBRE_INSTITUCION");

	private String codigo;

	ParametroArchivoNacionalEnum(String idString) {
		this.codigo = idString;
	}

	
	
	public String getCodigo() {
		return this.codigo;
	}
}
