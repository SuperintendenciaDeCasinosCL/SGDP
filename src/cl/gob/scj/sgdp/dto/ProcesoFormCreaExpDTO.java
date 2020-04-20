package cl.gob.scj.sgdp.dto;

import java.util.Date;

public class ProcesoFormCreaExpDTO extends RespuestaDTO {

	private Long idProcesoFormCreaExp;
	private String codigoProceso;
	private Date fecha;
	private String idUsuario;
	private String nombreProceso;
	private Long idProceso;
	
	public Long getIdProcesoFormCreaExp() {
		return idProcesoFormCreaExp;
	}
	public void setIdProcesoFormCreaExp(Long idProcesoFormCreaExp) {
		this.idProcesoFormCreaExp = idProcesoFormCreaExp;
	}
	public String getCodigoProceso() {
		return codigoProceso;
	}
	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}	
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombreProceso() {
		return nombreProceso;
	}
	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}	
	public Long getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}
	@Override
	public String toString() {
		return "ProcesoFormCreaExpDTO [idProcesoFormCreaExp=" + idProcesoFormCreaExp 
				+ ", codigoProceso=" + codigoProceso 
				+ ", fecha=" + fecha 
				+ ", idUsuario=" + idUsuario
				+ ", nombreProceso=" + nombreProceso
				+ ", idProceso=" + idProceso 
				+ "]";
	}	
}
