package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "\"SGDP_DETALLES_CARGA\"")
public class DetalleCarga implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5638671789643263123L;

	@Id
	@SequenceGenerator(name ="SEQ_ID_DETALLE_CARGA", sequenceName = "\"SEQ_ID_DETALLE_CARGA\"", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID_DETALLE_CARGA")
	@Column(name = "\"ID_DETALLE_CARGA\"")
	private Long idDetalleCarga;
	
	@ManyToOne
	@JoinColumn(name="\"ID_CARGA\"")
	private Carga carga;

	@Column(name = "\"A_NOMBRE_DOCUMENTO\"")
	private String aNombreDocumento;

	@Column(name = "\"D_FECHA_CREACION\"")
	private Date dFechaCreacion;


	public DetalleCarga() {
		super();
	}

	public Long getIdDetalleCarga() {
		return idDetalleCarga;
	}

	public void setIdDetalleCarga(Long idDetalleCarga) {
		this.idDetalleCarga = idDetalleCarga;
	}

	public Carga getCargaEntity() {
		return carga;
	}

	public void setCargaEntity(Carga cargaEntity) {
		this.carga = cargaEntity;
	}

	public String getaNombreDocumento() {
		return aNombreDocumento;
	}

	public void setaNombreDocumento(String aNombreDocumento) {
		this.aNombreDocumento = aNombreDocumento;
	}

	public Date getdFechaCreacion() {
		return dFechaCreacion;
	}

	public void setdFechaCreacion(Date dFechaCreacion) {
		this.dFechaCreacion = dFechaCreacion;
	}
}
