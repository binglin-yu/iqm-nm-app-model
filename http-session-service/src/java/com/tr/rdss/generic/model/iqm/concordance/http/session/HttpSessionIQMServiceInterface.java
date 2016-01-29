/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tr.rdss.generic.model.iqm.concordance.http.session;

import java.util.List;
import javax.jws.WebMethod;

/**
 *
 * @author U8015921
 */
public interface HttpSessionIQMServiceInterface {
    
    int TIME_OUT_SECONDS = 1000;

    void beginTransaction();
    
    void close();
    
    void commitTransaction();

    Long countInstrumentsInRDC(String propertyName, String propertyValue);

    Long countQuotesInRDC(String tickerSymbol, String marketRDNExchangeCode) ;

    String getCurrencyCodeByPermId(Long currencyPermId);

    Long getCurrencyPermIdByCode(String currencyCode);

    String getHistoryAttributeOfInstrumentByPermId(Long permId, String attributeName, String effectiveFrom, String effectiveTo);

    String getHistoryAttributeOfQuoteByPermId(Long permId, String attributeName, String effectiveFrom, String effectiveTo);

    String getInstrumentPermIdsByOAPermId(Long oaPermId, String effectiveFrom, String effectiveTo);

    String getInstrumentVOByPermId(Long permId, String effectiveFrom, String effectiveTo);

    Long getMarketPermIdByRDNExchangeCode(String marketRDNExchangeCode);

    List<String> getMarketRDNExchangeCodeByPermId(Long marketPermId);

    String getMasterGateLookup(String itemValue);

    String getMetadataValueByEnuPermid(Long enuPermId);

    Long getModelMetadataPermIdByLevelNameValue(String entityLevel, String propertyName, String propertyValue);

    String getModelMetadataProperties();

    String getOrgLookup(String itemValue);

    Long getQuotePermIdBySymbol(String quoteSymbol, Long marketPermId, String effectiveDate);

    String getQuotePermIdsByBaseAssetPermId(Long insPermId, String effectiveFrom, String effectiveTo);

    Long getQuotePermidByRic(String quoteRic, String effectiveFrom, String effectiveTo);

    @WebMethod
    String getQuoteVOByPermId(Long permId, String effectiveFrom, String effectiveTo);

    String getTimeZoneByMarketPermId(Long marketPermId) ;

    /**
     *
     * @return transaction status
     * @see javax.transaction.UserTransaction#getStatus()
     */
    @WebMethod
    int getTransactionStatus();

    @WebMethod
    void init(String userName, String pwd);

    String invokeDBFunction(String functionFullName, String inputJSON);

    String rdcCheckForCcd(String entityLevel, String propertyName, String propertyValue, String symbol, List<String> exchangeCodeList);

    @WebMethod
    void rollbackTransaction();

    @WebMethod
    Long saveEntityVO(String entityVOJSON);
}
