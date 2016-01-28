package com.tr.rdss.generic.model.iqm.concordance;

import java.util.EnumMap;

import com.tr.rdss.generic.model.iqm.Entity;
import com.tr.rdss.generic.model.iqm.concordance.util.EnumInterface;

/**
 * 
 * @author U8015921
 */
public class CurrencyVO extends EntityVO {

    public CurrencyVO() {
        super("Metadata", CurrencyVO.class.getName());
    }
    
    public CurrencyVO(Entity entity) {
        this();
        this.setEntity(entity);
    }

    /**
     * AttributesEnum list all the officially available attributes for the class
     */
    public static enum AttributesEnum implements EnumInterface {

        CURRENCY_PERM_ID, CURRENCY_NAME, CURRENCY_CODE;
        private static final EnumMap<AttributesEnum, String> enumMap;

        static {
            //!!! the mapped string can be customized, as long as unique
            enumMap = new EnumMap<AttributesEnum, String>(AttributesEnum.class);
            enumMap.put(AttributesEnum.CURRENCY_PERM_ID, "CURRENCY_PERM_ID");
            enumMap.put(AttributesEnum.CURRENCY_NAME, "CURRENCY_NAME");
            enumMap.put(AttributesEnum.CURRENCY_CODE, "CURRENCY_CODE");
        }

        /**
         * 
         * @return the name of the enumeration value
         */
        public String getEnumName() {
            return (String) enumMap.get(this);
        }
    }

    public CurrencyVO(Long currencyPermId, String currencyName,
        String currencyCode) {
        this();
        this.addProperty(AttributesEnum.CURRENCY_PERM_ID, currencyPermId, null,
            null);
        this.addProperty(AttributesEnum.CURRENCY_NAME, currencyName, null, null);
        this.addProperty(AttributesEnum.CURRENCY_CODE, currencyCode, null, null);
    }

    /**
     * 
     * @return (TRCS) currency perm id
     */
    public Long getId() {
        return Long.parseLong(this.getAttribute(AttributesEnum.CURRENCY_PERM_ID).getValue());
    }

    /**
     * 
     * @return (TRCS) currency name
     */
    public String getName() {
        return this.getAttribute(AttributesEnum.CURRENCY_NAME).getValue();
    }

    /**
     * 
     * @return (TRCS) currency code
     */
    public String getCode() {
        return this.getAttribute(AttributesEnum.CURRENCY_CODE).getValue();
    }

    /**
     * 
     * @return general info of the currency object
     */
    @Override
    public String toString() {
        return "<" + getId() + "," + getName() + "," + getCode() + ">";
    }

}
