package com.tr.rdss.generic.model.iqm.concordance;

import java.util.EnumMap;
import com.tr.rdss.generic.model.iqm.Attribute;
import com.tr.rdss.generic.model.iqm.Entity;
import com.tr.rdss.generic.model.iqm.Relationship;
import com.tr.rdss.generic.model.iqm.concordance.util.EnumInterface;

public class MarketVO extends EntityVO {

    public MarketVO() {
        super("Metadata", MarketVO.class.getName());
    }

    public MarketVO(Entity entity) {
        this();
        this.setEntity(entity);
    }

    /**
     * AttributesEnum list all the officially available attributes for the class
     */
    public static enum AttributesEnum implements EnumInterface {

        MARKET_PERM_ID, NAME_TYPE_ID, NAME_TYPE, NAME_VALUE, HIGHEST_MARKET;
        private static final EnumMap<AttributesEnum, String> enumMap;

        static {
            // the mapped string can be customized, as long as unique
            enumMap = new EnumMap<AttributesEnum, String>(AttributesEnum.class);
            enumMap.put(AttributesEnum.MARKET_PERM_ID, "MARKET_PERMID");
            enumMap.put(AttributesEnum.NAME_TYPE_ID, "NAME_TYPE_ID");
            enumMap.put(AttributesEnum.NAME_TYPE, "NAME_TYPE");
            enumMap.put(AttributesEnum.NAME_VALUE, "NAME_VALUE");
            enumMap.put(AttributesEnum.HIGHEST_MARKET, "HIGHEST_MARKET");
        }

        /**
         * 
         * @return enumeration name
         */
        public String getEnumName() {
            return (String) enumMap.get(this);
        }
    }

    public MarketVO(Long marketPermId, Long nameTypeId, String nameType,
        String nameValue, MarketVO marketVO) {
        this();
        this.addProperty(AttributesEnum.MARKET_PERM_ID, marketPermId, null,
            null);
        this.addProperty(AttributesEnum.NAME_TYPE_ID, nameTypeId, null, null);
        this.addProperty(AttributesEnum.NAME_TYPE, nameType, null, null);
        this.addProperty(AttributesEnum.NAME_VALUE, nameValue, null, null);
        this.addRelationship(AttributesEnum.HIGHEST_MARKET, marketVO, null,
            null);
    }

    /**
     * 
     * @return market perm id
     */
    public Long getId() {
        return Long.parseLong(this.getAttribute(AttributesEnum.MARKET_PERM_ID)
            .getValue());
    }

    /**
     * 
     * @return market name value
     */
    public String getName() {
        if (this.getAttribute(AttributesEnum.NAME_VALUE) != null) {
            return this.getAttribute(AttributesEnum.NAME_VALUE).getValue();
        } else {
            return null;
        }
    }

    /**
     * 
     * @return market name type
     */
    public String getNameType() {
        return this.getAttribute(AttributesEnum.NAME_TYPE).getValue();
    }

    /**
     * 
     * @return highest market perm id of the host market
     */
    public Long getHighestId() {
        Attribute t = this.getAttribute(MarketVO.AttributesEnum.HIGHEST_MARKET);
        if (t == null) {
            return null;
        }
        return ((MarketVO) EntityVO.createEntityVO(((Relationship) t)
            .getReference())).getId();

    }

    /**
     * 
     * @return general info of the Market object
     */
    @Override
    public String toString() {
        return "<" + getId() + "," + getNameType() + "," + getName() + ","
            + getHighestId() + ">";
    }
}
