package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the "SGDP_HISTORICO_USUARIOS_ASIGNADOS_A_TAREAS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_HISTORICO_USUARIOS_ASIGNADOS_A_TAREAS\"")
@NamedQueries({
	@NamedQuery(name="HistoricoUsuariosAsignadosATarea.findAll", query="SELECT s FROM HistoricoUsuariosAsignadosATarea s"),
	@NamedQuery(name="HistoricoUsuariosAsignadosATarea.getHistoricoUsrAsigandoPorIdHistoricoInstTarea", 
		query="SELECT s FROM HistoricoUsuariosAsignadosATarea s "
				+ "WHERE s.id.historicoDeInstDeTarea.idHistoricoDeInstDeTarea = :idHistoricoDeInstDeTarea")
	})
public class HistoricoUsuariosAsignadosATarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private HistoricoUsuariosAsignadosATareaPK id;
	
	public HistoricoUsuariosAsignadosATarea() {
	}	
	
	public HistoricoUsuariosAsignadosATareaPK getId() {
		return this.id;
	}

	public void setId(HistoricoUsuariosAsignadosATareaPK id) {
		this.id = id;
	}	

}