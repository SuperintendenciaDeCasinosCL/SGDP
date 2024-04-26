package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;

@Entity
@Table(name="\"SGDP_SERIE_DOCUMENTAL\"")
public class SerieDocumental implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_SERIE_DOCUMENTAL", sequenceName="\"SEQ_ID_SERIE_DOCUMENTAL\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_SERIE_DOCUMENTAL")
	@Column(name="\"ID_SERIE_DOCUMENTAL\"")
	private long idSerieDocumental;
	
	@ManyToOne
	@JoinColumn(name="\"ID_MACRO_PROCESO\"")
	private MacroProceso funcion;
	
	@ManyToOne
	@JoinColumn(name="\"ID_SUPER_PROCESO\"")
	private SuperProceso subFuncion;
	
	@Column(name="\"A_CODIGO_PROCESO\"")
	private String codigoProceso;
	
	@Column(name="\"A_NOMBRE_PROCESO\"")
	private String nombreProceso;
	
	@Column(name="\"A_SERIE_DOCUMENTAL\"")
	private String serieDocumental;

	@Column(name="\"A_SUBSERIE_DOCUMENTAL\"")
	private String subSerieDocumental;

	public SerieDocumental() {
	}

	public long getIdSerieDocumental() {
		return idSerieDocumental;
	}

	public void setIdSerieDocumental(long idSerieDocumental) {
		this.idSerieDocumental = idSerieDocumental;
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

	public MacroProceso getFuncion() {
		return funcion;
	}

	public void setFuncion(MacroProceso funcion) {
		this.funcion = funcion;
	}

	public SuperProceso getSubFuncion() {
		return subFuncion;
	}

	public void setSubFuncion(SuperProceso subFuncion) {
		this.subFuncion = subFuncion;
	}

	public String getCodigoProceso() {
		return codigoProceso;
	}

	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}



}