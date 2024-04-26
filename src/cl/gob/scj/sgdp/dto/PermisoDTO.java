package cl.gob.scj.sgdp.dto;

public class PermisoDTO {

	private long idPermiso;
	private String nombrePermiso;
	private boolean activo;
	
	public PermisoDTO() {
		
	}
	public PermisoDTO(long idPermiso, String nombrePermiso) {
		super();
		this.idPermiso = idPermiso;
		this.nombrePermiso = nombrePermiso;
	}
	public long getIdPermiso() {
		return idPermiso;
	}
	public void setIdPermiso(long idPermiso) {
		this.idPermiso = idPermiso;
	}
	public String getNombrePermiso() {
		return nombrePermiso;
	}
	public void setNombrePermiso(String nombrePermiso) {
		this.nombrePermiso = nombrePermiso;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	@Override
	public String toString() {
		return "PermisoDTO [idPermiso=" + idPermiso + ", nombrePermiso=" + nombrePermiso + ", activo=" + activo + "]";
	}
	
}
