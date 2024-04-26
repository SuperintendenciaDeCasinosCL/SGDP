package cl.gob.scj.sgdp.tipos;

public enum TipoParametroTareaType {

	RADIO ("radio", 1),
	COMENTARIO ("comentario", 2),
	ABRE_SELECT ("abre_select", 3),
	CIERRA_SELECT ("cierra_select", 4),
	OPTION ("option", 5),
	REQUISITO_DE_SATISFACCION ("requisito_de_satisfaccion", 6),
	SALIDA_NO_CONFORME ("salida_no_conforme", 7)	
	;
	
	private final String nombreTipoDeRequisito;
	private final long idTipoParametroTarea;
	
	private TipoParametroTareaType(String nombreTipoDeRequisito, int idTipoParametroTarea) {
		this.nombreTipoDeRequisito = nombreTipoDeRequisito;
		this.idTipoParametroTarea = idTipoParametroTarea;
	}

	public String getNombreTipoDeRequisito() {
		return nombreTipoDeRequisito;
	}

	public long getIdTipoParametroTarea() {
		return idTipoParametroTarea;
	}

}