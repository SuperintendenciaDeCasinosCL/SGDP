package cl.gob.scj.sgdp.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="\"SGDP_RESPONSABILIDAD_TAREA\"")
@NamedQueries({
	@NamedQuery(name="ResponsabilidadTarea.findAll", query="SELECT t FROM ResponsabilidadTarea t"),
	
	@NamedQuery(name="ResponsabilidadTarea.getResponsabilidadesTareasPorIdTarea", 
	query="SELECT t FROM ResponsabilidadTarea t where t.id.tarea.idTarea = :idTarea"),
	
	@NamedQuery(name="ResponsabilidadTarea.findAllByProcessID", 
	query="SELECT distinct r FROM ResponsabilidadTarea r where r.id.tarea.proceso.idProceso = :processid")
})
public class ResponsabilidadTarea implements Serializable  {

	@EmbeddedId
	private ResponsabilidadTareaPK id;

	public ResponsabilidadTareaPK getId() {
		return id;
	}

	public void setId(ResponsabilidadTareaPK id) {
		this.id = id;
	}
	
}
