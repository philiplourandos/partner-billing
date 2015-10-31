package com.mhgad.za.vitel.billing.batch.aspivia.model;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author plourand
 */
public class Summary {
    private Integer numberOfCalls = 0;
    private Integer accountCode;
    private String partner;
    private BigDecimal moneyIn = new BigDecimal(BigInteger.ZERO);
    private BigDecimal moneyOut = new BigDecimal(BigInteger.ZERO);

    public Summary() {
    }

    public Integer getNumberOfCalls() {
        return numberOfCalls;
    }

    public void incCallCount() {
        numberOfCalls++;
    }

    public Integer getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(Integer accountCode) {
        this.accountCode = accountCode;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public BigDecimal getMoneyIn() {
        return moneyIn;
    }

    public BigDecimal getMoneyOut() {
        return moneyOut;
    }

    public BigDecimal getFinalTotal() {
        return moneyIn.add(moneyOut);
    }

    public void addInbound(BigDecimal amount) {
        moneyIn = moneyIn.add(amount);
    }
    
    public void addOutBound(BigDecimal amount) {
        moneyOut = moneyOut.add(amount);
    }
}
