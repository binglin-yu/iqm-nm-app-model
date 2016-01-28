package com.tr.rdss.generic.model.iqm.concordance;

import java.util.EnumMap;

import com.tr.rdss.generic.model.iqm.concordance.util.EnumInterface;


public interface LookupInterface {

    /**
     * It is for holding the element of the lookup list
     */
    public static enum AttributesEnum implements EnumInterface {

        ELEMENT;
        private static EnumMap<AttributesEnum, String> enumMap;

        static {
            enumMap = new EnumMap<AttributesEnum, String>(AttributesEnum.class);
            enumMap.put(AttributesEnum.ELEMENT, AttributesEnum.ELEMENT.name());
        }

        public String getEnumName() {
            return (String) enumMap.get(this);
        }

    }
}
