package com.tr.rdss.generic.model.iqm.concordance.util;

public class InvalidRMIServiceException extends Exception {

    public InvalidRMIServiceException(Exception e) {
        super("Invalid RMI Service:\n" + e.toString());
    }

}
