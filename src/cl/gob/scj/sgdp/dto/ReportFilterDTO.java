package cl.gob.scj.sgdp.dto;

import cl.gob.scj.sgdp.dto.DataTableDTO;

public class ReportFilterDTO extends DataTableDTO {
	private String dateFrom;
	private String dateTo;
	private String textFilter;

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getTextFilter() {
		return textFilter;
	}

	public void setTextFilter(String textFilter) {
		this.textFilter = textFilter;
	}

	@Override
	public String toString() {
		return "ReportFilterDTO [dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", textFilter=" + textFilter + "]";
	}
	
}

