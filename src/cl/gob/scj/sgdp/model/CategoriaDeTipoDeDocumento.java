package cl.gob.scj.sgdp.model;

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

@Entity
@Table(name="\"SGDP_CATEGORIA_DE_TIPO_DE_DOCUMENTO\"")
@NamedQueries({
	@NamedQuery(name="CategoriaDeTipoDeDocumento.findAll", query="SELECT t FROM CategoriaDeTipoDeDocumento t")
})
public class CategoriaDeTipoDeDocumento {
	
	@Id
	@SequenceGenerator(name="SEQ_ID_CATEGORIA_DE_TIPO_DE_DOCUMENTO", sequenceName="\"SEQ_ID_CATEGORIA_DE_TIPO_DE_DOCUMENTO\"")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_CATEGORIA_DE_TIPO_DE_DOCUMENTO")
	@Column(name="\"ID_CATEGORIA_DE_TIPO_DE_DOCUMENTO\"")
	private long idCategoriaDeTipoDeDocumento;
	
	@Column(name="\"A_NOMBRE_DE_CATEGORIA_DE_TIPO_DE_DOCUMENTO\"")
	private String nombreDeCategoriaDeTipoDeDocumento;
	
	//bi-directional many-to-one association to TipoDeDocumento
	@OneToMany(mappedBy="categoriaDeTipoDeDocumento", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<TipoDeDocumento> tiposDeDocumentos;

	public long getIdCategoriaDeTipoDeDocumento() {
		return idCategoriaDeTipoDeDocumento;
	}

	public void setIdCategoriaDeTipoDeDocumento(long idCategoriaDeTipoDeDocumento) {
		this.idCategoriaDeTipoDeDocumento = idCategoriaDeTipoDeDocumento;
	}

	public String getNombreDeCategoriaDeTipoDeDocumento() {
		return nombreDeCategoriaDeTipoDeDocumento;
	}

	public void setNombreDeCategoriaDeTipoDeDocumento(String nombreDeCategoriaDeTipoDeDocumento) {
		this.nombreDeCategoriaDeTipoDeDocumento = nombreDeCategoriaDeTipoDeDocumento;
	}

	public List<TipoDeDocumento> getTiposDeDocumentos() {
		return tiposDeDocumentos;
	}

	public void setTiposDeDocumentos(List<TipoDeDocumento> tiposDeDocumentos) {
		this.tiposDeDocumentos = tiposDeDocumentos;
	}
	
	public TipoDeDocumento addTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
		getTiposDeDocumentos().add(tipoDeDocumento);
		tipoDeDocumento.setCategoriaDeTipoDeDocumento(this);

		return tipoDeDocumento;
	}

	public TipoDeDocumento removeTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
		getTiposDeDocumentos().remove(tipoDeDocumento);
		tipoDeDocumento.setCategoriaDeTipoDeDocumento(null);

		return tipoDeDocumento;
	}	

	@Override
	public String toString() {
		return "CategoriaDeTipoDeDocumento [idCategoriaDeTipoDeDocumento=" + idCategoriaDeTipoDeDocumento
				+ ", nombreDeCategoriaDeTipoDeDocumento=" + nombreDeCategoriaDeTipoDeDocumento + "]";
	}
	
}