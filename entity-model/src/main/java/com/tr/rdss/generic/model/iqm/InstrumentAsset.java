package com.tr.rdss.generic.model.iqm;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tr.rdss.generic.model.DaoAction;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
public class InstrumentAsset extends Asset {

    public InstrumentAsset(String key, DaoAction action) {
        super(key, action);
    }

    public InstrumentAsset(String key) {
        super(key, DaoAction.NONE);
    }

    public InstrumentAsset(DaoAction action) {
        this("PERM_ID", action);
    }

    public InstrumentAsset() {
        this(DaoAction.NONE);
    }
}

/*
 * $LastChangedDate$ $LastChangedRevision$
 */

// End of file
