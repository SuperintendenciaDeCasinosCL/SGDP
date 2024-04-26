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

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.array.StringArrayType;

import cl.gob.scj.sgdp.config.Constantes;

@TypeDefs({
    @TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
    )
})
@Entity
@Table(name="\"SGDP_BITACORA_SUBTAREAS\"")
@NamedQueries({
@NamedQuery(name="bitacoraSuBTareas.findAllByIdInstTarea", query=""
		+ "	SELECT "
		+ "		b "
		+ "	FROM "
		+ "		BitacoraSubTareas b"
		+ "	where "
		+ "		b.instanciaDeTarea.idInstanciaDeTarea = :idInstanciaDeTarea "
		+ "		and b.activo = true"
),
@NamedQuery(name="bitacoraSuBTareas.findByIdBitacoraSubTareaIdInstTarea", query=""
		+ "	SELECT "
		+ "		b "
		+ "	FROM "
		+ "		BitacoraSubTareas b"
		+ "	where "
		+ "		b.instanciaDeTarea.idInstanciaDeTarea = :idInstanciaDeTarea "
		+ "		and b.idBitacoraSubTarea = :idBitacoraSubTarea"
)
})

public class BitacoraSubTareas implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SequenceGenerator(name="SEQ_ID_BITACORA_SUBTAREAS", sequenceName="\"SEQ_ID_BITACORA_SUBTAREAS\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_BITACORA_SUBTAREAS")
	@Column(name="\"ID_BITACORA_SUBTAREAS\"")
	private long idBitacoraSubTarea;
	

	@ManyToOne
	@JoinColumn(name="\"ID_ACTIVIDAD\"")
	private TipoActividadBitacora tipoActividadBitacora;

	@ManyToOne
	@JoinColumn(name="\"ID_INSTANCIA_DE_TAREA\"")
	private InstanciaDeTarea instanciaDeTarea;

	@Column(name="\"ID_USUARIO\"")
	private String idUsuario;
	
	@Column(name="\"A_ACCION\"")
	private String accion;
	
	@Column(name="\"N_DURACION\"")
	private Integer duracion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"D_FECHA\"")
	private Date fecha;
	
	@Column(name="\"B_ACTIVO\"")
	private Boolean activo = true;

	@Type(type = "string-array")
    @Column(
        name = "\"R_USUARIOS_ASOCIADOS\"",
        columnDefinition = "text[]"
    )
	private String[] usuariosAsociados;

	public BitacoraSubTareas() {
	}

	public long getIdBitacoraSubTarea() {
		return idBitacoraSubTarea;
	}

	public void setIdBitacoraSubTarea(long idBitacoraSubTarea) {
		this.idBitacoraSubTarea = idBitacoraSubTarea;
	}

	public TipoActividadBitacora getTipoActividadBitacora() {
		return tipoActividadBitacora;
	}

	public void setTipoActividadBitacora(TipoActividadBitacora tipoActividadBitacora) {
		this.tipoActividadBitacora = tipoActividadBitacora;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public InstanciaDeTarea getInstanciaDeTarea() {
		return instanciaDeTarea;
	}

	public void setInstanciaDeTarea(InstanciaDeTarea instanciaDeTarea) {
		this.instanciaDeTarea = instanciaDeTarea;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public String[] getUsuariosAsociados() {
		return usuariosAsociados;
	}

	public void setUsuariosAsociados(String[] usuariosAsociados) {
		this.usuariosAsociados = usuariosAsociados;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	
}
