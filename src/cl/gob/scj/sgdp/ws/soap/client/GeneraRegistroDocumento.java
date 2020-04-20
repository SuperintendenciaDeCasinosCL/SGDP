
package cl.gob.scj.sgdp.ws.soap.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para generaRegistroDocumento complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="generaRegistroDocumento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://dto.numdoc.scj.gob.cl/}generaRegistroDocumentoRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generaRegistroDocumento", namespace = "http://ws.numdoc.scj.gob.cl/", propOrder = {
    "arg0"
})
public class GeneraRegistroDocumento {

    protected GeneraRegistroDocumentoRequest arg0;

    /**
     * Obtiene el valor de la propiedad arg0.
     * 
     * @return
     *     possible object is
     *     {@link GeneraRegistroDocumentoRequest }
     *     
     */
    public GeneraRegistroDocumentoRequest getArg0() {
        return arg0;
    }

    /**
     * Define el valor de la propiedad arg0.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneraRegistroDocumentoRequest }
     *     
     */
    public void setArg0(GeneraRegistroDocumentoRequest value) {
        this.arg0 = value;
    }

}
