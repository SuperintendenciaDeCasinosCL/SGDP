package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

public class ComplejidadExpedienteDTO extends RespuestaDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8986206406847995159L;

    private Long id;
    private Long idInstanciaDeProceso;
    private String nombreExpediente;
    private String complejidad;
    private String motivoComplejidad;
    private String usuario;
    private List<ComplejidadDTO> complejidades;
    
    public ComplejidadExpedienteDTO() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdInstanciaDeProceso() {
		return idInstanciaDeProceso;
	}

	public void setIdInstanciaDeProceso(Long idInstanciaDeProceso) {
		this.idInstanciaDeProceso = idInstanciaDeProceso;
	}

	public String getComplejidad() {
		return complejidad;
	}

	public void setComplejidad(String complejidad) {
		this.complejidad = complejidad;
	}

	public String getMotivoComplejidad() {
		return motivoComplejidad;
	}

	public void setMotivoComplejidad(String motivoComplejidad) {
		this.motivoComplejidad = motivoComplejidad;
	}

	public String getNombreExpediente() {
		return nombreExpediente;
	}

	public void setNombreExpediente(String nombreExpediente) {
		this.nombreExpediente = nombreExpediente;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<ComplejidadDTO> getComplejidades() {
		return complejidades;
	}

	public void setComplejidades(List<ComplejidadDTO> complejidades) {
		this.complejidades = complejidades;
	}
    
}
