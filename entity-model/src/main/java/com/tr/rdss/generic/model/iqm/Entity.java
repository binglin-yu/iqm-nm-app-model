package com.tr.rdss.generic.model.iqm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tr.rdss.generic.model.PersistUnit;
import com.tr.rdss.generic.model.DaoAction;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Asset.class, name = "Asset"),
    @JsonSubTypes.Type(value = Metadata.class, name = "Metadata")
		// !!! need to register new type of entity here !!!
})
public abstract class Entity extends PersistUnit {

    private String key = null;
    private String keyValue = null;

    public boolean equals(Entity e) {
        if (this.key != null && this.key.equals(e.getKey()) && this.keyValue != null && this.keyValue.equals(e.getKeyValue()) && this.getAction().equals(e.getAction())) {
            return true;
        }
        return false;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private List<Attribute> attrs = null;

    public Entity(String key, DaoAction action) {
        super(action);
        this.key = key;
        attrs = new ArrayList<Attribute>();
    }

    public void assignAttribute(Attribute attr) {
        attrs.add(attr);
    }

    public boolean hasAttribute(String name) {
        for (Attribute a : attrs) {
            if (a.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Attribute getAttribute(String name) {
        Attribute tmp = null;
        for (Attribute a : attrs) {
            if (a.getName().equals(name)) {
                tmp = a;
                if (ModelUtil.isCurrentAttribute(a))
                    return a;
            }
        }
        return tmp;
    }

    public Attribute getAttribute(String name, String DateStr) {
        if (attrs == null) {
            return null;
        }
        Date date = ModelUtil.formatDate(DateStr);
        for (Attribute a : attrs) {
            if (a.getName().equals(name)) {
                if (ModelUtil.isCurrentAttribute(a)) {
                    return a;
                }
            }
        }
        return null;
    }

    public List<Attribute> getAttributes() {
        return attrs;
    }

    public List<Attribute> getAttributes(String name) {

        List<Attribute> list = new ArrayList<Attribute>();
        if (attrs == null) {
            return list;
        }

        for (Attribute a : attrs) {
            if (a.getName().equals(name)) {
                list.add(a);
            }
        }
        return list;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getKey());
        sb.append(":");
        // System.out.println("this.getKey():" + this.getKey());
        if (!this.getKey().equals("PERM_ID")) {
            sb.append(this.getAttribute(this.getKey()).getValue());
        } else {
            sb.append(this.getKeyValue());
        }
        return sb.toString();
    }

    public void removeAttribute(Attribute attr) {
        if (attr != null) {
            attrs.remove(attr);
        }
    }

    // added by Kangwen on 2015-Nov
    public void removeDuplicate() {
        if (attrs != null) {
            ArrayList<Attribute> duplicateList = new ArrayList<Attribute>();
            for (int i = 0; i < attrs.size(); i++) {
                Attribute attr = attrs.get(i);
                if (attr instanceof Relationship) {
                    Relationship rs = (Relationship) attr;
                    if (rs.getReference() != null) {
                        rs.getReference().removeDuplicate();
                    }
                }
                if (attrs.indexOf(attr) != attrs.lastIndexOf(attr) && i == attrs.indexOf(attr)) {
                    duplicateList.add(attr);
                }
            }
            for (Attribute attr : duplicateList) {
                attrs.remove(attr);
            }
        }
    }
}

/*
 * $LastChangedDate$ $LastChangedRevision$
 */

// End of file
