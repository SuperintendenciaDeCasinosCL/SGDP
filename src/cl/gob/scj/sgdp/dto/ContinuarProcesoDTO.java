package cl.gob.scj.sgdp.dto;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ContinuarProcesoDTO implements Serializable {

	private static final long serialVersionUID = -5529127191199680637L;	

	private String comentario;
	private long idInstanciaDeTareaOrigen;
	private String avanzaRetrocede;
	private String nombreDeTarea;
	@JsonIgnore
	private List<MultipartFile> adjuntosContinuarProceso;
	private String idExpedienteContinuarProceso;
	private String asignacionesTareasJSON;
	@JsonIgnore
	private List<AsignacionTareaDTO> asignacionesTareasDTO;
	private boolean reasigna;
	private String usuarioSiguienteTareaRadio;
	private List<String> tareasUsuarios = new ArrayList<String>();
	private String respuestaMueveProceso;
	private String idUsuarioMueve;
	private boolean avanzaProcesoConAdvertenciaVisacion;
	private boolean avanzaProcesoConAdvertenciaFEA;
	private boolean recarga;
	private String parametrosMapParaGuardarJSON;
	private boolean aTodos;
	private String correosDeDistribucionJSON;
	private String idArchivosADistribuirJSON;
	private String asuntoCorreoDistribucion;
	private boolean reabre;
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



	public String getCorreosApiDocJSON() {
		return correosApiDocJSON;
	}
	public void setCorreosApiDocJSON(String correosApiDocJSON) {
		this.correosApiDocJSON = correosApiDocJSON;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public long getIdInstanciaDeTareaOrigen() {
		return idInstanciaDeTareaOrigen;
	}
	public void setIdInstanciaDeTareaOrigen(long idInstanciaDeTareaOrigen) {
		this.idInstanciaDeTareaOrigen = idInstanciaDeTareaOrigen;
	}	
	public String getAvanzaRetrocede() {
		return avanzaRetrocede;
	}
	public void setAvanzaRetrocede(String avanzaRetrocede) {
		this.avanzaRetrocede = avanzaRetrocede;
	}	
	public String getNombreDeTarea() {
		return nombreDeTarea;
	}
	public void setNombreDeTarea(String nombreDeTarea) {
		this.nombreDeTarea = nombreDeTarea;
	}	
	public List<MultipartFile> getAdjuntosContinuarProceso() {
		return adjuntosContinuarProceso;
	}
	public void setAdjuntosContinuarProceso(
			List<MultipartFile> adjuntosContinuarProceso) {
		this.adjuntosContinuarProceso = adjuntosContinuarProceso;
	}	
	public String getIdExpedienteContinuarProceso() {
		return idExpedienteContinuarProceso;
	}
	public void setIdExpedienteContinuarProceso(String idExpedienteContinuarProceso) {
		this.idExpedienteContinuarProceso = idExpedienteContinuarProceso;
	}	
	public String getUsuarioSiguienteTareaRadio() {
		return usuarioSiguienteTareaRadio;
	}
	public void setUsuarioSiguienteTareaRadio(String usuarioSiguienteTareaRadio) {
		this.usuarioSiguienteTareaRadio = usuarioSiguienteTareaRadio;
	}	
	public boolean getReasigna() {
		return reasigna;
	}
	public String getAsignacionesTareasJSON() {
		return asignacionesTareasJSON;
	}
	public void setAsignacionesTareasJSON(String asignacionesTareasJSON) throws JsonParseException, JsonMappingException, IOException {
		this.asignacionesTareasJSON = asignacionesTareasJSON;		
	}
	public List<AsignacionTareaDTO> getAsignacionesTareasDTO() {
		return asignacionesTareasDTO;
	}
	public void setAsignacionesTareasDTO(
			List<AsignacionTareaDTO> asignacionesTareasDTO) {
		this.asignacionesTareasDTO = asignacionesTareasDTO;
	}
	public void setReasigna(boolean reasigna) {
		this.reasigna = reasigna;
	}		
	public List<String> getTareasUsuarios() {
		return tareasUsuarios;
	}
	public void setTareasUsuarios(List<String> tareasUsuarios) {
		this.tareasUsuarios = tareasUsuarios;
	}	
	public String getRespuestaMueveProceso() {
		return respuestaMueveProceso;
	}
	public void setRespuestaMueveProceso(String respuestaMueveProceso) {
		this.respuestaMueveProceso = respuestaMueveProceso;
	}	
	public String getIdUsuarioMueve() {
		return idUsuarioMueve;
	}
	public void setIdUsuarioMueve(String idUsuarioMueve) {
		this.idUsuarioMueve = idUsuarioMueve;
	}
	public boolean isAvanzaProcesoConAdvertenciaVisacion() {
		return avanzaProcesoConAdvertenciaVisacion;
	}
	public void setAvanzaProcesoConAdvertenciaVisacion(boolean avanzaProcesoConAdvertenciaVisacion) {
		this.avanzaProcesoConAdvertenciaVisacion = avanzaProcesoConAdvertenciaVisacion;
	}
	public boolean isAvanzaProcesoConAdvertenciaFEA() {
		return avanzaProcesoConAdvertenciaFEA;
	}
	public void setAvanzaProcesoConAdvertenciaFEA(boolean avanzaProcesoConAdvertenciaFEA) {
		this.avanzaProcesoConAdvertenciaFEA = avanzaProcesoConAdvertenciaFEA;
	}	
	public boolean isRecarga() {
		return recarga;
	}
	public void setRecarga(boolean recarga) {
		this.recarga = recarga;
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
	public boolean getReabre() {
		return reabre;
	}
	public void setReabre(boolean reabre) {
		this.reabre = reabre;
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
		return "ContinuarProcesoDTO [comentario=" + comentario + ", idInstanciaDeTareaOrigen="
				+ idInstanciaDeTareaOrigen + ", avanzaRetrocede=" + avanzaRetrocede + ", nombreDeTarea=" + nombreDeTarea
				+ ", adjuntosContinuarProceso=" + adjuntosContinuarProceso + ", idExpedienteContinuarProceso="
				+ idExpedienteContinuarProceso + ", asignacionesTareasJSON=" + asignacionesTareasJSON
				+ ", asignacionesTareasDTO=" + asignacionesTareasDTO + ", reasigna=" + reasigna
				+ ", usuarioSiguienteTareaRadio=" + usuarioSiguienteTareaRadio + ", tareasUsuarios=" + tareasUsuarios
				+ ", respuestaMueveProceso=" + respuestaMueveProceso + ", idUsuarioMueve=" + idUsuarioMueve
				+ ", avanzaProcesoConAdvertenciaVisacion=" + avanzaProcesoConAdvertenciaVisacion
				+ ", avanzaProcesoConAdvertenciaFEA=" + avanzaProcesoConAdvertenciaFEA + ", recarga=" + recarga
				+ ", parametrosMapParaGuardarJSON=" + parametrosMapParaGuardarJSON + ", aTodos=" + aTodos
				+ ", correosDeDistribucionJSON=" + correosDeDistribucionJSON + ", idArchivosADistribuirJSON="
				+ idArchivosADistribuirJSON + ", asuntoCorreoDistribucion=" + asuntoCorreoDistribucion + ", reabre="
				+ reabre + ", horasOcupadas=" + horasOcupadas + ", minutosOcupados=" + minutosOcupados
				+ ", correosHiden=" + correosHiden 
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
