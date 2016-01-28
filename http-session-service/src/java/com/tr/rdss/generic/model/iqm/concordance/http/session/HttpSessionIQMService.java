/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tr.rdss.generic.model.iqm.concordance.http.session;

import com.tr.rdss.generic.model.iqm.concordance.QuoteVO;
import com.tr.rdss.generic.model.iqm.concordance.rmi.RMIIQMAppModuleServices;
import com.tr.rdss.generic.model.iqm.concordance.util.IQMHttpSessionTransactionException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidEntityVOException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidHttpSessionServiceException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidRMIServiceException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidRawValueException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidServiceMethodCallException;
import com.tr.rdss.generic.model.iqm.concordance.util.JSONException;
import com.tr.rdss.generic.model.iqm.concordance.util.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author U8015921
 */
@WebService
public class HttpSessionIQMService implements HttpSessionIQMServiceInterface {
    
    @Resource
    private WebServiceContext wsContext;
    
    private class Stub {
        public MessageContext messageContext;
        public HttpSession httpSession;
        public TransactionManager transactionManager;
        public Transaction transaction;
        public RMIIQMAppModuleServices rmiService;
    }
    
    private Stub resumeStub(boolean resumeTransaction) {
        try {
            Stub stub;
            stub = this.new Stub();
            stub.messageContext = this.wsContext.getMessageContext();
            stub.httpSession = this.getSession(stub.messageContext);
            stub.transactionManager = this.getTransactionManagerStub(stub.httpSession);
            stub.rmiService = this.getServiceStub(stub.httpSession);
            
            if(resumeTransaction) {                
                stub.transaction = this.getTransactionStub(stub.httpSession);
                if(stub.transaction==null || stub.transaction.getStatus()==Status.STATUS_NO_TRANSACTION)
                    throw new InvalidHttpSessionServiceException("Transaction is invalid");
                
                resumeTransaction(stub.transactionManager, stub.transaction);
            } 
            
            return stub;
        } catch (SystemException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
    }

    private void suspendStub(Stub stub) {
        this.setServiceStub(stub.httpSession, stub.rmiService);
        // suspending the transaction  
        this.suspendTransaction(stub.httpSession, stub.transactionManager);
    }

    private static final String SERVICE_STUB = "RMIIQMAppModuleServicesStub";

    private static final String TRANSACTION_STUB = "TransactionStub";
    
    private static final String TRANSACTION_MANAGER_STUB = "TransactionManagerStub";
    
    private static final String JAVAX_TRANSACTION_TRANSACTION_MANAGER = "javax.transaction.TransactionManager";


    private HttpSession getSession(MessageContext messageContext) {
        return ((javax.servlet.http.HttpServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST)).getSession();
    }

    private RMIIQMAppModuleServices getServiceStub(HttpSession session) {
        return (RMIIQMAppModuleServices) (session.getAttribute(HttpSessionIQMService.SERVICE_STUB));
    }
//    private RMIIQMAppModuleServices getServiceStub(MessageContext messageContext) {
//        HttpSession session = getSession(messageContext);
//        RMIIQMAppModuleServices service = getServiceStub(session);
//        return service;
//    }

    private Transaction getTransactionStub(HttpSession session) {
        return (Transaction) (session.getAttribute(HttpSessionIQMService.TRANSACTION_STUB));
    }
//    private Transaction getTransactionStub(MessageContext messageContext) {
//        HttpSession session = getSession(messageContext);
//        Transaction transaction = getTransactionStub(session);
//        return transaction;
//    }

    private TransactionManager getTransactionManagerStub(HttpSession session) {
        return (TransactionManager) (session.getAttribute(HttpSessionIQMService.TRANSACTION_MANAGER_STUB));
    }
//    private void setServiceStub(MessageContext messageContext, RMIIQMAppModuleServices service) {
//        HttpSession session = getSession(messageContext);
//        setServiceStub(session, service);
//    }
    private void setServiceStub(HttpSession session, RMIIQMAppModuleServices service) {
        session.setAttribute(HttpSessionIQMService.SERVICE_STUB, service);
    }

//    private void setTransactionStub(MessageContext messageContext, Transaction transaction) {
//        HttpSession session = getSession(messageContext);
//        setTransactionStub(session, transaction);
//    }
    private void setTransactionStub(HttpSession session, Transaction transaction) {
        session.setAttribute(HttpSessionIQMService.TRANSACTION_STUB, transaction);
    }
    
    private void setTransactionManagerStub(HttpSession session, TransactionManager transactonManager) {
        session.setAttribute(HttpSessionIQMService.TRANSACTION_MANAGER_STUB, transactonManager);        
    }
    
    private void suspendTransaction(HttpSession session, final TransactionManager transactionManager) {
        // suspending the transaction and keep it in httpsession
        try {
            Utils.printMessage("====== transaction status before suspending: " + transactionManager.getStatus());
            Transaction transaction = transactionManager.suspend();
            this.setTransactionStub(session, transaction);
            this.setTransactionManagerStub(session, transactionManager);
        } catch (SystemException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
    }

    private void resumeTransaction(final TransactionManager transactionManager, Transaction transaction) {
        // suspending the transaction and keep it in httpsession
        Utils.printTrace(false);
        try {
            if(transaction == null )
                throw new InvalidHttpSessionServiceException("Transaction is null");
            else 
                Utils.printMessage("===== before resuming, transaction.getStatus() : " + transaction.getStatus());
            transactionManager.resume(transaction);  
            
            Utils.printMessage("====== after resuming, transaction.getStatus() : " + transactionManager.getStatus());
        } catch (InvalidTransactionException | IllegalStateException | SystemException | SecurityException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
    }
    
    @WebMethod
    @Override
    public void init(String userName, String pwd) {
        Utils.printTrace(false);
        
        MessageContext mc = wsContext.getMessageContext();    // Step 3
        HttpSession session = getSession(mc);

        RMIIQMAppModuleServices service = getServiceStub(session);
        
        // init the RMIIQMAppModuleServices
        try {
            // check whether any service already exists
        if (service != null) {
            throw new Exception("service is not null");
        }
            // pick up the local services
            service = new RMIIQMAppModuleServices(userName, pwd);
            
            Utils.printMessage(mc.toString());
            Utils.printMessage(session.toString());
            Utils.printMessage(service.toString());

            setServiceStub(session, service);
            
            
            // init the transaction manager and transaction
            /**
             * **********************
             */
            // just output the transaction manager to check if it works
            Utils.printTrace(false);
            InitialContext ctx = new InitialContext();

            TransactionManager transactionManager = (TransactionManager) ctx.lookup(JAVAX_TRANSACTION_TRANSACTION_MANAGER);
            Utils.printMessage(JAVAX_TRANSACTION_TRANSACTION_MANAGER + ":" + transactionManager);            
            Utils.printMessage("ctx: " + ctx);

            setTransactionManagerStub(session, transactionManager);
            
        } catch (InvalidRMIServiceException | NamingException ex) {            
            this.close();
            throw new InvalidHttpSessionServiceException(ex);
        } catch(Exception ex) {                        
            this.close();
            throw new InvalidHttpSessionServiceException(ex);
        } 
    }

    @WebMethod
    @Override
    public void close() {
        Stub stub = this.resumeStub(false);
        
        try {
            if (stub.rmiService != null) {
                stub.rmiService.close();
            }
            stub.transaction = this.getTransactionStub(stub.httpSession);
            if (!(stub.transaction== null || stub.transactionManager.getStatus()==Status.STATUS_NO_TRANSACTION) )
                stub.transactionManager.rollback();
        } catch (InvalidRMIServiceException | SystemException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        } finally {
            stub.httpSession.invalidate();
        }
    }

    

    @WebMethod
    @Override
    public void beginTransaction() {
        //only resume the stub, without resume
        Stub stub = this.resumeStub(false);
        
        try {
            stub.transactionManager.setTransactionTimeout(TIME_OUT_SECONDS);
            stub.transactionManager.begin();
        } catch (SystemException | NotSupportedException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }

        this.suspendStub(stub);
    }

    @WebMethod
    @Override
    public void commitTransaction() {
        Stub stub = this.resumeStub(true);

        try {
            stub.transactionManager.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
        
        this.suspendStub(stub);
    }

    @WebMethod
    @Override
    public void rollbackTransaction() {
        Stub stub = this.resumeStub(true);

        try {
            stub.transactionManager.rollback();
        } catch (IllegalStateException | SecurityException | SystemException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }        
        
        this.suspendStub(stub);
    }
    


    /**
     *
     * @return transaction status
     * @see javax.transaction.UserTransaction#getStatus()
     */
    @WebMethod
    @Override
    public int getTransactionStatus() {        
        Stub stub = this.resumeStub(true);
        
        int status;
        try {
            status = stub.transactionManager.getStatus();
        } catch (SystemException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
        
        this.suspendStub(stub);
        return status;
    }

//    /**
//     *
//     * @param permId instrument perm id
//     * @param effectiveFromDate effective from
//     * @param effectiveToDate effective to
//     * @return InstrumentVO with all the info of the requested instrument.
//     * @throws JSONException
//     * @throws InvalidRawValueException
//     * @throws InvalidServiceMethodCallException
//     * <br>
//     * Only perm ids will be provided for all the associated quotes). <br>
//     * All attributes will have denormalized values instead of ids. <br>
//     * E.g. "Published" (TRCS name) will be provided for ADMIN_STATUS instead of 1010003 (TRCS perm id).
//     */
//    public InstrumentVO getInstrumentVOByPermId(Long permId, Date effectiveFromDate, Date effectiveToDate) throws JSONException, InvalidRawValueException, InvalidServiceMethodCallException {
//        
//        String entityVOStr = (String) Utils.callMethod(this.getIQMAppService(), //
//        "getInstrumentVOJSONByPermId", //
//        new Object[]{ //
//        permId.toString(), //
//        Utils.convertToString(effectiveFromDate), //
//        Utils.convertToString(effectiveToDate) //
//        });
//        InstrumentVO entityVO = (InstrumentVO) Utils.getEntityVOFromJSON(entityVOStr);
//        if (entityVO != null) {
//            entityVO.setIsNormalized(null);
//            AttributeConverter.convertEntityVO(entityVO, false);
//        }
//        return entityVO;
//    }
//
    /**
     * 
     * @param permId
     * @param effectiveFrom
     * @param effectiveTo
     * @return quoteVOJSON
     * 
     */
    @WebMethod
    @Override
    public String getQuoteVOByPermId(Long permId, String effectiveFrom, String effectiveTo) {
        Stub stub = this.resumeStub(true);
        String res = null;
        try {
            QuoteVO quoteVO = stub.rmiService.getQuoteVOByPermId(permId, Utils.convertToDate(effectiveFrom), Utils.convertToDate(effectiveTo));
            
            res = Utils.toJSON(quoteVO);
        } catch (JSONException | InvalidRawValueException | InvalidServiceMethodCallException  ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
        this.suspendStub(stub);
        return res;
    }
//
//    /**
//     *
//     * @param quoteRic quote ric
//     * @param effectiveFromDate effective from of ric
//     * @param effectiveToDate effective from of ric
//     * @return quote perm id
//     * @throws InvalidServiceMethodCallException
//     */
//    public Long getQuotePermidByRic(String quoteRic, Date effectiveFromDate, Date effectiveToDate) throws InvalidServiceMethodCallException {
//        Long permId = (Long) Utils.callMethod(this.getIQMAppService(), //
//        "getQuotePermidByRic", //
//        new Object[]{quoteRic, //
//        Utils.convertToString(effectiveFromDate), Utils.convertToString(effectiveToDate)});
//        return permId;
//    }
//
//    //    public Long getQuotePermIdBySymbol(String quoteSymbol, String marketRDNExchangeCode,
//    //        Date effectiveDate) {
//    //
//    //        Long permId = (Long) Utils.callMethod(this.getIQMAppService(), //
//    //            "getQuotePermIdBySymbolAndMktRDNCode", //
//    //            new Object[]{quoteSymbol, //
//    //                marketRDNExchangeCode,//
//    //                Utils.convertToString(effectiveDate)
//    //            });
//    //
//    //        return permId;
//    //
//    //    }
//    /**
//     *
//     * @param quoteSymbol quote ticker symbol
//     * @param marketPermId market perm id
//     * @param effectiveDate effective date of symbol and quote market info
//     * @return quote perm id
//     * @throws InvalidServiceMethodCallException
//     */
//    public Long getQuotePermIdBySymbol(String quoteSymbol, Long marketPermId, Date effectiveDate) throws InvalidServiceMethodCallException {
//        Long permId = (Long) Utils.callMethod(this.getIQMAppService(), //
//        "getQuotePermIdBySymbolAndMktPermId", //
//        new Object[]{quoteSymbol, //
//        marketPermId, //
//        Utils.convertToString(effectiveDate)});
//        return permId;
//    }
//
//    //    public List<Long> getInstrumentPermIdsByOAName(String oaName, Date effectiveFromDate,
//    //        Date effectiveToDate) {
//    //
//    //        String permIdsStr = (String) Utils.callMethod(this.getIQMAppService(), //
//    //            "getInstrumentPermIdsByOAName", //
//    //            new Object[]{oaName, //
//    //                Utils.convertToString(effectiveFromDate),
//    //                Utils.convertToString(effectiveToDate)
//    //            });
//    //
//    //        return Utils.getLongListFromString(permIdsStr);
//    //    }
//    /**
//     *
//     * @param oaPermId organization perm id
//     * @param effectiveFromDate effective from of rships between org and issues
//     * @param effectiveToDate effective to of rships between org and issues
//     * @return instrument perm id list
//     * @throws InvalidServiceMethodCallException
//     */
//    public List<Long> getInstrumentPermIdsByOAPermId(Long oaPermId, Date effectiveFromDate, Date effectiveToDate) throws InvalidServiceMethodCallException {
//        String permIdsStr = (String) Utils.callMethod(this.getIQMAppService(), //
//        "getInstrumentPermIdsByOrgPermId", //
//        new Object[]{oaPermId.toString(), //
//        Utils.convertToString(effectiveFromDate), Utils.convertToString(effectiveToDate)});
//        return Utils.getLongListFromString(permIdsStr);
//    }
//
//    /**
//     *
//     * @param insPermId instrument perm id
//     * @param effectiveFromDate effective from of rships between the base asset instrument and quotes
//     * @param effectiveToDate effective to of rships between the base asset instrument and quotes
//     * @return a list of Long value (quote perm ids which have the input instrument as "BASE ASSET")
//     * @throws InvalidServiceMethodCallException
//     */
//    public List<Long> getQuotePermIdsByBaseAssetPermId(Long insPermId, Date effectiveFromDate, Date effectiveToDate) throws InvalidServiceMethodCallException {
//        String permIdsStr = (String) Utils.callMethod(this.getIQMAppService(), //
//        "getQuotePermIdsByBaseAssetPermId", //
//        new Object[]{insPermId.toString(), //
//        Utils.convertToString(effectiveFromDate), Utils.convertToString(effectiveToDate)});
//        return Utils.getLongListFromString(permIdsStr);
//    }
//
    
    @WebMethod
    @Override
    public Long saveEntityVO(String entityVOJSON) {
        Stub stub = this.resumeStub(true);

        Long res;
        try {
                res = stub.rmiService.saveEntityVO(Utils.getEntityVOFromJSON(entityVOJSON));
            } catch (InvalidEntityVOException | JSONException | InvalidRawValueException | InvalidServiceMethodCallException ex) {
                throw new InvalidHttpSessionServiceException(ex);
            } 
        
        this.suspendStub(stub);

        return res;
    }
//
//    /**
//     *
//     * @param permId quote perm id
//     * @param attributeEnum quote attribute enumeration
//     * @param effectiveFrom effective from
//     * @param effectiveTo effective to
//     * @return quoteVO with the history of the required attribute within the specified period; return valid object with
//     * no attributes if no data found
//     * @throws JSONException
//     * @throws InvalidRawValueException
//     * @throws InvalidServiceMethodCallException
//     */
//    public QuoteVO getHistoryAttributeOfQuoteByPermId(Long permId, //
//    QuoteVO.AttributesEnum attributeEnum, //
//    Date effectiveFrom, Date effectiveTo) throws JSONException, InvalidRawValueException, InvalidServiceMethodCallException {
//        String entityVOStr = (String) Utils.callMethod( //
//        this.getIQMNonTemporalAppModuleService(), //
//        "getHistoryEntityVOJSONByAttributeNameAndDateRange", //
//        new Object[]{QuoteVO.entityLevel, permId.toString(), attributeEnum.getEnumName(), Utils.convertToString(effectiveFrom), Utils.convertToString(effectiveTo)});
//        QuoteVO quoteVO = (QuoteVO) Utils.getEntityVOFromJSON(entityVOStr);
//        if (quoteVO != null) {
//            quoteVO.setIsNormalized(null);
//            AttributeConverter.convertEntityVO(quoteVO, false);
//        }
//        return quoteVO;
//    }
//
//    /**
//     *
//     * @param permId quote perm id
//     * @param attributeEnum quote attribute enumeration
//     * @return quoteVO with the full history of the required attribute; return valid object with no attributes if no
//     * data found
//     * @throws JSONException
//     * @throws InvalidRawValueException
//     * @throws InvalidServiceMethodCallException
//     */
//    public QuoteVO getHistoryAttributeOfQuoteByPermId(Long permId, //
//    QuoteVO.AttributesEnum attributeEnum) throws JSONException, InvalidRawValueException, InvalidServiceMethodCallException {
//        String entityVOStr = (String) Utils.callMethod( //
//        this.getIQMNonTemporalAppModuleService(), //
//        "getHistoryEntityVOJSONByAttributeName", //
//        new Object[]{QuoteVO.entityLevel, permId.toString(), attributeEnum.getEnumName()});
//        QuoteVO quoteVO = (QuoteVO) Utils.getEntityVOFromJSON(entityVOStr);
//        if (quoteVO != null) {
//            quoteVO.setIsNormalized(null);
//            AttributeConverter.convertEntityVO(quoteVO, false);
//        }
//        return quoteVO;
//    }
//
//    /**
//     *
//     * @param permId instrument perm id
//     * @param attributeEnum instrument attribute enumeration
//     * @param effectiveFrom effective from
//     * @param effectiveTo effective to
//     * @return instrumentVO with the history of the required attribute within the specified period; return valid object
//     * with no attributes if no data found
//     * @throws JSONException
//     * @throws InvalidRawValueException
//     * @throws InvalidServiceMethodCallException
//     */
//    public InstrumentVO getHistoryAttributeOfInstrumentByPermId(Long permId, InstrumentVO.AttributesEnum attributeEnum, //
//    Date effectiveFrom, Date effectiveTo) throws JSONException, InvalidRawValueException, InvalidServiceMethodCallException {
//        String entityVOStr = (String) Utils.callMethod( //
//        this.getIQMNonTemporalAppModuleService(), //
//        "getHistoryEntityVOJSONByAttributeNameAndDateRange", //
//        new Object[]{InstrumentVO.entityLevel, permId.toString(), attributeEnum.getEnumName(), Utils.convertToString(effectiveFrom), Utils.convertToString(effectiveTo)});
//        InstrumentVO instrumentVO = (InstrumentVO) Utils.getEntityVOFromJSON(entityVOStr);
//        if (instrumentVO != null) {
//            instrumentVO.setIsNormalized(null);
//            AttributeConverter.convertEntityVO(instrumentVO, false);
//        }
//        return instrumentVO;
//    }
//
//    /**
//     *
//     * @param permId instrument perm id
//     * @param attributeEnum instrument attribute enumeration
//     * @return instrumentVO with the full history of the required attribute; return valid object with no attributes if
//     * no data found
//     * @throws JSONException
//     * @throws InvalidRawValueException
//     * @throws InvalidServiceMethodCallException
//     */
//    public InstrumentVO getHistoryAttributeOfInstrumentByPermId(Long permId, InstrumentVO.AttributesEnum attributeEnum) throws JSONException, InvalidRawValueException, InvalidServiceMethodCallException {
//        String entityVOStr = (String) Utils.callMethod( //
//        this.getIQMNonTemporalAppModuleService(), //
//        "getHistoryEntityVOJSONByAttributeName", //
//        new Object[]{InstrumentVO.entityLevel, permId.toString(), attributeEnum.getEnumName()});
//        InstrumentVO instrumentVO = (InstrumentVO) Utils.getEntityVOFromJSON(entityVOStr);
//        if (instrumentVO != null) {
//            instrumentVO.setIsNormalized(null);
//            AttributeConverter.convertEntityVO(instrumentVO, false);
//        }
//        return instrumentVO;
//    }
//
//    //    /**
//    //     *
//    //     * @param permId quote perm id
//    //     * @param attributeEnum quote attribute enumeration
//    //     * @return quoteVO with the full history of the required attribute; return valid object with no attributes if no data found
//    //     * @throws JSONException
//    //     * @throws InvalidRawValueException
//    //     * @throws InvalidRMIMethodCallException
//    //     */
//    //    public QuoteVO getHistoryAttributeOfQuoteByPermId(Long permId,
//    //        QuoteVO.AttributesEnum attributeEnum) throws JSONException, InvalidRawValueException, InvalidRMIMethodCallException {
//    //        String entityVOStr = (String) Utils.callMethod(//
//    //            this.getIQMNonTemporalAppModuleService(),//
//    //            "getHistoryQuoteVOJSONByAttributeName",//
//    //            new Object[]{permId.toString(), attributeEnum.getEnumName()});
//    //
//    //        QuoteVO quoteVO = (QuoteVO) Utils.getEntityVOFromJSON(entityVOStr);
//    //        if (quoteVO!=null) {
//    //            quoteVO.setIsNormalized(null);
//    //            AttributeConverter.convertEntityVO(quoteVO, false);
//    //        }
//    //        return quoteVO;
//    //    }
//    //    /**
//    //     *
//    //     * @param permId instrument perm id
//    //     * @param attributeEnum instrument attribute enumeration
//    //     * @return instrumentVO with the full history of the required attribute; return valid object with no attributes if no data found
//    //     * @throws JSONException
//    //     * @throws InvalidRawValueException
//    //     * @throws InvalidRMIMethodCallException
//    //     */
//    //    public InstrumentVO getHistoryAttributeOfInstrumentByPermId(Long permId,
//    //        InstrumentVO.AttributesEnum attributeEnum) throws JSONException, InvalidRawValueException, InvalidRMIMethodCallException {
//    //        String entityVOStr = (String) Utils.callMethod(//
//    //            this.getIQMNonTemporalAppModuleService(),//
//    //            "getHistoryInstrumentVOJSONByAttributeName",//
//    //            new Object[]{permId.toString(), attributeEnum.getEnumName()});
//    //
//    //        InstrumentVO instrumentVO = (InstrumentVO) Utils.getEntityVOFromJSON(entityVOStr);
//    //        if (instrumentVO!=null) {
//    //            instrumentVO.setIsNormalized(null);
//    //            AttributeConverter.convertEntityVO(instrumentVO, false);
//    //        }
//    //        return instrumentVO;
//    //    }
//    /**
//     *
//     * @param itemValue fuzzy search value
//     * @return
//     * @throws JSONException
//     * @throws InvalidServiceMethodCallException
//     * <br>
//     * The fuzzy search is case insensitive. The search covers <br>
//     * a) entity perm ids <br>
//     * b) entity identifiers <br>
//     * c) entity nonunique identifiers <br>
//     * d) entity names <br>
//     * <br>
//     * More details, please check the IQM native master service.
//     *
//     */
//    public MasterGateLookupVO getMasterGateLookup(String itemValue) throws JSONException, InvalidServiceMethodCallException {
//        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(), //
//        "getMasterGateLookupJSON", //
//        new Object[]{itemValue});
//        return (MasterGateLookupVO) Utils.getEntityVOFromJSON(resultStr);
//    }
//
//    /**
//     *
//     * @param itemValue organization long name for fuzzy search (case insensitive)
//     * @return
//     * @throws JSONException
//     * @throws InvalidServiceMethodCallException
//     */
//    public OrgLookupVO getOrgLookup(String itemValue) throws JSONException, InvalidServiceMethodCallException {
//        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(), //
//        "getOrgLookupJSON", //
//        new Object[]{itemValue});
//        return (OrgLookupVO) Utils.getEntityVOFromJSON(resultStr);
//    }
//
//    //    public CurrencyLookupVO getCurrencyLookup() throws JSONException {
//    //        if ("Y".equals(appServiceProperties.getProperty("CurrencyLookup.use.local").toUpperCase())) {
//    //            return LocalCurrencyLookup.getInstance();
//    //        }
//    //        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(),//
//    //            "getCurrencyLookupJSON", //
//    //            new Object[]{});
//    //
//    //        return (CurrencyLookupVO) Utils.getEntityVOFromJSON(resultStr);
//    //    }
//    //    public MarketLookupVO getMarketLookup() throws JSONException {
//    //
//    //        if ("Y".equals(appServiceProperties.getProperty("MarketLookup.use.local").toUpperCase())) {
//    //            return LocalMarketLookup.getInstance();
//    //        }
//    //
//    //        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(),//
//    //            "getMarketLookupJSON", //
//    //            new Object[]{});
//    //
//    //        return (MarketLookupVO) Utils.getEntityVOFromJSON(resultStr);
//    //    }
//    //    public ModelMetadataLookupVO getModelMetadataLookup() throws JSONException {
//    //        if ("Y".equals(appServiceProperties.getProperty("ModelMetadataLookup.use.local").toUpperCase())) {
//    //            return LocalModelMetadataLookup.getInstance();
//    //        }
//    //
//    //        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(),//
//    //            "getModelMetadataLookupJSON", //
//    //            new Object[]{});
//    //
//    //        return (ModelMetadataLookupVO) Utils.getEntityVOFromJSON(resultStr);
//    //    }
//    /**
//     *
//     * @param currencyPermId TRCS currency perm id
//     * @return TRCS currency code
//     * @throws InvalidServiceMethodCallException
//     */
//    @Override
//    public String getCurrencyCodeByPermId(Long currencyPermId) throws InvalidServiceMethodCallException {
//        String code = (String) Utils.callMethod(this.getIQMLookupAppModuleService(), //
//        "getCurrencyCodeByPermId", //
//        new Object[]{currencyPermId});
//        return code;
//    }
//
//    /**
//     *
//     * @param currencyCode TRCS currency code
//     * @return TRCS currency perm id
//     * @throws InvalidServiceMethodCallException
//     */
//    @Override
//    public Long getCurrencyPermIdByCode(String currencyCode) throws InvalidServiceMethodCallException {
//        Long permId = (Long) Utils.callMethod(this.getIQMLookupAppModuleService(), //
//        "getCurrencyPermIdByCode", //
//        new Object[]{currencyCode});
//        return permId;
//    }
//
//    /**
//     *
//     * @param marketPermId market perm id
//     * @return market RDN exchange code list
//     * @throws JSONException
//     * @throws InvalidServiceMethodCallException
//     */
//    public List<String> getMarketRDNExchangeCodeByPermId(Long marketPermId) throws JSONException, InvalidServiceMethodCallException {
//        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(), //
//        "getMarketRDNExchangeCodeLookupByPermId", //
//        new Object[]{marketPermId});
//        MarketLookupVO marketLookupVO = (MarketLookupVO) Utils.getEntityVOFromJSON(resultStr);
//        List<String> list = new ArrayList<String>();
//        for (MarketVO marketVO : marketLookupVO.getElementList()) {
//            list.add(marketVO.getName());
//        }
//        return list;
//    }
//
//    /**
//     *
//     * @param marketRDNExchangeCode market RDN exchange coode
//     * @return market perm id
//     * @throws InvalidServiceMethodCallException
//     */
//    public Long getMarketPermIdByRDNExchangeCode(String marketRDNExchangeCode) throws InvalidServiceMethodCallException {
//        if (marketRDNExchangeCode == null || marketRDNExchangeCode.equals("")) {
//            return null;
//        }
//        Long permId = (Long) Utils.callMethod(this.getIQMLookupAppModuleService(), //
//        "getMarketPermIdByRDNExchangeCode", //
//        new Object[]{marketRDNExchangeCode});
//        return permId;
//    }
//
//    /**
//     *
//     * @param marketPermId market perm id
//     * @return market time zone name
//     * @throws InvalidServiceMethodCallException
//     */
//    public String getTimeZoneByMarketPermId(Long marketPermId) throws InvalidServiceMethodCallException {
//        if (marketPermId == null) {
//            return null;
//        }
//        String res = (String) Utils.callMethod(this.getIQMLookupAppModuleService(), //
//        "getTimeZoneByMarketPermId", //
//        new Object[]{marketPermId});
//        return res;
//    }
//
//    /**
//     *
//     * @param enuPermId enumeration perm id
//     * @return enumeration value (DB filed: iqm_nav.mtd_domain_mv.enu_uniquename)
//     * @throws InvalidServiceMethodCallException
//     */
//    @Override
//    public String getMetadataValueByEnuPermid(Long enuPermId) throws InvalidServiceMethodCallException {
//        String value = (String) Utils.callMethod(this.getIQMLookupAppModuleService(), //
//        "getMetadataValueByEnuPermid", //
//        new Object[]{enuPermId});
//        return value;
//    }
//
//    /**
//     *
//     * @param entityLevel entity level (DB filed: iqm_nav.model_map_mv.bdm_structure)
//     * @param propertyName property name (DB filed: iqm_nav.model_map_mv.bdm_pro_name)
//     * @param propertyValue property value (DB filed: iqm_nav.mtd_domain_mv.enu_uniquename)
//     * @return enumeration perm id (DB filed: iqm_nav.mtd_domain_mv.enu_permid)
//     * @throws InvalidServiceMethodCallException service exceptions
//     */
//    @Override
//    public Long getModelMetadataPermIdByLevelNameValue(String entityLevel, String propertyName, String propertyValue) throws InvalidServiceMethodCallException {
//        Long permId = (Long) Utils.callMethod(this.getIQMLookupAppModuleService(), //
//        "getModelMetadataPermIdByLevelNameValue", //
//        new Object[]{entityLevel, propertyName, propertyValue});
//        return permId;
//    }
//
//    /**
//     *
//     * @return entity properties, value of which are from ModelMetadata lookup (DB object: iqm_nav.mtd_domain_mv)
//     * @throws InvalidServiceMethodCallException
//     * @throws JSONException
//     * <br>
//     * Return the local copy if context property "ModelMetadataLookup.use.local" is "Y", otherwise retrieve data from
//     * db.
//     */
//    @Override
//    public List<ModelMetadataVO> getModelMetadataProperties() throws InvalidServiceMethodCallException {
//        List<ModelMetadataVO> modelMetadataVOList = new ArrayList<ModelMetadataVO>();
//        List<ModelMetadataVO> list = null;
//        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(), //
//        "getModelMetadataPropertiesJSON", //
//        new Object[]{});
//        ModelMetadataLookupVO tmp;
//        try {
//            tmp = (ModelMetadataLookupVO) Utils.getEntityVOFromJSON(resultStr);
//            if (tmp != null) {
//                list = tmp.getElementList();
//            }
//        } catch (JSONException ex) {
//            //                Utils.printTrace(false);
//            //                Utils.printMessage(ex);
//            throw new InvalidServiceMethodCallException(ex);
//        }
//        // always dedup the list
//        HashSet<String> keySet = new HashSet<String>();
//        if (list == null) {
//            return modelMetadataVOList;
//        }
//        for (ModelMetadataVO modelMetadataVO : list) {
//            String key = modelMetadataVO.getEntityLevel() + "." + modelMetadataVO.getPropertyName();
//            if (!keySet.contains(key)) {
//                keySet.add(key);
//                modelMetadataVOList.add(new ModelMetadataVO(null, null, null, null, null, null, modelMetadataVO.getPropertyName(), modelMetadataVO.getEntityLevel()));
//            }
//        }
//        return modelMetadataVOList;
//    }
//
//    /**
//     *
//     * @param tickerSymbol quote ticker symbol
//     * @param marketRDNExchangeCode market RDN exchange code
//     * @return count of matched quotes
//     * @throws InvalidServiceMethodCallException
//     * @throws JSONException
//     * <br>
//     * Count the matched quotes in RDC, with the given ticker symbol and market RDN exchange code. <br>
//     * Refer to the IQM native master service for the details. <br>
//     */
//    public Long countQuotesInRDC(String tickerSymbol, String marketRDNExchangeCode) throws InvalidServiceMethodCallException, JSONException {
//        String res = (String) Utils.callMethod(this.getIQMAppService(), //
//        "getRDCCheckJSonForQuote", //
//        new Object[]{tickerSymbol, marketRDNExchangeCode});
//        DaoResult daoResult = Utils.fromJSON(res);
//        if (daoResult.isSucc()) {
//            Long cnt = Long.parseLong(daoResult.getKey_value());
//            return cnt;
//        } else {
//            throw new InvalidServiceMethodCallException(daoResult.getMessage());
//        }
//    }
//
//    /**
//     *
//     * @param propertyEnum property enumeration
//     * @param propertyValue property value
//     * @return count of matched instruments
//     * @throws InvalidServiceMethodCallException
//     * @throws JSONException
//     *
//     * <br>
//     * Count the matched instruments in RDC. <br>
//     * Refer to the IQM native master service for the details. <br>
//     */
//    public Long countInstrumentsInRDC(EnumInterface propertyEnum, String propertyValue) throws InvalidServiceMethodCallException, JSONException {
//        String res = (String) Utils.callMethod(this.getIQMAppService(), //
//        "getRDCCheckJSonForInstrument", //
//        new Object[]{propertyEnum.getEnumName(), propertyValue});
//        DaoResult daoResult = Utils.fromJSON(res);
//        if (daoResult.isSucc()) {
//            Long cnt = Long.parseLong(daoResult.getKey_value());
//            return cnt;
//        } else {
//            throw new InvalidServiceMethodCallException(daoResult.getMessage());
//        }
//    }
//
//    /**
//     *
//     * @param entityLevel entity level
//     * @param propertyEnum property enumeration
//     * @param propertyValue property value
//     * @param symbol symbol
//     * @param exchangeCodeList exchange code list
//     * @return response of the rdcCheckForCcd function of IQM native master service
//     *
//     * @throws InvalidServiceMethodCallException
//     * @throws JSONException
//     */
//    public String rdcCheckForCcd(String entityLevel, //
//    EnumInterface propertyEnum, String propertyValue, //
//    String symbol, List<String> exchangeCodeList) throws InvalidServiceMethodCallException, JSONException {
//        if (!(QuoteVO.entityLevel.equals(entityLevel) || InstrumentVO.entityLevel.equals(entityLevel))) {
//            throw new IllegalArgumentException("Input entityLevel (" + entityLevel + ") is invalid! Valid inputs: " + InstrumentVO.entityLevel + "," + QuoteVO.entityLevel);
//        }
//        RDCCheck rdcCheck = new RDCCheck(entityLevel);
//        DaoAction action = DaoAction.NONE;
//        Property property = new Property(propertyEnum.getEnumName(), propertyValue, action);
//        rdcCheck.assignProperty(property);
//        if (symbol != null && !symbol.trim().equals("")) {
//            Property symbolProperty = new Property(QuoteVO.AttributesEnum.TICKER_SYMBOL.getEnumName(), symbol.trim(), action);
//            rdcCheck.assignProperty(symbolProperty);
//        }
//        if (exchangeCodeList != null && !exchangeCodeList.isEmpty()) {
//            StringBuilder exchangeCodes = new StringBuilder();
//            for (String exchangeCode : exchangeCodeList) {
//                exchangeCodes.append(exchangeCode).append("|");
//            }
//            Property codeProperty = new Property(QuoteVO.AttributesEnum.TRADES_ON_SUBMARKET.getEnumName(), exchangeCodes.toString(), DaoAction.NONE);
//            rdcCheck.assignProperty(codeProperty);
//        }
//        String result = (String) Utils.callMethod(this.getIQMAppService(), //
//        "rdcCheckForCcd", //
//        new Object[]{Utils.toJSON(rdcCheck)});
//        return result;
//    }
//
//    /**
//     *
//     * @param functionFullName DB function full name, in format "schema_name"."package_name"."function_name" or
//     * "schema_name"."function_name
//     * @param inputJSON input string parameter (JSON string) for the db function
//     * @return response of the calling db function
//     * @throws InvalidServiceMethodCallException
//     * <br>
//     * If service succeeds, return the response of the calling db function; else raise Exception with the corresponding
//     * error message.
//     */
//    public String invokeDBFunction(String functionFullName, String inputJSON) throws InvalidServiceMethodCallException {
//        String res = (String) Utils.callMethod(this.getIQMAppService(), //
//        "invokeDBFunction", //
//        new Object[]{functionFullName, inputJSON});
//        return res;
//    }
//
//    private void removeTransactionStub(MessageContext mc) {
//        this.getSession(mc).removeAttribute(JAVAX_TRANSACTION_TRANSACTIONMANAGER);
//    }
}
