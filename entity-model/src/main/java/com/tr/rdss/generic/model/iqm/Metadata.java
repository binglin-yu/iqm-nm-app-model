package com.tr.rdss.generic.model.iqm;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tr.rdss.generic.model.DaoAction;
import com.tr.rdss.generic.model.iqm.Entity;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
public class Metadata extends Entity {

    public Metadata(String key, DaoAction action) {
        super(key, action);
    }

    public Metadata(String key) {
        super(key, DaoAction.NONE);
    }

    public Metadata(DaoAction action) {
        this("TYPE", action);
    }

    public Metadata() {
        this(DaoAction.NONE);
    }

}
