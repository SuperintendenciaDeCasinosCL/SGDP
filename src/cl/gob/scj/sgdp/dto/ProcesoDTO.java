package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ProcesoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idProceso;
	private String descripcionProceso;
	private String nombreProceso;
	private Boolean vigente;
	private Integer diasHabilesMaxDuracion;
	private String nombreMacroproceso;
	private String codigoProceso;
	private Long idMacroproceso;
	private Long idSuperProceso;
	private Boolean privado;
	private String xml;
	private Long idUnidad;
	private Date fechaCreacion;
	private Boolean tieneParametrosPorTarea;
	private List<TareaDTO> tareas;
	private List<TipoDeDocumentoDTO> docs;
	private List<ResponsabilidadDTO> roles;
	
	
	private MultipartFile image;
	private String tmp;
	private String nombreArchivo;

	public ProcesoDTO(Long idProceso, String descripcionProceso,
			String nombreProceso, Boolean vigente,
			Integer diasHabilesMaxDuracion) {
		super();
		this.idProceso = idProceso;
		this.descripcionProceso = descripcionProceso;
		this.nombreProceso = nombreProceso;
		this.vigente = vigente;
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
	}
	
	
	public ProcesoDTO(Long idProceso, String descripcionProceso,
			String nombreProceso, Boolean vigente,
			Integer diasHabilesMaxDuracion, String nombreMacroproceso, String codigoProceso) {
		super();
		this.idProceso = idProceso;
		this.descripcionProceso = descripcionProceso;
		this.nombreProceso = nombreProceso;
		this.vigente = vigente;
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
		this.nombreMacroproceso = nombreMacroproceso;
		this.codigoProceso = codigoProceso;
	}
	



	public ProcesoDTO(Long idProceso, String descripcionProceso, String nombreProceso, Boolean vigente,
			Integer diasHabilesMaxDuracion, String nombreMacroproceso, String codigoProceso, Long idMacroproceso,
			Boolean privado, String xml, Long idUnidad, Date fechaCreacion, List<TareaDTO> tareas,
			List<TipoDeDocumentoDTO> docs, List<ResponsabilidadDTO> roles) {
		super();
		this.idProceso = idProceso;
		this.descripcionProceso = descripcionProceso;
		this.nombreProceso = nombreProceso;
		this.vigente = vigente;
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
		this.nombreMacroproceso = nombreMacroproceso;
		this.codigoProceso = codigoProceso;
		this.idMacroproceso = idMacroproceso;
		this.privado = privado;
		this.xml = xml;
		this.idUnidad = idUnidad;
		this.fechaCreacion = fechaCreacion;
		this.tareas = tareas;
		this.docs = docs;
		this.roles = roles;
	}


	public ProcesoDTO() {
	}


	public Long getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}
	public String getDescripcionProceso() {
		return descripcionProceso;
	}
	public void setDescripcionProceso(String descripcionProceso) {
		this.descripcionProceso = descripcionProceso;
	}
	public String getNombreProceso() {
		return nombreProceso;
	}
	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}
	public Boolean getVigente() {
		return vigente;
	}
	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}
	public Integer getDiasHabilesMaxDuracion() {
		return diasHabilesMaxDuracion;
	}
	public void setDiasHabilesMaxDuracion(Integer diasHabilesMaxDuracion) {
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
	}
	public String getNombreMacroproceso() {
		return nombreMacroproceso;
	}
	public void setNombreMacroproceso(String nombreMacroproceso) {
		this.nombreMacroproceso = nombreMacroproceso;
	}
	public String getCodigoProceso() {
		return codigoProceso;
	}
	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}
	@Override
	public String toString() {
		return "ProcesoDTO [idProceso=" + idProceso + ", descripcionProceso=" + descripcionProceso + ", nombreProceso="
				+ nombreProceso + ", vigente=" + vigente + ", diasHabilesMaxDuracion=" + diasHabilesMaxDuracion
				+ ", nombreMacroproceso=" + nombreMacroproceso + ", codigoProceso=" + codigoProceso + "]";
	}


	public Long getIdMacroproceso() {
		return idMacroproceso;
	}


	public void setIdMacroproceso(Long idMacroproceso) {
		this.idMacroproceso = idMacroproceso;
	}


	public Boolean getPrivado() {
		return privado;
	}


	public void setPrivado(Boolean privado) {
		this.privado = privado;
	}


	public String getXml() {
		return xml;
	}


	public void setXml(String xml) {
		this.xml = xml;
	}


	public Long getIdUnidad() {
		return idUnidad;
	}


	public void setIdUnidad(Long idUnidad) {
		this.idUnidad = idUnidad;
	}


	


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public List<TareaDTO> getTareas() {
		return tareas;
	}


	public void setTareas(List<TareaDTO> tareas) {
		this.tareas = tareas;
	}


	public List<TipoDeDocumentoDTO> getDocs() {
		return docs;
	}


	public void setDocs(List<TipoDeDocumentoDTO> docs) {
		this.docs = docs;
	}


	public List<ResponsabilidadDTO> getRoles() {
		return roles;
	}


	public void setRoles(List<ResponsabilidadDTO> roles) {
		this.roles = roles;
	}


	public Long getIdSuperProceso() {
		return idSuperProceso;
	}


	public void setIdSuperProceso(Long idSuperProceso) {
		this.idSuperProceso = idSuperProceso;
	}


	public MultipartFile getImage() {
		return image;
	}


	public void setImage(MultipartFile image) {
		this.image = image;
	}


	public String getTmp() {
		return tmp;
	}


	public void setTmp(String tmp) throws UnsupportedEncodingException {
		this.tmp = tmp == null ? tmp : new String(tmp.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
	}


	public String getNombreArchivo() {
		return nombreArchivo;
	}


	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}


	public Boolean getTieneParametrosPorTarea() {
		return tieneParametrosPorTarea;
	}


	public void setTieneParametrosPorTarea(Boolean tieneParametrosPorTarea) {
		this.tieneParametrosPorTarea = tieneParametrosPorTarea;
	}


}
