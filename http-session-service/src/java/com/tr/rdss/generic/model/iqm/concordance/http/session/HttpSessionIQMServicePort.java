/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tr.rdss.generic.model.iqm.concordance.http.session;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * REST Web Service
 *
 * @author U8015921
 */
@Path("httpsessioniqmserviceport")
public class HttpSessionIQMServicePort {

    private com.tr.rdss.generic.model.iqm.concordance.http.session_client.HttpSessionIQMService port;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HttpSessionIQMServicePort
     */
    public HttpSessionIQMServicePort() {
        port = getPort();
    }

    /**
     * Invokes the SOAP method init
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     */
    @PUT
    @Consumes("text/plain")
    @Path("init/")
    public void putInit(@QueryParam("arg0") String arg0, @QueryParam("arg1") String arg1) {
        try {
            // Call Web Service Operation
            if (port != null) {
                port.init(arg0, arg1);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

    /**
     * Invokes the SOAP method close
     */
    @PUT
    @Consumes("text/plain")
    @Path("close/")
    public void putClose() {
        try {
            // Call Web Service Operation
            if (port != null) {
                port.close();
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

    /**
     * Invokes the SOAP method getTransactionStatus
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("gettransactionstatus/")
    public String getTransactionStatus() {
        try {
            // Call Web Service Operation
            if (port != null) {
                int result = port.getTransactionStatus();
                return new java.lang.Integer(result).toString();
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method beginTransaction
     */
    @PUT
    @Consumes("text/plain")
    @Path("begintransaction/")
    public void putBeginTransaction() {
        try {
            // Call Web Service Operation
            if (port != null) {
                port.beginTransaction();
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

    /**
     * Invokes the SOAP method commitTransaction
     */
    @PUT
    @Consumes("text/plain")
    @Path("committransaction/")
    public void putCommitTransaction() {
        try {
            // Call Web Service Operation
            if (port != null) {
                port.commitTransaction();
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

    /**
     * Invokes the SOAP method rollbackTransaction
     */
    @PUT
    @Consumes("text/plain")
    @Path("rollbacktransaction/")
    public void putRollbackTransaction() {
        try {
            // Call Web Service Operation
            if (port != null) {
                port.rollbackTransaction();
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

    /**
     * Invokes the SOAP method getQuoteVOByPermId
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @param arg2 resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("getquotevobypermid/")
    public String postGetQuoteVOByPermId(JAXBElement<Long> arg0, String arg1, String arg2) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getQuoteVOByPermId(arg0.getValue(), arg1, arg2);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method saveEntityVO
     * @param arg0 resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<java.lang.Long>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("saveentityvo/")
    public JAXBElement<Long> getSaveEntityVO(@QueryParam("arg0") String arg0) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.Long result = port.saveEntityVO(arg0);
                return new JAXBElement<java.lang.Long>(new QName("http//lang.java/", "long"), java.lang.Long.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     *
     */
    private com.tr.rdss.generic.model.iqm.concordance.http.session_client.HttpSessionIQMService getPort() {
        try {
            // Call Web Service Operation
            com.tr.rdss.generic.model.iqm.concordance.http.session_client.HttpSessionIQMServiceService service = new com.tr.rdss.generic.model.iqm.concordance.http.session_client.HttpSessionIQMServiceService();
            com.tr.rdss.generic.model.iqm.concordance.http.session_client.HttpSessionIQMService p = service.getHttpSessionIQMServicePort();
            return p;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }
}
