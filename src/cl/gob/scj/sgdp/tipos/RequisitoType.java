package cl.gob.scj.sgdp.tipos;

public enum RequisitoType {
	
	RDS ("RDS", 1),
	SNC ("SNC", 2);
	
	private final String nombreDeTipoDeRequisito;
	private final long idDeTipoDeRequisito;
	
	private RequisitoType(String nombreDeTipoDeRequisito, int idDeTipoDeRequisito) {
		this.nombreDeTipoDeRequisito = nombreDeTipoDeRequisito;
		this.idDeTipoDeRequisito = idDeTipoDeRequisito;
	}

	public String getNombreDeTipoDeRequisito() {
		return nombreDeTipoDeRequisito;
	}

	public long getIdDeTipoDeRequisito() {
		return idDeTipoDeRequisito;
	}	

}
