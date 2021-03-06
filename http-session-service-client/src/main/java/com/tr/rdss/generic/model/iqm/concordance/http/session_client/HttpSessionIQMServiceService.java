
package com.tr.rdss.generic.model.iqm.concordance.http.session_client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "HttpSessionIQMServiceService", targetNamespace = "http://session.http.concordance.iqm.model.generic.rdss.tr.com/", wsdlLocation = "http://localhost:7101/iqm-nm-app-model-http-session-service/HttpSessionIQMServiceService?wsdl")
public class HttpSessionIQMServiceService
    extends Service
{

    private final static URL HTTPSESSIONIQMSERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException HTTPSESSIONIQMSERVICESERVICE_EXCEPTION;
    private final static QName HTTPSESSIONIQMSERVICESERVICE_QNAME = new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "HttpSessionIQMServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:7101/iqm-nm-app-model-http-session-service/HttpSessionIQMServiceService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        HTTPSESSIONIQMSERVICESERVICE_WSDL_LOCATION = url;
        HTTPSESSIONIQMSERVICESERVICE_EXCEPTION = e;
    }

    public HttpSessionIQMServiceService() {
        super(__getWsdlLocation(), HTTPSESSIONIQMSERVICESERVICE_QNAME);
    }

    public HttpSessionIQMServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), HTTPSESSIONIQMSERVICESERVICE_QNAME, features);
    }

    public HttpSessionIQMServiceService(URL wsdlLocation) {
        super(wsdlLocation, HTTPSESSIONIQMSERVICESERVICE_QNAME);
    }

    public HttpSessionIQMServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, HTTPSESSIONIQMSERVICESERVICE_QNAME, features);
    }

    public HttpSessionIQMServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HttpSessionIQMServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns HttpSessionIQMService
     */
    @WebEndpoint(name = "HttpSessionIQMServicePort")
    public HttpSessionIQMService getHttpSessionIQMServicePort() {
        return super.getPort(new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "HttpSessionIQMServicePort"), HttpSessionIQMService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns HttpSessionIQMService
     */
    @WebEndpoint(name = "HttpSessionIQMServicePort")
    public HttpSessionIQMService getHttpSessionIQMServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://session.http.concordance.iqm.model.generic.rdss.tr.com/", "HttpSessionIQMServicePort"), HttpSessionIQMService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (HTTPSESSIONIQMSERVICESERVICE_EXCEPTION!= null) {
            throw HTTPSESSIONIQMSERVICESERVICE_EXCEPTION;
        }
        return HTTPSESSIONIQMSERVICESERVICE_WSDL_LOCATION;
    }

}
