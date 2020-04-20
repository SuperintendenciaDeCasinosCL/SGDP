package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class ParametroFormularioDTO implements Serializable {
	
	private String name;
	private Object value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "ParametroFormularioDTO [name=" + name + ", value=" + value + "]";
	}
	
}
