package com.tr.rdss.generic.model.iqm.concordance.util;

import java.io.IOException;

public class InvalidEntityVOException extends Exception {

    public InvalidEntityVOException(String msg, String entityVOJSON) {
        super(msg + "\n" + "entityVO:\n" + entityVOJSON);
    }

    public InvalidEntityVOException(IOException ex) {
        super(ex);
    }

}
