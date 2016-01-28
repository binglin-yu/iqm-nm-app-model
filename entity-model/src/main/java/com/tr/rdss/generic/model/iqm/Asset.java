package com.tr.rdss.generic.model.iqm;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tr.rdss.generic.model.DaoAction;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = InstrumentAsset.class, name = "InstrumentAsset"),
    @JsonSubTypes.Type(value = QuoteAsset.class, name = "QuoteAsset")})
public abstract class Asset extends Entity {

    public Asset(String key, DaoAction action) {
        super(key, action);
    }

    public Asset(String key) {
        this(key, DaoAction.NONE);
    }

    public String getAssetClassValue() {
        if (this instanceof QuoteAsset) {
            return "Q";
        } else if (this instanceof InstrumentAsset) {
            return "I";
        } else {
            return "N";
        }
    }

}

/*
 * $LastChangedDate$ $LastChangedRevision$
 */

// End of file
