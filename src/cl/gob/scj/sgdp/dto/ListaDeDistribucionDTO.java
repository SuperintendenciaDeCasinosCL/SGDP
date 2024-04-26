package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cl.gob.scj.sgdp.util.FechaUtil;
import cl.gob.scj.sgdp.util.SGDPUtil;


public class ListaDeDistribucionDTO implements Serializable {
	
	private static final Logger log = Logger.getLogger(ListaDeDistribucionDTO.class);
	
	@JsonIgnore
	private MultipartFile archivo;
	
	private long idListaDeDistribucion;
	private String nombreCompleto;
	private String email;
	private String organizacion;
	private String cargo;
	private String respuesta;
	private TipoDeDestinatarioDTO tipoDeDestinatarioDTO;
	private long idTipoDestinatario;
	private String nombreGrupo;
	private String motivo;
	private String fechaInicioVigenciaS;
	private String fechaFinVigenciaS;
	private Date fechaInicioVigencia;
	private Date fechaFinVigencia;
	private Long numeroTelefono1;
	private Long numeroTelefono2;
	private boolean errorAlLeerRegistroMasivo;
	private boolean errorAlGuardarRegistroMasivo;
	private String mensajeErrorAlLeerRegistroMasivo;
	private String mensajeErrorAlGuardarRegistroMasivo;
	private String nombreTipoDestinatario;
	private String idUsuarioCreacion;
	private String idUsuarioUltimaModificacion;
	private Date fechaCreacion;
	private Date fechaUltimaModificacion;
	
	public long getIdListaDeDistribucion() {
		return idListaDeDistribucion;
	}
	public void setIdListaDeDistribucion(long idListaDeDistribucion) {
		this.idListaDeDistribucion = idListaDeDistribucion;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getOrganizacion() {
		return organizacion;
	}
	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}	
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}	
	public TipoDeDestinatarioDTO getTipoDeDestinatarioDTO() {
		return tipoDeDestinatarioDTO;
	}
	public void setTipoDeDestinatarioDTO(TipoDeDestinatarioDTO tipoDeDestinatarioDTO) {
		this.tipoDeDestinatarioDTO = tipoDeDestinatarioDTO;
	}	
	public long getIdTipoDestinatario() {
		return idTipoDestinatario;
	}
	public void setIdTipoDestinatario(long idTipoDestinatario) {
		this.idTipoDestinatario = idTipoDestinatario;
	}
	public String getNombreGrupo() {
		return nombreGrupo;
	}
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public Date getFechaInicioVigencia() {
		return fechaInicioVigencia;
	}
	public void setFechaInicioVigencia(Date fechaInicioVigencia) {
		this.fechaInicioVigencia = fechaInicioVigencia;
	}
	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}
	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}
	public Long getNumeroTelefono1() {
		return numeroTelefono1;
	}
	public void setNumeroTelefono1(Long numeroTelefono1) {
		this.numeroTelefono1 = numeroTelefono1;
	}
	public Long getNumeroTelefono2() {
		return numeroTelefono2;
	}
	public void setNumeroTelefono2(Long numeroTelefono2) {
		this.numeroTelefono2 = numeroTelefono2;
	}
	public String getFechaInicioVigenciaS() {
		return fechaInicioVigenciaS;
	}
	public void setFechaInicioVigenciaS(String fechaInicioVigenciaS) {
		this.fechaInicioVigenciaS = fechaInicioVigenciaS;
		if (fechaInicioVigenciaS!=null && !fechaInicioVigenciaS.isEmpty() && this.fechaInicioVigencia == null) {
			try {
				this.fechaInicioVigencia = FechaUtil.simpleDateFormatForm.parse(fechaInicioVigenciaS);
			} catch (ParseException e) {
				log.error(SGDPUtil.getStackTrace(e));
			}
		}
	}
	public String getFechaFinVigenciaS() {
		return fechaFinVigenciaS;
	}
	public void setFechaFinVigenciaS(String fechaFinVigenciaS) {
		this.fechaFinVigenciaS = fechaFinVigenciaS;
		if (fechaFinVigenciaS!=null && !fechaFinVigenciaS.isEmpty() && this.fechaFinVigencia == null) {
			try {
				this.fechaFinVigencia = FechaUtil.simpleDateFormatForm.parse(fechaFinVigenciaS);
			} catch (ParseException e) {
				log.error(SGDPUtil.getStackTrace(e));
			}
		}
	}
	public MultipartFile getArchivo() {
		return archivo;
	}
	public void setArchivo(MultipartFile archivo) {
		this.archivo = archivo;
	}
	public boolean isErrorAlLeerRegistroMasivo() {
		return errorAlLeerRegistroMasivo;
	}
	public void setErrorAlLeerRegistroMasivo(boolean errorAlLeerRegistroMasivo) {
		this.errorAlLeerRegistroMasivo = errorAlLeerRegistroMasivo;
	}
	public boolean isErrorAlGuardarRegistroMasivo() {
		return errorAlGuardarRegistroMasivo;
	}
	public void setErrorAlGuardarRegistroMasivo(boolean errorAlGuardarRegistroMasivo) {
		this.errorAlGuardarRegistroMasivo = errorAlGuardarRegistroMasivo;
	}
	public String getMensajeErrorAlLeerRegistroMasivo() {
		return mensajeErrorAlLeerRegistroMasivo;
	}
	public void setMensajeErrorAlLeerRegistroMasivo(String mensajeErrorAlLeerRegistroMasivo) {
		this.mensajeErrorAlLeerRegistroMasivo = mensajeErrorAlLeerRegistroMasivo;
	}
	public String getMensajeErrorAlGuardarRegistroMasivo() {
		return mensajeErrorAlGuardarRegistroMasivo;
	}
	public void setMensajeErrorAlGuardarRegistroMasivo(String mensajeErrorAlGuardarRegistroMasivo) {
		this.mensajeErrorAlGuardarRegistroMasivo = mensajeErrorAlGuardarRegistroMasivo;
	}
	public String getNombreTipoDestinatario() {
		return nombreTipoDestinatario;
	}
	public void setNombreTipoDestinatario(String nombreTipoDestinatario) {
		this.nombreTipoDestinatario = nombreTipoDestinatario;
	}
	public String getIdUsuarioCreacion() {
		return idUsuarioCreacion;
	}
	public void setIdUsuarioCreacion(String idUsuarioCreacion) {
		this.idUsuarioCreacion = idUsuarioCreacion;
	}
	public String getIdUsuarioUltimaModificacion() {
		return idUsuarioUltimaModificacion;
	}
	public void setIdUsuarioUltimaModificacion(String idUsuarioUltimaModificacion) {
		this.idUsuarioUltimaModificacion = idUsuarioUltimaModificacion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}
	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
	@Override
	public String toString() {
		return "ListaDeDistribucionDTO [idListaDeDistribucion=" + idListaDeDistribucion + ", nombreCompleto="
				+ ", cargo=" + cargo
				+ ", respuesta=" + respuesta
				+ ", idTipoDestinatario=" + idTipoDestinatario
				+ ", motivo=" + motivo
				+ ", fechaInicioVigencia=" + fechaInicioVigencia
				+ ", fechaFinVigencia=" + fechaFinVigencia
				+ ", setFechaInicioVigenciaS=" + fechaInicioVigenciaS
				+ ", fechaFinVigenciaS=" + fechaFinVigenciaS
				+ ", numeroTelefono1=" + numeroTelefono1
				+ ", numeroTelefono2=" + numeroTelefono2
				+ ", errorAlLeerRegistroMasivo=" + errorAlLeerRegistroMasivo
				+ ", errorAlGuardarRegistroMasivo=" + errorAlGuardarRegistroMasivo
				+ ", mensajeErrorAlLeerRegistroMasivo=" + mensajeErrorAlLeerRegistroMasivo
				+ ", mensajeErrorAlGuardarRegistroMasivo=" + mensajeErrorAlGuardarRegistroMasivo
				+ ", nombreTipoDestinatario=" + nombreTipoDestinatario
				+ ", idUsuarioCreacion=" + idUsuarioCreacion
				+ ", idUsuarioUltimaModificacion=" + idUsuarioUltimaModificacion
				+ ", fechaCreacion=" + fechaCreacion
				+ ", fechaUltimaModificacion=" + fechaUltimaModificacion
				+ "]";
	}
	
}
