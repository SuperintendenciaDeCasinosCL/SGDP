package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class ResultDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2537649455684897974L;

    private String id_documento;

    private String id_solicitud;

    private AnexosDTO[] anexos;

    private String fecha_ingreso;

    public String getId_documento() {
        return id_documento;
    }

    public void setId_documento(String id_documento) {
        this.id_documento = id_documento;
    }

    public String getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(String id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public AnexosDTO[] getAnexos() {
        return anexos;
    }

    public void setAnexos(AnexosDTO[] anexos) {
        this.anexos = anexos;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    @Override
    public String toString() {
        return "Result [id_documento = " + id_documento + ", id_solicitud = " + id_solicitud + ", anexos = " + anexos
                + ", fecha_ingreso = " + fecha_ingreso + "]";
    }

}