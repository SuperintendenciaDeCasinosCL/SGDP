package cl.gob.scj.sgdp.dto.reportes;

public class TareasFinalizadasDTO {
	private String usuario;
	private int ceroACinco;
	private int seisADiez;
	private int onceAveinte;
	private int veintiunoAcuarenta;
	private int cuarentayunoAochenta;
	private int ochentayunoYMas;
	private int total;
	private String area;
	
	public TareasFinalizadasDTO() {
		
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getCeroACinco() {
		return ceroACinco;
	}

	public void setCeroACinco(int ceroACinco) {
		this.ceroACinco = ceroACinco;
	}

	public int getSeisADiez() {
		return seisADiez;
	}

	public void setSeisADiez(int seisADiez) {
		this.seisADiez = seisADiez;
	}

	public int getOnceAveinte() {
		return onceAveinte;
	}

	public void setOnceAveinte(int onceAveinte) {
		this.onceAveinte = onceAveinte;
	}

	public int getVeintiunoAcuarenta() {
		return veintiunoAcuarenta;
	}

	public void setVeintiunoAcuarenta(int veintiunoAcuarenta) {
		this.veintiunoAcuarenta = veintiunoAcuarenta;
	}

	public int getCuarentayunoAochenta() {
		return cuarentayunoAochenta;
	}

	public void setCuarentayunoAochenta(int cuarentayunoAochenta) {
		this.cuarentayunoAochenta = cuarentayunoAochenta;
	}

	public int getOchentayunoYMas() {
		return ochentayunoYMas;
	}

	public void setOchentayunoYMas(int ochentayunoYMas) {
		this.ochentayunoYMas = ochentayunoYMas;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public void calcular() {
		this.total = this.ceroACinco + this.seisADiez + this.onceAveinte + this.veintiunoAcuarenta + this.cuarentayunoAochenta + this.ochentayunoYMas;
	}
}
