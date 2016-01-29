/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tr.rdss.generic.model.iqm.concordance.http.session;

import com.tr.rdss.generic.model.iqm.concordance.http.session_client.HttpSessionIQMService;
import java.util.List;
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

    private HttpSessionIQMService port;

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
     * Invokes the SOAP method getInstrumentPermIdsByOAPermId
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @param arg2 resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("getinstrumentpermidsbyoapermid/")
    public String postGetInstrumentPermIdsByOAPermId(JAXBElement<Long> arg0, String arg1, String arg2) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getInstrumentPermIdsByOAPermId(arg0.getValue(), arg1, arg2);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getHistoryAttributeOfQuoteByPermId
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @param arg2 resource URI parameter
     * @param arg3 resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("gethistoryattributeofquotebypermid/")
    public String postGetHistoryAttributeOfQuoteByPermId(JAXBElement<Long> arg0, String arg1, String arg2, String arg3) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getHistoryAttributeOfQuoteByPermId(arg0.getValue(), arg1, arg2, arg3);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getHistoryAttributeOfInstrumentByPermId
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @param arg2 resource URI parameter
     * @param arg3 resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("gethistoryattributeofinstrumentbypermid/")
    public String postGetHistoryAttributeOfInstrumentByPermId(JAXBElement<Long> arg0, String arg1, String arg2, String arg3) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getHistoryAttributeOfInstrumentByPermId(arg0.getValue(), arg1, arg2, arg3);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getMarketRDNExchangeCodeByPermId
     * @param arg0 resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<com.tr.rdss.generic.model.iqm.concordance.http.session_client.GetMarketRDNExchangeCodeByPermIdResponse>
     */
    @POST
    @Produces("application/xml")
    @Consumes("application/xml")
    @Path("getmarketrdnexchangecodebypermid/")
    public JAXBElement<com.tr.rdss.generic.model.iqm.concordance.http.session_client.GetMarketRDNExchangeCodeByPermIdResponse> postGetMarketRDNExchangeCodeByPermId(JAXBElement<Long> arg0) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.util.List<java.lang.String> result = port.getMarketRDNExchangeCodeByPermId(arg0.getValue());

                class GetMarketRDNExchangeCodeByPermIdResponse_1 extends com.tr.rdss.generic.model.iqm.concordance.http.session_client.GetMarketRDNExchangeCodeByPermIdResponse {

                    GetMarketRDNExchangeCodeByPermIdResponse_1(java.util.List<java.lang.String> _return) {
                        this._return = _return;
                    }
                }
                com.tr.rdss.generic.model.iqm.concordance.http.session_client.GetMarketRDNExchangeCodeByPermIdResponse response = new GetMarketRDNExchangeCodeByPermIdResponse_1(result);
                return new com.tr.rdss.generic.model.iqm.concordance.http.session_client.ObjectFactory().createGetMarketRDNExchangeCodeByPermIdResponse(response);
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
     * Invokes the SOAP method getOrgLookup
     * @param arg0 resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("getorglookup/")
    public String getOrgLookup(@QueryParam("arg0") String arg0) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getOrgLookup(arg0);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getInstrumentVOByPermId
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @param arg2 resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("getinstrumentvobypermid/")
    public String postGetInstrumentVOByPermId(JAXBElement<Long> arg0, String arg1, String arg2) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getInstrumentVOByPermId(arg0.getValue(), arg1, arg2);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
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
     * Invokes the SOAP method getQuotePermIdBySymbol
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @param arg2 resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<java.lang.Long>
     */
    @POST
    @Produces("application/xml")
    @Consumes("application/xml")
    @Path("getquotepermidbysymbol/")
    public JAXBElement<Long> postGetQuotePermIdBySymbol(String arg0, JAXBElement<Long> arg1, String arg2) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.Long result = port.getQuotePermIdBySymbol(arg0, arg1.getValue(), arg2);
                return new JAXBElement<java.lang.Long>(new QName("http//lang.java/", "long"), java.lang.Long.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getMasterGateLookup
     * @param arg0 resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("getmastergatelookup/")
    public String getMasterGateLookup(@QueryParam("arg0") String arg0) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getMasterGateLookup(arg0);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getModelMetadataProperties
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("getmodelmetadataproperties/")
    public String getModelMetadataProperties() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getModelMetadataProperties();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method countQuotesInRDC
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<java.lang.Long>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("countquotesinrdc/")
    public JAXBElement<Long> getCountQuotesInRDC(@QueryParam("arg0") String arg0, @QueryParam("arg1") String arg1) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.Long result = port.countQuotesInRDC(arg0, arg1);
                return new JAXBElement<java.lang.Long>(new QName("http//lang.java/", "long"), java.lang.Long.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method countInstrumentsInRDC
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<java.lang.Long>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("countinstrumentsinrdc/")
    public JAXBElement<Long> getCountInstrumentsInRDC(@QueryParam("arg0") String arg0, @QueryParam("arg1") String arg1) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.Long result = port.countInstrumentsInRDC(arg0, arg1);
                return new JAXBElement<java.lang.Long>(new QName("http//lang.java/", "long"), java.lang.Long.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getQuotePermIdsByBaseAssetPermId
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @param arg2 resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("getquotepermidsbybaseassetpermid/")
    public String postGetQuotePermIdsByBaseAssetPermId(JAXBElement<Long> arg0, String arg1, String arg2) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getQuotePermIdsByBaseAssetPermId(arg0.getValue(), arg1, arg2);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getModelMetadataPermIdByLevelNameValue
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @param arg2 resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<java.lang.Long>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("getmodelmetadatapermidbylevelnamevalue/")
    public JAXBElement<Long> getModelMetadataPermIdByLevelNameValue(@QueryParam("arg0") String arg0, @QueryParam("arg1") String arg1, @QueryParam("arg2") String arg2) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.Long result = port.getModelMetadataPermIdByLevelNameValue(arg0, arg1, arg2);
                return new JAXBElement<java.lang.Long>(new QName("http//lang.java/", "long"), java.lang.Long.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getMarketPermIdByRDNExchangeCode
     * @param arg0 resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<java.lang.Long>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("getmarketpermidbyrdnexchangecode/")
    public JAXBElement<Long> getMarketPermIdByRDNExchangeCode(@QueryParam("arg0") String arg0) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.Long result = port.getMarketPermIdByRDNExchangeCode(arg0);
                return new JAXBElement<java.lang.Long>(new QName("http//lang.java/", "long"), java.lang.Long.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getQuotePermidByRic
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @param arg2 resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<java.lang.Long>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("getquotepermidbyric/")
    public JAXBElement<Long> getQuotePermidByRic(@QueryParam("arg0") String arg0, @QueryParam("arg1") String arg1, @QueryParam("arg2") String arg2) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.Long result = port.getQuotePermidByRic(arg0, arg1, arg2);
                return new JAXBElement<java.lang.Long>(new QName("http//lang.java/", "long"), java.lang.Long.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method rdcCheckForCcd
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @param arg2 resource URI parameter
     * @param arg3 resource URI parameter
     * @param arg4 resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("rdccheckforccd/")
    public String postRdcCheckForCcd(String arg0, String arg1, String arg2, String arg3, JAXBElement<List<String>> arg4) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.rdcCheckForCcd(arg0, arg1, arg2, arg3, arg4.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method invokeDBFunction
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("invokedbfunction/")
    public String getInvokeDBFunction(@QueryParam("arg0") String arg0, @QueryParam("arg1") String arg1) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.invokeDBFunction(arg0, arg1);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getCurrencyPermIdByCode
     * @param arg0 resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<java.lang.Long>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("getcurrencypermidbycode/")
    public JAXBElement<Long> getCurrencyPermIdByCode(@QueryParam("arg0") String arg0) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.Long result = port.getCurrencyPermIdByCode(arg0);
                return new JAXBElement<java.lang.Long>(new QName("http//lang.java/", "long"), java.lang.Long.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getMetadataValueByEnuPermid
     * @param arg0 resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("getmetadatavaluebyenupermid/")
    public String postGetMetadataValueByEnuPermid(JAXBElement<Long> arg0) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getMetadataValueByEnuPermid(arg0.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getCurrencyCodeByPermId
     * @param arg0 resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("getcurrencycodebypermid/")
    public String postGetCurrencyCodeByPermId(JAXBElement<Long> arg0) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getCurrencyCodeByPermId(arg0.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getTimeZoneByMarketPermId
     * @param arg0 resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("gettimezonebymarketpermid/")
    public String postGetTimeZoneByMarketPermId(JAXBElement<Long> arg0) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getTimeZoneByMarketPermId(arg0.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     *
     */
    private HttpSessionIQMService getPort() {
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
