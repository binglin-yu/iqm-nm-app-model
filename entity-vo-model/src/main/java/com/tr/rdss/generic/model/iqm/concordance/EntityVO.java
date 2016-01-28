package com.tr.rdss.generic.model.iqm.concordance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.tr.rdss.generic.model.DaoAction;
import com.tr.rdss.generic.model.iqm.Attribute;
import com.tr.rdss.generic.model.iqm.Entity;
import com.tr.rdss.generic.model.iqm.InstrumentAsset;
import com.tr.rdss.generic.model.iqm.Metadata;
import com.tr.rdss.generic.model.iqm.Property;
import com.tr.rdss.generic.model.iqm.QuoteAsset;
import com.tr.rdss.generic.model.iqm.Relationship;
import com.tr.rdss.generic.model.iqm.concordance.util.AttributeConverter;
import com.tr.rdss.generic.model.iqm.concordance.util.EnumInterface;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidServiceMethodCallException;
import com.tr.rdss.generic.model.iqm.concordance.util.InvalidRawValueException;
import com.tr.rdss.generic.model.iqm.concordance.util.Utils;

/**
 * 
 * @author U8015921 <br>
 * Always add corresponding changes to functions {createEntityVO, EntityVO}, when creating new type of entity
 */

public abstract class EntityVO {

    /**
     * Hold the entity object.
     */
    protected Entity entity;
    
    /**
     * Only support three values: <br>
     * "Y"  - means all the underlying attributes within {@link entity} have normalized values (if there are) <br>
     *        e.g. "ADMIN_STATUS" attribute has value of TRCS admin status ID (like 1010003) <br>
     * "N"  - means all the underlying attributes within {@link entity} have denormalized values <br>
     *        e.g. "ADMIN_STATUS" attribute has value of TRCS admin status value (like 'Published') <br>
     * null - means the state of isNormalized is unknown <br>
     * Note that, this attribute only works for the held attributes which have normalized values stored in IQM database <br>
     */
    private String isNormalized; 

    /**
     * 
     * @return entity level
     */
    public String getEntityLevel() {
        return null;
    }

    /**
     * 
     * @return the state of isNormalized
     */
    public String isNormalized() {
        return isNormalized;
    }

    /**
     * 
     * @param isNormalized the state of isNormalized 
     * <br>
     * Please make sure the held attributes get values correctly converted 
     * ( all to normalized values or all to denormalized values), before calling this function.
     */
    public void setIsNormalized(String isNormalized) {
        this.isNormalized = isNormalized;
    }

    /**
     * default action
     */
    public static DaoAction defaultAction = DaoAction.NONE;

    /**
     * 
     * @param entity entity object to hold all the attributes
     * @return the newly wrapped EntityVO object
     * <br>
     * Make sure, always add corresponding constructor for new types of EntityVO
     */
    public static EntityVO createEntityVO(Entity entity) {
        EntityVO entityVO = null;
        if (entity != null) {
            String keyStr = entity.getKey();
            String keyValueStr = entity.getKeyValue();
            if (keyStr != null && keyValueStr != null) {
                if (entity instanceof InstrumentAsset) {
                    entityVO = new InstrumentVO(Long.parseLong(keyValueStr));
                } else if (entity instanceof QuoteAsset) {
                    entityVO = new QuoteVO(Long.parseLong(keyValueStr));
                } else if (entity instanceof Metadata) {
                    try {
                        entityVO = (EntityVO) Class.forName(entity.getKeyValue())
                            .newInstance();
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                        Utils.printTrace(false);
                        Utils.printTrace(ex);
                    }
                }
            }

        }

        if (entityVO != null) {
            entityVO.setEntity(entity);
        }
        return entityVO;
    }

    /**
     * 
     * @param classType the detailed sub class of Entity
     * @param key key name of the sub class
     * @param keyValue key value of the sub class
     * @param action action
     */
    protected EntityVO(String classType, String key, String keyValue,
        DaoAction action) {
        if (classType.equals("InstrumentAsset")) {
            entity = new InstrumentAsset(key, action);
        } else if (classType.equals("QuoteAsset")) {
            entity = new QuoteAsset(key, action);
        } else if (classType.equals("Metadata")) {
            entity = new Metadata("TYPE", action);
        }

        entity.setKeyValue(keyValue);
        this.setEntity(entity);
    }

    /**
     * 
     * @param classType the detailed sub class of Entity
     * @param typeValue the sub class of EntityVO
     * <br>
     * This constructor is used for constructing objects, 
     * the attached entities of which are extended from Metadata class.
     */
    protected EntityVO(String classType, String typeValue) {
        this(classType, "TYPE", typeValue);
    }
    
    /**
     * 
     * @param classType the detailed sub class of Entity
     * @param key key name of the sub class
     * @param keyValue key value of the sub class
     */
    protected EntityVO(String classType, String key, String keyValue) {
        this(classType, key, keyValue, defaultAction);
    }

    /**
     * 
     * @return the attached Entity object
     */
    public final Entity getEntity() {
        return entity;
    }

    /**
     * 
     * @return key value
     */
    public final String getKeyValue() {
        return entity.getKeyValue();
    }

    /**
     * 
     * @return key name
     */
    public final String getKey() {
        return entity.getKey();
    }

    /**
     * 
     * @param entity entity
     */
    protected void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * 
     * @param newAttribute new attribute
     * @param dedup a flag to label whether the new attribute should be added if another same copy already exists
     * <br>
     * No operations will happen if any of the following cases <br>
     *  a) new attribute is null <br>
     *  b) new attribute has null name <br>
     *  c) new attribute has null value <br>
     *  d) the input dedup is true, and the same attribute (as the new input newAttribute) already exists <br>
     *     in the included attribute list <br>
     */
    private void addAttribute(Attribute newAttribute, boolean dedup) {
        List<Attribute> attrs = entity.getAttributes(newAttribute.getName());
        if (newAttribute == null || newAttribute.getName() == null
            || newAttribute.getValue() == null) {
            Utils.printMessage(new Exception("ERROR! newAttribute == null || newAttribute.getName() == null || newAttribute.getValue() == null"));
        }
        if (dedup == false) {
            entity.assignAttribute(newAttribute);
            return;
        }

        boolean found = false;
        if (attrs != null && attrs.size() > 0) {
            for (Attribute oldAttr : attrs) {
                    found = Utils.compare(oldAttr, newAttribute);
                    if (found) {
                        if (newAttribute.action != DaoAction.NONE
                            || oldAttr.action != DaoAction.NONE) {
                            // TODO what errors should be raised?
                            Utils.printMessage( new Exception("ERROR! Unexpected to find existing att, "
                                    + "name("
                                    + newAttribute.getName()
                                    + ")"
                                    + ", value("
                                    + newAttribute.getValue()
                                    + ")"
                                    + ", effectiveFrom("
                                    + newAttribute.getEffectiveFrom()
                                    + ")"
                                    + ", effectiveTo("
                                    + newAttribute.getEffectiveTo()
                                    + ")"));
                        }

                        return;
                    }

            }
        }
        entity.assignAttribute(newAttribute);
    }

    /**
     * 
     * @return attribute name list of all the included attributes in the attached Entity object
     */
    public List<String> getAttributeNames() {
        HashSet<String> hashSet = new HashSet<String>();
        List<Attribute> attrs = entity.getAttributes();
        List<String> res = new ArrayList<String>();
        for (Attribute attr : attrs) {
            String name = attr.getName();
            if (!hashSet.contains(attr.getName())) {
                hashSet.add(name);
                res.add(name);
            }
        }
        return res;
    }

    /**
     * 
     * @param attributeEnum attribute enumeration
     * @return attribute
     * @see #getAttribute(java.lang.String) 
     */
    public Attribute getAttribute(EnumInterface attributeEnum) {
        return getAttribute(attributeEnum.getEnumName());
    }
    
    /**
     * 
     * @param attributeEnum attribute enumeration
     * @return attribute list 
     * @see #getAttributes(java.lang.String) 
     */

    public List<Attribute> getAttributes(EnumInterface attributeEnum) {
        return getAttributes(attributeEnum.getEnumName());
    }

    /**
     * 
     * @param attributeName attribute name
     * @return attribute
     * <br>
     * Always return the first found attribute with the input attribute name, if there are multiple matched.
     */
    public Attribute getAttribute(String attributeName) {
        List<Attribute> atts = getAttributes(attributeName);
        if (atts == null || atts.size() > 0) {
            return atts.get(0);
        }
        return null;
    }

    /**
     * 
     * @param attributeName attribute name
     * @return attribute list
     */
    public List<Attribute> getAttributes(String attributeName) {
        return entity.getAttributes(attributeName);
    }

    /**
     * 
     * @return all the attributes included in the object
     */
    public List<Attribute> getAttributes() {
        return entity.getAttributes();
    }


    /**
     * 
     * @param attributeEnum attribute enumeration
     * @param value attribute value
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * 
     * @see #addProperty(com.tr.rdss.generic.model.iqm.concordance.util.EnumInterface, java.lang.Object, java.util.Date, java.util.Date, com.tr.rdss.generic.model.DaoAction) 
     * <br>
     * It is for building up data in ADF side.
     */
    public void addProperty(EnumInterface attributeEnum, Object value,
        java.util.Date effectiveFrom, java.util.Date effectiveTo) {
        addProperty(attributeEnum, value, effectiveFrom, effectiveTo, this.entity.getAction());
    }

    /**
     * 
     * @param attributeEnum attribute enumeration
     * @param value attribute value
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * @param action action
     * 
     * @see #addForceProperty(java.lang.String, java.lang.Object, java.util.Date, java.util.Date, com.tr.rdss.generic.model.DaoAction)
     */
    private void addProperty(EnumInterface attributeEnum, Object value,
        java.util.Date effectiveFrom, java.util.Date effectiveTo, DaoAction action) {
        if (attributeEnum == null) {
            return;
        }
        String name = attributeEnum.getEnumName();
        addForceProperty(name, value, effectiveFrom, effectiveTo, action);
    }

    /**
     * 
     * @param attributeEnum attribute name
     * @param value attribute value
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * 
     * @see #addForceProperty(java.lang.String, java.lang.Object, java.util.Date, java.util.Date, com.tr.rdss.generic.model.DaoAction)
     * <br>
     * It is for building up data in ADF side.
     */
    public void addForceProperty(String attributeEnum, Object value,
        java.util.Date effectiveFrom, java.util.Date effectiveTo) {
        addForceProperty(attributeEnum, value,
            effectiveFrom, effectiveTo, this.entity.getAction());
    }

    /**
     * 
     * @param attributeEnum attribute name
     * @param value attribute value
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * @param action action
     * 
     * @see #addProperty(java.lang.String, java.lang.Object, java.util.Date, java.util.Date, com.tr.rdss.generic.model.DaoAction, boolean) 
     * <br>
     * No adding will happen if the input property already exists in the property list
     */
    private void addForceProperty(String attributeEnum, Object value,
        java.util.Date effectiveFrom, java.util.Date effectiveTo, DaoAction action) {
        addProperty(attributeEnum, value,
            effectiveFrom, effectiveTo, action, true);
    }

    /**
     * 
     * @param name property name
     * @param value property value
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * @param action action
     * @param dedup a flag to label whether duplicate properties should be added
     * 
     * @see #addAttribute(com.tr.rdss.generic.model.iqm.Attribute, boolean) 
     */
    private void addProperty(String name, Object value,
        java.util.Date effectiveFrom, java.util.Date effectiveTo, DaoAction action,
        boolean dedup) {

        if (name == null || value == null) {
            return;
        }

        Property newProperty = new Property(name, value, action, effectiveFrom,
            effectiveTo);

        addAttribute(newProperty, dedup);
    }

    /**
     * 
     * @param relationShipName relationship enumeration
     * @param value entity value
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * 
     * @see #addRelationship(com.tr.rdss.generic.model.iqm.concordance.util.EnumInterface, com.tr.rdss.generic.model.iqm.concordance.EntityVO, java.util.Date, java.util.Date, com.tr.rdss.generic.model.DaoAction)
     * 
     * <br>
     * It is for building up data in ADF side.
     */
    public void addRelationship(EnumInterface relationShipName, EntityVO value,
        java.util.Date effectiveFrom, java.util.Date effectiveTo) {
        addRelationship(relationShipName, value, effectiveFrom, effectiveTo, this.entity.getAction());
    }
    
    /**
     * 
     * @param relationShipName relationship enumeration
     * @param value entity value
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * @param action action
     * 
     * @see #addForceRelationship(java.lang.String, com.tr.rdss.generic.model.iqm.concordance.EntityVO, java.util.Date, java.util.Date, com.tr.rdss.generic.model.DaoAction) addForceRelationship(String, EntityVO, Date, Date, DaoAction) 
     */
    private void addRelationship(EnumInterface relationShipName, EntityVO value,
        java.util.Date effectiveFrom, java.util.Date effectiveTo, DaoAction action) {
        if (relationShipName == null) {
            return;
        }
        String name = relationShipName.getEnumName();
        addForceRelationship(name, value, effectiveFrom, effectiveTo, action);
    }

    /**
     * 
     * @param relationShipName relationship enumeration
     * @param value entity value
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * <br>
     * This will just add the input relationship without checking duplication. <br>
     * This is only for building up lookup VO objects. <br>
     */
    protected void addRelationshipNoDedup(EnumInterface relationShipName,
        EntityVO value, java.util.Date effectiveFrom,
        java.util.Date effectiveTo) {
        if (relationShipName == null) {
            return;
        }
        String name = relationShipName.getEnumName();
        addRelationship(name, value, effectiveFrom,
            effectiveTo, this.entity.getAction(), false);
    }

    /**
     * 
     * @param relationShipName relationship name
     * @param value value
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * 
     * @see #addForceRelationship(java.lang.String, com.tr.rdss.generic.model.iqm.concordance.EntityVO, java.util.Date, java.util.Date, com.tr.rdss.generic.model.DaoAction) 
     * <br>
     * It is for building up data in ADF side.
     */
    public void addForceRelationship(String relationShipName, EntityVO value,
        java.util.Date effectiveFrom, java.util.Date effectiveTo) {
        addForceRelationship(relationShipName, value,
            effectiveFrom, effectiveTo, this.entity.getAction());
    }

    /**
     * 
     * @param relationShipName relationship name
     * @param value value
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * @param action action
     * 
     * @see #addRelationship(java.lang.String, com.tr.rdss.generic.model.iqm.concordance.EntityVO, java.util.Date, java.util.Date, com.tr.rdss.generic.model.DaoAction, boolean) 
     * <br>
     * No adding will happen if the input relationship already exists in the relationship list
     */
    private void addForceRelationship(String relationShipName, EntityVO value,
        java.util.Date effectiveFrom, java.util.Date effectiveTo, DaoAction action) {
        if (relationShipName == null) {
            return;
        }
        addRelationship(relationShipName, value,
            effectiveFrom, effectiveTo, action, true);
    }

    /**
     * 
     * @param relationShipName relationship name
     * @param value value
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * @param action action
     * @param dedup a flag to label whether duplicate properties should be added
     * 
     * @see #addAttribute(com.tr.rdss.generic.model.iqm.Attribute, boolean) 
     */
    private void addRelationship(String relationShipName, EntityVO value,
        java.util.Date effectiveFrom,
        java.util.Date effectiveTo, DaoAction action, boolean dedup) {

        if (relationShipName == null || value == null) {
            return;
        }

        Relationship relationShip = new Relationship(relationShipName,
            value.getEntity(), action, effectiveFrom, effectiveTo);
        addAttribute(relationShip, dedup);
    }

    /**
     * 
     * @param hostProperty the property, to which the change is supposed to happen
     * @param action action
     * @param newValue new value, the denormalized raw value. E.g. TRCS ADMIN_STATUS value "Published" is expected,
     * instead of the corresponding TRCS perm_id 1010003
     * @param newEffectiveFrom new effective from
     * @param newEffectiveTo new effective to
     * @throws InvalidRawValueException 
     * @throws InvalidServiceMethodCallException
     * <br>
     * This function will validate new value against the new property
     */
    public void setNewAttributeWithRawValueForProperty(Property hostProperty, DaoAction action, Object newValue, //
        java.util.Date newEffectiveFrom, java.util.Date newEffectiveTo) //
        throws InvalidRawValueException, InvalidServiceMethodCallException {
        if (newValue == null) {
            return;
        }
        {
            Boolean isOk = AttributeConverter.isValidRawValue(this.getEntityLevel(), hostProperty.getName(), newValue);

            if (isOk) {
                hostProperty.setNewAttribute(action, newValue, newEffectiveFrom, newEffectiveTo);
            } else {
                throw new InvalidRawValueException(hostProperty.getName(), newValue);
            }
        }
    }

    /**
     * 
     * @param hostRelationship the relationship, to which the change is supposed to happen
     * @param action action
     * @param newRef new reference
     * @param newEffectiveFrom effective from of the new reference
     * @param newEffectiveTo effective to of the new reference
     */
    public void setNewAttributeForRelationship(Relationship hostRelationship, DaoAction action, EntityVO newRef, java.util.Date newEffectiveFrom, java.util.Date newEffectiveTo) {
        if (newRef == null || newRef.getEntity() == null) {
            return;
        }

        hostRelationship.setNewAttribute(action, newRef.getEntity(), newEffectiveFrom, newEffectiveTo);
    }
    
    /**
     * 
     * @param attributeEnum attribute enumeration
     * @param value value, 
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     * @throws InvalidRawValueException 
     * @throws InvalidServiceMethodCallException
     * <br>
     * The input value will be converted to a normalized (Long) id, if the host object has state isNormalized=Y (if needed);
     * or to a denormalized (String) value if isNormalized=N.
     */
    public void addNewPropertyWithRawValue(EnumInterface attributeEnum,
        Object value, java.util.Date effectiveFrom,
        java.util.Date effectiveTo) throws InvalidRawValueException, InvalidServiceMethodCallException {

        if (attributeEnum == null || value == null) {
            return;
        }

        String name = attributeEnum.getEnumName();

        Boolean conversionDir = null;
        boolean requestNormalizedValue = "Y".equals(this.isNormalized());

        if (AttributeConverter.isConvertiable(this.getEntityLevel(), name)) {
            if ((requestNormalizedValue && !(value instanceof Long))
                || (!requestNormalizedValue && (value instanceof Long))) {
                conversionDir = requestNormalizedValue;
            }
            Boolean isOk = AttributeConverter.isValidRawValue(
                this.getEntityLevel(), attributeEnum.getEnumName(), value);

            if (!isOk) {
                throw new InvalidRawValueException(name, value);
            }
        }
        if (conversionDir != null) {
            this.addProperty(attributeEnum, AttributeConverter.convert(this.getEntityLevel(), attributeEnum.getEnumName(), value, conversionDir), effectiveFrom,
                effectiveTo, DaoAction.ADD);
        } else {
            this.addProperty(attributeEnum, value, effectiveFrom,
                effectiveTo, DaoAction.ADD);
        }
    }

    /**
     * 
     * @param relationShipName relationship enumeration
     * @param value value
     * @param effectiveFrom effective from
     * @param effectiveTo effective to
     */
    public void addNewRelationship(EnumInterface relationShipName,
        EntityVO value, java.util.Date effectiveFrom,
        java.util.Date effectiveTo) {
        this.addRelationship(relationShipName, value, effectiveFrom,
            effectiveTo, DaoAction.ADD);
    }

//    /**
//     * 
//     * @param relationShipName relationship name
//     * @param value value
//     * @param effectiveFrom effective from
//     * @param effectiveTo effective to
//     */
//    private void addNewForceRelationship(String relationShipName,
//        EntityVO value, java.util.Date effectiveFrom,
//        java.util.Date effectiveTo) {
//        this.addForceRelationship(relationShipName, value, effectiveFrom,
//            effectiveTo, DaoAction.ADD);
//    }

}
