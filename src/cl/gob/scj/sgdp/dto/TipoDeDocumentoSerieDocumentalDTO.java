package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TipoDeDocumentoSerieDocumentalDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long idTipoDeDocumento;
	private String nombreDeTipoDeDocumento;
	private String codigoTipoDocumonto;
	private String serieDocumental;
	private Boolean transferibleAarchivo;
	private int aniosDeRetencion;
	private String nombreEnDiagrama;
	private List<Long> ids = new ArrayList<>();
	
	public TipoDeDocumentoSerieDocumentalDTO() {
	}

	public TipoDeDocumentoSerieDocumentalDTO(long idTipoDeDocumento,
			String nombreDeTipoDeDocumento) {
		super();
		this.nombreDeTipoDeDocumento = nombreDeTipoDeDocumento;
	}
	
	public String getNombreDeTipoDeDocumento() {
		return nombreDeTipoDeDocumento;
	}
	
	public void setNombreDeTipoDeDocumento(String nombreDeTipoDeDocumento) {
		this.nombreDeTipoDeDocumento = nombreDeTipoDeDocumento;
	}	

	public String getCodigoTipoDocumonto() {
		return codigoTipoDocumonto;
	}

	public void setCodigoTipoDocumonto(String codigoTipoDocumonto) {
		this.codigoTipoDocumonto = codigoTipoDocumonto;
	}

	public String getNombreEnDiagrama() {
		return nombreEnDiagrama;
	}

	public void setNombreEnDiagrama(String nombreEnDiagrama) {
		this.nombreEnDiagrama = nombreEnDiagrama;
	}

	public String getSerieDocumental() {
		return serieDocumental;
	}

	public void setSerieDocumental(String serieDocumental) {
		this.serieDocumental = serieDocumental;
	}

	public Boolean getTransferibleAarchivo() {
		return transferibleAarchivo;
	}

	public void setTransferibleAarchivo(Boolean transferibleAarchivo) {
		this.transferibleAarchivo = transferibleAarchivo;
	}

	public int getAniosDeRetencion() {
		return aniosDeRetencion;
	}

	public void setAniosDeRetencion(int aniosDeRetencion) {
		this.aniosDeRetencion = aniosDeRetencion;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public long getIdTipoDeDocumento() {
		return idTipoDeDocumento;
	}

	public void setIdTipoDeDocumento(long idTipoDeDocumento) {
		this.idTipoDeDocumento = idTipoDeDocumento;
	}

}
