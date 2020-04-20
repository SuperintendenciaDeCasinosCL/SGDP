package cl.gob.scj.sgdp.dto.rest;

public class GeneraRegistroDocumentoResponseRest {

	protected int codDivisionUnidadSGDP;
    protected String codTipoDoc;
    protected Boolean confidencial;
    protected String expDoc;
    protected String fechaDocS;
    protected String fechaTramitacionS;
    protected String nombreTipoDocumento;
    protected Long numeroDoc;
    protected Long pkRegistroDoc;
    protected String usuario;
    protected String estado;
    protected String glosa;
    
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
	public Boolean getConfidencial() {
		return confidencial;
	}
	public void setConfidencial(Boolean confidencial) {
		this.confidencial = confidencial;
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
	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}
	public void setNombreTipoDocumento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
	}
	public Long getNumeroDoc() {
		return numeroDoc;
	}
	public void setNumeroDoc(Long numeroDoc) {
		this.numeroDoc = numeroDoc;
	}
	public Long getPkRegistroDoc() {
		return pkRegistroDoc;
	}
	public void setPkRegistroDoc(Long pkRegistroDoc) {
		this.pkRegistroDoc = pkRegistroDoc;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	@Override
	public String toString() {
		return "GeneraRegistroDocumentoResponseRest [codDivisionUnidadSGDP=" + codDivisionUnidadSGDP + ", codTipoDoc="
				+ codTipoDoc + ", confidencial=" + confidencial + ", expDoc=" + expDoc + ", fechaDocS=" + fechaDocS
				+ ", fechaTramitacionS=" + fechaTramitacionS + ", nombreTipoDocumento=" + nombreTipoDocumento
				+ ", numeroDoc=" + numeroDoc + ", pkRegistroDoc=" + pkRegistroDoc + ", usuario=" + usuario + ", estado="
				+ estado + ", glosa=" + glosa + "]";
	}
}
