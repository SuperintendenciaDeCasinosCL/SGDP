package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

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
	private short horasOcupadas;
	private short minutosOcupados;
	private String correosHiden;
	private String correosApiDocJSON;
	private List<String> ruts;
	private String tipoDistribucion;
	private String direccionesDeCorreosApiDoc;
	private String nombreExpediente;
	private String direccionesDeCorreoParaDistribuir;
	private String nombreArchivosADistribuir;
	private String idEntidadDestinataria;
	private boolean condifencialDocdigital;


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
	public short getHorasOcupadas() {
		return horasOcupadas;
	}
	public void setHorasOcupadas(short horasOcupadas) {
		this.horasOcupadas = horasOcupadas;
	}
	public short getMinutosOcupados() {
		return minutosOcupados;
	}
	public void setMinutosOcupados(short minutosOcupados) {
		this.minutosOcupados = minutosOcupados;
	}
	public String getCorreosHiden() {
		return correosHiden;
	}
	public void setCorreosHiden(String correosHiden) {
		this.correosHiden = correosHiden;
	}
	public String getCorreosApiDocJSON() {
		return correosApiDocJSON;
	}
	public void setCorreosApiDocJSON(String correosApiDocJSON) {
		this.correosApiDocJSON = correosApiDocJSON;
	}
	public List<String> getRuts() {
		return ruts;
	}
	public void setRuts(List<String> ruts) {
		this.ruts = ruts;
	}
	public String getTipoDistribucion() {
		return tipoDistribucion;
	}
	public void setTipoDistribucion(String tipoDistribucion) {
		this.tipoDistribucion = tipoDistribucion;
	}
	public String getDireccionesDeCorreosApiDoc() {
		return direccionesDeCorreosApiDoc;
	}
	public void setDireccionesDeCorreosApiDoc(String direccionesDeCorreosApiDoc) {
		this.direccionesDeCorreosApiDoc = direccionesDeCorreosApiDoc;
	}
	public String getNombreExpediente() {
		return nombreExpediente;
	}
	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}
	public String getDireccionesDeCorreoParaDistribuir() {
		return direccionesDeCorreoParaDistribuir;
	}
	public void setDireccionesDeCorreoParaDistribuir(String direccionesDeCorreoParaDistribuir) {
		this.direccionesDeCorreoParaDistribuir = direccionesDeCorreoParaDistribuir;
	}
	public String getNombreArchivosADistribuir() {
		return nombreArchivosADistribuir;
	}
	public void setNombreArchivosADistribuir(String nombreArchivosADistribuir) {
		this.nombreArchivosADistribuir = nombreArchivosADistribuir;
	}

	public String getIdEntidadDestinataria() {
		return idEntidadDestinataria;
	}

	public void setIdEntidadDestinataria(String idEntidadDestinataria) {
		this.idEntidadDestinataria = idEntidadDestinataria;
	}

	public boolean isCondifencialDocdigital() {
		return condifencialDocdigital;
	}

	public void setCondifencialDocdigital(boolean condifencialDocdigital) {
		this.condifencialDocdigital = condifencialDocdigital;
	}


	@Override
	public String toString() {
		return "FinalizaProcesoDTO [idInstanciaDeTarea=" + idInstanciaDeTarea + ", comentario=" + comentario
				+ ", parametrosMapParaGuardarJSON=" + parametrosMapParaGuardarJSON + ", aTodos=" + aTodos
				+ ", correosDeDistribucionJSON=" + correosDeDistribucionJSON + ", idArchivosADistribuirJSON="
				+ idArchivosADistribuirJSON + ", asuntoCorreoDistribucion=" + asuntoCorreoDistribucion + ", recarga="
				+ recarga + ", respuestaFinalizaProceso=" + respuestaFinalizaProceso + ", cierra=" + cierra
				+ ", horasOcupadas=" + horasOcupadas + ", minutosOcupados=" + minutosOcupados + ", correosHiden="
				+ correosHiden 
				+ ", correosApiDocJSON=" + correosApiDocJSON 
				+ ", ruts=" + ruts
				+ ", tipoDistribucion=" + tipoDistribucion
				+ ", direccionesDeCorreosApiDoc=" + direccionesDeCorreosApiDoc
				+ ", nombreExpediente=" + nombreExpediente
				+ ", direccionesDeCorreoParaDistribuir=" + direccionesDeCorreoParaDistribuir
				+ ", nombreArchivosADistribuir=" + nombreArchivosADistribuir
				+ ", idEntidadDestinataria=" + idEntidadDestinataria
				+ ", condifencialDocdigital=" + condifencialDocdigital
				+ "]";
	}
	 
}
