package com.tr.rdss.generic.model.iqm.concordance;

import java.util.EnumMap;

import com.tr.rdss.generic.model.iqm.Entity;
import com.tr.rdss.generic.model.iqm.concordance.util.EnumInterface;

public class ModelMetadataVO extends EntityVO {

    /**
     * Defines the key for ModelMetadata object
     */
    public static class ModelMetadataVOKey {

        /**
         * 
         * @return entity level
         */
        public String getEntityLevel() {
            return entityLevel;
        }

        /**
         * 
         * @return property name
         */
        public String getPropertyName() {
            return propertyName;
        }

        /**
         * 
         * @return property value
         */
        public String getPropertyValue() {
            return propertyValue;
        }
        
        private String entityLevel;
        private String propertyName;
        private String propertyValue;

        public ModelMetadataVOKey(String entityLevel, String propertyName,
            String propertyValue) {
            this.entityLevel = entityLevel == null ? null : entityLevel.toUpperCase();
            this.propertyName = propertyName;
            this.propertyValue = propertyValue == null ? null : propertyValue.toUpperCase();
        }

        @Override
        public boolean equals(Object a) {
            if (entityLevel.equals(((ModelMetadataVOKey) a).entityLevel)
                && propertyName
                .equals(((ModelMetadataVOKey) a).propertyName)
                && propertyValue.equals(((ModelMetadataVOKey) a).propertyValue)) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }

        @Override
        public String toString() {
            return "<" + entityLevel + "," + propertyName + "," + propertyValue
                + "," + ">";
        }
    }

    /**
     * key of the ModelMetadata
     */
    private ModelMetadataVOKey key;

    public ModelMetadataVO() {
        super("Metadata", ModelMetadataVO.class.getName());
    }

    public ModelMetadataVO(Entity entity) {
        this();
        this.setEntity(entity);
    }

    /**
     * AttributesEnum list all the officially available attributes for the class
     */
    public static enum AttributesEnum implements EnumInterface {

        DOM_PERM_ID, DOM_NAME, ENU_UNIQUENAME, ENU_PERM_ID, ENU_VALUE, TABLE_NAME, PROPERTY_NAME, ENTITY_LEVEL;

	private static final EnumMap<AttributesEnum, String> enumMap;

        static {
            // !!!the mapped string can be customized, as long as unique
            enumMap = new EnumMap<AttributesEnum, String>(AttributesEnum.class);
            enumMap.put(AttributesEnum.DOM_PERM_ID, "DOM_PERMID");
            enumMap.put(AttributesEnum.DOM_NAME, "DOM_NAME");
            enumMap.put(AttributesEnum.ENU_UNIQUENAME, "ENU_UNIQUENAME");
            enumMap.put(AttributesEnum.ENU_PERM_ID, "ENU_PERMID");
            enumMap.put(AttributesEnum.ENU_VALUE, "ENU_VALUE");
            enumMap.put(AttributesEnum.TABLE_NAME, "TABLE_NAME");
            enumMap.put(AttributesEnum.PROPERTY_NAME, "PROPERTY_NAME");
            enumMap.put(AttributesEnum.ENTITY_LEVEL, "ENTITY_LEVEL");

        }

        /**
         * 
         * @return enumeration name
         */
        @Override
        public String getEnumName() {
            return (String) enumMap.get(this);
        }

    }

    public ModelMetadataVO(Long domPermId, String domName, Long enuPermId,
        String enuUniqueName, String enuValue, String tableName,
        String propertyName, String entityLevel) {
        this();
        this.addProperty(AttributesEnum.DOM_PERM_ID, domPermId, null, null);
        this.addProperty(AttributesEnum.DOM_NAME, domName, null, null);
        this.addProperty(AttributesEnum.ENU_UNIQUENAME, enuUniqueName, null,
            null);
        this.addProperty(AttributesEnum.ENU_PERM_ID, enuPermId, null, null);
        this.addProperty(AttributesEnum.ENU_VALUE, enuValue, null, null);
        this.addProperty(AttributesEnum.TABLE_NAME, tableName, null, null);
        this.addProperty(AttributesEnum.PROPERTY_NAME, propertyName, null, null);
        this.addProperty(AttributesEnum.ENTITY_LEVEL, entityLevel, null, null);
    }

    private void setKey() {
        key = new ModelMetadataVOKey(this.getEntityLevel(),
            this.getPropertyName(), this.getPropertyValue());
    }

    /**
     * 
     * @return perm id of the metadata enumeration value 
     */
    public Long getId() {
        if ( this.getAttribute(AttributesEnum.ENU_PERM_ID) == null ) 
            return null;
        return Long.parseLong(this.getAttribute(AttributesEnum.ENU_PERM_ID)
            .getValue());
    }

    /**
     * 
     * @return metadata enumeration value
     */
    public String getPropertyValue() {
        if ( this.getAttribute(AttributesEnum.ENU_UNIQUENAME) == null ) 
            return null;
        return this.getAttribute(AttributesEnum.ENU_UNIQUENAME).getValue();
    }

    /**
     * 
     * @return the corresponding domain name
     */
    public String getDomName() {
        if ( this.getAttribute(AttributesEnum.DOM_NAME) == null ) 
            return null;
        return this.getAttribute(AttributesEnum.DOM_NAME).getValue();
    }

    /**
     * 
     * @return entity level
     */
    @Override
    public String getEntityLevel() {
        if ( this.getAttribute(AttributesEnum.ENTITY_LEVEL) == null ) 
            return null;
        return this.getAttribute(AttributesEnum.ENTITY_LEVEL).getValue();
    }

    /**
     * 
     * @return property name
     */
    public String getPropertyName() {
        if ( this.getAttribute(AttributesEnum.PROPERTY_NAME) == null ) 
            return null;
        return this.getAttribute(AttributesEnum.PROPERTY_NAME).getValue();
    }

    /**
     * 
     * @return general info of the ModelMetadata object
     */
    @Override
    public String toString() {
        return "<" + getId() + "," + getMetadataKey() + "," + getDomName()
            + "," + getPropertyValue() + ">";
    }

    /**
     * 
     * @return key of the object
     */
    public ModelMetadataVOKey getMetadataKey() {
        if (key == null) {
            setKey();
        }
        return key;
    }
}
