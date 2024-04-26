package cl.gob.scj.sgdp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cl.gob.scj.sgdp.config.Constantes;

@Entity
@Table(name="\"SGDP_TIPOS_DE_DESTINATARIOS\"")
@NamedQueries({

	@NamedQuery(name="TipoDeDestinatario.findAll", query="SELECT a FROM TipoDeDestinatario a"),
	
	@NamedQuery(name="TipoDeDestinatario.getTipoDeDestinatarioPorNombre", query="SELECT a FROM TipoDeDestinatario a WHERE a.nombreTipoDestinatario = :nombreTipoDestinatario"),

	@NamedQuery(name="TipoDeDestinatario.getTipoDeDestinatarioEnListaDistribucion", 
		query="SELECT a FROM TipoDeDestinatario a, ListaDeDistribucion l "
				+ "WHERE a.idTipoDestinatario = :idTipoDestinatario "
				+ "AND l.tipoDeDestinatario.idTipoDestinatario = :idTipoDestinatario")
	
})
public class TipoDeDestinatario {	
	
	@Id
	@SequenceGenerator(name="SEQ_ID_TIPO_DE_DESTINATARIO", sequenceName="\"SEQ_ID_TIPO_DE_DESTINATARIO\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_TIPO_DE_DESTINATARIO")
	@Column(name="\"ID_TIPO_DE_DESTINATARIO\"")
	private long idTipoDestinatario;

	@Column(name="\"A_NOMBRE_TIPO_DE_DESTINATARIO\"")
	private String nombreTipoDestinatario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA_CREACION\"")
	private Date fechaCreacion;
	
	@Column(name="\"A_USUARIO_CREACION\"")
	private String usuarioCreacion;
	
	//bi-directional many-to-one association to ListaDeDistribucion
	@JsonIgnore
	@OneToMany(mappedBy="tipoDeDestinatario", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<ListaDeDistribucion> listaDeDistribucion;

	public long getIdTipoDestinatario() {
		return idTipoDestinatario;
	}

	public void setIdTipoDestinatario(long idTipoDestinatario) {
		this.idTipoDestinatario = idTipoDestinatario;
	}

	public String getNombreTipoDestinatario() {
		return nombreTipoDestinatario;
	}

	public void setNombreTipoDestinatario(String nombreTipoDestinatario) {
		this.nombreTipoDestinatario = nombreTipoDestinatario;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public List<ListaDeDistribucion> getListaDeDistribucion() {
		return listaDeDistribucion;
	}

	public void setListaDeDistribucion(List<ListaDeDistribucion> listaDeDistribucion) {
		this.listaDeDistribucion = listaDeDistribucion;
	}	
	
}