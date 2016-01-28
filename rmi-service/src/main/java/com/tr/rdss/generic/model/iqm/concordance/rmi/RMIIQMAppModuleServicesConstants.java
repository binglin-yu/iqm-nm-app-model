package com.tr.rdss.generic.model.iqm.concordance.rmi;

import java.util.Properties;
import javax.naming.Context;

/**
 *
 * @author U8015921
 */
public abstract class RMIIQMAppModuleServicesConstants {

    /**
     * IQMAppModule Service
     */
    public static final String appModuleGeneralServiceName
        = "IQMAppServiceBean#thomsonreuters.iqm.master.adf.model.services.common.serviceinterface.IQMAppService";

    /**
     * IQMLookupAppModule Service
     */
    public static final String appModuleLookupServiceName
        = "IQMLookupAppModuleServiceBean#thomsonreuters.iqm.master.adf.model.services.common.serviceinterface.IQMLookupAppModuleService";

    /**
     * IQMNonTemporalAppModule Service
     */
    public static final String appModuleNonTemporalServiceName
        = "IQMNonTemporalAppModuleServiceBean#thomsonreuters.iqm.master.adf.model.services.common.serviceinterface.IQMNonTemporalAppModuleService";

    /**
     * UserTransaction Service
     */
    public static final String RMIUserTransaction = "javax.transaction.UserTransaction";

    /**
     *
     * @return default properties for RMI calls
     */
    public static Properties getDefaultProperties() {

        Properties tmpProps = new Properties();
        // app cfg
        tmpProps.setProperty("CurrencyLookup.use.local", "n");
        tmpProps.setProperty("MarketLookup.use.local", "n");
        tmpProps.setProperty("ModelMetadataLookup.use.local", "n");
        tmpProps.setProperty("print.service.available.methods", "n");
        tmpProps.setProperty("print.all.available.services", "n");
        tmpProps.setProperty("print.service.properties", "y");

        // JNDI
        tmpProps.setProperty(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        tmpProps.setProperty(Context.SECURITY_PRINCIPAL, "concordanceNasdaq");
        tmpProps.setProperty(Context.SECURITY_CREDENTIALS, "welcome1");
//        tmpProps.setProperty("java.naming.provider.url", "t3://localhost:7101"); // dev
//        tmpProps.setProperty(Context.PROVIDER_URL, "t3://10.35.62.107:7010"); // blue-1
//        tmpProps.setProperty("java.naming.provider.url", "t3://10.35.62.107:9001"); // blue-2
        tmpProps.setProperty(Context.PROVIDER_URL, "t3://10.35.62.232:7011"); // cluster-testing-1  
//        tmpProps.setProperty(Context.PROVIDER_URL, "t3://10.35.62.233:7011"); // cluster-testing-2 
        
//        tmpProps.setProperty(Context.PROVIDER_URL, "t3://10.35.62.234:7011"); // cluster-testing-0 // not workable
        

        return tmpProps;
    }

}
