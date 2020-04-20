
package cl.gob.scj.sgdp.ws.soap.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para borraRegistroDocumentoRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="borraRegistroDocumentoRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codTipoDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mensajeException" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="motivoAnulacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroDoc" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "borraRegistroDocumentoRequest", propOrder = {
    "codTipoDoc",
    "expDoc",
    "mensajeException",
    "motivoAnulacion",
    "numeroDoc"
})
public class BorraRegistroDocumentoRequest {

    protected String codTipoDoc;
    protected String expDoc;
    protected String mensajeException;
    protected String motivoAnulacion;
    protected Long numeroDoc;

    /**
     * Obtiene el valor de la propiedad codTipoDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoDoc() {
        return codTipoDoc;
    }

    /**
     * Define el valor de la propiedad codTipoDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoDoc(String value) {
        this.codTipoDoc = value;
    }

    /**
     * Obtiene el valor de la propiedad expDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpDoc() {
        return expDoc;
    }

    /**
     * Define el valor de la propiedad expDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpDoc(String value) {
        this.expDoc = value;
    }

    /**
     * Obtiene el valor de la propiedad mensajeException.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensajeException() {
        return mensajeException;
    }

    /**
     * Define el valor de la propiedad mensajeException.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensajeException(String value) {
        this.mensajeException = value;
    }

    /**
     * Obtiene el valor de la propiedad motivoAnulacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoAnulacion() {
        return motivoAnulacion;
    }

    /**
     * Define el valor de la propiedad motivoAnulacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoAnulacion(String value) {
        this.motivoAnulacion = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroDoc.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumeroDoc() {
        return numeroDoc;
    }

    /**
     * Define el valor de la propiedad numeroDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumeroDoc(Long value) {
        this.numeroDoc = value;
    }

}
