package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class SuggestionsDTO implements Serializable {

	private static final long serialVersionUID = 4428374712032863901L;
	
	private String value;
	private String data;
	
	public SuggestionsDTO() {
		super();
	}

	@Override
	public String toString() {
		return "SuggestionsDTO [value=" + value + ", data=" + data + "]";
	}
	
	public SuggestionsDTO(String value, String data) {
		super();
		this.value = value;
		this.data = data;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
		
}
