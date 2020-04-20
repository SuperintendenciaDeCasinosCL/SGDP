package cl.gob.scj.sgdp.dto.rest;

import java.util.List;

public class ObtenerTodasLasInstDeTareasAsigPorIdExpResponse {
	
	List<InstanciaDeTareaDTORest> listaInstanciaDeTareaDTORest;	
	private String mensaje;
	private String detalleRespuesta;
	
	public List<InstanciaDeTareaDTORest> getListaInstanciaDeTareaDTORest() {
		return listaInstanciaDeTareaDTORest;
	}
	public void setListaInstanciaDeTareaDTORest(List<InstanciaDeTareaDTORest> listaInstanciaDeTareaDTORest) {
		this.listaInstanciaDeTareaDTORest = listaInstanciaDeTareaDTORest;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getDetalleRespuesta() {
		return detalleRespuesta;
	}
	public void setDetalleRespuesta(String detalleRespuesta) {
		this.detalleRespuesta = detalleRespuesta;
	}
	@Override
	public String toString() {
		return "ObtenerTodasLasInstDeTareasAsigPorIdExpResponse [listaInstanciaDeTareaDTORest="
				+ listaInstanciaDeTareaDTORest + ", mensaje=" + mensaje + ", detalleRespuesta=" + detalleRespuesta
				+ "]";
	}
	
}
