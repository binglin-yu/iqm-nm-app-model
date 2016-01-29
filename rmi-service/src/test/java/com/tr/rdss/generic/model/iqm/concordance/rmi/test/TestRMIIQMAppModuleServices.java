/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tr.rdss.generic.model.iqm.concordance.rmi.test;

/**
 *
 * @author U8015921
 */
//import org.boon.json.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.rdss.generic.model.DaoAction;
import com.tr.rdss.generic.model.iqm.Asset;
import com.tr.rdss.generic.model.iqm.Attribute;
import com.tr.rdss.generic.model.iqm.Entity;
import com.tr.rdss.generic.model.iqm.ModelUtil;
import com.tr.rdss.generic.model.iqm.Property;
//import com.tr.rdss.generic.model.iqm.concordance.CurrencyLookupVO;
import com.tr.rdss.generic.model.iqm.concordance.EntityVO;
import com.tr.rdss.generic.model.iqm.concordance.InstrumentVO;
//import com.tr.rdss.generic.model.iqm.concordance.MarketLookupVO;
import com.tr.rdss.generic.model.iqm.concordance.MasterGateLookupVO;
import com.tr.rdss.generic.model.iqm.concordance.MasterGateVO;
//import com.tr.rdss.generic.model.iqm.concordance.ModelMetadataLookupVO;
import com.tr.rdss.generic.model.iqm.concordance.ModelMetadataVO;
import com.tr.rdss.generic.model.iqm.concordance.OrgLookupVO;
import com.tr.rdss.generic.model.iqm.concordance.OrgVO;
import com.tr.rdss.generic.model.iqm.concordance.QuoteVO;
import com.tr.rdss.generic.model.iqm.concordance.util.DateParseException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidEntityVOException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidRawValueException;
import com.tr.rdss.generic.model.iqm.concordance.util.JSONException;

import java.util.Date;

import org.junit.Test;
import com.tr.rdss.generic.model.iqm.concordance.rmi.RMIIQMAppModuleServices;
import com.tr.rdss.generic.model.iqm.concordance.util.AttributeConverter;
import com.tr.rdss.generic.model.iqm.concordance.util.EnumInterface;
import com.tr.rdss.generic.model.iqm.concordance.util.IQMRMITransactionException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidServiceMethodCallException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidRMIServiceException;
import com.tr.rdss.generic.model.iqm.concordance.util.Utils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class TestRMIIQMAppModuleServices {

    @Test
    public void testCollectEntityVO() throws IQMRMITransactionException, JSONException, InvalidRMIServiceException, InvalidRawValueException, InvalidServiceMethodCallException {
        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);

        //System.out.println(test.getIQMAppService().getClass().toString());
        //Utils.printMethods(test.getIQMAppService());
        test.beginTransaction(1000);
        System.out.println("=============== get quote VO from db ================");
        //get quote from db
        QuoteVO quoteVO = test.getQuoteVOByPermId(25759645309L, new Date(), new Date());  // 21475038760 // 21475119614L
        System.out.println(Utils.toJSON(quoteVO));
        AttributeConverter.convertEntityVO(quoteVO, true);
        System.out.println(Utils.toJSON(quoteVO));

        try {
            Property ricProperty = (Property) quoteVO.getAttribute(QuoteVO.AttributesEnum.RIC);
            System.out.println("=============== get quote VO from db ================");
            quoteVO = test.getQuoteVOByPermId(25759644883L, Utils.convertToDate("08-Jan-2015 00:00:00"), Utils.convertToDate("08-Jan-2015 00:00:00"));  // 21475038760 // 21475119614L
            System.out.println(Utils.toJSON(quoteVO));
        } catch (Exception e) {
            Utils.printMessage(e);
            test.rollbackTransaction();
            test.beginTransaction();
        }

        // get instrument from db
        System.out.println("=============== get instrument VO from db ================");
//        InstrumentVO instrumentVO = test.getInstrumentVOByPermId(25759645310L, new Date(), new Date());  // 21475038760 // 21475119614L
        InstrumentVO instrumentVO = test.getInstrumentVOByPermId(999999999999999L, Utils.convertToDate("01-Mar-2016 00:00:00"), Utils.convertToDate("01-Mar-2016 00:00:00"));  // 21475038760 // 21475119614L
        System.out.println(Utils.toJSON(instrumentVO));
        System.out.println(instrumentVO.getAttribute(InstrumentVO.AttributesEnum.PRIMARY_LISTED_QUOTE));

        test.rollbackTransaction();
//        test.beginTransaction();
//        try {
//            Date newEffectiveTo = new Date();
//            newEffectiveTo.setSeconds(newEffectiveTo.getSeconds()-1);
//            quoteVO.setNewAttributeWithRawValueForProperty(ricProperty, DaoAction.CHANGE, ricProperty.getValue(), Utils.convertToDate(ricProperty.getEffectiveFrom()), newEffectiveTo);
//
//            test.saveEntityVO(quoteVO);
//            
//        } catch (InvalidEntityVOException  ex) {
//            Utils.printTrace(false);
//            Utils.printMessage(ex);
//        } catch (DateParseException ex) {
//            Utils.printTrace(false);
//            Utils.printMessage(ex);
//        } catch (InvalidRawValueException ex) {
//            Utils.printTrace(false);
//            Utils.printMessage(ex);
//        }
//        test.rollbackTransaction();
//        System.out.println(quoteVO.getAttribute(QuoteVO.AttributesEnum.EXCHANGE_ISIN).toString());

        test.close();
    }

    @Test
    public void testSimple() throws DateParseException, InvalidRMIServiceException, InvalidServiceMethodCallException {
//        Properties props = new Properties();
//  

        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);

        for (int i = 0; i < 10; i++) {
            Date tmp = Utils.convertToDate("01-Jan-2015 06:00:00");
            System.out.println("================ getQuotePermIdBySymbol ==================");
            System.out.println(test.getQuotePermIdBySymbol("ACO.X", 25758802550L, new Date()));
            //

            System.out.println("================ getQuotePermIdBySymbol 1 ==================");
            System.out.println(test.getQuotePermIdBySymbol("LDCNXO.UN", 25758802497L, tmp));
            try {
                Utils.printTrace(false);
                Utils.printMessage("sleep ... " + Utils.convertToString(new Date()));
                Thread.sleep(2 * 60 * 1000);

                Utils.printMessage("awake ... " + Utils.convertToString(new Date()));
            } catch (InterruptedException ex) {
                Utils.printMessage(ex);
            }
        }
        test.close();

    }

    @Test
    public void testLookupService() throws InvalidRMIServiceException, JSONException, InvalidServiceMethodCallException {

        Properties properties = new Properties();
//        properties.setProperty(Context.SECURITY_PRINCIPAL, "concordanceNasdaq");
//        properties.setProperty(Context.SECURITY_CREDENTIALS, "welcome1");
//        properties.setProperty(Context.PROVIDER_URL, "t3://10.35.62.107:9001");
        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(properties);

        System.out.println("getCurrencyCodeByPermId(500230L):" + test.getCurrencyCodeByPermId(500230L));
        System.out.println("getCurrencyPermIdByCode(PHP):" + test.getCurrencyPermIdByCode("PHP"));

        System.out.println("getMarketRDNExchangeCodeByPermId(21475145545L):" + test.getMarketRDNExchangeCodeByPermId(21475145545L));
        System.out.println("getMarketPermIdByRDNExchangeCode(CAQ):" + test.getMarketPermIdByRDNExchangeCode("CAQ"));

        System.out.println("getMetadataValueByEnuPermid(1010005L):" + test.getMetadataValueByEnuPermid(1010005L));
        System.out.println("getMetadataValueByEnuPermid(300413L):" + test.getMetadataValueByEnuPermid(300413L));
        System.out.println("getModelMetadataPermIdByLevelNameValue(INSTRUMENT,TRCS PERMID,DDRAs):" + test.getModelMetadataPermIdByLevelNameValue("INSTRUMENT", "TRCS PERMID", "DDRAs"));

        System.out.println("test.getTimeZoneByMarketPermId(21475145545L):" + test.getTimeZoneByMarketPermId(21475145545L));
        test.close();
    }

//    @Test
//    public void testLookup() throws InvalidRMIServiceException {
//        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);
//        try {
//            CurrencyLookupVO currencyLookup = test.getCurrencyLookup();
//            MarketLookupVO marketLookupVO = test.getMarketLookup();
//            ModelMetadataLookupVO modelMetadataLookupVO = test.getModelMetadataLookup();
//
//            System.out.println("currencyLookup.getElementList().size():" + currencyLookup.getElementList().size());
//            System.out.println("marketLookupVO.getElementList().size():" + marketLookupVO.getElementList().size());
//            System.out.println("modelMetadataLookupVO.getElementList().size():" + modelMetadataLookupVO.getElementList().size());
//        } catch (Exception e) {
//            Utils.printTrace(false);
//            e.printStackTrace();
//
//        }        
//        test.close();
//
//    }
    @Test
    public void testGetPermIds() throws DateParseException, InvalidRMIServiceException, InvalidServiceMethodCallException {
        Utils.printTrace(false);

        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);

        Date tmp = Utils.convertToDate("01-Jan-2016 00:00:00");
        List<Long> permIds;

        System.out.println("================ getInstrumentPermIdsByOAPermId ==================");
        permIds = test.getInstrumentPermIdsByOAPermId(
            8589934213L, tmp, tmp);
        System.out.println("permIds.size() : " + permIds.size());
        System.out.println("permIds.get(0) : " + permIds.get(0));
        System.out.println("permIds.get(permIds.size()-1) : "
            + permIds.get(permIds.size() - 1));

        System.out.println("================ getQuotePermIdsByBaseAssetPermId ==================");
        permIds = test.getQuotePermIdsByBaseAssetPermId(
            25759645979L, tmp, tmp);
        System.out.println("permIds.size() : " + permIds.size());
        System.out.println("permIds.get(0) : " + permIds.get(0));
        System.out.println("permIds.get(permIds.size()-1) : "
            + permIds.get(permIds.size() - 1));

        System.out.println("================ getQuotePermidByRic ==================");
        System.out.println(test.getQuotePermidByRic("MOZ.TO", tmp, tmp));
        System.out.println("================ getQuotePermIdBySymbol ==================");
        System.out.println(test.getQuotePermIdBySymbol("PWF.PR.P", 25758802550L, tmp));

        test.close();
    }

    @Test
    public void testMsgLookup() throws DateParseException, JSONException, InvalidRMIServiceException, InvalidServiceMethodCallException {
        Utils.printTrace(false);

        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);
        MasterGateLookupVO masterGateLookupVO = test.getMasterGateLookup("CA1220112082"); //test.getMasterGateLookup("TELUS Comms Inc Preferred Stock");
        List<MasterGateVO> listMsg = masterGateLookupVO.getElementList();

        System.out.println("listMsg.size() : " + listMsg.size());

        for (MasterGateVO t : listMsg) {
            System.out.println(t);
        }
    }

    @Test
    public void testOrgLookup() throws DateParseException, JSONException, InvalidRMIServiceException, InvalidServiceMethodCallException {
        Utils.printTrace(false);

        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);
        OrgLookupVO orgLookupVO = test.getOrgLookup("RBW");
        List<OrgVO> listMsg = orgLookupVO.getElementList();

        System.out.println("listMsg.size() : " + listMsg.size());

        for (OrgVO t : listMsg) {
            System.out.println(t);
        }
        test.close();
    }

    @Test
    public void testModelMetadataProperties() throws InvalidRMIServiceException, InvalidServiceMethodCallException, JSONException {
        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);
        
        List<ModelMetadataVO> list = test.getModelMetadataProperties().getElementList();
        for (ModelMetadataVO modelMetadataVO : list) {
            Utils.printMessage("<" + modelMetadataVO.getEntityLevel() + ">\t<" + modelMetadataVO.getPropertyName() + ">");
        }
    }

    @Test
    public void testHistoryAttributes() throws JSONException, InvalidRMIServiceException, InvalidRawValueException, InvalidServiceMethodCallException {
        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);
//        CurrencyLookupVO.set(test.getCurrencyLookup());
//        MarketLookupVO.set(test.getMarketLookup());
//        ModelMetadataLookupVO.set(test.getModelMetadataLookup());
        EntityVO entityVO;
        System.out.println("=============== getHistoryAttributeOfInstrumentByPermId ================");
        entityVO = test.getHistoryAttributeOfInstrumentByPermId(
            999999999999999L, InstrumentVO.AttributesEnum.ISIN, new Date(), new Date());
        System.out.println("Utils.toJSON(entityVO):" + Utils.toJSON(entityVO));

        for (Attribute t : entityVO.getAttributes()) {
            System.out.println(t.getName() + ":" + t.getValue() + ":"
                + t.getEffectiveFrom() + ":" + t.getEffectiveTo());
        }
        System.out.println("=============== getHistoryAttributeOfInstrumentByPermId ================");
        for (EnumInterface e : InstrumentVO.AttributesEnum.values()) {
            if (e.getEnumName().equals("TYPE")) {
                continue;
            }
            entityVO = test.getHistoryAttributeOfInstrumentByPermId(999999999999999L, (InstrumentVO.AttributesEnum) e, new Date(), new Date());
            System.out.println("Utils.toJSON(entityVO):" + Utils.toJSON(entityVO));

            for (Attribute t : entityVO.getAttributes()) {
                System.out.println(t.getName() + ":" + t.getValue() + ":"
                    + t.getEffectiveFrom() + ":" + t.getEffectiveTo());
            }
        }

        System.out.println("=============== getHistoryAttributeOfQuoteByPermId ================");
        entityVO = test.getHistoryAttributeOfQuoteByPermId(
            21475119614L, QuoteVO.AttributesEnum.TICKER_SYMBOL, new Date(), new Date());
        System.out.println("Utils.toJSON(entityVO):" + Utils.toJSON(entityVO));

        for (Attribute t : entityVO.getAttributes()) {
            System.out.println(t.getName() + ":" + t.getValue() + ":"
                + t.getEffectiveFrom() + ":" + t.getEffectiveTo());
        }

        System.out.println("=============== getHistoryAttributeOfInstrumentByPermId ================");
        for (EnumInterface e : QuoteVO.AttributesEnum.values()) {
            entityVO = test.getHistoryAttributeOfQuoteByPermId(21475119614L, (QuoteVO.AttributesEnum) e, new Date(), new Date());
            System.out.println("Utils.toJSON(entityVO):" + Utils.toJSON(entityVO));

            for (Attribute t : entityVO.getAttributes()) {
                System.out.println(t.getName() + ":" + t.getValue() + ":"
                    + t.getEffectiveFrom() + ":" + t.getEffectiveTo());
            }
        }

        System.out.println("=============== getHistoryAttributeOfQuoteByPermId 1 ================");
        entityVO = test.getHistoryAttributeOfQuoteByPermId(
            21475119614L, QuoteVO.AttributesEnum.ROUND_LOT_SIZE, new Date(), new Date());
        System.out.println("Utils.toJSON(entityVO):" + Utils.toJSON(entityVO));

        for (Attribute t : entityVO.getAttributes()) {
            System.out.println(t.getName() + ":" + t.getValue() + ":"
                + t.getEffectiveFrom() + ":" + t.getEffectiveTo());
        }

        test.close();
    }

    @Test
    public void testGeneralServiceWithTransaction() throws IQMRMITransactionException, JSONException, InvalidRMIServiceException, InvalidRawValueException, InvalidServiceMethodCallException {
        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);

        // init lookups with rmi calls
//        CurrencyLookupVO.set(test.getCurrencyLookup());
//        MarketLookupVO.set(test.getMarketLookup());
//        ModelMetadataLookupVO.set(test.getModelMetadataLookup());
        //System.out.println(test.getIQMAppService().getClass().toString());
        //Utils.printMethods(test.getIQMAppService());
        System.out.println("=============== get from db ================");
        //get quote from db
        QuoteVO quoteVO = test.getQuoteVOByPermId(21475119614L, new Date(), new Date());
        System.out.println(quoteVO.getAttributeNames());
        System.out.println("keyValue : " + quoteVO.getKeyValue());
        System.out
            .println("Utils.toJSON(entityVO) : " + com.tr.rdss.generic.model.iqm.concordance.util.Utils.toJSON(quoteVO));

        // add ticker symbol
        Date effectiveFrom = new Date();
        effectiveFrom.setYear(effectiveFrom.getYear() - 1);
        Date effectiveTo = new Date();
        effectiveTo.setYear(effectiveFrom.getYear() + 2);

        try {
            quoteVO.addNewPropertyWithRawValue(
                QuoteVO.AttributesEnum.TICKER_SYMBOL, "byuSymbol1",
                effectiveFrom,
                effectiveTo);
        } catch (InvalidRawValueException ex) {
            Utils.printTrace(ex);
            System.out.println("===== ! exit the system ! =====");
            System.exit(1);
        }

        // print local asset json
        System.out.println("=============== before insert into db, local asset JSON ================");
        System.out
            .println("Utils.toJSON(entityVO) : " + com.tr.rdss.generic.model.iqm.concordance.util.Utils.toJSON(quoteVO));

        // send changes to db
        System.out.println("=============== call RMIIQMAppModuleServices.saveChanges ================");
        test.beginTransaction(1000);
        Long quotePermId = null;
        try {
            quotePermId = test.saveEntityVO(quoteVO);
            System.out.println("quotePermId: " + quotePermId);
        } catch (InvalidEntityVOException ex) {
            test.rollbackTransaction();
            Utils.printTrace(ex);
            System.exit(1);
        }

        // get the result from db again
        System.out.println("=============== after insert into db, local asset JSON ================");
        quoteVO = test.getQuoteVOByPermId(21475119614L, new Date(), new Date());
        System.out
            .println("Utils.toJSON(entityVO) : " + com.tr.rdss.generic.model.iqm.concordance.util.Utils.toJSON(quoteVO));

        // roll back the changes
        test.rollbackTransaction();

        System.out.println("=============== after roolback, retrieve json from db ================");
        quoteVO = test.getQuoteVOByPermId(21475119614L, new Date(), new Date());
        System.out
            .println("Utils.toJSON(entityVO) : " + com.tr.rdss.generic.model.iqm.concordance.util.Utils.toJSON(quoteVO));

        // add ticker symbol again
        try {
            quoteVO.addNewPropertyWithRawValue(
                QuoteVO.AttributesEnum.TICKER_SYMBOL, "byuSymbol2",
                effectiveFrom,
                effectiveTo);

            quoteVO.setNewAttributeWithRawValueForProperty(
                (Property) quoteVO.getAttribute(QuoteVO.AttributesEnum.FIRST_TRADING_DAY), //
                DaoAction.CHANGE, //
                Utils.convertToDate("07-Nov-2010 00:00:00"),
                effectiveFrom, effectiveTo);
        } catch (InvalidRawValueException ex) {
            Utils.printTrace(ex);
            System.err.println("===== ! exit the system ! =====");
            System.exit(1);
        }

        // send changes to db again
        System.out.println("=============== call RMIIQMAppModuleServices.saveChanges ================");
        test.beginTransaction(1000);
        try {
            quotePermId = test.saveEntityVO(quoteVO);
            System.out.println("quotePermId: " + quotePermId);
        } catch (InvalidEntityVOException ex) {
            test.rollbackTransaction();
            Utils.printTrace(ex);
            System.exit(1);
        }

        // get the result from db again
        System.out.println("=============== after insert into db, local asset JSON ================");
        quoteVO = test.getQuoteVOByPermId(21475119614L, new Date(), new Date());
        System.out
            .println("Utils.toJSON(entityVO) : " + com.tr.rdss.generic.model.iqm.concordance.util.Utils.toJSON(quoteVO));

        // commit the changes
        test.commitTransaction();

        // retrieve data from db to verify
        System.out.println("=============== after commit to db, local asset JSON ================");
        quoteVO = test.getQuoteVOByPermId(21475119614L, new Date(), new Date());
        System.out
            .println("Utils.toJSON(entityVO) : " + com.tr.rdss.generic.model.iqm.concordance.util.Utils.toJSON(quoteVO));

        test.close();
    }

    @Test
    public void testGuoJieCases() throws InvalidRMIServiceException {
        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);

        try {
            //initialize lookups

            QuoteVO quoteVOWithShortName = (QuoteVO) test.getHistoryAttributeOfQuoteByPermId(
                21475119614L, QuoteVO.AttributesEnum.SHORT_NAME, new Date(), new Date());
            QuoteVO quoteVOWithCommonName = (QuoteVO) test.getHistoryAttributeOfQuoteByPermId(
                21475119614L, QuoteVO.AttributesEnum.COMMON_NAME, new Date(), new Date());

            QuoteVO quoteVO = new QuoteVO(21475119614L);
            quoteVO.addProperty(QuoteVO.AttributesEnum.SHORT_NAME,//
                quoteVOWithShortName.getAttribute(QuoteVO.AttributesEnum.SHORT_NAME).getValue(),//
                ModelUtil.formatDate(quoteVOWithShortName.getAttribute(QuoteVO.AttributesEnum.SHORT_NAME).getEffectiveFrom()),//
                ModelUtil.formatDate(quoteVOWithShortName.getAttribute(QuoteVO.AttributesEnum.SHORT_NAME).getEffectiveTo()));

            quoteVO.addProperty(QuoteVO.AttributesEnum.COMMON_NAME,//
                quoteVOWithCommonName.getAttribute(QuoteVO.AttributesEnum.COMMON_NAME).getValue(),//
                ModelUtil.formatDate(quoteVOWithCommonName.getAttribute(QuoteVO.AttributesEnum.COMMON_NAME).getEffectiveFrom()),//
                ModelUtil.formatDate(quoteVOWithCommonName.getAttribute(QuoteVO.AttributesEnum.COMMON_NAME).getEffectiveTo()));

            System.out.println("Utils.toJSON(entityVO):" + Utils.toJSON(quoteVO));

            for (Attribute t : quoteVO.getAttributes()) {
                System.out.println(t.getName() + ":" + t.getValue() + ":"
                    + t.getEffectiveFrom() + ":" + t.getEffectiveTo());
            }

            //QuoteVO quoteVO = test.getQuoteVOByPermId(21475119614L, new Date(), new Date());
            Date date = new Date();
            date.setYear(date.getYear() - 1);

            System.out.println(date);

            quoteVO.setNewAttributeWithRawValueForProperty(//
                (Property) quoteVO.getAttribute(QuoteVO.AttributesEnum.COMMON_NAME),
                DaoAction.CORRECT, //
                quoteVO.getAttribute(QuoteVO.AttributesEnum.COMMON_NAME),
                date,
                ModelUtil.formatDate(quoteVO.getAttribute(QuoteVO.AttributesEnum.COMMON_NAME).getEffectiveTo()));

            quoteVO.setNewAttributeWithRawValueForProperty(//
                (Property) quoteVO.getAttribute(QuoteVO.AttributesEnum.SHORT_NAME),
                DaoAction.CORRECT, //
                quoteVO.getAttribute(QuoteVO.AttributesEnum.SHORT_NAME),
                date,
                ModelUtil.formatDate(quoteVO.getAttribute(QuoteVO.AttributesEnum.SHORT_NAME).getEffectiveTo()));

            quoteVO.addProperty(QuoteVO.AttributesEnum.TICKER_SYMBOL, "null", null, null);
            quoteVO.getAttribute(QuoteVO.AttributesEnum.TICKER_SYMBOL).setAction(DaoAction.CORRECT);

            System.out.println("Utils.toJSON(entityVO):" + Utils.toJSON(quoteVO));

            test.beginTransaction();
            test.saveEntityVO(quoteVO);
            test.rollbackTransaction();
            //test.commitTransaction();

        } catch (Exception e) {
            Utils.printMessage("!!!!!!  !!!!!!!!");
            Utils.printMessage(e);
        } finally {
            test.close();
        }
    }

    @Test
    public void testRdcCheckForCcd() throws InvalidRMIServiceException, Exception {
        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);
//        List<String> codeList = new ArrayList<String>();
//        codeList.add("TOR");
//        String res = test.rdcCheckForCcd(QuoteVO.entityLevel,//
//            QuoteVO.AttributesEnum.TICKER_SYMBOL, //
//            "TickerZy36000015012", //
//            "TickerZy36000015012", //
//            codeList);

        test.beginTransaction();
        String res = test.rdcCheckForCcd(InstrumentVO.entityLevel,//
            InstrumentVO.AttributesEnum.ISIN,//
            "EMP400000776", //
            null, null);
        System.out.println("res:" + res);
        test.rollbackTransaction();
        test.close();
    }

    @Test
    public void testCountInstrumentsInRDC() throws InvalidRMIServiceException, Exception {
        Utils.printTrace(false);
        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);
        test.beginTransaction();
        Long cnt = test.countInstrumentsInRDC(
            InstrumentVO.AttributesEnum.ISIN,//
            "EMP400000776");
        Utils.printMessage("cnt:" + cnt);
        test.rollbackTransaction();
        test.close();
    }

    @Test
    public void testCountQuotesInRDC() throws InvalidRMIServiceException, Exception {
        Utils.printTrace(false);
        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);
        test.beginTransaction();
        Long cnt = test.countQuotesInRDC(
            "ZSP.U", "TOR");
        Utils.printMessage("cnt:" + cnt);
        test.rollbackTransaction();
        test.close();
    }

    @Test
    public void testCreateEntity() throws InvalidRMIServiceException, IQMRMITransactionException, JSONException {
        Utils.printTrace(false);
        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);
        test.beginTransaction(1000);
        EntityVO entityVO = Utils.getEntityVOFromJSON("{\"key\":\"QUOTE PERMID\",\"key_value\":\"-1\",\"entity\":{\"objectType\":\"QuoteAsset\",\"action\":\"NONE\",\"key\":\"QUOTE PERMID\",\"keyValue\":\"-1\",\"assetClassValue\":\"Q\",\"attributes\":[{\"objectType\":\"Property\",\"action\":\"ADD\",\"entity\":null,\"name\":\"COMMON NAME\",\"effectiveFrom\":\"08-Dec-2015 14:01:40\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"TESTCOMMONNAMEJWANG\"},{\"objectType\":\"Property\",\"action\":\"ADD\",\"entity\":null,\"name\":\"TICKER SYMBOL\",\"effectiveFrom\":\"08-Dec-2015 14:01:40\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"TESTJL1\"},{\"objectType\":\"Property\",\"action\":\"ADD\",\"entity\":null,\"name\":\"TRADES ON SUBMARKET\",\"effectiveFrom\":\"08-Dec-2015 14:01:40\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"25758802550\"},{\"objectType\":\"Property\",\"action\":\"ADD\",\"entity\":null,\"name\":\"TRADING STATUS\",\"effectiveFrom\":\"08-Dec-2015 14:01:40\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"Not Yet Trading\"},{\"objectType\":\"Property\",\"action\":\"ADD\",\"entity\":null,\"name\":\"RIC\",\"effectiveFrom\":\"08-Dec-2015 14:01:40\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"TESTJL1.OQ\"},{\"objectType\":\"Property\",\"action\":\"ADD\",\"entity\":null,\"name\":\"QUOTATION CURRENCY\",\"effectiveFrom\":\"08-Dec-2015 14:01:40\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"USD\"}]},\"message\":\"create success\",\"succ\":true}");
        Property property = (Property) entityVO.getAttribute(QuoteVO.AttributesEnum.TRADES_ON_SUBMARKET);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Entity ent = null;
        String assetStr;
        try {
            assetStr = objectMapper
                .writeValueAsString((Asset) (entityVO.getEntity()));
            ent = objectMapper.readValue(assetStr, Entity.class);
            Utils.printMessage("byu:" + ent.getKeyValue());
            Long key = Long.parseLong(ent.getKeyValue());

            Utils.printMessage("byu:" + key.equals("-1L"));
        } catch (IOException ex) {
            Utils.printMessage(ex);
        }

        try {
            System.out.println(test.saveEntityVO(entityVO));
        } catch (InvalidEntityVOException | InvalidRawValueException | InvalidServiceMethodCallException ex) {
            Utils.printMessage(ex);
        }

        test.rollbackTransaction();
        test.close();
    }

    @Test
    public void testInvokeDBFunction() throws InvalidRMIServiceException, Exception {
        Utils.printTrace(false);
        String functionFullName = "byu_pkg.get_date";
        String inputJSON = "-1";
        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);
        String res = test.invokeDBFunction(functionFullName, inputJSON);
        Utils.printMessage("result:" + res);
    }

    @Test
    public void test_insert_exception() throws Exception {

        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("exceptionNumber", "9999999");
        dataMap.put("exchange", "NASDAQ");
        dataMap.put("symbol", "Demetrius");
        dataMap.put("feed", "jlTest");
        dataMap.put("issueEvent", "type 41");
        dataMap.put("createBy", "Concordance");
        dataMap.put("INC_ID", "701793685379");
        dataMap.put("companyName", "Demetrius KVPVA");
        dataMap.put("CUSIP", "48717");
        dataMap.put("effectiveDate", "02/11/2015"); // date must be of "DD/MM/YYYY"

        ObjectMapper mapper = new ObjectMapper();

        String inputJson = mapper.writeValueAsString(dataMap);

        Utils.printMessage(inputJson);
        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(null);
        test.beginTransaction();
        // functionFullName : <schema_name>.<pkg_name>.<fun_name>
        String res = test.invokeDBFunction("iqm_humantask.exception_api_pkg.insert_exception", inputJson);
        Utils.printMessage("result:" + res);
        test.commitTransaction();

    }

}
