package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

public class TareaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8597587058129207194L;

	private Long idTarea;
	private String descripcionTarea;
	private String nombreTarea;
	private Integer diasHabilesMaxDuracion;
	private Integer orden;
	private Boolean vigente;
	private String idDiagrama;
	private Boolean visa;
	private Boolean fea;
	private Boolean numOc;
	private Boolean esperarRespuesta;
	private Boolean soloInformar;
	private Long idEtapa;
	private Boolean obligatoria;
	private Boolean ulitimaTarea;
	private String tipoBifurcacion;
	private String tipoReseteo;
	private String codigotipodoc;
	private Integer numeroDiasReseteo;
	private Boolean conformaExpediente;
	private Boolean distribuye;
	private Boolean numeracionAuto;
	private List<String> tareaSiguiente;
	private List<String> roles;
	private List<String> docs;
	private SalidaNoConformeDTO salidaNoConforme;
	private List<ParametroDeTareaDTO> parametros;

	public TareaDTO() {

	}
	
	

	public TareaDTO(Long idTarea, String descripcionTarea, String nombreTarea, Integer diasHabilesMaxDuracion,
			Integer orden, Boolean vigente, String idDiagrama, Boolean visa, Boolean fea, Boolean numOc,
			Boolean esperarRespuesta, Boolean soloInformar, Long idEtapa, Boolean obligatoria, Boolean ulitimaTarea,
			String tipoBifurcacion, String tipoReseteo, Integer numeroDiasReseteo, Boolean conformaExpediente,
			Boolean distribuye, Boolean numeracionAuto, List<String> tareaSiguiente, List<String> roles,
			List<String> docs) {
		super();
		this.idTarea = idTarea;
		this.descripcionTarea = descripcionTarea;
		this.nombreTarea = nombreTarea;
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
		this.orden = orden;
		this.vigente = vigente;
		this.idDiagrama = idDiagrama;
		this.visa = visa;
		this.fea = fea;
		this.numOc = numOc;
		this.esperarRespuesta = esperarRespuesta;
		this.soloInformar = soloInformar;
		this.idEtapa = idEtapa;
		this.obligatoria = obligatoria;
		this.ulitimaTarea = ulitimaTarea;
		this.tipoBifurcacion = tipoBifurcacion;
		this.tipoReseteo = tipoReseteo;
		this.numeroDiasReseteo = numeroDiasReseteo;
		this.conformaExpediente = conformaExpediente;
		this.distribuye = distribuye;
		this.numeracionAuto = numeracionAuto;
		this.tareaSiguiente = tareaSiguiente;
		this.roles = roles;
		this.docs = docs;
	}



	public TareaDTO(Long idTarea, String descripcionTarea, String nombreTarea, Integer diasHabilesMaxDuracion,
			Integer orden, Boolean vigente, String idDiagrama) {
		super();
		this.idTarea = idTarea;
		this.descripcionTarea = descripcionTarea;
		this.nombreTarea = nombreTarea;
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
		this.orden = orden;
		this.vigente = vigente;
		this.idDiagrama = idDiagrama;
	}

	public Long getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	public String getDescripcionTarea() {
		return descripcionTarea;
	}

	public void setDescripcionTarea(String descripcionTarea) {
		this.descripcionTarea = descripcionTarea;
	}

	public String getNombreTarea() {
		return nombreTarea;
	}

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

	public Integer getDiasHabilesMaxDuracion() {
		return diasHabilesMaxDuracion;
	}

	public void setDiasHabilesMaxDuracion(Integer diasHabilesMaxDuracion) {
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public String getIdDiagrama() {
		return idDiagrama;
	}

	public void setIdDiagrama(String idDiagrama) {
		this.idDiagrama = idDiagrama;
	}

	public Boolean getVisa() {
		return visa;
	}

	public void setVisa(Boolean visa) {
		this.visa = visa;
	}

	public Boolean getFea() {
		return fea;
	}

	public void setFea(Boolean fea) {
		this.fea = fea;
	}

	public Boolean getSoloInformar() {
		return soloInformar;
	}

	public void setSoloInformar(Boolean soloInformar) {
		this.soloInformar = soloInformar;
	}

	public Long getIdEtapa() {
		return idEtapa;
	}

	public void setIdEtapa(Long idEtapa) {
		this.idEtapa = idEtapa;
	}

	public Boolean getObligatoria() {
		return obligatoria;
	}

	public void setObligatoria(Boolean obligatoria) {
		this.obligatoria = obligatoria;
	}

	@Override
	public String toString() {
		return "TareaDTO [idTarea=" + idTarea + ", descripcionTarea=" + descripcionTarea + ", nombreTarea="
				+ nombreTarea + ", diasHabilesMaxDuracion=" + diasHabilesMaxDuracion + ", orden=" + orden + ", vigente="
				+ vigente + ", idDiagrama=" + idDiagrama + "]";
	}

	public Boolean getUlitimaTarea() {
		return ulitimaTarea;
	}

	public void setUlitimaTarea(Boolean ulitimaTarea) {
		this.ulitimaTarea = ulitimaTarea;
	}

	public String getTipoBifurcacion() {
		return tipoBifurcacion;
	}

	public void setTipoBifurcacion(String tipoBifurcacion) {
		this.tipoBifurcacion = tipoBifurcacion;
	}

	public Boolean getNumOc() {
		return numOc;
	}

	public void setNumOc(Boolean numOc) {
		this.numOc = numOc;
	}

	public Boolean getEsperarRespuesta() {
		return esperarRespuesta;
	}

	public void setEsperarRespuesta(Boolean esperarRespuesta) {
		this.esperarRespuesta = esperarRespuesta;
	}

	public String getTipoReseteo() {
		return tipoReseteo;
	}

	public void setTipoReseteo(String tipoReseteo) {
		this.tipoReseteo = tipoReseteo;
	}

	public Integer getNumeroDiasReseteo() {
		return numeroDiasReseteo;
	}

	public void setNumeroDiasReseteo(Integer numeroDiasReseteo) {
		this.numeroDiasReseteo = numeroDiasReseteo;
	}

	public Boolean getConformaExpediente() {
		return conformaExpediente;
	}

	public void setConformaExpediente(Boolean conformaExpediente) {
		this.conformaExpediente = conformaExpediente;
	}

	public Boolean getDistribuye() {
		return distribuye;
	}

	public void setDistribuye(Boolean distribuye) {
		this.distribuye = distribuye;
	}

	public Boolean getNumeracionAuto() {
		return numeracionAuto;
	}

	public void setNumeracionAuto(Boolean numeracionAuto) {
		this.numeracionAuto = numeracionAuto;
	}

	public List<String> getTareaSiguiente() {
		return tareaSiguiente;
	}

	public void setTareaSiguiente(List<String> tareaSiguiente) {
		this.tareaSiguiente = tareaSiguiente;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getDocs() {
		return docs;
	}

	public void setDocs(List<String> docs) {
		this.docs = docs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public SalidaNoConformeDTO getSalidaNoConforme() {
		return salidaNoConforme;
	}



	public void setSalidaNoConforme(SalidaNoConformeDTO salidaNoConforme) {
		this.salidaNoConforme = salidaNoConforme;
	}



	public String getCodigotipodoc() {
		return codigotipodoc;
	}



	public void setCodigotipodoc(String codigotipodoc) {
		this.codigotipodoc = codigotipodoc;
	}



	public List<ParametroDeTareaDTO> getParametros() {
		return parametros;
	}



	public void setParametros(List<ParametroDeTareaDTO> parametros) {
		this.parametros = parametros;
	}
	
	
}
