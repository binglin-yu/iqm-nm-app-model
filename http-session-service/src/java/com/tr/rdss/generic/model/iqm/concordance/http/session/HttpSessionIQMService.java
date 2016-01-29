/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tr.rdss.generic.model.iqm.concordance.http.session;

import com.tr.rdss.generic.model.iqm.concordance.InstrumentVO;
import com.tr.rdss.generic.model.iqm.concordance.MasterGateLookupVO;
import com.tr.rdss.generic.model.iqm.concordance.ModelMetadataLookupVO;
import com.tr.rdss.generic.model.iqm.concordance.OrgLookupVO;
import com.tr.rdss.generic.model.iqm.concordance.QuoteVO;
import com.tr.rdss.generic.model.iqm.concordance.rmi.RMIIQMAppModuleServices;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidEntityVOException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidHttpSessionServiceException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidRMIServiceException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidRawValueException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidServiceMethodCallException;
import com.tr.rdss.generic.model.iqm.concordance.util.JSONException;
import com.tr.rdss.generic.model.iqm.concordance.util.Utils;
import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.naming.InitialContext;
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

            if (resumeTransaction) {
                stub.transaction = this.getTransactionStub(stub.httpSession);
                if (stub.transaction == null || stub.transaction.getStatus() == Status.STATUS_NO_TRANSACTION) {
                    throw new InvalidHttpSessionServiceException("Transaction is invalid");
                }

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

    private Transaction getTransactionStub(HttpSession session) {
        return (Transaction) (session.getAttribute(HttpSessionIQMService.TRANSACTION_STUB));
    }

    private TransactionManager getTransactionManagerStub(HttpSession session) {
        return (TransactionManager) (session.getAttribute(HttpSessionIQMService.TRANSACTION_MANAGER_STUB));
    }

    private void setServiceStub(HttpSession session, RMIIQMAppModuleServices service) {
        session.setAttribute(HttpSessionIQMService.SERVICE_STUB, service);
    }

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
            if (transaction == null) {
                throw new InvalidHttpSessionServiceException("Transaction is null");
            } else {
                Utils.printMessage("===== before resuming, transaction.getStatus() : " + transaction.getStatus());
            }
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
        } catch (Exception ex) {
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
            if (!(stub.transaction == null || stub.transactionManager.getStatus() == Status.STATUS_NO_TRANSACTION)) {
                stub.transactionManager.rollback();
            }
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

    @WebMethod
    @Override
    public String getInstrumentVOByPermId(Long permId, String effectiveFrom, String effectiveTo) {
        Stub stub = this.resumeStub(true);
        String res = null;
        try {
            InstrumentVO instrumentVO = stub.rmiService.getInstrumentVOByPermId(permId, Utils.convertToDate(effectiveFrom), Utils.convertToDate(effectiveTo));

            res = Utils.toJSON(instrumentVO);
        } catch (JSONException | InvalidRawValueException | InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
        this.suspendStub(stub);
        return res;
    }

    @WebMethod
    @Override
    public String getQuoteVOByPermId(Long permId, String effectiveFrom, String effectiveTo) {
        Stub stub = this.resumeStub(true);
        String res = null;
        try {
            QuoteVO quoteVO = stub.rmiService.getQuoteVOByPermId(permId, Utils.convertToDate(effectiveFrom), Utils.convertToDate(effectiveTo));

            res = Utils.toJSON(quoteVO);
        } catch (JSONException | InvalidRawValueException | InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
        this.suspendStub(stub);
        return res;
    }

    @WebMethod
    @Override
    public Long getQuotePermidByRic(String quoteRic, String effectiveFrom, String effectiveTo) {
        Stub stub = this.resumeStub(true);
        Long permId = null;
        try {
            permId = stub.rmiService.getQuotePermidByRic(quoteRic,
                    Utils.convertToDate(effectiveFrom), Utils.convertToDate(effectiveTo));
        } catch (InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }

        this.suspendStub(stub);

        return permId;
    }

    @WebMethod
    @Override
    public Long getQuotePermIdBySymbol(String quoteSymbol, Long marketPermId, String effectiveDate) {
        Stub stub = this.resumeStub(true);
        Long permId = null;
        try {
            permId = stub.rmiService.getQuotePermIdBySymbol(quoteSymbol, marketPermId,
                    Utils.convertToDate(effectiveDate));
        } catch (InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }

        this.suspendStub(stub);

        return permId;
    }

        @WebMethod
    @Override
    public String getInstrumentPermIdsByOAPermId(Long oaPermId, String effectiveFrom, String effectiveTo) {
        Stub stub = this.resumeStub(true);

        String permIdsStr = null;
        try {
            List<Long> permIds = stub.rmiService.getInstrumentPermIdsByOAPermId(oaPermId,
                    Utils.convertToDate(effectiveFrom), Utils.convertToDate(effectiveTo));
            permIdsStr = Utils.toPermIdsString(permIds);
        } catch (InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }

        this.suspendStub(stub);
        return permIdsStr;
    }

    @WebMethod
    @Override
    public String getQuotePermIdsByBaseAssetPermId(Long insPermId, String effectiveFrom, String effectiveTo) {
        Stub stub = this.resumeStub(true);
        String permIdsStr = null;

        try {
            List<Long> permIds = stub.rmiService.getQuotePermIdsByBaseAssetPermId(insPermId,
                    Utils.convertToDate(effectiveFrom), Utils.convertToDate(effectiveTo));
            permIdsStr = Utils.toPermIdsString(permIds);
        } catch (InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }

        this.suspendStub(stub);
        return permIdsStr;
    }

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

    private QuoteVO.AttributesEnum getQuoteAttrributeEnum(String quoteAttributeName) {
        if (quoteAttributeName == null) {
            return null;
        }
        QuoteVO.AttributesEnum[] arr = QuoteVO.AttributesEnum.values();
        for (QuoteVO.AttributesEnum t : arr) {
            if (t.getEnumName().equals(quoteAttributeName)) {
                return t;
            }
        }
        return null;
    }

    private InstrumentVO.AttributesEnum getInstrumentAttributeEnum(String instrumentAttributeName) {
        if (instrumentAttributeName == null) {
            return null;
        }
        InstrumentVO.AttributesEnum[] arr = InstrumentVO.AttributesEnum.values();
        for (InstrumentVO.AttributesEnum t : arr) {
            if (t.getEnumName().equals(instrumentAttributeName)) {
                return t;
            }
        }
        return null;
    }

    @WebMethod
    @Override
    public String getHistoryAttributeOfQuoteByPermId(Long permId, //
            String attributeName, //
            String effectiveFrom, String effectiveTo) {
        Stub stub = this.resumeStub(true);

        QuoteVO entityVO;
        String entityVOStr;
        try {
            if (effectiveFrom == null && effectiveTo == null) {
                entityVO = stub.rmiService.getHistoryAttributeOfQuoteByPermId(permId,
                        getQuoteAttrributeEnum(attributeName));
            } else {
                entityVO = stub.rmiService.getHistoryAttributeOfQuoteByPermId(permId,
                        getQuoteAttrributeEnum(attributeName), Utils.convertToDate(effectiveFrom), Utils.convertToDate(effectiveTo));
            }

            entityVOStr = Utils.toJSON(entityVO);
        } catch (JSONException | InvalidRawValueException | InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }

        this.suspendStub(stub);
        return entityVOStr;
    }

    @WebMethod
    @Override
    public String getHistoryAttributeOfInstrumentByPermId(Long permId, //
            String attributeName, //
            String effectiveFrom, String effectiveTo) {
        Stub stub = this.resumeStub(true);

        InstrumentVO entityVO;
        String entityVOStr;
        try {
            if (effectiveFrom == null && effectiveTo == null) {
                entityVO = stub.rmiService.getHistoryAttributeOfInstrumentByPermId(permId,
                        getInstrumentAttributeEnum(attributeName));
            } else {
                entityVO = stub.rmiService.getHistoryAttributeOfInstrumentByPermId(permId,
                        getInstrumentAttributeEnum(attributeName), Utils.convertToDate(effectiveFrom), Utils.convertToDate(effectiveTo));
            }

            entityVOStr = Utils.toJSON(entityVO);
        } catch (JSONException | InvalidRawValueException | InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }

        this.suspendStub(stub);
        return entityVOStr;
    }

    @WebMethod
    @Override
    public String getMasterGateLookup(String itemValue) {
        Stub stub = this.resumeStub(true);

        MasterGateLookupVO lookUp;
        String lookUpStr;
        try {
            lookUp = stub.rmiService.getMasterGateLookup(itemValue);

            lookUpStr = Utils.toJSON(lookUp);
        } catch (JSONException | InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
        this.suspendStub(stub);
        return lookUpStr;
    }

    @WebMethod
    @Override
    public String getOrgLookup(String itemValue) {
        Stub stub = this.resumeStub(true);

        OrgLookupVO lookUp;
        String lookUpStr;
        try {
            lookUp = stub.rmiService.getOrgLookup(itemValue);

            lookUpStr = Utils.toJSON(lookUp);
        } catch (JSONException | InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
        this.suspendStub(stub);
        return lookUpStr;
    }

    @WebMethod
    @Override
    public String getCurrencyCodeByPermId(Long currencyPermId) {
        Stub stub = this.resumeStub(true);

        String code;
        try {
            code = stub.rmiService.getCurrencyCodeByPermId(currencyPermId);
        } catch (InvalidServiceMethodCallException ex) {

            throw new InvalidHttpSessionServiceException(ex);
        }
        this.suspendStub(stub);
        return code;
    }

    @WebMethod
    @Override
    public Long getCurrencyPermIdByCode(String currencyCode) {

        Stub stub = this.resumeStub(true);
        Long permId;
        try {
            permId = stub.rmiService.getCurrencyPermIdByCode(currencyCode);
        } catch (InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
        this.suspendStub(stub);
        return permId;
    }

    @WebMethod
    @Override
    public List<String> getMarketRDNExchangeCodeByPermId(Long marketPermId) {

        Stub stub = this.resumeStub(true);
        List<String> list;
        try {
            list = stub.rmiService.getMarketRDNExchangeCodeByPermId(marketPermId);
        } catch (JSONException | InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }

        this.suspendStub(stub);
        return list;
    }

    @WebMethod
    @Override
    public Long getMarketPermIdByRDNExchangeCode(String marketRDNExchangeCode) {

        Stub stub = this.resumeStub(true);
        Long permId;
        try {
            permId = stub.rmiService.getMarketPermIdByRDNExchangeCode(marketRDNExchangeCode);
        } catch (InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
        this.suspendStub(stub);
        return permId;
    }

    @WebMethod
    @Override
    public String getTimeZoneByMarketPermId(Long marketPermId) {
        Stub stub = this.resumeStub(true);

        String timeZone;
        try {
            timeZone = stub.rmiService.getTimeZoneByMarketPermId(marketPermId);
        } catch (InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
        this.suspendStub(stub);
        return timeZone;
    }

    @WebMethod
    @Override
    public String getMetadataValueByEnuPermid(Long enuPermId) {
        Stub stub = this.resumeStub(true);

        String metadataValue;
        try {
            metadataValue = stub.rmiService.getMetadataValueByEnuPermid(enuPermId);
        } catch (InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
        this.suspendStub(stub);
        return metadataValue;
    }

    @WebMethod
    @Override
    public Long getModelMetadataPermIdByLevelNameValue(String entityLevel, String propertyName, String propertyValue) {

        Stub stub = this.resumeStub(true);
        Long permId;
        try {
            permId = stub.rmiService.getModelMetadataPermIdByLevelNameValue(entityLevel, propertyName, propertyValue);
        } catch (InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
        this.suspendStub(stub);
        return permId;
    }

    @WebMethod
    @Override
    public String getModelMetadataProperties() {

        Stub stub = this.resumeStub(true);
        ModelMetadataLookupVO modelMetadataLookupVO;
        String modelMetadataLookupVOStr;
        try {
            modelMetadataLookupVO = stub.rmiService.getModelMetadataProperties();
            modelMetadataLookupVOStr = Utils.toJSON(modelMetadataLookupVO);
        } catch (InvalidServiceMethodCallException | JSONException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }

        this.suspendStub(stub);

        return modelMetadataLookupVOStr;
    }

    @WebMethod
    @Override
    public Long countQuotesInRDC(String tickerSymbol, String marketRDNExchangeCode) {
        Stub stub = this.resumeStub(true);
        Long count = null;
        try {
            count = stub.rmiService.countQuotesInRDC(tickerSymbol, marketRDNExchangeCode);
        } catch (InvalidServiceMethodCallException| JSONException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }

        this.suspendStub(stub);
        return count;
    }

    @WebMethod
    @Override
    public Long countInstrumentsInRDC(String propertyName, String propertyValue) {
        Stub stub = this.resumeStub(true);
        Long count = null;
        try {
            count = stub.rmiService.countInstrumentsInRDC(getInstrumentAttributeEnum(propertyName), propertyValue);
        } catch (InvalidServiceMethodCallException | JSONException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        } 

        this.suspendStub(stub);
        return count;
    }

    @WebMethod
    @Override
    public String rdcCheckForCcd(String entityLevel, //
            String propertyName, String propertyValue, //
            String symbol, List<String> exchangeCodeList) {
        Stub stub = this.resumeStub(true);
        String res = null;
        try {
            res = stub.rmiService.rdcCheckForCcd(
                    entityLevel, getInstrumentAttributeEnum(propertyName), propertyValue, symbol,
                    exchangeCodeList);
        } catch (InvalidServiceMethodCallException | JSONException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }

        this.suspendStub(stub);
        return res;
    }

    @WebMethod
    @Override
    public String invokeDBFunction(String functionFullName, String inputJSON) {
        Stub stub = this.resumeStub(true);
        String res = null;
        try {
            res = stub.rmiService.invokeDBFunction(
                    functionFullName, inputJSON);
        } catch (InvalidServiceMethodCallException ex) {
            throw new InvalidHttpSessionServiceException(ex);
        }
        this.suspendStub(stub);
        return res;
    }

}
