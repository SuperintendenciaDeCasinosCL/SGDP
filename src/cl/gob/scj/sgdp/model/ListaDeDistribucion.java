package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import cl.gob.scj.sgdp.config.Constantes;

/**
 * The persistent class for the "SGDP_LISTA_DE_DISTRIBUCION" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_LISTA_DE_DISTRIBUCION\"")
@NamedQuery(name="ListaDeDistribucion.findAll", query="SELECT u FROM ListaDeDistribucion u")
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

	@Override
	public String toString() {
		return "ListaDeDistribucion [idListaDeDistribucion=" + idListaDeDistribucion + ", nombreCompleto="
				+ nombreCompleto 
				+ ", email=" + email
				+ ", organizacion=" + organizacion 
				+ ", cargo=" + cargo				
				+ "]";
	}		
	
}
