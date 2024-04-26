package cl.gob.scj.sgdp.dto;

public class HabilitaFuncionalidadDTO {
	
	private long idHabilitaFuncionalidad;
	private String nombreFuncionalidad;
	private boolean habilitada;
	
	public long getIdHabilitaFuncionalidad() {
		return idHabilitaFuncionalidad;
	}
	public void setIdHabilitaFuncionalidad(long idHabilitaFuncionalidad) {
		this.idHabilitaFuncionalidad = idHabilitaFuncionalidad;
	}
	public String getNombreFuncionalidad() {
		return nombreFuncionalidad;
	}
	public void setNombreFuncionalidad(String nombreFuncionalidad) {
		this.nombreFuncionalidad = nombreFuncionalidad;
	}
	public boolean isHabilitada() {
		return habilitada;
	}
	public void setHabilitada(boolean habilitada) {
		this.habilitada = habilitada;
	}
	
	@Override
	public String toString() {
		return "HabilitaFuncionalidadDTO [idHabilitaFuncionalidad=" + idHabilitaFuncionalidad + ", nombreFuncionalidad="
				+ nombreFuncionalidad + ", habilitada=" + habilitada + "]";
	}

}
