package com.tr.rdss.generic.model.iqm.concordance;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Set;

import com.tr.rdss.generic.model.iqm.Attribute;
import com.tr.rdss.generic.model.iqm.Relationship;
//import com.tr.rdss.generic.model.iqm.concordance.ModelMetadataVO.ModelMetadataVOKey;
//import com.tr.rdss.generic.model.iqm.concordance.util.Utils;

public class ModelMetadataLookupVO extends EntityVO implements LookupInterface {

    public ModelMetadataLookupVO() {
        super("Metadata", ModelMetadataLookupVO.class.getName());
    }

    /**
     * 
     * @param domPermId domain perm id
     * @param domName domain name
     * @param enuPermId perm id of enumeration value
     * @param enuUniqueName enumeration value
     * @param enuValue enumeration short value, useless
     * @param tableName table name
     * @param propertyName property name
     * @param entityLevel entity level
     * <br>
     * This function is used for building up the ModelMetadata lookup list
     */
    public void addModelMetadata(Long domPermId, String domName,
        Long enuPermId, String enuUniqueName, String enuValue,
        String tableName, String propertyName, String entityLevel) {
        this.addRelationshipNoDedup(AttributesEnum.ELEMENT,
            new ModelMetadataVO(domPermId, domName, enuPermId,
                enuUniqueName, enuValue, tableName, propertyName,
                entityLevel), null, null);
    }

    /**
     * 
     * @return all the ModelMetadata elements held by the object
     */
    public List<ModelMetadataVO> getElementList() {
        List<ModelMetadataVO> res = new ArrayList<ModelMetadataVO>();
        for (Attribute a : getAttributes(AttributesEnum.ELEMENT)) {
            res.add(new ModelMetadataVO(((Relationship) a).getReference()));
        }
        return res;
    }

    // disabled by byu on 2015-Nov
//    private static ModelMetadataLookupVO modelMetadataLookupVO = null;
//    private static HashMap<ModelMetadataVO.ModelMetadataVOKey, ModelMetadataVO> codeMap = null;
//    private static HashMap<Long, ModelMetadataVO> idMap = null;
//
//    public static void set(ModelMetadataLookupVO lookupVO) {
//        modelMetadataLookupVO = lookupVO;
//        initHashMaps();
//    }
//
//    private static void initHashMaps() {
//        try {
//
//            if (modelMetadataLookupVO == null
//                || modelMetadataLookupVO.getElementList() == null
//                || modelMetadataLookupVO.getElementList().size() == 0) {
//
//                Utils.printTrace(false);
//                Utils.printMessage("!!!!! Must call ModelMetadataLookupVO.set(ModelMetadataLookupVO) to initialize lookup !!!!!");
//
//                throw new Exception("ERROR empty modelMetadataLookupVO!");
//            }
//
//        } catch (Exception e) {
//            Utils.printTrace(false);
//            Utils.printMessage(e);
//            modelMetadataLookupVO = new ModelMetadataLookupVO();
//        }
//
//        if (idMap != null) {
//            return;
//        }
//
//        codeMap = new HashMap<ModelMetadataVO.ModelMetadataVOKey, ModelMetadataVO>();
//        idMap = new HashMap<Long, ModelMetadataVO>();
//        for (ModelMetadataVO mtd : modelMetadataLookupVO.getElementList()) {
//            codeMap.put(mtd.getMetadataKey(), mtd);
//            idMap.put(Long.parseLong(mtd.getAttribute(ModelMetadataVO.AttributesEnum.ENU_PERM_ID).getValue()), mtd);
//        }
//    }
//
//    public static ModelMetadataVO getModelMetadataByValue(String entityLevel, String propertyName, String propertyValue) {
//
//        ModelMetadataVO.ModelMetadataVOKey key = new ModelMetadataVO.ModelMetadataVOKey(entityLevel, propertyName, propertyValue);
//        if (codeMap.containsKey(key)) {
//            return codeMap.get(key);
//        } else {
//            return null;
//        }
//    }
//
//    public static ModelMetadataVO getModelMetadataById(Long id) {
//
//        if (idMap.containsKey(id)) {
//            return idMap.get(id);
//        } else {
//            return null;
//        }
//    }
//
//    public static Set<ModelMetadataVOKey> getMetadataKeys() {
//
//        if (codeMap == null || codeMap.size() == 0) {
//            return null;
//        }
//        return codeMap.keySet();
//    }
//
//    public static HashMap<Long, String> getIdValueMap() {
//
//        if (modelMetadataLookupVO == null) {
//            return null;
//        }
//        List<ModelMetadataVO> list = modelMetadataLookupVO.getElementList();
//        HashMap<Long, String> hashMap = new HashMap<Long, String>();
//        for (ModelMetadataVO t : list) {
//            hashMap.put(t.getId(), t.getPropertyValue());
//        }
//        return hashMap;
//    }
}
