package cl.gob.scj.sgdp.dto.rest;

public class BorraRegistroDocumentoRequestRest {

	protected String codTipoDoc;
    protected String expDoc;
    protected String mensajeException;
    protected String motivoAnulacion;
    protected Long numeroDoc;
    private String user;
    private String pass;
    
	public String getCodTipoDoc() {
		return codTipoDoc;
	}
	public void setCodTipoDoc(String codTipoDoc) {
		this.codTipoDoc = codTipoDoc;
	}
	public String getExpDoc() {
		return expDoc;
	}
	public void setExpDoc(String expDoc) {
		this.expDoc = expDoc;
	}
	public String getMensajeException() {
		return mensajeException;
	}
	public void setMensajeException(String mensajeException) {
		this.mensajeException = mensajeException;
	}
	public String getMotivoAnulacion() {
		return motivoAnulacion;
	}
	public void setMotivoAnulacion(String motivoAnulacion) {
		this.motivoAnulacion = motivoAnulacion;
	}
	public Long getNumeroDoc() {
		return numeroDoc;
	}
	public void setNumeroDoc(Long numeroDoc) {
		this.numeroDoc = numeroDoc;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	@Override
	public String toString() {
		return "BorraRegistroDocumentoRequestRest [codTipoDoc=" + codTipoDoc + ", expDoc=" + expDoc
				+ ", mensajeException=" + mensajeException + ", motivoAnulacion=" + motivoAnulacion + ", numeroDoc="
				+ numeroDoc + ", user=" + user + ", pass=" + pass + "]";
	}    
    
}
