package cl.gob.scj.sgdp.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import cl.gob.scj.sgdp.config.Constantes;


/**
 * The persistent class for the "SGDP_UNIDADES_OPERATIVAS" database table.
 * 
 */
@Entity
@Table(name="\"SGDP_UNIDADES_OPERATIVAS\"")
@NamedQuery(name="UnidadOperativa.findAll", query="SELECT s FROM UnidadOperativa s")
public class UnidadOperativa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ID_UNIDAD_OPERATIVA", sequenceName="\"SEQ_ID_UNIDAD_OPERATIVA\"", allocationSize=Constantes.ALLOCATION_SIZE)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID_UNIDAD_OPERATIVA")
	@Column(name="\"ID_UNIDAD_OPERATIVA\"")
	private Long idUnidadOperativa;

	@Column(name="\"A_CODIGO_UNIDAD_OPERATIVA\"")
	private String aCodigoUnidadOperativa;

	@Column(name="\"A_NOMBRE_COMPLETO_UNIDAD_OPERATIVA\"")
	private String aNombreCompletoUnidadOperativa;
	
	//bi-directional many-to-one association to SolicitudCreacionExp
	@OneToMany(mappedBy="unidadOperativa")
	private List<SolicitudCreacionExp> solicitudesDeCreacionExp;
	
	@Column(name="\"A_LOGIN_HEADER_MULTIOFICINA_B64\"")
	private String loginHeaderMultiOficinaB64;

	public UnidadOperativa() {
	}

	public Long getIdUnidadOperativa() {
		return this.idUnidadOperativa;
	}

	public void setIdUnidadOperativa(Long idUnidadOperativa) {
		this.idUnidadOperativa = idUnidadOperativa;
	}

	public String getACodigoUnidadOperativa() {
		return this.aCodigoUnidadOperativa;
	}

	public void setACodigoUnidadOperativa(String aCodigoUnidadOperativa) {
		this.aCodigoUnidadOperativa = aCodigoUnidadOperativa;
	}

	public String getANombreCompletoUnidadOperativa() {
		return this.aNombreCompletoUnidadOperativa;
	}

	public void setANombreCompletoUnidadOperativa(String aNombreCompletoUnidadOperativa) {
		this.aNombreCompletoUnidadOperativa = aNombreCompletoUnidadOperativa;
	}

	public String getaCodigoUnidadOperativa() {
		return aCodigoUnidadOperativa;
	}

	public void setaCodigoUnidadOperativa(String aCodigoUnidadOperativa) {
		this.aCodigoUnidadOperativa = aCodigoUnidadOperativa;
	}

	public String getaNombreCompletoUnidadOperativa() {
		return aNombreCompletoUnidadOperativa;
	}

	public void setaNombreCompletoUnidadOperativa(String aNombreCompletoUnidadOperativa) {
		this.aNombreCompletoUnidadOperativa = aNombreCompletoUnidadOperativa;
	}

	public List<SolicitudCreacionExp> getSolicitudesDeCreacionExp() {
		return solicitudesDeCreacionExp;
	}

	public void setSolicitudesDeCreacionExp(List<SolicitudCreacionExp> solicitudesDeCreacionExp) {
		this.solicitudesDeCreacionExp = solicitudesDeCreacionExp;
	}

	public String getLoginHeaderMultiOficinaB64() {
		return loginHeaderMultiOficinaB64;
	}

	public void setLoginHeaderMultiOficinaB64(String loginHeaderMultiOficinaB64) {
		this.loginHeaderMultiOficinaB64 = loginHeaderMultiOficinaB64;
	}
	

}