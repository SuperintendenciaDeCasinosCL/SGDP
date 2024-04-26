package cl.gob.scj.sgdp.dto;

import java.util.List;

public class ArchivosSolCreaExpDTOJsonObject {

	private int iTotalRecords;
	private int iTotalDisplayRecords;
	private String sEcho;
	private String sColumns;
	private List<ArchivosSolCreaExpDTO> aaData;
	public int getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public String getsEcho() {
		return sEcho;
	}
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}
	public String getsColumns() {
		return sColumns;
	}
	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}
	public List<ArchivosSolCreaExpDTO> getAaData() {
		return aaData;
	}
	public void setAaData(List<ArchivosSolCreaExpDTO> aaData) {
		this.aaData = aaData;
	}
	
	
	
}
