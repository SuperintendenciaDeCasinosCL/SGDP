package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoricoDeInstDeTareaDTO implements Serializable {

	private Long idHistoricoDeInstDeTarea;
	private Date fechaMovimiento;
	private String idUsuarioOrigen;
	private String comentario;
	private long idAccionHistoricoInstDeTarea;
	private String nombreAccion;
	private long idInstanciaDeTareaDeOrigen;
	private String nombreTareaDeOrigen;
	private long idInstanciaDeTareaDeDestino;
	private String nombreTareaDeDestino;
	private List<String> usuariosDestino;	
	private String usuariosDestinoString;
	private Boolean tieneHistoricoValorParametroDeTarea;
	private String nombreExpediente;
	private String nombreResponsabilidadOrigen;
	private String nombreResponsabilidadDestino;
	private Short horasOcupadas;
	private Short minutosOcupados;
	private Integer cantAccionesEnBitacora = 0;
	
	public HistoricoDeInstDeTareaDTO() {
		super();
		usuariosDestino = new ArrayList<String>();
	}
	public Long getIdHistoricoDeInstDeTarea() {
		return idHistoricoDeInstDeTarea;
	}
	public void setIdHistoricoDeInstDeTarea(Long idHistoricoDeInstDeTarea) {
		this.idHistoricoDeInstDeTarea = idHistoricoDeInstDeTarea;
	}
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	public String getIdUsuarioOrigen() {
		return idUsuarioOrigen;
	}
	public void setIdUsuarioOrigen(String idUsuarioOrigen) {
		this.idUsuarioOrigen = idUsuarioOrigen;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public long getIdAccionHistoricoInstDeTarea() {
		return idAccionHistoricoInstDeTarea;
	}
	public void setIdAccionHistoricoInstDeTarea(long idAccionHistoricoInstDeTarea) {
		this.idAccionHistoricoInstDeTarea = idAccionHistoricoInstDeTarea;
	}
	public String getNombreAccion() {
		return nombreAccion;
	}
	public void setNombreAccion(String nombreAccion) {
		this.nombreAccion = nombreAccion;
	}
	public long getIdInstanciaDeTareaDeOrigen() {
		return idInstanciaDeTareaDeOrigen;
	}
	public void setIdInstanciaDeTareaDeOrigen(long idInstanciaDeTareaDeOrigen) {
		this.idInstanciaDeTareaDeOrigen = idInstanciaDeTareaDeOrigen;
	}
	public String getNombreTareaDeOrigen() {
		return nombreTareaDeOrigen;
	}
	public void setNombreTareaDeOrigen(String nombreTareaDeOrigen) {
		this.nombreTareaDeOrigen = nombreTareaDeOrigen;
	}
	public long getIdInstanciaDeTareaDeDestino() {
		return idInstanciaDeTareaDeDestino;
	}
	public void setIdInstanciaDeTareaDeDestino(long idInstanciaDeTareaDeDestino) {
		this.idInstanciaDeTareaDeDestino = idInstanciaDeTareaDeDestino;
	}
	public String getNombreTareaDeDestino() {
		return nombreTareaDeDestino;
	}
	public void setNombreTareaDeDestino(String nombreTareaDeDestino) {
		this.nombreTareaDeDestino = nombreTareaDeDestino;
	}
	public List<String> getUsuariosDestino() {
		return usuariosDestino;
	}
	public void setUsuariosDestino(List<String> usuariosDestino) {
		this.usuariosDestino = usuariosDestino;
	}		
	public String getUsuariosDestinoString() {
		return usuariosDestinoString;
	}
	public void setUsuariosDestinoString(String usuariosDestinoString) {
		this.usuariosDestinoString = usuariosDestinoString;
	}	
	public Boolean getTieneHistoricoValorParametroDeTarea() {
		return tieneHistoricoValorParametroDeTarea;
	}
	public void setTieneHistoricoValorParametroDeTarea(Boolean tieneHistoricoValorParametroDeTarea) {
		this.tieneHistoricoValorParametroDeTarea = tieneHistoricoValorParametroDeTarea;
	}	
	public String getNombreExpediente() {
		return nombreExpediente;
	}
	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}	
	public String getNombreResponsabilidadOrigen() {
		return nombreResponsabilidadOrigen;
	}
	public void setNombreResponsabilidadOrigen(String nombreResponsabilidadOrigen) {
		this.nombreResponsabilidadOrigen = nombreResponsabilidadOrigen;
	}	
	public String getNombreResponsabilidadDestino() {
		return nombreResponsabilidadDestino;
	}
	public void setNombreResponsabilidadDestino(String nombreResponsabilidadDestino) {
		this.nombreResponsabilidadDestino = nombreResponsabilidadDestino;
	}	
	public Short getHorasOcupadas() {
		return horasOcupadas;
	}
	public void setHorasOcupadas(Short horasOcupadas) {
		this.horasOcupadas = horasOcupadas;
	}
	public Short getMinutosOcupados() {
		return minutosOcupados;
	}
	public void setMinutosOcupados(Short minutosOcupados) {
		this.minutosOcupados = minutosOcupados;
	}
	public void cargaUsuariosDestinoString(String caracterSeparadorDeUsuarios) {	
		
		StringBuilder sb = new StringBuilder("");
		int contador = 1;
	
		for (String usuarioDestino : usuariosDestino) {		
			sb.append(usuarioDestino);
			if (contador < usuariosDestino.size()) {
				sb.append(caracterSeparadorDeUsuarios);
			}
			contador ++;
		}	
		
		setUsuariosDestinoString(sb.toString());
	}
	@Override
	public String toString() {
		return "HistoricoDeInstDeTareaDTO [idHistoricoDeInstDeTarea="
				+ idHistoricoDeInstDeTarea + ", fechaMovimiento="
				+ fechaMovimiento + ", idUsuarioOrigen=" + idUsuarioOrigen
				+ ", comentario=" + comentario
				+ ", idAccionHistoricoInstDeTarea="
				+ idAccionHistoricoInstDeTarea + ", nombreAccion="
				+ nombreAccion + ", idInstanciaDeTareaDeOrigen="
				+ idInstanciaDeTareaDeOrigen + ", nombreTareaDeOrigen="
				+ nombreTareaDeOrigen + ", idInstanciaDeTareaDeDestino="
				+ idInstanciaDeTareaDeDestino + ", nombreTareaDeDestino="
				+ nombreTareaDeDestino + ", usuariosDestino=" + usuariosDestino
				+ ", usuariosDestinoString=" + usuariosDestinoString
				+ ", tieneHistoricoValorParametroDeTarea=" + tieneHistoricoValorParametroDeTarea
				+ ", nombreExpediente=" + nombreExpediente
				+ ", nombreResponsabilidadOrigen=" + nombreResponsabilidadOrigen
				+ ", nombreResponsabilidadDestino=" + nombreResponsabilidadDestino
				+ ", horasOcupadas=" + horasOcupadas
				+ ", minutosOcupados=" + minutosOcupados
				+ "]";
	}
	public Integer getCantAccionesEnBitacora() {
		return cantAccionesEnBitacora;
	}
	public void setCantAccionesEnBitacora(Integer cantAccionesEnBitacora) {
		this.cantAccionesEnBitacora = cantAccionesEnBitacora;
	}	
	
}
