package cl.gob.scj.sgdp.dto.rest;

import java.io.Serializable;

public class AgregaORemueveTagDeObjetoRequest implements Serializable {
	
	private String idObjeto;
	private String tag;
	private String accion;
	private String idUsuario;
	private String clave;
	
	public String getIdObjeto() {
		return idObjeto;
	}
	public void setIdObjeto(String idObjeto) {
		this.idObjeto = idObjeto;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}	
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	@Override
	public String toString() {
		return "AgregaORemueveTagDeObjetoRequest [idObjeto=" + idObjeto + ", tag=" + tag + ", accion=" + accion
				+ ", idUsuario=" + idUsuario + "]";
	}

}
