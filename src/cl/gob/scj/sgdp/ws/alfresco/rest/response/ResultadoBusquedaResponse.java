package cl.gob.scj.sgdp.ws.alfresco.rest.response;

import java.io.Serializable;
import java.util.List;

public class ResultadoBusquedaResponse implements Serializable {	

	private List<ElementoBuscar> listaDeArchivos;
	private String total;
	private String totalArchivos;
	private String totalCarpetas;	
	private String cantidadRegistrosPorPagina;	
	private String numeroPagina;	
	
	public List<ElementoBuscar> getListaDeArchivos() {
		return listaDeArchivos;
	}
	public void setListaDeArchivos(List<ElementoBuscar> listaDeArchivos) {
		this.listaDeArchivos = listaDeArchivos;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTotalArchivos() {
		return totalArchivos;
	}
	public void setTotalArchivos(String totalArchivos) {
		this.totalArchivos = totalArchivos;
	}
	public String getTotalCarpetas() {
		return totalCarpetas;
	}
	public void setTotalCarpetas(String totalCarpetas) {
		this.totalCarpetas = totalCarpetas;
	}
	public String getCantidadRegistrosPorPagina() {
		return cantidadRegistrosPorPagina;
	}
	public void setCantidadRegistrosPorPagina(String cantidadRegistrosPorPagina) {
		this.cantidadRegistrosPorPagina = cantidadRegistrosPorPagina;
	}
	public String getNumeroPagina() {
		return numeroPagina;
	}
	public void setNumeroPagina(String numeroPagina) {
		this.numeroPagina = numeroPagina;
	}
	@Override
	public String toString() {
		return "ResultadoBusquedaResponse [listaDeArchivos=" + listaDeArchivos + ", total=" + total + ", totalArchivos="
				+ totalArchivos + ", totalCarpetas=" + totalCarpetas + ", cantidadRegistrosPorPagina="
				+ cantidadRegistrosPorPagina + ", numeroPagina=" + numeroPagina + "]";
	}	
	
	
	
}
