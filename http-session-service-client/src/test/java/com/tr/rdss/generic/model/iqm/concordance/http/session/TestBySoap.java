
package com.tr.rdss.generic.model.iqm.concordance.http.session;

import com.tr.rdss.generic.model.iqm.concordance.QuoteVO;
import com.tr.rdss.generic.model.iqm.concordance.http.session_client.HttpSessionIQMService;
import com.tr.rdss.generic.model.iqm.concordance.http.session_client.HttpSessionIQMServiceService;
import com.tr.rdss.generic.model.iqm.concordance.util.JSONException;
import com.tr.rdss.generic.model.iqm.concordance.util.Utils;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import javax.xml.ws.BindingProvider;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author U8015921
 */
public class TestBySoap {
    private static final String URL_TAIL = HttpSessionIQMServiceClientService.URL_TAIL;

    public void testCommonConnection() throws MalformedURLException {
//          String str = "10.35.62.234:7011";
        String urlStr = "10.35.62.232:7011"; //cluster 1
        URL url = new URL("http://" + urlStr + URL_TAIL);

        HttpSessionIQMServiceService test = new HttpSessionIQMServiceService(url);
        HttpSessionIQMService port = test.getHttpSessionIQMServicePort();
        // request to preserve the session
        ((BindingProvider) port).getRequestContext().put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);
        
        System.out.println("port.toString(): " + port.toString());
        port.init("weblogic","welcome1");
//        System.out.println(port.beginTransaction(1000));
//        System.out.println(port.getModelMetadataProperties());
        port.beginTransaction();
        String str = port.getQuoteVOByPermId(25759645309L, Utils.convertToString(new Date()), Utils.convertToString(new Date()));
        Utils.printMessage(str);
        port.rollbackTransaction();
        port.close();
//        System.out.println(port.closeService());
    }
    
    public void testTransaction() throws JSONException, MalformedURLException {
        String urlStr = "10.238.126.3:80";
        URL url = new URL("http://" + urlStr + URL_TAIL);
        Long permId = 25759644104L;
        System.out.println("url:" + url.getHost());
        HttpSessionIQMServiceService test = new HttpSessionIQMServiceService(url);
        HttpSessionIQMService port = test.getHttpSessionIQMServicePort();
        // request to preserve the session
        ((BindingProvider) port).getRequestContext().put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);
        System.out.println("port.toString(): " + port.toString());
        port.init("weblogic", "welcome1");
        port.beginTransaction();
        String entityVOJSON = port.getQuoteVOByPermId(25759645309L, Utils.convertToString(new Date()), Utils.convertToString(new Date()));
        port.rollbackTransaction();
        QuoteVO quoteVO = (QuoteVO) Utils.getEntityVOFromJSON(entityVOJSON);
        
        System.out.println(quoteVO.getAttributeNames());
        System.out.println("keyValue : " + quoteVO.getKeyValue());
        System.out
                .println("Utils.toJSON(entityVO) : " + Utils.toJSON(quoteVO));

        // add ticker symbol
        quoteVO = (QuoteVO) Utils.getEntityVOFromJSON("{\"key\":\"QUOTE PERMID\",\"key_value\":\"25759644104\",\"entity\":{\"objectType\":\"QuoteAsset\",\"action\":\"NONE\",\"key\":\"QUOTE PERMID\",\"keyValue\":\"25759644104\",\"assetClassValue\":\"Q\",\"attributes\":[{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"QUOTE PERMID\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"25759644104\"},{\"objectType\":\"Relationship\",\"action\":\"NONE\",\"entity\":null,\"name\":\"BASE ASSET\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"25759644118\",\"reference\":{\"objectType\":\"InstrumentAsset\",\"action\":\"NONE\",\"key\":\"INSTRUMENT PERMID\",\"keyValue\":\"25759644118\",\"assetClassValue\":\"I\",\"attributes\":[{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"INSTRUMENT PERMID\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"25759644118\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"CUSIP\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"900000019\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"TRCS PERMID\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"Ordinary Shares\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"PILC\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"25759644118\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"COMMON NAME\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"123123TEST FOR COMPANY NAME ORD SHS\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"ADMIN STATUS\",\"effectiveFrom\":\"13-Jan-2016 00:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"Published\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"INSTRUMENT STATUS\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"IAT\"}]}},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"QUOTATION CURRENCY\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"USD\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"TRADES ON MARKET\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"25758802323\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"RIC\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"ABCD.BAT\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"ADMIN STATUS\",\"effectiveFrom\":\"13-Jan-2016 00:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"Published\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"TRADING STATUS\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"Not yet Trading\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"TRADES ON SUBMARKET\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"25758803563\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"ROUND LOT SIZE\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"100\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"COMMON NAME\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"123123TEST FOR COMPA ORD\"},{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"SHORT NAME\",\"effectiveFrom\":\"15-Oct-2015 04:00:00\",\"effectiveTo\":\"31-Dec-9999 00:00:00\",\"newAttribute\":null,\"value\":\"123123TEST FOR C\"},{\"objectType\":\"Property\",\"action\":\"ADD\",\"entity\":null,\"name\":\"TICKER SYMBOL\",\"effectiveFrom\":\"26-Jan-2015 06:42:48\",\"effectiveTo\":\"26-Jan-2017 06:42:48\",\"newAttribute\":null,\"value\":\"byuSymbol1\"}]},\"message\":\"create success\",\"succ\":true}");

        // print local asset json
        System.out.println("=============== before insert into db, local asset JSON ================");
        System.out
                .println("Utils.toJSON(entityVO) : " + Utils.toJSON(quoteVO));

        // send changes to db
        System.out.println("=============== call RMIIQMAppModuleServices.saveChanges ================");
        port.beginTransaction();
        Long quotePermId = null;
        try {
            quotePermId = port.saveEntityVO(Utils.toJSON(quoteVO));
            System.out.println("res: " + quotePermId);
        } catch (Exception ex) {
            port.rollbackTransaction();
            ex.printStackTrace();
            System.exit(1);
        }
        
        System.out.println("=============== after insert into db, local asset JSON ================");
        entityVOJSON = port.getQuoteVOByPermId(25759645309L, Utils.convertToString(new Date()), Utils.convertToString(new Date()));
        quoteVO = (QuoteVO) Utils.getEntityVOFromJSON(entityVOJSON);
        System.out
                .println("Utils.toJSON(entityVO) : " + Utils.toJSON(quoteVO));

        // roll back the changes
        //port.rollbackTransaction();
        port.commitTransaction();

        System.out.println("=============== after roolback, retrieve json from db ================");
        port.beginTransaction();
        entityVOJSON = port.getQuoteVOByPermId(25759645309L, Utils.convertToString(new Date()), Utils.convertToString(new Date()));
        quoteVO = (QuoteVO) Utils.getEntityVOFromJSON(entityVOJSON);
        System.out
                .println("Utils.toJSON(entityVO) : " + Utils.toJSON(quoteVO));
        port.rollbackTransaction();
        
        port.close();
    }
    
    public void testCommonConnectionViaBanana() throws MalformedURLException {
         class CheckResult {

            public void run(HttpSessionIQMService port, Long permId, String statusMsg) {
                System.out.println("=========== " + statusMsg + " ===========");
                String entityVOJSON = port.getQuoteVOByPermId(permId, Utils.convertToString(new Date()), Utils.convertToString(new Date()));
                QuoteVO quoteVO;
                try {
                    quoteVO = (QuoteVO) Utils.getEntityVOFromJSON(entityVOJSON);
                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(quoteVO.getAttributeNames());
                System.out.println("keyValue : " + quoteVO.getKeyValue());
                System.out.println("QuoteVO.AttributesEnum.TICKER_SYMBOL:" + quoteVO.getAttribute(QuoteVO.AttributesEnum.TICKER_SYMBOL));
            }
        }
        // initialize the service
        String urlStr = "10.238.126.3:80";
//        String str = "10.35.62.232:7011";
//        String str = "localhost:7101";
        URL url = new URL("http://" + urlStr + URL_TAIL);
//URL url = new URL("D:\\byu\\IQM\\github\\test-iqm-nm-app-model-stateful-service\\web\\WEB-INF\\wsdl\\10.238.126.3\\iqm-nm-app-model-stateful-service");
//URL url = new File("D:\\byu\\IQM\\github\\test-iqm-nm-app-model-stateful-service\\web\\WEB-INF\\wsdl\\10.238.126.3\\iqm-nm-app-model-stateful-service").toURL();
        Long permId = 25759644104L;
        System.out.println("url:" + url.getHost());
        HttpSessionIQMServiceService test = new HttpSessionIQMServiceService(url);
        HttpSessionIQMService port = test.getHttpSessionIQMServicePort();
        // request to preserve the session
        ((BindingProvider) port).getRequestContext().put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);
        System.out.println("port.toString(): " + port.toString());
        port.init("weblogic", "welcome1");

        // before all actions
        port.beginTransaction();
//        System.out.println(port.getModelMetadataProperties());        
        new CheckResult().run(port, permId, "before all actions");
        port.rollbackTransaction();

        // add ticker symbol into db, and roll back after checking
        System.out.println("=========== insert a new ticker symbol into db");
        String strForInsertNewSymbol = "{\"key\":\"QUOTE PERMID\",\"key_value\":\"25759644104\",\"entity\":{\"objectType\":\"QuoteAsset\",\"action\":\"NONE\",\"key\":\"QUOTE PERMID\",\"keyValue\":\"25759644104\",\"assetClassValue\":\"Q\",\"attributes\":[{\"objectType\":\"Property\",\"action\":\"ADD\",\"entity\":null,\"name\":\"TICKER SYMBOL\",\"effectiveFrom\":\"25-Jan-2015 00:00:00\",\"effectiveTo\":\"25-Jan-2017 00:00:00\",\"newAttribute\":null,\"value\":\"byuSymbol1\"}]},\"message\":\"create success\",\"succ\":true}";
        port.beginTransaction();
        port.saveEntityVO(strForInsertNewSymbol);
        new CheckResult().run(port, permId, "after inserting but before rolling back");
        port.rollbackTransaction();

        // after rolling back
        port.beginTransaction();
        new CheckResult().run(port, permId, "after rolling back");
        port.rollbackTransaction();

        // add ticker symbol into db again
        System.out.println("=========== insert a new ticker symbol into db, again");
        port.beginTransaction();
        port.saveEntityVO(strForInsertNewSymbol);
        new CheckResult().run(port, permId, "after the second round of inserting but before committing");
        port.commitTransaction();

        // after committing
        port.beginTransaction();
        new CheckResult().run(port, permId, "after comming");
        port.rollbackTransaction();

        // correct the ticker symbol, and roll back after checking
        System.out.println("=========== correct the ticker symbol");
        port.beginTransaction();
        String strForCorrectSymbol = "{\"key\":\"QUOTE PERMID\",\"key_value\":\"25759644104\",\"entity\":{\"objectType\":\"QuoteAsset\",\"action\":\"NONE\",\"key\":\"QUOTE PERMID\",\"keyValue\":\"25759644104\",\"assetClassValue\":\"Q\",\"attributes\":[{\"objectType\":\"Property\",\"action\":\"CORRECT\",\"entity\":null,\"name\":\"TICKER SYMBOL\",\"effectiveFrom\":\"25-Jan-2015 00:00:00\",\"effectiveTo\":\"25-Jan-2017 00:00:00\",\"newAttribute\":{\"objectType\":\"Property\",\"action\":\"NONE\",\"entity\":null,\"name\":\"TICKER SYMBOL\",\"effectiveFrom\":\"25-Jan-2015 00:00:00\",\"effectiveTo\":\"25-Jan-2017 00:00:00\",\"newAttribute\":null,\"value\":\"byuSymbol1.1\"},\"value\":\"byuSymbol1\"}]},\"message\":\"create success\",\"succ\":true}";
        port.saveEntityVO(strForCorrectSymbol);
        new CheckResult().run(port, permId, "after correcting but before rolling back");
        port.rollbackTransaction();

        port.beginTransaction();
        new CheckResult().run(port, permId, "after rolling back the correction");
        port.rollbackTransaction();

        // correct the ticker symbol again, and commit after checking
        System.out.println("=========== correct the ticker symbol, again");
        port.beginTransaction();
        port.saveEntityVO(strForCorrectSymbol);
        new CheckResult().run(port, permId, "after the second round of correcting but before committing");
        port.commitTransaction();

        port.beginTransaction();
        new CheckResult().run(port, permId, "after commiting the correcton");

        port.close();
    }
    
    public static void main(String[] args) throws MalformedURLException, JSONException {
        TestBySoap test = new TestBySoap();
        test.testCommonConnection();
//        test.testTransaction();
        test.testCommonConnectionViaBanana();
    }
}
