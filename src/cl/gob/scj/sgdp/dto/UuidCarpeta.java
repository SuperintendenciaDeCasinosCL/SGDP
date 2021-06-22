package cl.gob.scj.sgdp.dto;

import java.io.Serializable;
import java.util.UUID;

public class UuidCarpeta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UUID uuid;
	private String carpeta;
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public String getCarpeta() {
		return carpeta;
	}
	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}
	@Override
	public String toString() {
		return "UuidCarpeta [uuid=" + uuid + ", carpeta=" + carpeta + "]";
	}
	
}
