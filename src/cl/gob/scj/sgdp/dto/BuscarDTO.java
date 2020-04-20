package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

public class BuscarDTO implements Serializable {
	
	private String palabraClave;
	private String fechaInicio;
	private String fechaFin;
	private List<String> tipoDeObjeto;
	private List<String> filtrosBusqueda;	
	private String tipoDeObjetoParaBuscar;
	private boolean soloEnMiBandejaDeSalida;
	private String tipoDocumentoOficial;
	private String nombreTipoDocumento;
	//private StringBuilder nombreTipoDocumentoB;
	private String nombreSubprocesoVigente;
	private String flagTipoBusqueda;
	private String nombreFiltro;
	private String tipoFiltro;
	private String filtraPorConfidencialidad; 
	private String flagExportaExcel;
	
	private String listaSubproceso;
	private String listaMateria;
	private String listaAutor;
	private String listaCreador;
		
	public String getPalabraClave() {
		return palabraClave;
	}
	public void setPalabraClave(String palabraClave) {	
		this.palabraClave = palabraClave;
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
	public List<String> getTipoDeObjeto() {
		return tipoDeObjeto;
	}
	public void setTipoDeObjeto(List<String> tipoDeObjeto) {
		this.tipoDeObjeto = tipoDeObjeto;
	}
	public List<String> getFiltrosBusqueda() {
		return filtrosBusqueda;
	}
	public void setFiltrosBusqueda(List<String> filtrosBusqueda) {
		this.filtrosBusqueda = filtrosBusqueda;
	}	
	public String getTipoDeObjetoParaBuscar() {
		return tipoDeObjetoParaBuscar;
	}
	public void setTipoDeObjetoParaBuscar(String tipoDeObjetoParaBuscar) {
		this.tipoDeObjetoParaBuscar = tipoDeObjetoParaBuscar;
	}	
	public boolean getSoloEnMiBandejaDeSalida() {
		return soloEnMiBandejaDeSalida;
	}
	public void setSoloEnMiBandejaDeSalida(boolean soloEnMiBandejaDeSalida) {
		this.soloEnMiBandejaDeSalida = soloEnMiBandejaDeSalida;
	}		
	public String getTipoDocumentoOficial() {
		return tipoDocumentoOficial;
	}	
	public void setTipoDocumentoOficial(String tipoDocumentoOficial) {
		this.tipoDocumentoOficial = tipoDocumentoOficial;
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
	public void setNombreSubprocesoVigente(String nombreSubprocesoVigente){						
		this.nombreSubprocesoVigente = nombreSubprocesoVigente;
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
	public String getFiltraPorConfidencialidad() {
		return filtraPorConfidencialidad;
	}
	public void setFiltraPorConfidencialidad(String filtraPorConfidencialidad) {
		this.filtraPorConfidencialidad = filtraPorConfidencialidad;
	}	
	public String getFlagExportaExcel() {
		return flagExportaExcel;
	}
	public void setFlagExportaExcel(String flagExportaExcel) {
		this.flagExportaExcel = flagExportaExcel;
	}
	public String getListaSubproceso() {
		return listaSubproceso;
	}
	public void setListaSubproceso(String listaSubproceso) {
		this.listaSubproceso = listaSubproceso;
	}
	public String getListaMateria() {
		return listaMateria;
	}
	public void setListaMateria(String listaMateria) {
		this.listaMateria = listaMateria;
	}
	public String getListaAutor() {
		return listaAutor;
	}
	public void setListaAutor(String listaAutor) {
		this.listaAutor = listaAutor;
	}
	public String getListaCreador() {
		return listaCreador;
	}
	public void setListaCreador(String listaCreador) {
		this.listaCreador = listaCreador;
	}	
	@Override
	public String toString() {
		return "BuscarDTO [palabraClave=" + palabraClave + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", tipoDeObjeto=" + tipoDeObjeto + ", filtrosBusqueda=" + filtrosBusqueda
				+ ", tipoDeObjetoParaBuscar=" + tipoDeObjetoParaBuscar + ", soloEnMiBandejaDeSalida="
				+ soloEnMiBandejaDeSalida + ", tipoDocumentoOficial=" + tipoDocumentoOficial + ", nombreTipoDocumento="
				+ nombreTipoDocumento + ", nombreSubprocesoVigente=" + nombreSubprocesoVigente + ", flagTipoBusqueda="
				+ flagTipoBusqueda + ", nombreFiltro=" + nombreFiltro + ", tipoFiltro=" + tipoFiltro
				+ ", filtraPorConfidencialidad=" + filtraPorConfidencialidad + ", flagExportaExcel=" + flagExportaExcel
				+ ", listaSubproceso=" + listaSubproceso + ", listaMateria=" + listaMateria + ", listaAutor="
				+ listaAutor + ", listaCreador=" + listaCreador 
				+ "]";
	}
	
}
