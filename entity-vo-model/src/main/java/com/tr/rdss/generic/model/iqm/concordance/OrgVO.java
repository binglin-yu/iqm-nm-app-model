package com.tr.rdss.generic.model.iqm.concordance;

import java.util.EnumMap;
import com.tr.rdss.generic.model.iqm.Entity;
import com.tr.rdss.generic.model.iqm.concordance.util.EnumInterface;

public class OrgVO extends EntityVO {

    public OrgVO() {
        super("Metadata", OrgVO.class.getName());
    }

    public OrgVO(Entity entity) {
        this();
        this.setEntity(entity);
    }

    /**
     * AttributesEnum list all the officially available attributes for the class
     */
    public static enum AttributesEnum implements EnumInterface {

        ORG_PERM_ID,
        NAME_TYPE,
        NAME_VALUE;
        private static EnumMap<AttributesEnum, String> enumMap;

        static {
            // the mapped string can be customized, as long as unique
            enumMap = new EnumMap<AttributesEnum, String>(AttributesEnum.class);
            enumMap.put(AttributesEnum.ORG_PERM_ID, "ORG_PERM_ID");
            enumMap.put(AttributesEnum.NAME_TYPE, "NAME_TYPE");
            enumMap.put(AttributesEnum.NAME_VALUE, "NAME_VALUE");
        }

        public String getEnumName() {
            return (String) enumMap.get(this);
        }
    }

    public OrgVO(Long orgPermId, String nameType, String nameValue) {
        this();

        this.addProperty(AttributesEnum.ORG_PERM_ID, orgPermId, null, null);
        this.addProperty(AttributesEnum.NAME_TYPE, nameType, null, null);;
        this.addProperty(AttributesEnum.NAME_VALUE, nameValue, null, null);
    }

    /**
     * 
     * @return organization perm id
     */
    public Long getId() {
        return Long.parseLong(this.getAttribute(AttributesEnum.ORG_PERM_ID).getValue());
    }

    /**
     * 
     * @return organization name type
     */
    public String getNameType() {
        return this.getAttribute(AttributesEnum.NAME_TYPE).getValue();
    }

    /**
     * 
     * @return organization name value
     */
    public String getName() {
        return this.getAttribute(AttributesEnum.NAME_VALUE).getValue();
    }

    /**
     * 
     * @return general info of the Org object
     */
    @Override
    public String toString() {
        return "<" + getId() + "," + getNameType() + "," + getName() + ">";
    }
}
