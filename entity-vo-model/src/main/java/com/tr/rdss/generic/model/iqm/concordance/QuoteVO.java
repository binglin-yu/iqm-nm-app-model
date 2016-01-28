package com.tr.rdss.generic.model.iqm.concordance;

import java.util.EnumMap;

import com.tr.rdss.generic.model.iqm.QuoteAsset;
import com.tr.rdss.generic.model.iqm.concordance.util.EnumInterface;

public class QuoteVO extends EntityVO {

    /**
     *
     * @return entity level
     */
    @Override
    public String getEntityLevel() {
        return QuoteVO.entityLevel;
    }

    public static final String entityLevel = "QUOTE";

    public QuoteVO(Long permId) {
        super(QuoteAsset.class.getSimpleName(), AttributesEnum.PERM_ID.getEnumName(), permId == null ? "-1" : permId.toString());
    }

    public static enum AttributesEnum implements EnumInterface {

        PERM_ID, BASE_ASSET, FIRST_TRADING_DAY, DELIST_DATE, QUOTATION_CURRENCY, TRADES_ON_MARKET, RIC, TICKER_SYMBOL, ADMIN_STATUS, TRADING_STATUS, TRADES_ON_SUBMARKET, ROUND_LOT_SIZE, COMMON_NAME, SHORT_NAME, EXCHANGE_ISIN,
        TRADING_SYMBOL,
        ;
        private static final EnumMap<AttributesEnum, String> enumMap;

        static {
            // !!! note, the mapped String must come from the column value of iqm_nav.model_map_mv.bdm_pro_name 

            enumMap = new EnumMap<AttributesEnum, String>(AttributesEnum.class);
            enumMap.put(AttributesEnum.PERM_ID, "QUOTE PERMID");
            enumMap.put(AttributesEnum.BASE_ASSET, "BASE ASSET");
            enumMap.put(AttributesEnum.FIRST_TRADING_DAY, "FIRST TRADING DAY");
            enumMap.put(AttributesEnum.DELIST_DATE, "DELIST DATE");
            enumMap.put(AttributesEnum.QUOTATION_CURRENCY, "QUOTATION CURRENCY");
            enumMap.put(AttributesEnum.TRADES_ON_MARKET, "TRADES ON MARKET");
            enumMap.put(AttributesEnum.RIC, "RIC");
            enumMap.put(AttributesEnum.TICKER_SYMBOL, "TICKER SYMBOL");
            enumMap.put(AttributesEnum.ADMIN_STATUS, "ADMIN STATUS");
            enumMap.put(AttributesEnum.TRADES_ON_SUBMARKET,
                "TRADES ON SUBMARKET");
            enumMap.put(AttributesEnum.ROUND_LOT_SIZE, "ROUND LOT SIZE");
            enumMap.put(AttributesEnum.COMMON_NAME, "COMMON NAME");
            enumMap.put(AttributesEnum.SHORT_NAME, "SHORT NAME");
            enumMap.put(AttributesEnum.TRADING_STATUS, "TRADING STATUS");
            enumMap.put(AttributesEnum.EXCHANGE_ISIN, "EXCHANGE ISIN");
            enumMap.put(AttributesEnum.TRADING_SYMBOL, "TRADING SYMBOL");
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
