package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class CargaFacetDTO implements Serializable {
	
	private String palabraClave;
	private String fechaInicio;
	private String fechaFin;	
	private String tipoObjeto;
	private String nombreTipoDocumento;
	private String nombreSubprocesoVigente;
	private String flagTipoBusqueda;
	private String nombreFiltro;
	private String tipoFiltro;
	private String tipoDocumentoOficial;
	private String filtraPorConfidencialidad;
	
	public String getPalabraClave() {
		return palabraClave;
	}
	
	public void setPalabraClave(String palabraClave) throws UnsupportedEncodingException {
		this.palabraClave = palabraClave;		
	}
	
	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}	
	public void setNombreTipoDocumento(String nombreTipoDocumento)  {
		this.nombreTipoDocumento = nombreTipoDocumento;			
	}
	
	
	public String getNombreSubprocesoVigente() {
		return nombreSubprocesoVigente;
	}
	
	public void setNombreSubprocesoVigente(String nombreSubprocesoVigente)  {		
		this.nombreSubprocesoVigente = nombreSubprocesoVigente;					
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getTipoObjeto() {
		return tipoObjeto;
	}

	public void setTipoObjeto(String tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	public String getFlagTipoBusqueda() {
		return flagTipoBusqueda;
	}

	public void setFlagTipoBusqueda(String flagTipoBusqueda) {
		this.flagTipoBusqueda = flagTipoBusqueda;
	}

	public String getNombreFiltro() {
		return nombreFiltro;
	}

	public void setNombreFiltro(String nombreFiltro) throws UnsupportedEncodingException {
		if (nombreFiltro != null) {
			this.nombreFiltro = URLDecoder.decode(nombreFiltro, "UTF-8");
		} else {
			this.nombreFiltro = nombreFiltro;
		}
	}

	public String getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(String tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	
	public String getTipoDocumentoOficial() {
		return tipoDocumentoOficial;
	}

	public void setTipoDocumentoOficial(String tipoDocumentoOficial) {
		this.tipoDocumentoOficial = tipoDocumentoOficial;
	}
	

	public String getFiltraPorConfidencialidad() {
		return filtraPorConfidencialidad;
	}

	public void setFiltraPorConfidencialidad(String filtraPorConfidencialidad) {
		this.filtraPorConfidencialidad = filtraPorConfidencialidad;
	}

	@Override
	public String toString() {
		return "CargaFacetDTO [palabraClave=" + palabraClave + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", tipoObjeto=" + tipoObjeto + ", nombreTipoDocumento=" + nombreTipoDocumento
				+ ", nombreSubprocesoVigente=" + nombreSubprocesoVigente + ", flagTipoBusqueda=" + flagTipoBusqueda
				+ ", nombreFiltro=" + nombreFiltro 
				+ ", tipoFiltro=" + tipoFiltro 
				+ ", tipoDocumentoOficial="	+ tipoDocumentoOficial 
				+ ", filtraPorConfidencialidad=" + filtraPorConfidencialidad
				+ "]";
	}

}
