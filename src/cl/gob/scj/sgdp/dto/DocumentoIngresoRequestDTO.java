package cl.gob.scj.sgdp.dto;

import java.io.Serializable;

public class DocumentoIngresoRequestDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 557124536620231046L;

    private String descripcion;

    private DocumentoAnexoDTO[] documentos_anexos;

    private String[] listado_id_usuarios_destinatarios;

    private String documento;

    private Integer id_entidad_creadora;

    private String nombre;

    private String[] listado_correos_copia;

    private String run_usuario_creador;

    private String[] listado_id_entidades_destinatarias;

    private String es_reservado;

    private String folio;

    private String materia;

    private String tipo_id;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public DocumentoAnexoDTO[] getDocumentos_anexos() {
        return documentos_anexos;
    }

    public void setDocumentos_anexos(DocumentoAnexoDTO[] documentos_anexos) {
        this.documentos_anexos = documentos_anexos;
    }

    public String[] getListado_id_usuarios_destinatarios() {
        return listado_id_usuarios_destinatarios;
    }

    public void setListado_id_usuarios_destinatarios(String[] listado_id_usuarios_destinatarios) {
        this.listado_id_usuarios_destinatarios = listado_id_usuarios_destinatarios;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Integer getId_entidad_creadora() {
        return id_entidad_creadora;
    }

    public void setId_entidad_creadora(Integer id_entidad_creadora) {
        this.id_entidad_creadora = id_entidad_creadora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String[] getListado_correos_copia() {
        return listado_correos_copia;
    }

    public void setListado_correos_copia(String[] listado_correos_copia) {
        this.listado_correos_copia = listado_correos_copia;
    }

    public String getRun_usuario_creador() {
        return run_usuario_creador;
    }

    public void setRun_usuario_creador(String run_usuario_creador) {
        this.run_usuario_creador = run_usuario_creador;
    }

    public String[] getListado_id_entidades_destinatarias() {
        return listado_id_entidades_destinatarias;
    }

    public void setListado_id_entidades_destinatarias(String[] listado_id_entidades_destinatarias) {
        this.listado_id_entidades_destinatarias = listado_id_entidades_destinatarias;
    }

    public String getEs_reservado() {
        return es_reservado;
    }

    public void setEs_reservado(String es_reservado) {
        this.es_reservado = es_reservado;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(String tipo_id) {
        this.tipo_id = tipo_id;
    }

    @Override
    public String toString() {
        return "DocumentoIngresoRequest [descripcion = " + descripcion + ", documentos_anexos = " + documentos_anexos
                + ", listado_id_usuarios_destinatarios = " + listado_id_usuarios_destinatarios + ", documento = "
                + documento + ", id_entidad_creadora = " + id_entidad_creadora + ", nombre = " + nombre
                + ", listado_correos_copia = " + listado_correos_copia + ", run_usuario_creador = "
                + run_usuario_creador + ", listado_id_entidades_destinatarias = " + listado_id_entidades_destinatarias
                + ", es_reservado = " + es_reservado + ", folio = " + folio + ", materia = " + materia + ", tipo_id = "
                + tipo_id + "]";
    }

}