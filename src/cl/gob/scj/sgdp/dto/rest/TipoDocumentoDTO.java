package cl.gob.scj.sgdp.dto.rest;

public class TipoDocumentoDTO extends RespuestaDTO {

	private Long pkTipoDoc;
	private String nombreTipoDoc;
	private String nombreCompletoTipoDoc;

	private Integer idTipoDocDigital;

	
	public Long getPkTipoDoc() {
		return pkTipoDoc;
	}
	public void setPkTipoDoc(Long pkTipoDoc) {
		this.pkTipoDoc = pkTipoDoc;
	}
	public String getNombreTipoDoc() {
		return nombreTipoDoc;
	}
	public void setNombreTipoDoc(String nombreTipoDoc) {
		this.nombreTipoDoc = nombreTipoDoc;
	}		
	public String getNombreCompletoTipoDoc() {
		return nombreCompletoTipoDoc;
	}
	public void setNombreCompletoTipoDoc(String nombreCompletoTipoDoc) {
		this.nombreCompletoTipoDoc = nombreCompletoTipoDoc;
	}	
	
	
	
	public Integer getIdTipoDocDigital() {
		return idTipoDocDigital;
	}
	public void setIdTipoDocDigital(Integer idTipoDocDigital) {
		this.idTipoDocDigital = idTipoDocDigital;
	}
	@Override
	public String toString() {

		return "TipoDocumentoDTO [pkTipoDoc=" + pkTipoDoc + ", nombreTipoDoc=" + nombreTipoDoc
				+ ", nombreCompletoTipoDoc=" + nombreCompletoTipoDoc + ", idTipoDocDigital=" + idTipoDocDigital + "]";

	}
	
	
	
	
}
