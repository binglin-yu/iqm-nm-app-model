package com.tr.rdss.generic.model.iqm;

public class EffectiveValue {

    private String effectiveFrom;
    private String effectiveTo;
    private String value;

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public String getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(String effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EffectiveValue(String from, String to, String value) {
        this.effectiveFrom = from;
        this.effectiveTo = to;
        this.value = value;
    }
}
