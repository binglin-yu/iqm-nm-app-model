package com.tr.rdss.generic.model.iqm.concordance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tr.rdss.generic.model.iqm.Attribute;
import com.tr.rdss.generic.model.iqm.Relationship;

public class MasterGateLookupVO extends EntityVO implements LookupInterface {

    public MasterGateLookupVO() {
        super("Metadata", MasterGateLookupVO.class.getName());
    }

    /**
     * 
     * @param iqmId iqm id, internal id
     * @param permId entity perm id
     * @param entityLevel entity level
     * @param proNameD property name
     * @param value property value
     * @param proIqmId iqm id of the property name, internal id
     * @param effectiveFrom effective from of the property
     * @param effectiveTo effective to of the property
     * <br>
     * This function is used for building up the MasterGate lookup list
     */
    public void addMasterGate(Long iqmId, Long permId, String entityLevel,
        String proNameD, String value, Long proIqmId, Date effectiveFrom, Date effectiveTo) {
        this.addRelationshipNoDedup(AttributesEnum.ELEMENT, //
            new MasterGateVO(iqmId, permId, entityLevel,
                proNameD, value, proIqmId, effectiveFrom, effectiveTo), effectiveFrom, effectiveTo);

    }

    /**
     * 
     * @return all the MasterGate elements held by the object
     */
    public List<MasterGateVO> getElementList() {
        List<MasterGateVO> res = new ArrayList<MasterGateVO>();
        for (Attribute a : getAttributes(AttributesEnum.ELEMENT)) {
            res.add(new MasterGateVO(((Relationship) a).getReference()));
        }
        return res;
    }

}
