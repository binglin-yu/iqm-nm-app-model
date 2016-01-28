package com.tr.rdss.generic.model.iqm;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tr.rdss.generic.model.DaoAction;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
public class Relationship extends Attribute {

    /**
     * constructor *
     */
    public Relationship() {
        this(null, null, DaoAction.NONE);
    }

    Entity ref = null;

    ;

	private Relationship(Entity owner, String name, Entity ref, DaoAction action) {
        super(owner, name, action);
        this.ref = ref;
    }

    public Relationship(String name, Entity ref, DaoAction action) {
        super(null, name, action);
        this.ref = ref;
    }

    public Relationship(String name, Entity ref, DaoAction action, Entity newRef, String newEffectiveFrom, String newEffectiveTo) {
        this(name, ref, action);
        Relationship newAttribute = new Relationship(name, newRef, action);
        newAttribute.setEffectiveFrom(newEffectiveFrom);
        newAttribute.setEffectiveTo(newEffectiveTo);
        this.setNewAttribute(newAttribute);
    }

    public Relationship(String name, Entity value, String effectiveFrom, String effectiveTo, DaoAction action, Entity newValue, String newEffectiveFrom, String newEffectiveTo) {
        this(name, value, action);

        this.setEffectiveFrom(effectiveFrom);
        this.setEffectiveTo(effectiveTo);

        Relationship newAttribute = new Relationship(name, newValue, action);
        newAttribute.setEffectiveFrom(newEffectiveFrom);
        newAttribute.setEffectiveTo(newEffectiveTo);
        this.setNewAttribute(newAttribute);
    }

    private Relationship(Entity owner, String name, Entity ref) {
        super(owner, name, DaoAction.NONE);
        this.ref = ref;
    }

    public Relationship(String name, Entity ref) {
        super(null, name, DaoAction.NONE);
        this.ref = ref;
    }

    //added by byu 2015-Oct
    public Relationship(String name, Entity ref, DaoAction action,
        java.util.Date effectiveFrom, java.util.Date effectiveTo) {

        this(name, ref, action);
        this.setEffectiveFrom(ModelUtil.formatDateString(effectiveFrom));
        this.setEffectiveTo(ModelUtil.formatDateString(effectiveTo));
    }

    public void setReference(Entity ref) {
        this.ref = ref;
    }

    public void setNewAttribute(DaoAction action, Entity newRef, java.util.Date newEffectiveFrom, java.util.Date newEffectiveTo) {
        if (newRef == null) {
            return;
        }
        Relationship newRship = new Relationship(this.getName(), newRef,
            DaoAction.NONE, newEffectiveFrom, newEffectiveTo);
        this.setNewAttribute(newRship);
        this.setAction(action);
    }

    public Entity getReference() {
        return ref;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
//		sb.append(this.entity.getKey());
//		sb.append(":");
//		sb.append(((Property) entity.getAttribute(entity.getKey())).getValue());
//		sb.append("-");
        sb.append(name);
        sb.append("-");
        if (this.ref != null) {
            sb.append(this.ref.getKey());
            sb.append(":");
            sb.append(ref.getKeyValue());
        } else {
            sb.append("null");
        }

        return sb.toString();
    }

    // added by kangwen on 2015-Nov
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Relationship)) {
            return false;
        }
        Relationship other = (Relationship) obj;
        if (this.ref == null) {
            if (other.getReference() != null) {
                return false;
            } else {
                return true;
            }
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!ref.equals(other.getReference())) {
            return false;
        }
        return true;
    }

    @Override
    public String getValue() {
        return ref.getKeyValue();
    }
}

/*
 * $LastChangedDate$ $LastChangedRevision$
 */

// End of file
