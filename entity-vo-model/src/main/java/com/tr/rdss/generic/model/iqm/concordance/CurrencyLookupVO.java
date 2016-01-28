package com.tr.rdss.generic.model.iqm.concordance;

import java.util.ArrayList;
import java.util.List;

import com.tr.rdss.generic.model.iqm.Attribute;
import com.tr.rdss.generic.model.iqm.Relationship;

public class CurrencyLookupVO extends EntityVO implements LookupInterface {

    public CurrencyLookupVO() {
        super("Metadata", CurrencyLookupVO.class.getName());
    }

    /**
     * 
     * @param currencyPermId currency perm id
     * @param currencyName currency name
     * @param currencyCode currency code
     * <br>
     * This function is used for building up the currency lookup list
     */
    public void addCurrency(Long currencyPermId, String currencyName,
        String currencyCode) {
        this.addRelationshipNoDedup(AttributesEnum.ELEMENT, new CurrencyVO(
            currencyPermId, currencyName, currencyCode), null, null);
    }

    /**
     * 
     * @return all the currency elements held by the object
     */
    public List<CurrencyVO> getElementList() {
        List<CurrencyVO> res = new ArrayList<CurrencyVO>();
        for (Attribute a : getAttributes(AttributesEnum.ELEMENT)) {
            res.add(new CurrencyVO(((Relationship) a).getReference()));
        }
        return res;
    }

    // disabled by byu 2015-Nov
//    private static CurrencyLookupVO currencyLookupVO = null;
//    private static HashMap<String, CurrencyVO> codeMap = null;
//    private static HashMap<String, CurrencyVO> nameMap = null;
//    private static HashMap<Long, CurrencyVO> idMap = null;
//
//    public static void set(CurrencyLookupVO lookupVO) {
//        currencyLookupVO = lookupVO;
//        initHashMaps();
//    }
//
//    private static void initHashMaps() {
//        try {
//            if (currencyLookupVO == null
//                || currencyLookupVO.getElementList() == null
//                || currencyLookupVO.getElementList().size() == 0) {
//                Utils.printTrace(false);
//                Utils.printMessage("!!!!! Must call CurrencyLookupVO.set(CurrencyLookupVO) to initialize lookup !!!!!");
//                throw new Exception("ERROR empty currencyLookupVO!");
//            }
//
//        } catch (Exception e) {
//            Utils.printTrace(false);
//            Utils.printMessage(e);
//            currencyLookupVO = new CurrencyLookupVO();
//        }
//
//        if (idMap != null) {
//            return;
//        }
//
//        codeMap = new HashMap<String, CurrencyVO>();
//        nameMap = new HashMap<String, CurrencyVO>();
//        idMap = new HashMap<Long, CurrencyVO>();
//
//        for (CurrencyVO cur : currencyLookupVO.getElementList()) {
//            codeMap.put(cur.getCode(), cur);
//            nameMap.put(cur.getName(), cur);
//            idMap.put(cur.getId(), cur);
//        }
//    }
//
//    public static CurrencyVO getCurrencyByCode(String code) {
//
//        if (codeMap.containsKey(code)) {
//            return codeMap.get(code);
//        } else {
//            return null;
//        }
//    }
//
//    public static CurrencyVO getCurrencyByName(String name) {
//
//        if (nameMap.containsKey(name)) {
//            return nameMap.get(name);
//        } else {
//            return null;
//        }
//    }
//
//    public static CurrencyVO getCurrencyById(Long id) {
//
//        if (idMap.containsKey(id)) {
//            return idMap.get(id);
//        } else {
//            return null;
//        }
//    }
//
//    public static HashMap<Long, String> getIdValueMap() {
//
//        if (currencyLookupVO == null) {
//            return null;
//        }
//        List<CurrencyVO> curList = currencyLookupVO.getElementList();
//        HashMap<Long, String> hashMap = new HashMap<Long, String>();
//        for (CurrencyVO cur : curList) {
//            hashMap.put(cur.getId(), cur.getName());
//        }
//        return hashMap;
//    }

}
