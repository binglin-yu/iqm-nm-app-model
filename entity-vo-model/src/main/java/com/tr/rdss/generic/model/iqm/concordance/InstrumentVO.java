package com.tr.rdss.generic.model.iqm.concordance;

import java.util.EnumMap;

import com.tr.rdss.generic.model.iqm.InstrumentAsset;
import com.tr.rdss.generic.model.iqm.concordance.util.EnumInterface;

public class InstrumentVO extends EntityVO {

    /**
     *
     * @return entity level
     */
    @Override
    public String getEntityLevel() {
        return InstrumentVO.entityLevel;
    }

    /**
     * entity level
     */
    public static final String entityLevel = "INSTRUMENT";

    /**
     *
     * @param permId instrument perm id
     */
    public InstrumentVO(Long permId) {
        super(InstrumentAsset.class.getSimpleName(), AttributesEnum.PERM_ID.getEnumName(), permId == null ? "-1" : permId.toString());
    }

    /**
     * It list all the officially available attributes for instruments
     */
    public static enum AttributesEnum implements EnumInterface {

        PERM_ID, CUSIP, ISIN, CIN, TRCS, PILC,//
//        TYPE, // TYPE is not configured in model_map_mv!!!
        COMMON_NAME, ADMIN_STATUS, INSTRUMENT_STATUS,
        PRIMARY_LISTED_QUOTE;
        private static final EnumMap<AttributesEnum, String> enumMap;

        static {
            // !!! note, the mapped String must come from the column value of iqm_nav.model_map_mv.bdm_pro_name 
            enumMap = new EnumMap<AttributesEnum, String>(AttributesEnum.class);
            enumMap.put(AttributesEnum.PERM_ID, "INSTRUMENT PERMID");
            enumMap.put(AttributesEnum.CUSIP, "CUSIP");
            enumMap.put(AttributesEnum.ISIN, "ISIN");
            enumMap.put(AttributesEnum.CIN, "CIN CUSIP");

            enumMap.put(AttributesEnum.TRCS, "TRCS PERMID");
            enumMap.put(AttributesEnum.PILC, "PILC");
//            enumMap.put(AttributesEnum.TYPE, "TYPE");
            enumMap.put(AttributesEnum.COMMON_NAME, "COMMON NAME");
            enumMap.put(AttributesEnum.ADMIN_STATUS, "ADMIN STATUS");
            enumMap.put(AttributesEnum.INSTRUMENT_STATUS, "INSTRUMENT STATUS");

            enumMap.put(AttributesEnum.PRIMARY_LISTED_QUOTE, "PRIMARY LISTED QUOTE");
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

}
