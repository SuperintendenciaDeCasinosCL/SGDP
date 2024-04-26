package cl.gob.scj.sgdp.dto;

import org.springframework.http.HttpStatus;

public class ResponseDto {
	private int code;
	private String message;
	private Object data;
	private long recordsTotal;
	private long recordsFiltered;
	
	private int statusCodeValue;
	private String reasonPhrase;
	private HttpStatus httpStatus;

	public ResponseDto() {

	}

	public ResponseDto(int code, String message) {
		super();
		this.code = code;
		this.message = message;

	}

	public ResponseDto(int code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}

	public ResponseDto(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public ResponseDto(int code, Object data, long rowsCount) {
		super();
		this.code = code;
		this.data = data;
		this.recordsTotal = rowsCount;
		this.recordsFiltered = rowsCount;
	}

	public ResponseDto(int code, String message, Object data, long rowsCount) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
		this.recordsTotal = rowsCount;
		this.recordsFiltered = rowsCount;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	
	public int getStatusCodeValue() {
		return statusCodeValue;
	}

	public void setStatusCodeValue(int statusCodeValue) {
		this.statusCodeValue = statusCodeValue;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	@Override
	public String toString() {
		return "ResponseDto [code=" + code + ", message=" + message + ", data=" + data + ", recordsTotal="
				+ recordsTotal + ", recordsFiltered=" + recordsFiltered + "]";
	}

}
