
package cl.gob.scj.sgdp.ws.soap.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cl.gob.scj.sgdp.ws.soap.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BorraRegistroDocumentoResponse_QNAME = new QName("http://ws.numdoc.scj.gob.cl/", "borraRegistroDocumentoResponse");
    private final static QName _GeneraRegistroDocumentoResponse_QNAME = new QName("http://ws.numdoc.scj.gob.cl/", "generaRegistroDocumentoResponse");
    private final static QName _GeneraRegistroDocumento_QNAME = new QName("http://ws.numdoc.scj.gob.cl/", "generaRegistroDocumento");
    private final static QName _BorraRegistroDocumento_QNAME = new QName("http://ws.numdoc.scj.gob.cl/", "borraRegistroDocumento");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cl.gob.scj.sgdp.ws.soap.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GeneraRegistroDocumentoRequest }
     * 
     */
    public GeneraRegistroDocumentoRequest createGeneraRegistroDocumentoRequest() {
        return new GeneraRegistroDocumentoRequest();
    }

    /**
     * Create an instance of {@link BorraRegistroDocumentoRequest }
     * 
     */
    public BorraRegistroDocumentoRequest createBorraRegistroDocumentoRequest() {
        return new BorraRegistroDocumentoRequest();
    }

    /**
     * Create an instance of {@link BorraRegistroDocumentoResponse }
     * 
     */
    public BorraRegistroDocumentoResponse createBorraRegistroDocumentoResponse() {
        return new BorraRegistroDocumentoResponse();
    }

    /**
     * Create an instance of {@link GeneraRegistroDocumentoResponse }
     * 
     */
    public GeneraRegistroDocumentoResponse createGeneraRegistroDocumentoResponse() {
        return new GeneraRegistroDocumentoResponse();
    }

    /**
     * Create an instance of {@link GeneraRegistroDocumento }
     * 
     */
    public GeneraRegistroDocumento createGeneraRegistroDocumento() {
        return new GeneraRegistroDocumento();
    }

    /**
     * Create an instance of {@link BorraRegistroDocumento }
     * 
     */
    public BorraRegistroDocumento createBorraRegistroDocumento() {
        return new BorraRegistroDocumento();
    }

    /**
     * Create an instance of {@link BorraRegistroDocumentoResponse2 }
     * 
     */
    public BorraRegistroDocumentoResponse2 createBorraRegistroDocumentoResponse2() {
        return new BorraRegistroDocumentoResponse2();
    }

    /**
     * Create an instance of {@link GeneraRegistroDocumentoResponse2 }
     * 
     */
    public GeneraRegistroDocumentoResponse2 createGeneraRegistroDocumentoResponse2() {
        return new GeneraRegistroDocumentoResponse2();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorraRegistroDocumentoResponse2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.numdoc.scj.gob.cl/", name = "borraRegistroDocumentoResponse")
    public JAXBElement<BorraRegistroDocumentoResponse2> createBorraRegistroDocumentoResponse(BorraRegistroDocumentoResponse2 value) {
        return new JAXBElement<BorraRegistroDocumentoResponse2>(_BorraRegistroDocumentoResponse_QNAME, BorraRegistroDocumentoResponse2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneraRegistroDocumentoResponse2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.numdoc.scj.gob.cl/", name = "generaRegistroDocumentoResponse")
    public JAXBElement<GeneraRegistroDocumentoResponse2> createGeneraRegistroDocumentoResponse(GeneraRegistroDocumentoResponse2 value) {
        return new JAXBElement<GeneraRegistroDocumentoResponse2>(_GeneraRegistroDocumentoResponse_QNAME, GeneraRegistroDocumentoResponse2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GeneraRegistroDocumento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.numdoc.scj.gob.cl/", name = "generaRegistroDocumento")
    public JAXBElement<GeneraRegistroDocumento> createGeneraRegistroDocumento(GeneraRegistroDocumento value) {
        return new JAXBElement<GeneraRegistroDocumento>(_GeneraRegistroDocumento_QNAME, GeneraRegistroDocumento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorraRegistroDocumento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.numdoc.scj.gob.cl/", name = "borraRegistroDocumento")
    public JAXBElement<BorraRegistroDocumento> createBorraRegistroDocumento(BorraRegistroDocumento value) {
        return new JAXBElement<BorraRegistroDocumento>(_BorraRegistroDocumento_QNAME, BorraRegistroDocumento.class, null, value);
    }

}
