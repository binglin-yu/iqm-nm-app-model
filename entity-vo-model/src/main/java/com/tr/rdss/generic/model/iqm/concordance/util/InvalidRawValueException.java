package com.tr.rdss.generic.model.iqm.concordance.util;

public class InvalidRawValueException extends Exception {

    public InvalidRawValueException(String attName, Object value) {
        super("Invalid Raw Value: attName(" + attName + "),value(" + value.toString() + ")");
    }

}
