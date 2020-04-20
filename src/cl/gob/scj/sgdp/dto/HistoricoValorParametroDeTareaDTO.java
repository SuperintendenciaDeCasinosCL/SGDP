package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class HistoricoValorParametroDeTareaDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7452718555422991196L;
	
	private Long idHistoricoValorParametroDeTarea;
	private String valor;
	private HistoricoDeInstDeTareaDTO historicoDeInstDeTareaDTO;
	private ParametroDeTareaDTO parametroDeTareaDTO;
	private String comentario;
	
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
	public HistoricoDeInstDeTareaDTO getHistoricoDeInstDeTareaDTO() {
		return historicoDeInstDeTareaDTO;
	}
	public void setHistoricoDeInstDeTareaDTO(HistoricoDeInstDeTareaDTO historicoDeInstDeTareaDTO) {
		this.historicoDeInstDeTareaDTO = historicoDeInstDeTareaDTO;
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
	@Override
	public String toString() {
		return "HistoricoValorParametroDeTareaDTO [idHistoricoValorParametroDeTarea=" + idHistoricoValorParametroDeTarea
				+ ", valor=" + valor + ", historicoDeInstDeTareaDTO=" + historicoDeInstDeTareaDTO
				+ ", parametroDeTareaDTO=" + parametroDeTareaDTO + ", comentario=" + comentario + "]";
	}
}
