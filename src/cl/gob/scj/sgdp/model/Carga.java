package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cl.gob.scj.sgdp.config.Constantes;

@Entity
@Table(name="\"SGDP_CARGAS\"")
@NamedQuery(name="Carga.findAll", query="SELECT s FROM Carga s order by s.idCarga desc")
public class Carga implements Serializable {
	@Id
	@SequenceGenerator(name = "SEQ_ID_CARGA", sequenceName = "\"SEQ_ID_CARGA\"", allocationSize = Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID_CARGA")
	@Column(name="\"ID_CARGA\"")
	private Long idCarga;

	@Column(name="\"A_NOMBRE_ACUERDO\"")
	private String aNombreAcuerdo;

	@Column(name="\"A_NOMBRE_SERIE\"")
	private String aNombreSerie;

	@Column(name="\"A_TIPO_ACUERDO\"")
	private String aTipoAcuerdo;

	@Column(name="\"D_FECHA_CREACION\"")
	private Date dFechaCreacion;

	@Column(name="\"N_CANTIDAD_DOCUMENTOS\"")
	private Long nCantidadDocumentos;

	@Column(name="\"A_ID_TRANSFERENCIA\"")
	private String aIdTransferencia;
	
	//bi-directional many-to-one association to SgdpDetallesCarga
	@OneToMany(mappedBy="carga")
	private List<DetalleCarga> detallesCargas;

	//bi-directional many-to-one association to SgdpLogCarga
	@OneToMany(mappedBy="cargaLog")
	private List<LogCarga> cargasLog;

	public Carga() {
		super();
	}

	public Long getIdCarga() {
		return this.idCarga;
	}

	public void setIdCarga(Long idCarga) {
		this.idCarga = idCarga;
	}

	public String getANombreAcuerdo() {
		return this.aNombreAcuerdo;
	}

	public void setANombreAcuerdo(String aNombreAcuerdo) {
		this.aNombreAcuerdo = aNombreAcuerdo;
	}

	public String getANombreSerie() {
		return this.aNombreSerie;
	}

	public void setANombreSerie(String aNombreSerie) {
		this.aNombreSerie = aNombreSerie;
	}

	public String getATipoAcuerdo() {
		return this.aTipoAcuerdo;
	}

	public void setATipoAcuerdo(String aTipoAcuerdo) {
		this.aTipoAcuerdo = aTipoAcuerdo;
	}

	public Date getDFechaCreacion() {
		return this.dFechaCreacion;
	}

	public void setDFechaCreacion(Date dFechaCreacion) {
		this.dFechaCreacion = dFechaCreacion;
	}

	public Long getNCantidadDocumentos() {
		return this.nCantidadDocumentos;
	}

	public void setNCantidadDocumentos(Long nCantidadDocumentos) {
		this.nCantidadDocumentos = nCantidadDocumentos;
	}

	public String getaIdTransferencia() {
		return aIdTransferencia;
	}

	public void setaIdTransferencia(String aIdTransferencia) {
		this.aIdTransferencia = aIdTransferencia;
	}

	public List<DetalleCarga> getDetallesCargas() {
		return detallesCargas;
	}

	public void setDetallesCargas(List<DetalleCarga> detallesCargas) {
		this.detallesCargas = detallesCargas;
	}

	public void setCargasLog(List<LogCarga> sgdpDetallesCargas) {
		this.cargasLog = sgdpDetallesCargas;
	}
	
	public List<LogCarga> getCargasLog() {
		return this.cargasLog;
	}

}
