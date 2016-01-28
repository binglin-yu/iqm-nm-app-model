package com.tr.rdss.generic.model.iqm.concordance;

import java.util.Date;
import java.util.EnumMap;
import com.tr.rdss.generic.model.iqm.Entity;
import com.tr.rdss.generic.model.iqm.ModelUtil;
import com.tr.rdss.generic.model.iqm.concordance.util.EnumInterface;

public class MasterGateVO extends EntityVO {

    public MasterGateVO() {
        super("Metadata", MasterGateVO.class.getName());
    }

    public MasterGateVO(Entity entity) {
        this();
        this.setEntity(entity);
    }

    /**
     * AttributesEnum list all the officially available attributes for the class
     */
    public static enum AttributesEnum implements EnumInterface {
        IQM_ID,
        PERM_ID,
        ENTITY_LEVEL,
        PRO_NAME_D,
        VALUE,
        PRO_IQM_ID;
        private static final EnumMap<AttributesEnum, String> enumMap;

        static {
            // the mapped string can be customized, as long as unique
            enumMap = new EnumMap<AttributesEnum, String>(AttributesEnum.class);
            enumMap.put(AttributesEnum.IQM_ID, "IQM_ID");
            enumMap.put(AttributesEnum.PERM_ID, "PERM_ID");
            enumMap.put(AttributesEnum.ENTITY_LEVEL, "ENTITY_LEVEL");
            enumMap.put(AttributesEnum.PRO_NAME_D, "PRO_NAME_D");
            enumMap.put(AttributesEnum.VALUE, "VALUE");
            enumMap.put(AttributesEnum.PRO_IQM_ID, "PRO_IQM_ID");

        }

        public String getEnumName() {
            return (String) enumMap.get(this);
        }
    }

    public MasterGateVO(Long iqmId, Long permId, String entityLevel,
        String proNameD, String value, Long proIqmId, Date effectiveFrom, Date effectiveTo) {
        this();

        this.addProperty(AttributesEnum.IQM_ID, iqmId, effectiveFrom, effectiveTo);
        this.addProperty(AttributesEnum.PERM_ID, permId, effectiveFrom, effectiveTo);
        this.addProperty(AttributesEnum.ENTITY_LEVEL, entityLevel, effectiveFrom, effectiveTo);
        this.addProperty(AttributesEnum.PRO_NAME_D, proNameD, effectiveFrom, effectiveTo);
        this.addProperty(AttributesEnum.VALUE, value, effectiveFrom, effectiveTo);
        this.addProperty(AttributesEnum.PRO_IQM_ID, proIqmId, effectiveFrom, effectiveTo);
    }

    /**
     * 
     * @return entity perm id
     */
    public Long getId() {
        return Long.parseLong(this.getAttribute(AttributesEnum.PERM_ID).getValue());
    }

    /**
     * 
     * @return entity level
     */
    public String getEntityLevel() {
        return this.getAttribute(AttributesEnum.ENTITY_LEVEL).getValue();
    }
    
    /**
     * 
     * @return property name
     */
    public String getPropertyName() {
        return this.getAttribute(AttributesEnum.PRO_NAME_D).getValue();
    }

    /**
     * 
     * @return property value
     */
    public String getPropertyValue() {
        return this.getAttribute(AttributesEnum.VALUE).getValue();
    }

    /**
     * 
     * @return effective from
     */
    public Date getEffectiveFrom() {
        return ModelUtil.formatDate(this.getAttribute(AttributesEnum.VALUE).getEffectiveFrom());
    }

    /**
     * 
     * @return effective to
     */
    public Date getEffectiveTo() {
        return ModelUtil.formatDate(this.getAttribute(AttributesEnum.VALUE).getEffectiveTo());
    }

    /**
     * 
     * @return general info of the MasterGate object
     */
    @Override
    public String toString() {
        return "<" + getId() + "," + getEntityLevel() + "," + getPropertyName() + "," + getPropertyValue() + ","
            + ModelUtil.formatDateString(getEffectiveFrom()) + ","
            + ModelUtil.formatDateString(getEffectiveTo()) + ">";
    }
}
