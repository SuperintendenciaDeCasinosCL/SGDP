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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.gob.scj.sgdp.config.Constantes;

/**
 * The persistent class for the "SGDP_LISTA_DE_DISTRIBUCION" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_LISTA_DE_DISTRIBUCION\"")
@NamedQueries({	
	
	@NamedQuery(name="ListaDeDistribucion.findAll", query="SELECT u FROM ListaDeDistribucion u"),
	
	@NamedQuery(name="ListaDeDistribucion.getListaDistribucionPorIdTipoDestinatario", 
				query="SELECT u FROM ListaDeDistribucion u where u.tipoDeDestinatario.idTipoDestinatario = :idTipoDestinatario"),
	
	@NamedQuery(name="ListaDeDistribucion.getListaDistribucionPorEmail", 
				query="SELECT u FROM ListaDeDistribucion u where u.email = :email"),
	
	@NamedQuery(name="ListaDeDistribucion.getListaDistribucionPorEmailIdDistinto", 
				query="SELECT u FROM ListaDeDistribucion u where u.email = :email and u.idListaDeDistribucion <> :idListaDeDistribucion")
	
})
public class ListaDeDistribucion implements Serializable {
	
	@Id
	@SequenceGenerator(name="SEQ_ID_LISTA_DE_DISTRIBUCION", sequenceName="\"SEQ_ID_LISTA_DE_DISTRIBUCION\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_LISTA_DE_DISTRIBUCION")
	@Column(name="\"ID_LISTA_DE_DISTRIBUCION\"")
	private long idListaDeDistribucion;

	@Column(name="\"A_NOMBRE_COMPLETO\"")
	private String nombreCompleto;

	@Column(name="\"A_EMAIL\"")
	private String email;
	
	@Column(name="\"A_ORGANIZACION\"")
	private String organizacion;
	
	@Column(name="\"A_CARGO\"")
	private String cargo;
	
	//bi-directional many-to-one association to TipoDeDestinatario
	@ManyToOne
	@JoinColumn(name="\"ID_TIPO_DE_DESTINATARIO\"")
	private TipoDeDestinatario tipoDeDestinatario; 	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_INICIO_VIGENCIA\"")
	private Date fechaInicioVigencia;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_FIN_VIGENCIA\"")
	private Date fechaFinVigencia;
	
	@Column(name="\"N_NUMERO_TELEFONO_1\"")
	private Long numeroTelefono1;
	
	@Column(name="\"N_NUMERO_TELEFONO_2\"")
	private Long numeroTelefono2;
	
	@Column(name="\"ID_USUARIO_CREACION\"")
	private String idUsuarioCreacion;
	
	@Column(name="\"ID_USUARIO_ULTIMA_MODIFICACION\"")
	private String idUsuarioUltimaModificacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_CREACION\"")
	private Date fechaCreacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_ULTIMA_MODIFICACION\"")
	private Date fechaUltimaModificacion;

	public long getIdListaDeDistribucion() {
		return idListaDeDistribucion;
	}

	public void setIdListaDeDistribucion(long idListaDeDistribucion) {
		this.idListaDeDistribucion = idListaDeDistribucion;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public TipoDeDestinatario getTipoDeDestinatario() {
		return tipoDeDestinatario;
	}

	public void setTipoDeDestinatario(TipoDeDestinatario tipoDeDestinatario) {
		this.tipoDeDestinatario = tipoDeDestinatario;
	}

	public Date getFechaInicioVigencia() {
		return fechaInicioVigencia;
	}

	public void setFechaInicioVigencia(Date fechaInicioVigencia) {
		this.fechaInicioVigencia = fechaInicioVigencia;
	}

	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public Long getNumeroTelefono1() {
		return numeroTelefono1;
	}

	public void setNumeroTelefono1(Long numeroTelefono1) {
		this.numeroTelefono1 = numeroTelefono1;
	}

	public Long getNumeroTelefono2() {
		return numeroTelefono2;
	}

	public void setNumeroTelefono2(Long numeroTelefono2) {
		this.numeroTelefono2 = numeroTelefono2;
	}
	
	public String getIdUsuarioCreacion() {
		return idUsuarioCreacion;
	}

	public void setIdUsuarioCreacion(String idUsuarioCreacion) {
		this.idUsuarioCreacion = idUsuarioCreacion;
	}

	public String getIdUsuarioUltimaModificacion() {
		return idUsuarioUltimaModificacion;
	}

	public void setIdUsuarioUltimaModificacion(String idUsuarioUltimaModificacion) {
		this.idUsuarioUltimaModificacion = idUsuarioUltimaModificacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	@Override
	public String toString() {
		return "ListaDeDistribucion [idListaDeDistribucion=" + idListaDeDistribucion + ", nombreCompleto="
				+ nombreCompleto + ", email=" + email + ", organizacion=" + organizacion + ", cargo=" + cargo
				+ ", tipoDeDestinatario=" + tipoDeDestinatario + ", fechaInicioVigencia=" + fechaInicioVigencia
				+ ", fechaFinVigencia=" + fechaFinVigencia + ", numeroTelefono1=" + numeroTelefono1
				+ ", numeroTelefono2=" + numeroTelefono2 + ", idUsuarioCreacion=" + idUsuarioCreacion
				+ ", idUsuarioUltimaModificacion=" + idUsuarioUltimaModificacion + ", fechaCreacion=" + fechaCreacion
				+ ", fechaUltimaModificacion=" + fechaUltimaModificacion + "]";
	}
	
}
