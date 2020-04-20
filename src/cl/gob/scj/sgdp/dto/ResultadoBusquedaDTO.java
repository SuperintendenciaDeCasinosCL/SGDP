package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultadoBusquedaDTO implements Serializable {

	private List<ElementoResultadoBusquedaDTO> elementosResultadoBusquedaDTO;
	private String total;
	private String totalArchivos;
	private String totalCarpetas;
	
	private Map<String, Integer> facetProcesos;
	private Map<String, Integer> facetSubProcesos;
	private Map<String, Integer> facetMaterias;
	private Map<String, Integer> facetAutores;
	private Map<String, Integer> facetFechasCreacion;
	private Map<String, Integer> facetCreadores;	
	
	public ResultadoBusquedaDTO() {
		super();
		facetProcesos = new HashMap<String, Integer>();
		facetMaterias = new HashMap<String, Integer>();
		facetAutores = new HashMap<String, Integer>();
		facetFechasCreacion = new HashMap<String, Integer>();	
		facetCreadores = new HashMap<String, Integer>();
		facetSubProcesos = new HashMap<String, Integer>();
	}
	
	public List<ElementoResultadoBusquedaDTO> getElementosResultadoBusquedaDTO() {
		return elementosResultadoBusquedaDTO;
	}
	public void setElementosResultadoBusquedaDTO(
			List<ElementoResultadoBusquedaDTO> elementosResultadoBusquedaDTO) {
		this.elementosResultadoBusquedaDTO = elementosResultadoBusquedaDTO;
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
	public Map<String, Integer> getFacetProcesos() {
		return facetProcesos;
	}
	public void setFacetProcesos(Map<String, Integer> facetProcesos) {
		this.facetProcesos = facetProcesos;
	}
	public Map<String, Integer> getFacetMaterias() {
		return facetMaterias;
	}
	public void setFacetMaterias(Map<String, Integer> facetMaterias) {
		this.facetMaterias = facetMaterias;
	}
	public Map<String, Integer> getFacetAutores() {
		return facetAutores;
	}
	public void setFacetAutores(Map<String, Integer> facetAutores) {
		this.facetAutores = facetAutores;
	}
	public Map<String, Integer> getFacetFechasCreacion() {
		return facetFechasCreacion;
	}
	public void setFacetFechasCreacion(Map<String, Integer> facetFechasCreacion) {
		this.facetFechasCreacion = facetFechasCreacion;
	}	
	public Map<String, Integer> getFacetCreadores() {
		return facetCreadores;
	}
	public void setFacetCreadores(Map<String, Integer> facetCreadores) {
		this.facetCreadores = facetCreadores;
	}		
	public Map<String, Integer> getFacetSubProcesos() {
		return facetSubProcesos;
	}
	public void setFacetSubProcesos(Map<String, Integer> facetSubProcesos) {
		this.facetSubProcesos = facetSubProcesos;
	}
	@Override
	public String toString() {
		return "ResultadoBusquedaDTO [elementosResultadoBusquedaDTO="
				+ elementosResultadoBusquedaDTO + ", total=" + total
				+ ", totalArchivos=" + totalArchivos + ", totalCarpetas="
				+ totalCarpetas + ", facetProcesos=" + facetProcesos
				+ ", facetMaterias=" + facetMaterias + ", facetAutores="
				+ facetAutores + ", facetFechasCreacion=" + facetFechasCreacion
				+ ", facetCreadores=" + facetCreadores 
				+ ", facetSubProcesos=" + facetSubProcesos 
				+ "]";
	}		
}
