package cl.gob.scj.sgdp.dto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.apache.log4j.Logger;

import cl.gob.scj.sgdp.model.DocumentoDeSalidaDeTarea;
import cl.gob.scj.sgdp.model.InstanciaDeTarea;
import cl.gob.scj.sgdp.model.UsuarioAsignado;
import cl.gob.scj.sgdp.util.FechaUtil;

public class InstanciaDeTareaDTO extends RespuestaDTO  implements Serializable{

	private static final long serialVersionUID = -6012363370954761418L;	
	
	private static final Logger log = Logger.getLogger(InstanciaDeTareaDTO.class);	
		
	private long idInstanciaDeTarea;
	private String nombreDeTarea;
	private String nombreDeProceso;
	private String origen;
	private String nombreExpediente;
	private Date fechaVencimientoInstanciaDeTarea;
	private List<String> idUsuariosAsignados;
	private Boolean tieneDocumentosEnCMS;
	private boolean puedeDevolver;
	private long orden;	
	private String idExpediente;
	private Date fechaDeInicio;
	private String iniciadorProceso;
	private String ultimoComentario;
	private boolean esUltimaTarea;
	private Date fechaVencimientoInstanciaDeProceso;
	private String usuariosAsignadosString;
	private Map<String, TipoDeDocumentoDTO> tiposDeDocumentosDeSalida;
	private String muestraDocumentosObligatoriosChecked;
	private String muestraTodosDocumentosChecked;
	private String tipoDeBifurcacion;
	public boolean adviertePlazo;
    public boolean fueraDePlazo;
	private boolean puedeVisarDocumentos;
	private boolean puedeAplicarFEA;
	private Date fechaVencimientoUsuario;
	private List<String> posiblesIdUsuarios;
	private String fechaVencimientoInstanciaDeTareaJavaScript;
	private String emisor;
	private Date fechaInicioInstanciaDeProceso;
	private String asunto;
	private String idUsuarioQueAsigna;
	private String primerUsuarioAsignado;
	private int cantidadDeMasUsuariosAsignados;
	private String nombreRolQueEjecuta;
	private boolean puedeAvanzarProceso;
	private int diasHabilesMaxDuracion;
	private boolean obligatoria;
	private boolean puedeAvanzarProcesoConAdvertenciaVisacion;
	private boolean puedeAvanzarProcesoConAdvertenciaFEA;
	private long idInstanciaDeProceso;
	private String urlControl;
	private String idDiagrama;
	private long idProceso;
	private long idEstadoTarea;
	private boolean asignaNumDoc;
	private boolean esperaRespuesta;
	private String nombreEstadoHomologadoDeInstProceso;
	private long idTarea;
	private boolean distribuye;
	private long idUltimaInstanciaDeTareaAnterior;
	private String nombreTareaUltimaInstanciaDeTareaAnterior;
	private String usuarioAnterior;
	private List<String> posiblesIdUsuariosFueraDeOficina;
	
	public InstanciaDeTareaDTO() {
		idUsuariosAsignados = new ArrayList<String>();
		posiblesIdUsuarios = new ArrayList<String>();
		posiblesIdUsuariosFueraDeOficina = new ArrayList<String>();
	}
	
	public long getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}

	public void setIdInstanciaDeTarea(long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
	}

	public String getNombreDeTarea() {
		return nombreDeTarea;
	}

	public void setNombreDeTarea(String nombreDeTarea) {
		this.nombreDeTarea = nombreDeTarea;
	}

	public String getNombreDeProceso() {
		return nombreDeProceso;
	}

	public void setNombreDeProceso(String nombreDeProceso) {
		this.nombreDeProceso = nombreDeProceso;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getNombreExpediente() {
		return nombreExpediente;
	}

	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}
	
	public List<String> getIdUsuariosAsignados() {
		return idUsuariosAsignados;
	}

	public void setIdUsuariosAsignados(List<String> idUsuariosAsignados) {
		this.idUsuariosAsignados = idUsuariosAsignados;
	}
	
	public void cargaIdUsuariosAsignados(List<UsuarioAsignado> usuariosAsignados) {
		for (UsuarioAsignado usuarioAsignado : usuariosAsignados) {
			idUsuariosAsignados.add(usuarioAsignado.getId().getIdUsuario());
		}
	}

	public Boolean getTieneDocumentosEnCMS() {
		return tieneDocumentosEnCMS;
	}

	public void setTieneDocumentosEnCMS(Boolean tieneDocumentosEnCMS) {
		this.tieneDocumentosEnCMS = tieneDocumentosEnCMS;
	}
	
	public boolean getPuedeDevolver() {
		return puedeDevolver;
	}

	public void setPuedeDevolver(boolean puedeDevolver) {
		this.puedeDevolver = puedeDevolver;
	}

	public long getOrden() {
		return orden;
	}

	public void setOrden(long orden) {
		this.orden = orden;				
	}
	
	public String getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}
	
	public Date getFechaDeInicio() {
		return fechaDeInicio;
	}

	public void setFechaDeInicio(Date fechaDeInicio) {
		this.fechaDeInicio = fechaDeInicio;
	}

	public String getIniciadorProceso() {
		return iniciadorProceso;
	}

	public void setIniciadorProceso(String iniciadorProceso) {
		this.iniciadorProceso = iniciadorProceso;
	}

	public String getUltimoComentario() {
		return ultimoComentario;
	}

	public void setUltimoComentario(String ultimoComentario) {
		this.ultimoComentario = ultimoComentario;
	}	
	
	public boolean isEsUltimaTarea() {
		return esUltimaTarea;
	}

	public void setEsUltimaTarea(boolean esUltimaTarea) {
		this.esUltimaTarea = esUltimaTarea;
	}	
	
	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public void setUsuariosAsignadosString(String usuariosAsignadosString) {
		this.usuariosAsignadosString = usuariosAsignadosString;
	}

	public Date getFechaVencimientoInstanciaDeTarea() {
		return fechaVencimientoInstanciaDeTarea;
	}

	public void setFechaVencimientoInstanciaDeTarea(
			Date fechaVencimientoInstanciaDeTarea) {
		this.fechaVencimientoInstanciaDeTarea = fechaVencimientoInstanciaDeTarea;
	}

	public Date getFechaVencimientoInstanciaDeProceso() {
		return fechaVencimientoInstanciaDeProceso;
	}

	public void setFechaVencimientoInstanciaDeProceso(
			Date fechaVencimientoInstanciaDeProceso) {
		this.fechaVencimientoInstanciaDeProceso = fechaVencimientoInstanciaDeProceso;
	}

	public String getUsuariosAsignadosString() {		
		return usuariosAsignadosString;
	}

	public Map<String, TipoDeDocumentoDTO> getTiposDeDocumentosDeSalida() {
		return tiposDeDocumentosDeSalida;
	}

	public void setTiposDeDocumentosDeSalida(
			Map<String, TipoDeDocumentoDTO> tiposDeDocumentosDeSalida) {
		this.tiposDeDocumentosDeSalida = tiposDeDocumentosDeSalida;
	}	

	public String getMuestraDocumentosObligatoriosChecked() {
		return muestraDocumentosObligatoriosChecked;
	}

	public void setMuestraDocumentosObligatoriosChecked(
			String muestraDocumentosObligatoriosChecked) {
		this.muestraDocumentosObligatoriosChecked = muestraDocumentosObligatoriosChecked;
	}

	public String getMuestraTodosDocumentosChecked() {
		return muestraTodosDocumentosChecked;
	}

	public void setMuestraTodosDocumentosChecked(
			String muestraTodosDocumentosChecked) {
		this.muestraTodosDocumentosChecked = muestraTodosDocumentosChecked;
	}

	public String getTipoDeBifurcacion() {
		return tipoDeBifurcacion;
	}

	public void setTipoDeBifurcacion(String tipoDeBifurcacion) {
		this.tipoDeBifurcacion = tipoDeBifurcacion;
	}
	
	public boolean getAdviertePlazo() {
		return adviertePlazo;
	}
	
	public void setAdviertePlazo(boolean adviertePlazo) {
		this.adviertePlazo = adviertePlazo;
	}
	
	public boolean getFueraDePlazo() {
		return fueraDePlazo;
	}

	public void setFueraDePlazo(boolean fueraDePlazo) {
		this.fueraDePlazo = fueraDePlazo;
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
	
	public Date getFechaVencimientoUsuario() {
		return fechaVencimientoUsuario;
	}

	public void setFechaVencimientoUsuario(Date fechaVencimientoUsuario) {
		this.fechaVencimientoUsuario = fechaVencimientoUsuario;
	}

	public List<String> getPosiblesIdUsuarios() {
		return posiblesIdUsuarios;
	}

	public void setPosiblesIdUsuarios(List<String> posiblesIdUsuarios) {
		this.posiblesIdUsuarios = posiblesIdUsuarios;
	}		

	public String getFechaVencimientoInstanciaDeTareaJavaScript() {
		return fechaVencimientoInstanciaDeTareaJavaScript;
	}

	public void setFechaVencimientoInstanciaDeTareaJavaScript(
			String fechaVencimientoInstanciaDeTareaJavaScript) {
		this.fechaVencimientoInstanciaDeTareaJavaScript = fechaVencimientoInstanciaDeTareaJavaScript;
	}	

	public Date getFechaInicioInstanciaDeProceso() {
		return fechaInicioInstanciaDeProceso;
	}

	public void setFechaInicioInstanciaDeProceso(Date fechaInicioInstanciaDeProceso) {
		this.fechaInicioInstanciaDeProceso = fechaInicioInstanciaDeProceso;
	}	
	
	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}	

	public String getIdUsuarioQueAsigna() {
		return idUsuarioQueAsigna;
	}

	public void setIdUsuarioQueAsigna(String idUsuarioQueAsigna) {
		this.idUsuarioQueAsigna = idUsuarioQueAsigna;

	}
	
	public String getPrimerUsuarioAsignado() {
		return primerUsuarioAsignado;
	}

	public void setPrimerUsuarioAsignado(String primerUsuarioAsignado) {
		this.primerUsuarioAsignado = primerUsuarioAsignado;
	}	

	public int getCantidadDeMasUsuariosAsignados() {
		return cantidadDeMasUsuariosAsignados;
	}

	public void setCantidadDeMasUsuariosAsignados(int cantidadDeMasUsuariosAsignados) {
		this.cantidadDeMasUsuariosAsignados = cantidadDeMasUsuariosAsignados;
	}
	
	public String getNombreRolQueEjecuta() {
		return nombreRolQueEjecuta;
	}

	public void setNombreRolQueEjecuta(String nombreRolQueEjecuta) {
		this.nombreRolQueEjecuta = nombreRolQueEjecuta;
	}	
	
	public boolean getPuedeAvanzarProceso() {
		return puedeAvanzarProceso;
	}

	public void setPuedeAvanzarProceso(boolean puedeAvanzarProceso) {
		this.puedeAvanzarProceso = puedeAvanzarProceso;
	}
	
	public int getDiasHabilesMaxDuracion() {
		return diasHabilesMaxDuracion;
	}
	
	public long getIdEstadoTarea() {
		return idEstadoTarea;
	}

	public void setIdEstadoTarea(long idEstadoTarea) {
		this.idEstadoTarea = idEstadoTarea;
	}

	public void setDiasHabilesMaxDuracion(int diasHabilesMaxDuracion) {
		this.diasHabilesMaxDuracion = diasHabilesMaxDuracion;
	}	
	
	public boolean isObligatoria() {
		return obligatoria;
	}

	public void setObligatoria(boolean obligatoria) {
		this.obligatoria = obligatoria;
	}
	
	public boolean isPuedeAvanzarProcesoConAdvertenciaVisacion() {
		return puedeAvanzarProcesoConAdvertenciaVisacion;
	}

	public void setPuedeAvanzarProcesoConAdvertenciaVisacion(boolean puedeAvanzarProcesoConAdvertenciaVisacion) {
		this.puedeAvanzarProcesoConAdvertenciaVisacion = puedeAvanzarProcesoConAdvertenciaVisacion;
	}

	public boolean isPuedeAvanzarProcesoConAdvertenciaFEA() {
		return puedeAvanzarProcesoConAdvertenciaFEA;
	}

	public void setPuedeAvanzarProcesoConAdvertenciaFEA(boolean puedeAvanzarProcesoConAdvertenciaFEA) {
		this.puedeAvanzarProcesoConAdvertenciaFEA = puedeAvanzarProcesoConAdvertenciaFEA;
	}	
	
	public long getIdInstanciaDeProceso() {
		return idInstanciaDeProceso;
	}

	public void setIdInstanciaDeProceso(long idInstanciaDeProceso) {
		this.idInstanciaDeProceso = idInstanciaDeProceso;
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

	public long getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(long idProceso) {
		this.idProceso = idProceso;
	}
	
	public boolean isAsignaNumDoc() {
		return asignaNumDoc;
	}

	public void setAsignaNumDoc(boolean asignaNumDoc) {
		this.asignaNumDoc = asignaNumDoc;
	}

	public boolean getEsperaRespuesta() {
		return esperaRespuesta;
	}

	public void setEsperaRespuesta(boolean esperaRespuesta) {
		this.esperaRespuesta = esperaRespuesta;
	}

	public String getNombreEstadoHomologadoDeInstProceso() {
		return nombreEstadoHomologadoDeInstProceso;
	}

	public void setNombreEstadoHomologadoDeInstProceso(String nombreEstadoHomologadoDeInstProceso) {
		this.nombreEstadoHomologadoDeInstProceso = nombreEstadoHomologadoDeInstProceso;
	}	

	public long getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(long idTarea) {
		this.idTarea = idTarea;
	}	

	public boolean isDistribuye() {
		return distribuye;
	}

	public void setDistribuye(boolean distribuye) {
		this.distribuye = distribuye;
	}
	
	public long getIdUltimaInstanciaDeTareaAnterior() {
		return idUltimaInstanciaDeTareaAnterior;
	}

	public void setIdUltimaInstanciaDeTareaAnterior(long idUltimaInstanciaDeTareaAnterior) {
		this.idUltimaInstanciaDeTareaAnterior = idUltimaInstanciaDeTareaAnterior;
	}

	public String getNombreTareaUltimaInstanciaDeTareaAnterior() {
		return nombreTareaUltimaInstanciaDeTareaAnterior;
	}

	public void setNombreTareaUltimaInstanciaDeTareaAnterior(String nombreTareaUltimaInstanciaDeTareaAnterior) {
		this.nombreTareaUltimaInstanciaDeTareaAnterior = nombreTareaUltimaInstanciaDeTareaAnterior;
	}

	public String getUsuarioAnterior() {
		return usuarioAnterior;
	}

	public void setUsuarioAnterior(String usuarioAnterior) {
		this.usuarioAnterior = usuarioAnterior;
	}
	
	public List<String> getPosiblesIdUsuariosFueraDeOficina() {
		return posiblesIdUsuariosFueraDeOficina;
	}

	public void setPosiblesIdUsuariosFueraDeOficina(List<String> posiblesIdUsuariosFueraDeOficina) {
		this.posiblesIdUsuariosFueraDeOficina = posiblesIdUsuariosFueraDeOficina;
	}

	public void cargaInstanciaDeTareaDTO(InstanciaDeTarea instanciaDeTarea ) {
		log.debug("Inicio cargaInstanciaDeTareaDTO");		
		if (instanciaDeTarea.getInstanciaDeProceso().getInstanciaDeProcesoPadre()!= null) {
			this.origen = instanciaDeTarea.getInstanciaDeProceso().getInstanciaDeProcesoPadre().getProceso().getUnidad().getCodigoUnidad();
		} 
		this.setIdInstanciaDeTarea(instanciaDeTarea.getIdInstanciaDeTarea());
		this.setNombreDeProceso(instanciaDeTarea.getTarea().getProceso().getNombreProceso());		
		this.setNombreDeTarea(instanciaDeTarea.getTarea().getNombreTarea());			
		this.setNombreExpediente(instanciaDeTarea.getInstanciaDeProceso().getNombreExpediente());
		this.setFechaVencimientoInstanciaDeTarea(instanciaDeTarea.getFechaVencimiento());
		this.setTieneDocumentosEnCMS(instanciaDeTarea.getInstanciaDeProceso().getTieneDocumentosEnCMS());
		this.setOrden(instanciaDeTarea.getTarea().getOrden());
		this.setIdExpediente(instanciaDeTarea.getInstanciaDeProceso().getIdExpediente());
		this.setFechaDeInicio(instanciaDeTarea.getFechaInicio());
		this.setIniciadorProceso(instanciaDeTarea.getInstanciaDeProceso().getIdUsuarioInicia());		
		this.setEsUltimaTarea(instanciaDeTarea.getTarea().getEsUltimaTarea());
		this.setFechaVencimientoInstanciaDeProceso(instanciaDeTarea.getInstanciaDeProceso().getFechaVencimiento());
		this.setTipoDeBifurcacion(instanciaDeTarea.getTarea().getTipoDeBifurcacion());
		this.setPuedeVisarDocumentos(instanciaDeTarea.getTarea().getPuedeVisarDocumentos());
		this.setPuedeAplicarFEA(instanciaDeTarea.getTarea().getPuedeAplicarFEA());
		this.cargaIdUsuariosAsignados(instanciaDeTarea.getUsuariosAsignados());	
		this.setFechaVencimientoUsuario(instanciaDeTarea.getFechaVencimientoUsuario());
		this.setEmisor(instanciaDeTarea.getInstanciaDeProceso().getEmisor());
		this.setFechaInicioInstanciaDeProceso(instanciaDeTarea.getInstanciaDeProceso().getFechaInicio());
		this.setAsunto(instanciaDeTarea.getInstanciaDeProceso().getAsunto());
		this.setIdUsuarioQueAsigna(instanciaDeTarea.getIdUsuarioQueAsigna());
		this.setObligatoria(instanciaDeTarea.getTarea().getObligatoria());
		//this.setNombreRolQueEjecuta(instanciaDeTarea.getTarea().getTareasRoles().get(0).getId().getRol().getNombreRol());
		this.setNombreRolQueEjecuta(instanciaDeTarea.getTarea().getResponsabilidadesTareas().get(0).getId().getResponsabilidad().getNombreResponsabilidad());
		this.setIdInstanciaDeProceso(instanciaDeTarea.getInstanciaDeProceso().getIdInstanciaDeProceso());
		this.setUrlControl(instanciaDeTarea.getTarea().getUrlControl());
		this.setIdDiagrama(instanciaDeTarea.getTarea().getIdDiagrama());
		this.setIdProceso(instanciaDeTarea.getInstanciaDeProceso().getProceso().getIdProceso());
		this.setIdEstadoTarea(instanciaDeTarea.getEstadoDeTarea().getIdEstadoDeTarea());
		this.setAsignaNumDoc(instanciaDeTarea.getTarea().getAsignaNumDoc());
		this.setEsperaRespuesta(instanciaDeTarea.getTarea().getEsperarResp());
		this.setIdTarea(instanciaDeTarea.getTarea().getIdTarea());
		this.distribuye = instanciaDeTarea.getTarea().getDistribuye() == null ? false : instanciaDeTarea.getTarea().getDistribuye().booleanValue();
		
		if (instanciaDeTarea.getFechaVencimiento()!=null) {
			try {
				this.fechaVencimientoInstanciaDeTareaJavaScript = FechaUtil.toFormat(instanciaDeTarea.getFechaVencimiento(), FechaUtil.simpleDateFormatShortDate);			
			} catch (ParseException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String exceptionAsString = sw.toString();
				log.error(exceptionAsString);
			}
		}
		List<DocumentoDeSalidaDeTarea> documentosDeSalidasDeTarea = instanciaDeTarea.getTarea().getDocumentosDeSalidasDeTarea();
		if (documentosDeSalidasDeTarea!=null && documentosDeSalidasDeTarea.size()>0) {
			tiposDeDocumentosDeSalida = new HashMap<String, TipoDeDocumentoDTO>();
			for (DocumentoDeSalidaDeTarea documentoDeSalidaDeTarea : documentosDeSalidasDeTarea) {
				TipoDeDocumentoDTO tipoDeDocumentoDTO = new TipoDeDocumentoDTO(documentoDeSalidaDeTarea.getId().getTipoDeDocumento().getIdTipoDeDocumento(), documentoDeSalidaDeTarea.getId().getTipoDeDocumento().getNombreDeTipoDeDocumento());
				tiposDeDocumentosDeSalida.put(documentoDeSalidaDeTarea.getId().getTipoDeDocumento().getNombreDeTipoDeDocumento(), tipoDeDocumentoDTO);
			}
		}
	}
	
	public void cargaUsuariosAsignadosString(String caracterSeparadorDeUsuarios) {
		
		log.debug("Inicio cargaUsuariosAsignadosString...");
		
		StringBuilder sb = new StringBuilder("");
		int contador = 1;
		cantidadDeMasUsuariosAsignados = 0;
		
		log.debug("idUsuariosAsignados.size(): " + idUsuariosAsignados.size());
		for (String idUsuarioAsignado : idUsuariosAsignados) {		
			log.debug("idUsuarioAsignado: " + idUsuarioAsignado);
			if (contador == 1) {
				this.primerUsuarioAsignado = idUsuarioAsignado;
			} else {
				sb.append(idUsuarioAsignado);
				cantidadDeMasUsuariosAsignados ++;
				if (contador < idUsuariosAsignados.size()) {
					sb.append(caracterSeparadorDeUsuarios);
				}
			}
			contador ++;
		}	
		
		setUsuariosAsignadosString(sb.toString());
	}
	
	public void cargaAdvertenciaDePlazo(int porcentajeAdvertenciaTarea, int diasHabilesMaxDuracion) throws IOException {
		
		log.debug("cargaAdvertenciaDePlazo inicio");
		log.debug("porcentajeAdvertenciaTarea: " + porcentajeAdvertenciaTarea);
		log.debug("diasHabilesMaxDuracion: " + diasHabilesMaxDuracion);
		
		int diasEntreFechas = 0;
		
		if (fechaVencimientoUsuario!=null && !fechaVencimientoUsuario.equals("")) {
			log.debug("fechaVencimientoUsuario!=null");
			diasEntreFechas = (int)FechaUtil.diasEntreFechas(fechaVencimientoUsuario, new Date());			
		} else if (fechaVencimientoInstanciaDeTarea!=null && !fechaVencimientoInstanciaDeTarea.equals("")) {
			log.debug("fechaVencimientoInstanciaDeTarea!=null");
			diasEntreFechas = (int)FechaUtil.diasEntreFechas(fechaVencimientoInstanciaDeTarea, new Date());			
		}
		
		if (fechaVencimientoUsuario!=null || fechaVencimientoInstanciaDeTarea!=null) {			
			
			log.debug("diasEntreFechas: " + diasEntreFechas);
			
			if (diasEntreFechas<0) {
				fueraDePlazo = true;	
				adviertePlazo = false;
				log.debug("diasEntreFechas<0");
				return;
			} else if (diasEntreFechas==0) {
				fueraDePlazo = false;	
				adviertePlazo = true;
				log.debug("diasEntreFechas=0");
				return;
			}
			
			log.debug("porcentajeAdvertenciaTarea: " + porcentajeAdvertenciaTarea);			
			
			log.debug("diasHabilesMaxDuracion: " + diasHabilesMaxDuracion);
			
			int diasLimiteAdvertencia = (int)((porcentajeAdvertenciaTarea * diasHabilesMaxDuracion) / 100.0f);
			log.debug("diasLimiteAdvertencia: " + diasLimiteAdvertencia);
			
			if (diasLimiteAdvertencia==0) {
				log.debug("diasLimiteAdvertencia==0");
				diasLimiteAdvertencia = 1;
			}	
			
			if (diasEntreFechas<=diasLimiteAdvertencia) {
				log.debug("diasEntreFechas<=diasLimiteAdvertencia");			
				adviertePlazo = true;
			}	
			
		} else {
			log.debug("fechaVencimientoUsuario==null || fechaVencimientoInstanciaDeTarea==null");
			fueraDePlazo = false;
			adviertePlazo = false;	
		}	
		
	}

	@Override
	public String toString() {
		return "InstanciaDeTareaDTO [idInstanciaDeTarea=" + idInstanciaDeTarea
				+ ", nombreDeTarea=" + nombreDeTarea + ", nombreDeProceso="
				+ nombreDeProceso + ", origen=" + origen
				+ ", nombreExpediente=" + nombreExpediente
				+ ", fechaVencimientoInstanciaDeTarea="
				+ fechaVencimientoInstanciaDeTarea + ", idUsuariosAsignados="
				+ idUsuariosAsignados + ", tieneDocumentosEnCMS="
				+ tieneDocumentosEnCMS + ", puedeDevolver=" + puedeDevolver
				+ ", orden=" + orden + ", idExpediente=" + idExpediente
				+ ", fechaDeInicio=" + fechaDeInicio + ", iniciadorProceso="
				+ iniciadorProceso + ", ultimoComentario=" + ultimoComentario
				+ ", esUltimaTarea=" + esUltimaTarea
				+ ", fechaVencimientoInstanciaDeProceso="
				+ fechaVencimientoInstanciaDeProceso
				+ ", usuariosAsignadosString=" + usuariosAsignadosString
				+ ", tiposDeDocumentosDeSalida=" + tiposDeDocumentosDeSalida
				+ ", muestraDocumentosObligatoriosChecked="
				+ muestraDocumentosObligatoriosChecked
				+ ", muestraTodosDocumentosChecked="
				+ muestraTodosDocumentosChecked + ", tipoDeBifurcacion="
				+ tipoDeBifurcacion + ", adviertePlazo=" + adviertePlazo
				+ ", fueraDePlazo=" + fueraDePlazo + ", puedeVisarDocumentos="
				+ puedeVisarDocumentos + ", puedeAplicarFEA=" + puedeAplicarFEA
				+ ", fechaVencimientoUsuario=" + fechaVencimientoUsuario
				+ ", posiblesIdUsuarios=" + posiblesIdUsuarios
				+ ", fechaVencimientoInstanciaDeTareaJavaScript="
				+ fechaVencimientoInstanciaDeTareaJavaScript + ", emisor="
				+ emisor + ", fechaInicioInstanciaDeProceso="
				+ fechaInicioInstanciaDeProceso + ", asunto=" + asunto
				+ ", idUsuarioQueAsigna=" + idUsuarioQueAsigna
				+ ", primerUsuarioAsignado=" + primerUsuarioAsignado
				+ ", cantidadDeMasUsuariosAsignados="
				+ cantidadDeMasUsuariosAsignados + ", nombreRolQueEjecuta="
				+ nombreRolQueEjecuta + ", puedeAvanzarProceso="
				+ puedeAvanzarProceso + ", diasHabilesMaxDuracion="
				+ diasHabilesMaxDuracion + ", obligatoria=" + obligatoria
				+ ", puedeAvanzarProcesoConAdvertenciaVisacion="
				+ puedeAvanzarProcesoConAdvertenciaVisacion
				+ ", puedeAvanzarProcesoConAdvertenciaFEA="
				+ puedeAvanzarProcesoConAdvertenciaFEA
				+ ", idInstanciaDeProceso=" + idInstanciaDeProceso
				+ ", urlControl=" + urlControl + ", idDiagrama=" + idDiagrama
				+ ", idProceso=" + idProceso + ", idEstadoTarea="
				+ idEstadoTarea 
				+ ", asignaNumDoc=" + asignaNumDoc
				+ ", distribuye=" + distribuye
				+ ", idUltimaInstanciaDeTareaAnterior=" + idUltimaInstanciaDeTareaAnterior
				+ ", nombreTareaUltimaInstanciaDeTareaAnterior=" + nombreTareaUltimaInstanciaDeTareaAnterior
				+ ", usuarioAnterior=" + usuarioAnterior				
				+ "]";
	}	
	
}