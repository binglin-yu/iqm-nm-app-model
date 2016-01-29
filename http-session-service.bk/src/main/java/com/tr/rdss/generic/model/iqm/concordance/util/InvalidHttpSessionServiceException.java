/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tr.rdss.generic.model.iqm.concordance.util;

import javax.transaction.SystemException;

/**
 *
 * @author U8015921
 */
public class InvalidHttpSessionServiceException extends RuntimeException{
    public InvalidHttpSessionServiceException(String msg) {
        super(msg);
    }
    public InvalidHttpSessionServiceException(Exception exception) {
        super(exception);
    }
//
//    public InvalidHttpSessionServiceException(SystemException ex) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
