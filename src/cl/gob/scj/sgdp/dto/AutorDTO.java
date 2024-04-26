package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class AutorDTO implements Serializable {
	
	private long idAutor;
	private String nombreAutor;
	private String respuesta;

		
	public AutorDTO() {
		super();
	}

	public AutorDTO(long idAutor, String nombreAutor) {
		super();
		this.idAutor = idAutor;
		this.nombreAutor = nombreAutor;
	}
	
	public long getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(long idAutor) {
		this.idAutor = idAutor;
	}
	public String getNombreAutor() {
		return nombreAutor;
	}
	public void setNombreAutor(String nombreAutor) {
		this.nombreAutor = nombreAutor;
	}

	@Override
	public String toString() {
		return "AutorDTO [idAutor=" + idAutor + ", nombreAutor=" + nombreAutor
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idAutor ^ (idAutor >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AutorDTO other = (AutorDTO) obj;
		if (idAutor != other.idAutor)
			return false;
		return true;
	}	
	
	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}


}
