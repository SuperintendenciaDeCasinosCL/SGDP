package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

public class EnviarMailDTO implements Serializable  {

	private UsuarioEmailDTO usuario;
	private String subject;
	private String body;
	private List<String> listaUsuarioPara;
	private List<String> listaUsuarioCC;
   
	public EnviarMailDTO() {
	}

	public UsuarioEmailDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEmailDTO usuario) {
		this.usuario = usuario;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<String> getListaUsuarioPara() {
		return listaUsuarioPara;
	}

	public void setListaUsuarioPara(List<String> listaUsuarioPara) {
		this.listaUsuarioPara = listaUsuarioPara;
	}

	public List<String> getListaUsuarioCC() {
		return listaUsuarioCC;
	}

	public void setListaUsuarioCC(List<String> listaUsuarioCC) {
		this.listaUsuarioCC = listaUsuarioCC;
	}

}
