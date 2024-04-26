package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class SerieDocumentalDTO implements Serializable {

    private static final long serialVersionUID = -8986206406847995159L;

    private Long idSerieDocumental;
    private Long idFuncion;
    private String nombreFuncion;
    private Long idSubFuncion;
    private String nombreSubFuncion;
    private Long idProceso;
    private String codigoProceso;
    private String nombreProceso;
    private String serieDocumental;
    private String subSerieDocumental;
	
    public SerieDocumentalDTO() {
    	super();
	}
    
    

	public SerieDocumentalDTO(Long idSerieDocumental, Long idFuncion, String nombreFuncion, Long idSubFuncion,
			String nombreSubFuncion, Long idProceso, String nombreProceso, String serieDocumental,
			String subSerieDocumental, String codigoProceso) {
		super();
		this.idSerieDocumental = idSerieDocumental;
		this.idFuncion = idFuncion;
		this.nombreFuncion = nombreFuncion;
		this.idSubFuncion = idSubFuncion;
		this.nombreSubFuncion = nombreSubFuncion;
		this.idProceso = idProceso;
		this.nombreProceso = nombreProceso;
		this.serieDocumental = serieDocumental;
		this.subSerieDocumental = subSerieDocumental;
		this.codigoProceso = codigoProceso;
	}



	public Long getIdSerieDocumental() {
		return idSerieDocumental;
	}

	public void setIdSerieDocumental(Long idSerieDocumental) {
		this.idSerieDocumental = idSerieDocumental;
	}

	public Long getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(Long idFuncion) {
		this.idFuncion = idFuncion;
	}

	public String getNombreFuncion() {
		return nombreFuncion;
	}

	public void setNombreFuncion(String nombreFuncion) {
		this.nombreFuncion = nombreFuncion;
	}

	public Long getIdSubFuncion() {
		return idSubFuncion;
	}

	public void setIdSubFuncion(Long idSubFuncion) {
		this.idSubFuncion = idSubFuncion;
	}

	public String getNombreSubFuncion() {
		return nombreSubFuncion;
	}

	public void setNombreSubFuncion(String nombreSubFuncion) {
		this.nombreSubFuncion = nombreSubFuncion;
	}

	public Long getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public String getSerieDocumental() {
		return serieDocumental;
	}

	public void setSerieDocumental(String serieDocumental) {
		this.serieDocumental = serieDocumental;
	}

	public String getSubSerieDocumental() {
		return subSerieDocumental;
	}

	public void setSubSerieDocumental(String subSerieDocumental) {
		this.subSerieDocumental = subSerieDocumental;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getCodigoProceso() {
		return codigoProceso;
	}



	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}
    
    

}