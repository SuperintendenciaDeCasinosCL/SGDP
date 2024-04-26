package cl.gob.scj.sgdp.dto.reportes;

public class ProcesosDTO {
	private Long idProceso;
	private String nombreProceso;
	private String codigoProceso;
	private int contv;
	private String nombroMacroProceso;
	private String codigoUnidad;
	private String nombreCompletoUnidad;
	
	private String dentroPlazo;
	private String contIn;
	private String contOut;
	private String rat;
	private String totPlazo;
	
	private String tiempoPromedio;
	private String min;
	private String max;
	private String prom;
	private String devS;
	
	private String tareaEjec;
	private String env;
	private String dev;
	private String fin;
	private String totEjec;
	
	private int tareasTotales;

	public ProcesosDTO() {
		super();
	}

	public Long getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public String getCodigoProceso() {
		return codigoProceso;
	}

	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}

	public int getContv() {
		return contv;
	}

	public void setContv(int contv) {
		this.contv = contv;
	}

	public String getNombroMacroProceso() {
		return nombroMacroProceso;
	}

	public void setNombroMacroProceso(String nombroMacroProceso) {
		this.nombroMacroProceso = nombroMacroProceso;
	}

	public String getCodigoUnidad() {
		return codigoUnidad;
	}

	public void setCodigoUnidad(String codigoUnidad) {
		this.codigoUnidad = codigoUnidad;
	}

	public String getNombreCompletoUnidad() {
		return nombreCompletoUnidad;
	}

	public void setNombreCompletoUnidad(String nombreCompletoUnidad) {
		this.nombreCompletoUnidad = nombreCompletoUnidad;
	}

	public String getDentroPlazo() {
		return dentroPlazo;
	}

	public void setDentroPlazo(String dentroPlazo) {
		this.dentroPlazo = dentroPlazo;
		if (dentroPlazo != null) {
			String[] spl =  this.dentroPlazo.split(",");
			this.setContIn(spl[0]);
			this.setContOut(spl[1]);
			this.setRat(spl[2]);
			this.setTotPlazo(spl[3]);
		}
	}

	public String getContIn() {
		return contIn;
	}

	public void setContIn(String contIn) {
		this.contIn = contIn;
	}

	public String getContOut() {
		return contOut;
	}

	public void setContOut(String contOut) {
		this.contOut = contOut;
	}

	public String getRat() {
		return rat;
	}

	public void setRat(String rat) {
		this.rat = rat;
	}

	public String getTotPlazo() {
		return totPlazo;
	}

	public void setTotPlazo(String totPlazo) {
		this.totPlazo = totPlazo;
	}

	public String getTiempoPromedio() {
		return tiempoPromedio;		
	}

	public void setTiempoPromedio(String tiempoPromedio) {
		this.tiempoPromedio = tiempoPromedio;
		if (tiempoPromedio != null ) {
			String[] spl =  this.tiempoPromedio.split(",");
			this.setMin(spl[0]);
			this.setMax(spl[1]);
			this.setProm(spl[2]);
			this.setDevS(spl[3]);
		}
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getProm() {
		return prom;
	}

	public void setProm(String prom) {
		this.prom = prom;
	}

	public String getDevS() {
		return devS;
	}

	public void setDevS(String devS) {
		this.devS = devS;
	}

	public String getTareaEjec() {
		return tareaEjec;
	}

	public void setTareaEjec(String tareaEjec) {
		this.tareaEjec = tareaEjec;
		if (tareaEjec != null ) {
			String[] spl =  this.tareaEjec.split(",");
			this.setEnv(spl[0].replace("env=", ""));
			this.setDev(spl[1].replace("dev=", ""));
			this.setFin(spl[2].replace("fin=", ""));
			this.setTotEjec(spl[3].replace("tot=", ""));
		}
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getDev() {
		return dev;
	}

	public void setDev(String dev) {
		this.dev = dev;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public String getTotEjec() {
		return totEjec;
	}

	public void setTotEjec(String totEjec) {
		this.totEjec = totEjec;
	}

	public int getTareasTotales() {
		return tareasTotales;
	}

	public void setTareasTotales(int tareasTotales) {
		this.tareasTotales = tareasTotales;
	}
	
	
}
