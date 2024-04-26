package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class BitacoraSubTareaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idBitacora;
	private Long idInstanciaDeTarea;
	private String nombreTarea;
	private Long idActividad;
	private String nombreActividad;
	private String idUsuario;
	private String accion;
	private Long fecha;
	private Integer duracion;
	private String[] usuarios;
	private boolean canDelete = false; 
		
	public BitacoraSubTareaDTO() {
		super();
	}

	public Long getIdBitacora() {
		return idBitacora;
	}

	public void setIdBitacora(Long idBitacora) {
		this.idBitacora = idBitacora;
	}

	public Long getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}

	public void setIdInstanciaDeTarea(Long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
	}

	public Long getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(Long idActividad) {
		this.idActividad = idActividad;
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

	public Long getFecha() {
		return fecha;
	}

	public void setFecha(java.util.Date date) {
		this.fecha = date.getTime();
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public String getNombreTarea() {
		return nombreTarea;
	}

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public String[] getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(String[] usuarios) {
		this.usuarios = usuarios;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	

}
