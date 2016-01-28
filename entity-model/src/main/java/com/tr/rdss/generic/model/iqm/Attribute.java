package com.tr.rdss.generic.model.iqm;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tr.rdss.generic.model.DaoAction;
import com.tr.rdss.generic.model.PersistUnit;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Property.class, name = "Property"),
    @JsonSubTypes.Type(value = Relationship.class, name = "Relationship")})
public abstract class Attribute extends PersistUnit {

    protected Entity entity;
    protected String name;
    protected String effectiveFrom;
    protected String effectiveTo;

    protected Attribute newAttribute;

    public Attribute getNewAttribute() {
        return newAttribute;
    }

    public void setNewAttribute(Attribute newAttribute) {
        this.newAttribute = newAttribute;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(String effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public Attribute(Entity entity, String name, DaoAction action) {
        super(action);
        this.entity = entity;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Entity getEntity() {
        return entity;
    }

    /**
     * 
     * @return value It's the subclss's responsibility to decide what value to return. For 
     * example, the property should return straight value of the property, for 
     * Relationships, the return value could be the Key value of the reference 
     * entity, for example, ISIN, ORGID, etc.
     */
    public abstract String getValue();

    // added by kangwen on 2015-Nov
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
            + ((effectiveFrom == null) ? 0 : effectiveFrom.hashCode());
        result = prime * result
            + ((effectiveTo == null) ? 0 : effectiveTo.hashCode());
        result = prime * result + ((entity == null) ? 0 : entity.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
            + ((newAttribute == null) ? 0 : newAttribute.hashCode());
        return result;
    }

    // added by kangwen on 2015-Nov
    @Override
    public boolean equals(Object obj) {
        if (this == null) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Attribute other = (Attribute) obj;
        if (effectiveFrom == null) {
            if (other.effectiveFrom != null) {
                return false;
            }
        } else if (!effectiveFrom.equals(other.effectiveFrom)) {
            return false;
        }
        if (effectiveTo == null) {
            if (other.effectiveTo != null) {
                return false;
            }
        } else if (!effectiveTo.equals(other.effectiveTo)) {
            return false;
        }
        if (entity == null) {
            if (other.entity != null) {
                return false;
            }
        } else if (!entity.equals(other.entity)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (newAttribute == null) {
            if (other.newAttribute != null) {
                return false;
            }
        } else if (!newAttribute.equals(other.newAttribute)) {
            return false;
        }

        if (this.action != null && other.getAction() != null) {
            if (!this.action.equals(other.getAction())) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}

/*
 * $LastChangedDate$ $LastChangedRevision$
 */

// End of file
