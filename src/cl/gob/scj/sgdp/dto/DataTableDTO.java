package cl.gob.scj.sgdp.dto;

public class DataTableDTO {
	private String draw;
	private Object columns;
	private int start;
	private int length;
	private String orderDirection;
	private String orderColumName;

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public Object getColumns() {
		return columns;
	}

	public void setColumns(Object columns) {
		this.columns = columns;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public String getOrderColumName() {
		return orderColumName;
	}

	public void setOrderColumName(String orderColumName) {
		this.orderColumName = orderColumName;
	}

}

