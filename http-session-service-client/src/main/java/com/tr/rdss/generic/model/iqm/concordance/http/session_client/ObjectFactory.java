
package com.tr.rdss.generic.model.iqm.concordance.http.session_client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tr.rdss.generic.model.iqm.concordance.http.session_client package. 
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

    private final static QName _CloseResponse_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "closeResponse");
    private final static QName _GetQuoteVOByPermIdResponse_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "getQuoteVOByPermIdResponse");
    private final static QName _CommitTransaction_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "commitTransaction");
    private final static QName _GetTransactionStatus_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "getTransactionStatus");
    private final static QName _GetTransactionStatusResponse_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "getTransactionStatusResponse");
    private final static QName _CommitTransactionResponse_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "commitTransactionResponse");
    private final static QName _SaveEntityVOResponse_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "saveEntityVOResponse");
    private final static QName _RollbackTransaction_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "rollbackTransaction");
    private final static QName _RollbackTransactionResponse_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "rollbackTransactionResponse");
    private final static QName _BeginTransactionResponse_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "beginTransactionResponse");
    private final static QName _GetQuoteVOByPermId_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "getQuoteVOByPermId");
    private final static QName _SaveEntityVO_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "saveEntityVO");
    private final static QName _BeginTransaction_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "beginTransaction");
    private final static QName _Close_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "close");
    private final static QName _InitResponse_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "initResponse");
    private final static QName _Init_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "init");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tr.rdss.generic.model.iqm.concordance.http.session_client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BeginTransactionResponse }
     * 
     */
    public BeginTransactionResponse createBeginTransactionResponse() {
        return new BeginTransactionResponse();
    }

    /**
     * Create an instance of {@link GetQuoteVOByPermId }
     * 
     */
    public GetQuoteVOByPermId createGetQuoteVOByPermId() {
        return new GetQuoteVOByPermId();
    }

    /**
     * Create an instance of {@link RollbackTransactionResponse }
     * 
     */
    public RollbackTransactionResponse createRollbackTransactionResponse() {
        return new RollbackTransactionResponse();
    }

    /**
     * Create an instance of {@link RollbackTransaction }
     * 
     */
    public RollbackTransaction createRollbackTransaction() {
        return new RollbackTransaction();
    }

    /**
     * Create an instance of {@link SaveEntityVOResponse }
     * 
     */
    public SaveEntityVOResponse createSaveEntityVOResponse() {
        return new SaveEntityVOResponse();
    }

    /**
     * Create an instance of {@link Init }
     * 
     */
    public Init createInit() {
        return new Init();
    }

    /**
     * Create an instance of {@link SaveEntityVO }
     * 
     */
    public SaveEntityVO createSaveEntityVO() {
        return new SaveEntityVO();
    }

    /**
     * Create an instance of {@link BeginTransaction }
     * 
     */
    public BeginTransaction createBeginTransaction() {
        return new BeginTransaction();
    }

    /**
     * Create an instance of {@link Close }
     * 
     */
    public Close createClose() {
        return new Close();
    }

    /**
     * Create an instance of {@link InitResponse }
     * 
     */
    public InitResponse createInitResponse() {
        return new InitResponse();
    }

    /**
     * Create an instance of {@link CommitTransaction }
     * 
     */
    public CommitTransaction createCommitTransaction() {
        return new CommitTransaction();
    }

    /**
     * Create an instance of {@link GetTransactionStatus }
     * 
     */
    public GetTransactionStatus createGetTransactionStatus() {
        return new GetTransactionStatus();
    }

    /**
     * Create an instance of {@link GetQuoteVOByPermIdResponse }
     * 
     */
    public GetQuoteVOByPermIdResponse createGetQuoteVOByPermIdResponse() {
        return new GetQuoteVOByPermIdResponse();
    }

    /**
     * Create an instance of {@link CloseResponse }
     * 
     */
    public CloseResponse createCloseResponse() {
        return new CloseResponse();
    }

    /**
     * Create an instance of {@link CommitTransactionResponse }
     * 
     */
    public CommitTransactionResponse createCommitTransactionResponse() {
        return new CommitTransactionResponse();
    }

    /**
     * Create an instance of {@link GetTransactionStatusResponse }
     * 
     */
    public GetTransactionStatusResponse createGetTransactionStatusResponse() {
        return new GetTransactionStatusResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CloseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "closeResponse")
    public JAXBElement<CloseResponse> createCloseResponse(CloseResponse value) {
        return new JAXBElement<CloseResponse>(_CloseResponse_QNAME, CloseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetQuoteVOByPermIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "getQuoteVOByPermIdResponse")
    public JAXBElement<GetQuoteVOByPermIdResponse> createGetQuoteVOByPermIdResponse(GetQuoteVOByPermIdResponse value) {
        return new JAXBElement<GetQuoteVOByPermIdResponse>(_GetQuoteVOByPermIdResponse_QNAME, GetQuoteVOByPermIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommitTransaction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "commitTransaction")
    public JAXBElement<CommitTransaction> createCommitTransaction(CommitTransaction value) {
        return new JAXBElement<CommitTransaction>(_CommitTransaction_QNAME, CommitTransaction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransactionStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "getTransactionStatus")
    public JAXBElement<GetTransactionStatus> createGetTransactionStatus(GetTransactionStatus value) {
        return new JAXBElement<GetTransactionStatus>(_GetTransactionStatus_QNAME, GetTransactionStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransactionStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "getTransactionStatusResponse")
    public JAXBElement<GetTransactionStatusResponse> createGetTransactionStatusResponse(GetTransactionStatusResponse value) {
        return new JAXBElement<GetTransactionStatusResponse>(_GetTransactionStatusResponse_QNAME, GetTransactionStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommitTransactionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "commitTransactionResponse")
    public JAXBElement<CommitTransactionResponse> createCommitTransactionResponse(CommitTransactionResponse value) {
        return new JAXBElement<CommitTransactionResponse>(_CommitTransactionResponse_QNAME, CommitTransactionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveEntityVOResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "saveEntityVOResponse")
    public JAXBElement<SaveEntityVOResponse> createSaveEntityVOResponse(SaveEntityVOResponse value) {
        return new JAXBElement<SaveEntityVOResponse>(_SaveEntityVOResponse_QNAME, SaveEntityVOResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RollbackTransaction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "rollbackTransaction")
    public JAXBElement<RollbackTransaction> createRollbackTransaction(RollbackTransaction value) {
        return new JAXBElement<RollbackTransaction>(_RollbackTransaction_QNAME, RollbackTransaction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RollbackTransactionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "rollbackTransactionResponse")
    public JAXBElement<RollbackTransactionResponse> createRollbackTransactionResponse(RollbackTransactionResponse value) {
        return new JAXBElement<RollbackTransactionResponse>(_RollbackTransactionResponse_QNAME, RollbackTransactionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BeginTransactionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "beginTransactionResponse")
    public JAXBElement<BeginTransactionResponse> createBeginTransactionResponse(BeginTransactionResponse value) {
        return new JAXBElement<BeginTransactionResponse>(_BeginTransactionResponse_QNAME, BeginTransactionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetQuoteVOByPermId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "getQuoteVOByPermId")
    public JAXBElement<GetQuoteVOByPermId> createGetQuoteVOByPermId(GetQuoteVOByPermId value) {
        return new JAXBElement<GetQuoteVOByPermId>(_GetQuoteVOByPermId_QNAME, GetQuoteVOByPermId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveEntityVO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "saveEntityVO")
    public JAXBElement<SaveEntityVO> createSaveEntityVO(SaveEntityVO value) {
        return new JAXBElement<SaveEntityVO>(_SaveEntityVO_QNAME, SaveEntityVO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BeginTransaction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "beginTransaction")
    public JAXBElement<BeginTransaction> createBeginTransaction(BeginTransaction value) {
        return new JAXBElement<BeginTransaction>(_BeginTransaction_QNAME, BeginTransaction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Close }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "close")
    public JAXBElement<Close> createClose(Close value) {
        return new JAXBElement<Close>(_Close_QNAME, Close.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InitResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "initResponse")
    public JAXBElement<InitResponse> createInitResponse(InitResponse value) {
        return new JAXBElement<InitResponse>(_InitResponse_QNAME, InitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Init }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", name = "init")
    public JAXBElement<Init> createInit(Init value) {
        return new JAXBElement<Init>(_Init_QNAME, Init.class, null, value);
    }

}
