package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferenciaArchivoNacionalDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3705895674256633292L;
	@JsonProperty(value = "codigoAcuerdoTransferencia")
	private String codigoAcuerdoTransferencia;
	@JsonProperty(value = "codigoSerie")
	private String codigoSerie;
	@JsonProperty(value = "idInstitucion")
	private int idInstitucion;
	@JsonProperty(value = "listaDocumentos")
	private List<DocumentoDTO> listaDocumentos;
	@JsonProperty(value = "listaExpedientes")
	private List<ExpedienteArchNacDTO> listaExpedientes;
	@JsonProperty(value = "nombreAcuerdoTransferencia")
	private String nombreAcuerdoTransferencia;
	@JsonProperty(value = "nombreInstitucion")
	private String nombreInstitucion;
	@JsonProperty(value = "nombreSerie")
	private String nombreSerie;

	public String getCodigoAcuerdoTransferencia() {
		return codigoAcuerdoTransferencia;
	}

	public void setCodigoAcuerdoTransferencia(String codigoAcuerdoTransferencia) {
		this.codigoAcuerdoTransferencia = codigoAcuerdoTransferencia;
	}

	public String getCodigoSerie() {
		return codigoSerie;
	}

	public void setCodigoSerie(String codigoSerie) {
		this.codigoSerie = codigoSerie;
	}

	public int getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(int idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public List<DocumentoDTO> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<DocumentoDTO> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public List<ExpedienteArchNacDTO> getListaExpedientes() {
		return listaExpedientes;
	}

	public void setListaExpedientes(List<ExpedienteArchNacDTO> listaExpedientes) {
		this.listaExpedientes = listaExpedientes;
	}

	public String getNombreAcuerdoTransferencia() {
		return nombreAcuerdoTransferencia;
	}

	public void setNombreAcuerdoTransferencia(String nombreAcuerdoTransferencia) {
		this.nombreAcuerdoTransferencia = nombreAcuerdoTransferencia;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public String getNombreSerie() {
		return nombreSerie;
	}

	public void setNombreSerie(String nombreSerie) {
		this.nombreSerie = nombreSerie;
	}

	@Override
	public String toString() {
		return "TransferenciaArchivoNacionalDTO [codigoAcuerdoTransferencia=" + codigoAcuerdoTransferencia
				+ ", codigoSerie=" + codigoSerie + ", idInstitucion=" + idInstitucion + ", listaDocumentos="
				+ listaDocumentos + ", listaExpedientes=" + listaExpedientes + ", nombreAcuerdoTransferencia="
				+ nombreAcuerdoTransferencia + ", nombreInstitucion=" + nombreInstitucion + ", nombreSerie="
				+ nombreSerie + "]";
	}

}
