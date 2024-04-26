package cl.gob.scj.sgdp.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_TAREAS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_TAREAS\"")
@NamedQueries({
	
	@NamedQuery(name="Tarea.findAll", query="SELECT t FROM Tarea t"),
	
	@NamedQuery(name="Tarea.getTareaPorIdSubProceso", query="SELECT p FROM Tarea p where p.proceso.idProceso =:idSubProceso "
			+ "	and p.vigente =:vigente and p.orden = 1 "),
	
	@NamedQuery(name="Tarea.getBuscaDocumentoOficialEnTarea",
		query="SELECT t FROM InstanciaDeTarea i "
			+ " inner join i.tarea t "
			+ " where i.idInstanciaDeTarea = :idInstanciaDeTarea "),
	
	@NamedQuery(name="Tarea.getTareaPorIdTarea",
	query="SELECT t FROM Tarea t "	
		+ " where t.idTarea = :idTarea "),
	
	@NamedQuery(name="Tarea.getTareasPorIdProceso", 
			query="SELECT p FROM Tarea p where p.proceso.idProceso =:idProceso "
					+ " order by p.nombreTarea asc "),
	
	@NamedQuery(name="Tarea.getTareasPorCodigoProceso", 
	query="SELECT t FROM Tarea t INNER JOIN t.proceso p WHERE p.codigoProceso =:codigoProceso AND p.vigente = true"),
	
	@NamedQuery(name="Tarea.getSegundasTareasPorIdProceso", 
	query="SELECT t FROM Tarea t INNER JOIN t.proceso p WHERE p.idProceso =:idProceso "
			+ "AND t.idTarea IN ( "
			+ " SELECT rt.tareaSiguiente.idTarea FROM ReferenciaDeTarea rt WHERE rt.tarea.orden = 1 AND rt.tarea.proceso.idProceso =:idProceso "		
			+ ") ")
	
})

public class Tarea implements Serializable, Comparable<Tarea> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_TAREA", sequenceName="\"SEQ_ID_TAREA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_TAREA")
	@Column(name="\"ID_TAREA\"")
	private long idTarea;

	@Column(name="\"A_DESCRIPCION_TAREA\"")
	private String descripcionTarea;

	@Column(name="\"A_NOMBRE_TAREA\"")
	private String nombreTarea;

	@Column(name="\"N_DIAS_HABILES_MAX_DURACION\"")
	private int diasHabilesMaxDuracion;

	@Column(name="\"N_ORDEN\"")
	private int orden;
	
	@Column(name="\"B_VIGENTE\"")
	private boolean vigente;

	@Column(name="\"B_SOLO_INFORMAR\"")
	private boolean soloInformar;
	
	@Column(name="\"B_OBLIGATORIA\"")
	private boolean obligatoria;
	
	@Column(name="\"B_ES_ULTIMA_TAREA\"")
	private boolean esUltimaTarea;
	
	@Column(name="\"A_TIPO_DE_BIFURCACION\"")
	private String tipoDeBifurcacion;
		
	@Column(name="\"B_PUEDE_VISAR_DOCUMENTOS\"")
	private boolean puedeVisarDocumentos;
	
	@Column(name="\"B_PUEDE_APLICAR_FEA\"")
	private boolean puedeAplicarFEA;
	
	@Column(name="\"B_ESPERAR_RESP\"")
	private Boolean esperarResp;
	
	@Column(name="\"B_CONFORMA_EXPEDIENTE\"")
	private Boolean conformaExpediente;
	
	@Column(name="\"B_DISTRIBUYE\"")
	private Boolean distribuye;
	
	@Column(name="\"B_NUMERACION_AUTO\"")
	private Boolean numAuto;
	

	//bi-directional many-to-one association to InstanciaDeTarea
	@JsonIgnore
	@OneToMany(mappedBy="tarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<InstanciaDeTarea> instanciasDeTareas;

	//bi-directional many-to-one association to Proceso
	@ManyToOne
	@JoinColumn(name="\"ID_PROCESO\"")
	private Proceso proceso;
	
	//bi-directional many-to-one association to Etapa
	@ManyToOne
	@JoinColumn(name="\"ID_ETAPA\"")
	private Etapa etapa;
	
	//bi-directional many-to-one association to Etapa
	/*@ManyToOne
	@JoinColumn(name="\"ID_ROL\"")
	private Rol rol;*/
	
	//bi-directional many-to-one association to DocumentoDeSalidaDeTarea
	@OneToMany(mappedBy="id.tarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<DocumentoDeSalidaDeTarea> documentosDeSalidasDeTarea;
	
	//bi-directional many-to-one association to ReferenciaDeTarea
	@OneToMany(mappedBy="tarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<ReferenciaDeTarea> referenciasDeTareas;

	//bi-directional many-to-one association to ReferenciaDeTarea
	@OneToMany(mappedBy="tareaSiguiente", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<ReferenciaDeTarea> referenciasDeTareasSiguientes;

	//bi-directional many-to-one association to ReferenciaDeTarea
	/*@OneToMany(mappedBy="tareaAnterior")
	private List<ReferenciaDeTarea> referenciasDeTareasAnteriores;*/

	//bi-directional many-to-one association to TareaRol
	@OneToMany(mappedBy="id.tarea")
	private List<TareaRol> tareasRoles;
	
	//bi-directional many-to-one association to ResponsabilidadTarea
	@OneToMany(mappedBy="id.tarea")
	private List<ResponsabilidadTarea> responsabilidadesTareas;
	
	@Column(name="\"A_URL_CONTROL\"")
	private String urlControl;
	
	@Column(name="\"ID_DIAGRAMA\"")
	private String idDiagrama;
	
	@Column(name = "\"B_ASIGNA_NUM_DOC\"")
	private boolean asignaNumDoc;
	
	@OneToMany(mappedBy="tarea", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	private List<UsuarioNotificacionTarea> usuarioNotificacionTarea;
	
	@Column(name="\"N_DIAS_RESETEO\"")
	private Integer diasReseteo;
	
	@Column(name="\"A_TIPO_RESETEO\"")
	private String tipoReseteo;
	
	@Column(name="\"A_URL_WS\"")
	private String urlWS;
	
	//bi-directional many-to-one association to ParametroRelacionTarea
	@OneToMany(mappedBy="id.tarea")
	private List<ParametroRelacionTarea> parametroRelacionTareas;
	
	public Tarea() {
	}

	public List<UsuarioNotificacionTarea> getUsuarioNotificacionTarea() {
		return usuarioNotificacionTarea;
	}

	public void setUsuarioNotificacionTarea(List<UsuarioNotificacionTarea> usuarioNotificacionTarea) {
		this.usuarioNotificacionTarea = usuarioNotificacionTarea;
	}
	

	public UsuarioNotificacionTarea addUsuarioNotificacionTarea(UsuarioNotificacionTarea usuarioNotificacionTarea) {
		getUsuarioNotificacionTarea().add(usuarioNotificacionTarea);
		usuarioNotificacionTarea.setTarea(this);

		return usuarioNotificacionTarea;
	}

	public UsuarioNotificacionTarea removeUsuarioNotificacionTarea(UsuarioNotificacionTarea usuarioNotificacionTarea) {
		getUsuarioNotificacionTarea().remove(usuarioNotificacionTarea);
		usuarioNotificacionTarea.setTarea(null);

		return usuarioNotificacionTarea;
	}	

	public long getIdTarea() {
		return this.idTarea;
	}

	public void setIdTarea(long idTarea) {
		this.idTarea = idTarea;
	}

	public String getDescripcionTarea() {
		return this.descripcionTarea;
	}

	public void setDescripcionTarea(String descripcionTarea) {
		this.descripcionTarea = descripcionTarea;
	}

	public String getNombreTarea() {
		return this.nombreTarea;
	}

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

	public int getDiasHabilesMaxDuracion() {
		return this.diasHabilesMaxDuracion;
	}

	public void setDiasHabilesMaxDuracion(int diasHabilesMaxDuracion) {
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public List<InstanciaDeTarea> getInstanciasDeTareas() {
		return this.instanciasDeTareas;
	}

	public void setInstanciasDeTareas(List<InstanciaDeTarea> instanciasDeTareas) {
		this.instanciasDeTareas = instanciasDeTareas;
	}

	public InstanciaDeTarea addInstanciasDeTarea(InstanciaDeTarea instanciasDeTarea) {
		getInstanciasDeTareas().add(instanciasDeTarea);
		instanciasDeTarea.setTarea(this);

		return instanciasDeTarea;
	}

	public InstanciaDeTarea removeInstanciasDeTarea(InstanciaDeTarea instanciasDeTarea) {
		getInstanciasDeTareas().remove(instanciasDeTarea);
		instanciasDeTarea.setTarea(null);

		return instanciasDeTarea;
	}

	public Proceso getProceso() {
		return this.proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public boolean getSoloInformar() {
		return soloInformar;
	}

	public void setSoloInformar(boolean soloInformar) {
		this.soloInformar = soloInformar;
	}

	public Etapa getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}	

	public String getTipoDeBifurcacion() {
		return tipoDeBifurcacion;
	}

	public void setTipoDeBifurcacion(String tipoDeBifurcacion) {
		this.tipoDeBifurcacion = tipoDeBifurcacion;
	}

	public List<DocumentoDeSalidaDeTarea> getDocumentosDeSalidasDeTarea() {
		return documentosDeSalidasDeTarea;
	}

	public void setDocumentosDeSalidasDeTarea(
			List<DocumentoDeSalidaDeTarea> documentosDeSalidasDeTarea) {
		this.documentosDeSalidasDeTarea = documentosDeSalidasDeTarea;
	}

	public DocumentoDeSalidaDeTarea addDocumentosDeSalidasDeTarea(DocumentoDeSalidaDeTarea documentosDeSalidasDeTarea) {
		getDocumentosDeSalidasDeTarea().add(documentosDeSalidasDeTarea);
		documentosDeSalidasDeTarea.getId().setTarea(this);

		return documentosDeSalidasDeTarea;
	}

	public DocumentoDeSalidaDeTarea removeDocumentosDeSalidasDeTarea(DocumentoDeSalidaDeTarea documentosDeSalidasDeTarea) {
		getDocumentosDeSalidasDeTarea().remove(documentosDeSalidasDeTarea);
		documentosDeSalidasDeTarea.getId().setTarea(null);

		return documentosDeSalidasDeTarea;
	}
	
	public List<ReferenciaDeTarea> getReferenciasDeTareas() {
		return this.referenciasDeTareas;
	}

	public void setReferenciasDeTareas(List<ReferenciaDeTarea> referenciasDeTareas) {
		this.referenciasDeTareas = referenciasDeTareas;
	}

	public ReferenciaDeTarea addReferenciasDeTarea(ReferenciaDeTarea referenciasDeTarea) {
		getReferenciasDeTareas().add(referenciasDeTarea);
		referenciasDeTarea.setTarea(this);

		return referenciasDeTarea;
	}

	public ReferenciaDeTarea removeReferenciasDeTarea(ReferenciaDeTarea referenciasDeTarea) {
		getReferenciasDeTareas().remove(referenciasDeTarea);
		referenciasDeTarea.setTarea(null);

		return referenciasDeTarea;
	}

	public List<ReferenciaDeTarea> getReferenciasDeTareasSiguientes() {
		return this.referenciasDeTareasSiguientes;
	}

	public void setReferenciasDeTareasSiguientes(List<ReferenciaDeTarea> referenciasDeTareasSiguientes) {
		this.referenciasDeTareasSiguientes = referenciasDeTareasSiguientes;
	}

	public ReferenciaDeTarea addReferenciasDeTareasSiguiente(ReferenciaDeTarea referenciasDeTareasSiguiente) {
		getReferenciasDeTareasSiguientes().add(referenciasDeTareasSiguiente);
		referenciasDeTareasSiguiente.setTareaSiguiente(this);

		return referenciasDeTareasSiguiente;
	}

	public ReferenciaDeTarea removeReferenciasDeTareasSiguiente(ReferenciaDeTarea referenciasDeTareasSiguiente) {
		getReferenciasDeTareasSiguientes().remove(referenciasDeTareasSiguiente);
		referenciasDeTareasSiguiente.setTareaSiguiente(null);

		return referenciasDeTareasSiguiente;
	}
	
	public boolean getObligatoria() {
		return obligatoria;
	}

	public void setObligatoria(boolean obligatoria) {
		this.obligatoria = obligatoria;
	}

	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}
	
	public List<TareaRol> getTareasRoles() {
		return this.tareasRoles;
	}

	public void setTareasRoles(List<TareaRol> tareasRoles) {
		this.tareasRoles = tareasRoles;
	}

	public TareaRol addTareasRole(TareaRol tareasRole) {
		getTareasRoles().add(tareasRole);
		tareasRole.getId().setTarea(this);

		return tareasRole;
	}

	public TareaRol removeTareasRole(TareaRol tareasRole) {
		getTareasRoles().remove(tareasRole);
		tareasRole.getId().setTarea(null);

		return tareasRole;
	}	

	public List<ResponsabilidadTarea> getResponsabilidadesTareas() {
		return responsabilidadesTareas;
	}

	public void setResponsabilidadesTareas(List<ResponsabilidadTarea> responsabilidadesTareas) {
		this.responsabilidadesTareas = responsabilidadesTareas;
	}
	
	public ResponsabilidadTarea addResponsabilidadesTareas(ResponsabilidadTarea responsabilidadTarea) {
		getResponsabilidadesTareas().add(responsabilidadTarea);
		responsabilidadTarea.getId().setTarea(this);

		return responsabilidadTarea;
	}

	public ResponsabilidadTarea removeResponsabilidadesTareas(ResponsabilidadTarea responsabilidadTarea) {
		getResponsabilidadesTareas().remove(responsabilidadTarea);
		responsabilidadTarea.getId().setTarea(null);

		return responsabilidadTarea;
	}

	public boolean getEsUltimaTarea() {
		return esUltimaTarea;
	}

	public void setEsUltimaTarea(boolean esUltimaTarea) {
		this.esUltimaTarea = esUltimaTarea;
	}
	
	public boolean getPuedeVisarDocumentos() {
		return puedeVisarDocumentos;
	}

	public void setPuedeVisarDocumentos(boolean puedeVisarDocumentos) {
		this.puedeVisarDocumentos = puedeVisarDocumentos;
	}

	public boolean getPuedeAplicarFEA() {
		return puedeAplicarFEA;
	}

	public void setPuedeAplicarFEA(boolean puedeAplicarFEA) {
		this.puedeAplicarFEA = puedeAplicarFEA;
	}	

	public String getUrlControl() {
		return urlControl;
	}

	public void setUrlControl(String urlControl) {
		this.urlControl = urlControl;
	}	

	public String getIdDiagrama() {
		return idDiagrama;
	}

	public void setIdDiagrama(String idDiagrama) {
		this.idDiagrama = idDiagrama;
	}	
	
	public boolean getAsignaNumDoc() {
		return asignaNumDoc;
	}

	public void setAsignaNumDoc(boolean asignaNumDoc) {
		this.asignaNumDoc = asignaNumDoc;
	}
		
	public Boolean getEsperarResp() {
		return esperarResp;
	}

	public void setEsperarResp(Boolean esperarResp) {
		this.esperarResp = esperarResp;
	}

	@Override
	public int compareTo(Tarea tarea) {
		return Comparators.ID.compare(this, tarea);
	}	
	
	public Boolean getConformaExpediente() {
		return conformaExpediente;
	}

	public void setConformaExpediente(Boolean conformaExpediente) {
		this.conformaExpediente = conformaExpediente;
	}

	public Integer getDiasReseteo() {
		return diasReseteo;
	}

	public void setDiasReseteo(Integer diasReseteo) {
		this.diasReseteo = diasReseteo;
	}

	public String getTipoReseteo() {
		return tipoReseteo;
	}

	public void setTipoReseteo(String tipoReseteo) {
		this.tipoReseteo = tipoReseteo;
	}

	public String getUrlWS() {
		return urlWS;
	}

	public void setUrlWS(String urlWS) {
		this.urlWS = urlWS;
	}

	public List<ParametroRelacionTarea> getParametroRelacionTareas() {
		return parametroRelacionTareas;
	}

	public void setParametroRelacionTareas(List<ParametroRelacionTarea> parametroRelacionTareas) {
		this.parametroRelacionTareas = parametroRelacionTareas;
	}
	
	public ParametroRelacionTarea addParametroRelacionTarea(ParametroRelacionTarea parametroRelacionTarea) {
		getParametroRelacionTareas().add(parametroRelacionTarea);
		parametroRelacionTarea.getId().setTarea(this);

		return parametroRelacionTarea;
	}

	public ParametroRelacionTarea removeParametroRelacionTarea(ParametroRelacionTarea parametroRelacionTarea) {
		getParametroRelacionTareas().remove(parametroRelacionTarea);
		parametroRelacionTarea.getId().setTarea(null);

		return parametroRelacionTarea;
	}	

	public Boolean getDistribuye() {
		return distribuye;
	}

	public void setDistribuye(Boolean distribuye) {
		this.distribuye = distribuye;
	}
	
	public Boolean getNumAuto() {
		return numAuto;
	}

	public void setNumAuto(Boolean numAuto) {
		this.numAuto = numAuto;
	}



	public static class Comparators {

        public static Comparator<Tarea> ID = new Comparator<Tarea>() {
            @Override
            public int compare(Tarea tarea1, Tarea tarea2) { 
                return new Long(tarea1.idTarea - tarea2.idTarea).intValue();
            }
        };
        public static Comparator<Tarea> ORDEN = new Comparator<Tarea>() {
            @Override
            public int compare(Tarea tarea1, Tarea tarea2) {
            	return tarea1.orden - tarea2.orden;
            }
        };  
        public static Comparator<Integer> ORDEN_INTEGER = new Comparator<Integer>() {
            @Override
            public int compare(Integer orden1, Integer orden2) {
            	return orden1 - orden2;
            }
        };  
        
    }		

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idTarea ^ (idTarea >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarea other = (Tarea) obj;
		if (idTarea != other.idTarea)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Tarea [idTarea=" + idTarea + ", descripcionTarea="
				+ descripcionTarea + ", nombreTarea=" + nombreTarea
				+ ", diasHabilesMaxDuracion=" + diasHabilesMaxDuracion
				+ ", orden=" + orden + ", vigente=" + vigente
				+ ", soloInformar=" + soloInformar + ", obligatoria="
				+ obligatoria + ", esUltimaTarea=" + esUltimaTarea
				+ ", tipoDeBifurcacion=" + tipoDeBifurcacion
				+ ", puedeVisarDocumentos=" + puedeVisarDocumentos
				+ ", puedeAplicarFEA=" + puedeAplicarFEA
				+ ", urlControl=" + urlControl 
				+ ", idDiagrama=" + idDiagrama
				+ ", diasReseteo=" + diasReseteo
				+ ", tipoReseteo=" + tipoReseteo
				+ ", urlWS=" + urlWS
				+ ", conformaExpediente=" + conformaExpediente
				+ ", distribuye=" + distribuye
				+ ", numAuto=" + numAuto				
				+ "]";
	}
	
}