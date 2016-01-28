/**
 *
 * @author U8015921
 */
package com.tr.rdss.generic.model.iqm.concordance.rmi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.rdss.generic.model.DaoAction;
import com.tr.rdss.generic.model.iqm.Asset;
import com.tr.rdss.generic.model.iqm.DaoResult;
import com.tr.rdss.generic.model.iqm.Property;
import com.tr.rdss.generic.model.iqm.RDCCheck;
import com.tr.rdss.generic.model.iqm.concordance.EntityVO;
import com.tr.rdss.generic.model.iqm.concordance.InstrumentVO;
import com.tr.rdss.generic.model.iqm.concordance.MarketLookupVO;
import com.tr.rdss.generic.model.iqm.concordance.MarketVO;
import com.tr.rdss.generic.model.iqm.concordance.MasterGateLookupVO;
import com.tr.rdss.generic.model.iqm.concordance.ModelMetadataLookupVO;
import com.tr.rdss.generic.model.iqm.concordance.ModelMetadataVO;
import com.tr.rdss.generic.model.iqm.concordance.OrgLookupVO;
import com.tr.rdss.generic.model.iqm.concordance.QuoteVO;
import com.tr.rdss.generic.model.iqm.concordance.util.AttributeConverter;
import com.tr.rdss.generic.model.iqm.concordance.util.AttributeConverterService;
import com.tr.rdss.generic.model.iqm.concordance.util.EnumInterface;
import com.tr.rdss.generic.model.iqm.concordance.util.IQMRMITransactionException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidEntityVOException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidServiceMethodCallException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidRMIServiceException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidRawValueException;
import com.tr.rdss.generic.model.iqm.concordance.util.JSONException;
import com.tr.rdss.generic.model.iqm.concordance.util.Utils;
import java.io.IOException;
import java.util.ArrayList;
import javax.naming.Context;

import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import javax.transaction.UserTransaction;

public class RMIIQMAppModuleServices implements AttributeConverterService{

    /**
     * IQMAppModule Service
     */
    private Object appModuleService;
    /**
     * IQMLookupAppModule Service
     */
    private Object appModuleLookupService;
    /**
     * IQMNonTemporalAppModule Service
     */
    private Object appModuleNonTemporalService;

    /**
     * Context
     */
    private InitialContext ctx;
    /**
     * UserTransaction Service
     */
    private UserTransaction userTransaction;
    /**
     * properties of the application services
     */
    private Properties appServiceProperties;

    /**
     * No properties will be accepted, even the default ones
     * This constructor will provide local services instead of remote calling
     * @param userName weblogic-user
     * @param pwd   weblogic-pwd
     * @throws com.tr.rdss.generic.model.iqm.concordance.util.InvalidRMIServiceException
     */
    public RMIIQMAppModuleServices(String userName, String pwd) throws InvalidRMIServiceException {
        Properties property = new Properties();

        property.put(Context.SECURITY_PRINCIPAL, userName);
        property.put(Context.SECURITY_CREDENTIALS, pwd);

        try {
            initJNDI(appServiceProperties);
            AttributeConverter.init(this);
        } catch (NamingException e) {
            throw new InvalidRMIServiceException(e);
        } catch (InvalidServiceMethodCallException | JSONException ex) {
            throw new InvalidRMIServiceException(ex);
        }
    }

    /**
     *
     * @param properties - service properties
     * @throws InvalidRMIServiceException
     *
     * <br>
     * If null properties provided, default properties will be used. <br>
     * If non-null properties provided, only three properties will be used for RMI calls: <br>
     * a) Context.SECURITY_PRINCIPAL <br>
     * b) Context.PROVIDER_URL <br>
     * c) Context.SECURITY_CREDENTIALS <br>
     * If any of them missing in the input, the corresponding default properties will be used. <br>
     * If the input properties are NOT sufficient for initializing a service, return null. <br>
     */
    public RMIIQMAppModuleServices(Properties properties) throws InvalidRMIServiceException {
        super();
//        Utils.printTrace(false);
//        Utils.printMessage("start ... " + Utils.convertToString(new java.util.Date()));
        
        // collect the configured properties and default properties 
        Properties tmpProps = RMIIQMAppModuleServicesConstants.getDefaultProperties();
        if (properties != null) {
            if (properties.containsKey(Context.SECURITY_PRINCIPAL)) {
                tmpProps.setProperty(//
                    Context.SECURITY_PRINCIPAL, //
                    properties.getProperty(Context.SECURITY_PRINCIPAL));
            }
            if (properties.containsKey(Context.PROVIDER_URL)) {
                tmpProps.setProperty(//
                    Context.PROVIDER_URL, //
                    properties.getProperty(Context.PROVIDER_URL));
            }
            if (properties.containsKey(Context.SECURITY_CREDENTIALS)) {
                tmpProps.setProperty(//
                    Context.SECURITY_CREDENTIALS, //
                    properties.getProperty(Context.SECURITY_CREDENTIALS));
            }
        }
        if (!this.validateJNDIProperties(tmpProps)) {
            Utils.printTrace(false);
            Utils.printMessage("!!!!! invalid properties for JNDI initialization !!!!!");
            Utils.printMessage(tmpProps.toString());
//            tmpProps.list(System.out);
            // TODO , question: is it right to exit here, instead of throwing exceptions?
            return;
        } else {
            appServiceProperties = tmpProps;
//            if (appServiceProperties.containsKey("print.service.properties")
//                && appServiceProperties.get("print.service.properties").toString().equalsIgnoreCase("Y")) {
//                Utils.printMessage(appServiceProperties.toString());
////                appServiceProperties.list(System.out);
//            }
        }
        try {
            initJNDI(appServiceProperties);
//            CurrencyLookupVO.set(this.getCurrencyLookup());
//            MarketLookupVO.set(this.getMarketLookup());
//            ModelMetadataLookupVO.set(this.getModelMetadataLookup());
            AttributeConverter.init(this);
        } catch (NamingException e) {
            throw new InvalidRMIServiceException(e);
        } catch (InvalidServiceMethodCallException | JSONException ex) {
            throw new InvalidRMIServiceException(ex);
        } 
    }

    /**
     *
     * @param properties context properties for initializing rmi services
     * @return verification result
     * <br>
     * Four properties are mandatory:<br>
     * a) Context.PROVIDER_URL<br>
     * b) Context.SECURITY_PRINCIPAL<br>
     * c) Context.SECURITY_CREDENTIALS<br>
     * d) Context.INITIAL_CONTEXT_FACTORY<br>
     */
    private boolean validateJNDIProperties(Properties properties) {
        if (properties.containsKey(Context.PROVIDER_URL) //
            && properties.containsKey(Context.SECURITY_PRINCIPAL) //
            && properties.containsKey(Context.SECURITY_CREDENTIALS) //
            && properties.containsKey(Context.INITIAL_CONTEXT_FACTORY) //
            ) {
            return true;
        } else {
            Utils.printTrace(false);
            Utils.printMessage("! four properties are manadatory: !\n +"
                + "\t" + Context.PROVIDER_URL + "\n"
                + "\t" + Context.SECURITY_PRINCIPAL + "\n"
                + "\t" + Context.SECURITY_CREDENTIALS + "\n"
                + "\t" + Context.INITIAL_CONTEXT_FACTORY + "\n");
            return false;
        }
    }

    /**
     *
     * @return appModuleService
     */
    private Object getIQMAppService() {
        return appModuleService;
    }

    /**
     *
     * @return appModuleLookupService
     */
    private Object getIQMLookupAppModuleService() {
        return appModuleLookupService;
    }

    /**
     *
     * @return appModuleNonTemporalService
     */
    private Object getIQMNonTemporalAppModuleService() {
        return appModuleNonTemporalService;
    }

    /**
     *
     * @return userTransaction
     */
    private UserTransaction getUserTransaction() {
        return userTransaction;
    }

    /**
     *
     * @throws IQMRMITransactionException
     * @see #beginTransaction(int)
     * <br>
     * default timeout-seconds is 30
     */
    public void beginTransaction() throws IQMRMITransactionException {
        this.beginTransaction(30);
    }

    /**
     *
     * @param timeOutSeconds time out in seconds for the transaction
     * @throws IQMRMITransactionException
     * <br>
     * It begin the transaction and set the transaction time out.
     */
    public void beginTransaction(int timeOutSeconds) throws IQMRMITransactionException {
        try {
            getUserTransaction().setTransactionTimeout(timeOutSeconds);
            getUserTransaction().begin();
        } catch (NotSupportedException | SystemException e) {
            throw new IQMRMITransactionException(e);
        }
    }

    /**
     *
     * @return transaction status
     * @throws SystemException
     * @see javax.transaction.UserTransaction#getStatus()
     */
    public int getTransactionStatus() throws SystemException {
        return getUserTransaction().getStatus();
    }

    /**
     *
     * @throws IQMRMITransactionException
     * <br>
     * Roll back the transaction
     */
    public void rollbackTransaction() throws IQMRMITransactionException {
        try {
            getUserTransaction().rollback();
        } catch (IllegalStateException | SecurityException | SystemException e) {
            throw new IQMRMITransactionException(e);
        }
    }

    /**
     *
     * @throws IQMRMITransactionException
     * <br>
     * Commit the transaction
     */
    public void commitTransaction() throws IQMRMITransactionException {
        try {
            getUserTransaction().commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException e) {
            throw new IQMRMITransactionException(e);
        }
    }

    /**
     *
     * @throws InvalidRMIServiceException
     * <br>
     * Close the service.
     */
    public void close() throws InvalidRMIServiceException {
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException ex) {
                throw new InvalidRMIServiceException(ex);
            }
        }
    }

    /**
     *
     * @param properties context properties for services
     * @throws NamingException
     */
    private void initContext(Properties properties) throws NamingException {
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
//                Utils.printTrace(e);
                throw e;
            }
        }

//        jndiProps.put("weblogic.jndi.responseReadTimeout", "100");
        try {
            if (properties != null) {
                Hashtable<String, String> jndiProps = null;
                jndiProps = new Hashtable<String, String>();

                jndiProps.put(Context.PROVIDER_URL, properties.getProperty(Context.PROVIDER_URL));
                jndiProps.put(Context.SECURITY_CREDENTIALS, properties.getProperty(Context.SECURITY_CREDENTIALS));
                jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, properties.getProperty(Context.INITIAL_CONTEXT_FACTORY));
                jndiProps.put(Context.SECURITY_PRINCIPAL, properties.getProperty(Context.SECURITY_PRINCIPAL));
                jndiProps.put("dicated.connection", "true");
                ctx = new InitialContext(jndiProps);
            }
            else
                ctx = new InitialContext();

            Hashtable hashTable = ctx.getEnvironment();
            Utils.printTrace(false);
            Utils.printMessage(hashTable.toString());
        } catch (NamingException e) {
//            Utils.printTrace(false);
//            Utils.printTrace(e);
//            Utils.printMessage("! service stop !");
            throw e;
        }
    }
    private void initJNDI(Properties properties) throws NamingException {
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
//                Utils.printTrace(e);
                throw e;
            }
        }

//        jndiProps.put("weblogic.jndi.responseReadTimeout", "100");
        try {
            if (properties != null) {
                Hashtable<String, String> jndiProps = null;
                jndiProps = new Hashtable<String, String>();

                jndiProps.put(Context.PROVIDER_URL, properties.getProperty(Context.PROVIDER_URL));
                jndiProps.put(Context.SECURITY_CREDENTIALS, properties.getProperty(Context.SECURITY_CREDENTIALS));
                jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, properties.getProperty(Context.INITIAL_CONTEXT_FACTORY));
                jndiProps.put(Context.SECURITY_PRINCIPAL, properties.getProperty(Context.SECURITY_PRINCIPAL));
                jndiProps.put("dicated.connection", "true");
                ctx = new InitialContext(jndiProps);
            }
            else
                ctx = new InitialContext();

            Hashtable hashTable = ctx.getEnvironment();
            Utils.printTrace(false);
            Utils.printMessage(hashTable.toString());
        } catch (NamingException e) {
//            Utils.printTrace(false);
//            Utils.printTrace(e);
//            Utils.printMessage("! service stop !");
            throw e;
        }

        // connect to the server
        try {
            NamingEnumeration<NameClassPair> list = null;
            // list all avaiable names
            if (this.appServiceProperties!=null && "Y".equals(this.appServiceProperties.getProperty("print.all.available.services").toUpperCase())) {
                Utils.printMessage("=======================================");
                list = ctx.list("");
                while (list.hasMore()) {

                    try {
                        NameClassPair tt = list.next();
                        Object ttt = ctx.lookup(tt.getName());
                        Utils.printMessage(ttt.getClass() + ":\t" + tt.getName());
                    } catch (Exception e) {
                        Utils.printTrace(e);
                    }
                }
                Utils.printMessage("=======================================");
            }

            // get user transaction
            userTransaction = (UserTransaction) ctx.lookup(RMIIQMAppModuleServicesConstants.RMIUserTransaction);

            // get general app service
            this.appModuleService = ctx.lookup(RMIIQMAppModuleServicesConstants.appModuleGeneralServiceName);
            // get lookup service
            this.appModuleLookupService = ctx.lookup(RMIIQMAppModuleServicesConstants.appModuleLookupServiceName);
            // get non temporal service
            this.appModuleNonTemporalService = ctx.lookup(RMIIQMAppModuleServicesConstants.appModuleNonTemporalServiceName);

            if (this.appServiceProperties!= null && "Y".equals(this.appServiceProperties.getProperty("print.service.available.methods").toUpperCase())) {
                Utils.printMessage("================ this.appModuleService ================");
                Utils.printMethods(this.appModuleService);
                Utils.printMessage("================ this.appModuleLookupService ================");
                Utils.printMethods(this.appModuleLookupService);
                Utils.printMessage("================ this.appModuleNonTemporalService ================");
                Utils.printMethods(this.appModuleNonTemporalService);
            }

        } catch (Exception e) {
            Utils.printTrace(e);
            Utils.printMessage(e);
            throw e;
        }

    }

    /**
     *
     * @param permId instrument perm id
     * @param effectiveFromDate effective from
     * @param effectiveToDate effective to
     * @return InstrumentVO with all the info of the requested instrument.
     * @throws JSONException
     * @throws InvalidRawValueException
     * @throws InvalidServiceMethodCallException
     * <br>
     * Only perm ids will be provided for all the associated quotes). <br>
     * All attributes will have denormalized values instead of ids. <br>
     * E.g. "Published" (TRCS name) will be provided for ADMIN_STATUS instead of 1010003 (TRCS perm id).
     */
    public InstrumentVO getInstrumentVOByPermId(Long permId,
        Date effectiveFromDate, Date effectiveToDate) throws JSONException, InvalidRawValueException, InvalidServiceMethodCallException {

        String entityVOStr = (String) Utils.callMethod(this.getIQMAppService(), //
            "getInstrumentVOJSONByPermId", //
            new Object[]{//
                permId.toString(),//
                Utils.convertToString(effectiveFromDate), //
                Utils.convertToString(effectiveToDate) //
            });

        InstrumentVO entityVO = (InstrumentVO) Utils.getEntityVOFromJSON(entityVOStr);
        if (entityVO != null) {
            entityVO.setIsNormalized(null);

            AttributeConverter.convertEntityVO(entityVO, false);
        }
        return entityVO;
    }

    /**
     *
     * @param permId quote perm id
     * @param effectiveFromDate effective from
     * @param effectiveToDate effective to
     * @return QuoteVO with all the info of the requested quote
     * @throws JSONException
     * @throws InvalidRawValueException
     * @throws InvalidServiceMethodCallException
     * <br>
     * Only its BASE_ASEET instrument will have full info provided, while other related issues (if any) will just have
     * perm ids). <br>
     * All attributes will have denormalized values instead of ids. <br>
     * E.g. "Published" (TRCS name) will be provided for ADMIN_STATUS instead of 1010003 (TRCS perm id).
     *
     */
    public QuoteVO getQuoteVOByPermId(Long permId, Date effectiveFromDate,
        Date effectiveToDate) throws JSONException, InvalidRawValueException, InvalidServiceMethodCallException {

        String entityVOStr = (String) Utils.callMethod(this.getIQMAppService(), //
            "getQuoteVOJSONByPermId", //
            new Object[]{//
                permId.toString(),//
                Utils.convertToString(effectiveFromDate), //
                Utils.convertToString(effectiveToDate) //
            });

        QuoteVO entityVO = (QuoteVO) Utils.getEntityVOFromJSON(entityVOStr);
        if (entityVO != null) {
            entityVO.setIsNormalized(null);
            AttributeConverter.convertEntityVO(entityVO, false);
        }

        return entityVO;
    }

    /**
     *
     * @param quoteRic quote ric
     * @param effectiveFromDate effective from of ric
     * @param effectiveToDate effective from of ric
     * @return quote perm id
     * @throws InvalidServiceMethodCallException
     */
    public Long getQuotePermidByRic(String quoteRic, Date effectiveFromDate,
        Date effectiveToDate) throws InvalidServiceMethodCallException {
        Long permId = (Long) Utils.callMethod(this.getIQMAppService(), //
            "getQuotePermidByRic", //
            new Object[]{quoteRic, //                
                Utils.convertToString(effectiveFromDate),
                Utils.convertToString(effectiveToDate)
            });

        return permId;

    }

//    public Long getQuotePermIdBySymbol(String quoteSymbol, String marketRDNExchangeCode,
//        Date effectiveDate) {
//
//        Long permId = (Long) Utils.callMethod(this.getIQMAppService(), //
//            "getQuotePermIdBySymbolAndMktRDNCode", //
//            new Object[]{quoteSymbol, //                
//                marketRDNExchangeCode,//
//                Utils.convertToString(effectiveDate)
//            });
//
//        return permId;
//
//    }
    /**
     *
     * @param quoteSymbol quote ticker symbol
     * @param marketPermId market perm id
     * @param effectiveDate effective date of symbol and quote market info
     * @return quote perm id
     * @throws InvalidServiceMethodCallException
     */
    public Long getQuotePermIdBySymbol(String quoteSymbol, Long marketPermId,
        Date effectiveDate) throws InvalidServiceMethodCallException {

        Long permId = (Long) Utils.callMethod(this.getIQMAppService(), //
            "getQuotePermIdBySymbolAndMktPermId", //
            new Object[]{quoteSymbol, //                
                marketPermId,//
                Utils.convertToString(effectiveDate)
            });

        return permId;

    }

//    public List<Long> getInstrumentPermIdsByOAName(String oaName, Date effectiveFromDate,
//        Date effectiveToDate) {
//        
//        String permIdsStr = (String) Utils.callMethod(this.getIQMAppService(), //
//            "getInstrumentPermIdsByOAName", //
//            new Object[]{oaName, //                
//                Utils.convertToString(effectiveFromDate),
//                Utils.convertToString(effectiveToDate)
//            });
//        
//        return Utils.getLongListFromString(permIdsStr);
//    }
    /**
     *
     * @param oaPermId organization perm id
     * @param effectiveFromDate effective from of rships between org and issues
     * @param effectiveToDate effective to of rships between org and issues
     * @return instrument perm id list
     * @throws InvalidServiceMethodCallException
     */
    public List<Long> getInstrumentPermIdsByOAPermId(Long oaPermId,
        Date effectiveFromDate,
        Date effectiveToDate) throws InvalidServiceMethodCallException {
        String permIdsStr = (String) Utils.callMethod(this.getIQMAppService(), //
            "getInstrumentPermIdsByOrgPermId", //
            new Object[]{oaPermId.toString(), //                
                Utils.convertToString(effectiveFromDate),
                Utils.convertToString(effectiveToDate)
            });

        return Utils.getLongListFromString(permIdsStr);

    }

    /**
     *
     * @param insPermId instrument perm id
     * @param effectiveFromDate effective from of rships between the base asset instrument and quotes
     * @param effectiveToDate effective to of rships between the base asset instrument and quotes
     * @return a list of Long value (quote perm ids which have the input instrument as "BASE ASSET")
     * @throws InvalidServiceMethodCallException
     */
    public List<Long> getQuotePermIdsByBaseAssetPermId(Long insPermId,
        Date effectiveFromDate,
        Date effectiveToDate) throws InvalidServiceMethodCallException {
        String permIdsStr = (String) Utils.callMethod(this.getIQMAppService(), //
            "getQuotePermIdsByBaseAssetPermId", //
            new Object[]{insPermId.toString(), //                
                Utils.convertToString(effectiveFromDate),
                Utils.convertToString(effectiveToDate)
            });

        return Utils.getLongListFromString(permIdsStr);
    }

    /**
     *
     * @param entityVO entityVO which holds the business changes
     * @return entity perm id
     * @throws InvalidEntityVOException
     * @throws JSONException
     * @throws InvalidRawValueException
     * @throws InvalidServiceMethodCallException
     * <br>
     * It calls entityMaintain to save the change to db. <br>
     * If the db write succeeds, return quote perm id; else throw InvalidEntityVOException.
     */
    public Long saveEntityVO(EntityVO entityVO) throws InvalidEntityVOException, JSONException, InvalidRawValueException, InvalidServiceMethodCallException {
        ObjectMapper mapper = new ObjectMapper();
        String assetStr = null;
        if (entityVO == null) {
            return null;
        }

        EntityVO tmpEntityVO = AttributeConverter.convertEntityVO(entityVO, true);
        try {
            assetStr = mapper
                .writeValueAsString((Asset) (tmpEntityVO.getEntity()));

//            // output for testing
//            Utils.printTrace(false);
//            Utils.printMessage(assetStr);
        } catch (IOException ex) {
//            Utils.printTrace(ex);
//            Utils.printMessage(ex);
            throw new InvalidEntityVOException(ex);
        }

        String entityMaintainStr = (String) Utils.callMethod(//
            this.getIQMAppService(), //
            "entityMaintain", //
            new Object[]{assetStr});

        DaoResult daoResult = Utils.fromJSON(entityMaintainStr);

        if (daoResult.isSucc()) {
            return Long.parseLong(daoResult.getKey_value());
        } else {
            throw new InvalidEntityVOException(daoResult.getMessage(),
                entityMaintainStr);
        }

    }

    /**
     *
     * @param permId quote perm id
     * @param attributeEnum quote attribute enumeration
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * @return quoteVO with the history of the required attribute within the specified period; return valid object with
     * no attributes if no data found
     * @throws JSONException
     * @throws InvalidRawValueException
     * @throws InvalidServiceMethodCallException
     */
    public QuoteVO getHistoryAttributeOfQuoteByPermId(Long permId, //
        QuoteVO.AttributesEnum attributeEnum, //
        Date effectiveFrom, Date effectiveTo
    ) throws JSONException, InvalidRawValueException, InvalidServiceMethodCallException {
        String entityVOStr = (String) Utils.callMethod(//
            this.getIQMNonTemporalAppModuleService(),//
            "getHistoryEntityVOJSONByAttributeNameAndDateRange",//
            new Object[]{QuoteVO.entityLevel, permId.toString(), attributeEnum.getEnumName(), Utils.convertToString(effectiveFrom),
                Utils.convertToString(effectiveTo)});

        QuoteVO quoteVO = (QuoteVO) Utils.getEntityVOFromJSON(entityVOStr);
        if (quoteVO != null) {
            quoteVO.setIsNormalized(null);
            AttributeConverter.convertEntityVO(quoteVO, false);
        }
        return quoteVO;
    }

    /**
     *
     * @param permId quote perm id
     * @param attributeEnum quote attribute enumeration
     * @return quoteVO with the full history of the required attribute; return valid object with no attributes if no
     * data found
     * @throws JSONException
     * @throws InvalidRawValueException
     * @throws InvalidServiceMethodCallException
     */
    public QuoteVO getHistoryAttributeOfQuoteByPermId(Long permId, //
        QuoteVO.AttributesEnum attributeEnum
    ) throws JSONException, InvalidRawValueException, InvalidServiceMethodCallException {
        String entityVOStr = (String) Utils.callMethod(//
            this.getIQMNonTemporalAppModuleService(),//
            "getHistoryEntityVOJSONByAttributeName",//
            new Object[]{QuoteVO.entityLevel, permId.toString(), attributeEnum.getEnumName()});

        QuoteVO quoteVO = (QuoteVO) Utils.getEntityVOFromJSON(entityVOStr);
        if (quoteVO != null) {
            quoteVO.setIsNormalized(null);
            AttributeConverter.convertEntityVO(quoteVO, false);
        }
        return quoteVO;
    }

    /**
     *
     * @param permId instrument perm id
     * @param attributeEnum instrument attribute enumeration
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * @return instrumentVO with the history of the required attribute within the specified period; return valid object
     * with no attributes if no data found
     * @throws JSONException
     * @throws InvalidRawValueException
     * @throws InvalidServiceMethodCallException
     */
    public InstrumentVO getHistoryAttributeOfInstrumentByPermId(Long permId,
        InstrumentVO.AttributesEnum attributeEnum, //
        Date effectiveFrom, Date effectiveTo
    ) throws JSONException, InvalidRawValueException, InvalidServiceMethodCallException {
        String entityVOStr = (String) Utils.callMethod(//
            this.getIQMNonTemporalAppModuleService(),//
            "getHistoryEntityVOJSONByAttributeNameAndDateRange",//
            new Object[]{InstrumentVO.entityLevel, permId.toString(), attributeEnum.getEnumName(), Utils.convertToString(effectiveFrom),
                Utils.convertToString(effectiveTo)});

        InstrumentVO instrumentVO = (InstrumentVO) Utils.getEntityVOFromJSON(entityVOStr);
        if (instrumentVO != null) {
            instrumentVO.setIsNormalized(null);
            AttributeConverter.convertEntityVO(instrumentVO, false);
        }
        return instrumentVO;
    }

    /**
     *
     * @param permId instrument perm id
     * @param attributeEnum instrument attribute enumeration
     * @return instrumentVO with the full history of the required attribute; return valid object with no attributes if
     * no data found
     * @throws JSONException
     * @throws InvalidRawValueException
     * @throws InvalidServiceMethodCallException
     */
    public InstrumentVO getHistoryAttributeOfInstrumentByPermId(Long permId,
        InstrumentVO.AttributesEnum attributeEnum
    ) throws JSONException, InvalidRawValueException, InvalidServiceMethodCallException {
        String entityVOStr = (String) Utils.callMethod(//
            this.getIQMNonTemporalAppModuleService(),//
            "getHistoryEntityVOJSONByAttributeName",//
            new Object[]{InstrumentVO.entityLevel, permId.toString(), attributeEnum.getEnumName()});

        InstrumentVO instrumentVO = (InstrumentVO) Utils.getEntityVOFromJSON(entityVOStr);
        if (instrumentVO != null) {
            instrumentVO.setIsNormalized(null);
            AttributeConverter.convertEntityVO(instrumentVO, false);
        }
        return instrumentVO;
    }

//    /**
//     * 
//     * @param permId quote perm id
//     * @param attributeEnum quote attribute enumeration
//     * @return quoteVO with the full history of the required attribute; return valid object with no attributes if no data found
//     * @throws JSONException 
//     * @throws InvalidRawValueException
//     * @throws InvalidRMIMethodCallException
//     */
//    public QuoteVO getHistoryAttributeOfQuoteByPermId(Long permId,
//        QuoteVO.AttributesEnum attributeEnum) throws JSONException, InvalidRawValueException, InvalidRMIMethodCallException {
//        String entityVOStr = (String) Utils.callMethod(//
//            this.getIQMNonTemporalAppModuleService(),//
//            "getHistoryQuoteVOJSONByAttributeName",//
//            new Object[]{permId.toString(), attributeEnum.getEnumName()});
//
//        QuoteVO quoteVO = (QuoteVO) Utils.getEntityVOFromJSON(entityVOStr);
//        if (quoteVO!=null) {
//            quoteVO.setIsNormalized(null);
//            AttributeConverter.convertEntityVO(quoteVO, false);
//        }
//        return quoteVO;
//    }
//    /**
//     * 
//     * @param permId instrument perm id
//     * @param attributeEnum instrument attribute enumeration
//     * @return instrumentVO with the full history of the required attribute; return valid object with no attributes if no data found
//     * @throws JSONException 
//     * @throws InvalidRawValueException
//     * @throws InvalidRMIMethodCallException
//     */
//    public InstrumentVO getHistoryAttributeOfInstrumentByPermId(Long permId,
//        InstrumentVO.AttributesEnum attributeEnum) throws JSONException, InvalidRawValueException, InvalidRMIMethodCallException {
//        String entityVOStr = (String) Utils.callMethod(//
//            this.getIQMNonTemporalAppModuleService(),//
//            "getHistoryInstrumentVOJSONByAttributeName",//
//            new Object[]{permId.toString(), attributeEnum.getEnumName()});
//
//        InstrumentVO instrumentVO = (InstrumentVO) Utils.getEntityVOFromJSON(entityVOStr);
//        if (instrumentVO!=null) {
//            instrumentVO.setIsNormalized(null);
//            AttributeConverter.convertEntityVO(instrumentVO, false);
//        }
//        return instrumentVO;
//    }
    /**
     *
     * @param itemValue fuzzy search value
     * @return
     * @throws JSONException
     * @throws InvalidServiceMethodCallException
     * <br>
     * The fuzzy search is case insensitive. The search covers <br>
     * a) entity perm ids <br>
     * b) entity identifiers <br>
     * c) entity nonunique identifiers <br>
     * d) entity names <br>
     * <br>
     * More details, please check the IQM native master service.
     *
     */
    public MasterGateLookupVO getMasterGateLookup(String itemValue) //
        throws JSONException, InvalidServiceMethodCallException {
        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(), //
            "getMasterGateLookupJSON", //
            new Object[]{itemValue});

        return (MasterGateLookupVO) Utils.getEntityVOFromJSON(resultStr);
    }

    /**
     *
     * @param itemValue organization long name for fuzzy search (case insensitive)
     * @return
     * @throws JSONException
     * @throws InvalidServiceMethodCallException
     */
    public OrgLookupVO getOrgLookup(String itemValue) throws JSONException, InvalidServiceMethodCallException {
        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(), //
            "getOrgLookupJSON", //
            new Object[]{itemValue});

        return (OrgLookupVO) Utils.getEntityVOFromJSON(resultStr);
    }

//    public CurrencyLookupVO getCurrencyLookup() throws JSONException {
//        if ("Y".equals(appServiceProperties.getProperty("CurrencyLookup.use.local").toUpperCase())) {
//            return LocalCurrencyLookup.getInstance();
//        }
//        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(),//
//            "getCurrencyLookupJSON", //
//            new Object[]{});
//        
//        return (CurrencyLookupVO) Utils.getEntityVOFromJSON(resultStr);
//    }
//    public MarketLookupVO getMarketLookup() throws JSONException {
//        
//        if ("Y".equals(appServiceProperties.getProperty("MarketLookup.use.local").toUpperCase())) {
//            return LocalMarketLookup.getInstance();
//        }
//        
//        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(),//
//            "getMarketLookupJSON", //
//            new Object[]{});
//        
//        return (MarketLookupVO) Utils.getEntityVOFromJSON(resultStr);
//    }
//    public ModelMetadataLookupVO getModelMetadataLookup() throws JSONException {
//        if ("Y".equals(appServiceProperties.getProperty("ModelMetadataLookup.use.local").toUpperCase())) {
//            return LocalModelMetadataLookup.getInstance();
//        }
//        
//        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(),//
//            "getModelMetadataLookupJSON", //
//            new Object[]{});
//        
//        return (ModelMetadataLookupVO) Utils.getEntityVOFromJSON(resultStr);
//    }
    /**
     *
     * @param currencyPermId TRCS currency perm id
     * @return TRCS currency code
     * @throws InvalidServiceMethodCallException
     */
    @Override
    public String getCurrencyCodeByPermId(Long currencyPermId) throws InvalidServiceMethodCallException {
        String code = (String) Utils.callMethod(this.getIQMLookupAppModuleService(),//
            "getCurrencyCodeByPermId", //
            new Object[]{currencyPermId});
        return code;
    }

    /**
     *
     * @param currencyCode TRCS currency code
     * @return TRCS currency perm id
     * @throws InvalidServiceMethodCallException
     */
    @Override
    public Long getCurrencyPermIdByCode(String currencyCode) throws InvalidServiceMethodCallException {
        Long permId = (Long) Utils.callMethod(this.getIQMLookupAppModuleService(),//
            "getCurrencyPermIdByCode", //
            new Object[]{currencyCode});
        return permId;
    }

    /**
     *
     * @param marketPermId market perm id
     * @return market RDN exchange code list
     * @throws JSONException
     * @throws InvalidServiceMethodCallException
     */
    public List<String> getMarketRDNExchangeCodeByPermId(Long marketPermId) throws JSONException, InvalidServiceMethodCallException {
        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(),//
            "getMarketRDNExchangeCodeLookupByPermId", //
            new Object[]{marketPermId});

        MarketLookupVO marketLookupVO = (MarketLookupVO) Utils.getEntityVOFromJSON(resultStr);
        List<String> list = new ArrayList<String>();
        for (MarketVO marketVO : marketLookupVO.getElementList()) {
            list.add(marketVO.getName());
        }
        return list;
    }

    /**
     *
     * @param marketRDNExchangeCode market RDN exchange coode
     * @return market perm id
     * @throws InvalidServiceMethodCallException
     */
    public Long getMarketPermIdByRDNExchangeCode(String marketRDNExchangeCode) throws InvalidServiceMethodCallException {
        if (marketRDNExchangeCode == null || marketRDNExchangeCode.equals("")) {
            return null;
        }
        Long permId = (Long) Utils.callMethod(this.getIQMLookupAppModuleService(),//
            "getMarketPermIdByRDNExchangeCode", //
            new Object[]{marketRDNExchangeCode});
        return permId;
    }

    /**
     *
     * @param marketPermId market perm id
     * @return market time zone name
     * @throws InvalidServiceMethodCallException
     */
    public String getTimeZoneByMarketPermId(Long marketPermId) throws InvalidServiceMethodCallException {
        if (marketPermId == null) {
            return null;
        }
        String res = (String) Utils.callMethod(this.getIQMLookupAppModuleService(),//
            "getTimeZoneByMarketPermId", //
            new Object[]{marketPermId});
        return res;
    }

    /**
     *
     * @param enuPermId enumeration perm id
     * @return enumeration value (DB filed: iqm_nav.mtd_domain_mv.enu_uniquename)
     * @throws InvalidServiceMethodCallException
     */
    @Override
    public String getMetadataValueByEnuPermid(Long enuPermId) throws InvalidServiceMethodCallException {
        String value = (String) Utils.callMethod(this.getIQMLookupAppModuleService(),//
            "getMetadataValueByEnuPermid", //
            new Object[]{enuPermId});
        return value;
    }

    /**
     *
     * @param entityLevel entity level (DB filed: iqm_nav.model_map_mv.bdm_structure)
     * @param propertyName property name (DB filed: iqm_nav.model_map_mv.bdm_pro_name)
     * @param propertyValue property value (DB filed: iqm_nav.mtd_domain_mv.enu_uniquename)
     * @return enumeration perm id (DB filed: iqm_nav.mtd_domain_mv.enu_permid)
     * @throws InvalidServiceMethodCallException service exceptions
     */
    @Override
    public Long getModelMetadataPermIdByLevelNameValue(String entityLevel, String propertyName,
        String propertyValue) throws InvalidServiceMethodCallException {
        Long permId = (Long) Utils.callMethod(this.getIQMLookupAppModuleService(),//
            "getModelMetadataPermIdByLevelNameValue", //
            new Object[]{entityLevel, propertyName, propertyValue});
        return permId;
    }

    /**
     *
     * @return entity properties, value of which are from ModelMetadata lookup (DB object: iqm_nav.mtd_domain_mv)
     * @throws InvalidServiceMethodCallException
     * @throws JSONException
     * <br>
     * Return the local copy if context property "ModelMetadataLookup.use.local" is "Y", otherwise retrieve data from
     * db.
     */
    @Override
    public List<ModelMetadataVO> getModelMetadataProperties() throws InvalidServiceMethodCallException {
        List<ModelMetadataVO> modelMetadataVOList = new ArrayList<ModelMetadataVO>();
        List<ModelMetadataVO> list = null;

        String resultStr = (String) Utils.callMethod(this.getIQMLookupAppModuleService(),//
            "getModelMetadataPropertiesJSON", //
            new Object[]{});

        ModelMetadataLookupVO tmp;
        try {
            tmp = (ModelMetadataLookupVO) Utils.getEntityVOFromJSON(resultStr);
            if (tmp != null) {
                list = tmp.getElementList();
            }
        } catch (JSONException ex) {
//                Utils.printTrace(false);
//                Utils.printMessage(ex);
            throw new InvalidServiceMethodCallException(ex);
        }

        // always dedup the list
        HashSet<String> keySet = new HashSet<String>();
        if (list == null) {
            return modelMetadataVOList;
        }

        for (ModelMetadataVO modelMetadataVO : list) {
            String key = modelMetadataVO.getEntityLevel() + "." + modelMetadataVO.getPropertyName();
            if (!keySet.contains(key)) {
                keySet.add(key);
                modelMetadataVOList.add(new ModelMetadataVO(null, null, null, null, null, null, modelMetadataVO.getPropertyName(), modelMetadataVO.getEntityLevel()));
            }
        }
        return modelMetadataVOList;
    }

    /**
     *
     * @param tickerSymbol quote ticker symbol
     * @param marketRDNExchangeCode market RDN exchange code
     * @return count of matched quotes
     * @throws InvalidServiceMethodCallException
     * @throws JSONException
     * <br>
     * Count the matched quotes in RDC, with the given ticker symbol and market RDN exchange code. <br>
     * Refer to the IQM native master service for the details. <br>
     */
    public Long countQuotesInRDC(String tickerSymbol, String marketRDNExchangeCode) throws InvalidServiceMethodCallException, JSONException {
        String res = (String) Utils.callMethod(this.getIQMAppService(),//
            "getRDCCheckJSonForQuote", //
            new Object[]{tickerSymbol, marketRDNExchangeCode});
        DaoResult daoResult = Utils.fromJSON(res);
        if (daoResult.isSucc()) {
            Long cnt = Long.parseLong(daoResult.getKey_value());
            return cnt;
        } else {
            throw new InvalidServiceMethodCallException(daoResult.getMessage());
        }
    }

    /**
     *
     * @param propertyEnum property enumeration
     * @param propertyValue property value
     * @return count of matched instruments
     * @throws InvalidServiceMethodCallException
     * @throws JSONException
     *
     * <br>
     * Count the matched instruments in RDC. <br>
     * Refer to the IQM native master service for the details. <br>
     */
    public Long countInstrumentsInRDC(EnumInterface propertyEnum, String propertyValue) throws InvalidServiceMethodCallException, JSONException {
        String res = (String) Utils.callMethod(this.getIQMAppService(),//
            "getRDCCheckJSonForInstrument", //
            new Object[]{propertyEnum.getEnumName(), propertyValue});
        DaoResult daoResult = Utils.fromJSON(res);
        if (daoResult.isSucc()) {
            Long cnt = Long.parseLong(daoResult.getKey_value());
            return cnt;
        } else {
            throw new InvalidServiceMethodCallException(daoResult.getMessage());
        }
    }

    /**
     *
     * @param entityLevel entity level
     * @param propertyEnum property enumeration
     * @param propertyValue property value
     * @param symbol symbol
     * @param exchangeCodeList exchange code list
     * @return response of the rdcCheckForCcd function of IQM native master service
     *
     * @throws InvalidServiceMethodCallException
     * @throws JSONException
     */
    public String rdcCheckForCcd(String entityLevel, //
        EnumInterface propertyEnum, String propertyValue, //
        String symbol, List<String> exchangeCodeList) throws InvalidServiceMethodCallException, JSONException {
        if (!(QuoteVO.entityLevel.equals(entityLevel) || InstrumentVO.entityLevel.equals(entityLevel))) {
            throw new IllegalArgumentException("Input entityLevel (" + entityLevel + ") is invalid! Valid inputs: " + InstrumentVO.entityLevel + "," + QuoteVO.entityLevel);
        }
        RDCCheck rdcCheck = new RDCCheck(entityLevel);
        DaoAction action = DaoAction.NONE;

        Property property = new Property(propertyEnum.getEnumName(), propertyValue, action);
        rdcCheck.assignProperty(property);

        if (symbol != null && !symbol.trim().equals("")) {
            Property symbolProperty = new Property(QuoteVO.AttributesEnum.TICKER_SYMBOL.getEnumName(), symbol.trim(), action);
            rdcCheck.assignProperty(symbolProperty);
        }
        if (exchangeCodeList != null && !exchangeCodeList.isEmpty()) {
            StringBuilder exchangeCodes = new StringBuilder();
            for (String exchangeCode : exchangeCodeList) {
                exchangeCodes.append(exchangeCode).append("|");
            }
            Property codeProperty = new Property(QuoteVO.AttributesEnum.TRADES_ON_SUBMARKET.getEnumName(), exchangeCodes.toString(), DaoAction.NONE);
            rdcCheck.assignProperty(codeProperty);
        }
        String result = (String) Utils.callMethod(this.getIQMAppService(),//
            "rdcCheckForCcd", //
            new Object[]{Utils.toJSON(rdcCheck)});
        return result;
    }

    /**
     *
     * @param functionFullName DB function full name, in format "schema_name"."package_name"."function_name" or
     * "schema_name"."function_name
     * @param inputJSON input string parameter (JSON string) for the db function
     * @return response of the calling db function
     * @throws InvalidServiceMethodCallException
     * <br>
     * If service succeeds, return the response of the calling db function; else raise Exception with the corresponding
     * error message.
     */
    public String invokeDBFunction(String functionFullName, String inputJSON) throws InvalidServiceMethodCallException {
        String res = (String) Utils.callMethod(this.getIQMAppService(),//
            "invokeDBFunction", //
            new Object[]{functionFullName, inputJSON});
        return res;
    }

}
