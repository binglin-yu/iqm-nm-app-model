package com.tr.rdss.generic.model.iqm;

import java.util.ArrayList;
import java.util.List;

public class RDCCheck {

    private String entityLevel = null;

    public String getEntityLevel() {
        return entityLevel;
    }

    public void setEntityLevel(String entityLevel) {
        this.entityLevel = entityLevel;
    }

    private List<Property> properties = null;

    public RDCCheck() {
    }

    public RDCCheck(String entityLevel) {
        this.entityLevel = entityLevel;
        properties = new ArrayList<Property>();
    }

    public void assignProperty(Property prop) {
        properties.add(prop);
    }

    public Property getProperty(String name) {
        for (Property a : properties) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    public List<Property> getProperties() {
        return properties;
    }

}
