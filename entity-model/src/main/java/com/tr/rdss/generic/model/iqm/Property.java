package com.tr.rdss.generic.model.iqm;

import com.tr.rdss.generic.model.DaoAction;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
public class Property extends Attribute {

    /**
     * constructor *
     */
    public Property() {
        this(null, null, DaoAction.NONE);
    }

    String value;

    public Property(String name, String value, DaoAction action) {
        super(null, name, action);

        this.value = value;
    }

    public Property(String name, String value, DaoAction action, String newValue, String newEffectiveFrom, String newEffectiveTo) {
        this(name, value, action);

        Property newAttribute = new Property(name, newValue, action);
        newAttribute.setEffectiveFrom(newEffectiveFrom);
        newAttribute.setEffectiveTo(newEffectiveTo);
        this.setNewAttribute(newAttribute);
    }

    public Property(String name, String value, String effectiveFrom, String effectiveTo, DaoAction action, String newValue, String newEffectiveFrom, String newEffectiveTo) {
        this(name, value, action);

        this.setEffectiveFrom(effectiveFrom);
        this.setEffectiveTo(effectiveTo);

        Property newAttribute = new Property(name, newValue, action);
        newAttribute.setEffectiveFrom(newEffectiveFrom);
        newAttribute.setEffectiveTo(newEffectiveTo);
        this.setNewAttribute(newAttribute);
    }

    private Property(Entity owner, String name, String value, DaoAction action) {
        super(owner, name, action);

        this.value = value;
    }

    private Property(Entity owner, String name, String value) {
        super(owner, name, DaoAction.NONE);

        this.value = value;
    }

    //added by byu on 2015-Oct
    public Property(String name, Object value, DaoAction action,
        java.util.Date effectiveFrom, java.util.Date effectiveTo) {
        this(name, null, action);

        String valueStr = null;
        if (value instanceof java.util.Date) {
            valueStr = ModelUtil.formatDateString((java.util.Date) value);
        } else {
            valueStr = value.toString();
        }
        this.setValue(valueStr);

        this.setEffectiveFrom(ModelUtil.formatDateString(effectiveFrom));
        this.setEffectiveTo(ModelUtil.formatDateString(effectiveTo));
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setNewAttribute(DaoAction action, Object newValue, java.util.Date newEffectiveFrom, java.util.Date newEffectiveTo) {
        if (newValue == null) {
            return;
        }

        Property newProperty = new Property(this.getName(), newValue, DaoAction.NONE, newEffectiveFrom, newEffectiveTo);
        this.setNewAttribute(newProperty);
        this.setAction(action);
    }

    public String getValue() {
        return this.value;
    }

    // added by kangwen on 2015-Nov
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Property)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        Property other = (Property) obj;

        if (value == null) {
            if (other.getValue() != null) {
                return false;
            }
        } else if (!value.equals(other.getValue())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//		StringBuffer sb = new StringBuffer();
//		sb.append(this.entity.getKey());
//		sb.append(":");
//		sb.append(((Property) entity.getAttribute(entity.getKey())).getValue());
//		sb.append("-");
//		sb.append(name);
//		sb.append("-");
//		sb.append(value);
//		return sb.toString();
        return this.name + ":" + this.value;
    }
}

/*
 * $LastChangedDate$ $LastChangedRevision$
 */

// End of file
