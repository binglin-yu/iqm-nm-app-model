package com.tr.rdss.generic.model.iqm;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tr.rdss.generic.model.DaoAction;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
public class QuoteAsset extends Asset {

    public QuoteAsset(String key, DaoAction action) {
        super(key, action);
    }

    public QuoteAsset(DaoAction action) {
        this("PERM_ID", action);
    }

    public QuoteAsset(String key) {
        this(key, DaoAction.NONE);
    }

    public QuoteAsset() {
        this("RIC", DaoAction.NONE);
    }
}

/*
 * $LastChangedDate$ $LastChangedRevision$
 */
// End of file

