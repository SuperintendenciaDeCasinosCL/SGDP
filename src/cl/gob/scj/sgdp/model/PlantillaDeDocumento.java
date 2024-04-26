package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the "SGDP_PLANTILLA_DE_DOCUMENTO" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_PLANTILLA_DE_DOCUMENTO\"")
@NamedQueries({
	
	@NamedQuery(name="PlantillaDeDocumento.findAll", query="SELECT p FROM PlantillaDeDocumento p WHERE p.vigente is true ORDER BY p.nombre ASC"),
	
	@NamedQuery(name="PlantillaDeDocumento.cancelaVigenciaPorCodigo", query="UPDATE PlantillaDeDocumento p set p.vigente = false WHERE p.codigo = :codigo"),
	
	@NamedQuery(name="PlantillaDeDocumento.getPlantillaPorIdTipoDocumento", 
		query="SELECT p FROM PlantillaDeDocumento p, DocumentoDeSalidaDeTarea d "
				+ " WHERE d.id.tipoDeDocumento.plantilla.idPlantillaDeDocumento = p.idPlantillaDeDocumento"
				+ " AND d.id.tipoDeDocumento.idTipoDeDocumento = :idTipoDeDocumento")

})
public class PlantillaDeDocumento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_PLANTILLA_DE_DOCUMENTO", sequenceName="\"SEQ_ID_PLANTILLA_DE_DOCUMENTO\"")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_PLANTILLA_DE_DOCUMENTO")
	@Column(name="\"ID_PLANTILLA\"")
	private long idPlantillaDeDocumento;

	@Column(name="\"A_NOMBRE\"")
	private String nombre;
	
	@Column(name="\"A_DESCRIPCION\"")
	private String descripcion;
	
	@Column(name="\"A_CODIGO\"")
	private String codigo;
	
	@Column(name="\"B_VIGENTE\"")
	private Boolean vigente;
	
	@Column(name="\"A_PLANTILLA_JSON\"")	
	private String plantilla;
	
	public PlantillaDeDocumento() {
	}

	public long getIdPlantillaDeDocumento() {
		return idPlantillaDeDocumento;
	}

	public void setIdPlantillaDeDocumento(long idPlantillaDeDocumento) {
		this.idPlantillaDeDocumento = idPlantillaDeDocumento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public String getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
}