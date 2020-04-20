package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class EtapaDeInstanciaDeProcesoDTO implements Serializable {

	private long idEtapa;
	private String nombreEtapa;
	private long codigoEstadoDeTarea;
	
	public long getIdEtapa() {
		return idEtapa;
	}
	public void setIdEtapa(long idEtapa) {
		this.idEtapa = idEtapa;
	}
	public String getNombreEtapa() {
		return nombreEtapa;
	}
	public void setNombreEtapa(String nombreEtapa) {
		this.nombreEtapa = nombreEtapa;
	}
	public long getCodigoEstadoDeTarea() {
		return codigoEstadoDeTarea;
	}
	public void setCodigoEstadoDeTarea(long codigoEstadoDeTarea) {
		this.codigoEstadoDeTarea = codigoEstadoDeTarea;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (codigoEstadoDeTarea ^ (codigoEstadoDeTarea >>> 32));
		result = prime * result + (int) (idEtapa ^ (idEtapa >>> 32));
		result = prime * result + ((nombreEtapa == null) ? 0 : nombreEtapa.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EtapaDeInstanciaDeProcesoDTO other = (EtapaDeInstanciaDeProcesoDTO) obj;
		if (codigoEstadoDeTarea != other.codigoEstadoDeTarea)
			return false;
		if (idEtapa != other.idEtapa)
			return false;
		if (nombreEtapa == null) {
			if (other.nombreEtapa != null)
				return false;
		} else if (!nombreEtapa.equals(other.nombreEtapa))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "EtapaDeInstanciaDeProcesoDTO [idEtapa=" + idEtapa + ", nombreEtapa=" + nombreEtapa
				+ ", codigoEstadoDeTarea=" + codigoEstadoDeTarea + "]";
	}
	
}
