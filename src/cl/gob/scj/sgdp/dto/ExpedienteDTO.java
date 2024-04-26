package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.Date;

public class ExpedienteDTO implements Serializable {

	private static final long serialVersionUID = 472060425462063347L;
	
	private String idExpediente;
	private String nombreExpediente;
	private String creador;
	private String materia;
	private String idAutor;
	private Long idPerspectiva;
	private Long idMacroProceso;
	private Long idProceso;
	private String nombreAutor;
	private String nombrePerpectiva;
	private String nombreMacroProceso;
	private String nombreProceso;
	private long idInstanciaDeTareaSalida;
	private String esConfidencial;
	private Date fechaDeInicioInstProc;
	private String idUsuarioCrea;
	private String codigoProceso;
	
	private String idComplejidad;
	private String motivoComplejidad;
	
	public Long getIdMacroProceso() {
		return idMacroProceso;
	}

	public void setIdMacroProceso(Long idMacroProceso) {
		this.idMacroProceso = idMacroProceso;
	}

	public Long getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}

	public String getCreador() {
		return creador;
	}

	public void setCreador(String creador) {
		this.creador = creador;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(String idAutor) {
		this.idAutor = idAutor;
	}

	public String getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}

	public String getNombreExpediente() {
		return nombreExpediente;
	}

	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}

	public Long getIdPerspectiva() {
		return idPerspectiva;
	}

	public void setIdPerspectiva(Long idPerspectiva) {
		this.idPerspectiva = idPerspectiva;
	}

	public String getNombreAutor() {
		return nombreAutor;
	}

	public void setNombreAutor(String nombreAutor) {
		this.nombreAutor = nombreAutor;
	}
	
	public String getNombrePerpectiva() {
		return nombrePerpectiva;
	}

	public void setNombrePerpectiva(String nombrePerpectiva) {
		this.nombrePerpectiva = nombrePerpectiva;
	}
	
	public String getNombreMacroProceso() {
		return nombreMacroProceso;
	}

	public void setNombreMacroProceso(String nombreMacroProceso) {
		this.nombreMacroProceso = nombreMacroProceso;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}
	
	public long getIdInstanciaDeTareaSalida() {
		return idInstanciaDeTareaSalida;
	}

	public void setIdInstanciaDeTareaSalida(long idInstanciaDeTareaSalida) {
		this.idInstanciaDeTareaSalida = idInstanciaDeTareaSalida;
	}	

	public String getEsConfidencial() {
		return esConfidencial;
	}

	public void setEsConfidencial(String esConfidencial) {
		this.esConfidencial = esConfidencial;
	}
	
	public Date getFechaDeInicioInstProc() {
		return fechaDeInicioInstProc;
	}

	public void setFechaDeInicioInstProc(Date fechaDeInicioInstProc) {
		this.fechaDeInicioInstProc = fechaDeInicioInstProc;
	}	

	public String getIdUsuarioCrea() {
		return idUsuarioCrea;
	}

	public void setIdUsuarioCrea(String idUsuarioCrea) {
		this.idUsuarioCrea = idUsuarioCrea;
	}
	
	public String getCodigoProceso() {
		return codigoProceso;
	}

	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}
	
	public String getIdComplejidad() {
		return idComplejidad;
	}

	public void setIdComplejidad(String idComplejidad) {
		this.idComplejidad = idComplejidad;
	}

	public String getMotivoComplejidad() {
		return motivoComplejidad;
	}

	public void setMotivoComplejidad(String motivoComplejidad) {
		this.motivoComplejidad = motivoComplejidad;
	}

	@Override
	public String toString() {
		return "ExpedienteDTO [idExpediente=" + idExpediente
				+ ", nombreExpediente=" + nombreExpediente + ", creador="
				+ creador + ", materia=" + materia + ", idAutor=" + idAutor
				+ ", idPerspectiva=" + idPerspectiva + ", idMacroProceso="
				+ idMacroProceso + ", idProceso=" + idProceso
				+ ", nombreAutor=" + nombreAutor + ", nombrePerpectiva="
				+ nombrePerpectiva 
				+ ", nombreMacroProceso="	+ nombreMacroProceso 
				+ ", nombreProceso=" + nombreProceso 
				+ ", esConfidencial=" + esConfidencial
				+ ", fechaDeInicioInstProc=" + fechaDeInicioInstProc
				+ ", idUsuarioCrea=" + idUsuarioCrea
				+ ", codigoProceso=" + codigoProceso
				+ ", complejidad=" + idComplejidad
				+ ", motivoComplejidad=" + motivoComplejidad
				+ "]";
	}
		
}
