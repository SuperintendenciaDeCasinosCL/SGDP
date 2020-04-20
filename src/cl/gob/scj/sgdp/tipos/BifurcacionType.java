package cl.gob.scj.sgdp.tipos;

public enum BifurcacionType {

	AND("AND", 1), OR("OR", 2);
	
	private final String nombreTipoDeBifurcacion;
	private final int codigoTipoDeBifurcacion;
	
	private BifurcacionType(String nombreTipoDeBifurcacion, int codigoTipoDeBifurcacion) {
		this.nombreTipoDeBifurcacion = nombreTipoDeBifurcacion;
		this.codigoTipoDeBifurcacion = codigoTipoDeBifurcacion;
	}

	public String getNombreTipoDeBifurcacion() {
		return nombreTipoDeBifurcacion;
	}

	public int getCodigoTipoDeBifurcacion() {
		return codigoTipoDeBifurcacion;
	}
	
}
