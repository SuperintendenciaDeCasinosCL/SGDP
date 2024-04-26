package cl.gob.scj.sgdp.model;


import java.io.Serializable;
import javax.persistence.*;

	/**
	 * The persistent class for the "SGDP_GRUPOS" database table.
	 * 
	 */
	@Entity
	@Table(name="\"SGDP_GRUPOS\"")
	@NamedQuery(name="Grupo.findAll", query="SELECT s FROM Grupo s")
	public class Grupo implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@Column(name="\"ID_GRUPO\"")
		private Long idGrupo;

		@Column(name="\"CODIGO_GRUPO\"")
		private String codigoGrupo;

		@Column(name="\"NOMBRE_GRUPO\"")
		private String nombreGrupo;

		public Grupo() {
		}

		public Long getIdGrupo() {
			return this.idGrupo;
		}

		public void setIdGrupo(Long idGrupo) {
			this.idGrupo = idGrupo;
		}

		public String getCodigoGrupo() {
			return this.codigoGrupo;
		}

		public void setCodigoGrupo(String codigoGrupo) {
			this.codigoGrupo = codigoGrupo;
		}

		public String getNombreGrupo() {
			return this.nombreGrupo;
		}

		public void setNombreGrupo(String nombreGrupo) {
			this.nombreGrupo = nombreGrupo;
		}

		@Override
		public String toString() {
			return "Grupo [idGrupo=" + idGrupo + ", codigoGrupo=" + codigoGrupo + ", nombreGrupo=" + nombreGrupo + "]";
		}

	
	
}
