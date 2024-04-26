package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.List;

public class CargaProcesoDataInicialDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<PerspectivaDTO> perspectivas;	
	private List<MacroProcesoDTO> macroProcesos;
	private List<UnidadDTO> unidades;
	
	
	public CargaProcesoDataInicialDTO(List<PerspectivaDTO> perspectivas, List<MacroProcesoDTO> macroProcesos,
			List<UnidadDTO> unidades) {
		super();
		this.perspectivas = perspectivas;
		this.macroProcesos = macroProcesos;
		this.unidades = unidades;
	}

	public List<UnidadDTO> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<UnidadDTO> unidades) {
		this.unidades = unidades;
	}

	public CargaProcesoDataInicialDTO() {
	}

	public List<PerspectivaDTO> getPerspectivas() {
		return perspectivas;
	}
	public void setPerspectivas(List<PerspectivaDTO> perspectivas) {
		this.perspectivas = perspectivas;
	}
	public List<MacroProcesoDTO> getMacroProcesos() {
		return macroProcesos;
	}
	public void setMacroProcesos(List<MacroProcesoDTO> macroProcesos) {
		this.macroProcesos = macroProcesos;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
	
}
