/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tr.rdss.generic.model.iqm.concordance.rmi;

import com.tr.rdss.generic.model.iqm.concordance.ModelMetadataVO;
import com.tr.rdss.generic.model.iqm.concordance.QuoteVO;
import com.tr.rdss.generic.model.iqm.concordance.util.IQMRMITransactionException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidEntityVOException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidServiceMethodCallException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidRMIServiceException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidRawValueException;
import com.tr.rdss.generic.model.iqm.concordance.util.JSONException;
import com.tr.rdss.generic.model.iqm.concordance.util.Utils;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;

/**
 *
 * @author U8015921
 */
public class TestMain {

    public TestMain() {
        
    }
    
    public Properties getProperties(String[] args) {        
        Utils.printMessage("--------------------------");
        for (String str : args) {
            System.out.println(str + "\n");
        }
        Utils.printMessage("--------------------------");
        Properties pro = null;
        if (args != null && args.length > 0) {
            pro = new Properties();
            if (args.length > 0) {
                pro.setProperty(Context.PROVIDER_URL, args[0]);
            }
            if (args.length > 1) {
                pro.setProperty(Context.SECURITY_PRINCIPAL, args[1]);
            }
            if (args.length > 2) {
                pro.setProperty(Context.SECURITY_CREDENTIALS, args[2]);
            }
            pro.list(System.out);
            Utils.printMessage("--------------------------");
        }
        return pro;
    }
    
    public void testGeneralServiceWithTransaction(String[] args) throws IQMRMITransactionException, JSONException, InvalidRMIServiceException, InvalidRawValueException, InvalidServiceMethodCallException {
        
        QuoteVO quoteVO = null;
        Properties pro = this.getProperties(args);
        RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(pro);
        Long permId = 25759644104L;
        
        System.out.println("=============== get from db ================");
        //get quote from db
        test.beginTransaction(1000);
        quoteVO = test.getQuoteVOByPermId(permId, new Date(), new Date());
        test.rollbackTransaction();
        System.out.println(quoteVO.getAttributeNames());
        System.out.println("keyValue : " + quoteVO.getKeyValue());
        System.out
                .println("Utils.toJSON(entityVO) : " + com.tr.rdss.generic.model.iqm.concordance.util.Utils.toJSON(quoteVO));

        // add ticker symbol
        Date effectiveFrom = new Date();
        effectiveFrom.setYear(effectiveFrom.getYear() - 1);
        Date effectiveTo = new Date();
        effectiveTo.setYear(effectiveFrom.getYear() + 2);
        
//        try {
            quoteVO.addForceProperty(
                    QuoteVO.AttributesEnum.TICKER_SYMBOL.toString(), "byuSymbol1",
                    effectiveFrom,
                    effectiveTo);
//        } catch (InvalidRawValueException ex) {
//            Utils.printTrace(ex);
//            System.out.println("===== ! exit the system ! =====");
//            System.exit(1);
//        }

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
        quoteVO = test.getQuoteVOByPermId(permId, new Date(), new Date());
        System.out
                .println("Utils.toJSON(entityVO) : " + com.tr.rdss.generic.model.iqm.concordance.util.Utils.toJSON(quoteVO));

        // roll back the changes
        test.rollbackTransaction();
        
        System.out.println("=============== after roolback, retrieve json from db ================");
        quoteVO = test.getQuoteVOByPermId(permId, new Date(), new Date());
        System.out
                .println("Utils.toJSON(entityVO) : " + com.tr.rdss.generic.model.iqm.concordance.util.Utils.toJSON(quoteVO));

        // add ticker symbol again
//        try {
//            quoteVO.addNewPropertyWithRawValue(
//                    QuoteVO.AttributesEnum.TICKER_SYMBOL, "byuSymbol2",
//                    effectiveFrom,
//                    effectiveTo);
//            
//        } catch (InvalidRawValueException ex) {
//            Utils.printTrace(ex);
//            System.err.println("===== ! exit the system ! =====");
//            System.exit(1);
//        }
            quoteVO.addForceProperty(
                    QuoteVO.AttributesEnum.TICKER_SYMBOL.toString(), "byuSymbol1",
                    effectiveFrom,
                    effectiveTo);

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
        quoteVO = test.getQuoteVOByPermId(permId, new Date(), new Date());
        System.out
                .println("Utils.toJSON(entityVO) : " + com.tr.rdss.generic.model.iqm.concordance.util.Utils.toJSON(quoteVO));

        // commit the changes
        test.rollbackTransaction();

        // retrieve data from db to verify
        System.out.println("=============== after commit to db, local asset JSON ================");
        quoteVO = test.getQuoteVOByPermId(permId, new Date(), new Date());
        System.out
                .println("Utils.toJSON(entityVO) : " + com.tr.rdss.generic.model.iqm.concordance.util.Utils.toJSON(quoteVO));
        
        test.close();
    }
    
    public void testT3(String[] args) {
        try {
            QuoteVO quoteVO = null;
            Properties pro = this.getProperties(args);
            RMIIQMAppModuleServices test = new RMIIQMAppModuleServices(pro);
            Date tmp = Utils.convertToDate("01-Jan-2016 00:00:00");
//            test.beginTransaction(1000);
            for (int i = 0; i < 1000; i++) {
                test.beginTransaction();
                
                System.out.println("================ getQuotePermIdsByBaseAssetPermId ==================");
                List<Long> permIds = test.getQuotePermIdsByBaseAssetPermId(
                        25759645979L, tmp, tmp);
                System.out.println(permIds);
                
                System.out.println("================ getModelMetadataProperties ==================");
                List<ModelMetadataVO> list = test.getModelMetadataProperties().getElementList();
                System.out.println(list.size());
                System.out.println(list.size() + ": " + list.get(1).toString());

                Thread.sleep(10 * 1000);
                test.rollbackTransaction();
            }
//            test.rollbackTransaction();
        } catch (Exception e) {
            Utils.printMessage(e);
        }
    }

    public static void main(String[] args) {
        TestMain test = new TestMain();
        try {
            test.testT3(args);
//            test.testT3(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
