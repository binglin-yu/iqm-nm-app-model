package com.tr.rdss.generic.model.iqm.concordance.util;

public class InvalidServiceMethodCallException extends Exception {

    public InvalidServiceMethodCallException(Exception e) {
        this(e.toString());
    }

    public InvalidServiceMethodCallException(String msg) {
        super("Invalid Service Method Call:\n" + msg);
    }

}
