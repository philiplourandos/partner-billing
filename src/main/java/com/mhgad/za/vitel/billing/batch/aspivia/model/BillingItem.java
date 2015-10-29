package com.mhgad.za.vitel.billing.batch.aspivia.model;

import com.mhgad.za.vitel.billing.batch.aspivia.AttributeEnum;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author plourand
 */
public class BillingItem {
    private String extension;
    private Timestamp pbxDateTime;
    private Integer callDuration;
    private String accountCode;
    private String digits;
    private BigDecimal cost;
    private String destination;
    private String carrier;
    private AttributeEnum attribute;

    public BillingItem() {
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Timestamp getPbxDateTime() {
        return pbxDateTime;
    }

    public void setPbxDateTime(Timestamp pbxDateTime) {
        this.pbxDateTime = pbxDateTime;
    }

    public Integer getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Integer callDuration) {
        this.callDuration = callDuration;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public AttributeEnum getAttribute() {
        return attribute;
    }

    public void setAttribute(AttributeEnum attribute) {
        this.attribute = attribute;
    }
}
