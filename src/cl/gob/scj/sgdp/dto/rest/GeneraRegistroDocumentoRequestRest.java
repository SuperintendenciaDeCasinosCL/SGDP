package cl.gob.scj.sgdp.dto.rest;

public class GeneraRegistroDocumentoRequestRest {

	protected int codDivisionUnidadSGDP;
    protected String codTipoDoc;
    protected String expDoc;
    protected String fechaDocS;
    protected String fechaTramitacionS;
    protected String usuario;    
    private String user;
    private String pass;
    
	public int getCodDivisionUnidadSGDP() {
		return codDivisionUnidadSGDP;
	}
	public void setCodDivisionUnidadSGDP(int codDivisionUnidadSGDP) {
		this.codDivisionUnidadSGDP = codDivisionUnidadSGDP;
	}
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
	public String getFechaDocS() {
		return fechaDocS;
	}
	public void setFechaDocS(String fechaDocS) {
		this.fechaDocS = fechaDocS;
	}
	public String getFechaTramitacionS() {
		return fechaTramitacionS;
	}
	public void setFechaTramitacionS(String fechaTramitacionS) {
		this.fechaTramitacionS = fechaTramitacionS;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
		return "GeneraRegistroDocumentoRequestRest [codDivisionUnidadSGDP=" + codDivisionUnidadSGDP + ", codTipoDoc="
				+ codTipoDoc + ", expDoc=" + expDoc + ", fechaDocS=" + fechaDocS + ", fechaTramitacionS="
				+ fechaTramitacionS + ", usuario=" + usuario + ", user=" + user + "]";
	}
	
}
