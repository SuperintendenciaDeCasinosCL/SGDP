package cl.gob.scj.sgdp.dto.reportes;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cl.gob.scj.sgdp.util.FechaUtil;

public class TareasPorAreaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String usuario;
	private float inDia;
	private float outDia;
	private float tasaIn;
	private float tiempoRespuesta;
	private Integer enBandeja;
	private String area;
	private String fechaMovimiento;
	private float tasa = 0;
	private String color1;
	private String color2;
	private float respuestaPromedio;

	public TareasPorAreaDTO() {
		super();
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public float getInDia() {
		return inDia;
	}

	public void setInDia(float inDia) {
		this.inDia = inDia;
	}

	public float getOutDia() {
		return outDia;
	}

	public void setOutDia(float outDia) {
		this.outDia = outDia;
	}

	public float getTasaIn() {
		return tasaIn;
	}

	public void setTasaIn(float tasaIn) {
		this.tasaIn = tasaIn;
	}

	public float getTiempoRespuesta() {
		return tiempoRespuesta;
	}

	public void setTiempoRespuesta(float tiempoRespuesta) {
		this.tiempoRespuesta = tiempoRespuesta;
	}

	public Integer getEnBandeja() {
		return enBandeja;
	}

	public void setEnBandeja(Integer enBandeja) throws IOException, ParseException {
		this.enBandeja = enBandeja;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(String fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public float getTasa() {
		return tasa;
	}

	public void setTasa(int tasa) {
		this.tasa = tasa;
	}

	public String getColor1() {
		return color1;
	}

	public void setColor1(String color1) {
		this.color1 = color1;
	}

	public String getColor2() {
		return color2;
	}

	public void setColor2(String color2) {
		this.color2 = color2;
	}

	
	private boolean isBetween(float x, int lower, int upper) {
		return lower <= x && x <= upper;
	}

	public float getRespuestaPromedio() {
		return respuestaPromedio;
	}

	public void setRespuestaPromedio(float respuestaPromedio) {
		this.respuestaPromedio = respuestaPromedio;
	}
	
	public void calcular() throws IOException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		long dias = FechaUtil.diasEntreFechas(new Date(), ( this.fechaMovimiento == null ? new Date() : sdf.parse(this.fechaMovimiento )));
		
		float totin = this.outDia - enBandeja;
		
		if (dias > 0) {
			this.inDia = totin / dias;
		} else {
			this.inDia = 0l;
		}
		if(this.inDia == 0) {
			this.tasa = 0;
		} else {
			this.tasa = (this.outDia / this.inDia) * 100;
		}
		
		if (this.tasa == 0) {
			this.color1 = "";
		} else 
			if (isBetween(this.tasa, 70, 80)) {
				this.color1 = "#FFC489";
			} else 
				if (isBetween(this.tasa, 80, 85)) {
					this.color1 = "#FFFEB0";
				} else
					if (isBetween(this.tasa, 85, 95)) {
						this.color1 = "#FFFEB0";
					} else
						if (isBetween(this.tasa, 95, 150)) {
							this.color1 = "#FFFEB0";
						} else {
							this.color1 = "#FFB0B0";
						}
		
		if (this.tasa == 0) {
			this.color2 = "";
		} else 
			if (isBetween(this.respuestaPromedio, 3, 5)) {
				this.color2 = "#FFC489";
			} else 
				if (isBetween(this.respuestaPromedio, 2, 3)) {
					this.color2 = "#FFFEB0";
				} else
					if (isBetween(this.respuestaPromedio, 1, 2)) {
						this.color2 = "#A7E89C";
					} else
						if (isBetween(this.respuestaPromedio, -1, 1)) {
							this.color2 = "#7FD570";
						} else {
							this.color2 = "#FFB0B0";
						}
		
	
	
	}
}

