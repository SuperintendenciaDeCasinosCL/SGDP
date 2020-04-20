
package cl.gob.scj.sgdp.ws.soap.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para borraRegistroDocumentoResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="borraRegistroDocumentoResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dto.numdoc.scj.gob.cl/}respuestaDTO">
 *       &lt;sequence>
 *         &lt;element name="codDivisionUnidadSGDP" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codTipoDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="confidencial" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="expDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaDocS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaTramitacionS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreTipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroDoc" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="pkRegistroDoc" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "borraRegistroDocumentoResponse", propOrder = {
    "codDivisionUnidadSGDP",
    "codTipoDoc",
    "confidencial",
    "expDoc",
    "fechaDocS",
    "fechaTramitacionS",
    "nombreTipoDocumento",
    "numeroDoc",
    "pkRegistroDoc",
    "usuario"
})
public class BorraRegistroDocumentoResponse
    extends RespuestaDTO
{

    protected int codDivisionUnidadSGDP;
    protected String codTipoDoc;
    protected Boolean confidencial;
    protected String expDoc;
    protected String fechaDocS;
    protected String fechaTramitacionS;
    protected String nombreTipoDocumento;
    protected Long numeroDoc;
    protected Long pkRegistroDoc;
    protected String usuario;

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
     * Obtiene el valor de la propiedad confidencial.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isConfidencial() {
        return confidencial;
    }

    /**
     * Define el valor de la propiedad confidencial.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setConfidencial(Boolean value) {
        this.confidencial = value;
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
     * Obtiene el valor de la propiedad nombreTipoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreTipoDocumento() {
        return nombreTipoDocumento;
    }

    /**
     * Define el valor de la propiedad nombreTipoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreTipoDocumento(String value) {
        this.nombreTipoDocumento = value;
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

    /**
     * Obtiene el valor de la propiedad pkRegistroDoc.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPkRegistroDoc() {
        return pkRegistroDoc;
    }

    /**
     * Define el valor de la propiedad pkRegistroDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPkRegistroDoc(Long value) {
        this.pkRegistroDoc = value;
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

	@Override
	public String toString() {
		return "BorraRegistroDocumentoResponse [codDivisionUnidadSGDP=" + codDivisionUnidadSGDP + ", codTipoDoc="
				+ codTipoDoc + ", confidencial=" + confidencial + ", expDoc=" + expDoc + ", fechaDocS=" + fechaDocS
				+ ", fechaTramitacionS=" + fechaTramitacionS + ", nombreTipoDocumento=" + nombreTipoDocumento
				+ ", numeroDoc=" + numeroDoc + ", pkRegistroDoc=" + pkRegistroDoc + ", usuario=" + usuario + "]";
	}

}
