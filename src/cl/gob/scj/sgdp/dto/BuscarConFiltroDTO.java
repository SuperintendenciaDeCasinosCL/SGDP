package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class BuscarConFiltroDTO implements Serializable  {

	private String nombreFiltro;
	private String tipoFiltro;
	private String flagTipoBusqueda;

	public BuscarConFiltroDTO() {
		super();
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

	public String getFlagTipoBusqueda() {
		return flagTipoBusqueda;
	}

	public void setFlagTipoBusqueda(String flagTipoBusqueda) {
		this.flagTipoBusqueda = flagTipoBusqueda;
	}

}
