package com.tr.rdss.generic.model.iqm.concordance.util;

import java.util.HashSet;
import java.util.List;

import com.tr.rdss.generic.model.iqm.Attribute;
import com.tr.rdss.generic.model.iqm.Entity;
import com.tr.rdss.generic.model.iqm.InstrumentAsset;
import com.tr.rdss.generic.model.iqm.Property;
import com.tr.rdss.generic.model.iqm.QuoteAsset;
import com.tr.rdss.generic.model.iqm.Relationship;
import com.tr.rdss.generic.model.iqm.concordance.EntityVO;
import com.tr.rdss.generic.model.iqm.concordance.InstrumentVO;
import com.tr.rdss.generic.model.iqm.concordance.ModelMetadataVO;
import com.tr.rdss.generic.model.iqm.concordance.QuoteVO;

/**
 * 
 * @author U8015921
 * <br>
 * <br>
 * It defines the keys of the convertible properties (e.g. INSTRUMENT_STATUS).
 */
class ConvertiblePropertyKey {

    /**
     * entity level
     */
    private final String entityLevel;
    /**
     * property name
     */
    private final String propertyName;
    public static final String quoteEntityLevel = QuoteVO.entityLevel;
    public static final String instrumentEntityLevel = InstrumentVO.entityLevel;

    public ConvertiblePropertyKey(String entityLevel, String propertyName) {
        this.entityLevel = entityLevel.toUpperCase();
        this.propertyName = propertyName;
    }

    @Override
    public boolean equals(Object o) {
        if (entityLevel.equals(((ConvertiblePropertyKey) o).entityLevel)
            && propertyName
            .equals(((ConvertiblePropertyKey) o).propertyName)) {
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
        return "<" + entityLevel + "," + propertyName + ">";
    }
}

/**
 * 
 * @author U8015921
 * <br>
 * <br>
 * Currently, only two kinds of properties are convertible for RMI services<br>
 *  a) currency related properties <br>
 *  b) db configured properties, value of which are from ModelMedata (DB object: iqm_nav.mtd_domain_mv) <br>
 * 
 * So, convertible properties must have valid values (values which are already covered/maintained by IQM model).
 * E.g. entity ADMIN_STATUS can have valid value "Published" while "PUBLISHED" are illegal.
 */

public abstract class AttributeConverter {

    private static HashSet<ConvertiblePropertyKey> currencyLookupAttributeNames = null;
    // market related properties are removed out of the convertiable list because 
    // we originally thought RDNExchangeCode were unique for markets which was wrong.
//    private static HashSet<ConvertiblePropertyKey> marketLookupAttributeNames = null;
    
    private static HashSet<ConvertiblePropertyKey> modelMetadataLookupAttributeNames = null;
    private static AttributeConverterService service;

    /**
     * 
     * @param entityLevel entity level
     * @param enumName property enumeration name
     * @return checking result
     * @see #isConvertiable(java.lang.String, java.lang.String) 
     */
    public static boolean isConvertiable(String entityLevel,
        EnumInterface enumName) {
        return isConvertiable(entityLevel, enumName.getEnumName());
    }

    /**
     * 
     * @param entityLevel entity level
     * @param attName attribute name
     * @return checking result, whether the input attribute is related to currency
     */
    private static boolean isCurrencyLookupRelated(String entityLevel,
        String attName) {
        if (currencyLookupAttributeNames.contains(new ConvertiblePropertyKey(
            entityLevel, attName))) {
            return true;
        } else {
            return false;
        }
    }

//    private static boolean isMarketLookupRelated(String entityLevel,
//        String attName) {
//        if (marketLookupAttributeNames.contains(new ConvertiblePropertyKey(
//            entityLevel, attName))) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    /**
     * 
     * @param entityLevel entity level
     * @param attName attribute name
     * @return checking result, whether the input attribute is related to ModelMetadata lookup
     */
    private static boolean isModelMetadataLookupRelated(String entityLevel,
        String attName) {
        if (modelMetadataLookupAttributeNames
            .contains(new ConvertiblePropertyKey(entityLevel, attName))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @param entityLevel entity level
     * @param propertyName property name
     * @return checking result, whether the input property is convertible 
     * (whether property value is from ModelMetadata lookup, or whether the input property is related to currency)
     */
    public static boolean isConvertiable(String entityLevel, String propertyName) {
        if (isCurrencyLookupRelated(entityLevel, propertyName)
//            || isMarketLookupRelated(entityLevel, propertyName)
            || isModelMetadataLookupRelated(entityLevel, propertyName)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     * @param service RMI service
     * @throws InvalidServiceMethodCallException
     * @throws JSONException
     * <br>
     * Initialize the convertible property list
     */
    public static void init(AttributeConverterService service) throws InvalidServiceMethodCallException, JSONException {
//        Utils.printTrace(false);
//        Utils.printMessage("start ... " + Utils.convertToString(new java.util.Date()));
        AttributeConverter.service = service;

        currencyLookupAttributeNames = new HashSet<ConvertiblePropertyKey>();
//        marketLookupAttributeNames = new HashSet<ConvertiblePropertyKey>();
        modelMetadataLookupAttributeNames = new HashSet<ConvertiblePropertyKey>();

        currencyLookupAttributeNames.add(new ConvertiblePropertyKey(
            ConvertiblePropertyKey.quoteEntityLevel,
            QuoteVO.AttributesEnum.QUOTATION_CURRENCY.getEnumName()));
//        marketLookupAttributeNames.add(new ConvertiblePropertyKey(
//            ConvertiblePropertyKey.quoteEntityLevel,
//            QuoteVO.AttributesEnum.TRADES_ON_MARKET.getEnumName()));
//
//        marketLookupAttributeNames.add(new ConvertiblePropertyKey(
//            ConvertiblePropertyKey.quoteEntityLevel,
//            QuoteVO.AttributesEnum.TRADES_ON_SUBMARKET.getEnumName()));
        
        List<ModelMetadataVO> modelMetadataVOList = service.getModelMetadataProperties();
        for (ModelMetadataVO modelMetadataVO : modelMetadataVOList) {
            modelMetadataLookupAttributeNames.add(new ConvertiblePropertyKey(modelMetadataVO.getEntityLevel(), modelMetadataVO.getPropertyName()));
        }

    }

    /**
     * 
     * @param v raw string value
     * @return checking result, whether the input string value is actually the string output of a Long value
     */
    private static boolean isLong(String v) {
        if (v == null) {
            return true;
        }
        try {
            Long.parseLong(v);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    /**
     * 
     * @param v raw object value
     * @return checking result, whether the input object value is actually of Long value, 
     * or a String value which can be verified by isLong(String)
     */
    private static boolean isLong(Object v) {
        if (v instanceof Long) {
            return true;
        } else if (v instanceof String) {
            return isLong(v.toString());
        } else {
            return false;
        }
    }

    /**
     * 
     * @param entityLevel entity level
     * @param attName attribute name
     * @param value attribute denormalized value
     * @return attribute normalized value (i.e. widely used Id in IQM model)
     * @throws InvalidServiceMethodCallException
     */
    private static Long getNormalizedIdForRawValue(String entityLevel,
        String attName, Object value) throws InvalidServiceMethodCallException {
        String valueStr = value.toString();
        if (isCurrencyLookupRelated(entityLevel, attName)) {
            return AttributeConverter.service.getCurrencyPermIdByCode(valueStr);
        } else if (isModelMetadataLookupRelated(entityLevel, attName)) {
            return AttributeConverter.service.getModelMetadataPermIdByLevelNameValue(entityLevel, attName, valueStr);
        }
//        else if (isMarketLookupRelated(entityLevel, attName)) {
//            // TODO ? how to pick up the right market to return?
//            return AttributeConverter.service.getMarketPermIdByRDNExchangeCode(valueStr);
//        }
        return null;
    }

    /**
     * 
     * @param entityLevel entity level
     * @param attName attribute
     * @param value attribute value
     * @return checking result
     * @throws InvalidServiceMethodCallException
     * <br>
     * If the input attribute is not convertible, always return true.
     * Otherwise, return the checking result of whether the input raw value is valid (acceptable for IQM model)
     */
    public static boolean isValidRawValue(String entityLevel, String attName,
        Object value) throws InvalidServiceMethodCallException {
        if (!isConvertiable(entityLevel, attName)) {
            return true;
        }

        Long id = getNormalizedIdForRawValue(entityLevel, attName, value);
        if (id == null) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @param entityLevel entity level
     * @param attName attribute name
     * @param value attribute value
     * @param requestNormalizedValue a flag to label whether normalized value are requested or the denormalized value
     * @return converted value
     * @throws InvalidRawValueException 
     * @throws InvalidServiceMethodCallException
     * <br>
     * If the input value is already the requested version of value, just forward back it.
     * Otherwise, call RMI services to convert the input value to what requested.
     * E.g. if requesting the normalized id of attribute value "Published" 
     * (of entityLevel:"INSTRUMENT" and attName:"ADMIN STATUS"), returning 1010003.
     */
    public static Object convert(String entityLevel, String attName,
        Object value, boolean requestNormalizedValue)
        throws InvalidRawValueException, InvalidServiceMethodCallException {
        boolean actionFlag = (!requestNormalizedValue && isLong(value))
            || (requestNormalizedValue && !isLong(value));
        if (!actionFlag) {
            return value;
        }

        if (isCurrencyLookupRelated(entityLevel, attName)) {
            if (!requestNormalizedValue) {
                Long oldId = Long.parseLong(value.toString());
                return AttributeConverter.service.getCurrencyCodeByPermId(oldId);
            } else if (requestNormalizedValue) {
                return getNormalizedIdForRawValue(entityLevel, attName, value.toString());
            }
        } else if (isModelMetadataLookupRelated(entityLevel, attName)) {
            if (!requestNormalizedValue) {
                Long oldId = Long.parseLong(value.toString());
                return AttributeConverter.service.getMetadataValueByEnuPermid(oldId);
            } else if (requestNormalizedValue) {
                return getNormalizedIdForRawValue(entityLevel, attName, value.toString());
            }
        }
//        else if (isMarketLookupRelated(entityLevel, attName)) {
//            if (!requestNormalizedValue) {
//                Long oldId = Long.parseLong(value.toString());
//                // TODO !!! how to deal with the multiple matched values?
//                return AttributeConverter.service.getMarketRDNExchangeCodeByPermId(oldId);
//            } else if (requestNormalizedValue) {
//                // TODO !!! how to deal with the multiple matched values?
//                return getNormalizedIdForRawValue(entityLevel, attName, value.toString());
//            }
//        }
        return value;
    }

    // convert id to value for properties within convertiblePropertyKeys
    /**
     * 
     * @param entityLevel entity level
     * @param att property type of attribute
     * @param normalizedValue a flag to label whether normalized value requested or the reverse one
     * @throws InvalidRawValueException
     * @throws InvalidServiceMethodCallException
     * <br>
     * Convert the property value for the input property (if it is a convertible property).
     * If the input property is not of convertible, or its value is already the requested copy, no changes will happen.
     */
    private static void convert(String entityLevel, Property att,
        boolean normalizedValue) throws InvalidRawValueException, InvalidServiceMethodCallException {


            Object value = convert(entityLevel, att.getName(), att.getValue(),
                normalizedValue);

            att.setValue(value != null ? value.toString() : "");

            if (att.getNewAttribute() != null
                && att.getNewAttribute().getValue() != null) {
                Object value1 = convert(entityLevel, att.getName(), att
                    .getNewAttribute().getValue(), normalizedValue);
                ((Property) att.getNewAttribute()).setValue(value1 != null ? value1.toString() : "");
            }
    }

    /**
     * 
     * @param entityVO entityVO
     * @param normalizedValue a flag to label whether normalized value requested for all attributes of entityVO, 
     * or the denormalized versions.
     * @return entityVO with converted attribute values
     * @throws InvalidRawValueException
     * @throws InvalidServiceMethodCallException
     * <br>
     * If the conversion happens successfully, the EntityVO.isNormalized state will be set.
     */
    public static EntityVO convertEntityVO(EntityVO entityVO,
        boolean normalizedValue) throws InvalidRawValueException, InvalidServiceMethodCallException {
        if (normalizedValue == true && !("Y".equals(entityVO.isNormalized()))
            || normalizedValue == false
            && !("N".equals(entityVO.isNormalized()))) {
            AttributeConverter.convert(entityVO.getEntity(), normalizedValue);
            entityVO.setIsNormalized(normalizedValue == true ? "Y" : "N");
        }
        return entityVO;
    }

    /**
     * 
     * @param entity entity
     * @param normalizedValue a flag to label whether normalized value requested for all attributes of entityVO, 
     * or the denormalized versions.
     * @throws InvalidRawValueException
     * @throws InvalidServiceMethodCallException
     * <br>
     * Only the convertible properties will have value changes if needed.
     */
    private static void convert(Entity entity, boolean normalizedValue) throws InvalidRawValueException, InvalidServiceMethodCallException {

        if (entity == null) {
            return;
        }

        String entityLevel = null;
        if (entity instanceof InstrumentAsset) {
            entityLevel = ConvertiblePropertyKey.instrumentEntityLevel;
        } else if (entity instanceof QuoteAsset) {
            entityLevel = ConvertiblePropertyKey.quoteEntityLevel;
        } else {
            return;
        }

        List<Attribute> atts = entity.getAttributes();
        if (atts == null || atts.size() == 0) {
            return;
        }
        for (Attribute att : atts) {
            if (att instanceof Relationship && ((Relationship) att).getReference() != null) {
                convert(((Relationship) att).getReference(), normalizedValue);
            } else if (att instanceof Property
                && AttributeConverter.isConvertiable(entityLevel,
                    att.getName())) {
                convert(entityLevel, (Property) att, normalizedValue);
            }
        }
    }
}
