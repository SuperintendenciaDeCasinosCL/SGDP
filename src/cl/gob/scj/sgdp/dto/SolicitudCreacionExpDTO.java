package cl.gob.scj.sgdp.dto;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;

import cl.gob.scj.sgdp.exception.SgdpException;
import cl.gob.scj.sgdp.util.FechaUtil;

public class SolicitudCreacionExpDTO extends RespuestaDTO {

	private long idSolicitudCreacionExp;
	private String comentario;
	private Date fechaAtencion;
	private Date fechaSolicitud;
	private String idUsuarioCreadorExpediente;
	private String idUsuarioDestinatario;
	private String idUsuarioSolicitante;
	private long idEstadoSolicitudCreacionExp;
	private String nombreEstadoSolicitudCreacionExp;
	private long idProceso;
	private String nombreSubProceso;
	private AutorDTO autorDTO;
	private String asuntoMateria;
	private List<AutorDTO> autoresDTO;
	private List<String> idsUsuariosDestinatarios;
	private long idAutor;
	private String nombreExpediente;
	private int pagina;
	private int tamanoPagina;
	private String fechaAtencionS;
	private String fechaSolicitudS;
	private String idCMSCarpeta;
	private Long idCarpeta;

	public long getIdSolicitudCreacionExp() {
		return idSolicitudCreacionExp;
	}
	public void setIdSolicitudCreacionExp(long idSolicitudCreacionExp) {
		this.idSolicitudCreacionExp = idSolicitudCreacionExp;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Date getFechaAtencion() {
		return fechaAtencion;
	}
	public void setFechaAtencion(Date fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
		if (fechaAtencion!=null) {
			this.fechaAtencionS = FechaUtil.simpleDateFormatForm.format(fechaAtencion);
		}
	}
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
		if (fechaSolicitud!=null) {
			this.fechaSolicitudS = FechaUtil.simpleDateFormatForm.format(fechaSolicitud);
		}
	}
	public String getIdUsuarioCreadorExpediente() {
		return idUsuarioCreadorExpediente;
	}
	public void setIdUsuarioCreadorExpediente(String idUsuarioCreadorExpediente) {
		this.idUsuarioCreadorExpediente = idUsuarioCreadorExpediente;
	}
	public String getIdUsuarioDestinatario() {
		return idUsuarioDestinatario;
	}
	public void setIdUsuarioDestinatario(String idUsuarioDestinatario) {
		this.idUsuarioDestinatario = idUsuarioDestinatario;
	}
	public String getIdUsuarioSolicitante() {
		return idUsuarioSolicitante;
	}
	public void setIdUsuarioSolicitante(String idUsuarioSolicitante) {
		this.idUsuarioSolicitante = idUsuarioSolicitante;
	}
	public long getIdEstadoSolicitudCreacionExp() {
		return idEstadoSolicitudCreacionExp;
	}
	public void setIdEstadoSolicitudCreacionExp(long idEstadoSolicitudCreacionExp) {
		this.idEstadoSolicitudCreacionExp = idEstadoSolicitudCreacionExp;
	}
	public String getNombreEstadoSolicitudCreacionExp() {
		return nombreEstadoSolicitudCreacionExp;
	}
	public void setNombreEstadoSolicitudCreacionExp(String nombreEstadoSolicitudCreacionExp) {
		this.nombreEstadoSolicitudCreacionExp = nombreEstadoSolicitudCreacionExp;
	}
	public String getNombreSubProceso() {
		return nombreSubProceso;
	}
	public void setNombreSubProceso(String nombreSubProceso) {
		this.nombreSubProceso = nombreSubProceso;
	}
	public AutorDTO getAutorDTO() {
		return autorDTO;
	}
	public void setAutorDTO(AutorDTO autorDTO) {
		this.autorDTO = autorDTO;
	}
	public String getAsuntoMateria() {
		return asuntoMateria;
	}
	public void setAsuntoMateria(String asuntoMateria) {
		this.asuntoMateria = asuntoMateria;
	}
	public List<AutorDTO> getAutoresDTO() {
		return autoresDTO;
	}
	public void setAutoresDTO(List<AutorDTO> autoresDTO) {
		this.autoresDTO = autoresDTO;
	}
	public List<String> getIdsUsuariosDestinatarios() {
		return idsUsuariosDestinatarios;
	}
	public void setIdsUsuariosDestinatarios(List<String> idsUsuariosDestinatarios) {
		this.idsUsuariosDestinatarios = idsUsuariosDestinatarios;
	}
	public long getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(long idProceso) {
		this.idProceso = idProceso;
	}
	public long getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(long idAutor) {
		this.idAutor = idAutor;
	}
	public String getNombreExpediente() {
		return nombreExpediente;
	}
	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}
	public int getPagina() {
		return pagina;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	public int getTamanoPagina() {
		return tamanoPagina;
	}
	public void setTamanoPagina(int tamanoPagina) {
		this.tamanoPagina = tamanoPagina;
	}
	public String getFechaAtencionS() {
		return fechaAtencionS;
	}
	public void setFechaAtencionS(String fechaAtencionS) {
		this.fechaAtencionS = fechaAtencionS;
	}
	public String getFechaSolicitudS() {
		return fechaSolicitudS;
	}
	public void setFechaSolicitudS(String fechaSolicitudS) {
		this.fechaSolicitudS = fechaSolicitudS;
	}
	public void validaCreacionExp() throws SgdpException {
		if (this.idProceso==0) {
			throw new SgdpException("ERROR: se requiere indicar subproceso", Level.WARN, false);
		}
		if (this.idAutor==0) {
			throw new SgdpException("ERROR: se requiere indicar autor", Level.WARN, false);
		}
		if (this.asuntoMateria==null || this.asuntoMateria.isEmpty() ) {
			throw new SgdpException("ERROR: se requiere indicar asunto", Level.WARN, false);
		}
		if (this.idUsuarioDestinatario==null || this.idUsuarioDestinatario.isEmpty() ) {
			throw new SgdpException("ERROR: se requiere usuario destinatario", Level.WARN, false);
		}
	}
	public String getIdCMSCarpeta() {
		return idCMSCarpeta;
	}
	public void setIdCMSCarpeta(String idCMSCarpeta) {
		this.idCMSCarpeta = idCMSCarpeta;
	}
	public Long getIdCarpeta() {
		return idCarpeta;
	}
	public void setIdCarpeta(Long idCarpeta) {
		this.idCarpeta = idCarpeta;
	}
	@Override
	public String toString() {
		return "SolicitudCreacionExpDTO [idSolicitudCreacionExp=" + idSolicitudCreacionExp + ", comentario="
				+ comentario + ", fechaAtencion=" + fechaAtencion + ", fechaSolicitud=" + fechaSolicitud
				+ ", idUsuarioCreadorExpediente=" + idUsuarioCreadorExpediente
				+ ", idUsuarioDestinatario="+ idUsuarioDestinatario
				+ ", idUsuarioSolicitante=" + idUsuarioSolicitante
				+ ", idEstadoSolicitudCreacionExp=" + idEstadoSolicitudCreacionExp
				+ ", nombreEstadoSolicitudCreacionExp=" + nombreEstadoSolicitudCreacionExp
				+ ", idProceso=" + idProceso
				+ ", nombreSubProceso=" + nombreSubProceso
				+ ", asuntoMateria=" + asuntoMateria
				+ ", idAutor=" + idAutor
				+ ", nombreExpediente=" + nombreExpediente
				+ ", pagina=" + pagina
				+ ", tamanoPagina=" + tamanoPagina
				+ ", fechaAtencionS=" + fechaAtencionS
				+ ", fechaSolicitudS=" + fechaSolicitudS
				+ ", idCMSCarpeta=" + idCMSCarpeta
				+ ", idCarpeta=" + idCarpeta
				+ "]";
	}

}
