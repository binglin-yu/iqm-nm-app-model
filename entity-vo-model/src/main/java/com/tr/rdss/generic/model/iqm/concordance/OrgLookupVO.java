package com.tr.rdss.generic.model.iqm.concordance;

import java.util.ArrayList;
import java.util.List;

import com.tr.rdss.generic.model.iqm.Attribute;
import com.tr.rdss.generic.model.iqm.Relationship;

public class OrgLookupVO extends EntityVO implements LookupInterface {

    public OrgLookupVO() {
        super("Metadata", OrgLookupVO.class.getName());
    }

    /**
     * 
     * @param orgPermId organization perm id
     * @param nameType organization name type
     * @param nameValue name value
     * <br>
     * This function is used for building up the Org lookup list
     */
    public void addOrg(Long orgPermId, String nameType, String nameValue) {
        this.addRelationshipNoDedup(AttributesEnum.ELEMENT, //
            new OrgVO(orgPermId, nameType, nameValue), null, null);

    }

    
    /**
     * 
     * @return all the org elements held by the object
     */
    public List<OrgVO> getElementList() {
        List<OrgVO> res = new ArrayList<OrgVO>();
        for (Attribute a : getAttributes(AttributesEnum.ELEMENT)) {
            res.add(new OrgVO(((Relationship) a).getReference()));
        }
        return res;
    }

}
