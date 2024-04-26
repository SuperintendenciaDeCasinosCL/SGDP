package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class DocumentoAnexoDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String tipo;

    private String contenido;

    private String nombre_archivo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    @Override
    public String toString() {
        return "Documento_anexo [tipo = " + tipo + ", contenido = " + contenido + ", nombre_archivo = " + nombre_archivo
                + "]";
    }

}