package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the "SGDP_AUTORES" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_AUTORES\"")
@NamedQueries({
@NamedQuery(name="Autor.findAll", query="SELECT a FROM Autor a ORDER BY a.nombreAutor ASC")
})
public class Autor implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_AUTOR", sequenceName="\"SEQ_ID_AUTOR\"")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_AUTOR")
	@Column(name="\"ID_AUTOR\"")
	private long idAutor;

	@Column(name="\"A_NOMBRE_AUTOR\"")
	private String nombreAutor;
	
	//bi-directional many-to-one association to SolicitudCreacionExp
	@OneToMany(mappedBy="autor")
	private List<SolicitudCreacionExp> solicitudesCreacionExps;
	
	//bi-directional many-to-one association to HistoricoSolicitudCreacionExp
	@OneToMany(mappedBy="autor")
	private List<HistoricoSolicitudCreacionExp> historicoSolicitudesCreacionExps;

	public Autor() {
	}

	public long getIdAutor() {
		return this.idAutor;
	}

	public void setIdAutor(long idAutor) {
		this.idAutor = idAutor;
	}

	public String getNombreAutor() {
		return this.nombreAutor;
	}

	public void setNombreAutor(String nombreAutor) {
		this.nombreAutor = nombreAutor;
	}
	
	public List<SolicitudCreacionExp> getSolicitudesCreacionExps() {
		return this.solicitudesCreacionExps;
	}

	public void setSolicitudesCreacionExps(List<SolicitudCreacionExp> solicitudesCreacionExps) {
		this.solicitudesCreacionExps = solicitudesCreacionExps;
	}

	public SolicitudCreacionExp addSolicitudesCreacionExp(SolicitudCreacionExp solicitudesCreacionExp) {
		getSolicitudesCreacionExps().add(solicitudesCreacionExp);
		solicitudesCreacionExp.setAutor(this);

		return solicitudesCreacionExp;
	}

	public SolicitudCreacionExp removeSolicitudesCreacionExp(SolicitudCreacionExp solicitudesCreacionExp) {
		getSolicitudesCreacionExps().remove(solicitudesCreacionExp);
		solicitudesCreacionExp.setAutor(null);

		return solicitudesCreacionExp;
	}
	
	public List<HistoricoSolicitudCreacionExp> getHistoricoSolicitudesCreacionExps() {
		return historicoSolicitudesCreacionExps;
	}

	public void setHistoricoSolicitudesCreacionExps(List<HistoricoSolicitudCreacionExp> historicoSolicitudesCreacionExps) {
		this.historicoSolicitudesCreacionExps = historicoSolicitudesCreacionExps;
	}
	
	public HistoricoSolicitudCreacionExp addHistoricoSolicitudesCreacionExp(HistoricoSolicitudCreacionExp historicoSolicitudCreacionExp) {
		getHistoricoSolicitudesCreacionExps().add(historicoSolicitudCreacionExp);
		historicoSolicitudCreacionExp.setAutor(this);

		return historicoSolicitudCreacionExp;
	}

	public HistoricoSolicitudCreacionExp removeHistoricoSolicitudesCreacionExp(HistoricoSolicitudCreacionExp historicoSolicitudCreacionExp) {
		getHistoricoSolicitudesCreacionExps().remove(historicoSolicitudCreacionExp);
		historicoSolicitudCreacionExp.setAutor(null);

		return historicoSolicitudCreacionExp;
	}

}