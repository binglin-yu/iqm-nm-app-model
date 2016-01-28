package com.tr.rdss.generic.model.iqm.concordance;

import java.util.ArrayList;
import java.util.List;

import com.tr.rdss.generic.model.iqm.Attribute;
import com.tr.rdss.generic.model.iqm.Relationship;

public class MarketLookupVO extends EntityVO implements LookupInterface {

    public MarketLookupVO() {
        super("Metadata", MarketLookupVO.class.getName());
    }

    /**
     * 
     * @param marketPermId market perm id
     * @param nameTypeId market name type id
     * @param nameType name type
     * @param nameValue name value
     * @param highestMarketPermId highest market perm id of the host market
     * 
     * <br>
     * This function is used for building up the market lookup list
     */
    public void addMarket(Long marketPermId, Long nameTypeId, String nameType,
        String nameValue, Long highestMarketPermId) {
        this.addRelationshipNoDedup(AttributesEnum.ELEMENT, //
            new MarketVO(marketPermId, nameTypeId, nameType, nameValue, //
                (highestMarketPermId == null ? null : new MarketVO(
                        highestMarketPermId, null, null, null, null)) //
            ),//
            null, null);

    }

    /**
     * 
     * @return all the market elements held by the object
     */
    public List<MarketVO> getElementList() {
        List<MarketVO> res = new ArrayList<MarketVO>();
        for (Attribute a : getAttributes(AttributesEnum.ELEMENT)) {
            res.add(new MarketVO(((Relationship) a).getReference()));
        }
        return res;
    }

    // disabled by byu on 2015-Nov
//    private static MarketLookupVO marketLookupVO = null;
//    private static HashMap<String, List<MarketVO>> nameMap = null;
//    private static HashMap<Long, List<MarketVO>> idMap = null;
//
//    public static void set(MarketLookupVO lookupVO) {
//        marketLookupVO = lookupVO;
//        initHashMaps();
//    }
//
//    private static void initHashMaps() {
//        try {
////            if (marketLookupVO == null) {
////                // use lookup to initialization
////                marketLookupVO = (MarketLookupVO) IQMLookupAppModuleService
////                    .getInstance().getMarketLookup();
////            }
//
//            if (marketLookupVO == null
//                || marketLookupVO.getElementList() == null
//                || marketLookupVO.getElementList().size() == 0) {
//
//                Utils.printTrace(false);
//                Utils.printMessage("!!!!! Must call MarketLookupVO.set(MarketLookupVO) to initialize lookup !!!!!");
//
//                throw new Exception("ERROR empty marketLookupVO!");
//            }
//
//        } catch (Exception e) {
//            Utils.printTrace(false);
//            Utils.printMessage(e);
//            marketLookupVO = new MarketLookupVO();
//        }
//
//        if (idMap != null) {
//            return;
//        }
//
//        nameMap = new HashMap<String, List<MarketVO>>();
//        idMap = new HashMap<Long, List<MarketVO>>();
//
//        for (MarketVO mkt : marketLookupVO.getElementList()) {
//            List<MarketVO> mktList = null;
//            String nameKey = mkt.getName();
//            if (nameKey == null) {
//                continue;
//            } else {
//                nameKey = nameKey.toUpperCase();
//            }
//            if (!nameMap.containsKey(nameKey)) {
//                mktList = new ArrayList<MarketVO>();
//                nameMap.put(nameKey, mktList);
//            } else {
//                mktList = nameMap.get(nameKey);
//            }
//            mktList.add(mkt);
//
//            List<MarketVO> mktList1 = null;
//            Long idKey = mkt.getId();
//            if (!idMap.containsKey(idKey)) {
//                mktList1 = new ArrayList<MarketVO>();
//                idMap.put(idKey, mktList1);
//            } else {
//                mktList1 = idMap.get(idKey);
//            }
//            mktList1.add(mkt);
//        }
//    }
//
//    public static List<MarketVO> getMarketsByName(String name) {
//
//        String nameKey = name.toUpperCase();
//        if (nameMap.containsKey(nameKey)) {
//            return nameMap.get(nameKey);
//        } else {
//            return null;
//        }
//    }
//
//    public static List<MarketVO> getMarketsById(Long id) {
//
//        if (idMap.containsKey(id)) {
//            return idMap.get(id);
//        } else {
//            return null;
//        }
//    }
//
//    public static Long getMarketIdByLongName(String longName) {
//        List<MarketVO> marketVO = MarketLookupVO
//            .getMarketsByName(longName);
//        if (marketVO == null || marketVO.size() == 0) {
//            return null;
//        } else {
//            Long tmpId = marketVO.get(0).getId();
//            for (int i = 0; i < marketVO.size(); i++) {
//                if (marketVO.get(i).getNameType().toUpperCase().equals("LONG NAME")) {
//                    tmpId = marketVO.get(i).getId();
//                    break;
//                }
//            }
//            return tmpId;
//        }
//    }
//
//    public static String getMarketLongNameById(Long marketPermId) {
//        List<MarketVO> marketVO = MarketLookupVO.getMarketsById(marketPermId);
//        if (marketVO == null || marketVO.size() == 0) {
//            return null;
//        } else {
//            String tmpName = marketVO.get(0).getName();
//            for (int i = 0; i < marketVO.size(); i++) {
//                if (marketVO.get(i).getNameType().toUpperCase().equals("LONG NAME")) {
//                    tmpName = marketVO.get(i).getName();
//                    break;
//                }
//            }
//            return tmpName;
//        }
//    }
//
//    public static String getMarketMicById(Long marketPermId) {
//        List<MarketVO> marketVO = MarketLookupVO.getMarketsById(marketPermId);
//        if (marketVO == null || marketVO.size() == 0) {
//            return null;
//        } else {
//            String tmpMic = marketVO.get(0).getName();
//            for (int i = 0; i < marketVO.size(); i++) {
//                if (marketVO.get(i).getNameType().toUpperCase().equals("MIC")) {
//                    tmpMic = marketVO.get(i).getName();
//                    break;
//                }
//            }
//            return tmpMic;
//        }
//    }

//    public static HashMap<Long, String> getIdValueMap() {
//
//        if (marketLookupVO == null) {
//            return null;
//        }
//        List<MarketVO> list = marketLookupVO.getElementList();
//        HashMap<Long, String> hashMap = new HashMap<Long, String>();
//        for (MarketVO t : list) {
//            if (t.getNameType().toUpperCase().equals("LONG NAME")) {
//                hashMap.put(t.getId(), t.getName());
//            }
//        }
//        return hashMap;
//    }
}
