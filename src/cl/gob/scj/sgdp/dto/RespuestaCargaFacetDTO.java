package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.Map;

public class RespuestaCargaFacetDTO implements Serializable {
	
	Map<String, Integer> facetProcesos;
	Map<String, Integer> facetSubProcesos;
	Map<String, Integer> facetMaterias;
	Map<String, Integer> facetAutores;
	Map<String, Integer> facetCreadores;
	
	public Map<String, Integer> getFacetProcesos() {
		return facetProcesos;
	}
	public void setFacetProcesos(Map<String, Integer> facetProcesos) {
		this.facetProcesos = facetProcesos;
	}
	public Map<String, Integer> getFacetSubProcesos() {
		return facetSubProcesos;
	}
	public void setFacetSubProcesos(Map<String, Integer> facetSubProcesos) {
		this.facetSubProcesos = facetSubProcesos;
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
	public Map<String, Integer> getFacetCreadores() {
		return facetCreadores;
	}
	public void setFacetCreadores(Map<String, Integer> facetCreadores) {
		this.facetCreadores = facetCreadores;
	}
	@Override
	public String toString() {
		return "RespuestaCargaFacetDTO [facetProcesos=" + facetProcesos + ", facetSubProcesos=" + facetSubProcesos
				+ ", facetMaterias=" + facetMaterias + ", facetAutores=" + facetAutores + ", facetCreadores="
				+ facetCreadores + "]";
	}	
}
