
package cl.gob.scj.sgdp.ws.soap.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para generaRegistroDocumentoRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="generaRegistroDocumentoRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codDivisionUnidadSGDP" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codTipoDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaDocS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaTramitacionS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="materia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generaRegistroDocumentoRequest", propOrder = {
    "codDivisionUnidadSGDP",
    "codTipoDoc",
    "expDoc",
    "fechaDocS",
    "fechaTramitacionS",
    "usuario",
    "materia"
})
public class GeneraRegistroDocumentoRequest {

    protected int codDivisionUnidadSGDP;
    protected String codTipoDoc;
    protected String expDoc;
    protected String fechaDocS;
    protected String fechaTramitacionS;
    protected String usuario;
    protected String materia;

    /**
     * Obtiene el valor de la propiedad codDivisionUnidadSGDP.
     * 
     */
    public int getCodDivisionUnidadSGDP() {
        return codDivisionUnidadSGDP;
    }

    /**
     * Define el valor de la propiedad codDivisionUnidadSGDP.
     * 
     */
    public void setCodDivisionUnidadSGDP(int value) {
        this.codDivisionUnidadSGDP = value;
    }

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
     * Obtiene el valor de la propiedad fechaDocS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaDocS() {
        return fechaDocS;
    }

    /**
     * Define el valor de la propiedad fechaDocS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaDocS(String value) {
        this.fechaDocS = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaTramitacionS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaTramitacionS() {
        return fechaTramitacionS;
    }

    /**
     * Define el valor de la propiedad fechaTramitacionS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaTramitacionS(String value) {
        this.fechaTramitacionS = value;
    }

    /**
     * Obtiene el valor de la propiedad usuario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Define el valor de la propiedad usuario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuario(String value) {
        this.usuario = value;
    }

    /**
     * Obtiene el valor de la propiedad materia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getMateria() {
		return materia;
	}

	 /**
     * Define el valor de la propiedad materia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setMateria(String materia) {
		this.materia = materia;
	}
    

}
