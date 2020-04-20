package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public class HistoricoDeDocumentos implements Serializable  {

	private Timestamp fecha;
	
	private String nombreTarea;
	private String idUsuarioQueAsigna;
	private String comentario;
	private String nombreDeTipoDeDocumento;
	
	private long idInstanciaDeTarea;
	
	private String idArchivo;	
	private boolean esVisable;
	private boolean aplicaFEA;
	private boolean aplicaFirmaApplet;
	private String idExpediente;
	private String nombre;
	private String mimeType;
	

	public HistoricoDeDocumentos() {
	}

	

	public Timestamp getFecha() {
		return fecha;
	}



	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}



	public String getNombreTarea() {
		return nombreTarea;
	}

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

	public String getIdUsuarioQueAsigna() {
		return idUsuarioQueAsigna;
	}

	public void setIdUsuarioQueAsigna(String idUsuarioQueAsigna) {
		this.idUsuarioQueAsigna = idUsuarioQueAsigna;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getNombreDeTipoDeDocumento() {
		return nombreDeTipoDeDocumento;
	}

	public void setNombreDeTipoDeDocumento(String nombreDeTipoDeDocumento) {
		this.nombreDeTipoDeDocumento = nombreDeTipoDeDocumento;
	}



	public long getIdInstanciaDeTarea() {
		return idInstanciaDeTarea;
	}



	public void setIdInstanciaDeTarea(long idInstanciaDeTarea) {
		this.idInstanciaDeTarea = idInstanciaDeTarea;
	}



	public String getIdArchivo() {
		return idArchivo;
	}



	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}



	public boolean isEsVisable() {
		return esVisable;
	}



	public void setEsVisable(boolean esVisable) {
		this.esVisable = esVisable;
	}



	public boolean isAplicaFEA() {
		return aplicaFEA;
	}



	public void setAplicaFEA(boolean aplicaFEA) {
		this.aplicaFEA = aplicaFEA;
	}



	public boolean isAplicaFirmaApplet() {
		return aplicaFirmaApplet;
	}



	public void setAplicaFirmaApplet(boolean aplicaFirmaApplet) {
		this.aplicaFirmaApplet = aplicaFirmaApplet;
	}



	public String getIdExpediente() {
		return idExpediente;
	}



	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getMimeType() {
		return mimeType;
	}



	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}





}
