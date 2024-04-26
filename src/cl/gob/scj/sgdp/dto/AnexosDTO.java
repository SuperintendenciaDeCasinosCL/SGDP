package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class AnexosDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8986206406847995159L;

    private String nombre_anexo;

    private String id_documento_anexo;

    public String getNombre_anexo() {
        return nombre_anexo;
    }

    public void setNombre_anexo(String nombre_anexo) {
        this.nombre_anexo = nombre_anexo;
    }

    public String getId_documento_anexo() {
        return id_documento_anexo;
    }

    public void setId_documento_anexo(String id_documento_anexo) {
        this.id_documento_anexo = id_documento_anexo;
    }

    @Override
    public String toString() {
        return "Anexos [nombre_anexo = " + nombre_anexo + ", id_documento_anexo = " + id_documento_anexo + "]";
    }

}