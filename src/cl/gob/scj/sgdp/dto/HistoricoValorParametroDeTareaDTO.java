package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.Date;

public class HistoricoValorParametroDeTareaDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7452718555422991196L;
	
	private Long idHistoricoValorParametroDeTarea;
	private String valor;
	//private HistoricoDeInstDeTareaDTO historicoDeInstDeTareaDTO;
	private ParametroDeTareaDTO parametroDeTareaDTO;
	private String comentario;
	private InstanciaDeTareaDTO instanciaDeTareaDTO;
	private Date fecha;
	private String idUsuario;
	private String nombreResponsabilidad;
	
	public Long getIdHistoricoValorParametroDeTarea() {
		return idHistoricoValorParametroDeTarea;
	}
	public void setIdHistoricoValorParametroDeTarea(Long idHistoricoValorParametroDeTarea) {
		this.idHistoricoValorParametroDeTarea = idHistoricoValorParametroDeTarea;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}	
	public ParametroDeTareaDTO getParametroDeTareaDTO() {
		return parametroDeTareaDTO;
	}
	public void setParametroDeTareaDTO(ParametroDeTareaDTO parametroDeTareaDTO) {
		this.parametroDeTareaDTO = parametroDeTareaDTO;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}	
	public InstanciaDeTareaDTO getInstanciaDeTareaDTO() {
		return instanciaDeTareaDTO;
	}
	public void setInstanciaDeTareaDTO(InstanciaDeTareaDTO instanciaDeTareaDTO) {
		this.instanciaDeTareaDTO = instanciaDeTareaDTO;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}	
	public String getNombreResponsabilidad() {
		return nombreResponsabilidad;
	}
	public void setNombreResponsabilidad(String nombreResponsabilidad) {
		this.nombreResponsabilidad = nombreResponsabilidad;
	}
	@Override
	public String toString() {
		return "HistoricoValorParametroDeTareaDTO [idHistoricoValorParametroDeTarea=" + idHistoricoValorParametroDeTarea
				+ ", valor=" + valor + ", instanciaDeTareaDTO=" + instanciaDeTareaDTO
				+ ", parametroDeTareaDTO=" + parametroDeTareaDTO 
				+ ", comentario=" + comentario
				+ ", idUsuario=" + idUsuario
				+ ", nombreResponsabilidad=" + nombreResponsabilidad			
				+ "]";
	}
}
