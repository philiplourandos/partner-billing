package com.mhgad.za.vitel.billing.batch.aspivia;

public enum AttributeEnum {

    INBOUND(8, "inbound"), OUTBOUND(1, "outbound");
    
    private final int val;
    private final String description;
    
    private AttributeEnum(int value, String description) {
        this.val = value;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getVal() {
        return val;
    }
    
    public static AttributeEnum forVal(int search) {
        AttributeEnum[] attribs = values();

        AttributeEnum required = null;

        for (AttributeEnum current : attribs) {
            if (current.val == search) {
                required = current;

                break;
            }
        }

        return required;
    }
}
