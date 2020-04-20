package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class FinalizaProcesoDTO implements Serializable {
	
	private static final long serialVersionUID = -5545612719199689637L;

	private long idInstanciaDeTarea;
	private String comentario;
	private String parametrosMapParaGuardarJSON;
	private boolean aTodos;
	private String correosDeDistribucionJSON;
	private String idArchivosADistribuirJSON;
	private String asuntoCorreoDistribucion;
	private boolean recarga;
	private String respuestaFinalizaProceso;
	private boolean cierra;
	
	public long getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}
	public void setIdInstanciaDeTarea(long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}	
	public String getParametrosMapParaGuardarJSON() {
		return parametrosMapParaGuardarJSON;
	}
	public void setParametrosMapParaGuardarJSON(String parametrosMapParaGuardarJSON) {
		this.parametrosMapParaGuardarJSON = parametrosMapParaGuardarJSON;
	}	
	public boolean isaTodos() {
		return aTodos;
	}
	public void setaTodos(boolean aTodos) {
		this.aTodos = aTodos;
	}
	public String getCorreosDeDistribucionJSON() {
		return correosDeDistribucionJSON;
	}
	public void setCorreosDeDistribucionJSON(String correosDeDistribucionJSON) {
		this.correosDeDistribucionJSON = correosDeDistribucionJSON;
	}
	public String getIdArchivosADistribuirJSON() {
		return idArchivosADistribuirJSON;
	}
	public void setIdArchivosADistribuirJSON(String idArchivosADistribuirJSON) {
		this.idArchivosADistribuirJSON = idArchivosADistribuirJSON;
	}	
	public String getAsuntoCorreoDistribucion() {
		return asuntoCorreoDistribucion;
	}
	public void setAsuntoCorreoDistribucion(String asuntoCorreoDistribucion) {
		this.asuntoCorreoDistribucion = asuntoCorreoDistribucion;
	}	
	public boolean isRecarga() {
		return recarga;
	}
	public void setRecarga(boolean recarga) {
		this.recarga = recarga;
	}
	public String getRespuestaFinalizaProceso() {
		return respuestaFinalizaProceso;
	}
	public void setRespuestaFinalizaProceso(String respuestaFinalizaProceso) {
		this.respuestaFinalizaProceso = respuestaFinalizaProceso;
	}	
	public boolean getCierra() {
		return cierra;
	}
	public void setCierra(boolean cierra) {
		this.cierra = cierra;
	}
	@Override
	public String toString() {
		return "FinalizaProcesoDTO [idInstanciaDeTarea=" + idInstanciaDeTarea
				+ ", comentario=" + comentario
				+ ", parametrosMapParaGuardarJSON=" + parametrosMapParaGuardarJSON
				+ ", aTodos=" + aTodos 
				+ ", correosDeDistribucionJSON=" + correosDeDistribucionJSON 
				+ ", idArchivosADistribuirJSON=" + idArchivosADistribuirJSON
				+ ", asuntoCorreoDistribucion=" + asuntoCorreoDistribucion
				+ ", recarga=" + recarga 
				+ ", respuestaFinalizaProceso=" + respuestaFinalizaProceso
				+ ", cierra=" + cierra
				+ "]";
	}	
}
