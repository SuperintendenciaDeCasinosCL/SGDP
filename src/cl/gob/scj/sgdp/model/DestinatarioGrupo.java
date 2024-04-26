package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import javax.persistence.*;

	/**
	 * The persistent class for the "SGDP_DESTINATARIO_GRUPO" database table.
	 * 
	 */
	@Entity
	@Table(name="\"SGDP_DESTINATARIO_GRUPO\"")
	@NamedQuery(name="DestinatarioGrupo.findAll", query="SELECT s FROM DestinatarioGrupo s")
	public class DestinatarioGrupo implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@Column(name="\"ID_DESTINATARIO_GRUPO\"")
		private Long idDestinatarioGrupo;

		@Column(name="\"ID_GRUPO\"")
		private Long idGrupo;

		@Column(name="\"ID_LISTA_DE_DISTRIBUCION\"")
		private Long idListaDeDistribucion;

		public DestinatarioGrupo() {
		}

		public Long getIdDestinatarioGrupo() {
			return this.idDestinatarioGrupo;
		}

		public void setIdDestinatarioGrupo(Long idDestinatarioGrupo) {
			this.idDestinatarioGrupo = idDestinatarioGrupo;
		}

		public Long getIdGrupo() {
			return this.idGrupo;
		}

		public void setIdGrupo(Long idGrupo) {
			this.idGrupo = idGrupo;
		}

		public Long getIdListaDeDistribucion() {
			return this.idListaDeDistribucion;
		}

		public void setIdListaDeDistribucion(Long idListaDeDistribucion) {
			this.idListaDeDistribucion = idListaDeDistribucion;
		}

		@Override
		public String toString() {
			return "DestinatarioGrupo [idDestinatarioGrupo=" + idDestinatarioGrupo + ", idGrupo=" + idGrupo
					+ ", idListaDeDistribucion=" + idListaDeDistribucion + "]";
		}

	
}
