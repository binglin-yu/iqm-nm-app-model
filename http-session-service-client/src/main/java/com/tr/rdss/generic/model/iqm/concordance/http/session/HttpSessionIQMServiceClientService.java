package com.tr.rdss.generic.model.iqm.concordance.http.session;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author U8015921
 */
public class HttpSessionIQMServiceClientService {
    static final String URL_TAIL = "/iqm-nm-app-model-http-session-service/HttpSessionIQMServiceService?wsdl";

    private String urlStr = "10.238.126.3:80";
    private final String userName;
    private final String pwd;    
    private final URL url;
    
    public HttpSessionIQMServiceClientService(String urlStr, String userName, String pwd) throws MalformedURLException {
        this.urlStr = urlStr;
        this.userName = userName;
        this.pwd = pwd;
        this.url = new URL("http://" + urlStr + URL_TAIL);
    }
    
    
        
}
