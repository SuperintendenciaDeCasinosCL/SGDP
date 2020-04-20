package cl.gob.scj.sgdp.tipos;

public enum AccionSolCreaExpType {

	CREA_SOLICITUD ("CREA_SOLICITUD", 1),
    RECHAZA_SOLICITUD ("RECHAZA_SOLICITUD", 2),
    CREA_EXPEDIENTE ("CREA_EXPEDIENTE", 3);
	
	private String nombreAccionSolCreaExp;
	private final long idAccionSolCreaExp;
	
	private AccionSolCreaExpType(String nombreAccionSolCreaExp, long idAccionSolCreaExp) {
		this.nombreAccionSolCreaExp = nombreAccionSolCreaExp;
		this.idAccionSolCreaExp = idAccionSolCreaExp;
	}

	public String getNombreAccionSolCreaExp() {
		return nombreAccionSolCreaExp;
	}

	public void setNombreAccionSolCreaExp(String nombreAccionSolCreaExp) {
		this.nombreAccionSolCreaExp = nombreAccionSolCreaExp;
	}

	public long getIdAccionSolCreaExp() {
		return idAccionSolCreaExp;
	}
	
}
